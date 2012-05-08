/* $Name:  $ */
/* $Id: BaseForm.java,v 1.6 2010/11/04 21:08:53 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/BaseForm.java,v 1.6 2010/11/04 21:08:53 ajokela Exp $
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

import org.apache.struts.action.ActionForm;
import org.portfolio.util.LogService;

abstract public class BaseForm extends ActionForm {
   /**
	 * 
	 */
	private static final long serialVersionUID = -5711934141369824332L;
	protected LogService logService = new LogService(this.getClass());
	protected String action;

   public String getAction() {
      return action;
   }

   public void setAction(String action) {
      this.action = action;
   }

   protected String[] removeNoSelection(String[] inputArray) {
        String[] finalArray = null;
        if(inputArray != null) {
            boolean noSelection = false;
            for(String str : inputArray) {
                if(str == null || str.trim().length() < 1) {
                    noSelection = true;
                }
            }

            if(noSelection && inputArray.length > 1) {
                finalArray = new String[inputArray.length -1];
                int j= 0;
                for (int i = 0; i < inputArray.length; i++) {
                    String str = inputArray[i];
                    if (str != null && str.trim().length() > 0) {
                        finalArray[j] = str;
                        j++;
                    }
                }
            }
            if(!noSelection) {
                finalArray = inputArray;
            }
        }
        return finalArray;
    }

    protected String removeSpecialCharacters(String input) {
        return input.replaceAll("\\|/|:|\\*|\\?|\"|<|>|'|%", "");

    }

}

