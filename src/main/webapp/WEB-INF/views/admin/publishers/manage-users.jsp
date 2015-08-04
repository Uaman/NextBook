<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 03.08.2015
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Publisher's users</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/tables.css"/>
</head>
<body>

<a href="/admin/publishers/edit-publisher/${publisher.id}">Edit publisher</a><br />
<a href="/admin/publishers/all">Back to publishers page</a>

<table style="border-collapse:collapse;">
  <tr>
    <th width="50px">id</th>
    <th>name</th>
    <th>email</th>
    <th>active</th>
    <th>role</th>
    <th width="150px">action</th>
  </tr>
  <c:forEach var="user" items="${publisherUsers}">
    <tr>
      <td>${user.id}</td>
      <td>${user.name}</td>
      <td>${user.email}</td>
      <td>${user.active}</td>
      <td>${user.role.name}</td>
      <td>
        <form action="/admin/publishers/manage-users">
          <input type="hidden" name="publisher" value="${publisher.id}" />
          <input type="hidden" name="user" value="${user.id}" />
          <input type="hidden" name="action" value="delete" />
          <input type="submit" value="Delete user from publisher" />
        </form>
      </td>
    </tr>
  </c:forEach>
  <c:forEach var="user" items="${allUsers}">
    <tr>
      <td>${user.id}</td>
      <td>${user.name}</td>
      <td>${user.email}</td>
      <td>${user.active}</td>
      <td>${user.role.name}</td>
      <td>
        <form action="/admin/publishers/manage-users">
          <input type="hidden" name="publisher" value="${publisher.id}" />
          <input type="hidden" name="user" value="${user.id}" />
          <input type="hidden" name="action" value="add" />
          <input type="submit" value="Add user to publisher" />
        </form>
      </td>
    </tr>
  </c:forEach>
  <c:forEach var="user" items="${anotherPublisherUsers}">
    <tr>
      <td>${user.id}</td>
      <td>${user.name}</td>
      <td>${user.email}</td>
      <td>${user.active}</td>
      <td>${user.role.name}</td>
      <td>
        [already has publisher]
      </td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
