<%@ tag body-content="scriptless"%>
<%@ attribute name="viewMode" required="true"%>
<%@ attribute name="pageTitle" required="false"%>
<%@ attribute name="jsModules" required="false"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>

<jsp:include page="/pageHeader.jsp">
	<jsp:param name="viewMode" value="${viewMode}" />
	<jsp:param name="pageTitle" value="${pageTitle}" />
	<jsp:param name="jsModules" value="${jsModules}" />
</jsp:include>

<jsp:doBody />

<jsp:include page="/pageFooter.jsp">
	<jsp:param name="viewMode" value="${viewMode}" />
</jsp:include>