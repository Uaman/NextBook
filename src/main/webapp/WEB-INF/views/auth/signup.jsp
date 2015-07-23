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
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <jsp:include page="/resources/js/main/sign.up.js.jsp"></jsp:include>
</head>
<body>

<div class="errorblock">

</div>

<form id="register-form" action="/register" method="POST">
  <spring:message code="user.info.name" />:<input type="text" name="name" id="name"/><br />
  <spring:message code="user.info.email" />:<input type="text" name="email" id="email"/><br />
  <spring:message code="user.info.role" />:
  <select name="roleId" id="roleId">
    <option value="1"><spring:message code="role.user" /></option>
    <option value="2"><spring:message code="role.author" /></option>
    <option value="3"><spring:message code="role.publisher" /></option>
  </select>
  <br />
  <spring:message code="user.info.password" />:<input type="password"  name="password" id="password"/><br />
  <spring:message code="user.info.confPass" />:<input type="password"  name="confirm_pass" id="confirm_pass"/><br />
  <input type="submit" value='<spring:message code="global.signUp" />'/><br />
</form>

</body>
</html>
