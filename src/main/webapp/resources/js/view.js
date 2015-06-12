$(document).ready(function() {
    var pdfDocument = null;
    var currentPage = 1;
    var pageRendering = false;
    var pageNumPending = null;
    var scale = 0.8;
    var canvas = $('#canvas')[0];
    var context = canvas.getContext('2d');

    PDFJS.getDocument(params).then(function (pdfDoc_) {
        pdfDocument = pdfDoc_;
        $('#number-of-pages').text(pdfDocument.numPages);
        $('#current-page').text(currentPage);
        $('#scale').text(scale);
        renderPage(currentPage);
    });

    function renderPage(num) {
        pageRendering = true;
        pdfDocument.getPage(num).then(function (page) {
            var viewport = page.getViewport(scale);
            canvas.height = viewport.height;
            canvas.width = viewport.width;

            var renderContext = {
                canvasContext: context,
                viewport: viewport
            };
            var renderTask = page.render(renderContext);

            renderTask.promise.then(function () {
                pageRendering = false;
                if (pageNumPending !== null) {
                    // New page rendering is pending
                    renderPage(pageNumPending);
                    pageNumPending = null;
                }
            });
        });

        $('#current-page').text(currentPage);
    }

    function queueRenderPage(num) {
        if (pageRendering) {
            pageNumPending = num;
        } else {
            renderPage(num);
        }
    }

    $('#prev').click(function () {
        if (currentPage <= 1) {
            return;
        }
        --currentPage;
        queueRenderPage(currentPage);
    });

    $('#next').click(function () {
        if (currentPage >= pdfDocument.numPages) {
            return;
        }
        ++currentPage;
        queueRenderPage(currentPage);
    });

    $('#set-scale').click(function(){
        var _scale = parseFloat($('#scale').val());
        if(!isNaN(_scale)){
            scale = _scale;
            renderPage(currentPage);
        }
    });
    $('#set-page').click(function(){
        var _page = parseInt($('#page').val());
        if(!isNaN(_page) && _page <= pdfDocument.numPages){
            currentPage = _page;
            renderPage(currentPage);
        }
    });
});