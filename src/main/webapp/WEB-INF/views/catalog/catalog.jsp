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
    <script src="/resources/js/main/sign.in.js"></script>
    <jsp:include page="/resources/js/main/bookFavoriteButton.js.jsp"/>

    <link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700italic,700&subset=la-tin-ext,cyrillic-ext'
    rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>

    <c:url var="getBooks" value="/catalog/getBooksByCriterion"/>
    <script type="text/javascript">

        function getCriterion() {
            var data = {
                category: ${category},
                subCategory: ${subcategory}
            };
            return data;
        }

        var last_showed = ${last_book};
        var per_page = 3;

        function printBooks(data) {
            var html = '';
            var len = data.length;
            var to_book = 0;

            if (last_showed > len)
                return html;
            to_book = (last_showed + per_page < len) ? (last_showed + per_page) : len;
            for (var i = last_showed; i < to_book; i++) {
                html += '<div style="background-color: #dfe1e4"><p>' +
                        '<hr>' +
                        '<a href = "/bookInfo/' + data[i].id + '">' +
                        '<img src="/book/getCover/' + data[i].id + '/1" width="80" height="80" onerror="this.src=\'/resources/images/no-cover.png\'"/> <br />' +
                        '</a><spring:message code="book.title" />:<a href = "/bookInfo/' + data[i].id + '">' + data[i].uaName +
                        '</a><br/><spring:message code="book.year" />:' + data[i].yearOfPublication +
                        '<br/><spring:message code="book.description" />: ' + data[i].description +
                        '<br/><spring:message code="book.publisher" />: ' + data[i].publisher.name;
                if(data[i].favorite)
                    html += '<br/><button class="deleteFavorite" id="favorite/'+data[i].id+'"><spring:message code="book.favorites.deletefromfavorites" /></button>';
                else
                    html += '<br/><button class="addFavorite" id="favorite/'+data[i].id+'"><spring:message code="book.favorites.addtofavorites" /></button>';
                html += '<br/><hr></p></div>';
            }
            last_showed += per_page;
            return html;
        }

        $(document).ready(
                function () {
                    last_showed = 0;
                    $.getJSON('${getBooks}', {
                        bookCriterion: (getCriterion()),
                        ajax: 'true'
                    }, function (data) {
                        $('#catalog').html(printBooks(data, last_showed));
                        updateFavoriteButtons();
                    });
                });

        $(window).scroll(
                function () {
                    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
                        $.getJSON('${getBooks}', {
                            criterion: (getCriterion()),
                            ajax: 'true'
                        }, function (data) {
                            $('#catalog').append(printBooks(data, last_showed));
                            updateFavoriteButtons();
                        });
                    }
                });
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

<div id="catalog">
</div>

</body>
</html>
