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
  <ul>

    <security:authorize access="isAuthenticated()">
      <li><a href="/profile">Профайл</a></li>
    </security:authorize>

    <security:authorize url="/book/add/**">
      <li><a href="">Додати книгу</a></li>
    </security:authorize>

    <security:authorize url="/admin/**">
      <li><a href="">Модерація</a></li>
      <li><a href="/users">Керування користувачами</a></li>
    </security:authorize>

  </ul>

</body>
</html>
