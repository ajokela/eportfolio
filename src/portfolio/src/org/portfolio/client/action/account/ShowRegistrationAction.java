/* $Name:  $ */
/* $Id: ShowRegistrationAction.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
/*
* $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/action/account/ShowRegistrationAction.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $
* $Revision: 1.3 $
* $Date: 2010/10/27 19:24:56 $
*
 * =====================================================================
*
* Copyright (c) 2003, 2001, 1999, 1996
*    by the University of Minnesota, Web Development.
* All rights reserved.
*
* Contact Web Development at 1300 S. 2nd Street, Suite 660 WBOB,
*                            Minneapolis, MN 55454.
* Web Development is a unit of the Office of Information Technology (OIT).
*
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
* OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED.  IN NO EVENT SHALL THE UNIVERSITY OF MINNESOTA OR
* ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
* USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
* OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
* SUCH DAMAGE.
*
* It is agreed that the below parties will embark upon an effort to
* transform the existing University of Minnesota Portfolio code base
* into a viable open source product. It is necessary to document the
* agreement between the three principle parties:
* the University of Minnesota, The RSmart Group, and the University
* of Delaware.
*
* The following basic tenets will govern the initial effort:
* .    Each party will provide resources to achieve the development
*      goals.
* .    Each party will provide a good faith effort to further the
*      adoption of the code base in the higher education market.
* .    All work that is performed in the initial development period
*     will be placed into the open source realm.
* .    No disclosure of code or intellectual property will occur
*     outside of the partnership during the development period.
* .    No redistribution of source or binary code may take place
*      during the development period outside of the above tenets.
*/
package org.portfolio.client.action.account;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.client.action.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action has been created to allow external users to create themselves an account.
 *
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.3 $ $Date: 2010/10/27 19:24:56 $
 * @since 11 Feb 2003
 */
public class ShowRegistrationAction extends BaseAction {

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {

        return mapping.findForward(FORWARD_SUCCESS);
    }

}
