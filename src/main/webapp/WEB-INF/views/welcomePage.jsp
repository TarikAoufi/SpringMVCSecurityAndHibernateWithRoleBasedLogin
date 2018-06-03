<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<spring:message code=""/>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<spring:theme code="stylesheet" var="themeName" />
	<link href='<spring:url value="/static/css/menu.css" />' rel="stylesheet" />
	<link href='<spring:url value="/resources/css/${themeName}"/>' rel="stylesheet" />
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
	<script	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<link href='<spring:url value="/static/css/app.css" />' rel="stylesheet" />
	<title><spring:message code="header.title" /></title>
</head>

<body>
   <!-- Page incluant l'entête -->
   <jsp:include page="header.jsp"/>

	<div role="main" class="container">   
      <!-- Page incluant le menu -->
   	  <jsp:include page="menu.jsp"/>
   	  
      <div class="jumbotron">
         <h2><spring:message code="welcome.page.body-title"/></h2>
         <br>
         <p class="lead"><spring:message code="welcome.page.body1"/></p>
         
		 <p class="tbw"><spring:message code="welcome.page.body2"/></p>
		 <p class="lead"><spring:message code="welcome.page.body3"/></p>
		
         <hr/> 
      </div>
   </div>  
</body>
</html>