<!-- $Name:  $ -->
<!-- $Id: porticoResponse.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ page import="java.util.Enumeration"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="org.apache.struts.action.ActionErrors"%>
<%@ page import="java.util.Iterator"%>

<%@ include file="taglibs.jsp"%>
<%
    if(pageContext.findAttribute(Globals.ERROR_KEY)!= null) {
%>
<html:messages id="error">
	<%
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    %>
	<bean:write name="error" filter="false" />
</html:messages>
<%
    }
    else {
%>
<%
    response.setStatus(HttpServletResponse.SC_OK);
    %>
<%
    }
%>
