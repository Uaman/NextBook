
<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 01.08.2015
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title></title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script src="/resources/js/admin/publishers/edit-publisher.js"></script>
  <script>
    var EDIT = ${edit};
  </script>
</head>
<body>

<a href='/admin/publishers/all'>Return to publishers page</a><br/><br/>

<c:if test="${edit}">
  id: <input id="id" readonly value="${publisher.id}"/><br />
  nameUa: <input id="nameUa" value="${publisher.nameUa}"/><br />
  nameRu: <input id="nameRu" value="${publisher.nameRu}"/><br />
  nameEn:<input id="nameEn" value="${publisher.nameEn}"/><br />
  description: <input id="description" value="${publisher.description}"/><br />
  <button id="send">Update publisher</button><br />
</c:if>
<c:if test="${!edit}">
  nameUa: <input id="nameUa"/><br />
  nameRu: <input id="nameRu"/><br />
  nameEn: <input id="nameEn"/><br />
  description: <input id="description"/><br />
  <button id="send">Add publisher</button><br />
</c:if>

<c:if test="fn:length(${publisher.users})!=0"><br/>Users:</c:if>

<ol>
<c:forEach var="user" items="${publisher.users}">
  <li>${user.email}</li>
</c:forEach>
</ol>

<a href="/admin/publishers/manage-users/?publisher=${publisher.id}">Manage users</a>

</body>
</html>
