<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 10.07.2015
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="https://d26b395fwzu5fz.cloudfront.net/3.2.6/keen.min.js"></script>
    <script src="<c:url value="../../../resources/js/statistic/common-web.js" />" type="text/javascript"></script>
    <script src="<c:url value="../../../resources/js/statistic/statistic.min.js" />" type="text/javascript"></script>
    <title></title>

    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
</head>
<body>
<div class="wrapper">
    <div class="page">
        <jsp:include page="../../template/default/headerContent.jsp"/>
        <div class="row">
            <div class="page-title">
                <h1><spring:message code="user.info.my.profile"/></h1>
            </div>
        </div>
    </div>
    <div class="page content">
        <div class="row">
            <div class="left-block">
                <h2><spring:message code="user.page.title"/></h2>
                <ul>
                    <%--<security:authentication property="principal.authorities" />--%>
                    <security:authorize access="isAuthenticated()">
                        <li><a href="#" class="active-link"><spring:message code="user.info.my.profile"/></a></li>
                        <li><a href="/cabinet/edit-profile"><spring:message code="user.info.editProfile"/></a></li>
                        <li><a href="/static/j_spring_security_logout"><spring:message code="global.exit"/></a></li>
                        <c:if test="${hasFavorites}">
                        <li><a href="/user/favorites/"><spring:message code="user.info.favorites"/></a></li>
                        </c:if>
                    </security:authorize>

                    <security:authorize access="@Secure.isPublisher()">
                        <li><a href="/book/new-book"><spring:message code="user.page.add.book"/></a></li>
                    </security:authorize>
                    <c:choose>
                        <c:when test="${publisher ne null}">
                            <li><a href="/publisher/view?publisherId=${publisher.id}"><spring:message code="user.page.publisher"/></a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="/publisher/new"><spring:message code="user.page.new.publisher"/></a></li>
                        </c:otherwise>
                    </c:choose>

                    <security:authorize access="@Secure.isAdmin()">
                        <li><a href="/admin/books/all"><spring:message code="books.manageBooks"/></a></li>
                        <li><a href="/admin/users/all"><spring:message code="users.manageUsers"/></a></li>
                        <li><a href="/admin/authors/all"><spring:message code="authors.manageAuthors"/></a></li>
                        <li><a href="/admin/publishers/all"><spring:message code="publishers.managePublishers"/></a></li>
                    </security:authorize>
                </ul>
            </div>
            <div class="right-block">
                <security:authorize access="isAuthenticated()">
                    <spring:message code="user.info.name"/>: ${user.name}<br/>
                    <spring:message code="user.info.email"/>: ${user.email}<br/>
                    <spring:message code="user.info.role"/>: <spring:message code="role.${user.role.name}"/>
                </security:authorize>
            </div>
        </div>
    </div>
</div>

</body>
</html>
