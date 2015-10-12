<%--
  Created by IntelliJ IDEA.
  User: KutsykV
  Date: 11.10.2015
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title><spring:message code="catalog.title"/></title>
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>

    <%--<link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700italic,700&subset=la-tin-ext,cyrillic-ext'--%>
          <%--rel='stylesheet' type='text/css'>--%>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>

    <script type="text/javascript">
        <%--var last_showed = ${last_book};--%>
        <%--var per_page = 5;--%>
        <%--function printBooks(data) {--%>
            <%--var html = '';--%>
            <%--var len = data.length;--%>
            <%--var to_book = 0;--%>
            <%--if (last_showed > len)--%>
                <%--return html;--%>
            <%--if (last_showed + per_page < len)--%>
                <%--to_book = last_showed + per_page;--%>
            <%--else--%>
                <%--to_book = len;--%>

            <%--for (var i = last_showed; i < to_book; i++) {--%>
                <%--html += '<div style="background-color: #dfe1e4"><p>' +--%>
                        <%--'<hr>' +--%>
                        <%--'<a href = "/bookInfo/' + data[i].id + '">' +--%>
                        <%--'<img src="/book/getCover/' + data[i].id + '/1" width="80" height="80" onerror="this.src=\'/resources/images/no-cover.png\'"/> <br />' +--%>
                        <%--'</a><spring:message code="book.title" />:<a href = "/bookInfo/' + data[i].id + '">' + data[i].uaName +--%>
                        <%--'</a><br/><spring:message code="book.year" />:' + data[i].yearOfPublication +--%>
                        <%--'<br/><spring:message code="book.description" />: ' + data[i].description +--%>
                        <%--'<br/><spring:message code="book.publisher" />: ' + data[i].publisher.name +--%>
                        <%--'<br/><hr></p></div>'--%>
            <%--}--%>
            <%--last_showed += per_page;--%>
            <%--return html;--%>
        <%--}--%>
        <%--function printAllBooks() {--%>
            <%--last_showed = 0;--%>
            <%--$.getJSON('${getBooks}', {--%>
                <%--ajax: 'true'--%>
            <%--}, function (data) {--%>
                <%--$('#catalog').html(printBooks(data, last_showed));--%>
            <%--});--%>
        <%--}--%>
        <%--$(document).ready(--%>
                <%--function () {--%>
                    <%--printAllBooks();--%>
                <%--});--%>

        <%--$(window).scroll(--%>
                <%--function () {--%>
                    <%--if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {--%>
                        <%--$.getJSON('${getBooks}', {--%>
                            <%--ajax: 'true'--%>
                        <%--}, function (data) {--%>
                            <%--$('#catalog').append(printBooks(data, ${last_book}));--%>
                        <%--});--%>
                    <%--}--%>
                <%--});--%>
    </script>

</head>
<body>
<div class="wrapper">
    <div class="page">
        <jsp:include page="../../template/default/headerContent.jsp"/>
    </div>
</div>
<jsp:include page="../auth/signinPopup.jsp"/>

<jsp:include page="book-filter.jsp"/>

<c:forEach var="book" items="${books}">
    <div style="background-color: #dfe1e4">
        <p>
        <hr>
        <a href="/bookInfo/${book.id}"><img src="/book/getCover/${book.id}/1" width="80" height="80"
                                            onerror="this.src='../../resources/images/no-cover.png'"/>
            <br/>
        </a><spring:message code="book.title"/>:<a href="/bookInfo/${book.id}">${book.name}</a>
        <br/><spring:message code="book.year"/>: ${book.yearOfPublication}
        <br/><spring:message code="book.description"/>: ${book.description}
        <br/><spring:message code="book.publisher"/>: ${book.publisher.name}
        <br/>
        <hr>
        </p>
    </div>
</c:forEach>

</body>
</html>
