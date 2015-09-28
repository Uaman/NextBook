<%--
  Created by IntelliJ IDEA.
  User: Stacy
  Date: 9/28/15
  Time: 6:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
  <script src="/resources/js/jquery-2.1.3.min.js"></script>
  <script src="/resources/js/jquery.validate.min.js"></script>
  <script src="/resources/js/main/index.js"></script>

  <link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700italic,700&subset=latin-ext,cyrillic-ext' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
  <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>
</head>
<body>
  <div class="wrapper">
    <div class="page">
      <jsp:include page="../../template/default/headerContent.jsp"/>
    </div>
  </div>
  <jsp:include page="../auth/signinPopup.jsp"/>
  <img src="/book/getCover/${book.id}/1" width="80" height="100" onerror="this.src='/resources/images/no-cover.png'" align="left"/>
  <spring:message code="book.title" />: ${book.name}
  <c:if test="${book.eighteenPlus}">
    <spring:message code="book.adult"/>
  </c:if><br />
  <spring:message code="book.author" />:
  <c:forEach var="author" items="${book.authors}">
    ${author.name}<br />
  </c:forEach>
  <spring:message code="book.type" />: ${type} <br />
  <spring:message code="book.pages" />: ${book.pages} <br />
  <spring:message code="book.year" />: ${book.yearOfPublication} <br />
  <spring:message code="book.description" />: ${book.description} <br />
  <spring:message code="book.publisher" />: ${book.publisher.name} <br />
  ${category}:${book.subCategory}</br>
  <c:forEach var="keyword" items="${keywords}">
    ${keyword.keyword}
  </c:forEach>
</body>
</html>
