$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
        done: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            location.reload();
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css(
                'width',
                    progress + '%'
            );
        },
        dropZone: $('#dropzone')
    });
})
;