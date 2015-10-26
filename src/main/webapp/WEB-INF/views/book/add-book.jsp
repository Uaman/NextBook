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
    <script src="/resources/js/spin/spin.js"></script>

    <script src="https://d26b395fwzu5fz.cloudfront.net/3.2.6/keen.min.js"></script>
    <script src="<c:url value="../../../resources/js/statistic/common-web.js" />" type="text/javascript"></script>
    <script src="<c:url value="../../../resources/js/statistic/statistic.min.js" />" type="text/javascript"></script>

    <link rel="stylesheet" type="text/css" href="/resources/css/textext/textext.core.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/textext/textext.plugin.tags.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/textext/textext.plugin.autocomplete.css"/>

    <script type="text/javascript" src="/resources/js/galleria/galleria-1.4.2.min.js"></script>
    <script type="text/javascript" src="/resources/js/dropzone.js"></script>
    <link type="text/css" rel="stylesheet" href="/resources/css/dropzone.css" />

    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
    <jsp:include page="../../../resources/js/book/add.book.js.jsp"/>
    <style>
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
        ul {
            list-style-type: none;
            padding: 0px;
            margin: 0px;
            text-align: left;
        }
        .small-image{
            width: 200px;
            height: 150px;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <div class="page">
        <jsp:include page="../../template/default/headerContent.jsp"/>
        <div class="row">
            <div class="page-title">
                <h1><spring:message code="add.book.title"/></h1>
            </div>
        </div>
    </div>
    <div class="page content">
        <div class="row">
            <div class="center-block">
                <h2><spring:message code="add.book.information"/></h2>
                <div class="errorblock">
                    <ul></ul>
                </div>
                <span class="req-star">* <spring:message code="required.fields"/></span><br/>
                <div class="edit-form">
                    <form id="edit-book-form">
                        <div class="left-box">
                            <label>
                                <span class="label-title"><spring:message code="add.book.isbn"/><span class="req-star">*</span>:</span>
                                <input type="text" name="isbn" id="isbn" value="${book.isbn}"/>
                            </label><br/><br/>

                            <h3><spring:message code="add.book.name"/></h3>
                            <label class="lang lang-ua">
                                <spring:message code="add.book.language.ua"/><span class="req-star">*</span>:
                                <input type="text" name="name_ua" id="name_ua" value="${book.uaName}"/><br/>
                            </label>
                            <label class="lang lang-en">
                                <spring:message code="add.book.language.en"/>:
                                <input type="text" name="name_en" id="name_en" value="${book.enName}"/><br/>
                            </label>
                            <label class="lang lang-ru">
                                <spring:message code="add.book.language.ru"/>:
                                <input type="text" name="name_ru" id="name_ru" value="${book.ruName}"/>
                            </label><br/><br/>

                            <label>
                                <span class="label-title"><spring:message code="add.book.author"/><span class="req-star">*</span>:</span>
                                <textarea id="authors" name="authors" rows="2"></textarea>
                                <span class="help" title="<spring:message code='add.book.help'/>">?</span>
                                <button id="add-author-button" class="button but-orange add-new"><spring:message code="add.book.new.author"/></button>
                            </label><br/><br/>

                            <label class="short">
                                <span class="label-title"><spring:message code="add.book.year.publication"/><span class="req-star">*</span>:</span>
                                <input type="text" id="publication_year" name="publication_year" value="${book.yearOfPublication}"/>
                            </label><br /><br/>
                            <input type="hidden" value="some-id" id="publisher-id" name="publisher-id" />

                            <label class="short">
                                <span class="label-title"><spring:message code="add.book.language"/><span class="req-star">*</span>:</span>
                                <input type="text" id="language" name="language" value="${book.language}"/>
                            </label><br /><br/>

                            <h3><spring:message code="add.book.type.book"/><span class="req-star">*</span>:</h3>
                                <label class="label-float"><spring:message code="add.book.type.electronic"/>: <input type="checkbox" id="electronic" name="electronic" /></label>
                                <label class="label-float"><spring:message code="add.book.type.paper"/>: <input type="checkbox" id="paper" name="paper"/></label><br /><br/>
                        </div>
                        <div class="right-box">
                            <h3><spring:message code="add.book.description"/></h3>
                            <label class="short lang lang-ua">
                                <spring:message code="add.book.language.ua"/><span class="req-star">*</span>:
                                <textarea name="description_ua" id="description_ua">${book.descriptionUa}</textarea>
                            </label><br />
                            <label class="short lang lang-en">
                                <spring:message code="add.book.language.en"/>:
                                <textarea name="description_en" id="description_en">${book.descriptionEn}</textarea>
                            </label><br />
                            <label class="short lang lang-ru">
                                <spring:message code="add.book.language.ru"/>:
                                <textarea name="description_ru" id="description_ru">${book.descriptionRu}</textarea>
                            </label><br /><br/>

                            <label>
                                <span class="label-title"><spring:message code="add.book.keywords"/><span class="req-star">*</span>:</span>
                                <textarea name="keywords" id="keywords" rows="3"></textarea>
                                <span class="help">?</span>
                            </label><br /><br/>

                            <label class="short">
                                <span class="label-title"><spring:message code="add.book.number.pages"/>:</span>
                                <input type="text" id="number_of_pages" name="number_of_pages" value="${book.numberOfPages}"/>
                            </label><br /><br/>

                            <label>
                                <span class="label-title"><spring:message code="add.book.eighteen.plus"/>:</span>
                                <input type="checkbox" id="eighteen-plus"/>
                            </label><br /><br/>

                            <label>
                                <span class="label-title"><spring:message code="add.book.category"/>:</span>
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
                            </label><br /><br/>
                            <input type="hidden" name="test_files" id="test_files"/>
                            <label>
                                <input type="submit" class="button but-orange add-new" value="<spring:message code='add.book.send'/>" />
                            </label>
                        </div>
					</form>
					<form id="first-page-form" action="/book/send-first-page" method="POST" class="edit-form">
						<div id="container-progress-first-page" class="progress-container" style="display: none;">
							<div id="progress-bar-first-page" style="width:0%;background-color: #ffff00; height:inherit;"></div>
						</div>
						<input type="hidden" name="bookId" value="${book.id}">
						<label>
                            <span class="label-title"><spring:message code="add.book.first.page"/><span class="req-star">*</span>:</span>
                            <input type="file" name="first_page" id="first_page">
                        </label><br />
					</form>
					<form id="last-page-form" action="/book/send-last-page" method="POST" class="edit-form">
						<div id="container-progress-last-page" class="progress-container" style="display: none;">
							<div id="progress-bar-last-page" style="width:0%;background-color: green; height:inherit;"></div>
						</div>
						<input type="hidden" name="bookId" value="${book.id}">
						<label>
                            <span class="label-title"><spring:message code="add.book.last.page"/><span class="req-star">*</span>:</span>
                            <input type="file" name="last_page" id="last_page">
                        </label><br />
					</form>
					<form id="book-form" action="/book/send-book" method="POST" class="edit-form">
						<div id="container-progress-book" class="progress-container" style="display: none;">
							<div id="progress-bar-book" class="progress-bar"></div>
						</div>
						<input type="hidden" name="bookId" value="${book.id}">
						<label>
                            <span class="label-title"><spring:message code="add.book.book"/><span class="req-star">*</span>:</span>
                            <input type="file" name="book" id="book">
                        </label><br />
					</form>
					<form action="/book/send-gallery-photo" enctype="multipart/form-data" class="dropzone" id="my-awesome-dropzone">
						<input type="hidden" name ="bookId" value="${book.id}"/>
					</form>
                    <div id="container_galleria">
                        <div class="galleria" style="height: 600px;">
                        </div>
                    </div>
                    <div id="images"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="add-author-form" class="popup-default block-author" style="display: none;">
    <span class="block-title"><spring:message code="add.book.popup.new.author"/></span>
    <div class="block-content">
        <div id="author-message" class="info-message">

        </div>
        <h3><spring:message code="add.book.author.first.name"/></h3>
        <label class="lang lang-ua">
            <spring:message code="add.book.language.ua"/>:
            <input type="text" id="author-first-name-ua" class="input-text" />
        </label>
        <label class="lang lang-en">
            <spring:message code="add.book.language.en"/>:
            <input type="text" id="author-first-name-en" class="input-text" />
        </label>
        <label class="lang lang-ru">
            <spring:message code="add.book.language.ru"/>:
            <input type="text" id="author-first-name-ru" class="input-text" />
        </label><br />
        <h3><spring:message code="add.book.author.last.name"/></h3>
        <label class="lang lang-ua">
            <spring:message code="add.book.language.ua"/>:
            <input type="text" id="author-last-name-ua" class="input-text" />
        </label>
        <label class="lang lang-en">
            <spring:message code="add.book.language.en"/>:
            <input type="text" id="author-last-name-en" class="input-text" />
        </label>
        <label class="lang lang-ru">
            <spring:message code="add.book.language.ru"/>:
            <input type="text" id="author-last-name-ru" class="input-text" />
        </label>
        <input type="button" class="button but-orange" value="Add" id="send-author-form" />
        <button class="close but-close but-gray" title="<spring:message code='button.close'/>">x</button>
    </div>
</div>
<div id="send-popup" class="popup-default block-info" style="display: none;">
    <span class="block-title"></span>
    <div class="block-content">
        <spring:message code="add.book.please.wait"/>
        <div id="spin" style="margin-top: 50px;"></div>
    </div>
</div>

<div id="success" class="popup-default block-info" style="display: none;">
    <span class="block-title success"><spring:message code="add.book.success"/></span>
    <div class="block-content">
        <button class="close button but-orange"><spring:message code="button.ok"/></button>
    </div>
</div>

<div id="error" class="popup-default block-info" style="display: none;">
    <span class="block-title error"><spring:message code="add.book.error"/></span>
    <div class="block-content">
        <button class="close button but-gray"><spring:message code="button.close"/></button>
    </div>
</div>

<div id="help-popup" class="popup-default" style="display: none;">
    <span class="block-title"><spring:message code="add.book.help"/></span>
    <div class="block-content">
        <spring:message code="book.registration.help"/>
        <button class="close button but-orange"><spring:message code="button.ok"/></button>
        <button class="close but-close but-gray">x</button>
    </div>
</div>
<div id="registration-ok" class="popup-default block-login" style="display: none;">
    <span class="block-title">Woohoo</span>
    <div class="block-content">
        Well done. Now you can move to your <br/><a href="/publisher/view?publisherId=${publisherId}">publisher cabinet</a><br/> and send book to admin for review
        <button class="close but-close but-gray" title='<spring:message code="button.close"/>'>x</button>
    </div>
</div>

<div id="problems-with-service" class="popup-default block-login" style="display: none;">
    <span class="block-title">Error</span>
    <div class="block-content">
        Oooops.. We have some problems. please wait for a while
        <button class="close but-close but-gray" title='<spring:message code="button.close"/>'>x</button>
    </div>
</div>
<div class="shadow" style="display: none;"></div>
</body>
</html>
