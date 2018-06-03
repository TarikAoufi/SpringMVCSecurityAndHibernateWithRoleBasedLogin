<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<spring:theme code="stylesheet" var="themeName" />
	<link href='<spring:url value="/resources/css/${themeName}"/>' rel="stylesheet" />
	<title><spring:message code="registration.page.title"/></title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href='<spring:url value="/static/css/menu.css" />' rel="stylesheet" />
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<!-- Page incluant le header -->
    <jsp:include page="header.jsp"/>
    <%@include file="authheader.jsp" %>
 	<div class="generic-container">
 		<!-- Page incluant le menu -->
   	   <div class="container-menu"><jsp:include page="menu.jsp"/> </div>
		
		<div class="alert alert-info"><spring:message code="registration.form.title"/></div>
	 	<form:form method="POST" modelAttribute="user" class="form-horizontal">
			<form:input type="hidden" path="id" id="id"/>
			
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="firstName"><spring:message code="registration.form.firstname"/></label>
					<div class="col-sm-5">
						<form:input type="text" path="firstName" id="firstName" autofocus="true" class="form-control input-sm"/>
					</div>
					<div class="has-error">
						<form:errors path="firstName" class="help-inline"/>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="lastName"><spring:message code="registration.form.lastname"/></label>
					<div class="col-sm-5"> 
						<form:input type="text" path="lastName" id="lastName" autofocus="true" class="form-control input-sm" />						
					</div>
					<div class="has-error">
						<form:errors path="lastName" class="help-inline"/>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="username"><spring:message code="registration.form.username"/></label>
					<div class="col-sm-5">
						<c:choose>
							<c:when test="${edit}">
								<form:input type="text" path="username" id="username" autofocus="true" class="form-control input-sm" disabled="true"/>
							</c:when>
							<c:otherwise>
								<form:input type="text" path="username" id="username" autofocus="true" class="form-control input-sm" />								
							</c:otherwise>
						</c:choose>
					</div>
					<div class="has-error">
						<form:errors path="username" class="help-inline"/>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="password"><spring:message code="registration.form.password"/></label>
					<div class="col-md-5">
						<form:input type="password" path="password" id="password" autofocus="true" class="form-control input-sm" />		
						<div class="has-error">
							<form:errors path="password" class="help-inline"/>
						</div>				
					</div>					
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="email"><spring:message code="registration.form.email"/></label>
					<div class="col-md-5">
						<form:input type="text" path="email" id="email" autofocus="true" class="form-control input-sm" />						
					</div>
					<div class="has-error">
						<form:errors path="email" class="help-inline"/>
					</div>
				</div>
			</div>
			<c:choose>				
				<c:when test="${ not empty pageContext.request.userPrincipal}">
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="roles"><spring:message code="registration.form.roles"/></label>
							<div class="col-md-2">		
						        <form:select path="roles" items="${roleList}" multiple="true" itemValue="id" itemLabel="type" class="form-control input-sm" />						     								
							</div>
							<div class="has-error">
								<form:errors path="roles" class="help-inline"/>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-3 control-lable" for="roles"><spring:message code="registration.form.role"/></label>
							<div class="col-md-2">
								<form:select path="roles" itemValue="id" itemLabel="type" class="form-control input-sm">
									<c:forEach items="${roleList}" var="role">
										<c:choose>
											<c:when test="${role.id eq '3'}">
												<option value="${role.id}" selected>${role.type}</option>
											</c:when>
										</c:choose>
									</c:forEach>
								</form:select>								
							</div>
							<div class="has-error">
								<form:errors path="roles" class="help-inline"/>
							</div>
						</div>
					</div>
				</c:otherwise>			
			</c:choose>


			

			<div class="row">
				<div class="form-actions floatRight">
				<ul class="navbar-nav ml-auto">
				<li class="reg">
					<c:choose>
						<c:when test="${edit}">
							<button type="submit" class="btn btn-primary"><spring:message code="registration.btn-form.update" /></button>
							<button type="button" class="btn btn-danger" onclick="location.href='<c:url value='/list'/>'"><spring:message code="registration.btn-form.cancel" /></button>							
						</c:when>
						<c:otherwise>					
							<button type="submit" class="btn btn-primary btn-sm"><spring:message code="registration.btn-form.register" /></button>
							<button type="button" class="btn btn-danger btn-sm" onclick="location.href='<c:url value='/list'/>'"><spring:message code="registration.btn-form.cancel" /></button>
						</c:otherwise>
					</c:choose>
					</li>
					</ul>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>