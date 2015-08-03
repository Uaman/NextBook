<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 03.08.2015
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>Books-manager</title>
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

<form id="filterForm">
  <label>From:<input type="text" id="from"/></label> <br />
  <label>Max:<input type="text" id="max"/></label> <br />
  <label>Id:<input type="text" id="id"/></label> <br />
  <label>ISBN:<input type="text" id="isbn"/></label> <br />
  <label>Name:<input type="text" id="name"/></label> <br />
  <label>Subcategory:<input type="text" id="subCategory"/></label> <br />
  <label>All:<input type="checkbox" id="all" checked></label> <label>18+:<input type="checkbox" id="eighteenPlus"></label> <br />
  <label>Year of publication:<input type="text" id="yearOfPublication"/></label> <br />
  <label>Publisher:<input type="text" id="publisher"/></label> <br />
  <label>Language:<input type="text" id="language"/></label> <br />
  <label>Type:<input type="text" id="typeOfBook"/></label> <br />
  <label>Number of pages:<input type="text" id="numberOfPages"/></label> <br />
  <input value="Search" type="submit">
</form>

<table style="border-collapse:collapse;" width="100%" id="added">
  <tr>
    <th width="50px">id</th>
    <th>ISBN</th>
    <th>name</th>
    <th>authors</th>
    <th>subcategory</th>
    <th width="20px">18+</th>
    <th>year</th>
    <th>publisher</th>
    <th>language</th>
    <th>type</th>
    <th>keywords</th>
    <th># pages</th>
    <th>description</th>
    <th width="150px">action</th>
  </tr>
  <tbody>
  <c:forEach var="book" items="${books}">
    <tr>
      <td>
          ${book.id}
      </td>
      <td>
          ${book.isbn}
      </td>
      <td>
          ${book.uaName}<br />
          ${book.ruName}<br />
          ${book.enName}<br />
      </td>
      <td>
        <c:forEach items="${book.authors}" var="author">
          ${author.firstNameUa} ${author.lastNameUa}<br />
          ${author.firstNameRu} ${author.lastNameRu}<br />
          ${author.firstNameEn} ${author.lastNameEn}<br /><br />
        </c:forEach>
      </td>
      <td>
          ${book.subCategory.category.nameUa} -> ${book.subCategory.nameUa}<br />
          ${book.subCategory.category.nameRu} -> ${book.subCategory.nameRu}<br />
          ${book.subCategory.category.nameEn} -> ${book.subCategory.nameEn}<br />
      </td>
      <td>
        <b>
          <c:choose>
            <c:when test="${book.eighteenPlus==true}">
              +
            </c:when>
            <c:otherwise>
              -
            </c:otherwise>
          </c:choose>
        </b>
      </td>
      <td>
          ${book.yearOfPublication}
      </td>
      <td>
          ${book.publisher.nameUa}<br />
          ${book.publisher.nameRu}<br />
          ${book.publisher.nameEn}<br />
      </td>
      <td>
          ${book.language}
      </td>
      <td>
          ${book.typeOfBook}
      </td>
      <td>
        <c:forEach items="${book.keywords}" var="keyword">
          ${keyword.keyword}
        </c:forEach>
      </td>
      <td>
          ${book.numberOfPages}
      </td>
      <td>
          ${book.descriptionUa}<br /><br />
          ${book.descriptionRu}<br /><br />
          ${book.descriptionEn}<br /><br />
      </td>
      <td>
        <a href="">Edit</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
