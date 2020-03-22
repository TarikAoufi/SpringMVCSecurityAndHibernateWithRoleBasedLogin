<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<spring:theme code="stylesheet" var="themeName" />
<link href='<spring:url value="/resources/css/${themeName}"/>' rel="stylesheet" />
<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
<link href='<spring:url value="/static/css/menu.css" />' rel="stylesheet" />
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<title><spring:message code="userslist.page.title"/></title>
</head>

<body>
    <!-- Page incluant l'entête -->
    <jsp:include page="header.jsp"/>
    <div id="myDIV">
  		<h5><%@include file="authheader.jsp" %></h5>
	</div>
    
	<div class="generic-container">
		<!-- Page incluant le menu -->
   	   <div class="container-menu"><jsp:include page="menu.jsp"/> </div>
		<div class="panel panel-default">
			  <!-- Default panel contents -->
		  	<div class="panel-heading"><span class="lead"><spring:message code="userslist.page.title"/></span></div>
			<table class="table table-hover">
	    		<thead>
		      		<tr>
		      			<th><spring:message code="userslist.table.username"/></th>
				        <th><spring:message code="userslist.table.firstname"/></th>
				        <th><spring:message code="userslist.table.lastname"/></th>
				        <th><spring:message code="userslist.table.email"/></th>				        
				        <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
				        	<th width="100"></th>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
				        	<th width="100"></th>
				        </sec:authorize>				        
					</tr>
		    	</thead>
	    		<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.userName}</td>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.email}</td>						
					    <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
							<td><a href="<c:url value='/edit-user-${user.userName}' />" class="btn btn-warning btn-xs custom-width"><spring:message code="userslist.btn.edit"/></a></td>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
							<td><a href="<c:url value='/delete-user-${user.userName}' />" class="btn btn-danger btn-xs custom-width"><spring:message code="userslist.btn.delete"/></a></td>
        				</sec:authorize>
					</tr>
				</c:forEach>
	    		</tbody>
	    	</table>
		</div>
		
   	</div>
	<sec:authorize access="hasRole('ADMIN')">
	<br><br>
		<div class="floatRight">
			<button type="submit" class="btn btn-info btn-sm" onclick="location.href='<c:url value='/newuser'/>'"><spring:message code="userslist.btn.newuser" /></button>
		</div>
		
	</sec:authorize>
</body>
</html>
