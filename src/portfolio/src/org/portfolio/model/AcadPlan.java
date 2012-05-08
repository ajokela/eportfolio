/* $Name:  $ */
/* $Id: AcadPlan.java,v 1.14 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/AcadPlan.java,v 1.14 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.14 $
 * $Date: 2010/10/27 19:24:57 $
 *
 * ============================================================================
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 * copy of the License at http://www.theospi.org/.
 * 
 * Software distributed under the License is distributed on an AS IS basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.  See the License
 * for the specific language governing rights and limitations under the
 * License.
 * 
 * Copyrights:
 * 
 * Portions created by or assigned to The University of Minnesota are Copyright
 * (c) 2003 The University of Minnesota.  All Rights Reserved.  Contact
 * information for OSPI is available at http://www.theospi.org/.
 * 
 * Portions Copyright (c) 2003 the r-smart group, inc.
 * 
 * Portions Copyright (c) 2003 The University of Delaware.
 * 
 * Acknowledgements
 * 
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
 */

package org.portfolio.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates AcadPlan element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	0.0
 * @version	$Revision: 1.14 $ $Date: 2010/10/27 19:24:57 $
 */
public class AcadPlan extends ElementBase {

    private static final long serialVersionUID = -1794914281231630414L;

    private String class1;
    private String subject1;
    private String course1;
    private String title1;
    private String credits1;
    private String class2;
    private String subject2;
    private String course2;
    private String title2;
    private String credits2;
    private String class3;
    private String subject3;
    private String course3;
    private String title3;
    private String credits3;
    private String class4;
    private String subject4;
    private String course4;
    private String title4;
    private String credits4;
    private String class5;
    private String subject5;
    private String course5;
    private String title5;
    private String credits5;
    private String class6;
    private String subject6;
    private String course6;
    private String title6;
    private String credits6;
    private String class7;
    private String subject7;
    private String course7;
    private String title7;
    private String credits7;
    private String comments;
    
    public String getClass1() {
        return (class1 != null ? class1 : EMPTY_STRING);
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public String getSubject1() {
        return (subject1 != null ? subject1 : EMPTY_STRING);
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getCourse1() {
        return (course1 != null ? course1 : EMPTY_STRING);
    }

    public void setCourse1(String course1) {
        this.course1 = course1;
    }

    public String getTitle1() {
        return (title1 != null ? title1 : EMPTY_STRING);
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getCredits1() {
        return (credits1 != null ? credits1 : EMPTY_STRING);
    }

    public void setCredits1(String credits1) {
        this.credits1 = credits1;
    }

    public String getClass2() {
        return (class2 != null ? class2 : EMPTY_STRING);
    }

    public void setClass2(String class2) {
        this.class2 = class2;
    }

    public String getSubject2() {
        return (subject2 != null ? subject2 : EMPTY_STRING);
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getCourse2() {
        return (course2 != null ? course2 : EMPTY_STRING);
    }

    public void setCourse2(String course2) {
        this.course2 = course2;
    }

    public String getTitle2() {
        return (title2 != null ? title2 : EMPTY_STRING);
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getCredits2() {
        return (credits2 != null ? credits2 : EMPTY_STRING);
    }

    public void setCredits2(String credits2) {
        this.credits2 = credits2;
    }

    public String getClass3() {
        return (class3 != null ? class3 : EMPTY_STRING);
    }

    public void setClass3(String class3) {
        this.class3 = class3;
    }

    public String getSubject3() {
        return (subject3 != null ? subject3 : EMPTY_STRING);
    }

    public void setSubject3(String subject3) {
        this.subject3 = subject3;
    }

    public String getCourse3() {
        return (course3 != null ? course3 : EMPTY_STRING);
    }

    public void setCourse3(String course3) {
        this.course3 = course3;
    }

    public String getTitle3() {
        return (title3 != null ? title3 : EMPTY_STRING);
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getCredits3() {
        return (credits3 != null ? credits3 : EMPTY_STRING);
    }

    public void setCredits3(String credits3) {
        this.credits3 = credits3;
    }

    public String getClass4() {
        return (class4 != null ? class4 : EMPTY_STRING);
    }

    public void setClass4(String class4) {
        this.class4 = class4;
    }

    public String getSubject4() {
        return (subject4 != null ? subject4 : EMPTY_STRING);
    }

    public void setSubject4(String subject4) {
        this.subject4 = subject4;
    }

    public String getCourse4() {
        return (course4 != null ? course4 : EMPTY_STRING);
    }

    public void setCourse4(String course4) {
        this.course4 =  course4;
    }

    public String getTitle4() {
        return (title4 != null ? title4 : EMPTY_STRING);
    }

    public void setTitle4(String title4) {
        this.title4 = title4;
    }

    public String getCredits4() {
        return (credits4 != null ? credits4 : EMPTY_STRING);
    }

    public void setCredits4(String credits4) {
        this.credits4 = credits4;
    }

    public String getClass5() {
        return (class5 != null ? class5 : EMPTY_STRING);
    }

    public void setClass5(String class5) {
        this.class5 = class5;
    }

    public String getSubject5() {
        return (subject5 != null ? subject5 : EMPTY_STRING);
    }

    public void setSubject5(String subject5) {
        this.subject5 = subject5;
    }

    public String getCourse5() {
        return (course5 != null ? course5 : EMPTY_STRING);
    }

    public void setCourse5(String course5) {
        this.course5 = course5;
    }

    public String getTitle5() {
        return (title5 != null ? title5 : EMPTY_STRING);
    }

    public void setTitle5(String title5) {
        this.title5 = title5;
    }

    public String getCredits5() {
        return (credits5 != null ? credits5 : EMPTY_STRING);
    }

    public void setCredits5(String credits5) {
        this.credits5 = credits5;
    }

    public String getClass6() {
        return (class6 != null ? class6 : EMPTY_STRING);
    }

    public void setClass6(String class6) {
        this.class6 = class6;
    }

    public String getSubject6() {
        return (subject6 != null ? subject6 : EMPTY_STRING);
    }

    public void setSubject6(String subject6) {
        this.subject6 = subject6;
    }

    public String getCourse6() {
        return (course6 != null ? course6 : EMPTY_STRING);
    }

    public void setCourse6(String course6) {
        this.course6 = course6;
    }

    public String getTitle6() {
        return (title6 != null ? title6 : EMPTY_STRING);
    }

    public void setTitle6(String title6) {
        this.title6 = title6;
    }

    public String getCredits6() {
        return (credits6 != null ? credits6 : EMPTY_STRING);
    }

    public void setCredits6(String credits6) {
        this.credits6 = credits6;
    }

    public String getClass7() {
        return (class7 != null ? class7 : EMPTY_STRING);
    }

    public void setClass7(String class7) {
        this.class7 = class7;
    }

    public String getSubject7() {
        return (subject7 != null ? subject7 : EMPTY_STRING);
    }

    public void setSubject7(String subject7) {
        this.subject7 = subject7;
    }

    public String getCourse7() {
        return (course7 != null ? course7 : EMPTY_STRING);
    }

    public void setCourse7(String course7) {
        this.course7 = course7;
    }

    public String getTitle7() {
        return (title7 != null ? title7 : EMPTY_STRING);
    }

    public void setTitle7(String title7) {
        this.title7 =  title7;
    }

    public String getCredits7() {
        return (credits7 != null ? credits7 : EMPTY_STRING);
    }

    public void setCredits7(String credits7) {
        this.credits7 = credits7;
    }

    public String getComments() {
        return (comments != null ? comments : EMPTY_STRING);
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Term"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage( "error.lengthTooLong", "Term", PortfolioConstants.FIFTY_CHARS_DESC ));
        }
        if(class1 != null && class1.trim().length() > PortfolioConstants.FIVE_CHARS) {
            class1 = class1.trim().substring(0, PortfolioConstants.FIVE_CHARS);
            errors.add("class1" , new ActionMessage("error.lengthTooLong", "Class Number 1" , PortfolioConstants.FIVE_CHARS_DESC ));
        }
        if(subject1 != null && subject1.trim().length() > PortfolioConstants.FOUR_CHARS) {
            subject1 = subject1.trim().substring(0, PortfolioConstants.FOUR_CHARS);
            errors.add( "subject1", new ActionMessage( "error.lengthTooLong", "Subject 1" , PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if(course1 != null && course1.trim().length() > PortfolioConstants.FOUR_CHARS) {
            course1 = course1.trim().substring(0, PortfolioConstants.FOUR_CHARS);
            errors.add( "course1", new ActionMessage( "error.lengthTooLong", "Course Number 1", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if(title1 != null && title1.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS) {
            title1 = title1.trim().substring(0, PortfolioConstants.THIRTY_FIVE_CHARS);
            errors.add( "title1", new ActionMessage( "error.lengthTooLong", "Class Title 1", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        if(credits1 != null && credits1.trim().length() > PortfolioConstants.FOUR_CHARS) {
            credits1 = credits1.trim().substring(0, PortfolioConstants.FOUR_CHARS);
            errors.add( "credits1", new ActionMessage( "error.lengthTooLong", "Credits 1", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( class2 != null && class2.trim().length() > PortfolioConstants.FIVE_CHARS ) {
            class2 = class2.trim().substring( 0, PortfolioConstants.FIVE_CHARS );
            errors.add( "class2", new ActionMessage( "error.lengthTooLong", "Class Number 2", PortfolioConstants.FIVE_CHARS_DESC ) );
        }
        if ( subject2 != null && subject2.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            subject2 = subject2.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "subject2", new ActionMessage( "error.lengthTooLong", "Subject 2", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( course2 != null && course2.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            course2 = course2.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "course2", new ActionMessage( "error.lengthTooLong", "Course Number 2", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( title2 != null && title2.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            title2 = title2.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "title2", new ActionMessage( "error.lengthTooLong", "Class Title 2", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        if ( credits2 != null && credits2.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            credits2 = credits2.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "credits2", new ActionMessage( "error.lengthTooLong", "Credits 2 ", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( class3 != null && class3.trim().length() > PortfolioConstants.FIVE_CHARS ) {
            class3 = class3.trim().substring( 0, PortfolioConstants.FIVE_CHARS );
            errors.add( "class3", new ActionMessage( "error.lengthTooLong", "Class Number 3", PortfolioConstants.FIVE_CHARS_DESC ) );
        }
        if ( subject3 != null && subject3.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            subject3 = subject3.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "subject3", new ActionMessage( "error.lengthTooLong", "Subject 3", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( course3 != null && course3.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            course3 = course3.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "course3", new ActionMessage( "error.lengthTooLong", "Course Number 3", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( title3 != null && title3.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            title3 = title3.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "title3", new ActionMessage( "error.lengthTooLong", "Class Title 3", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        if ( credits3 != null && credits3.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            credits3 = credits3.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "credits3", new ActionMessage( "error.lengthTooLong","Credits 3", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( class4 != null && class4.trim().length() > PortfolioConstants.FIVE_CHARS ) {
            class4 = class4.trim().substring( 0, PortfolioConstants.FIVE_CHARS );
            errors.add( "class4", new ActionMessage( "error.lengthTooLong", "Class Number 4", PortfolioConstants.FIVE_CHARS_DESC ) );
        }
        if ( subject4 != null && subject4.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            subject4 = subject4.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "subject4", new ActionMessage( "error.lengthTooLong", "Subject 4", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( course4 != null && course4.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            course4 = course4.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "course4", new ActionMessage( "error.lengthTooLong", "Course Number 4", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( title4 != null && title4.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            title4 = title4.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "title4", new ActionMessage( "error.lengthTooLong", "Class Title 4", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        if ( credits4 != null && credits4.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            credits4 = credits4.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "credits4", new ActionMessage( "error.lengthTooLong", "Credits 4", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( class5 != null && class5.trim().length() > PortfolioConstants.FIVE_CHARS ) {
            class5 = class5.trim().substring( 0, PortfolioConstants.FIVE_CHARS );
            errors.add( "class5", new ActionMessage( "error.lengthTooLong", "Class Number 5", PortfolioConstants.FIVE_CHARS_DESC ) );
        }
        if ( subject5 != null && subject5.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            subject5 = subject5.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "subject5", new ActionMessage( "error.lengthTooLong", "Subject 5", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( course5 != null && course5.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            course5 = course5.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "course5", new ActionMessage( "error.lengthTooLong", "Course Number 5", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( title5 != null && title5.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            title5 = title5.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "title5", new ActionMessage( "error.lengthTooLong", "Class Title 5", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        if ( credits5 != null && credits5.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            credits5 = credits5.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "credits5", new ActionMessage( "error.lengthTooLong", "Credits 5", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( class6 != null && class6.trim().length() > PortfolioConstants.FIVE_CHARS ) {
            class6 = class6.trim().substring( 0, PortfolioConstants.FIVE_CHARS );
            errors.add( "class6", new ActionMessage( "error.lengthTooLong", "Class Number 6", PortfolioConstants.FIVE_CHARS_DESC ) );
        }
        if ( subject6 != null && subject6.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            subject6 = subject6.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "subject6", new ActionMessage( "error.lengthTooLong", "Subject 6", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        if ( course6 != null && course6.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            course6 = course6.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "course6", new ActionMessage( "error.lengthTooLong", "Course Number 6", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        
        if ( title6 != null && title6.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            title6 = title6.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "title6", new ActionMessage( "error.lengthTooLong", "Class Title 6", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        
        if ( credits6 != null && credits6.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            credits6 = credits6.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "credits6", new ActionMessage( "error.lengthTooLong", "Credits 6", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        
        if ( class7 != null && class7.trim().length() > PortfolioConstants.FIVE_CHARS ) {
            class7 = class7.trim().substring( 0, PortfolioConstants.FIVE_CHARS );
            errors.add( "class7", new ActionMessage( "error.lengthTooLong", "Class Number 7", PortfolioConstants.FIVE_CHARS_DESC ) );
        }
        
        if ( subject7 != null && subject7.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            subject7 = subject7.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "subjec7", new ActionMessage( "error.lengthTooLong", "Subject 7", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        
        if ( course7 != null && course7.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            course7 = course7.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "course7", new ActionMessage( "error.lengthTooLong", "Course Number 7", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        
        if ( title7 != null &&  title7.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            title7 =  title7.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "title7", new ActionMessage( "error.lengthTooLong", "Class Title 7", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        
        if ( credits7 != null && credits7.trim().length() > PortfolioConstants.FOUR_CHARS ) {
            credits7 = credits7.trim().substring( 0, PortfolioConstants.FOUR_CHARS );
            errors.add( "credits7", new ActionMessage( "error.lengthTooLong", "Credits 7", PortfolioConstants.FOUR_CHARS_DESC ) );
        }
        
        if (comments != null && (comments.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            comments = comments.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("Comments", new ActionMessage("error.lengthTooLong", "Comments",PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        return errors;
    }
}
