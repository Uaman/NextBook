<%--
  Created by IntelliJ IDEA.
  User: borsch
  Date: 7/22/2015
  Time: 12:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="index.title"/></title>
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/main/index.js"></script>

    <link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700italic,700&subset=latin-ext,cyrillic-ext' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/main.css"/>
</head>
<body>
<ul>

    <security:authorize access="isAnonymous()">
        <li id="sign-in"><spring:message code="global.signIn" /></li>
        <li><a href="/signup"><spring:message code="global.signUp" /></a></li>
    </security:authorize>

    <security:authorize access="isAuthenticated()">
        <li><a href="/cabinet/profile"><spring:message code="user.info.profile" /></a></li>
    </security:authorize>

    <security:authorize access="@Secure.isPublisher()">
        <li><a href="/book/new-book">Add book</a></li>
    </security:authorize>

    <security:authorize access="@Secure.isAdmin()">
        <li><a href="/admin/books/all"><spring:message code="books.manageBooks" /></a></li>
        <li><a href="/admin/users/all"><spring:message code="users.manageUsers" /></a></li>
        <li><a href="/admin/authors/all"><spring:message code="authors.manageAuthors" /></a></li>
        <li><a href="/admin/publishers/all"><spring:message code="publishers.managePublishers" /></a></li>
    </security:authorize>

    <security:authorize access="isAuthenticated()">
        <li><a href="/static/j_spring_security_logout"><spring:message code="global.exit" /></a></li>
    </security:authorize>

</ul>


    <div id="sign-in-form" class="popup-default block-login" style="display: none;">
        <span class="block-title"><spring:message code="global.signIn" /></span>
        <div class="block-content">
            <form:form action="static/j_spring_security_check" method="POST">
                <label><spring:message code="user.info.email" />:<input type="text" name="j_username" class="input-text"/></label><br />
                <label><spring:message code="user.info.password" />:<input type="password"  name="j_password" class="input-text"/></label><br />
                <input id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
                <label for="remember_me"> <spring:message code="global.rememberMe" /> </label><br />
                <input type="submit" value='<spring:message code="global.signIn" />' class="button but-orange"/>
            </form:form>
            <button id="close" class="but-close but-gray" title='<spring:message code="button.close"/>'>x</button>
        </div>
    </div>
    <div class="shadow" style="display: none;"></div>
</body>
</html>
