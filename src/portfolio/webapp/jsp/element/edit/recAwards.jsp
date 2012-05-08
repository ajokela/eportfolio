<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: recAwards.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>

<h3>Name of award</h3>
<html:text property="entryName" size="25" maxlength="50" />
<span class="required">* Required</span>
<br />
<br />
<h3>Date received</h3>
<ospi:datefield name="dataDef" property="rcdDate"
	mode="<%=PortfolioTagConstants.EDIT%>"
	flag="<%=PortfolioTagConstants.MONTH%>" />
<br />
<br />
<h3>Organization</h3>
<html:text property="organization" size="25" maxlength="50" />
<h3><br />
<br />
Description</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<html:textarea property="description" cols="40" rows="8"
	onkeyup="limitChar(this,4000)" /></p>
