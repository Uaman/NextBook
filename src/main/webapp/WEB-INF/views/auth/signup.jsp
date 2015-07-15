<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 09.07.2015
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
</head>
<body>


<form action="/users/add" method="POST">
  Ім'я:<input type="text" name="name"/><br />
  Eл. пошта:<input type="email" name="email"/><br />
  Роль:
  <select name="roleId">
    <option value="1">Читач</option>
    <option value="2">Автор</option>
    <option value="3">Видавець</option>
    <option value="4">Адмін</option>
  </select>
  <br />
  Новий пароль:<input type="password"  name="password"/><br />
  Підтвердіть пароль:<input type="password"  name="confirm_pass"/><br />
  <input type="submit" value="Приєднатись"/><br />
</form>

</body>
</html>
