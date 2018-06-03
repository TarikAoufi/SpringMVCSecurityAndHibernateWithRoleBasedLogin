<!--
	**********************************************************************************************
	Cette page définit l'entête, qui est composé par : navbar, les bouttons de connexion et d'inscription, les icônes pour changer la langue. 
	Elle est basée sur les feuilles de style : 
	 bootstrap-theme1.css , bootstrap-theme2.css ==> navbar 
	 								  app.css ==> Bouttons de connexion et d'inscription + icônes de langue
	**********************************************************************************************
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-expand-md navbar-dark bg-dark mb-4">
	<!-- Partie incluant les bouttons de connexion et d'inscription, les icônes pour changer la langue.  -->
	<div class="navbar-collapse collapse w-100 order-3 dual-collapse2">
		<ul class="navbar-nav ml-auto">
			<li class="left">
				<c:choose>
					<c:when test="${not empty pageContext.request.userPrincipal}">
						<button type="button" class="btn btn-danger btn-sm" onclick="location.href='<c:url value='/logout'/>'"><spring:message code="header.btn.logout" /></button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-warning btn-sm" onclick="location.href='<c:url value='/login'/>'"><spring:message code="header.btn.login" /></button>
						<button type="button" class="btn btn-success btn-sm" onclick="location.href='<c:url value='userregist'/>'"><spring:message code="header.btn.register" /></button>
					</c:otherwise>
				</c:choose>
			</li>
		</ul>
		<ul class="navbar-nav floatRight">
			<li class="right">
				<a href="?lang=fr">
					<img src="<c:url value='static/images/fr.png' />" alt='Fran&ccedil;ais' />
				</a>
			</li>
			<li>
				<a href="?lang=en">
					<img src="<c:url value='static/images/en.png' />" alt='English' />
				</a>
			</li>
		</ul>
	</div>

	<a class="navbar-brand" href="#"><spring:message code="header.title" /></a>
	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarCollapse">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link" href="#"><spring:message code="header.home" /> <span class="sr-only"></span></a></li>
		</ul>
	</div>
</nav>