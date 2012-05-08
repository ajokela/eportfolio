/* $Name:  $ */
/* $Id: CommunityMessageManagerImpl.java,v 1.1 2010/11/23 14:29:11 ajokela Exp $ */
package org.portfolio.bus.community;

import java.util.List;

import org.portfolio.dao.community.MessageHome;
import org.portfolio.model.Person;
import org.portfolio.model.community.Message;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alex Jokela
 * 
 */
@Service
public class CommunityMessageManagerImpl implements CommunityMessageManager {
    
    @SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());
    
    @Autowired private MessageHome messageHome;
    
    public List<Message> findUnreadMessages(int communityId, Person person) {
    	return messageHome.findUnreadMessages(communityId, person);
    }
    
    public Message findMessageById(int messageId) {
    	return messageHome.findMessageById(Integer.valueOf(messageId));
    }
    
    public List<Message> findMessagesByCommunity(int communityId, Person person) {
    	return messageHome.findMessagesByCommunity(communityId, person);
    }
    
    public void saveMessage(Message msg) {
    	messageHome.save(msg);
    }
    
    public void setMessageRead(Message message, Person person)  {
    	messageHome.setMessageRead(message, person);
    }

	public boolean setMessageRemoved(Message message) {
		return setMessageRemoved(message.getId());
	}

	public boolean setMessageRemoved(int messageId) {
		
		messageHome.deleteById(messageId);
		
		Message message = messageHome.findMessageById(messageId, org.portfolio.dao.community.MessageHomeImpl.MESSAGE_STATUS_UNDELETED);
		
		return message == null;
	}
    
}
