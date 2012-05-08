/* $Name:  $ */
/* $Id: ElementManagerImpl.java,v 1.22 2011/03/24 19:03:25 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.bsf.BSFException;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.bus.element.ElementFolderManager;
import org.portfolio.dao.ElementHome;
import org.portfolio.dao.FileAccessor;
import org.portfolio.dao.PersonHome;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.dao.wizard.WizardElementInstanceHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.ElementDefinition;
import org.portfolio.model.EntryKey;
import org.portfolio.model.FileElement;
import org.portfolio.model.Link;
import org.portfolio.model.Person;
import org.portfolio.model.Person.UserType;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("elementManager")
public class ElementManagerImpl implements ElementManager {

    @Autowired
    private ElementDefinitionManager elementDefinitionManager;
    @Autowired
    private AttachmentManager attachmentManager;
    @Autowired
    private TagManager tagManager;
    @Autowired
    private WizardElementInstanceHome wizardElementInstanceHome;
    @Autowired
    private ShareEntryHome shareEntryHome;
    @Autowired
    private ElementFolderManager elementFolderManager;
    
    @Autowired
    private PersonHome personHome;
    
    @Autowired
    private CommunityManager communityManager;
    
    private final LogService logService = new LogService(this.getClass());

    public List<EntryKey> findEntryKeysByPersonId(String elementDefId, String personId) {
    	List<? extends ElementDataObject> elements = findByPersonId(elementDefId, personId);
    	
    	List<EntryKey> entryKeys = new ArrayList<EntryKey>();
    	
    	if(elements != null) {
			
		    for (ElementDataObject elementDataObject : elements) {
		        entryKeys.add(elementDataObject.getEntryKey());
		    }
    	}
    	
    	return entryKeys;
    }
    
    public List<? extends ElementDataObject> findByPersonId(String elementDefId, String personId) {
    	   	
        ElementDefinition elementDefinition = elementDefinitionManager.findByElementId(elementDefId);
        
        if (elementDefinition != null) {
	        
	        ElementHome elementHome = elementDefinition.getElementHome();
	        
	        if (elementHome != null) {
		        
		        List<? extends ElementDataObject> elements = null;
				try {
					elements = elementHome.findByPersonId(personId);
				} catch (BSFException e) {
					logService.debug(e);
				} catch (Exception e) {
					logService.debug(e);
				}
		        
		        if (elements != null) {
			        
			        for (ElementDataObject elementDataObject : elements) {
			        	// elementDataObject.getElementDefinition().isDeletable();
			            elementDataObject.setElementDefinition(elementDefinition);
			        }
			        
			        return elements;
	        
		        }
	        }
        }
        
        return null;
    }
    
    @SuppressWarnings("unchecked")
	public List<ElementDataObject> findByPersonId(String personId) {
    	
        List<ElementDefinition> elementDefList = elementDefinitionManager.findAll();
        
        if (elementDefList != null) {
	        
        	for(Iterator<ElementDefinition> i = elementDefList.iterator(); i.hasNext(); ) {
	        	
        		ElementDefinition e = i.next();
        		
		        ElementHome elementHome = e.getElementHome();
		        
		        if (elementHome != null) {
		            
			        List<ElementDataObject> elements = null;
					try {
						elements = (List<ElementDataObject>) elementHome.findByPersonId(personId);
					} catch (BSFException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
			        
			        if (elements != null) {
				        
				        for (ElementDataObject elementDataObject : elements) {
				        	logService.debug("e.getClassName(): " + e.getClassName());
				        	logService.debug("elementDataObject.getClass(): " + elementDataObject.getClass().toString());
				            elementDataObject.setElementDefinition(e);
				        }
				        
				        return elements;
		        
			        }
		        }
        	}
        }
        
    	return null;
    }

    public ElementDataObject findElementInstance(String elementDefId, String personId, BigDecimal entryId) {
    	return findElementInstance(elementDefId, personId, entryId, false, personId);
    }
    
    public ElementDataObject findElementInstance(String elementDefId, String personId, BigDecimal entryId, boolean isPublic, String requestPersonId) {
        ElementDefinition elementDefinition = elementDefinitionManager.findByElementId(elementDefId);
        ElementHome elementHome = elementDefinition.getElementHome();
        
        // logService.debug(" ==> elementHome: " + elementHome.getClass().getCanonicalName());
        
        ElementDataObject element = null;
		try {
			
			if(personId != null) {
				
				boolean good = false;
				Person entryPerson   = personHome.findByPersonId(personId);
				
				if(entryPerson.getUsertype() == UserType.CMTY) {
					
					Person requestPerson = personHome.findByPersonId(requestPersonId);
	
					Community community = communityManager.getCommunityById(entryPerson.getCommunityId());
					
					if(community != null) {
						List<Person> members = communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.MEMBER);
						members.addAll(communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.ASSESSMENT_COORDINATOR));
						members.addAll(communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.EVALUATOR));
						members.addAll(communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.COMMUNITY_COORDINATOR));
					
						if(members.contains(requestPerson)) {
							good = true;
						}
	
					}
					
				}
				else {
					good = true;
				}
				
				if (good) {
					
					element = elementHome.findElementInstance(personId, entryId);
					
					if(element == null) {
						logService.debug("===> element was returned null");
					}
					
				}
				else {
					logService.debug("==> good == false");
				}
			}
			else {
				logService.debug("===> personId is " + personId);
			}
			
		} catch (BSFException e) {
			// TODO Auto-generated catch block
			logService.debug(e);
		} catch (Exception e) {
			
			logService.debug(e);
			
		}
		
		
        if (element != null) {
            element.setElementDefinition(elementDefinition);
        }
        else {
        
        	logService.debug("===> element is null.");
        	
        }
        
        return element;
    }

    public ElementDataObject findElementInstance(EntryKey entryKey) {
    	Person person = personHome.findByPersonId(entryKey.getPersonId());
    	return findElementInstance(entryKey, person);
    }
    
    public ElementDataObject findElementInstance(EntryKey entryKey, Person person) {
        return findElementInstance(entryKey.getElementId(), entryKey.getPersonId(), entryKey.getEntryId(), entryKey.isPublic(), person != null ? person.getPersonId() : null);
    }

    public int findCountByPersonId(String elementDefId, String personId) {
        if(findByPersonId(elementDefId, personId) != null)
        	return findByPersonId(elementDefId, personId).size();
        
        return 0;
    }

    public List<BigDecimal> findEntryIdsByPersonId(String elementDefId, String personId) {
        List<BigDecimal> result = new ArrayList<BigDecimal>();
        List<? extends ElementDataObject> cachedEntries = findByPersonId(elementDefId, personId);
        
        if ( cachedEntries != null) {
	        
	        for (ElementDataObject elementDataObject : cachedEntries) {
	            result.add(elementDataObject.getEntryId());
	        }
        
        }
        
        return result;
    }

    public List<BigDecimal> findEntryIdsMatchingQuery(String elementDefId, String personId, String entryName) {
        List<BigDecimal> result = new ArrayList<BigDecimal>();
        List<? extends ElementDataObject> cachedEntries = findByPersonId(elementDefId, personId);
        
        if ( cachedEntries != null) {
	        
	        for (ElementDataObject elementDataObject : cachedEntries) {
	            if (elementDataObject.getEntryName().toLowerCase().contains((entryName.toLowerCase()))) {
	                result.add(elementDataObject.getEntryId());
	            } else  {
	                for (String tag : elementDataObject.getTags()) {
	                    if (tag.toLowerCase().contains(entryName.toLowerCase())) {
	                        result.add(elementDataObject.getEntryId());
	                    }
	                }
	            }
	        }
        
        }
        
        return result;
    }

    @Transactional
    public void remove(ElementDataObject data) {
        EntryKey entryKey = data.getEntryKey();
        ElementHome elementHome = entryKey.getElementDefinition().getElementHome();

        attachmentManager.detachAll(entryKey);
        attachmentManager.detachAllByAttachment(entryKey);
        tagManager.deleteTags(entryKey);
        wizardElementInstanceHome.deleteForEntry(entryKey);
        shareEntryHome.deleteForEntry(entryKey);
        elementFolderManager.deleteForEntry(entryKey);

        elementHome.remove(data);

        FileAccessor fileAccessor = entryKey.getElementDefinition().getFileAccessor();
        if (fileAccessor != null) {
            fileAccessor.deleteFile((FileElement) data);
        }
    }

    @Transactional
    public void remove(List<EntryKey> entryKeys) {
        for (EntryKey entryKey : entryKeys) {
            ElementDataObject entry = findElementInstance(entryKey.getElementId(), entryKey.getPersonId(), entryKey.getEntryId(), entryKey.isPublic(), entryKey.getPersonId());
            remove(entry);
        }
    }

    public void store(ElementDataObject data) {
        ElementHome elementHome = data.getElementDefinition().getElementHome();
        elementHome.store(data);
    }

    public ElementDataObject newInstance(ElementDefinition def) {
        try {
            ElementDataObject newInstance = (ElementDataObject) Class.forName(def.getClassName()).newInstance();
            newInstance.setElementDefinition(def);
            return newInstance;
        } catch (Exception e) {
            throw new IllegalStateException("can't create element from def: " + def.getId());
        }
    }

    public ElementDataObject newInstance(String elementDefId) {
        return newInstance(elementDefinitionManager.findByElementId(elementDefId));
    }

    public List<ElementDataObject> findFilesByPersonId(String personId) {
        List<ElementDataObject> nonImages = new ArrayList<ElementDataObject>();
        List<ElementDataObject> allFiles = findByPersonAndElementType(personId, "file");
        for (ElementDataObject elementDataObject : allFiles) {
            if (!((FileElement) elementDataObject).isImage()) {
                nonImages.add(elementDataObject);
            }
        }
        return nonImages;
    }
    
    @SuppressWarnings("unchecked")
	public List<Link> findEPFLinksByPersonId(String personId) {
    	// "epf_link", 
    	List<Link> links = new ArrayList<Link>();
    	List<? extends ElementDataObject> base = findByPersonId("epf_link", personId);
    	
    	links.addAll((Collection<? extends Link>) base);
    	
    	return links;
    }

    public List<ElementDataObject> findLinksByPersonId(String personId) {
        return findByPersonAndElementType(personId, "link");
    }

    public List<ElementDataObject> findPhotosByPersonId(String personId) {
        List<ElementDataObject> images = new ArrayList<ElementDataObject>();
        List<ElementDataObject> allFiles = findByPersonAndElementType(personId, "file");
        for (ElementDataObject elementDataObject : allFiles) {
            if (((FileElement) elementDataObject).isImage()) {
                images.add(elementDataObject);
            }
        }
        return images;
    }

    private List<ElementDataObject> findByPersonAndElementType(String personId, String elementTypeId) {
        List<ElementDataObject> files = new ArrayList<ElementDataObject>();
        List<ElementDefinition> defs = elementDefinitionManager.findByElementTypeId(elementTypeId);
        for (ElementDefinition elementDefinition : defs) {
            files.addAll(findByPersonId(elementDefinition.getId(), personId));
        }
        return files;
    }

    public List<ElementDataObject> loadAll(List<EntryKey> keys) {
        List<ElementDataObject> list = new ArrayList<ElementDataObject>();
        for (EntryKey entryKey : keys) {
            ElementDefinition elementDefinition = entryKey.getElementDefinition();
            ElementDataObject entry = null;
			
            try {
				entry = elementDefinition.getElementHome().findElementInstance(entryKey.getPersonId(), entryKey.getEntryId());
			} catch (BSFException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if( entry != null ) {
				entry.setElementDefinition(elementDefinition);
				list.add(entry);
			}
        }
        return list;
    }


	public boolean elementInstanceExist(String elementDefId, String personId, BigDecimal entryId, String requestPersonId) {

        ElementDefinition elementDefinition = elementDefinitionManager.findByElementId(elementDefId);
        ElementHome elementHome = elementDefinition.getElementHome();
        
        // logService.debug(" ==> elementHome: " + elementHome.getClass().getCanonicalName());
        
        boolean element = false;
        
		try {
			
			boolean good = false;
			Person entryPerson   = personHome.findByPersonId(personId);
			
			if(entryPerson.getUsertype() == UserType.CMTY) {
				
				Person requestPerson = personHome.findByPersonId(requestPersonId);

				Community community = communityManager.getCommunityById(entryPerson.getCommunityId());
				
				if(community != null) {
					List<Person> members = communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.MEMBER);
					members.addAll(communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.ASSESSMENT_COORDINATOR));
					members.addAll(communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.EVALUATOR));
					members.addAll(communityManager.getMembers(String.valueOf(community.getId()), CommunityRoleType.COMMUNITY_COORDINATOR));
				
					if(members.contains(requestPerson)) {
						good = true;
					}

				}
				
			}
			else {
				good = true;
			}
			
			if (good) {
				element = elementHome.elementInstanceExist(personId, entryId);
			}
			
		} catch (Exception e) {
			
			logService.debug(e);
			
		}
		
		
        if (element) {
            return true;
        }
        
		return false;
	}
}
