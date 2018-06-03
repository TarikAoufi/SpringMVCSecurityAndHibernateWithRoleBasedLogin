<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<spring:theme code="stylesheet" var="themeName" />
	<link href='<spring:url value="/resources/css/${themeName}"/>' rel="stylesheet" />
	<title><spring:message code="accessdenied.page.title"/></title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href='<spring:url value="/static/css/menu.css" />' rel="stylesheet" />
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<!-- Page incluant l'entête -->
    <jsp:include page="header.jsp"/>
	<div class="generic-container">
		<!-- Page incluant le menu -->
   	    <div class="container-menu"><jsp:include page="menu.jsp"/> </div>
		<div class="authbar">
			<span><spring:message code="accessdenied.msg1.user"/> <strong>${loggedonuser}</strong>, <spring:message code="accessdenied.msg2.user"/></span> 
		</div>
	</div>
</body>

</html>
