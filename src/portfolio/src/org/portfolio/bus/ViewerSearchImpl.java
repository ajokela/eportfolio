/* $Name:  $ */
/* $Id: ViewerSearchImpl.java,v 1.10 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.bus;

import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.util.RequiredInjection;

/**
 * org.portfolio.util.share.ViewerSearchImpl.
 * 
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.10 $ $Date: 2011/03/14 19:37:44 $
 * @since x.x
 */
public class ViewerSearchImpl implements ViewerSearch {

    protected boolean guestUserCreateOk;
    protected boolean memberUserCreateOk;
    protected PersonHome personHome;

    public Person findMemberViewer(String username) {
    	
    	Person person = personHome.findByUsername(username);
    	
    	if(person == null) {
    		person = personHome.findByEmail(username);
    	}
    	
        return person;
    }

    public Person findGuestViewer(String email) {
        Person person = null;
        if (email != null) {
            
        	//List<Person> persons = personHome.findByEmail(email);
            //if (persons.size() > 0) {
                //person = (Person) persons.get(0);
            //}
        	
        	person = personHome.findByEmail(email);

        	/*
            if (person == null && guestUserCreateOk) {
                person = personHome.createNewGuestUser(email);
            }
            */
        }
        return person;
    }

    public Person findViewerByPersonId(String personId) {
        return personHome.findByPersonId(personId);
    }

    @RequiredInjection
    public void setGuestUserCreateOk(boolean guestUserCreateOk) {
        this.guestUserCreateOk = guestUserCreateOk;
    }

    @RequiredInjection
    public void setMemberUserCreateOk(boolean memberUserCreateOk) {
        this.memberUserCreateOk = memberUserCreateOk;
    }

    @RequiredInjection
    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }
}
