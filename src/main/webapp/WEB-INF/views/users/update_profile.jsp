<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 10.07.2015
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
</head>
<body>

  <form action="/user/${userId}/update" method="POST" name="userForm">
    <spring:message code="user.info.name" />:<input type="text" name="name" value="${userName}"/><br />
    <spring:message code="user.info.email" />:<input type="email" name="email" value="${userEmail}" /><br />
    <spring:message code="user.info.role" />:
    <select name="roleId">
      <option value="1"><spring:message code="role.user" /></option>
      <option value="2"><spring:message code="role.author" /></option>
      <option value="3"><spring:message code="role.publisher" /></option>
      <security:authorize access="hasRole('ROLE_MODERATOR')">
        <option value="4"><spring:message code="role.moderator" /></option>
      </security:authorize>
      <security:authorize access="hasRole('ROLE_ADMIN')">
        <option value="5"><spring:message code="role.admin" /></option>
      </security:authorize>
    </select>
    <script>
      document.userForm.roleId.selectedIndex = ${userRole-1};
    </script>
    <br />
    <spring:message code="user.info.newPass" />:<input type="password"  name="password"/><br />
    <spring:message code="user.info.confPass" />:<input type="password"  name="confirm_pass"/><br />
    <input type="submit" value='<spring:message code="global.update" />'/><br />
  </form>

</body>
</html>
