/* $Name:  $ */
/* $Id: AttachController.java,v 1.5 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.controller.element;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.portfolio.bus.AttachmentManager;
import org.portfolio.bus.ElementManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.portfolio.model.Person;
import org.portfolio.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AttachController {

    @Autowired
    private ElementManager elementManager;
    @Autowired
    private AttachmentManager attachmentManager;

    @RequestMapping("/element/attach/link/list")
    public String listLinks(@RequestParam("entry") EntryKey entry, HttpServletRequest request, Model model) {
        Person person = RequestUtils.getPerson(request);
        
		List<? extends ElementDataObject> allLinks = elementManager.findLinksByPersonId(person.getPersonId());
        List<? extends ElementDataObject> attachedLinks = elementManager.findElementInstance(entry, person).getLinkAttachments();
        List<? extends ElementDataObject> attachableLinks = new ArrayList<ElementDataObject>();
        
        if(allLinks instanceof List<?>) {
        	attachableLinks = CollectionUtil.subtract(allLinks, attachedLinks);
        }
        
        if(attachableLinks != null) {
        	Collections.sort(attachableLinks, ElementDataObject.NAME_ORDER);
        }
        
        model.addAttribute("links", attachableLinks);
        return "element/listLinks";
    }

    @RequestMapping("/element/attach/photo/list")
    public String listPhotos(@RequestParam("entry") EntryKey entry, HttpServletRequest request, Model model) {
        Person person = RequestUtils.getPerson(request);
        @SuppressWarnings("unchecked")
		List<? extends ElementDataObject> allPhotos = (List<ElementDataObject>) elementManager.findPhotosByPersonId(person.getPersonId());
        List<? extends ElementDataObject> attachedPhotos = elementManager.findElementInstance(entry, person).getPhotoAttachments();
        List<? extends ElementDataObject> attachablePhotos = CollectionUtil.subtract(allPhotos, attachedPhotos);
        Collections.sort(attachablePhotos, ElementDataObject.NAME_ORDER);
        model.addAttribute("photos", attachablePhotos);
        return "element/listPhotos";
    }

    @RequestMapping("/element/attach/file/list")
    public String listFiles(@RequestParam("entry") EntryKey entry, HttpServletRequest request, Model model) {
        Person person = RequestUtils.getPerson(request);
        @SuppressWarnings("unchecked")
		List<? extends ElementDataObject> allFiles = (List<ElementDataObject>) elementManager.findFilesByPersonId(person.getPersonId());
        List<? extends ElementDataObject> attachedFiles = elementManager.findElementInstance(entry, person).getFileAttachments();
        List<? extends ElementDataObject> attachableFiles = CollectionUtil.subtract(allFiles, attachedFiles);
        Collections.sort(attachableFiles, ElementDataObject.NAME_ORDER);
        model.addAttribute("files", attachableFiles);
        return "element/listFiles";
    }

    @RequestMapping("/element/attach")
    public void attach(
            @RequestParam("attachment") EntryKey[] attachments,
            @RequestParam("entry") EntryKey entry,
            HttpServletRequest request,
            PrintWriter writer) {
        
    	// Person person = RequestUtils.getPerson(request);
        attachmentManager.addAttachments(entry, attachments);
        // TODO
    }

    @RequestMapping("/element/detach")
    public void detach(
            @RequestParam("entry") EntryKey entry,
            @RequestParam("attachment") EntryKey attachment,
            HttpServletRequest request,
            PrintWriter writer) {
        // Person person = RequestUtils.getPerson(request);
        attachmentManager.detach(entry, attachment);
    }
}
