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

<div id="book-slider">
<c:forEach var="book" items="${last_books}">
    <div class="book-default">
        <div class="book-preview">
            <button class="button but-orange"><a href="/bookInfo/${book.id}#read-book">Preview</a></button>
        </div>
        <div class="gInfo-book">
        <a href="/bookInfo/${book.id}"><img src="/book/getCover/${book.id}/1" onerror="this.src='/resources/images/no-cover.png'"/>
        </a></div>
            <h3><a href="/bookInfo/${book.id}">${book.name}</a></h3>
            <div class="book-detail">
                <hr/>
        <%--<br/><spring:message code="book.year"/>: ${book.yearOfPublication}
        <br/><spring:message code="book.description"/>: ${book.description}
        <br/><spring:message code="book.publisher"/>: ${book.publisher.name}<br/>--%>
                <div class="add-cart"></div>
            <c:choose>
                <c:when test="${book.favorite}"> <button class="deleteFavorite" id="favorite/${book.id}"><span>-</span><spring:message code="book.favorites.deletefromfavorites" /></button></c:when>
                <c:otherwise><button class="addFavorite" id="favorite/${book.id}"><span>+</span><spring:message code="book.favorites.addtofavorites" /></button></c:otherwise>
             </c:choose>
         </div>
    </div>
</c:forEach>
    </div>
<div class="clear"></div>

<div class="right-nav more-book"><a href="/catalog/all"><button class="button but-orange"><spring:message code="catalog.more"/></button></a></div>