/* $Name:  $ */
/* $Id: CommunityMessageManager.java,v 1.1 2010/11/23 14:29:11 ajokela Exp $ */
package org.portfolio.bus.community;

import java.util.List;

import org.portfolio.model.Person;
import org.portfolio.model.community.Message;

/**
 * @author Alex Jokela
 * 
 */
public interface CommunityMessageManager {

	public List<Message> findUnreadMessages(int communityId, Person person);
	
	public Message findMessageById(int messageId);
	
	public List<Message> findMessagesByCommunity(int communityId, Person person);
	
	public void setMessageRead(Message message, Person person);
	
	public boolean setMessageRemoved(Message message);
	
	public boolean setMessageRemoved(int messageId);
	
	public void saveMessage(Message msg);
	
}