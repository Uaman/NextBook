<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 09.07.2015
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Sign in</title>
</head>
<body>

  <form:form action="static/j_spring_security_check" method="POST">
    <spring:message code="user.info.email" />:<input type="text" name="j_username"/><br />
    <spring:message code="user.info.password" />:<input type="password"  name="j_password"/><br />
    <input id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
    <label for="remember_me"> <spring:message code="global.rememberMe" /> </label><br />
    <input type="submit" value='<spring:message code="global.signIn" />'/><br />
  </form:form>


</body>
</html>
