/* $Name:  $ */
/* $Id: AdviserCommentController.java,v 1.5 2011/01/18 20:30:06 ajokela Exp $ */
package org.portfolio.client.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.portfolio.bus.CommentsManager;
import org.portfolio.bus.ViewerSearch;
import org.portfolio.client.RequestUtils;
import org.portfolio.model.AdviserComment;
import org.portfolio.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdviserCommentController extends ApplicationController {

    @Autowired
    private ViewerSearch viewerSearch;
    @Autowired
    private CommentsManager commentsManager;

    @RequestMapping("/adviser/comment/view/{username}")
    public String view(@PathVariable("username") String username, HttpServletRequest request) {
        return showView(request, username);
    }

    private String showView(HttpServletRequest request, String username) {
        Person advisee = viewerSearch.findMemberViewer(username);
        List<AdviserComment> comments = commentsManager.getAdviserCommentsByAdvisee(advisee.getPersonId());
        Collections.sort(comments, new Comparator<AdviserComment>() {
            public int compare(AdviserComment o1, AdviserComment o2) {
                return o1.getDateCreated().compareTo(o2.getDateCreated());
            }
        });
        request.setAttribute("comments", comments);
        request.setAttribute("advisee", advisee);
        return "adviserComments";
    }

    public String edit(HttpServletRequest request, HttpServletResponse response) {
        Person person = RequestUtils.getPerson(request);

        int id = Integer.parseInt(request.getParameter("id"));
        AdviserComment comment = commentsManager.getAdviserCommentById(id);

        if (!comment.getAdviser().equals(person)) {
            throw new SecurityException("no access");
        }

        comment.setText(request.getParameter("text"));
        commentsManager.updateAdviserComment(comment);

        return showView(request, comment.getAdvisee().getUsername());
    }

    @RequestMapping("/adviser/comment/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {
        Person person = RequestUtils.getPerson(request);
        AdviserComment comment = commentsManager.getAdviserCommentById(id);

        if (!comment.getAdviser().equals(person)) {
            throw new SecurityException("no access");
        }

        commentsManager.deleteAdviserComment(id);
        return showView(request, comment.getAdvisee().getUsername());
    }

    @RequestMapping("/adviser/comment/add")
    public String add(@RequestParam("text") String text, @RequestParam("adviseeUsername") String username, HttpServletRequest request) {
        Person person = RequestUtils.getPerson(request);
        Person advisee = viewerSearch.findMemberViewer(username);

        if (!RequestUtils.getAdviseeList(request).contains(advisee)) {
            throw new SecurityException("target is not your advisee");
        }

        if (text != null && text.length() > 0) {
	        
	        AdviserComment comment = new AdviserComment();
	        comment.setAdvisee(advisee);
	        comment.setAdviser(person);
	        comment.setText(text);
	
	        commentsManager.insertAdviserComment(comment);
        
        }
        
        return showView(request, username);
    }
}
