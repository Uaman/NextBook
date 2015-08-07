<%--
  Created by IntelliJ IDEA.
  User: Stacy
  Date: 8/5/15
  Time: 10:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title></title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script src="/resources/js/admin/authors/author-table/edit-author.js"></script>
  <script>
    var EDIT = ${edit};
  </script>
</head>
<body>
<a href='/admin/authors/all'>Back to all authors</a><br/><br/>

<c:if test="${edit}">
  id: <input id="id" readonly value="${author.id}"/><br />
  FirstNameUa: <input id="firstNameUa" value="${author.firstNameUa}"/><br />
  LastNameUa: <input id="lastNameUa" value="${author.lastNameUa}"/><br />
  FirstNameEn: <input id="firstNameEn" value="${author.firstNameEn}"/><br />
  LastNameEn: <input id="lastNameEn" value="${author.lastNameEn}"/><br />
  FirstNameRu: <input id="firstNameRu" value="${author.firstNameRu}"/><br />
  LastNameRu: <input id="lastNameRu" value="${author.lastNameRu}"/><br />
  <button id="send">Update author</button><br />
</c:if>
<c:if test="${!edit}">
  FirstNameUa: <input id="firstNameUa" value=""/><br />
  LastNameUa: <input id="lastNameUa" value=""/><br />
  FirstNameEn: <input id="firstNameEn" value=""/><br />
  LastNameEn: <input id="lastNameEn" value=""/><br />
  FirstNameRu: <input id="firstNameRu" value=""/><br />
  LastNameRu: <input id="lastNameRu" value=""/><br />
  <button id="send">Add author</button><br />
</c:if>
</body>
</html>
