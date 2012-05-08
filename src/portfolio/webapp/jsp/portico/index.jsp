<!-- $Name:  $ -->
<!-- $Id: index.jsp,v 1.8 2010/11/23 20:34:59 ajokela Exp $ -->
<%@ page contentType="application/x-java-jnlp-file; charset=UTF-8"%>
<?xml version="1.0" encoding="utf-8"?>

<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/portfolio-custom.tld" prefix="ospi"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jnlp codebase="<c:out value="${baseUrl}"></c:out>/portico"
	href="portico.jnlp" spec="1.0+"> <information>
<title>ePortfolio Portico</title>
<vendor>University of Minnesota</vendor> <homepage
	href="/services/portico" /> <description>Portable package
of files for use as ePortfolio materials and printing of Portfolios.</description> <icon
	href="/images/DesktopPorticoLogo.gif" /> <!--  icon kind="splash" href="/images/WebStartPorticoLogo.gif"/ -->
<offline-allowed /> </information> <j2se version="1.5+" /> <resources> <jar
	download="eager" href="jars/PortfolioPortico.jar" main="true" /> <jar
	download="eager" href="jars/swing-layout-1.0.1.jar" main="false" /> <jar
	download="eager" href="jars/AbsoluteLayout.jar" main="false" /> <jar
	download="eager" href="jars/commons-codec-1.4.jar" main="false" /> <jar
	download="eager" href="jars/commons-logging-1.1.jar" main="false" /> <jar
	download="eager" href="jars/commons-httpclient-3.1.jar" main="false" />
<jar download="eager" href="jars/log4j-1.2.7.jar" main="false" /> <jar
	download="eager" href="jars/swing-worker-1.1.jar" main="false" /> </resources> <application-desc
	main-class="org.portfolio.portico.PortfolioPortico" /> <security>
<all-permissions /> </security> </jnlp>
