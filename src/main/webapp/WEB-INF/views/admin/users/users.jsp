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
  <script src="/resources/js/admin/users/user-table/admin.users.js"></script>

    <link rel="stylesheet" type="text/css" href="/resources/css/tables.css"/>
</head>
<body>
Filters: <br />
<label>name:<input type="text" id="name"/></label> <br />
<label>email:<input type="text" id="email"/></label> <br />
<label>all:<input type="checkbox" id="all"></label> <label>active:<input type="checkbox" id="active" checked></label> <br />
<label>role:
<select id="role-filter">
    <option value="0">All</option>
    <c:forEach var="role" items="${roles}">
        <option value="${role.id}"><spring:message code="role.${role.name}" /></option>
    </c:forEach>
</select>
</label> <br />
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
    <th width="10%">role</th>
    <th width="10%">action</th>
  </tr>
  <tbody id="added">
  <c:forEach var="user" items="${users}">
    <tr id="row-${user.id}">
        <td width="10%">
          ${user.id}
        </td>
        <td width="20%">
          ${user.name}
        </td>
        <td width="20%">
          ${user.email}
        </td>
        <td width="20%">
          ${user.password}
        </td>
        <td width="10%">
            <c:choose>
                <c:when test="${user.active}">
                    <button class="deactivate" value="${user.id}">Deactivate</button>
                </c:when>
                <c:otherwise>
                    <button class="activate" value="${user.id}">Activate</button>
                </c:otherwise>
            </c:choose>
        </td>
        <td width="10%">
            <spring:message code="role.${user.role.name}" />
        </td>
        <td width="10%">
            <button class="delete" value="${user.id}">Delete</button>
            <form action="/admin/users/edit-user">
                <input type="hidden" name="userId" value="${user.id}">
                <input type="submit" value="Edit">
            </form>
        </td>
    </tr>
  </c:forEach>
  </tbody>
    <%--
  <tr>
      <td width="10%">
      </td>
      <td width="20%">
        <input type="text" name="name" placeholder="name.."/>
      </td>
      <td width="20%">
        <input type="text" value="" name="email" placeholder="email.."/>
      </td>
      <td width="20%">
        <input type="text" value="" name="password" placeholder="password.."/>
      </td>
      <td width="10%">
        <select name="active">
          <option selected value="true">true</option>
          <option value="false">false</option>
        </select>
      </td>
      <td width="10%">
          <select id="role-for">
              <c:forEach var="role" items="${roles}">
                  <option value="${role.id}"><spring:message code="role.${role.name}" /></option>
              </c:forEach>
          </select>
      </td>
      <th width="10%">
        <input type="submit" value="Add new" id="add-new-user"/>
      </th>
  </tr>
  --%>
</table>
</body>
</html>
