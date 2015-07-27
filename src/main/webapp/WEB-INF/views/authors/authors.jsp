<%--
  Created by IntelliJ IDEA.
  User: Stacy
  Date: 7/25/15
  Time: 3:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Authors</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <style>
    table{
      width: 100%;
    }
    table, th, td{
      border: 1px solid black;
      text-align: left;
    }
    th, td{
      padding: 5px;
    }
  </style>
</head>
<body>
<p align="right">
<label>firstNameUa:<input type="text" id="firstNameUa"/></label> <br />
<label>lastNameUa:<input type="text" id="lastNameUa"/></label> <br />
<label>firstNameEn:<input type="text" id="firstNameEn"/></label> <br />
<label>lastNameEn:<input type="text" id="lastNameEn"/></label> <br />
<label>firstNameRu:<input type="text" id="firstNameRu"/></label> <br />
<label>lastNameRu:<input type="text" id="lastNameRu"/></label> <br />
<input value="Search" type="submit" id="send-filter"> </p>
<table style="border-collapse:collapse;">
  <tr>
    <th width="5%">id</th>
    <th width="10%">firstNameUa</th>
    <th width="10%">lastNameUa</th>
    <th width="10%">firstNameEn</th>
    <th width="10%">lastNameEn</th>
    <th width="10%">firstNameRu</th>
    <th width="10%">lastNameRu</th>
    <th width="20%">Action</th>
  </tr>
  <tbody id="added">
<c:forEach var="author" items="${authors}">
  <tr>
  <form action="/admin/authors/update-author" method="POST" id="authorForm${author.id}">
  <input type="hidden" value="${author.id}" name="id"/>
  <td width="5%">
  ${author.id}
  </td>
    <td width="10%">
      <input type="text" value="${author.firstNameUa}" name="firstNameUa"/>
    </td>
    <td width="10%">
      <input type="text" value="${author.lastNameUa}" name="lastNameUa"/>
    </td>
    <td width="10%">
      <input type="text" value="${author.firstNameEn}" name="firstNameEn"/>
    </td>
    <td width="10%">
      <input type="text" value="${author.lastNameEn}" name="lastNameEn"/>
    </td>
    <td width="10%">
      <input type="text" value="${author.firstNameRu}" name="firstNameRu"/>
    </td>
    <td width="10%">
      <input type="text" value="${author.lastNameRu}" name="lastNameRu"/>
    </td>
    <th width="10%">
      <input type="submit" value="Update" />
  </form>
    <form>
      <input type ="submit" value ="Review books"/>
    </form>
    <form action="/admin/authors/delete-author/${author.id}" method="GET">
      <input type="submit" value="Delete" />
      </th>
    </form>
  </tr>
</c:forEach>
  </tbody>
  <tr>
    <form action="/admin/authors/add-author" method="POST">
      <td width="10%">
      </td>
      <td width="10%">
        <input type="text" value="" name="firstNameUa"/>
      </td>
      <td width="10%">
        <input type="text" value="" name="lastNameUa"/>
      </td>
      <td width="10%">
        <input type="text" value="" name="firstNameEn"/>
      </td>
      <td width="10%">
        <input type="text" value="" name="lastNameEn"/>
      </td>
      <td width="10%">
        <input type="text" value="" name="firstNameRu"/>
      </td>
      <td width="10%">
        <input type="text" value="" name="lastNameRu"/>
      </td>
      <th width="10%">
        <input type="submit" value="Add new"/>
      </th>
    </form>
  </tr>
</table>
</body>
</html>
