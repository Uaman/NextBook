<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 30.07.2015
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>Publishers</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script src="/resources/js/admin/publishers/publishers-table.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/tables.css"/>
</head>
<body>

<a href="/admin/publishers/add-publisher">ADD NEW</a>

<form id="filterForm">
  <label>id:<input type="text" id="id"/></label> <br />
  <label>nameUa:<input type="text" id="nameUa"/></label> <br />
  <label>nameEn:<input type="text" id="nameEn"/></label> <br />
  <label>nameRu:<input type="text" id="nameRu"/></label> <br />
  <label>description:<input type="text" id="description"/></label> <br />
  <label>from:<input type="text" id="from"/></label> <br />
  <label>max:<input type="text" id="max"/></label> <br />
  <input value="Search" type="submit">
</form>

<table style="border-collapse:collapse;" width="100%" id="added">
  <tr>
    <th width="50px">id</th>
    <th>nameUa</th>
    <th>nameRu</th>
    <th>nameEn</th>
    <th>description</th>
    <th>users</th>
    <th width="150px">action</th>
  </tr>
  <tbody>
  <c:forEach var="publisher" items="${publishers}">
    <tr>
      <td>
          ${publisher.id}
      </td>
      <td>
          ${publisher.nameUa}
      </td>
      <td>
          ${publisher.nameRu}
      </td>
      <td>
          ${publisher.nameEn}
      </td>
      <td>
          ${publisher.description}
      </td>
      <td>
          <c:forEach var="user" items="${publisher.users}">
            ${user.email}<br />
          </c:forEach>
      </td>
      <td>
        <a href="/admin/publishers/edit-publisher/${publisher.id}">Edit</a>
        <br/><a href="/admin/publishers/manage-users/?publisher=${publisher.id}">Manage users</a>
        <button value="${publisher.id}" class="deleteButton">Delete</button>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>
