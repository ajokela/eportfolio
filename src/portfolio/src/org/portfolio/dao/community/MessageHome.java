/* $Name:  $ */
/* $Id: MessageHome.java,v 1.1 2010/11/23 14:29:11 ajokela Exp $ */
package org.portfolio.dao.community;

import java.util.List;

import org.portfolio.model.Person;
import org.portfolio.model.community.Message;

/**
 * @author Alex Jokela
 * 
 */
public interface MessageHome {
	
	public List<Message> findMessagesByCommunity(int communityId, Person person);

	public List<Message> findMessagesByCoordId(int coord_id, Person person);
	
	public List<Message> findUnreadMessages(int communityId, Person person);
	
	public void save(Message message);
	
	public void setMessageStatus(Message message, Person person, String status);
	
	public String getMessageStatus(Message message, Person person);
	
	public Message findMessageById(int id);
	public Message findMessageById(int id, int deleted);
		
    public void deleteById(int id);

    public void deleteByCommunityId(int communityId);
    
    public void setMessageRead(Message message, Person person);
    
    public void setMessageUnread(Message message, Person person);

}
