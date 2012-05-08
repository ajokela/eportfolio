package org.portfolio.bus;

import org.portfolio.dao.WhiteboardHome;
import org.portfolio.model.Person;
import org.portfolio.model.Whiteboard;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service("whiteboardManager")
public class WhiteboardManagerImpl implements WhiteboardManager {
	
	@Autowired private WhiteboardHome whiteboardHome;
	
	public void newLine(String line, String session_id, int community_id, int grouping, Person person) {
		
		String[] array_lines = line.split(",");
		
		if(array_lines != null) {
		
			for(int i=0; i<array_lines.length; ++i) {
				
				// each line is like this --
				// color:offsetx1:offsety1:offsetyx2:offsety2
				
				if(array_lines[i] != null) {
					
					String[] parts = array_lines[i].split(":");
					
					if(parts != null && parts.length == 5) {
						
						Whiteboard wb = new Whiteboard();
						
						wb.setCommunityId(community_id);
						wb.setPersonId(person.getPersonId());
						wb.setIsDeleted(false);
						wb.setSessionId(session_id);
						
						wb.setColor(parts[0]);
						wb.setOffsetX1(Integer.valueOf(parts[1]));
						wb.setOffsetY1(Integer.valueOf(parts[2]));
						wb.setOffsetX2(Integer.valueOf(parts[3]));
						wb.setOffsetY2(Integer.valueOf(parts[4]));
						
						wb.setGrouping(grouping);
						
						whiteboardHome.insertWhiteboard(wb);
					}
				}				
			}			
		}		
		
	}
	
}