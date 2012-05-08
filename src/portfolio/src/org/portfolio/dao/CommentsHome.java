/* $Name:  $ */
/* $Id: CommentsHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.model.Comment;

/**
 * @author Matt Sheehan
 * 
 */
public interface CommentsHome {

    public void update(Comment comments);

    public void insert(Comment comments);

    void remove(Comment data);

    void deleteAll(Comment comments);

    List<Comment> findXMostRecentCommentsRecievedByUser(String userId, int x);

    List<Comment> findXMostRecentCommentsSentByUser(String userId, int x);

    Comment findByCommentId(String commentId);

    List<Comment> findAllByElement(String personId, BigDecimal entryId, String className, boolean isElementOwner);

    List<Comment> findAllByOwner(String ownerId);

    List<Comment> findByPersonId(String personId);

}
