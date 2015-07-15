<%--
  Created by IntelliJ IDEA.
  User: borsch
  Date: 7/13/2015
  Time: 11:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
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
    <table style="border-collapse:collapse;">
        <tr>
            <th width="10%">id</th>
            <th width="15%">name</th>
            <th width="15%">email</th>
            <th width="20%">password</th>
            <th width="10%">active</th>
            <th width="10%">role id</th>
            <th width="10%">update</th>
            <th width="10%">delete</th>
        </tr>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <form action="/update" method="POST">
                        <input type="hidden" value="${user.id}" name="id"/>
                        <td width="10%">
                            ${user.id}
                        </td>
                        <td width="15%">
                            <input type="text" value="${user.name}" name="name"/>
                        </td>
                        <td width="15%">
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
                        <td width="10%">
                            <input type="submit" value="Update"/>
                        </td>
                    </form>
                    <td>
                        <form action="/delete-user" method="POST">
                            <input type="hidden" value="${user.id}" name="id"/>
                            <td width="10%">
                                <input type="submit" value="Delete"/>
                            </td>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <form action="/add-user" method="POST">
                    <td width="10%"></td>
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
                    <th width="10%"><input type="submit" value="Add new"/></th>
                </form>
                <td width="10%"></td>
            </tr>
        </tbody>
    </table>
</body>
</html>
