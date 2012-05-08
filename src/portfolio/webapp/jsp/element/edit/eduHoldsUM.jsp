<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: eduHoldsUM.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="2" class="Instructions">
		<h2>Any holds on U of M records</h2>
		</td>
	</tr>
</table>
<logic:empty name="dataDef" property="entryName">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center" style="white-space: nowrap;" class="noText"
				colspan="2">
			<h3>Any holds on U of M records</h3>
			</td>
		</tr>
	</table>
</logic:empty>
<logic:notEmpty name="dataDef" property="entryName">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="left" style="white-space: nowrap;" class="Label">
			<h3>Hold type</h3>
			</td>
			<td class="BodyStyle">
			<p><c:out value="${dataDef.holdType}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" class="Label">
			<h3>Institution</h3>
			</td>
			<td class="BodyStyle">
			<p><c:out value="${dataDef.institutionDesc}" /></p>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" class="Label"
				width="1%">
			<h3>Effective date</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.srvcIndActiveDt}" /></p>
			</td>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" class="Label"
				width="1%">
			<h3>Effective term</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<p><c:out value="${dataDef.termDesc}" /></p>
		</tr>
		<tr>
			<td align="left" style="white-space: nowrap;" class="Label"
				width="1%">
			<h3>Service(s) impacted</h3>
			</td>
			<td width="99%" class="BodyStyle"><logic:iterate id="services"
				name="dataDef" property="recordsList" indexId="index">
				<table width="70%" border="0" cellspacing="0" cellpadding="2">
					<c:choose>
						<c:when test="${index % 2 == 0}">
							<c:set value="#EEEEEE" var="color" />
						</c:when>
						<c:otherwise>
							<c:set value="#DDDDDD" var="color" />
						</c:otherwise>
					</c:choose>
					<tr bgcolor="<c:out value="${color}"/>">
						<td style="white-space: nowrap;" class="BodyStyle">
						<p><c:out value="${services.entryName}" /></p>
						</td>
					</tr>
				</table>
			</logic:iterate></td>
		</tr>
		<tr>
			<td align="left" class="Label" width="1%">
			<h3>Address to resolve hold(s)</h3>
			</td>
			<td width="99%" class="BodyStyle">
			<h3><c:out value="${dataDef.address}" /></h3>
			</td>
		</tr>
	</table>
</logic:notEmpty>
