<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 26.09.2015
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:message code="mainpage.bookstotal" />: ${booksQuantity} <br />
<spring:message code="mainpage.publisherstotal" />: ${publishersQuantity} <br />

Last books: <br />
<c:forEach var="book" items="${lastBooks}">
  <div style="background-color: #dfe1e4">
    <hr>
    <a href = "/bookInfo/${book.id}">
      <img src="/book/getCover/${book.id}/1" width="80" height="80" onerror="this.src='/resources/images/no-cover.png'"/> <br />
    </a>
    <spring:message code="book.title" />: <a href = "/bookInfo/${book.id}">${book.name}</a> <br />
    <spring:message code="book.year" />: ${book.yearOfPublication} <br />
    <spring:message code="book.description" />: ${book.description} <br />
    <spring:message code="book.publisher" />: ${book.publisher.name} <br />
    <spring:message code="book.author" />:
    <c:forEach var="author" items="${book.authors}">
      ${author.name}<br />
    </c:forEach>
    <hr>
  </div>
</c:forEach>


<c:forEach var="category" items="${categories}">
  ${category.name}<br />
  <ul>
    <c:forEach var="subcat" items="${category.subcategories}">
      <li>
        ${subcat.name}
      </li>
    </c:forEach>
  </ul>
</c:forEach>
