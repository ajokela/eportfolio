/* $Name:  $ */
/* $Id: FileController.groovy,v 1.18 2011/03/14 19:37:44 ajokela Exp $ */
/* $Id: FileController.groovy,v 1.18 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.client.controller

import java.io.FileNotFoundException;
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.net.URLEncoder
import java.util.Date
import net.sf.json.JSONObject
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import java.security.*

import org.apache.commons.io.IOUtils
import org.portfolio.util.LogService
import org.portfolio.util.FileUtil
import org.portfolio.bus.PortfolioManager
import org.portfolio.model.Person
import org.portfolio.model.Person.UserType;
import org.portfolio.util.DateUtil
import org.portfolio.bus.AttachmentManager;
import org.portfolio.bus.CollectionGuideManager;
import org.portfolio.bus.ElementDefinitionManager
import org.portfolio.bus.ElementManager
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils
import org.portfolio.client.multipart.UploadProgressListener
import org.portfolio.dao.FileAccessor
import org.portfolio.dao.PersonHome;
import org.portfolio.model.ElementDataObject
import org.portfolio.model.ElementDefinition
import org.portfolio.model.EntryKey
import org.portfolio.model.FileElement
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.model.community.Community.CommunityType;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.PathVariable
import org.portfolio.bus.PermissionsManager
import org.portfolio.util.HexTools
import org.portfolio.model.Portfolio

@Controller
@NoAuthentication
public class FileController {
	
	@Autowired
	private ElementManager elementManager;
	@Autowired
	private ElementDefinitionManager elementDefinitionManager;
	@Autowired
	private AttachmentManager attachmentManager;
    @Autowired
    private PermissionsManager permissionsManager;
    @Autowired
    private PortfolioManager portfolioManager;
    
	@Autowired
	private CommunityManager communityManager;
	
	@Autowired
	private PersonHome personHome;
	
	private LogService logService = new LogService(this.getClass());
	
	private final static String notfound_message = "<html><head><title>File Not Found</title></head><body><div style=\"text-align: center; width: 100%; font-weight: bold;\">It appears that your file has been misplaced.  ePortfolio was not able to find it.</div></body></html>";
	
	@RequestMapping(["/file/{entryKeyId}"])
	public void download(
	    @PathVariable("entryKeyId") EntryKey entryKey,
	    @RequestParam(value="portfolioId", required=false) String portfolioId,
	    HttpServletRequest request,
	    HttpServletResponse response,
	    OutputStream os) {
		
		Person person = RequestUtils.getPerson(request);

		def encodedFileName = "NotFound.html";
		
        if (portfolioId != null) {
		    
		    if ( portfolioManager.containsEntry(portfolioId, entryKey) ) {
		    	permissionsManager.grantPermission(entryKey);
    		}
    		
    	}
		
		Person fP = personHome.findByPersonId(entryKey.getPersonId());
		
		if(fP != null) {
			if(fP.getUsertype() == UserType.CMTY) {
				Community community = communityManager.getCommunityById(fP.getCommunityId());
				
				if(community != null) {
					List<Person> members = communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.MEMBER);
					members.addAll(communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.ASSESSMENT_COORDINATOR));
					members.addAll(communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.EVALUATOR));
					members.addAll(communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.COMMUNITY_COORDINATOR));
				
					if(members.contains(person)) {
						permissionsManager.grantPermission(entryKey);
					}
					
				}
				
			}
		}

		
		if (person != null && !person.getPersonId().equals(entryKey.getPersonId()) && !(permissionsManager.hasPermission(entryKey))) {
			throw new SecurityException("no access");
		}
		else {
			permissionsManager.grantPermission(entryKey);
		}
		
        if (!(permissionsManager.hasPermission(entryKey))) {
            throw new SecurityException("no access");
        }
		
		if(portfolioId != null) {
			Portfolio portfolio = portfolioManager.findById(portfolioId)
			
			if(portfolio != null && portfolio.isPublicView()) {
				entryKey.setIsPublic(true)
			}
			
		}
		
		ElementDataObject elementDataObject = elementManager.findElementInstance(entryKey, person)
		
		if (elementDataObject instanceof FileElement) {
			FileElement fileElement = (FileElement) elementDataObject;
			encodedFileName = URLEncoder.encode(fileElement.fileName, "UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=$encodedFileName");
			response.contentType = fileElement.mimeType;
			
			FileAccessor fileAccessor = fileElement.elementDefinition.fileAccessor;
		
			try {
				InputStream is = fileAccessor.getFile(fileElement);
								
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				DigestInputStream input = new DigestInputStream(is, md);
				
				if(is != null) {
					
					IOUtils.copy(input, os);
					
					if(fileElement.getSHA2() == null) {
						
						logService.debug("==> Setting SHA2 fingerprint for $encodedFileName");
						fileElement.setSHA2(HexTools.bytesToHex(md.digest()));
						elementManager.store(fileElement);
						
					}					
					
					return;
				
				}				
			}
			catch (FileNotFoundException fnfe) {
				logService.debug(fnfe);	
			}
			catch (Exception e) {
				logService.debug(fnfe);
			}
			
		}
		
		response.setHeader("Content-Disposition", "inline; filename=$encodedFileName");
		response.contentType = "text/html";
		os.write(notfound_message.getBytes());
	}
	
	@RequestMapping(["/file/upload/"])
	void upload(
    	@RequestParam("file") MultipartFile file,
		@RequestParam("elementDefId") String elementDefId,
		@RequestParam(value="attachToEntryKeyId", required=false) EntryKey attachToEntryKey,
    	HttpServletRequest request,
    	HttpServletResponse response, 
    	PrintWriter writer) {
		ElementDefinition elementDefinition = elementDefinitionManager.findByElementId(elementDefId)
		
		response.contentType = "text/html"
		
		def resp
		try {
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			DigestInputStream input = new DigestInputStream(file.inputStream, md);
			
			FileElement newInstance = (FileElement) elementManager.newInstance(elementDefinition)
			Date now = new Date()
			
			newInstance.with {
				
				entryName = file.originalFilename
				fileName = file.originalFilename
				personId = RequestUtils.getPersonId(request)
				mimeType = file.contentType
				size = file.size
				modifiedDate = now
				dateCreated = now
				
			}
			
			if (newInstance.image) {
			    BufferedImage image = ImageIO.read(file.inputStream)
                newInstance.height = image.height
                newInstance.width = image.width
			}
						
            FileAccessor fileAccessor = elementDefinition.fileAccessor
            fileAccessor.saveFile(newInstance, input)
			
			newInstance.setSHA2(HexTools.bytesToHex(md.digest()));
			
			elementManager.store(newInstance);

			if (attachToEntryKey) {
				attachmentManager.addAttachments(attachToEntryKey, [newInstance.entryKey] as EntryKey[])
			}
			
			resp = [
                    stat: "ok",
                    data: [
                        entry: entryToJsonFormat(newInstance),
                        filename: file.originalFilename,
                        entryKeyId: newInstance.entryKey.id    
                    ]
            ]
		} catch (e) {
		    logService.error(e)
            resp = [
                    stat: "fail",
                    errorCode: "general.fail",
                    errorMsg: "An error occurred."]
		}

        writer.print JSONObject.fromObject(resp).toString()
	}
    
    def entryToJsonFormat = { entry ->
        [
            entryKeyId: entry.entryKey.id,
            entryName: entry.entryName,
            modifiedDate: entry.modifiedDate?.format('MM/dd/yy'),
            modifiedDateMillis: entry.modifiedDate?.time,
            elementDefinition: [
                name: entry.elementDefinition.name,
                iconPath: entry.elementDefinition.iconPath
            ]
        ]
    } 
	
	@RequestMapping(["/file/upload/progress"])
	void uploadProgress(HttpSession session, HttpServletResponse response, PrintWriter writer) {
		response.contentType = "application/json"
		UploadProgressListener listener = (UploadProgressListener) session.getAttribute("ProgressListener")
		
		def resp
		try {
			if (listener) {
				if (listener.finished) {
					session.removeAttribute("ProgressListener")
				}
				resp = [
						stat: "ok",
						listenerAvailable: true,
						percentComplete: listener.percentDone,
						bytesRead: FileUtil.format(listener.bytesRead),
						contentLength: FileUtil.format(listener.contentLength),
						finished: listener.finished]
			} else {
				resp = [
						stat: "ok",
						listenerAvailable: false]
			}
		} catch (e) {
            logService.error(e)
			resp = [
					stat: "fail",
					errorCode: "general.fail",
					errorMsg: "An error occurred."]
		}
		writer.print JSONObject.fromObject(resp).toString()
	}
}
