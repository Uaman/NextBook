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
    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>
</head>
<body id ="bodyAuthor">
<form id="requestAuthor">
        <p align="right">
<label>firstNameUa:<input type="text" id="fU"/></label> <br />
<label>lastNameUa:<input type="text" id="lU"/></label> <br />
<input value="Search" type="submit"> </p></form>
<button value="Add" class="editButton">Add author</button>
<table style="border-collapse:collapse;">
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
            <input type="hidden" id="firstNameUa${author.id}" value=" ${author.firstNameUa}" />
        ${author.firstNameUa}
        </td>
        <td width="10%">
            <input type="hidden" id="lastNameUa${author.id}" value="  ${author.lastNameUa}" />
        ${author.lastNameUa}
        </td>
        <td width="10%">
            <input type="hidden" id="firstNameEn${author.id}" value=" ${author.firstNameEn}" />
        ${author.firstNameEn}
        </td>
        <td width="10%">
            <input type="hidden" id="lastNameEn${author.id}" value="${author.lastNameEn}" />
        ${author.lastNameEn}
        </td>
        <td width="10%">
            <input type="hidden" id="firstNameRu${author.id}" value="  ${author.firstNameRu}" />
         ${author.firstNameRu}
        </td>
        <td width="10%">
            <input type="hidden" id="lastNameRu${author.id}" value=" ${author.lastNameRu}" />
         ${author.lastNameRu}
        </td>
        <th width="10%">
            <button value="${author.id}" class="editButton">Update</button>
            <button value="${author.id}" class="deleteButton">Delete</button>
          </th>
    </tr>
  </c:forEach>
  </tbody>
</table>
<div id="edit-form" class="popup-default" style="display: none;">
    <form:form modelAttribute="adminAuthorForm" action="/admin/authors/update-author" method="POST">
        <input type="hidden" name="id" value="" />
        FirstNameUa <input type="text" value=""  name="firstNameUa"/></br>
        LastNameUa<input type="text" value=""  name="lastNameUa"/></br>
        FirstNameEn<input type="text" value=""  name="firstNameEn"/></br>
        LastNameEn<input type="text" value=""  name="lastNameEn"/></br>
        FirstNameRu<input type="text" value=""  name="firstNameRu"/></br>
        LastNameRu<input type="text" value=""  name="lastNameRu"/></br>
        <input type="submit" value="Submit"/><br />
        </form:form>
    <input value="Cancel" type="submit" id="closeButton">
    </div>
<div class="shadow" style="display: none;"></div>
</body>
</html>
