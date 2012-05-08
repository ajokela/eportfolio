<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduDegreeReq.jsp,v 1.4 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>These are courses necessary for degree completion.</p>
<h3>Entry Name</h3>
<html:text property="entryName" size="25" maxlength="25" />

<br />
<br />
<h3>Catalog Year</h3>
<html:text property="bulletinYear" size="25" maxlength="25" />

<h3><br />
<br />
Requirements</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words) <br />
<html:textarea property="requirements" cols="40" rows="5"
	onkeyup="limitChar(this,4000)" /></p>
