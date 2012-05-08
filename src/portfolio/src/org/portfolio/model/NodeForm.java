/* $Name:  $ */
/* $Id: NodeForm.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/NodeForm.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.7 $
 * $Date: 2010/10/27 19:24:57 $
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

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


public class NodeForm extends BaseValidatorForm implements Serializable {

   private static final long serialVersionUID = -3029728899933178742L;
   private String name;
   private String id;
   private String type = "-1"; // default is category type
   private String tipData;
   private boolean isNew = true;

   public NodeForm() {
      super();
   }

   public ActionErrors validate(ActionMapping mapping,
                                HttpServletRequest request) {

      ActionErrors errors = null;
      if (action != null && !action.equals("add")) {
         errors = super.validate(mapping, request);
      }
      return errors;
   }

   public boolean isMaxDepth() {
      return id.length() >= 14;
   }

   public int getDepth(){
      return id.length() / 2;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public boolean getIsNew() {
      return isNew;
   }

   public void setIsNew(boolean isNew) {
      this.isNew = isNew;
   }

   public String getTipData() {
      return tipData;
   }

   public void setTipData(String tipData) {
      this.tipData = tipData;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name.replaceAll("\\s", " ");
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }
}
