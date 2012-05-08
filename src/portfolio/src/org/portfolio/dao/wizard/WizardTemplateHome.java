/* $Name:  $ */
/* $Id: WizardTemplateHome.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.util.List;

import org.portfolio.model.Template;

/**
 * @author Matt Sheehan
 *
 */
public interface WizardTemplateHome {

	/**
	 * Find the templates associated with the given wizard.
	 */
	public List<Template> getTemplates(int wizardId);
}
