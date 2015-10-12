<%--
  Created by IntelliJ IDEA.
  User: Lena
  Date: 21.09.2015
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="header-container">
  <div class="top-switch-bg">
    <div class="row clearfix">
      <div class="grid_6">language-switch</div>
      <div class="grid_6">
        <ul class="links">

          <security:authorize access="isAnonymous()">
            <li id="sign-in" class="signin"><spring:message code="global.signIn" /></li>
            <li><a href="/signup"><spring:message code="global.signUp" /></a></li>
          </security:authorize>

          <security:authorize access="isAuthenticated()">
            <li><a href="/cabinet/profile"><spring:message code="user.info.profile" /></a></li>
          </security:authorize>

          <security:authorize access="@Secure.isPublisher()">
            <li><a href="/book/new-book">Add book</a></li>
          </security:authorize>

          <security:authorize access="@Secure.isAdmin()">
            <li><a href="#">Management</a>
              <ul>
                <li><a href="/admin/books/all"><spring:message code="books.manageBooks" /></a></li>
                <li><a href="/admin/users/all"><spring:message code="users.manageUsers" /></a></li>
                <li><a href="/admin/authors/all"><spring:message code="authors.manageAuthors" /></a></li>
                <li><a href="/admin/publishers/all"><spring:message code="publishers.managePublishers" /></a></li>
              </ul>
            </li>
          </security:authorize>

          <security:authorize access="isAuthenticated()">
            <li><a href="/static/j_spring_security_logout"><spring:message code="global.exit" /></a></li>
          </security:authorize>

        </ul>
      </div>
    </div>
  </div>
  <div class="header-wrapper">
    <header>
      <div class="row clearfix">
        <div class="grid_12">
          <a href="/" class="logo">
            <img src="<c:url value='../../../resources/images/logo.png'/>"/>
          </a>
          <div class="nav-container">
            <nav>
            </nav>
            <div class="top-dropdowns">
              <div class="search-top-container">
                <div class="search-top"></div>
                <div class="search-form">
                  <form id="search_mini_form" action="" method="get">
                    <div class="form-search">
                      <input id="search" type="text" name="q" value="" class="input-text" autocomplete="off" placeholder="Search book">
                      <button type="submit" title="Пошук"></button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
  </div>

</div>
