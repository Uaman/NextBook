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
    <title>Edit Profile</title>
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/jquery.form.min.js"></script>
    <script src="/resources/js/user-cabinet/user.edit.profile.js"></script>
</head>
<body>
  <div id="result-update-password">

  </div>

  <div id="change-password-form">
      <label><spring:message code="user.edit.password.old.password" /> <input type="password" id="oldPassword"/></label><br />
      <label><spring:message code="user.edit.password.new.password" /> <input type="password" id="newPassword"/></label><br />
      <label><spring:message code="user.edit.password.new.password.confirm" /> <input type="password" id="confirmNewPassword"/></label><br />
      <input type="submit" id="send-form-password"/>
  </div>

  <div id="result-update-name-email">

  </div>
  <div>
      <spring:message code="user.info.name" />:<input type="text" id="name" value="${user.name}"/><br />
      <spring:message code="user.info.email" />:<input type="email" id="email" value="${user.email}" /><br />
      <input type="submit" id="update-submit" value="<spring:message code="global.update" />"/>
  </div>

</body>
</html>
