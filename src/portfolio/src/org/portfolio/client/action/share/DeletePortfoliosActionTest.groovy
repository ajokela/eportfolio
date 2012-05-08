/* $Name:  $ */
/* $Id: DeletePortfoliosActionTest.groovy,v 1.2 2010/10/27 19:28:15 ajokela Exp $ */
package org.portfolio.client.action.share

import org.apache.struts.mock.MockHttpServletRequestimport org.apache.struts.mock.MockHttpServletResponseimport org.apache.struts.action.ActionMappingimport org.junit.Beforeimport org.apache.struts.mock.MockFormBeanimport org.junit.Test

/**
 * @author Matt Sheehan
 *
 */
public class DeletePortfoliosActionTest{
	
	DeletePortfoliosAction deletePortfoliosAction
	MockHttpServletRequest request
	MockHttpServletResponse response
	MockFormBean actionForm
	ActionMapping actionMapping
	
	@Before
	public void setUp() {
		deletePortfoliosAction = new DeletePortfoliosAction()
		request = new MockHttpServletRequest()
		response = new MockHttpServletResponse()
		actionForm = new MockFormBean()
		actionMapping = new ActionMapping()
	}
	
	@Test
	public void testSuccess() {
        request.addParameter("ids", "1")
        request.addParameter("ids", "2")
        request.addParameter("ids", "3")
	}
}
