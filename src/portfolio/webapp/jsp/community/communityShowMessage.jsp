<!-- $Name:  $ -->
<!-- $Id: communityShowMessage.jsp,v 1.1 2010/11/23 14:29:11 ajokela Exp $ -->
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
	<head>
		<title>${message.subject}</title>
		<link rel="shortcut icon" href="/favicon.ico" />
  		<link rel="stylesheet" href="/js/yui/2.8.1/build/grids/grids-min.css" type="text/css" />
  		<link rel="stylesheet" type="text/css" href="/css/modules/core.css" />
  
  		<script type="text/javascript" src="/js/prototype.js"></script>
  		<script type="text/javascript" src="/js/scriptaculous/1.8.3/scriptaculous.js?load=effects,dragdrop,controls"></script>
  		<script type="text/javascript" src="/js/yui/2.8.1/build/yuiloader/yuiloader-min.js"></script>  
  		<script type="text/javascript" src="/js/EPF.js"></script>  
  		<script type="text/javascript" src="/js/modules/core.js"></script>
  		<script type="text/javascript" src="/js/tiny_mce/tiny_mce.js"></script>
  
	</head>
	<body>
		<form action="#" method="get" onSubmit="return false;">
			<div style="padding: 2px; margin: 2px; background-color: #cccccc;">
				<div style="padding: 1px; background-color: #ffffff; text-align: left;">
				
					<div style="text-align: left; width: 100%; font-size: 12pt;">
						<span style="font-weight: bold;">Subject:&nbsp;</span>${fn:escapeXml(message.subject)}
					</div>
				
					<div style="text-align: right; width: 100%;">
						Sent: <fmt:formatDate value="${message.startedAt}" pattern="MM/dd/yyyy" />
					</div>
					
					<div>
						<input type="text" size="60" style="border: 1px solid #000000;" value="${message.coordinator.displayName}" readonly="readonly" />
					</div>
					<div>
						<span style="font-size: 8pt;">Coordinator</span><br />
					</div>
					
					<div>
						<textarea rows="15" cols="64" style="border: 1px solid #000000; font-size: 10pt;" readonly="readonly">${fn:escapeXml(message.messageTxt)}</textarea>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>