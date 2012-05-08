/* $Name:  $ */
/* $Id: CollectionGuideHome.java,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.util.List;

import org.portfolio.model.wizard.CollectionGuide;

/**
 * @author Matt Sheehan
 *
 */
public interface CollectionGuideHome {

	public void insert(CollectionGuide wizard);

	public void update(CollectionGuide wizard);

    public CollectionGuide find(int wizardId);

    public List<CollectionGuide> findByGroupId(int groupId);

	public List<CollectionGuide> findAll();

}
