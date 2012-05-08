/* $Name:  $ */
/* $Id: ProfilePictureController.java,v 1.9 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller.account;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.portfolio.bus.ElementManager;
import org.portfolio.bus.PermissionsManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.portfolio.model.Person;
import org.portfolio.util.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Matt Sheehan
 * 
 */
@Controller
public class ProfilePictureController extends ApplicationController {

    @Autowired
    private StringEncryptor stringEncryptor;
    @Autowired
    private PersonHome personHome;
    @Autowired
    private ElementManager elementManager;
    @Autowired
    private PermissionsManager permissionsManager;

    @RequestMapping("/profilePicture")
    public String execute(
            @RequestParam(value = "euid", required = false) String euid,
            @RequestParam(value = "username", required = false) String username,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Person person;
        if (!RequestUtils.isEmpty(euid)) {
            person = personHome.findByPersonId(stringEncryptor.decrypt(euid));
        } else if (!RequestUtils.isEmpty(username)) {
            person = personHome.findByUsername(username);
        } else {
            person = RequestUtils.getPerson(request);
        }

        EntryKey entryKey = person.getProfilePictureKey();
        if (entryKey == null) {
            return "forward:/images/anonymous.gif";
        } else {
            permissionsManager.grantPermission(entryKey);
            return String.format("forward:/photo/%s?width=60&height=60", entryKey.getId());
        }
    }

    @RequestMapping("/profilePicture/save")
    public void save(
            @RequestParam("pictureSource") String pictureSourceParam,
            @RequestParam(value = "materialEntryId", required = false) String materialEntryIdParam,
            HttpServletRequest request,
            HttpServletResponse response) {
        Person person = RequestUtils.getPerson(request);
        EntryKey pictureKey = null;
        if (!"anonymous".equals(pictureSourceParam)) {
            pictureKey = new EntryKey(materialEntryIdParam);
        }
        person.setProfilePictureKey(pictureKey);
        personHome.store(person);
    }

    @RequestMapping("/profilePicture/window")
    public String window(HttpServletRequest request, HttpServletResponse response) {
        List<? extends ElementDataObject> profileImages = elementManager.findPhotosByPersonId(RequestUtils.getPersonId(request));
        Collections.sort(profileImages, ElementDataObject.NAME_ORDER);
        request.setAttribute("images", profileImages);
        return "account/changeProfilePictureWindow";
    }
}
