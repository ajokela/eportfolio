package org.portfolio.bus;

import org.portfolio.model.Person;

public interface WhiteboardManager {
	
	void newLine(String line, String session_id, int community_id, int grouping, Person person);
	
}