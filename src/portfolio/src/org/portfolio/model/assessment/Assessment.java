/* $Name:  $ */
/* $Id: Assessment.java,v 1.20 2010/11/23 14:27:17 ajokela Exp $ */
package org.portfolio.model.assessment;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.portfolio.model.Comment;
import org.portfolio.model.Person;
import org.portfolio.model.PortfolioItemType;

/**
 * This class represents an assessment. The purpose of this class is to
 * encapsulate assessments assigned to shared portfolios and shared elements.
 */
public class Assessment {

    public static final Comparator<Assessment> ASSESSED_DATE_COMPARATOR = new Comparator<Assessment>() {
        public int compare(Assessment o1, Assessment o2) {
            return o2.getAssessedDate().compareTo(o1.getAssessedDate());
        }
    };

    private Integer id;
    private Integer assessedItemId;
    private PortfolioItemType assessedItemType;
    private String scoreAssignment;
    private String quantifiedScore;
    private Date assessedDate;
    private String assessmentType;
    private Person evaluator;
    private List<Comment> commentList;
    private String shareId;
    private String overallScore;
    private int overallQuantifiedScore;
  
    public Integer getId() {
        return this.id;
    }

    public Integer getAssessmentId() {
        return getId();
    }
    
    public void setId(Integer assessmentId) {
        this.id = assessmentId;
    }

    public Integer getAssessedItemId() {
        return this.assessedItemId;
    }

    public void setAssessedItemId(Integer assessedItemId) {
        this.assessedItemId = assessedItemId;
    }

    public PortfolioItemType getAssessedItemType() {
        return this.assessedItemType;
    }

    public void setAssessedItemType(PortfolioItemType assessedItemType) {
        this.assessedItemType = assessedItemType;
    }

    public String getScoreAssignment() {
        return scoreAssignment;
    }

    public void setScoreAssignment(String scoreAssignment) {
        this.scoreAssignment = scoreAssignment;
    }

    public String[] getScores() {
        return this.scoreAssignment.split(",");
    }

    public String getQuantifiedScore() {
        return quantifiedScore;
    }

    public void setQuantifiedScore(String quantifiedScore) {
        this.quantifiedScore = quantifiedScore;
    }

    public String[] getQuantifiedScores() {
        return this.quantifiedScore.split(",");
    }

    public Date getAssessedDate() {
        return this.assessedDate;
    }

    public void setAssessedDate(Date assessedDate) {
        this.assessedDate = assessedDate;
    }

    public String getAssessmentType() {
        return this.assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Person getEvaluator() {
        return this.evaluator;
    }

    public void setEvaluator(Person personId) {
        this.evaluator = personId;
    }

    public List<Comment> getCommentList() {
        return this.commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getShareId() {
        return this.shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(String overallScore) {
        this.overallScore = overallScore;
    }

    public int getOverallQuantifiedScore() {
        return overallQuantifiedScore;
    }

    public void setOverallQuantifiedScore(int overallQuantifiedScore) {
        this.overallQuantifiedScore = overallQuantifiedScore;
    }
    
    public boolean isFinal() {
        return "final".equals(getAssessmentType());
    }
}
