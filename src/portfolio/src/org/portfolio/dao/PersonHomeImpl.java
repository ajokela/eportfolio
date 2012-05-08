/* $Name:  $ */
/* $Id: PersonHomeImpl.java,v 1.25 2011/03/17 19:15:29 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.portfolio.model.EntryKey;
import org.portfolio.model.Person;
import org.portfolio.model.Person.UserType;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * Handles the persistence of the Person data element.
 * 
 */
@Repository("personHome")
public class PersonHomeImpl implements PersonHome {
    
    private LogService logService = new LogService(this.getClass());

    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<Person> rowMapper = new RowMapper<Person>() {
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            String personId = rs.getString("person_id");
            person.setPersonId(personId);
            person.setUsername(rs.getString("username"));
            person.setFirstName(rs.getString("first_name"));
            person.setMiddlename(rs.getString("middlename"));
            person.setLastname(rs.getString("lastname"));
            person.setLastLogin(rs.getTimestamp("last_login"));
            person.setDateCreated(rs.getTimestamp("date_created"));
            person.setMaxStorageSize(rs.getLong("max_storage_size"));
            person.setEmail(rs.getString("email"));
            person.setUsertype(UserType.valueOf(rs.getString("usertype")));
            person.setPassword(rs.getString("password"));
            person.setPassword2(rs.getString("password"));
            person.setCampus(rs.getString("campus"));
            int communityId = rs.getInt("community_id");
            
            if (communityId != 0) {
                person.setCommunityId(communityId);
            }
            
            try {
            	String ie = rs.getString("IS_ENABLED");
            	
            	if(ie == null) {
            		ie = "t";
            	}
            	
            	person.setIsEnabled(ie.contentEquals("t"));
            }
            catch (Exception e) {
            	person.setIsEnabled(rs.getString("IS_ENABLED").contentEquals("t"));
            }
            
            String profilePicDefId = rs.getString("profile_pic_def_id");
            String profilePicId = rs.getString("profile_pic_id");
            if (StringUtils.hasText(profilePicDefId)) {
                try {
                    BigDecimal entryId = new BigDecimal(profilePicId);
                    EntryKey entryKey = new EntryKey(personId, profilePicDefId, entryId);
                    person.setProfilePictureKey(entryKey);
                } catch (NumberFormatException e) {
                    logService.error("ERROR: TODO fix bad profile_pic_id -> " + profilePicId);
                }
            }
            return person;
        }
    };

    public Person createNewGuestUser(String email) {
    	
    	email = email.toLowerCase();
    	    	
    	Person ps = findByEmail(email);
    	
    	if (ps == null) {
	    	
    		UserType userType = UserType.MEMBER;
	        Person person = new Person();
	        
	        email = email.replaceAll(" ", "");
	        
	        // logService.debug("email: '" + email + "'" );
	        
	        String ut = Configuration.get("user.registration.usertype");
	        
	        person.setUsertype(ut);
	        
	        person.setEmail(email);
	        person.setUsername(email);
	        person.setUsertype(userType);
	        person.setPassword(getRandomPassword());
	        person.setMaxStorageSize(Long.parseLong(Configuration.get("user.storage.default")));
	        person.setDateCreated(new Date());
	        
	        return insert(person);
	        
    	}

    	return ps;
    	
    }

    private String passwordChars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789";

    /**
     * Creates a random alphanumeric password of 8 characters
     * 
     * @return the password as a String
     */
    private String getRandomPassword() {
        Random rand = new Random();
        char[] pass = new char[8];
        for (int i = 0; i < 8; i++) {
            int index = rand.nextInt(passwordChars.length());
            pass[i] = passwordChars.charAt(index);
        }
        return new String(pass);
    }

    public List<Person> findBy(String query) {
    	
    	/* remove leading whitespace */
    	query = query.replaceAll("^\\s+", "");
    	
    	/* remove trailing whitespace */
    	query = query.replaceAll("\\s+$", "");
    	
    	/* replace multiple whitespaces between words with single blank */
    	query = query.replaceAll("\\b\\s{2,}\\b", " ");
    	   	
    	String sql = "SELECT * FROM person WHERE " + 
    					"LOWER(email) LIKE '%' || LOWER(?) || '%' OR " +
    					"LOWER(username) LIKE '%' || LOWER(?) || '%' OR " +
    					"LOWER(first_name) LIKE '%' || LOWER(?) || '%' OR " +
    					"LOWER(middlename) LIKE '%' || LOWER(?) || '%' OR " + 
    					"LOWER(lastname) LIKE '%' || LOWER(?) || '%' OR " +
    					"LOWER(campus) LIKE '%' || LOWER(?) || '%' OR " +
    					"LOWER(first_name || ' ' || lastname) LIKE '%' || LOWER(?) || '%' OR " +
    					"LOWER(lastname || ' ' || first_name) LIKE '%' || LOWER(?) || '%'";
    	
    	return simpleJdbcTemplate.query(sql, rowMapper, query, query, query, query, query, query, query, query);
    	
    }

    public List<Person> findBy(String query, List<UserType> userTypes, Person person, Integer limit) {
    	
    	/* remove leading whitespace */
    	query = query.replaceAll("^\\s+", "");
    	
    	/* remove trailing whitespace */
    	query = query.replaceAll("\\s+$", "");
    	
    	/* replace multiple whitespaces between words with single blank */
    	query = query.replaceAll("\\b\\s{2,}\\b", " ");
    	
    	String sql = "SELECT * FROM person WHERE ";
    	
    	String sql_chunk = "(LOWER(email) LIKE '%' || LOWER(?) || '%' OR " +
    					   "LOWER(username) LIKE '%' || LOWER(?) || '%' OR " +
    					   "LOWER(first_name) LIKE '%' || LOWER(?) || '%' OR " +
    					   "LOWER(middlename) LIKE '%' || LOWER(?) || '%' OR " + 
    					   "LOWER(lastname) LIKE '%' || LOWER(?) || '%' OR " +
    					   "LOWER(campus) LIKE '%' || LOWER(?) || '%' OR " +
    					   "LOWER(first_name || ' ' || lastname) LIKE '%' || LOWER(?) || '%' OR " +
    					   "LOWER(lastname || ' ' || first_name) LIKE '%' || LOWER(?) || '%') ";
    	
    	int increment_t_params = 8;
    	
    	int t_params = increment_t_params;
    	
    	String[] parts = query.split(" +");
    	String many_chunks = "";
    	
    	List<String> params = new ArrayList<String>();
    	
    	for(int i=0; i<increment_t_params; ++i) {
    		params.add(query);
    	}
    	
    	for(int i=0; i<parts.length; ++i) {
    		
    		for(int j=0; j<increment_t_params; ++j) {
    			params.add(parts[i]);
    		}
    		
    		many_chunks += sql_chunk;
    		
    		if(i != (parts.length - 1)) {
    			many_chunks += " AND ";
    		}
    		
    		t_params += increment_t_params;
    	}
    	
    	
    	boolean did_add = false;
    	int cnt = 0;
    	String sql2 = "";
    	
    	for(Iterator<UserType> i = userTypes.iterator(); i.hasNext();) {
    		UserType ut = i.next();
    		
    		sql2 += "USERTYPE = '" + Person.getUserTypeToString(ut) + "'";
    		
    		if(cnt != (userTypes.size() - 1)) {
    			sql2 += " OR ";
    		}
    		
    		did_add = true;
    		cnt++;
    	}
    	
    	if(did_add) {
    		sql += "(";
    	}
    	
    	sql += sql_chunk;

    	if(many_chunks.length() > 0) {
    		sql += " OR (" + many_chunks + ")";
    	}
    	
    	if(did_add) {
    		sql += ") AND (" + sql2 + ")";
    	}
    	
    	if(limit > 0) {
    		sql += " LIMIT " + Integer.toString(limit);
    	}
    	
    	// logService.debug(sql);
    	
    	Object[] queries = new String[t_params]; // << query, query, query, query, query, query, query, query;
    	
    	for(int i=0; i<t_params; ++i) {
    		queries[i] = params.get(i);
    	}
    	
    	
    	
    	return simpleJdbcTemplate.query(sql, rowMapper, queries);
    	
    }

    
    public List<Person> findByName(String firstname, String lastname) {
    	String sql = "SELECT * FROM person WHERE (LOWER(first_name) LIKE LOWER(?) || '%' AND " +
    				 "LOWER(lastname) LIKE '%' || LOWER(?) || '%') OR " + 
    				 "(LOWER(lastname) LIKE LOWER(?) || '%' AND LOWER(first_name) LIKE '%' || LOWER(?) || '%')";
    	
    	return simpleJdbcTemplate.query(sql, rowMapper, firstname, lastname, firstname, lastname);
    }
    
    public List<Person> findAdmins() {
    	String sql = "SELECT * FROM person WHERE USERTYPE = 'ADMIN'";
    	
    	return simpleJdbcTemplate.query(sql, rowMapper);
    }
    
    public List<Person> findByName(String firstname, String middlename, String lastname) {
    	
    	String sql = "SELECT * FROM person WHERE LOWER(first_name) LIKE LOWER(?) || '%' AND " +
    				 "LOWER(middlename) LIKE LOWER(?) || '%' AND " +
    				 "LOWER(lastname) LIKE '%' || LOWER(?) || '%'";
    	
    	return simpleJdbcTemplate.query(sql, rowMapper, firstname, middlename.substring(0, middlename.length() - 1), lastname );
    }
    
    public Person findByEmailAndPassword(String email, String password) {
        // logService.debug("in findByEmailAndPassword");

        String sql = "select * from person where LOWER(email) = LOWER(?) and password = ?";
        List<Person> result = simpleJdbcTemplate.query(sql, rowMapper, email, password);
        return result.isEmpty() ? null : result.get(0);
    }

    public Person findByUsername(String externalId) {
        String sql = "select * from person where LOWER(username) = LOWER(?) order by usertype desc";
        List<Person> result = simpleJdbcTemplate.query(sql, rowMapper, externalId);
        return result.isEmpty() ? null : result.get(0);
    }

    public Person findByPersonId(String personId) {
        List<Person> result = simpleJdbcTemplate.query("select * from person where person_id = ?", rowMapper, personId);
        return result.isEmpty() ? null : result.get(0);
    }

    public Person findByEmail(String email) {
        String sql = "select * from person where LOWER(email) = LOWER(?)";
        List<Person> result = simpleJdbcTemplate.query(sql, rowMapper, email);
        return result.isEmpty() ? null : result.get(0);
    }
    
    public Person store(Person person) {
    	return insert(person);
    }

    protected Person update(Person person) {

        String sql = "update person set username = ?,first_name = ?,middlename = ? "
                + ",lastname = ?,last_login = ?,max_storage_size = ?,email = ?,usertype = ?,password = ?,"
                + "campus = ?, profile_pic_def_id=?, profile_pic_id=?, community_id=?, is_enabled=? where person_id=?";
        String elementId = person.getProfilePictureKey() == null ? null : person.getProfilePictureKey().getElementId();
        String entryId = person.getProfilePictureKey() == null ? null : person.getProfilePictureKey().getEntryId().toString();
        simpleJdbcTemplate.update(
                sql,
                person.getUsername(),
                person.getFirstName(),
                person.getMiddlename(),
                person.getLastname(),
                person.getLastLogin(),
                person.getMaxStorageSize(),
                person.getEmail(),
                person.getUsertype().toString(),
                person.getPassword(),
                person.getCampus(),
                elementId,
                entryId,
                person.getCommunityId(),
                person.isEnabled() ? "t" : "f",
                person.getPersonId());
        
        return findByPersonId(person.getPersonId());
    }
   

    protected Person insert(Person person) {
        String sql = "insert into person(person_id,username,first_name,middlename"
                + ",lastname,last_login,date_created,max_storage_size,email,usertype,password,campus,profile_pic_def_id,profile_pic_id,community_id,is_enabled"
                + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        if (person.getPersonId() == null || person.getPersonId().length() == 0) {
            person.setPersonId(generateId(person.getUsertype()));
        }
        
        Date now = new Date();
        String elementId = person.getProfilePictureKey() == null ? null : person.getProfilePictureKey().getElementId();
        String entryId = person.getProfilePictureKey() == null ? null : person.getProfilePictureKey().getEntryId().toString();
        
        Person p = findByPersonId(person.getPersonId());
        
        // logService.debug("==> Person[" + person.getPersonId() + "]: findByPersonId()...");

        if (p == null) {
		    
        	logService.debug("==> Person[" + person.getPersonId() + "]: Not Found; looking at email address...");
        	
           	if (person.getEmail() != null && person.getEmail().length() > 0) {

        		logService.debug("==> Person[" + person.getPersonId() + "]: Email address '" + person.getEmail() + "'");
        		
        		//List<Person> ps = findByEmail(person.getEmail());
        		Person ps = findByEmail(person.getEmail());
        		
        		//if(ps.size() > 0) {
        		if(ps != null) {
        			
        			logService.debug("==> Person[" + person.getPersonId() + "]: Found one or more for '" + person.getEmail() + "', updating Person");
        			
        			//person.setPersonId(ps.get(0).getPersonId());
        			person.setPersonId(ps.getPersonId());
        			
        			return update(person);
        			
        		}
        		else {
        			
        			// logService.debug("==> Person[" + person.getPersonId() + "]: Not Found by Email; inserting with person_id[" + person.getPersonId() + "]");

        			simpleJdbcTemplate.update(sql, person.getPersonId(), person.getUsername(), person.getFirstName(), person.getMiddlename(), person
    			            .getLastname(), now, now, person.getMaxStorageSize(), person.getEmail(), person.getUsertype().toString(), person
    			            .getPassword(), person.getCampus(), elementId, entryId, person.getCommunityId(), person.isEnabled() ? "t" : "f");
    			    
            	
    			    return findByPersonId(person.getPersonId());
        			
        		}
        		
        	}
        	else {
        		
        		logService.debug("==> Person[" + person.getPersonId() + "]: Email is NULL/Empty; inserting with person_id[" + person.getPersonId() + "]");
        		
			    simpleJdbcTemplate.update(sql, person.getPersonId(), person.getUsername(), person.getFirstName(), person.getMiddlename(), person
			            .getLastname(), now, now, person.getMaxStorageSize(), person.getEmail(), person.getUsertype().toString(), person
			            .getPassword(), person.getCampus(), elementId, entryId, person.getCommunityId(), person.isEnabled() ? "t" : "f");
			    
        	
			    return findByPersonId(person.getPersonId());
        	
        	}
        	
        }
        else {
        	
        	// logService.debug("==> Person[" + person.getPersonId() + "]: Person Found, updating");

        	return update(person);
        	
        }
    }


    private String generateId(UserType usertype) {
        int id = sequenceGenerator.getNextSequenceNumber("PERSONID");
        
        while(findByPersonId(String.valueOf(id)) != null) {
        	id++;
        }
        
        String idStr = String.valueOf(id);
        if (usertype == UserType.GUEST) {
            // if this is a guest user, be sure not to overlap with
            // valid IDs in a secondary repository (i.e. PeopleSoft)
            idStr = "G" + idStr;
        } else if (usertype == UserType.CMTY) {
            idStr = "C" + idStr;
        }
        return idStr;
    }

    public Person findCommunityPerson(int communityId) {
        String sql = "select * from person where community_id = ?";
        List<Person> result = simpleJdbcTemplate.query(sql, rowMapper, communityId);
        return result.isEmpty() ? null : result.get(0);
    }

    public void remove(String personId) {
        simpleJdbcTemplate.update("delete from person where person_id=?", personId);
    }
}
