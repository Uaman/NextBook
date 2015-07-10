<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 09.07.2015
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
</head>
<body>

  <form:form action="/signin" method="POST">
    Login:<input type="text" name="login"/><br />
    Pass:<input type="password"  name="password"/><br />
    <input type="submit" /><br />
  </form:form>


</body>
</html>
