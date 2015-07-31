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
  <script src="/resources/js/publisher-table/table.js"></script>
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
    .sinput
    {
      width:100%;
      height:100%;
    }
  </style>
</head>
<body>

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
    <th width="150px">action</th>
  </tr>
  <tbody>
  <c:forEach var="publisher" items="${publishers}">
    <tr>
      <td>
        <input type="hidden" value="${publisher.id}" name="id"/>
          ${publisher.id}
      </td>
      <td>
        <input value="${publisher.nameUa}" name="nameUa" class="sinput"/>
      </td>
      <td>
        <input value="${publisher.nameRu}" name="nameRu" class="sinput"/>
      </td>
      <td>
        <input value="${publisher.nameEn}" name="nameEn" class="sinput"/>
      </td>
      <td>
        <input value="${publisher.description}" name="description" class="sinput"/>
      </td>
      <td>
        <input type="submit" value="Update" class="updateButton">
        <input type="submit" value="Delete" class="deleteButton">
      </td>
    </tr>
  </c:forEach>
  <tr>
    <td>
      #
    </td>
    <td>
      <input name="nameUa"  class="sinput"/>
    </td>
    <td>
      <input name="nameRu" class="sinput"/>
    </td>
    <td>
      <input name="nameEn" class="sinput"/>
    </td>
    <td>
      <input name="description" class="sinput"/>
    </td>
    <td>
      <input type="submit" value="Add" class="addButton"/>
    </td>
  </tr>
  </tbody>
</table>




</body>
</html>
