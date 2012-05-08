<!-- $Name:  $ -->
<!-- $Id: simError.jsp,v 1.3 2010/11/23 20:34:57 ajokela Exp $ -->
<%-- Error Simulation --%>

<!-- 

This page simulates an error condition which can be used for testing and 

debugging the error page itself.  Whenever this page is accessed, it will throw 

an exception causing the user to be redirected to the error page.

-->

<html>

<head>

<title>Error Simulation</title>

</head>

<body bgcolor="#ffffff">

<p>
<ul>

	<center>

	<h2>Error simulation page...</h2>

	<h2>This page will cause an error</h2>

	</center>

</ul>

</body>

</html>

<% 

if(true) throw new Exception("This is a test exception!");

%>

