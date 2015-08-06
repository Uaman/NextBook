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

<html>
<head>
    <title>Books-manager</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/tables.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="/resources/js/admin/book/manage-books.js"></script>
</head>
<body>


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


<table>
    <tr>
        <th>id <a href="ASC" class="order" id="orderId">&darr;</a></th>
        <th>ISBN <a href="ASC" class="order" id="orderIsbn">&darr;</a></th>
        <th>name</th>
        <th>authors</th>
        <th>subcategory</th>
        <th>18+ <a href="ASC" class="order" id="order18">&darr;</a></th>
        <th>year <a href="ASC" class="order" id="orderYear">&darr;</a></th>
        <th>publisher</th>
        <th>language <a href="ASC" class="order" id="orderLanguage">&darr;</a></th>
        <th>type <a href="ASC" class="order" id="orderType">&darr;</a></th>
        <th>keywords</th>
        <th># pages <a href="ASC" class="order" id="orderPages">&darr;</a></th>
        <th>description</th>
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
            <td>
                <a href="/book/edit-book?bookId=${book.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
