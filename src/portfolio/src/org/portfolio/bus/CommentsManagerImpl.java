/* $Name:  $ */
/* $Id: CommentsManagerImpl.java,v 1.17 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.dao.AdviserCommentHome;
import org.portfolio.dao.CommentsHome;
import org.portfolio.model.AdviserComment;
import org.portfolio.model.Comment;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.assessment.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The attempt has been made to make this generic for commenting on all data
 * types (including instances of elements). There may need to be special cases
 * added in when table data being commented on does not have a(n) entry_id
 * column.
 */

@Service("commentsManager")
public class CommentsManagerImpl implements CommentsManager {

    @Autowired
    private CommentsHome commentsHome;
    @Autowired
    private PortfolioManager portfolioManager;
    @Autowired
    private AdviserCommentHome adviserCommentHome;

    public List<Comment> getCommentList(Portfolio shareDefinition, String currentPersonId) {
        BigDecimal entryId = new BigDecimal(shareDefinition.getShareId());
        String personId = shareDefinition.getPersonId();
        String className = Portfolio.class.getName();
        return commentsHome.findAllByElement(currentPersonId, entryId, className, personId.equals(currentPersonId));
    }

    public List<Comment> getCommentList(Assessment assessment, String currentPersonId) {
        BigDecimal entryId = new BigDecimal(assessment.getId());
        String className = Assessment.class.getName();
        Portfolio portfolio = portfolioManager.findById(assessment.getShareId());
        return commentsHome.findAllByElement(currentPersonId, entryId, className, portfolio.getPersonId().equals(currentPersonId));
    }

    public List<Comment> getCommentList(ShareEntry shareEntry, String currentPersonId) {
        BigDecimal entryId = new BigDecimal(shareEntry.getId());
        String className = ShareEntry.class.getName();
        Portfolio portfolio = portfolioManager.findById(shareEntry.getShareId());
        return commentsHome.findAllByElement(currentPersonId, entryId, className, portfolio.getPersonId().equals(currentPersonId));
    }

    public List<Comment> getCommentList(ElementDataObject elementDataObject, String currentPersonId) {
        BigDecimal entryId = elementDataObject.getEntryId();
        String personId = elementDataObject.getPersonId();
        String className = elementDataObject.getClass().getName();

        // there may be things we are attaching to that
        if (entryId == null)
            entryId = BigDecimal.ZERO;

        return commentsHome.findAllByElement(currentPersonId, entryId, className, personId.equals(currentPersonId));
    }

    public List<Comment> getCommentsByCreator(String currentPersonId) {
        return commentsHome.findByPersonId(currentPersonId);
    }

    public List<Comment> getCommentsByOwner(String currentPersonId) {
        return commentsHome.findAllByOwner(currentPersonId);
    }

    public void insert(Comment commDef) {
        commentsHome.insert(commDef);
    }

    public void deleteComment(String commentId) {
        Comment commDef = new Comment();
        commDef.setCommentId(Long.parseLong(commentId));
        commentsHome.remove(commDef);
    }

    public void update(Comment comment) {
        commentsHome.update(comment);
    }

    public Comment getComment(String commentId) {
        return commentsHome.findByCommentId(commentId);
    }

    public void deleteAdviserComment(int commentId) {
        adviserCommentHome.delete(commentId);
    }

    public List<AdviserComment> getAdviserCommentsByAdvisee(String adviseeId) {
        return adviserCommentHome.findByAdvisee(adviseeId);
    }

    public void insertAdviserComment(AdviserComment comment) {
        adviserCommentHome.insert(comment);
    }

    public void updateAdviserComment(AdviserComment comment) {
        adviserCommentHome.update(comment);
    }

    public AdviserComment getAdviserCommentById(int id) {
        return adviserCommentHome.findById(id);
    }
}
