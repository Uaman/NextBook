<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 10.07.2015
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

  <form:form action="/profile" method="POST">
    Ім'я:<input type="text" name="name"/><br />
    Eл. пошта:<input type="email" name="email"/><br />
    Роль:
    <select name="role">
      <option value="1">Читач</option>
      <option value="2">Автор</option>
      <option value="3">Видавець</option>
      <option value="4">Адмін</option>
    </select>
    <br />
    Новий пароль:<input type="password"  name="password"/><br />
    Підтвердіть пароль:<input type="password"  name="confirm_pass"/><br />
    <input type="submit" value="Зберегти"/><br />
  </form:form>

</body>
</html>
