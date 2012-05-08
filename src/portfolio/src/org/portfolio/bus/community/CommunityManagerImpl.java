/* $Name:  $ */
/* $Id: CommunityManagerImpl.java,v 1.55 2011/02/16 17:35:17 ajokela Exp $ */
package org.portfolio.bus.community;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.portfolio.bus.ElementManager;
import org.portfolio.dao.PersonHome;
import org.portfolio.dao.assessment.AssessmentModelHome;
import org.portfolio.dao.assessment.ObjectiveHome;
import org.portfolio.dao.community.CommunityHome;
import org.portfolio.dao.community.RoleAssignmentHome;
import org.portfolio.dao.template.TemplateHome;
import org.portfolio.dao.wizard.CollectionGuideHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Link;
import org.portfolio.model.Person;
import org.portfolio.model.Template;
import org.portfolio.model.Person.UserType;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.Objective;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Matt Sheehan
 * 
 */
@Service
public class CommunityManagerImpl implements CommunityManager {
    
    private LogService logService = new LogService(this.getClass());

    @Autowired
    private RoleAssignmentHome roleAssignmentHome;
    @Autowired
    private CommunityHome communityHome;
    @Autowired
    private CollectionGuideHome wizardHome;
    @Autowired
    private ObjectiveHome objectiveHome;
    @Autowired
    private AssessmentModelHome assessmentModelHome;
    @Autowired
    private TemplateHome templateHome;
    @Autowired
    private ElementManager elementManager;
    @Autowired
    private PersonHome personHome;
    
    public int copyCommunity(Community community, String newName, boolean copyMembers, boolean copyAMObjectives) {
    	
    	if (community != null && newName != null) {
    		
    		Community newCommunity = new Community();
    		
    		newCommunity.setCampusCode(community.getCampusCode());
    	
    		newCommunity.setContactEmailAddress(community.getContactEmailAddress());
    		newCommunity.setDeleted(false);
    		newCommunity.setDescription(community.getDescription());
    		newCommunity.setName(newName);
    		newCommunity.setPrivateCommunity(community.isPrivateCommunity());
    		newCommunity.setProgram(community.getProgram());
    		newCommunity.setType(community.getType());

    		int id = communityHome.insert(newCommunity);
    		
    		newCommunity.setId(id);
    		
            Person communityPerson = new Person();
            communityPerson.setUsertype(UserType.CMTY);
            communityPerson.setCommunityId(id);
            personHome.store(communityPerson);

            List<Template> templates = templateHome.findByCommunityId(community.getId());
    		List<Template> newTemplates = new ArrayList<Template>();
    		
            for(Iterator<Template> i=templates.iterator(); i.hasNext();) {
            	Template t = i.next();
            	
            	Template newT = templateHome.copyTemplate(t, newCommunity, copyAMObjectives);
            	
            	newTemplates.add(newT);
            	
            }
            
            List<Person> coordinators = getMembers(Integer.toString(community.getId()), CommunityRoleType.COMMUNITY_COORDINATOR);
            List<Person> assessCoordinators = getMembers(Integer.toString(community.getId()), CommunityRoleType.ASSESSMENT_COORDINATOR);
            List<Person> evaluators = getMembers(Integer.toString(community.getId()), CommunityRoleType.EVALUATOR);
            
            for(Iterator<Person> i=coordinators.iterator(); i.hasNext();) {
            	Person p = i.next();
            	addRoleAssignment(Integer.toString(id), p.getPersonId(), CommunityRoleType.COMMUNITY_COORDINATOR);
            }

            for(Iterator<Person> i=evaluators.iterator(); i.hasNext();) {
            	Person p = i.next();
            	addRoleAssignment(Integer.toString(id), p.getPersonId(), CommunityRoleType.EVALUATOR);
            }

            for(Iterator<Person> i=assessCoordinators.iterator(); i.hasNext();) {
            	Person p = i.next();
            	addRoleAssignment(Integer.toString(id), p.getPersonId(), CommunityRoleType.ASSESSMENT_COORDINATOR);
            }
            
            if(copyMembers) {
            	List<Person> members = getMembers(Integer.toString(community.getId()), CommunityRoleType.MEMBER);
            	
                for(Iterator<Person> i=members.iterator(); i.hasNext();) {
                	Person p = i.next();
                	addRoleAssignment(Integer.toString(id), p.getPersonId(), CommunityRoleType.MEMBER);
                }

            }
            
            return id;
            
    	}
    
    	return -1;
    }


    public List<Person> getMembers(String communityId, CommunityRoleType communityRoleType) {
        List<Person> peoples = roleAssignmentHome.findPersonsByCommunityIdAndRoleTypeCode(communityId, communityRoleType.getCode());
        
        Collections.sort(peoples, Person.LASTNAME);
        
        return peoples;
    }

    public Community getCommunityById(String communityId) {
        return communityHome.findByCommunityId(communityId);
    }

    public Community getCommunityById(int communityId) {
        return getCommunityById(Integer.toString(communityId));
    }

    public String addRoleAssignment(String communityId, String personId, CommunityRoleType byCode) {
    	List<Person> peoples = getMembers(communityId, byCode);
    	
    	if(peoples.size() > 0) {
    		Person person = personHome.findByPersonId(personId);
    		
    		if(person != null) {
    			if(!peoples.contains(person)) {
    				roleAssignmentHome.insert(communityId, personId, byCode.getCode());
    				return "Person was added";
    			} else {
    				return "Person was not added because they are already a member of this community";
    			}
    		} else {
    			return "Person was not added because they could not be found";
    		}
    		
    	} else {
			roleAssignmentHome.insert(communityId, personId, byCode.getCode());
			return "Person was added";
    	}
    }

    public void removeRoleAssignment(String communityId, String personId, CommunityRoleType byCode) {
        roleAssignmentHome.delete(communityId, personId, byCode.getCode());
    }

    public void removeAllRoleAssignments(String communityId, String personId) {
        roleAssignmentHome.delete(communityId, personId);
    }

    public List<Template> getPublishedCommunityTemplates(String communityIdParam) {
        return templateHome.findPublishedByCommunityId(Integer.parseInt(communityIdParam));
    }

    public List<Template> getUnpublishedCommunityTemplates(String communityIdParam) {
        return templateHome.findUnpublishedByCommunityId(Integer.parseInt(communityIdParam));
    }

    public List<CollectionGuide> getPublishedCommunityWizards(String communityIdParam) {
        // TODO in query
        List<CollectionGuide> allGuides = wizardHome.findByGroupId(Integer.parseInt(communityIdParam));
        List<CollectionGuide> list = new ArrayList<CollectionGuide>();
        for (CollectionGuide collectionGuide : allGuides) {
            if (collectionGuide.isPublished()) {
                list.add(collectionGuide);
            }
        }
        return list;
    }

    public List<CollectionGuide> getUnpublishedCommunityWizards(String communityIdParam) {
        // TODO in query
        List<CollectionGuide> allGuides = wizardHome.findByGroupId(Integer.parseInt(communityIdParam));
        List<CollectionGuide> list = new ArrayList<CollectionGuide>();
        for (CollectionGuide collectionGuide : allGuides) {
            if (!collectionGuide.isPublished()) {
                list.add(collectionGuide);
            }
        }
        return list;
    }

    public List<CollectionGuide> getAllCommunityWizards(String communityIdParam) {
        return wizardHome.findByGroupId(Integer.parseInt(communityIdParam));
    }

    public List<Community> getAllCommunities() {
        return communityHome.findAll();
    }

    @Transactional
    public void saveCommunity(Community community, Person person) {
        if (community.getId() == null) {
            communityHome.insert(community);
            Person communityPerson = new Person();
            communityPerson.setUsertype(UserType.CMTY);
            communityPerson.setCommunityId(community.getId());
            communityPerson.setPersonId(null);
            communityPerson.setEmail(null);
            
            personHome.store(communityPerson);
         
        } else {
            communityHome.update(community);
        }
    }

    public List<CommunityRoleType> getRolesByCommunityIdAndPersonId(String communityId, String personId) {
        List<String> roleCodes = roleAssignmentHome.findRolesByCommunityIdAndPersonId(communityId, personId);
        List<CommunityRoleType> roleTypes = new ArrayList<CommunityRoleType>();
        for (String roleCode : roleCodes) {
            roleTypes.add(CommunityRoleType.getByCode(roleCode));
        }
        return roleTypes;
    }

    public List<Community> getCommunitiesByPersonId(String personId) {
        return roleAssignmentHome.findCommunitiesByPersonId(personId);
    }

    public List<Objective> getCommunityObjectiveSets(String communityId) {
        return objectiveHome.findObjectiveSetsByCommunityId(Integer.parseInt(communityId));
    }

    public List<AssessmentModel> getCommunityAssessmentModels(String communityIdParam, String assessmentModelType) {
        return assessmentModelHome.findAssessmentModelsByCommuntyAndType(communityIdParam, assessmentModelType);
    }

    @Transactional
    public void deleteCommunity(int communityId) {
        Community community = communityHome.findByCommunityId(communityId);
        roleAssignmentHome.deleteAllByCommunity(communityId);
        
        community.setDeleted(true);
        communityHome.update(community);
    }

    public List<Community> getPublicCommunities() {
        return communityHome.findPublicCommunities();
    }
    
    public List<Link> getCommunityLinks(String communityId) {
    	Community community = communityHome.findByCommunityId(communityId);
    	if(community != null) {
    		return getCommunityLinks(community);
    	}
    	
    	return null;
    }

    public List<Link> getCommunityLinks(Community community) {
        return elementManager.findEPFLinksByPersonId(community.getPerson().getPersonId());
    }
    
    public List<? extends ElementDataObject> getCommunityResources(Community community) {
    	
    	String personId = "0";
    	
    	if(community.getPerson() == null) {
    		Person p = personHome.findCommunityPerson(community.getId());
    		
    		if(p == null) {
    			logService.debug("Unable to find Community Person for Community[" + community.getId() + "]");
    		}
    		else {
    			personId = community.getPerson().getPersonId();
    		}
    		
    	}
    	
    	return elementManager.findByPersonId(personId);
    }

    public List<Template> getAssessableCommunityTemplates(String communityId) {
        return templateHome.findAssessableByCommunityId(communityId);
    }
    
    public List<Template> getAssessableCommunityTemplates(int communityId) {
        return getAssessableCommunityTemplates(Integer.toString(communityId));
    }
    
    public List<Template> getAssessableCommunityTemplates(String communityId, String templateId) {
    	List<Template> tmpList = new ArrayList<Template>();
    	List<Template> templates = templateHome.findAssessableByCommunityId(communityId);
    	
    	if(templates.contains(templateHome.findTemplateById(templateId))) {
    		tmpList.add(templateHome.findTemplateById(templateId));
    	}
    	else if (templateId.contentEquals("-1")) {
    		tmpList.addAll(templates);
    	}
    	
    	return tmpList;
    }

    public CommunityRoleType getHighestRoleByCommunityIdAndPersonId(Integer communityId, String personId) {
        List<CommunityRoleType> roles = getRolesByCommunityIdAndPersonId(communityId.toString(), personId);
        Collections.sort(roles);
        return roles.isEmpty() ? null : roles.get(roles.size() - 1);
    }

    @Override
    public List<Community> getDeletedCommunities() {
        return communityHome.findDeletedCommunities();
    }

    @Override
    public List<Community> getPrivateCommunities() {
        return communityHome.findPrivateCommunities();
    }

    @Override
    public void restoreCommunity(int communityId) {
        Community community = communityHome.findByCommunityId(communityId);
        
        community.setDeleted(false);
        communityHome.update(community);
    }
}
