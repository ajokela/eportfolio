/* $Name:  $ */
/* $Id: PersonHome.java,v 1.19 2011/03/17 19:15:29 ajokela Exp $ */
package org.portfolio.dao;

import java.util.List;

import org.portfolio.model.Person;
import org.portfolio.model.Person.UserType;

/**
 * @author Matt Sheehan
 *
 */
public interface PersonHome {

	Person createNewGuestUser(String email);

	Person store(Person person);

	/**
	 * Email is case-insensitive.
	 */
	Person findByEmailAndPassword(String email, String password);

	Person findByUsername(String externalId);

	Person findByPersonId(String personId);
	
	List<Person> findBy(String query);
	List<Person> findBy(String query, List<UserType> userTypes, Person person, Integer limit);
	List<Person> findByName(String firstname, String lastname);
	List<Person> findByName(String firstname, String middlename, String lastname);
	List<Person> findAdmins();
	
	Person findCommunityPerson(int communityId);

	//List<Person> findByEmail(String email);
	Person findByEmail(String email);

    void remove(String personId);
}
