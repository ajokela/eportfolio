/* $Name:  $ */
/* $Id: CommunityManager.java,v 1.36 2011/02/16 17:35:17 ajokela Exp $ */
package org.portfolio.bus.community;

import java.util.List;

import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Link;
import org.portfolio.model.Person;
import org.portfolio.model.Template;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.Objective;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.model.wizard.CollectionGuide;

/**
 * @author Matt Sheehan
 * 
 */
public interface CommunityManager {

	/**
	 * Gets the members of the given community with the given role type.
	 * 
	 * @return a list of Persons or an empty list if none found.
	 */
	public List<Person> getMembers(String communityId, CommunityRoleType communityRoleType);

    public List<CommunityRoleType> getRolesByCommunityIdAndPersonId(String communityIdParam, String personId);

	/**
	 * Adds the person to the given role in the community.
	 */
	//public void addRoleAssignment(String communityId, String personId, CommunityRoleType roleType);
	public String addRoleAssignment(String communityId, String personId, CommunityRoleType roleType);

	public int copyCommunity(Community community, String newName, boolean copyMembers, boolean copyAMObjectives);

	/**
	 * Removes the person from the given role in the community.
	 */
	public void removeRoleAssignment(String communityId, String personId, CommunityRoleType roleType);

    public List<Template> getAssessableCommunityTemplates(String communityId);
    
    public List<Template> getAssessableCommunityTemplates(int communityId);
    
    public List<Template> getAssessableCommunityTemplates(String communityId, String templateId);

    public List<CollectionGuide> getPublishedCommunityWizards(String communityIdParam);
    
    public List<CollectionGuide> getUnpublishedCommunityWizards(String communityIdParam);
    
    public List<CollectionGuide> getAllCommunityWizards(String communityIdParam);

	public List<Community> getAllCommunities();

    public List<Community> getPublicCommunities();

    public Community getCommunityById(String communityId);

    public Community getCommunityById(int communityId);

	public void saveCommunity(Community community, Person person);

    public void deleteCommunity(int communityId);

	public List<Community> getCommunitiesByPersonId(String personId);

	public List<Objective> getCommunityObjectiveSets(String communityId);

	public List<AssessmentModel> getCommunityAssessmentModels(String communityIdParam, String assessmentModelType);

    public List<Link> getCommunityLinks(Community community);
    public List<Link> getCommunityLinks(String communityId);
    
    public List<? extends ElementDataObject> getCommunityResources(Community community);

    public List<Template> getPublishedCommunityTemplates(String communityIdParam);

    public List<Template> getUnpublishedCommunityTemplates(String communityIdParam);

    public CommunityRoleType getHighestRoleByCommunityIdAndPersonId(Integer communityId, String personId);

    public List<Community> getPrivateCommunities();
    
    public List<Community> getDeletedCommunities();

    public void restoreCommunity(int parseInt);
}
