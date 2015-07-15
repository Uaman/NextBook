<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 10.07.2015
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <a href="/user/1/update">Редагувати профіль</a><br />
  Ім'я: ${userName}<br />
  Ел. пошта: ${pageContext.request.userPrincipal}<br />
  Роль: ${pageContext.request.userPrincipal.authorities}<br />

</body>
</html>
