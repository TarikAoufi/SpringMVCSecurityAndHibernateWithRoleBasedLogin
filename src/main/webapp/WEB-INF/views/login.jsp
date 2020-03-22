<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<spring:message code=""/>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="login.page.title"/> </title>
<spring:theme code="stylesheet" var="themeName" />
<link href='<spring:url value="/static/css/menu.css" />' rel="stylesheet" />
<link href='<spring:url value="/resources/css/${themeName}"/>' rel="stylesheet" />
<link href="<c:url value='/static/css/bootstrap.css' />"  rel="stylesheet"></link>
<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<script type="text/javascript">
	function myFunction() {
	    var x = document.getElementById("password");
	    if (x.type === "password") {
	        x.type = "text";
	    } else {
	        x.type = "password";
	    }
	}
</script>
<body>  
   <!-- Page incluant le header -->
   <jsp:include page="header.jsp"/>
   <div role="main" class="container">
      
   	  
   	  <div id="mainWrapper">
   	  		<!-- Page incluant le menu -->
   	  		<jsp:include page="menu.jsp"/>
			<div class="login-container">
				<div class="login-card">
					<div class="login-form">
						<c:url var="loginUrl" value="/login" />
						<div class="login-title"><p><spring:message code="login.form.title"/></p></div>
						<form action="${loginUrl}" method="post" class="form-horizontal">
							<c:if test="${param.error != null}">
								<div class="alert alert-danger">
									<p><spring:message code="login.msg.alert-danger"/></p>
								</div>
							</c:if>
							<c:if test="${param.logout != null}">
								<div class="alert alert-success">
									<p><spring:message code="login.msg.alert-success"/></p>
								</div>
							</c:if>
							<div class="input-group input-sm">
								<label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
								<input type="text" class="form-control" id="username" name="username" placeholder="<spring:message code="login.form.placeholder.username"/>" required>
							</div>
							<div class="input-group input-sm">
								<label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> 
								<input type="password" class="form-control" id="password" name="password" placeholder="<spring:message code="login.form.placeholder.password"/>" required>
							</div>
							<div class="input-group input-sm">
							  <div class="checkbox">
                                <label><input type="checkbox" onclick="myFunction()" id="showpassword" name="show-password"> <spring:message code="login.form.showpassword"/></label>  
                              </div>
                              <div class="checkbox">
                                <label><input type="checkbox" id="rememberme" name="remember-me"> <spring:message code="login.form.rememberme"/></label>  
                              </div>
                            </div>
							<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
								
							<div class="form-actions">
								<input type="submit"
									class="btn btn-login btn-block btn-primary btn-default" value="<spring:message code="login.btn-form.btnlogin"/>">
							</div>
									<br>
																					
							 		<div class="msg-login">  <spring:message code="login.link-form.msglogin1"/> <a href="<c:url value='/userregist' />"><spring:message code="login.link-form.msglogin2"/></a> </div>					 							
						</form>
					</div>
				</div>
			</div>
		</div>
   	  
      
   </div>  
</body>
</html>
