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
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/jquery.mask.min.js"></script>
    <script src="/resources/js/jquery.form.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/resources/css/buttons.css"/>
    <style>
        .progress-container{
            width:500px;
            height:10px;
            border:1px solid black;
            border-radius: 5px;
        }
    </style>
    <script>
        var BOOK_ID = ${bookId};
        var firstPageUploaded = false;
        var lastPageUploaded = false;
        var bookUploaded = false;
        $(document).ready(function(){
            paperBookChecked();

            $('#publication_year').mask('0000');
            $('#number_of_pages').mask('000000');
            $.validator.addMethod(
                    'validIsbn',
                    function(value, element){
                        return element.value.match(/^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$/);
                    },
                    '<spring:message code="book.registration.isbn.format" />'
            );
            $.validator.addMethod(
                    'validYear',
                    function(value, element) {
                        var date = new Date();
                        var intValue = parseInt(element.value);
                        return intValue > 1000 && !(intValue > date.getFullYear());
                    },
                    '<spring:message code="book.registration.year.value" />'
            );
            $.validator.addMethod(
                    'oneTypeOfBook',
                    function(){
                        return $('#electronic').is(':checked') || $('#paper').is(':checked');
                    },
                    '<spring:message code="book.registration.type.choose.one" />'
            );
            $.validator.addMethod(
                    'numberOfPagesForPaperBook',
                    function(value, element){
                        if($('#paper').is(':checked')){
                            if(element.value.length < 1)
                                return false;
                        }
                        return true;
                    },
                    '<spring:message code="book.registration.pages.number.for.paper.require" />'
            );
            $.validator.addMethod(
                    'keywordsNumber',
                    function(value, element){
                        var array = element.value.split(',');
                        if(array.length < 5)
                            return false;
                        for(var i = 0; i < array.length; ++i)
                            if(array[i].length < 1)
                                return false;
                        return true;
                    },
                    '<spring:message code="book.registration.keywords.min.count" />'
            );
            $.validator.addMethod(
                    'lastPageForPaperBook',
                    function(value, element){
                        if(lastPageUploaded) {
                            return true;
                        }
                        if($('#paper').is(':checked'))
                            if(element.value) {
                                return true;
                            } else {
                                return false;
                            }
                        return true;
                    },
                    '<spring:message code="book.registration.last.page.paper.book.require" />'
            );
            $.validator.addMethod(
                    'firstPageUploaded',
                    function(value, element){
                        return firstPageUploaded;
                    },
                    '<spring:message code="book.registration.first.page.require"/>'
            );
            $.validator.addMethod(
                    'bookUploaded',
                    function(value, element){
                        return bookUploaded;
                    },
                    '<spring:message code="book.registration.book.require"/>'
            );
            $('#add-book-form').validate({
                ignore: '', //allow validate hidden fields
                rules: {
                    isbn: {
                        required: true,
                        validIsbn: true
                    },
                    name_ua: {
                        required: true
                    },
                    author: {
                        required: true
                    },
                    publication_year: {
                        required: true,
                        validYear: true
                    },
                    language: {
                        required: true
                    },
                    electronic: {
                        oneTypeOfBook: true
                    },
                    number_of_pages: {
                        numberOfPagesForPaperBook: true
                    },
                    description_ua: {
                        required: true,
                        minlength: 50
                    },
                    keywords: {
                        required: true,
                        keywordsNumber: true
                    },
                    test_files: {
                        firstPageUploaded: true,
                        lastPageForPaperBook: true,
                        bookUploaded: true
                    }
                },
                messages: {
                    isbn: {
                        required: '<spring:message code="book.registration.isbn.require"/>'
                    },
                    name_ua: {
                        required: '<spring:message code="book.registration.name.ua.require" />'
                    },
                    author: {
                        required: '<spring:message code="book.registration.author.require" />'
                    },
                    publication_year: {
                        required: '<spring:message code="book.registration.year.require"/>'
                    },
                    language: {
                        required: '<spring:message code="book.registration.language.require"/>'
                    },
                    description_ua: {
                        required: '<spring:message code="book.registration.description.ua.require"/>',
                        minlength: '<spring:message code="book.registration.description.ua.length"/>'
                    },
                    keywords: {
                        required: '<spring:message code="book.registration.keywords.require"/>'
                    }
                },
                errorLabelContainer: $("div.errorblock"),
                wrapper: 'li',
                submitHandler: function(form){
                    $.ajax({
                        url: '/book/add-book',
                        data: JSON.stringify(formDataBook()),
                        type: 'POST',
                        dataType: 'json',
                        cache: false,
                        contentType: false,
                        processData: false,
                        beforeSend: function(xhr) {
                            xhr.setRequestHeader("Accept", "application/json");
                            xhr.setRequestHeader("Content-Type", "application/json");
                        },
                        success: function(data){
                            if(data == 1){
                                alert('success');
                            } else {
                                alert('error');
                            }
                        },
                        error: function(e){
                            console.log(e);
                            alert('error');
                        }
                    });
                }
            });

            //appendAjaxFormToButtonWithProgress('#send-book', '#book-form', '#container-progress-book', '#progress-bar-book');
            //appendAjaxFormToButtonWithProgress('#send-last-page', '#last-page-form', '#container-progress-last-page', '#progress-bar-last-page');
            //appendAjaxFormToButtonWithProgress('#send-first-page', '#first-page-form', '#container-progress-first-page', '#progress-bar-first-page');
            $('#paper').change(function(){
                paperBookChecked();
            });

            $('#send-book').click(function(){
                $('#container-progress-book').show();
                $('#book-form').ajaxForm({
                    success:function(data) {
                        if(!data)
                            alert('error');
                        bookUploaded = data;
                        $('#container-progress-book').hide();
                    },
                    error: function(e){
                        alert('error');
                        $('#container-progress-book').hide();
                    },
                    uploadProgress: function (event, position, total, percentComplete) {
                        $('#progress-bar-book').width(percentComplete + '%');
                    },
                    dataType:"text"
                }).submit();
            });

            $('#send-last-page').click(function(){
                $('#container-progress-last-page').show();
                $('#last-page-form').ajaxForm({
                    success:function(data) {
                        if(!data)
                            alert('error');
                        lastPageUploaded = data;
                        $('#container-progress-last-page').hide();
                    },
                    error: function(e){
                        alert('error');
                        $('#container-progress-last-page').hide();
                    },
                    uploadProgress: function (event, position, total, percentComplete) {
                        $('#progress-bar-last-page').width(percentComplete + '%');
                    },
                    dataType:"text"
                }).submit();
            });

            $('#send-first-page').click(function(){
                $('#container-progress-first-page').show();
                $('#first-page-form').ajaxForm({
                    success:function(data) {
                        if(!data)
                            alert('error');
                        firstPageUploaded = data;
                        $('#container-progress-first-page').hide();
                    },
                    error: function(e){
                        alert('error');
                        $('#container-progress-first-page').hide();
                    },
                    uploadProgress: function (event, position, total, percentComplete) {
                        $('#progress-bar-first-page').width(percentComplete + '%');
                    },
                    dataType:"text"
                }).submit();
            });
        });
        function formDataBook(){
            var data = {
                bookId: BOOK_ID,
                isbn: $('#isbn').val(),
                uaName: $('#name_ua').val(),
                enName: $('#name_en').val(),
                ruName: $('#name_ru').val(),
                yearOfPublication: $('#publication_year').val(),
                language: $('#language').val(),
                typeOfBook: function(){
                    if($('#electronic').is(':checked') && $('#paper').is(':checked'))
                        return 'PAPER_AND_ELECTRONIC';
                    if($('#electronic').is(':checked'))
                        return 'ELECTRONIC';
                    if($('#paper').is(':checked'))
                        return 'PAPER';
                }(),
                descriptionUa: $('#description_ua').val(),
                descriptionEn: $('#description_en').val(),
                descriptionRu: $('#description_ru').val(),
                keywords: $('#keywords').val().split(','),
                author: $('#author').val(),
                eighteenPlus: $('#eighteen-plus').is(':checked'),
                numberOfPages: $('#number_of_pages').val()
            };
            return data;
        }
/*
        function appendAjaxFormToButtonWithProgress(buttonId, formId, progressContainerId, progressBarId){
            $(buttonId).click(function(){
                $(progressContainerId).show();
                $(formId).ajaxForm({
                    success:function(data) {
                        if(data){

                        } else {
                            alert('error');
                        }
                        alert('a');
                        $(progressContainerId).hide();
                    },
                    error: function(e){
                        alert('error');
                        $(progressContainerId).hide();
                    },
                    uploadProgress: function (event, position, total, percentComplete) {
                        $(progressBarId).width(percentComplete + '%');
                    },
                    dataType:"text"
                }).submit();
            });
        }
*/
        function paperBookChecked(){
            if($('#paper').is(':checked')){
                $('#last-page-form').show();
            } else {
                $('#last-page-form').hide();
            }
        }
    </script>
</head>
<body>
<div class="errorblock">

</div>
    <form id="add-book-form">
        <label>ISBN*: <input type="text" name="isbn" id="isbn" placeholder="ISBN" value="ISBN 5-02-013850-9"/></label><br />
        <label>UA Name*:<input type="text" name="name_ua" id="name_ua"/></label><br />
        <label>EN Name:<input type="text" name="name_en" id="name_en"/></label><br />
        <label>RU Name:<input type="text" name="name_ru" id="name_ru"/></label><br />
        <label>Author*: <input type="text" id="author" name="author" placeholder="Author name"/></label><br />
        <label>Year Of Publication*: <input type="text" id="publication_year" name="publication_year" placeholder="2015"/></label><br />
        <input type="hidden" value="some-id" id="publisher-id" name="publisher-id" />
        <label>Language*: <input type="text" id="language" name="language"/></label><br />
        Type Of Book*:<br />
            <label>Electronic: <input type="checkbox" id="electronic" name="electronic"/></label><br />
            <label>Paper: <input type="checkbox" id="paper" name="paper"/></label>
        <br />
        <label>Number Of Pages: <input type="text" id="number_of_pages" name="number_of_pages"/></label><br />
        <label>UA Description*:<textarea name="description_ua" id="description_ua"></textarea></label><br />
        <label>EN Description:<textarea name="description_en" id="description_en"></textarea></label><br />
        <label>RU Description:<textarea name="description_ru" id="description_ru"></textarea></label><br />
        <label>Keywords(divide with commas)*:<textarea name="keywords" id="keywords"></textarea></label><br />
        <label>Eighteen Plus: <input type="checkbox" id="eighteen-plus"/></label><br />
        <input type="hidden" name="test_files" id="test_files"/>
        <input type="submit" />
    </form>
    <form id="first-page-form" action="/book/send-first-page" method="POST">
        <div id="container-progress-first-page" class="progress-container" style="display: none;">
            <div id="progress-bar-first-page" style="width:0%;background-color: #ffff00; height:inherit;"></div>
        </div>
        <input type="hidden" name="bookId" value="${bookId}">
        <label>First Page*:<input type="file" name="first_page" id="first_page"></label><br />
        <div id="send-first-page" class="button">send</div>
    </form>
    <form id="last-page-form" action="/book/send-last-page" method="POST">
        <div id="container-progress-last-page" class="progress-container" style="display: none;">
            <div id="progress-bar-last-page" style="width:0%;background-color: green; height:inherit;"></div>
        </div>
        <input type="hidden" name="bookId" value="${bookId}">
        <label>Last Page:<input type="file" name="last_page" id="last_page"></label><br />
        <div id="send-last-page" class="button">send</div>
    </form>
    <form id="book-form" action="/book/send-book" method="POST">
        <div id="container-progress-book" class="progress-container" style="display: none;">
            <div id="progress-bar-book" style="width:0%;background-color: red; height:inherit;"></div>
        </div>
        <input type="hidden" name="bookId" value="${bookId}">
        <label>Book*:<input type="file" name="book" id="book"></label><br />
        <div id="send-book" class="button">send</div>
    </form>
</body>
</html>
