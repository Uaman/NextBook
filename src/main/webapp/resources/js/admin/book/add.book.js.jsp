<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
var KEYWORD_X = 'keyword-x';
var AUTHOR_X = 'author-x';
var BOOK_ID = ${book.id};
//var firstPageUploaded = false;
//var lastPageUploaded = false;
//var bookUploaded = true;
var firstPageUploaded = true;
var lastPageUploaded = true;
var bookUploaded = true;
var isbnExist = false;
$(document).ready(function(){

    paperBookChecked();
    formImages(${numberOfPhotos});
    Galleria.loadTheme('/resources/js/galleria/theme/galleria.classic.min.js');
    Galleria.run('.galleria');

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
    $('#edit-book-form').validate({
        ignore: '', //allow validate hidden fields
        rules: {
            isbn: {
                required: true,
                validIsbn: true
            },
            name_ua: {
                required: true
            },
            authors: {
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
                //required: true
                //keywordsNumber: true
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
            authors: {
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
                //required: '<spring:message code="book.registration.keywords.require"/>'
            }
        },
        errorLabelContainer: $("div.errorblock"),
        wrapper: 'li',
        submitHandler: function(form){
            if($('.text-tags:eq(0)').find('.text-tag').length + $('.author').length < 1){ //divs with authors
                alert('authors require');
                return;
            }
            if($('.text-tags:eq(1)').find('.text-tag').length + $('.keyword').length < 5){ //divs with authors
                alert('keywords at least 5 require');
                return;
            }
            /*if(isbnExist) {
             alert('isbn exist');
             return;
             }*/
            $.ajax({
                url: '/book/edit-book',
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

    $('#add-author-button').click(function(){
        $('#add-author-form').show();
        $('.shadow').show();
    });
    $('#close').click(function(){
        $('#add-author-form').hide();
        $('.shadow').hide();
    });

    $('#send-author-form').click(function(){
        var json = {
            firstNameUa: $('#author-first-name-ua').val(),
            lastNameUa: $('#author-last-name-ua').val(),
            firstNameRu: $('#author-first-name-ru').val(),
            lastNameRu: $('#author-last-name-ru').val(),
            firstNameEn: $('#author-first-name-en').val(),
            lastNameEn: $('#author-last-name-en').val()
        };
        $.ajax({
            url: '/admin/authors/update-author',
            type: 'POST',
            data: JSON.stringify(json),
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data){
                console.log(data);
            },
            error: function(e){
                console.log(e);
            }
        })
    });

    //appendAjaxFormToButtonWithProgress('#send-book', '#book-form', '#container-progress-book', '#progress-bar-book');
    //appendAjaxFormToButtonWithProgress('#send-last-page', '#last-page-form', '#container-progress-last-page', '#progress-bar-last-page');
    //appendAjaxFormToButtonWithProgress('#send-first-page', '#first-page-form', '#container-progress-first-page', '#progress-bar-first-page');
    $('#paper').change(function(){
        paperBookChecked();
    });

    $('#isbn').change(function(){
        $.ajax({
            url: '/admin/books/check-isbn/'+$('#isbn').val(),
            type: 'POST',
            success: function(data){
                isbnExist = data;
            }
        });
    });

    $('#book').change(function(){
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

    $('#last_page').change(function(){
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

    $('#first_page').change(function(){
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

    $('#authors').textext({
        plugins : 'autocomplete tags'
    }).bind('getSuggestions', function(e, data){
        var query = (data ? data.query : '') || '';
        var self = this;
        $.ajax({
            url: '/admin/books/authors-auto-complete/'+query,
            type: 'POST'
        }).done(function(response){
            $(self).trigger(
                    'setSuggestions',
                    { result : response }
            );
        });
    });

    CLASS = AUTHOR_X;

    <c:if test="${authors ne null}">
    <c:forEach items="${authors}" var="author">
    $('#authors').textext()[0].tags().addTags([{value: '${author.name}', id: ${author.id}}]);
    </c:forEach>
    </c:if>

    $('#keywords').textext({
        plugins : 'autocomplete tags'

    }).bind('getSuggestions', function(e, data){
        var query = (data ? data.query : '') || '';
        var self = this;
        $.ajax({
            url: '/admin/books/keywords-auto-complete/'+query,
            type: 'POST'
        }).done(function(response){
            $(self).trigger(
                    'setSuggestions',
                    { result : response }
            );
        });
    });

    CLASS = KEYWORD_X;

    <c:if test="${book.keywords ne null}">
    <c:forEach items="${book.keywords}" var="keyword">
    $('#keywords').textext()[0].tags().addTags([{value: '${keyword.keyword}', id: ${keyword.id}}]);
    </c:forEach>
    </c:if>

    $('.keyword-x').click(function(){
        var id = $(this).attr('id');
        $.ajax({
            url: '/book/delete-keyword/'+BOOK_ID+'/'+id,
            type: 'POST',
            success: function(data){
                if(data){
                    $('#keyword-'+id).remove();
                }
            },
            error: function(e){
                console.log(e);
            }
        })
    });

    $('.author-x').click(function(){
        var id = $(this).attr('id');
        $.ajax({
            url: '/book/delete-author/'+BOOK_ID+'/'+id,
            type: 'POST',
            success: function(data){
                if(data){
                    $('#author-'+id).remove();
                }
            },
            error: function(e){
                console.log(e);
            }
        })
    });

    Dropzone.options.myAwesomeDropzone = {
        paramName: "file", // The name that will be used to transfer the file
        acceptedFiles: 'image/*',
        maxFilesize: 2, // MB
        init: function() {
            this.on("complete", function(file) {
                this.removeFile(file);
            });
            this.on("success", function (file, resp) {
                console.log(resp);
                if (resp != -1) {
                    reloadGallery(resp);
                }
            });
        }
    };
    $('body').on('click', '.delete-image', function(){
        var photoId = $(this).val();
        $.ajax({
            url: '/admin/books/delete-gallery-image/'+BOOK_ID+'/'+photoId,
            type: 'POST',
            success: function(data){
                if(data != -1){
                    $('#image-'+photoId).remove();
                    reloadGallery(data);
                }
            },
            error: function(e){
                console.log(e);
            }
        })
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
        keywords: function(){
            var array = [];
            var tags = $('.text-tags:eq(1)').find('.text-tag');
            $.each(tags, function( index, div ){
                array.push($(div).text());
            });
            return array;
        }(),
        authors: function(){
            var array = [];
            var tags = $('.text-tags:eq(0)').find('.text-tag').find('span');
            $.each(tags, function( index, div ){
                var id = $(div).attr('id');
                id = id.substr(id.lastIndexOf('-')+1);
                if(!id || id == '' || id < 1)
                    id = 0;
                array.push(id);
            });
            return array;
        }(),
        eighteenPlus: $('#eighteen-plus').is(':checked'),
        numberOfPages: $('#number_of_pages').val(),
        subCategoryId: $('#category').val()
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

function formImages(numberOfPhotos){
    console.log((numberOfPhotos));
    if(numberOfPhotos < 1){
        $('#container_galleria').hide();
    } else {
        $('#container_galleria').show();
        $('#images').html('');
        $('.galleria').html('');
        var html = '';
        var htmlBig = '<ul>';
        for (var i = 0; i < numberOfPhotos; ++i) {
            html += '<div id="image-' + i + '"><img class="small-image" src="/book/getGalleryPhoto/' + BOOK_ID + '/' + i + '"/>';
            htmlBig += '<li><img src="/book/getGalleryPhoto/' + BOOK_ID + '/' + i + '"/></li>';
            html += '<br/><button class="delete-image" value="' + i + '">X</button></div>';
        }
        htmlBig += '</ul>';
        $('#images').html(html);
        $('.galleria').html(htmlBig);
    }
}

function reloadGallery(numberOfPhotos){
    console.log((numberOfPhotos));
    var gallery = Galleria.get(0);
    gallery.destroy();
    formImages(numberOfPhotos);
    Galleria.loadTheme('/resources/js/galleria/theme/galleria.classic.min.js');
    Galleria.run('.galleria');
    console.log((numberOfPhotos));
}
</script>