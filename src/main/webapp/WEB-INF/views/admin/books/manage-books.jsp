<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 03.08.2015
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <title>Books-manager</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/tables.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            $('.activate-book').click(function(){
                var bookId = $(this).val();
                $.ajax({
                    url: '/admin/books/activateBook/'+bookId,
                    type: 'POST',
                    success: function(response){
                        if(response) {
                            $('#status-' + bookId).text('ACTIVE');
                        }
                    },
                    error: function(e){
                        console.log(e);
                    }
                })
            });
            $('.deactivate-book').click(function(){
                var bookId = $(this).val();
                $.ajax({
                    url: '/admin/books/deactivateBook/'+bookId,
                    type: 'POST',
                    success: function(response){
                        if(response) {
                            $('#status-' + bookId).text('NOT_ACTIVE');
                        }
                    },
                    error: function(e){
                        console.log(e);
                    }
                })
            });
        });
    </script>
    <%--<script src="/resources/js/admin/book/manage-books.js"></script>--%>
</head>
<body>

<div style="font-size: 20px; margin-bottom: 30px;">
    <a href="/admin/books/allComments">Manage comments</a>
</div>

<form:form method="GET" action="/admin/books/all" modelAttribute="booksFilter">
    <label>Publisher:
        <form:select path="publisher">
            <form:option value="0" label="All"/>
            <form:options items="${publishers}" itemValue="id" itemLabel="nameUa"/>
        </form:select>
        <br />
    </label>
    <label>Author:
        <form:select path="author">
            <form:option value="0" label="All"/>
            <form:options items="${authors}" itemValue="id" itemLabel="lastNameUa"/>
        </form:select>
        <br />
    </label>
    <label>Book type: <form:select path="bookType" items="${bookTypes}"/></label><br />
    <label>Eighteen Plus: <form:select path="eighteenPlus" items="${eighteenPlusValues}"/></label><br />
    <label>Order direction: <form:select path="orderDirection" items="${orderDirections}"/></label><br />
    <label>Order by: <form:select path="orderBy" items="${orderBy}"/></label><br />
    <label>Status: <form:select path="status" items="${statuses}"/></label><br />
    <label>Number of pages: <form:input type="text" path="numberOfPages"/></label><br />
    <label>Year of publication: <form:input type="text" path="yearOfPublication"/></label><br />
    <input type="submit">
</form:form>

<%--
<label>From:<input type="text" id="from"/></label> <br />
<label>Max:<input type="text" id="max"/></label> <br />
<label>Id:<input type="text" id="id"/></label> <br />
<label>ISBN:<input type="text" id="isbn"/></label> <br />
<label>Name:<input type="text" id="name"/></label> <br />
Category:
<select id="subCategory">
    <option selected value="-1">ALL</option>
    <c:if test="${subCategories ne null}">
        <c:forEach items="${subCategories}" var="subCategory">
            <option value="${subCategory.id}">${subCategory.nameUa} : ${subCategory.category.nameUa}</option>
        </c:forEach>
    </c:if>
</select>
<br />
<label>All:<input type="checkbox" id="all" checked></label> <label>18+:<input type="checkbox" id="eighteenPlus"></label> <br />
<label>Year of publication:<input type="text" id="yearOfPublication"/></label> <br />
<label>Publisher:<input type="text" id="publisher"/></label> <br />
<label>Language:<input type="text" id="language"/></label> <br />
<label>Type:
    <select id="typeOfBook">
        <option selected value="ALL">ALL</option>
        <option value="ELECTRONIC">ELECTRONIC</option>
        <option value="PAPER">PAPER</option>
    </select></label> <br />
<label>Number of pages:<input type="text" id="numberOfPages"/></label> <br />
<input value="Search" type="submit"  id="send-filter">
--%>

<table>
    <tr>
        <th>id</th>
        <th>ISBN</th>
        <th>name</th>
        <th>authors</th>
        <th>subcategory</th>
        <th>18+</th>
        <th>year</th>
        <th>publisher</th>
        <th>language</th>
        <th>type</th>
        <th>keywords</th>
        <th># pages</th>
        <th>description</th>
        <th>status</th>
        <th>action</th>
    </tr>
    <tbody id="added">
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
                <c:if test="${book.ruName ne null && book.ruName ne ''}">
                    ${book.ruName}<br />
                </c:if>
                <c:if test="${book.enName ne null && book.enName ne ''}">
                    ${book.enName}<br />
                </c:if>
            </td>
            <td>
                <c:forEach items="${book.authors}" var="author">
                    ${author.firstNameUa} ${author.lastNameUa}<br />
                    <c:if test="${author.firstNameRu ne null && author.firstNameRu ne ''}">
                        ${author.firstNameRu} ${author.lastNameRu}<br />
                    </c:if>
                    <c:if test="${author.firstNameEn ne null && author.firstNameEn ne ''}">
                        ${author.firstNameEn} ${author.lastNameEn}<br />
                    </c:if>
                    <hr />
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
                <spring:message code="book.type.${book.typeOfBook}"/>
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
                <c:if test="${book.descriptionRu ne null && book.descriptionRu ne ''}">
                    ${book.descriptionRu}<br /><br />
                </c:if>
                <c:if test="${book.descriptionEn ne null && book.descriptionEn ne ''}">
                    ${book.descriptionEn}<br /><br />
                </c:if>
            </td>
            <td id="status-${book.id}">${book.status}</td>
            <td>
                <c:if test="${book.status eq 'READY_FOR_REVIEW'}">
                    <button class="activate-book" value="${book.id}">Activate Book</button><br />
                    <button class="deactivate-book" value="${book.id}">Deactivate Book</button><br />
                </c:if>
                <a href="/admin/books/edit-book?bookId=${book.id}">Edit</a>
                <a href="/bookInfo/${book.id}">View</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
