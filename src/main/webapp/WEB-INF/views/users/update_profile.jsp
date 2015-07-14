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
    Логін:<input type="text" name="login"/><br />
    Eл. пошта:<input type="email" name="email"/><br />
    Роль:
    <select name="role">
      <option value="user">Читач</option>
      <option value="author">Автор</option>
      <option value="publisher">Видавець</option>
      <option value="admin">Адмін</option>
    </select>
    <br />
    Новий пароль:<input type="password"  name="password"/><br />
    Підтвердіть пароль:<input type="password"  name="confirm_pass"/><br />
    <input type="submit" value="Зберегти"/><br />
  </form:form>

</body>
</html>
