<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 10.07.2015
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
  <title>Users</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <script src="/resources/js/admin/users/user-table/filter.js"></script>
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
Filters: <br />
<label>name:<input type="text" id="name"/></label> <br />
<label>email:<input type="text" id="email"/></label> <br />
<label>all:<input type="checkbox" id="all"></label> <label>active:<input type="checkbox" id="active" checked></label> <br />
<label>role id:<input type="text" id="roleId"/></label> <br />
<label>from:<input type="text" id="from"/></label> <br />
<label>max:<input type="text" id="max"/></label> <br />
<input value="Search" type="submit" id="send-filter">
<table style="border-collapse:collapse;">
  <tr>
    <th width="10%">id</th>
    <th width="20%">name</th>
    <th width="20%">email</th>
    <th width="20%">password</th>
    <th width="10%">active</th>
    <th width="10%">role id</th>
    <th width="10%">action</th>
  </tr>
  <tbody id="added">
  <c:forEach var="user" items="${users}">
    <tr>
      <form action="/admin/users/update-user" method="POST" id="userForm${user.id}">
        <input type="hidden" value="${user.id}" name="id"/>
        <td width="10%">
          ${user.id}
        </td>
        <td width="20%">
          <input type="text" value="${user.name}" name="name"/>
        </td>
        <td width="20%">
          <input type="text" value="${user.email}" name="email"/>
        </td>
        <td width="20%">
          <input type="text" value="${user.password}" name="password"/>
        </td>
        <td width="10%">
          <select name="active">
            <option ${user.active?"selected":""} value="true">true</option>
            <option ${user.active?"":"selected"} value="false">false</option>
          </select>
        </td>
        <td width="10%">
            <%--<spring:message code="role.${user.role.name}" />--%>
          <select name="roleId">
            <c:forEach begin="1" end="5" step="1" var="index">
              <option value="${index}" <c:if test="index==user.role.id">selected</c:if> >
                 <c:choose>
                   <c:when test="${index eq 1}">
                     <spring:message code="role.user" />
                   </c:when>
                   <c:when test="${index eq 2}">
                     <spring:message code="role.author" />
                   </c:when>
                   <c:when test="${index eq 3}">
                     <spring:message code="role.publisher" />
                   </c:when>
                   <c:when test="${index eq 4}">
                     <spring:message code="role.moderator" />
                   </c:when>
                   <c:when test="${index eq 5}">
                     <spring:message code="role.admin" />
                   </c:when>
                 </c:choose>
               </option>
            </c:forEach>
          </select>
        </td>
        <th width="10%">
          <input type="submit" value="Update" />
      </form>
      <form action="/users/delete/${user.id}" method="GET">
          <input type="submit" value="Delete" />
        </th>
      </form>
    </tr>
  </c:forEach>
  </tbody>
  <tr>
    <form action="/users/add" method="POST">
      <td width="10%">
      </td>
      <td width="20%">
        <input type="text" value="" name="name"/>
      </td>
      <td width="20%">
        <input type="text" value="" name="email"/>
      </td>
      <td width="20%">
        <input type="text" value="" name="password"/>
      </td>
      <td width="10%">
        <select name="active">
          <option selected value="true">true</option>
          <option value="false">false</option>
        </select>
      </td>
      <td width="10%">
        <select name="roleId">
          <option value="1"><spring:message code="role.user" /></option>
          <option value="2"><spring:message code="role.author" /></option>
          <option value="3"><spring:message code="role.publisher" /></option>
          <option value="4"><spring:message code="role.moderator" /></option>
          <option value="5"><spring:message code="role.admin" /></option>
        </select>
      </td>
      <th width="10%">
        <input type="submit" value="Add new"/>
      </th>
    </form>
  </tr>
</table>
</body>
</html>
