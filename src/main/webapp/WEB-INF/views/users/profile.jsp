<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 10.07.2015
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title></title>
</head>
<body>
    <ul>
    <%--<security:authentication property="principal.authorities" />--%>
        <security:authorize access="isAuthenticated()">
            <li><a href="/static/j_spring_security_logout"><spring:message code="global.exit" /></a></li>
        </security:authorize>

        <li><a href="/cabinet/edit"><spring:message code="user.info.editProfile" /></a></li>
        <security:authorize access="hasRole('ROLE_ADMIN')">
            <li><a href="/admin/users/all"><spring:message code="users.manageUsers" /></a></li>
        </security:authorize>
    </ul>
  <spring:message code="user.info.name" />: ${user.name}<br />
  <spring:message code="user.info.email" />: ${user.email}<br />
  <spring:message code="user.info.role" />: <spring:message code="role.${user.role.name}" />

</body>
</html>
