/* $Name:  $ */
/* $Id: TemplateHome.java,v 1.13 2011/02/16 17:35:17 ajokela Exp $ */
package org.portfolio.dao.template;

import java.util.List;

import org.portfolio.model.Template;
import org.portfolio.model.community.Community;

/**
 * @author Matt Sheehan
 * 
 */
public interface TemplateHome {

    public int insert(Template template);

    public void update(Template template);

    public void delete(String templateId);
    
    public Template copyTemplate(Template template, Community community, boolean copyAMObjectives);

    public Template findTemplateById(String id);
    
    public Template findTemplateById(String id, String dateFrom, String dateTo);

    public List<Template> findPublishedTemplates();

    public List<Template> findAllTemplates();

    public List<Template> findByCommunityId(int communityId);

    public List<Template> findAssessableByCommunityId(String communityId);

    public List<Template> findPublishedByCommunityId(int parseInt);

    public List<Template> findUnpublishedByCommunityId(int parseInt);
    
}
