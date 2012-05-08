/* $Name:  $ */
/* $Id: MyCommunitiesAction.groovy,v 1.2 2010/10/27 19:28:15 ajokela Exp $ */
package org.portfolio.client.action.community

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.springframework.beans.factory.annotation.Autowired
import org.portfolio.util.LogService
import net.sf.json.JSONArray

/**
 * @author Matt Sheehan
 *
 */
public class MyCommunitiesAction extends BaseAction {
	
	@Autowired
	CommunityManager communityManager;
	
	private final static LogService logService = new LogService(MyCommunitiesAction)
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		def person = getPerson(request)
		def communities = communityManager.getCommunitiesByPersonId(person.personId)
		communities.sort { x, y -> x.name.compareToIgnoreCase(y.name)}
		
		def communityInfoMaps = []
		communities.each() { Community community ->
			def role = communityManager.getHighestRoleByCommunityIdAndPersonId(community.id, person.personId)
			communityInfoMaps << [community:community, role:role]
		} 

		request.setAttribute("myCommunities", communityInfoMaps)
		return mapping.findForward("success")
	}
}
