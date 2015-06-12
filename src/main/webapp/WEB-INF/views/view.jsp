<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${title}</title>
    <script src="<c:url value="../../resources/js/jquery.min.js" />"></script>
    <script>
        <jsp:include page="js.jsp/pdf.min.js.jsp" />
        PDFJS.workerSrc = '<c:url value="../../resources/js/pdf.js/pdf.worker.min.js" />';
        var params = {
            url: '${urlToFile}'
        };
    </script>
    <script src="<c:url value='../../resources/js/view.js' />"></script>
</head>
<body>
    <div>
        Scale: <input type="text" id="scale" /> <button id="set-scale">Set Scale</button>
    </div>
    <div>
        Page: <input type="text" id="page" /> <button id="set-page">Set Page</button>
    </div>
    <div>
        <span id="current-page"></span> / <span id="number-of-pages"></span>
    </div>
    <div>
        <button id="prev">Previous</button>
        <button id="next">Next</button>
    </div>
    <canvas id="canvas" width="300px" height="400px" style="border: 1px solid !important;"></canvas>
</body>
</html>
