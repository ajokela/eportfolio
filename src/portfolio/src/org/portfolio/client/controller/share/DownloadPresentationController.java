/* $Id: DownloadPresentationController.java,v 1.5 2010/12/06 14:26:05 ajokela Exp $ */
package org.portfolio.client.controller.share;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.portfolio.bus.ElementManager;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.dao.PersonHome;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.dao.template.TemplateCategoryHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.UploadedMaterial;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

@Controller
public class DownloadPresentationController {
    
    private static LogService logService = new LogService(DownloadPresentationController.class);

    private static final String FO_XSL_FILE_DEFAULT = "/css/templates/pGray.fo.xsl";
    private static final List<String> generateExtPDFElements = Arrays.asList("StudentTerm", "ProfDevelopmentPS");
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");

    @Autowired
    private ShareEntryHome shareEntryHome;
    @Autowired
    private TemplateCategoryHome templateCategoryHome;
    @Autowired
    private TemplateElementMappingHome templateElementFormHome;
    @Autowired
    private PersonHome personHome;
    @Autowired
    private PortfolioManager portfolioManager;
    @Autowired
    private ElementManager elementManager;

    @RequestMapping("/portfolio/?*/download")
    public void execute(HttpServletRequest request, HttpServletResponse response, OutputStream os)
            throws Exception {
        String[] split = request.getRequestURI().split("/");
        String shareId = split[split.length - 2];
        Portfolio shareDefinition = portfolioManager.findById(shareId);
        String presentationName = removeSpecialCharacters(shareDefinition.getShareName());
        String fileNameFormat = presentationName + "-" + dateFormat.format(new Date());
        
        Person person = RequestUtils.getPerson(request);

        response.setContentType("application/zip");
        // these two header values are set to allow IE to download zip file.
        // IE errors when no-cache directive
        // is set, these headers override no-cache directive.
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=0");
        response.addHeader("Content-Disposition", String.format("attachment;filename=\"%s.zip\"", fileNameFormat));

        // get the XML data
        Document doc = createDocument(shareDefinition, person);

        // Create the ZIP file and add generted pdf to it
        ZipOutputStream zipOS = new ZipOutputStream(os);
        zipOS.putNextEntry(new ZipEntry(fileNameFormat + ".pdf"));

        // Setup XSLT
        // first generate the presentation PDF
        TransformerFactory factory = TransformerFactory.newInstance();
        FopFactory fopFactory = FopFactory.newInstance();

        addPdfToZip(request, shareDefinition, doc, zipOS, factory, fopFactory);
        zipOS.closeEntry();

        // zip all materials attached to presentation
        Map<String, UploadedMaterial> materialMap = getAllMaterials(shareDefinition, person);
        
        logService.debug("==> materialMap.size(): " + materialMap.size());
        
        Set<String> keySet = materialMap.keySet();
        if (!keySet.isEmpty()) {
            Iterator<String> keyItr = keySet.iterator();
            while (keyItr.hasNext()) {
                UploadedMaterial mat = (UploadedMaterial) materialMap.get(keyItr.next());
                
                logService.debug("mat.getFileName(): " + mat.getFileName());
                
                try {
                    InputStream inputStream = mat.getElementDefinition().getFileAccessor().getFile(mat);
                    zipOS.putNextEntry(new ZipEntry("attachments/" + mat.getFileName()));
                    IOUtils.copy(inputStream, zipOS);
                    zipOS.closeEntry();
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    // Do nothing for now
                	logService.debug("=======> " + e.getLocalizedMessage());
                }
            }
        }
        else {
        	logService.debug("=======> keySet.isEmpty()");
        }

        // find if the elements which needs external PDF are included in the
        // presentation, if yes
        // then generate PDF for them and add them to the zip file
        // to generate pdf, generate xml for the element, then get the
        // xsl-fo for that element and
        // pass it through the xslt and fop engine to output pdf

        List<TemplateCategory> headingCategories = templateCategoryHome.findTopLevelCategories(shareDefinition.getTemplateId().toString());
        for (TemplateCategory thc : headingCategories) {
            // level1 element.
            List<TemplateCategory> childCategories = templateCategoryHome.findChildren(thc.getId());
            for (TemplateCategory thc2 : childCategories) {
                // level2 element.
                List<TemplateElementMapping> elements = templateElementFormHome.findByTemplateCategoryId(thc2.getId());
                Map<String, TemplateElementMapping> tefMap = new HashMap<String, TemplateElementMapping>();
                List<ShareEntry> shareEntries = new ArrayList<ShareEntry>();
                for (TemplateElementMapping tef : elements) {
                    shareEntries.addAll(shareEntryHome.findByShareIdElementId(shareDefinition.getShareId(), tef.getId()));
                    tefMap.put(tef.getId(), tef);
                }
                for (ShareEntry entry : shareEntries) {
                    TemplateElementMapping tef = tefMap.get(entry.getElementId());

                    // generate PDF and add it to zip
                    String elementName = "";
                    if (entry.getElement() != null
                            && generateExtPDFElements.contains((elementName = entry.getElement().getShortClassName()))) {
                        ElementDataObject edo = null;
                        edo = getElement(entry, tef, shareDefinition, person);
                        if (edo != null) {
                            String instanceName = edo.getEntryName();
                            zipOS.putNextEntry(new ZipEntry(tef.getElementTitle() + "/" + instanceName + ".pdf"));
                            // Construct fop with desired output format
                            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
                            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, zipOS);
                            String extElementXSLT = "/WEB-INF/templates/view/system/download" + elementName + ".fo.xsl";

                            File extXSLTElementFile = new File(request.getSession().getServletContext().getRealPath(""), extElementXSLT);

                            // Setup XSLT
                            Transformer transformer = factory.newTransformer(new StreamSource(extXSLTElementFile));
                            transformer.setParameter("versionParam", "2.0");
                            if (elementName.equalsIgnoreCase("StudentTerm")) {
                                transformer.setParameter("termName", instanceName);
                            }
                            // Setup input for XSLT transformation
                            transformer.transform(new DOMSource(doc), new SAXResult(fop.getDefaultHandler()));
                            logService.debug("Successfully generated PDF for " + instanceName);
                            zipOS.closeEntry();
                        }
                    }
                }
            }
        }

        zipOS.close();
        os.flush();
    }

    private void addPdfToZip(
            HttpServletRequest request,
            Portfolio shareDefinition,
            Document doc,
            ZipOutputStream zipOS,
            TransformerFactory factory,
            FopFactory fopFactory) throws TransformerConfigurationException, FOPException, TransformerException {
        File xsltFile = new File(request.getSession().getServletContext().getRealPath(""), FO_XSL_FILE_DEFAULT);
        Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
        String servletPath = request.getRequestURL().toString();
        servletPath = servletPath.substring(0, servletPath.lastIndexOf("/"));
        transformer.setParameter("siloURL", servletPath);

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, zipOS);

        // Start XSLT transformation and FOP processing
        transformer.transform(new DOMSource(doc), new SAXResult(fop.getDefaultHandler()));
    }

    private ElementDataObject getElement(ShareEntry entry, TemplateElementMapping templateElement, Portfolio share, Person person) {
        ElementDataObject element = elementManager.findElementInstance(
                templateElement.getElementDefinition().getId(),
                share.getPersonId(),
                entry.getEntryIndex(), false, person.getPersonId());
        return element;
    }

    private Document createDocument(Portfolio shareDefinition, Person person) throws ParserConfigurationException, SQLException, Exception,
            MalformedURLException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        Document doc = docFactory.newDocumentBuilder().newDocument();

        Element rootElement = doc.createElement("ospiTemplate");
        doc.appendChild(rootElement);

        // locate person
        Person sharer = personHome.findByPersonId(shareDefinition.getPersonId());

        rootElement.setAttribute("description", shareDefinition.getShareDesc());
        rootElement.setAttribute("name", shareDefinition.getShareName());
        rootElement.setAttribute("dateCreated", dateFormat.format(shareDefinition.getDateCreated()));

        Element sharerElement = doc.createElement("sharer");
        sharerElement.setAttribute("sharedBy", sharer.getFullName());
        sharerElement.setAttribute("userName", sharer.getUsername());
        sharerElement.setAttribute("email", sharer.getEmail());

        rootElement.appendChild(sharerElement);

        List<TemplateCategory> headingCategories = templateCategoryHome.findTopLevelCategories(shareDefinition.getTemplateId().toString());
        for (TemplateCategory thc : headingCategories) {
            // Create level1 element.
            Element level1Heading = doc.createElement("category");
            level1Heading.setAttribute("title", thc.getTitle());

            List<TemplateCategory> childCategories = templateCategoryHome.findChildren(thc.getId());
            for (TemplateCategory thc2 : childCategories) {
                // Create level2 element.
                Element level2Heading = doc.createElement("subcategory");
                level2Heading.setAttribute("title", thc2.getTitle());

                List<TemplateElementMapping> elements = templateElementFormHome.findByTemplateCategoryId(thc2.getId());

                Map<String, TemplateElementMapping> tefMap = new HashMap<String, TemplateElementMapping>();
                List<ShareEntry> shareEntries = new ArrayList<ShareEntry>();
                for (TemplateElementMapping tef : elements) {
                    shareEntries.addAll(shareEntryHome.findByShareIdElementId(shareDefinition.getShareId(), tef.getId()));
                    tefMap.put(tef.getId(), tef);
                }
                Collections.sort(shareEntries, new Comparator<ShareEntry>() {
                    public int compare(ShareEntry o1, ShareEntry o2) {
                        return new Integer(o1.getSortOrder()).compareTo(o2.getSortOrder());
                    }
                });

                for (ShareEntry entry : shareEntries) {
                    TemplateElementMapping tef = tefMap.get(entry.getElementId());
                    ElementDataObject edo = getElement(entry, tef, shareDefinition, person);
                    if (edo != null) {
                        Element element = createElement(level2Heading, tef, edo, shareDefinition.isPublicView());
                        level2Heading.appendChild(element);
                    }
                }
                if (level2Heading.hasChildNodes()) {
                    level1Heading.appendChild(level2Heading);
                }
            }
            if (level1Heading.hasChildNodes()) {
                rootElement.appendChild(level1Heading);
            }
        }
        return doc;
    }

    private Element createElement(Element parentElement, TemplateElementMapping tef, ElementDataObject object, boolean publicView)
            throws Exception, MalformedURLException {
        String name = object.getShortClassName();

        logService.debug("adding object");
        Element objectNode = parentElement.getOwnerDocument().createElement(name);
        objectNode.setAttribute("title", tef.getElementTitle());

        try {
            addObjectNodeInfo(objectNode, object);
        } catch (IntrospectionException e) {
            logService.error("error getting element's properties", e);
        }

        // addCommentsToElement( request, object, objectNode );
        // addMaterialsToElement(object, objectNode, publicView);

        return objectNode;
    }

    private Element createChildElement(Element parentElement, ElementDataObject object) throws Exception {
        String interfaceClass = object.getClass().getName();
        String name = interfaceClass.substring(interfaceClass.lastIndexOf(".") + 1);
        logService.debug("adding object");

        Element objectNode = parentElement.getOwnerDocument().createElement(name);
        try {
            addObjectNodeInfo(objectNode, object);
        } catch (IntrospectionException e) {
            logService.error("error getting element's properties", e);
        }

        return objectNode;
    }

    private void addObjectNodeInfo(Element parentNode, Object object) throws IntrospectionException {
        // go through each property... put each one in...

        BeanInfo info = Introspector.getBeanInfo(object.getClass());
        PropertyDescriptor[] props = info.getPropertyDescriptors();

        for (int i = 0; i < props.length; i++) {
            String attribName = props[i].getName();
            if (attribName.equals("metaData") || attribName.equals("class")) {
                continue;
            }

            Method readMethod = props[i].getReadMethod();
            if (readMethod == null || readMethod.getParameterTypes().length > 0) {
                continue;
            }

            Element attribute = parentNode.getOwnerDocument().createElement(attribName);
            Object attribValue = null;

            try {
                Object[] o = null;
                attribValue = readMethod.invoke(object, o);
            } catch (IllegalAccessException e) {
                logService.error("could not get attribute", e);
            } catch (InvocationTargetException e) {
                logService.error("could not get attribute", e);
            }

            if (attribValue != null && attribValue.toString().length() > 0 && !(attribValue instanceof Person)) {
                Element value = parentNode.getOwnerDocument().createElement("value");
                Text valueText = parentNode.getOwnerDocument().createTextNode(stripNonValidXMLCharacters(attribValue.toString()));
                value.appendChild(valueText);
                attribute.appendChild(value);
            }

            String readMethodName = props[i].getReadMethod().getName();
            // If element has child element then add them to the xml e.g
            // StudentTerm has child elements StudentTermRecord
            if (readMethodName.equalsIgnoreCase("getRecordsList")) {
                if (attribValue != null && attribValue instanceof List) {
                    List<?> childElements = (List<?>) attribValue;
                    for (Object childObject : childElements) {
                        if (childObject instanceof ElementDataObject) {
                            try {
                                Element recordItem = attribute.getOwnerDocument().createElement("recordItem");
                                Element childElement = createChildElement(recordItem, (ElementDataObject) childObject);
                                recordItem.appendChild(childElement);
                                attribute.appendChild(recordItem);
                            } catch (Exception e) {
                                logService.error("Error in adding child element " + childObject + " to parent- " + attribName, e);
                            }
                        }

                    }
                }
            }

            parentNode.appendChild(attribute);
        }
    }

    // private void addMaterialsToElement(ElementDataObject object, Element parentElement, boolean publicView) throws Exception {
    // List<UploadedMaterial> materialsList = object.getMaterials();
    //
    // if (!materialsList.isEmpty()) {
    //
    // Element elementMaterials = parentElement.getOwnerDocument().createElement("elementMaterials");
    // for (UploadedMaterial material : materialsList) {
    // Element newNode;
    // if (material.isImage()) {
    // newNode = parentElement.getOwnerDocument().createElement("image");
    // } else {
    // newNode = parentElement.getOwnerDocument().createElement("file");
    // }
    // addObjectNodeInfo(newNode, material);
    // elementMaterials.appendChild(newNode);
    // }
    // parentElement.appendChild(elementMaterials);
    // }
    // }

    private Map<String, UploadedMaterial> getAllMaterials(Portfolio shareDefinition, Person person) throws Exception {
        Map<String, UploadedMaterial> materialsMap = new HashMap<String, UploadedMaterial>();
        List<TemplateCategory> headingCategories = templateCategoryHome.findTopLevelCategories(shareDefinition.getTemplateId().toString());
        for (TemplateCategory thc : headingCategories) {
            // level1 element.
            List<TemplateCategory> childCategories = templateCategoryHome.findChildren(thc.getId());
            for (TemplateCategory thc2 : childCategories) {
                // level2 element.
                List<TemplateElementMapping> elements = templateElementFormHome.findByTemplateCategoryId(thc2.getId());

                Map<String, TemplateElementMapping> tefMap = new HashMap<String, TemplateElementMapping>();
                List<ShareEntry> shareEntries = new ArrayList<ShareEntry>();
                for (TemplateElementMapping tef : elements) {
                    shareEntries.addAll(shareEntryHome.findByShareIdElementId(shareDefinition.getShareId(), tef.getId()));
                    tefMap.put(tef.getId(), tef);
                }
                for (ShareEntry entry : shareEntries) {
                    TemplateElementMapping tef = tefMap.get(entry.getElementId());
                    ElementDataObject edo = getElement(entry, tef, shareDefinition, person);
                    if (edo != null) {
                    	
                    	if(edo.isMaterial()) {
                    		materialsMap.put(edo.getEntryId().toString(), (UploadedMaterial)edo);
                    	}
                    	
                    	if (edo.getAttachments().size() > 0 ) {
	                    	
	                        List<? extends ElementDataObject> materialsList = edo.getAttachments();
	                        
	                        for (ElementDataObject mat : materialsList) {
	                        	
	                        	if(mat.isMaterial()) {
	                        		materialsMap.put(mat.getEntryId().toString(), (UploadedMaterial)mat);
	                        	}
	                        }
                    	}
                    }
                }
            }
        }
        return materialsMap;
    }

    /**
     * This method ensures that the output String has only valid XML unicode characters as specified by the XML 1.0 standard. For reference,
     * please see <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the standard</a>. This method will return an empty String if
     * the input is null or empty.
     * 
     * http://cse-mjmcl.cse.bris.ac.uk/blog/2007/02/14/1171465494443.html
     * 
     * @param in The String whose non-valid characters we want to remove.
     * @return The in String, stripped of non-valid characters.
     */
    private String stripNonValidXMLCharacters(String in) {
        StringBuilder out = new StringBuilder(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in)))
            return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught
            // here; it should not happen.
            if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }

    /**
     * removes characters which are not allowed in file name on different OS
     */
    private String removeSpecialCharacters(String input) {
        return input.replaceAll("\\|/|:|\\*|\\?|\"|<|>|\\|", "");
    }
}
