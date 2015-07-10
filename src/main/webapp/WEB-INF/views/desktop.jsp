<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 09.07.2015
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set scope="session" var="USER" value="0"></c:set>
<c:set scope="session" var="AUTHOR" value="1"></c:set>
<c:set scope="session" var="PUBLISHER" value="2"></c:set>
<c:set scope="session" var="ADMIN" value="3"></c:set>

<c:set scope="session" var="ROLE" value="3"></c:set>
<html>
<head>
    <title></title>
</head>
<body>
  <ul>
    <li><a href="/profile">Профайл</a></li>

    <c:if test="${ROLE==AUTHOR}">
      <li><a href="">Додати книгу</a></li>
    </c:if>

    <c:if test="${ROLE==ADMIN}">
      <li><a href="">Модерація</a></li>
      <li><a href="/users">Керування користувачами</a></li>
    </c:if>

  </ul>

</body>
</html>
