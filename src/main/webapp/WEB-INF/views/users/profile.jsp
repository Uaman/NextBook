<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 10.07.2015
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
  <a href="/profile/update"><spring:message code="user.info.editProfile" /></a><br />
  <spring:message code="user.info.name" />: ${userName}<br />
  <spring:message code="user.info.email" />: ${userEmail}<br />
  <spring:message code="user.info.role" />: ${userRole}<br />

</body>
</html>
