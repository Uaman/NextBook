<%--
  Created by IntelliJ IDEA.
  User: borsch
  Date: 8/3/2015
  Time: 7:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="/resources/js/admin/users/edit.user.js"></script>
</head>
<body>
    <a href="/admin/users/all">Back to all</a><br/><br/>
    <div id="result"></div>
    <label>Name: <input type="text" id="name" value="${user.name}"></label><br />
    <label>Email: <input type="text" id="email" value="${user.email}"></label><br />
    Role:
    <select id="role">
        <c:forEach var="role" items="${roles}">
            <option value="${role.id}"
                    <c:if test="${user.role.id eq role.id}">selected="selected" </c:if>
                    ><spring:message code="role.${role.name}" /></option>
        </c:forEach>
    </select><br />
    <input type="hidden" id="userId" value="${user.id}">
    <button id="edit">Edit</button>
</body>
</html>
