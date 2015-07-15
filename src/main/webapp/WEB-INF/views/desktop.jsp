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

<html>
<head>
    <title></title>
</head>
<body>

<%--${pageContext.request.userPrincipal.authorities}--%>

  <ul>

    <security:authorize access="isAnonymous()">
      <li><a href="/signin">Вхід</a></li>
      <li><a href="/signup">Реєстрація</a></li>
    </security:authorize>

    <security:authorize access="isAuthenticated()">
      <li><a href="/profile">Профайл</a></li>
    </security:authorize>

    <%--<security:authorize access="hasRole('ROLE_ADMIN')">--%>
      <li><a href="/users">Керування користувачами</a></li>
    <%--</security:authorize>--%>

    <security:authorize access="isAuthenticated()">
      <li><a href="/static/j_spring_security_logout">Вийти</a></li>
    </security:authorize>

  </ul>

</body>
</html>
