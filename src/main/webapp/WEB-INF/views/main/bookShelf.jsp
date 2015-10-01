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

<c:url var="getCategories" value="/categories"/>
<c:url var="subcategories" value="/subcategories"/>
<c:url var="getBooks" value="/getbooks"/>
<c:set var="last_book" value="${last_book}"/>
<c:set var="books_on_page" value="${books_on_page}"/>

<script type="text/javascript">
    var last_showed = ${last_book};
    var per_page = ${books_on_page};
    function printBooks(data) {
        var html = '';
        var len = data.length;
        var to_book = 0;
        console.log(len);
        console.log(last_showed);
        if (last_showed > len)
            return html;
        if (last_showed + per_page < len)
            to_book = last_showed + per_page;
        else
            to_book = len;

        for (var i = last_showed; i < to_book; i++) {
            html += '<div style="background-color: #dfe1e4"><p>' +
                    '<hr>' +
                    '<a href = "/bookInfo/' + data[i].id + '">' +
                    '<img src="/book/getCover/' + data[i].id + '/1" width="80" height="80" onerror="this.src=\'/resources/images/no-cover.png\'"/> <br />' +
                    '</a><spring:message code="book.title" />:<a href = "/bookInfo/' + data[i].id + '">' + data[i].uaName +
                    '</a><br/><spring:message code="book.year" />:' + data[i].yearOfPublication +
                    '<br/><spring:message code="book.description" />: ' + data[i].description +
                    '<br/><spring:message code="book.publisher" />: ' + data[i].publisher.name +
                    '<br/><hr></p></div>'
        }
        last_showed += per_page;
        return html;
    }
    function printAllBooks() {
        last_showed = 0;
        $.getJSON('${getBooks}', {
            ajax: 'true'
        }, function (data) {
            $('#catalog').html(printBooks(data, last_showed));
        });
    }
    function printAllSubCategorieForCategory(categoryValue) {
        $.getJSON('${subcategories}', {
            category: categoryValue,
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
    }

    $(document).ready(
            function () {
                $('#category').change(
                        function () {
                            last_showed = 0;
                            if ($(this).val() == -1) {
                                var html = '<option value=""><spring:message code="book.subcategory"/></option>';
                                $('#subCategory').html(html);
                                printAllBooks();
                            } else {
                                printAllSubCategorieForCategory($(this).val());
                            }
                            $.getJSON('${getBooks}', {
                                category: $(this).val(),
                                ajax: 'true'
                            }, function (data) {
                                $('#catalog').html(printBooks(data, last_showed));
                            });
                        });
                $('#subCategory').change(
                        function () {
                            last_showed = 0;
                            $.getJSON('${getBooks}', {
                                categroy: $('#catalog').value,
                                subcategory: $(this).val(),
                                ajax: 'true'
                            }, function (data) {
                                $('#catalog').html(printBooks(data, last_showed));
                            });
                        });
                printAllBooks();
            });
    $(window).scroll(
            function () {
                if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
                    $.getJSON('${getBooks}', {
                        category: $('#catalog').value,
                        subcategory: $('#subCategory').value,
                        ajax: 'true'
                    }, function (data) {
                        $('#catalog').append(printBooks(data, ${last_book}));
                    });
                }
            });
</script>

<form:form modelAttribute="bookCatalog">
    <fieldset>
        <form:select id="category" path="category">
            <option value="-1"><spring:message code="book.category"/></option>
            <c:forEach var="categ" items="${categories}">
                <form:option value="${categ.id}">${categ.nameUa}</form:option>
            </c:forEach>
        </form:select>
        <form:select id="subCategory" path="subCategory">
            <option value=""><spring:message code="book.subcategory"/></option>
        </form:select>
    </fieldset>
</form:form>
<div id="catalog">
</div>

