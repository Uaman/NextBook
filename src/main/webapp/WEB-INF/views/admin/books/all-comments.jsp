<%--
  Created by IntelliJ IDEA.
  User: borsch
  Date: 10/13/2015
  Time: 11:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <title>Comments manager</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/tables.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            $('.activate-comment').click(function(){
                var commentId = $(this).val();
                $.ajax({
                    url: '/admin/books/activateComment/'+commentId,
                    type: 'POST',
                    success: function(response){
                        if(response){
                            $('#comment-status-'+commentId).text('ACTIVE');
                            $('#comment-changed-by-'+commentId).text('ADMIN');
                        }
                    },
                    error: function(e){
                        console.log(e);
                    }
                })
            });
            $('.deactivate-comment').click(function(){
                var commentId = $(this).val();
                $.ajax({
                    url: '/admin/books/deactivateComment/'+commentId,
                    type: 'POST',
                    success: function(response){
                        if(response){
                            $('#comment-status-'+commentId).text('NOT_ACTIVE');
                            $('#comment-changed-by-'+commentId).text('ADMIN');
                        }
                    },
                    error: function(e){
                        console.log(e);
                    }
                })
            });
            $('.delete-comment').click(function(){
                var commentId = $(this).val();
                $.ajax({
                    url: '/admin/books/deleteComment/'+commentId,
                    type: 'POST',
                    success: function(response){
                        if(response){
                            $('#comment-'+commentId).remove();
                        }
                    },
                    error: function(e){
                        console.log(e);
                    }
                })
            });
        });
    </script>
</head>
<body>
<form:form method="GET" modelAttribute="commentsCriterion" action="/admin/books/allComments">
    Status of comment: <form:select path="status" items="${statuses}" /><br />
    Changed Status By: <form:select path="changedBy" items="${changedByValues}" /><br />
    Time created from: <form:input path="timeFrom" type="text" /><br/>
    Time created to: <form:input path="timeTo" type="text" /><br />
    Book id: <form:input path="bookId" type="text" /><br />
    User id: <form:input path="userId" type="text" /><br />
    <input type="submit" />
</form:form>

<form method="GET" action="/admin/books/deactivateAllUserComments">
    <label>deactivate all comments of user: <input name="userId" type="text" /></label>
    <input type="submit">
</form>

<table>
    <tr>
        <th>id</th>
        <th>user</th>
        <th>book</th>
        <th>comment</th>
        <th>date created</th>
        <th>status</th>
        <th>changed by</th>
        <th>action</th>
    </tr>
    <tbody>
    <jsp:useBean id="dateValue" class="java.util.Date"/>
    <c:forEach var="comment" items="${comments}">
        <tr id="comment-${comment.id}">
            <td>${comment.id}</td>
            <td>${comment.user.name}(${comment.user.id})</td>
            <td>
                <a href="/bookInfo/${comment.book.id}">View Book</a><br />
                <a href="/admin/books/edit-book?bookId=${comment.book.id}">Edit Book</a><br/>
                (${comment.book.id})
            </td>
            <td>${comment.comment}</td>
            <td>
                <jsp:setProperty name="dateValue" property="time" value="${comment.time}"/>
                <fmt:formatDate value="${dateValue}" pattern="MM/dd/yyyy HH:mm"/>
            </td>
            <td id="comment-status-${comment.id}">${comment.status}</td>
            <td id="comment-changed-by-${comment.id}">${comment.changedBy}</td>
            <td>
                <button class="activate-comment" value="${comment.id}">Active Comment</button><br/>
                <button class="deactivate-comment" value="${comment.id}">Deactivate Comment</button><br />
                <button class="delete-comment" value="${comment.id}">Delete Comment</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
