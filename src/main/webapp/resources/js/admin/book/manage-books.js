/**
 * Created by Polomani on 06.08.2015.
 */

var orderBy;
var orderDirection;

function sendFilter () {
    var data = {
        id: $('#id').val(),
        name: $('#name').val(),
        from: $('#from').val(),
        max: $('#max').val(),
        isbn: $('#isbn').val(),
        subCategory: $('#subCategory').val(),
        yearOfPublication: $('#yearOfPublication').val(),
        publisher: $('#publisher').val(),
        language: $('#language').val(),
        typeOfBook: $('#typeOfBook').val(),
        numberOfPages: $('#numberOfPages').val(),
        orderBy: orderBy,
        orderDirection: orderDirection
    };

    if ($('#all').is(':checked')) {
        data.state = 'all';
    } else {
        data.state = $('#eighteenPlus').is(':checked') ? 'eighteenPlus' : 'noEighteenPlus';
    }

    $.ajax({
        url: '/admin/books/filter',
        data: JSON.stringify(data),
        type: 'POST',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data) {
            $('#added').empty();
            var html = '';
            for (var i = 0; i < data.length; ++i) {
                html += formBook(data[i]);
            }
            $('#added').append(html);
        },
        error: function (e) {
            console.log('Error: ' + JSON.stringify(e));
        }
    });
}

$(document).ready(function() {

    $('#send-filter').click(function () {sendFilter();});

    $(".order").click(function(e) {
        var o = $(e.currentTarget);
        e.preventDefault();
        e.stopPropagation();
        if (o.attr("href")=='ASC') {
            o.html('&uarr;');
            o.attr("href",'DESC');
            orderDirection = "DESC";
        } else {
            o.html('&darr;')
            o.attr("href",'ASC');
            orderDirection = "ASC"
        }

        switch (o.attr("id")) {
            case "orderId":
                orderBy = "ID";
                break;
            case "orderIsbn":
                orderBy = "ISBN";
                break;
            case "order18":
                orderBy = "EIGHTEEN_PLUS";
                break;
            case "orderYear":
                orderBy = "YEAR_OF_PUBLICATION";
                break;
            case "orderLanguage":
                orderBy = "LANGUAGE";
                break;
            case "orderType":
                orderBy = "TYPE_OF_BOOK";
                break;
            case "orderPages":
                orderBy = "NUMBER_OF_PAGES";
                break;
        }
        sendFilter();
    });

});

function formBook(book){
    var html ='<tr>' +
        '<td>' + book.id+ ' </td>' +
        '<td>' + book.isbn+ ' </td>' +
        '<td>' + book.uaName+ '<br />';
    if (book.ruName!=null)
        html += book.ruName+ '<br />';
    if (book.enName!=null)
        html += book.enName+ '<br /> ';
    html += '</td><td> ';
    for (var i = 0; i < book.authors.length; ++i) {
        var author = book.authors[i];
        html += author.firstNameUa + ' ' + author.lastNameUa + '<br /> ';
        if (author.firstNameRu!= null && author.firstNameRu!=null)
            html+= author.firstNameRu + ' ' + author.lastNameRu + '<br /> ';
        if (author.firstNameEn!= null && author.firstNameEn!=null)
            html+= author.firstNameEn + ' ' + author.lastNameEn + '<br /> ';
        html += '<hr />';
    }
    html += '</td>';
    html += '<td> ' +book.subCategory.category.nameUa+ ' -> ' +book.subCategory.nameUa+ '<br />' +
    book.subCategory.category.nameRu+ ' -> ' +book.subCategory.nameRu+ '<br /> ' +
    book.subCategory.category.nameEn+ ' -> ' +book.subCategory.nameEn+ '<br /> </td>' +
    ' <td> <b>';
    if (book.eighteenPlus) {
        html += '+';
    } else {
        html += '-';
    }
    html += '</b></td>' +
    '<td>' +book.yearOfPublication+ ' </td>' +
    '<td>' +book.publisher.nameUa+ '<br />';
    if (book.publisher.nameEn!=null)
        html += book.publisher.nameEn+ '<br />';
    if (book.publisher.nameRu!=null)
        html += book.publisher.nameRu+ '<br />';
    html += '</td><td>' +book.language+ ' </td>' +
    '<td>' +book.typeOfBook +'</td><td>';
    for (var i = 0; i < book.keywords.length; ++i) {
        html += book.keywords[i].keyword + ' ';
    }
    html+='</td>' +
    '<td>' +book.numberOfPages+ ' </td>' +
    '<td>' +book.descriptionUa+ '<br /><br />';
    if (book.descriptionRu!=null)
        html += book.descriptionRu+ '<br /><br />';
    if (book.descriptionEn!=null)
        html += +book.descriptionEn+ '<br /><br />';
    html+= '</td> ' +
    '<td> <a href="/admin/books/edit-book?bookId=' +book.id+ '">Edit</a> ' +
    '<a href="/admin/books/view/' +book.id+ '">View</a></td>' +
    '</tr>';
    return html;
}