<!-- $Name: ump5-5_9_36_10_7-ajokela $ -->
<!-- $Id: carStrongIntInventory.jsp,v 1.3 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>
<p>Report the results of a Strong Interest Inventory.</p>
<h3>Name of Inventory</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<c:out value="${dataDef.entryName}" />
	</c:when>
	<c:otherwise>
		<html:text property="entryName" size="25" maxlength="50" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>

<br />
<br />
<h3>Date Taken</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
		<ospi:datefield name="dataDef" property="inventoryDate"
			mode="<%=PortfolioTagConstants.TEXTONLY%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
	</c:when>
	<c:otherwise>
		<ospi:datefield name="dataDef" property="inventoryDate"
			mode="<%=PortfolioTagConstants.EDIT%>"
			flag="<%=PortfolioTagConstants.MONTH%>" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>
<br />
<br />
<table border="0" cellspacing="0" cellpadding="4">
	<tr valign="top">
		<td>
		<h3>General occupational themes</h3>
		<table border="0" cellspacing="0" cellpadding="4"
			summary="This table is organized by theme, code, and interest rating for six interests.  Enter your rating for each interest using these codes: V = Very high, H = High, A = Average, L = Little and VL = Very little."
			style="font-size: .75em">
			<tr valign="top" bgcolor="#DDDDDD">
				<td class="BodyStyleBold" scope="col" align="right">Theme</td>
				<td class="BodyStyleBold" scope="col" align="center">Code</td>
				<td class="BodyStyle" scope="col" colspan="2"><span
					class="BodyStyleBold">Interest Rating</span></td>
			</tr>
			<tr>
				<td class="BodyStyle" align="right" scope="row"
					style="white-space: nowrap;" bgcolor="#DDDDDD">Realistic</td>
				<td class="BodyStyleBold" scope="row" align="center"
					bgcolor="#DDDDDD">R</td>
				<td bgcolor="#EEEEEE"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.realistic}" />
					</c:when>
					<c:otherwise>
						<html:select property="realistic" size="1">
							<html:option value="">- Select Rating -</html:option>
							<html:option value="V">Very High</html:option>
							<html:option value="H">High</html:option>
							<html:option value="A">Average</html:option>
							<html:option value="L">Little</html:option>
							<html:option value="VL">Very Little</html:option>
						</html:select>
					</c:otherwise>
				</c:choose> &nbsp;</td>
			</tr>
			<tr>
				<td class="BodyStyle" scope="row" align="right"
					style="white-space: nowrap;" bgcolor="#DDDDDD">Investigative</td>
				<td class="BodyStyleBold" scope="row" align="center"
					bgcolor="#DDDDDD">I</td>
				<td bgcolor="#EEEEEE"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.investigative}" />
					</c:when>
					<c:otherwise>
						<html:select property="investigative" size="1">
							<html:option value="">- Select Rating -</html:option>
							<html:option value="V">Very High</html:option>
							<html:option value="H">High</html:option>
							<html:option value="A">Average</html:option>
							<html:option value="L">Little</html:option>
							<html:option value="VL">Very Little</html:option>
						</html:select>
						<!-- html:text property="investigative" size="2" maxlength="2"/ -->
					</c:otherwise>
				</c:choose> &nbsp;</td>
			</tr>
			<tr>
				<td class="BodyStyle" scope="row" align="right"
					style="white-space: nowrap;" bgcolor="#DDDDDD">Artistic</td>
				<td class="BodyStyleBold" scope="row" align="center"
					bgcolor="#DDDDDD">A</td>
				<td bgcolor="#EEEEEE"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.artistic}" />
					</c:when>
					<c:otherwise>
						<html:select property="artistic" size="1">
							<html:option value="">- Select Rating -</html:option>
							<html:option value="V">Very High</html:option>
							<html:option value="H">High</html:option>
							<html:option value="A">Average</html:option>
							<html:option value="L">Little</html:option>
							<html:option value="VL">Very Little</html:option>
						</html:select>

					</c:otherwise>
				</c:choose> &nbsp;</td>
			</tr>
			<tr>
				<td class="BodyStyle" scope="row" align="right"
					style="white-space: nowrap;" bgcolor="#DDDDDD">Social</td>
				<td class="BodyStyleBold" scope="row" align="center"
					bgcolor="#DDDDDD">S</td>
				<td bgcolor="#EEEEEE"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.social}" />
					</c:when>
					<c:otherwise>
						<html:select property="social" size="1">
							<html:option value="">- Select Rating -</html:option>
							<html:option value="V">Very High</html:option>
							<html:option value="H">High</html:option>
							<html:option value="A">Average</html:option>
							<html:option value="L">Little</html:option>
							<html:option value="VL">Very Little</html:option>
						</html:select>

					</c:otherwise>
				</c:choose> &nbsp;</td>
			</tr>
			<tr>
				<td class="BodyStyle" scope="row" align="right"
					style="white-space: nowrap;" bgcolor="#DDDDDD">Enterprising</td>
				<td class="BodyStyleBold" scope="row" align="center"
					bgcolor="#DDDDDD">E</td>
				<td bgcolor="#EEEEEE"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.enterprising}" />
					</c:when>
					<c:otherwise>
						<html:select property="enterprising" size="1">
							<html:option value="">- Select Rating -</html:option>
							<html:option value="V">Very High</html:option>
							<html:option value="H">High</html:option>
							<html:option value="A">Average</html:option>
							<html:option value="L">Little</html:option>
							<html:option value="VL">Very Little</html:option>
						</html:select>

					</c:otherwise>
				</c:choose> &nbsp;</td>
			</tr>
			<tr>
				<td class="BodyStyle" scope="row" align="right"
					style="white-space: nowrap;" bgcolor="#DDDDDD">Conventional</td>
				<td class="BodyStyleBold" scope="row" align="center"
					bgcolor="#DDDDDD">C</td>
				<td bgcolor="#EEEEEE"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.conventional}" />
					</c:when>
					<c:otherwise>
						<html:select property="conventional" size="1">
							<html:option value="">- Select Rating -</html:option>
							<html:option value="V">Very High</html:option>
							<html:option value="H">High</html:option>
							<html:option value="A">Average</html:option>
							<html:option value="L">Little</html:option>
							<html:option value="VL">Very Little</html:option>
						</html:select>

					</c:otherwise>
				</c:choose> &nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr valign="middle">
		<td align="left" style="white-space: nowrap;" colspan="2" class="text">
		<br />
		<br />
		<span class="Label">Basic interest scales</span> <c:if
			test="${param.action == 'view'}">
			<br />
      Enter the five areas identified by the inventory in
      which you show the most interest.
           </c:if></td>
	</tr>
	<tr valign="top" align="left">
		<td class="BodyStyle" colspan="2">
		<table border="0" cellspacing="0" cellpadding="4" width="100%">
			<tr>
				<td class="Label" width="1%">1</td>
				<td width="99%" class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.interest1}" />
					</c:when>
					<c:otherwise>
						<html:text property="interest1" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">2</td>
				<td width="99%" class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.interest2}" />
					</c:when>
					<c:otherwise>
						<html:text property="interest2" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">3</td>
				<td width="99%" class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.interest3}" />
					</c:when>
					<c:otherwise>
						<html:text property="interest3" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">4</td>
				<td width="99%" class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.interest4}" />
					</c:when>
					<c:otherwise>
						<html:text property="interest4" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">5</td>
				<td width="99%" class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.interest5}" />
					</c:when>
					<c:otherwise>
						<html:text property="interest5" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr valign="top" align="left">
		<td style="white-space: nowrap;" class="text" colspan="2"><span
			class="Label"> <br />
		<br />
		<br>
		Occupational scales</span> <c:if test="${param.action == 'view'}">
			<br />
      Enter the 10 occupations of people with similar interests
      identified by the inventory.
      </c:if></td>
	</tr>
	<tr valign="top" align="left">
		<td class="BodyStyle" colspan="2">
		<table border="0" cellspacing="0" cellpadding="4" width="100%">
			<tr>
				<td class="Label" width="1%">1</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation1}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation1" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">2</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation2}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation2" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">3</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation3}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation3" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">4</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation4}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation4" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">5</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation5}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation5" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">6</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation6}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation6" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">7</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation7}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation7" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">8</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation8}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation8" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">9</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation9}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation9" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
			<tr>
				<td class="Label" width="1%">10</td>
				<td class="BodyStyle"><c:choose>
					<c:when test="${param.action == 'view'}">
						<c:out value="${dataDef.occupation10}" />
					</c:when>
					<c:otherwise>
						<html:text property="occupation10" size="25" maxlength="50" />
					</c:otherwise>
				</c:choose></td>
			</tr>
		</table>

		<h3><br />
		<br />
		Interpretation / Reaction</h3>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words) <br />
		<c:choose>
			<c:when test="${param.action == 'view'}">
				<c:out value="${dataDef.interpretation}" />
			</c:when>
			<c:otherwise>
				<html:textarea property="interpretation" cols="40" rows="5"
					onkeyup="limitChar(this,4000)" />
			</c:otherwise>
		</c:choose></p>
		</td>
	</tr>
</table>
