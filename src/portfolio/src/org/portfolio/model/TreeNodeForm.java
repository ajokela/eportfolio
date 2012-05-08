/* $Name:  $ */
/* $Id: TreeNodeForm.java,v 1.6 2010/11/04 21:08:53 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/TreeNodeForm.java,v 1.6 2010/11/04 21:08:53 ajokela Exp $
 * $Revision: 1.6 $
 * $Date: 2010/11/04 21:08:53 $
 *
 * =====================================================================
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 * copy of the License at http://www.theospi.org/.
 *
 * Software distributed under the License is distributed on an AS IS basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.  See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Copyrights:
 *
 * Portions Copyright (c) 2003 the r-smart group, inc.
 *
 * Acknowledgements
 *
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
 */

package org.portfolio.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;


public class TreeNodeForm extends ValidatorForm {

   /**
	 * 
	 */
	private static final long serialVersionUID = 4849199391478859402L;

	private String filterType = null;

   private String filterBy = null;

   private String attachTo = null;

   public TreeNodeForm() {
      super();
   }

   public ActionErrors validate(ActionMapping mapping,
                                HttpServletRequest request) {

      ActionErrors errors = null;
      return errors;
   }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(String filterBy) {
        this.filterBy = filterBy;
    }

    public String getAttachTo() {
        return attachTo;
    }

    public void setAttachTo(String attachTo) {
        this.attachTo = attachTo;
    }

}
