	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<div class="authbar">
		<c:if test="${not empty pageContext.request.userPrincipal}">
			<span class="floatRight"><spring:message code="authheader.msg.loggedonuser" />: <strong>${loggedonuser}</strong></span>
		</c:if> 
	</div>

	
	