<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 09.07.2015
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>Sign up</title>
</head>
<body>


<form action="/users/add" method="POST">
  <spring:message code="user.info.name" />:<input type="text" name="name"/><br />
  <spring:message code="user.info.email" />:<input type="email" name="email"/><br />
  <spring:message code="user.info.role" />:
  <select name="roleId">
    <option value="1"><spring:message code="role.user" /></option>
    <option value="2"><spring:message code="role.author" /></option>
    <option value="3"><spring:message code="role.publisher" /></option>
    <option value="4"><spring:message code="role.admin" /></option>
  </select>
  <br />
  <spring:message code="user.info.password" />:<input type="password"  name="password"/><br />
  <spring:message code="user.info.confPass" />:<input type="password"  name="confirm_pass"/><br />
  <input type="submit" value='<spring:message code="global.signUp" />'/><br />
</form>

</body>
</html>
