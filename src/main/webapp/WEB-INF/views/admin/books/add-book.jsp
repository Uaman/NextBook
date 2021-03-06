<%--
  Created by IntelliJ IDEA.
  User: borsch
  Date: 7/23/2015
  Time: 9:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Book</title>
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="/resources/js/jquery.ui.widget.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/jquery.mask.min.js"></script>
    <script src="/resources/js/jquery.form.min.js"></script>
    <script src="/resources/js/textext/textext.core.js"></script>
    <script src="/resources/js/textext/textext.plugin.autocomplete.js"></script>
    <script src="/resources/js/textext/textext.plugin.tags.js"></script>

    <script type="text/javascript" src="/resources/js/galleria/galleria-1.4.2.min.js"></script>
    <script type="text/javascript" src="/resources/js/dropzone.js"></script>
    <link type="text/css" rel="stylesheet" href="/resources/css/dropzone.css" />

    <link rel="stylesheet" type="text/css" href="/resources/css/textext/textext.core.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/textext/textext.plugin.tags.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/textext/textext.plugin.autocomplete.css"/>

    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>
    <jsp:include page="../../../../resources/js/admin/book/add.book.js.jsp"/>
    <style>
        .progress-container{
            width:500px;
            height:10px;
            border:1px solid black;
            border-radius: 5px;
        }
        .author, .keyword{
            background-color: red;
            padding: 5px;
            margin: 3px;
            border: 1px solid #990000;
            border-radius: 5px;
            min-width: 200px;
            width: 400px;
        }
        .author-x, .keyword-x{
        }
        .small-image{
            width: 200px;
            height: 150px;
        }
    </style>

</head>
<body>
<div class="errorblock">

</div>
<form id="edit-book-form">
    <label>ISBN*: <input type="text" name="isbn" id="isbn" value="${book.isbn}"/></label><br />
    <label>UA Name*:<input type="text" name="name_ua" id="name_ua" value="${book.uaName}"/></label><br />
    <label>EN Name:<input type="text" name="name_en" id="name_en" value="${book.enName}"/></label><br />
    <label>RU Name:<input type="text" name="name_ru" id="name_ru" value="${book.ruName}"/></label><br />
    <label>Author*: <textarea id="authors" name="authors" rows="2" cols="50"></textarea></label><br />
    <div id="add-author-button" style="width: 150px; height: 30px; border: 1px solid black; cursor: pointer;">Add new author</div>
    <label>Year Of Publication*: <input type="text" id="publication_year" name="publication_year" value="${book.yearOfPublication}"/></label><br />
    <input type="hidden" value="some-id" id="publisher-id" name="publisher-id" />
    <label>Language*: <input type="text" id="language" name="language" value="${book.language}"/></label><br />
    Type Of Book*:<br />
    <label>Electronic: <input type="checkbox" id="electronic" name="electronic" /></label><br />
    <label>Paper: <input type="checkbox" id="paper" name="paper"/></label>
    <br />
    <label>Number Of Pages: <input type="text" id="number_of_pages" name="number_of_pages" value="${book.numberOfPages}"/></label><br />
    <label>UA Description*:<textarea name="description_ua" id="description_ua">${book.descriptionUa}</textarea></label><br />
    <label>EN Description:<textarea name="description_en" id="description_en">${book.descriptionEn}</textarea></label><br />
    <label>RU Description:<textarea name="description_ru" id="description_ru">${book.descriptionRu}</textarea></label><br />
    <label>Keywords*:<textarea name="keywords" id="keywords" rows="2" cols="50"></textarea></label><br />
    <label>Eighteen Plus: <input type="checkbox" id="eighteen-plus"/></label><br />
    Category: <br />
    <select id="category">
        <c:if test="${subCategories ne null}">
            <c:forEach items="${subCategories}" var="subCategory">
                <option value="${subCategory.id}"
                        <c:if test="${book.subCategory.id eq subCategory.id}">
                            selected="selected"
                        </c:if>
                        >${subCategory.nameUa} : ${subCategory.category.nameUa}</option>
            </c:forEach>
        </c:if>
    </select>
    <br />
    <input type="hidden" name="test_files" id="test_files"/>
    <input type="submit" />
</form>
<form id="first-page-form" action="/admin/books/send-first-page" method="POST">
    <div id="container-progress-first-page" class="progress-container" style="display: none;">
        <div id="progress-bar-first-page" style="width:0%;background-color: #ffff00; height:inherit;"></div>
    </div>
    <input type="hidden" name="bookId" value="${book.id}">
    <label>First Page*:<input type="file" name="first_page" id="first_page"></label><br />
</form>
<form id="last-page-form" action="/admin/books/send-last-page" method="POST">
    <div id="container-progress-last-page" class="progress-container" style="display: none;">
        <div id="progress-bar-last-page" style="width:0%;background-color: green; height:inherit;"></div>
    </div>
    <input type="hidden" name="bookId" value="${book.id}">
    <label>Last Page:<input type="file" name="last_page" id="last_page"></label><br />
</form>
<form id="book-form" action="/admin/books/send-book" method="POST">
    <div id="container-progress-book" class="progress-container" style="display: none;">
        <div id="progress-bar-book" style="width:0%;background-color: red; height:inherit;"></div>
    </div>
    <input type="hidden" name="bookId" value="${book.id}">
    <label>Book*:<input type="file" name="book" id="book"></label><br />
</form>

<form action="/admin/books/send-gallery-photo" enctype="multipart/form-data" class="dropzone" id="my-awesome-dropzone">
    <input type="hidden" name ="bookId" value="${book.id}"/>
</form>

<div id="container_galleria">
    <div class="galleria" style="height: 600px;">
    </div>
</div>
<div id="images"></div>

<div id="add-author-form" class="popup-default" style="display: none;">
    Author First Name UA: <input type="text" id="author-first-name-ua"><br />
    Author Last Name UA: <input type="text" id="author-last-name-ua"><br />
    Author First Name EN: <input type="text" id="author-first-name-en"><br />
    Author Last Name EN: <input type="text" id="author-last-name-en"><br />
    Author First Name RU: <input type="text" id="author-first-name-ru"><br />
    Author Last Name RU: <input type="text" id="author-last-name-ru"><br />
    <input type="button" value="Add" id="send-author-form">
    <button id="close"><spring:message code="button.close"/></button>
</div>
<div class="shadow" style="display: none;"></div>
</body>
</html>
