/* $Name:  $ */
/* $Id: CommunityRoleAction.java,v 1.14 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.action.community;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.ViewerSearch;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.util.StringEncryptor;




/**
 * @author Matt Sheehan
 * 
 */
public class CommunityRoleAction extends DispatchAction {

    private CommunityManager communityManager;
    private ViewerSearch viewerSearch;
    private StringEncryptor stringEncryptor;
    private CommunityAuthorizationManager communityAuthorizationManager;

    private static final DateFormat dateFormat = new SimpleDateFormat("MMddyy_HHmmss");
    
    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);

        List<Person> communityCoordinators = communityManager.getMembers(communityIdParam, CommunityRoleType.COMMUNITY_COORDINATOR);
        List<Person> assessmentCoordinators = communityManager.getMembers(communityIdParam, CommunityRoleType.ASSESSMENT_COORDINATOR);
        List<Person> evaluators = communityManager.getMembers(communityIdParam, CommunityRoleType.EVALUATOR);
        List<Person> members = communityManager.getMembers(communityIdParam, CommunityRoleType.MEMBER);

        request.setAttribute("communityCoordinators", communityCoordinators);
        request.setAttribute("assessmentCoordinators", assessmentCoordinators);
        request.setAttribute("evaluators", evaluators);
        request.setAttribute("members", members);

        request.setAttribute("community", community);
        return mapping.findForward("success");
    }

    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        String roleTypeParam = request.getParameter("roleType");

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), Integer.parseInt(communityIdParam));

        Person person;
        if (!isEmpty(request.getParameter("addUser"))) {
            String userIdParam = request.getParameter("userId");
            person = viewerSearch.findMemberViewer(userIdParam);
        } else {
            String guestEmailParam = request.getParameter("guestEmail");
            person = viewerSearch.findGuestViewer(guestEmailParam);
        }

        if (person == null) {
            addError(request, "The user could not be found.");
        } else {
            communityManager.addRoleAssignment(communityIdParam, person.getPersonId(), CommunityRoleType.getByCode(roleTypeParam));
        }
        return new ActionRedirect(mapping.findForward("add")).addParameter("communityId", communityIdParam);
    }

    @SuppressWarnings("unchecked")
    private void addError(HttpServletRequest request, String string) {
        List<String> epfErrors = (List<String>) request.getSession().getAttribute("epfErrors");
        if (epfErrors == null) {
            epfErrors = new ArrayList<String>();
            request.getSession().setAttribute("epfErrors", epfErrors);
        }
        epfErrors.add(string);
    }

    public ActionForward export(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        String roleTypeParam = request.getParameter("roleType");
        String longRoleString;
                
        List<Person> peoples;
        
        Community community = communityManager.getCommunityById(communityIdParam);
        
        if (roleTypeParam.compareTo("ac") == 0) {
        	longRoleString = "AssessmentCoordinators";
        	peoples = communityManager.getMembers(communityIdParam, CommunityRoleType.ASSESSMENT_COORDINATOR);
        }
        else if (roleTypeParam.compareTo("cc") == 0) {
        	longRoleString = "CommunityCoordinators";
        	peoples = communityManager.getMembers(communityIdParam, CommunityRoleType.COMMUNITY_COORDINATOR);
        }
        else if (roleTypeParam.compareTo("ev") == 0) {
        	longRoleString = "Evaluators";
        	peoples = communityManager.getMembers(communityIdParam, CommunityRoleType.EVALUATOR);
        }
        else /* (roleTypeParam.compareTo("me") == 0) */ {
        	longRoleString = "Members";
        	peoples = communityManager.getMembers(communityIdParam, CommunityRoleType.MEMBER);
        }
        
        response.setContentType("application/vnd.ms-excel");
        String fileName = "CommunityRosterFor" + longRoleString + "Of" + community.getName() + "_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

        Workbook wb = createReport(peoples, community);

        wb.write(response.getOutputStream());
        return null;
        
    }

    private Workbook createReport(List<Person> peoples, Community community) {
    	
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Font bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);

        int rowIndex = 0;
                
        
        {
            Row row = sheet.createRow(rowIndex++);
            HSSFRichTextString header1 = new HSSFRichTextString("Name");
            header1.applyFont(bold);
            row.createCell(0).setCellValue(header1);
            HSSFRichTextString header2 = new HSSFRichTextString("Email");
            header2.applyFont(bold);
            row.createCell(1).setCellValue(header2);
        }

        Iterator<Person> it = peoples.iterator();
        
        while(it.hasNext()) {
        	
        	Person person = (Person)it.next();
        	
            {
                Row row = sheet.createRow(rowIndex++);
                HSSFRichTextString rts0 = new HSSFRichTextString(person.getFullName());
                HSSFRichTextString rts1 = new HSSFRichTextString(person.getEmail());
                
                row.createCell(0).setCellValue(rts0);
                row.createCell(1).setCellValue(rts1);
                
            }
        	
        	
        }
        
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        return wb;
    }    
    
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		    throws Exception {
		String communityIdParam = request.getParameter("communityId");
		String roleTypeParam = request.getParameter("roleType");
		String euidParam = request.getParameter("euid");
		String personId = stringEncryptor.decrypt(euidParam);
		int communityId = Integer.parseInt(communityIdParam);
		
		CommunityRoleType roleType = CommunityRoleType.getByCode(roleTypeParam);
		Person person = getPerson(request);
		switch (roleType) {
		    case ASSESSMENT_COORDINATOR:
		        if (!person.isAdmin()) {
		            throw new SecurityException("must be admin to delete role assignments.");
		        }
		        break;
		    default:
		        communityAuthorizationManager.verifyCommunityCoordinatorAccess(person, communityId);
		        break;
		}
		
		communityManager.removeRoleAssignment(communityIdParam, personId, roleType);
		return new ActionRedirect(mapping.findForward("delete")).addParameter("communityId", communityIdParam);
	}
	
    
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    public void setViewerSearch(ViewerSearch viewerSearch) {
        this.viewerSearch = viewerSearch;
    }

    public void setStringEncryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }

}
