<!--
	**************************************************
	Cette page définit le menu de l'application. 
	Elle est basée sur la feuille de style : menu.css
	**************************************************
-->
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav id="nav">
  <ul>
    <li><a href="<c:url value='/' />"><spring:message code="menu.home.title"/></a></li>
    <li><span class="submenu"><spring:message code="menu.theme.title"/></span>
      <ul class="submenu">
        <li><a href="?theme=theme_1"><spring:message code="menu.theme.theme1"/></a></li>
		<li><a href="?theme=theme_2"><spring:message code="menu.theme.theme2"/></a></li>
      </ul>
    </li>
    <li><span class="submenu"><spring:message code="menu.lang.title"/></span>
      <ul class="submenu">
        <li><a href="?lang=fr"><spring:message code="menu.lang.french"/></a></li>
		<li><a href="?lang=en"><spring:message code="menu.lang.english"/></a></li>
      </ul>
    </li>
    <c:if test="${not empty pageContext.request.userPrincipal}">
		<li><a href="<c:url value='/list' />"><spring:message code="menu.userslist.title"/></a></li>
	</c:if>
    <li class="right"><a href="#"><spring:message code="menu.about.title"/></a></li>
  </ul>
</nav>