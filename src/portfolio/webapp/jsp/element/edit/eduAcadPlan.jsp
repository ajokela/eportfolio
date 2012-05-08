<!-- $Name:  $ -->
<!-- $Id: eduAcadPlan.jsp,v 1.5 2011/01/27 17:37:01 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="org.portfolio.client.tags.PortfolioTagConstants"%>

<p>An academic plan is a plan of coursework to satisfy degree
requirements.</p>
<h3>Term</h3>
<c:choose>
	<c:when test="${param.action == 'view'}">
      ${fn:escapeXml(dataDef.entryName)}"/>
   </c:when>
	<c:otherwise>
		<html:text property="entryName" size="50" maxlength="50" />
		<span class="required">* Required</span>
	</c:otherwise>
</c:choose>
<br />
<br />

<table border="0" cellspacing="1" cellpadding="4"
	summary="This table is orgaized for you to input your academic plan.  It is categorized by class number, subject, course number, class title, and credits.">
	<tr valign="top" align="left">
		<td style="font-size: 10pt;">
		<p>Class</p>
		</td>
		<td style="font-size: 10pt;">
		<p>Subject</p>
		</td>
		<td style="font-size: 10pt;">
		<p>Course</p>
		</td>
		<td style="font-size: 10pt;">
		<p>Class Title</p>
		</td>
		<td style="font-size: 10pt;">
		<p>Credits</p>
		</td>
	</tr>
	<tr valign="top" align="left">

		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.class1)}
            </c:when>
			<c:otherwise>
				<html:text property="class1" size="5" maxlength="5" />
			</c:otherwise>
		</c:choose></td>

		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.subject1)}
            </c:when>
			<c:otherwise>
				<html:text property="subject1" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>

		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.course1)}
            </c:when>
			<c:otherwise>
				<html:text property="course1" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>

		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.title1)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="title1" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.credits1)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="credits1" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
	</tr>

	<tr valign="top" align="left">
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.class2)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="class2" size="5" maxlength="5" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.subject2)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="subject2" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.course2)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="course2" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.title2)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="title2" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.credits2)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="credits2" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.class3)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="class3" size="5" maxlength="5" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.subject3)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="subject3" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.course3)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="course3" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.title3)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="title3" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.credits3)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="credits3" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.class4)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="class4" size="5" maxlength="5" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.subject4)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="subject4" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.course4)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="course4" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.title4)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="title4" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.credits4)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="credits4" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.class5)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="class5" size="5" maxlength="5" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.subject5)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="subject5" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.course5)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="course5" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.title5)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="title5" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.credits5)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="credits5" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.class6)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="class6" size="5" maxlength="5" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.subject6)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="subject6" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.course6)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="course6" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.title6)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="title6" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.credits6)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="credits6" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr valign="top" align="left">
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.class7)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="class7" size="5" maxlength="5" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.subject7)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="subject7" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.course7)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="course7" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.title7)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="title7" size="25" maxlength="35" />
			</c:otherwise>
		</c:choose></td>
		<td><c:choose>
			<c:when test="${param.action == 'view'}">
               ${fn:escapeXml(dataDef.credits7)}"/>
            </c:when>
			<c:otherwise>
				<html:text property="credits7" size="4" maxlength="4" />
			</c:otherwise>
		</c:choose></td>
	</tr>
</table>

<h3><br />
<br />
Comments</h3>
<p>&nbsp;&nbsp;&nbsp;&nbsp;(Limit approximately 600 words)<br />
<c:choose>
	<c:when test="${param.action == 'view'}">
      ${dataDef.comments}
   </c:when>
	<c:otherwise>
	
			<div>
				<div>
					<div style="width: 70%; float: left; display: block;">
						&nbsp;
					</div>
					<div style="width: 25%; float: right; display: block; vertical-align: bottom;">
						<span id="commentsCounter"></span>
					</div>
				</div>
				<div style="clear: both;"></div>
				
				<div>
					<textarea name="comments" id="elementComments" cols="56" rows="5" autocomplete="off" class="plainText">${dataDef.comments}</textarea>
				</div>
	
				<script type="text/javascript">
				           new EPF.widget.TextareaCounter({
				               textareaEl: 'elementComments',
				               counterEl: 'commentsCounter',
				               max: 4000
				           });
			    </script>
			    
		    </div>

	</c:otherwise>
</c:choose>