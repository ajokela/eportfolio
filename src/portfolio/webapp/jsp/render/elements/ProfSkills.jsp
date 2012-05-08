<!-- $Name:  $ -->
<!-- $Id: ProfSkills.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>

<div>
	<div style="display: block; float: left; width: 135px; font-weight: bold;">Description:</div>
	<div>${element.description}</div>
	<div style="clear: both;"></div>

	<div style="display: block; float: left; width: 135px; font-weight: bold;">Experience:</div>
	<div>${element.experience}</div>
	<div style="clear: both;"></div>

	<div style="display: block; float: left; width: 135px; font-weight: bold;">Reflection:</div>
	<div>${element.reflection}</div>
	<div style="clear: both;"></div>
</div>
