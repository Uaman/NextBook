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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="index.title"/></title>
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/main/index.js"></script>

    <link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700italic,700&subset=latin-ext,cyrillic-ext'
          rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>


    <c:url var="categories" value="/categories"/>
    <c:url var="subcategories" value="/subcategories"/>
    <c:url var="getBooks" value="/getbooks"/>
    <script type="text/javascript">
        $(document).ready(
                function () {
                    $.getJSON('categories', {
                        ajax: 'true'
                    }, function (data) {
                        var html = '<option value=""><spring:message code="book.category"/></option>';
                        var len = data.length;
                        for (var i = 0; i < len; i++) {
                            html += '<option value="' + data[i].id + '">'
                                    + data[i].nameUa + '</option>';
                        }
                        html += '</option>';
                        $('#category').html(html);
                    });
                });

        $(document).ready(function () {
            $('#category').change(
                    function () {
                        $.getJSON('${subcategories}', {
                            category: $(this).val(),
                            ajax: 'true'
                        }, function (data) {
                            var html = '<option value=""><spring:message code="book.subcategory"/></option>';
                            var len = data.length;
                            for (var i = 0; i < len; i++) {
                                html += '<option value="' + data[i].id + '">'
                                        + data[i].nameUa + '</option>';
                            }
                            html += '</option>';
                            $('#subCategory').html(html);
                        });

                        $.getJSON('${getBooks}', {
                            category: $(this).val(),
                            ajax: 'true'
                        }, function (data) {
                            var html = '<table>';
                            var len = data.length;
                            for (var i = 0; i < len; i++) {
                                html += '<tr><td><a href="/bookInfo/' + data[i].id + '">'
                                        + data[i].uaName
                                        + '</td></tr>';
                            }
                            html += '</table>';
                            $('#catalog').html(html);
                        });
                    });
        });
        $(document).ready(function () {
            $('#subCategory').change(
                    function () {
                        $.getJSON('${getBooks}', {
                            subcategory: $(this).val(),
                            ajax: 'true'
                        }, function (data) {
                            var html = '<table>';
                            var len = data.length;
                            for (var i = 0; i < len; i++) {
                                html += '<tr><td><a href="/bookInfo/' + data[i].id + '">'
                                        + data[i].uaName
                                        + '</td></tr>';
                            }
                            html += '</table>';
                            $('#catalog').html(html);
                        });
                    });
        });
        $(document).ready(function () {
            $.getJSON('${getBooks}', {
                ajax: 'true'
            }, function (data) {
                var html = '<table>';
                var len = data.length;
                for (var i = 0; i < len; i++) {
                    html += '<tr><td><a href="/bookInfo/' + data[i].id + '">'
                            + data[i].uaName
                            + '</td></tr>';
                }
                html += '</table>';
                $('#catalog').html(html);
            });
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


<form:form modelAttribute="bookCatalog">
    <fieldset>
        <form:select id="category" path="category">
        </form:select>

        <form:select id="subCategory" path="subCategory">
            <option value=""><spring:message code="book.subcategory"/></option>
        </form:select>
    </fieldset>
</form:form>
<div id="catalog">

</div>

</body>
</html>
