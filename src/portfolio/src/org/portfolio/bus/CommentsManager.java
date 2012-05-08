/* $Name:  $ */
/* $Id: CommentsManager.java,v 1.13 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.model.AdviserComment;
import org.portfolio.model.Comment;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.assessment.Assessment;

/**
 * @author Matt Sheehan
 * 
 */
public interface CommentsManager {

    public List<Comment> getCommentList(Portfolio shareDefinition, String currentPersonId);

    public List<Comment> getCommentList(Assessment assessment, String currentPersonId);

    public List<Comment> getCommentList(ElementDataObject elementDataObject, String currentPersonId);

    public List<Comment> getCommentList(ShareEntry shareEntry, String currentPersonId);

    public List<Comment> getCommentsByCreator(String currentPersonId);

    public List<Comment> getCommentsByOwner(String currentPersonId);

    public Comment getComment(String commentId);

    public void insert(Comment commDef);

    public void deleteComment(String commentId);

    public void update(Comment comment);

    List<AdviserComment> getAdviserCommentsByAdvisee(String adviseeId);

    void insertAdviserComment(AdviserComment comment);

    void updateAdviserComment(AdviserComment comment);

    void deleteAdviserComment(int commentId);

    AdviserComment getAdviserCommentById(int id);
}
