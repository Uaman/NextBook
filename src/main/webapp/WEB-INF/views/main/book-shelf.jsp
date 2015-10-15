<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 26.09.2015
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/resources/js/main/bookFavoriteButton.js.jsp"/>

<c:forEach var="book" items="${last_books}">
    <div style="background-color: #dfe1e4">
        <p>
        <hr>
        <a href="/bookInfo/${book.id}"><img src="/book/getCover/${book.id}/1" width="80" height="80"
                                            onerror="this.src='/resources/images/no-cover.png'"/>
            <br/>
        </a><spring:message code="book.title"/>:<a href="/bookInfo/${book.id}">${book.name}</a>
        <br/><spring:message code="book.year"/>: ${book.yearOfPublication}
        <br/><spring:message code="book.description"/>: ${book.description}
        <br/><spring:message code="book.publisher"/>: ${book.publisher.name}
        <br/><c:choose>
                <c:when test="${book.favorite}"> <button class="deleteFavorite" id="favorite/${book.id}"><spring:message code="book.favorites.deletefromfavorites" /></button></c:when>
                <c:otherwise> <button class="addFavorite" id="favorite/${book.id}"><spring:message code="book.favorites.addtofavorites" /></button></c:otherwise>
             </c:choose>
        <br/>
        <hr>
        </p>
    </div>
</c:forEach>

<h3><a href="/catalog/all"><spring:message code="catalog.more"/></a></h3>