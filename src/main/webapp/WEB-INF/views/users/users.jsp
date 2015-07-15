<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 10.07.2015
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <title>Users</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="/resources/js/user-table/filter.js"></script>
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
<label>all:<input type="checkbox" id="all"></label> <label>active:<input type="checkbox" id="active"></label> <br />
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
  <tbody>
  <div id="added">
  <c:forEach var="user" items="${users}">
    <tr>
      <form>
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
          <input type="text" value="${user.active}" name="active"/>
        </td>
        <td width="10%">
          <input type="text" value="${user.roleId}" name="roleId"/>
        </td>
        <th width="10%">
          <input type="submit" value="Update"
                 onclick="this.form.action='/user/'+this.form.id.value+'/update';
                                   this.form.method='POST';" />
          <input type="submit" value="Delete"
                 onclick="this.form.action='/user/'+this.form.id.value+'/delete';
                                   this.form.method='GET';" />
        </th>
      </form>
    </tr>
  </c:forEach>
  </div>
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
        <input type="text" value="" name="active"/>
      </td>
      <td width="10%">
        <input type="text" value="" name="roleId"/>
      </td>
      <th width="10%">
        <input type="submit" value="Add new"/>
      </th>
    </form>
  </tr>
  </tbody>
</table>
</body>
</html>
