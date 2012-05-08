<!-- $Name:  $ -->
<!-- $Id: googleTracker.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<c:if
	test="${applicationScope['org.portfolio.tracking.google.enabled']}">
	<script type="text/javascript">
    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
    document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    </script>
	<script type="text/javascript">
    var pageTracker = _gat._getTracker("${applicationScope['org.portfolio.tracking.google.accountId']}");
    pageTracker._trackPageview();
  </script>
</c:if>
