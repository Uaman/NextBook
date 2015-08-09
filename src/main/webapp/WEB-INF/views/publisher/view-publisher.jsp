<%--
  Created by IntelliJ IDEA.
  User: borsch
  Date: 8/9/2015
  Time: 8:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Publisher View</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/tables.css"/>
</head>
<body>
Users: <br />
<table style="border-collapse:collapse;">
    <tr>
        <th width="20%">name</th>
        <th width="20%">email</th>
    </tr>
    <tbody id="added">
    <c:forEach var="user" items="${users}">
        <tr>
            <td width="20%">
                    ${user.name}
            </td>
            <td width="20%">
                    ${user.email}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br /><br /><br /><br />
Books: <br />
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
        <th>action</th>
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
                        <c:when test="${book.eighteenPlus}">
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
            <td>
                <ul>
                <li><a href="/book/edit-book?bookId=${book.id}">Edit</a></li>
                <li><a href="/book/view?bookId=${book.id}">View</a></li>
                </ul>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="/book/new-book">New Book</a>
</body>
</html>
