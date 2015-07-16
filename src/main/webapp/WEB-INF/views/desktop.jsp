<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 09.07.2015
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title></title>
</head>
<body>

<%--${pageContext.request.userPrincipal.authorities}--%>

  <ul>

    <security:authorize access="isAnonymous()">
      <li><a href="/signin"><spring:message code="global.signIn" /></a></li>
      <li><a href="/signup"><spring:message code="global.signUp" /></a></li>
    </security:authorize>

    <security:authorize access="isAuthenticated()">
      <li><a href="/profile"><spring:message code="user.info.profile" /></a></li>
    </security:authorize>

    <security:authorize access="hasRole('ROLE_ADMIN')">
      <li><a href="/users"><spring:message code="users.manageUsers" /></a></li>
    </security:authorize>

    <security:authorize access="isAuthenticated()">
      <li><a href="/static/j_spring_security_logout"><spring:message code="global.exit" /></a></li>
    </security:authorize>

  </ul>

</body>
</html>
