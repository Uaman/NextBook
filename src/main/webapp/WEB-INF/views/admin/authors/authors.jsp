<%--
  Created by IntelliJ IDEA.
  User: Stacy
  Date: 7/25/15
  Time: 3:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Authors</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="/resources/js/admin/authors/author-table/filter.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/tables.css"/>
</head>
<body id ="bodyAuthor">
<a href="/cabinet/profile">Back to main page</a></br>
<form id="requestAuthor">
    <p align="right">
<label>firstNameUa:<input type="text" id="fU"/></label> <br />
<label>lastNameUa:<input type="text" id="lU"/></label> <br />
<input value="Search" type="submit">
    </p>
</form>
<form action="/admin/authors/add-author/">
    <input type="submit" value="Add author">
</form>
<table style="border-collapse:collapse;" width="100%">
  <tr>
    <th width="5%">id</th>
    <th width="15%">firstNameUa</th>
    <th width="15%">lastNameUa</th>
    <th width="10%">firstNameEn</th>
    <th width="10%">lastNameEn</th>
    <th width="10%">firstNameRu</th>
    <th width="10%">lastNameRu</th>
    <th width="20%">Action</th>
  </tr>
  <tbody id="added">
  <c:forEach var="author" items="${authors}">
    <tr>
        <td width="5%">
            ${author.id}
        </td>
        <td width="10%">
        ${author.firstNameUa}
        </td>
        <td width="10%">
        ${author.lastNameUa}
        </td>
        <td width="10%">
        ${author.firstNameEn}
        </td>
        <td width="10%">
        ${author.lastNameEn}
        </td>
        <td width="10%">
         ${author.firstNameRu}
        </td>
        <td width="10%">
         ${author.lastNameRu}
        </td>
        <th width="10%">
            <form action="/admin/authors/edit-author/${author.id}">
                <input type="submit" value="Edit">
            </form>
            <button value="${author.id}" class="deleteButton">Delete</button>
          </th>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
