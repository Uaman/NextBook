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
  <%--<security:authentication property="principal.authorities" />--%>
  <a href="/profile/update"><spring:message code="user.info.editProfile" /></a><br />
  <spring:message code="user.info.name" />: ${userName}<br />
  <spring:message code="user.info.email" />: ${userEmail}<br />
  <spring:message code="user.info.role" />:
  <c:choose>
    <c:when test="${userRole==1}">
      <spring:message code="role.user" />
    </c:when>
    <c:when test="${userRole==2}">
      <spring:message code="role.author" />
    </c:when>
    <c:when test="${userRole==3}">
      <spring:message code="role.publisher" />
    </c:when>
    <c:when test="${userRole==4}">
      <spring:message code="role.moderator" />
    </c:when>
    <c:when test="${userRole==5}">
      <spring:message code="role.admin" />
    </c:when>
  </c:choose><br />

</body>
</html>
