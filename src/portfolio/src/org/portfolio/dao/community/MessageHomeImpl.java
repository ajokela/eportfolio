/* $Name:  $ */
/* $Id: MessageHomeImpl.java,v 1.1 2010/11/23 14:29:11 ajokela Exp $ */
package org.portfolio.dao.community;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import java.util.Iterator;

import org.portfolio.dao.PersonHome;
import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.Person;
import org.portfolio.model.community.Message;
import org.portfolio.model.community.MessageView;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
import difflib.*;
import java.util.Arrays;

/**
 * @author Alex Jokela
 * 
 */

@Repository("MessageHome")
public class MessageHomeImpl implements MessageHome {
	
	private SimpleJdbcTemplate simpleJdbcTemplate;
    @Autowired private SequenceGenerator sequenceGenerator;
	
    @Autowired private PersonHome ph;
    
    public static final int MESSAGE_STATUS_INDIFFERENT = -1;
    public static final int MESSAGE_STATUS_DELETED     = 0;
    public static final int MESSAGE_STATUS_UNDELETED   = 1;
    
    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

	private static final String MESSAGE_UNREAD = "unread";
	private static final String MESSAGE_READ   = "read";
	
	protected LogService logService = new LogService(this.getClass());
    
	private final RowMapper<MessageView> messageViewRowMapper = new RowMapper<MessageView>() {
        public MessageView mapRow(ResultSet rs, int rowNum) throws SQLException {
            
        	MessageView msgV = new MessageView(false);
        	
        	msgV.setId(rs.getInt("id"));
        	msgV.setPersonId(rs.getString("person_id"));
        	msgV.setMessageId(rs.getInt("message_id"));
        	msgV.setStatus(rs.getString("status"));
        	msgV.setCreatedAt(rs.getDate("created_at"));
        	msgV.setUpdatedAt(rs.getDate("updated_at"));
        	
        	return msgV;
        }
    };
    
    private final RowMapper<Message> messageRowMapper = new RowMapper<Message>() {
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            
        	
        	Message msg = new Message(false);
        	
        	msg.setId(rs.getInt("id"));
        	msg.setCoordinatorId(rs.getString("coord_id"));
        	msg.setCoordinator(ph.findByPersonId(msg.getCoordinatorId()));
        	msg.setCommunityId(rs.getInt("community_id"));
        	msg.setDeleted(rs.getString("deleted") == "T" ? true : false);
        	msg.setCreatedAt(rs.getDate("created_at"));
        	msg.setUpdatedAt(rs.getDate("updated_at"));
        	msg.setStartedAt(rs.getDate("started_at"));
        	msg.setMessageTxtDiff(rs.getString("message_txt_diff"));
        	
        	if (msg.getStartedAt() == null) {
        		msg.setStartedAt(msg.getCreatedAt());
        	}
        	
        	msg.setEndedAt(rs.getDate("ended_at"));
        	msg.setMessageTxt(rs.getString("message_txt"));
        	msg.setSubject(rs.getString("subject"));
        	
        	return msg;
        }
    };



    
	public List<Message> findMessagesByCommunity(int communityId, Person person) {
		
    	String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
    	
    	String sp = "sysdate";
    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sp += "()";
    	}
    	else {
    		logService.debug("====> sysdate_parentheses: " + sysdate_parentheses);
    	}
    	
		String sql = "SELECT * FROM messages WHERE deleted = 'F' AND ((started_at <= " + sp + " AND ended_at >= " + sp + ") OR (started_at IS NULL AND ended_at >= " + sp + ") OR (started_at <= " + sp + " AND ended_at IS NULL) OR (started_at IS NULL AND ended_at IS NULL)) AND community_id = ?";
		
		List<Message> msgs =  simpleJdbcTemplate.query(sql, messageRowMapper, communityId);
		
		if(msgs == null) {
			return null;
		}
		
		for(Iterator<Message> i = msgs.iterator(); i.hasNext(); ) {
			
			Message msg = i.next();
			
			if (person != null) {
				String status = getMessageStatus(msg, person);
				msg.setRead(status.contentEquals(MESSAGE_READ));
			}
			else {
				msg.setRead(false);
			}
		}
		
		Collections.sort(msgs);
		
		return msgs;
	}

	public List<Message> findMessagesByCoordId(int coord_id, Person person) {

		String sql = "SELECT * FROM messages WHERE deleted = 'F' AND coord_id = ?";
		
		List<Message> msgs = simpleJdbcTemplate.query(sql, messageRowMapper, coord_id);
		
		if(msgs == null) {
			return null;
		}
		
		for(Iterator<Message> i = msgs.iterator(); i.hasNext(); ) {
			
			Message msg = i.next();
			String status = this.getMessageStatus(msg, person);
			
			msg.setRead(status.contentEquals(MESSAGE_READ));
			
		}
		
		Collections.sort(msgs);
		
		return msgs;
	}
	
	public void save(Message message) {
		
		Date d = new Date();
		
		String sql = "SELECT * FROM messages WHERE id = ?";
		
		Message msg = null;
		boolean updated = false;
		
		try {
			msg = simpleJdbcTemplate.queryForObject(sql, messageRowMapper, message.getId());
		}
		catch(Exception e) {
			
		}
		
		if(msg == null) {
			
			sql = "INSERT INTO messages (id, community_id, coord_id, message_txt, subject, created_at, updated_at, started_at, ended_at, deleted, message_txt_diff) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'F', '')";
			
			message.setId(sequenceGenerator.getNextSequenceNumber("message_id_seq"));
			
			simpleJdbcTemplate.update(sql, message.getId(), message.getCommunityId(), message.getCoordinatorId(), message.getMessageTxt(), message.getSubject(), message.getCreatedAt(), message.getUpdatedAt(), message.getStartedAt(), message.getEndedAt());

		}
		else {
			
			String msg_diff = "";
			
			List<String> original_lines;
			List<String> new_lines;
			
			sql = "UPDATE messages SET message_txt = ?, subject = ?, started_at = ?, ended_at = ?, deleted = ?, updated_at = ?, message_txt_diff = ? WHERE id = ?";
			
			if(message.getSubject() == null || (message.getSubject() != null && message.getSubject().length() == 0)) {
				message.setSubject("No Subject");
			}
			
			message.setUpdatedAt(d);
			
			original_lines = Arrays.asList(msg.getMessageTxt().split("\\r?\\n"));
			new_lines      = Arrays.asList(message.getMessageTxt().split("\\r?\\n"));
			
			try {
				
				Patch patch = DiffUtils.diff(original_lines, new_lines);
				
				msg_diff = msg.getMessageTxtDiff() == null ? "" : msg.getMessageTxtDiff();
				
				msg_diff += "-----BEGIN MESSAGE DIFF-----\n";
				
				for(Delta delta: patch.getDeltas()) {
					msg_diff += delta.toString() + "\n";
				}
				
				msg_diff += "-----END MESSAGE DIFF-----\n";
			}
			catch(Exception e) {
				logService.debug(e);
			}
			
			simpleJdbcTemplate.update(sql, message.getMessageTxt(), message.getSubject(), message.getStartedAt(), message.getEndedAt(), message.getDeleted() == true ? "T" : "F", message.getUpdatedAt(), msg_diff, message.getId() );
			
			updated = true;
			
		}
		
		if (updated) {
			message.setSubject("[UPDATE] " + message.getSubject());
		}
		
		if ((message.getStartedAt() == null) || (message.getStartedAt() != null && message.getStartedAt().compareTo(d) < 0)) {
			
			/*
			Community community = communityManager.getCommunityById(message.getCommunityId());
			
			List<Person> members = communityManager.getMembers(Integer.toString(community.getId()), CommunityRoleType.MEMBER);
			
			for(Iterator<Person> i = members.iterator(); i.hasNext();) {
				// Person p = i.next();
				
				
				
			}
			*/
		}
		
		
	}
	
	public void setMessageRead(Message message, Person person) {
		setMessageStatus(message, person, MESSAGE_READ);
	}
	
	public void setMessageUnread(Message message, Person person) {
		setMessageStatus(message, person, MESSAGE_UNREAD);
	}
		
	public List<Message> findUnreadMessages(int communityId, Person person) {
		
		if(simpleJdbcTemplate == null) {
			logService.debug("simpleJdbcTemplate is null");
		}
		
		List<Message> all = findMessagesByCommunity(communityId, person);
		List<Message> unread = new ArrayList<Message>();
		
		for(Iterator<Message> i = all.iterator(); i.hasNext(); ) {
			Message m = i.next();
			
			String status = getMessageStatus(m, person);
			
			if(status.contentEquals(MESSAGE_UNREAD)) {
				unread.add(m);
			}
			
		}
		
		return unread;
	}
	
	public String getMessageStatus(Message message, Person person) {
		
		String sql = "SELECT * FROM message_views WHERE message_id = ? AND person_id = ?";
		MessageView msgV = null;
		
		try {
			msgV = simpleJdbcTemplate.queryForObject(sql, messageViewRowMapper, message.getId(), person.getPersonId());
		}
		catch (Exception e) {
			
		}
		
		if (msgV == null) {
			return MESSAGE_UNREAD;
		}
		else {
			return msgV.getStatus();
		}
		
	}

	
	public void setMessageStatus(Message message, Person person, String status) {
		
		Date d = new Date();
		
		String sql = "SELECT * FROM message_views WHERE message_id = ? AND person_id = ?";
		MessageView msgV = null;
		List<MessageView> msgsV = null;
		
		try {
			msgsV = simpleJdbcTemplate.query(sql, messageViewRowMapper, message.getId(), person.getPersonId());
		}
		catch(Exception e) {
			
		}
		
		
		if (msgsV == null || (msgsV != null && msgsV.size() == 0)) {
			
			sql = "INSERT INTO message_views (id, message_id, person_id, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
			msgV = new MessageView();
			
			msgV.setId(sequenceGenerator.getNextSequenceNumber("message_view_id_seq"));
			msgV.setStatus(status);
			msgV.setMessageId(message.getId());
			msgV.setPersonId(person.getPersonId());
			
			simpleJdbcTemplate.update(sql, msgV.getId(), msgV.getMessageId(), msgV.getPersonId(), msgV.getStatus(), msgV.getCreatedAt(), msgV.getUpdatedAt());
		
		}
		else {
			
			msgV = msgsV.get(0);
			
			sql = "UPDATE message_views SET status = ?, updated_at = ? WHERE id = ?";
			
			msgV.setUpdatedAt(d);
			msgV.setStatus(status);
			
			simpleJdbcTemplate.update(sql, msgV.getStatus(), msgV.getUpdatedAt(), msgV.getId());
			
		}
		
	}
	
	public Message findMessageById(int id) {
		return findMessageById(id, MESSAGE_STATUS_INDIFFERENT);
	}
	
	public Message findMessageById(int id, int deleted) {
		
		try {
			
			String sql = "SELECT * FROM messages WHERE id = ?";
			
			if(deleted == MESSAGE_STATUS_DELETED) {
				sql += " AND deleted = 'T'";
			}
			else if(deleted == MESSAGE_STATUS_UNDELETED) {
				sql += " AND deleted = 'F'";
			}
			
			return simpleJdbcTemplate.queryForObject(sql, messageRowMapper, id);
		}
		catch (Exception e) {
			
		}
		
		return null;
		
	}

    public void deleteById(int id) {
    	
    	Date d = new Date();
    	String sql = "UPDATE messages SET deleted = 'T', updated_at = ? WHERE id = ?";
    	simpleJdbcTemplate.update(sql, d, id);
    	
    }

    public void deleteByCommunityId(int communityId) {
    	
    	Date d = new Date();
    	String sql = "UPDATE message SET deleted = 'T', updated_at = ? WHERE community_id = ?";
    	simpleJdbcTemplate.update(sql, d, communityId);

    }

}