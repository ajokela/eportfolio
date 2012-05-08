<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: perPhotoUM.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="2" class="Instructions">
		<p>The official U of M photo</p>
		</td>
	</tr>
	<logic:empty name="dataDef" property="entryName">
		<tr valign="top">
			<td align="center" style="white-space: nowrap;" colspan="2">
			<h3>No data available.</h3>
			</td>
		</tr>
	</logic:empty>
	<logic:notEmpty name="dataDef" property="entryName">
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%" valign="top">
			<h3>Type of photo:</h3>
			</td>
			<td width="99%">
			<p class="BodyStyle"><c:out value="${dataDef.entryName}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" width="1%" valign="top">
			<h3>Photo:</h3>
			</td>
			<td><img
				src="UCardPicture?personid=${ospi:encodeUriComponent(ospi:encrypt(dataDef.personId))}"
				width="125" height="125" alt="Official U of M Photo" border="0"></td>
		</tr>
	</logic:notEmpty>
</table>
