<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<spring:theme code="stylesheet" var="themeName" />
	<link href='<spring:url value="/resources/css/${themeName}"/>' rel="stylesheet" />
	<title><spring:message code="registrationsuccess.page.title"/></title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href='<spring:url value="/static/css/menu.css" />' rel="stylesheet" />
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<!-- Page incluant l'entête -->
    <jsp:include page="header.jsp"/>
    <%@include file="authheader.jsp" %>
	<div class="generic-container">
		<!-- Page incluant le menu -->
   	    <div class="container-menu"><jsp:include page="menu.jsp"/> </div>
		<div class="alert alert-success lead">
	    	<spring:message code="registrationsuccess.msg.user"/>${success}<spring:message code="registrationsuccess.msg.registration"/>
		</div>
		<c:choose>
			<c:when test="${not empty pageContext.request.userPrincipal}">
				<span class="well floatRight">
					<spring:message code="registrationsuccess.msg1.userslist"/> <a href="<c:url value='/list' />"><spring:message code="registrationsuccess.msg2.userslist"/></a>
				</span>
			</c:when>
			<c:otherwise>
				<span class="well floatLeft">
					<a href="<c:url value='/list' />"><spring:message code="registrationsuccess.msg1.login"/></a> <spring:message code="registrationsuccess.msg2.login"/>
				</span>
			</c:otherwise>
		</c:choose>
	</div>
</body>

</html>