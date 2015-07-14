<%--
  Created by IntelliJ IDEA.
  User: Kutsyk
  Date: 03.06.2015
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>Upload File Request Page</title>
    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>

    <script src="<c:url value="/resources/js/jquery.ui.widget.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.iframe-transport.js" />"></script>
    <script src="<c:url value="/resources/js/jquery.fileupload.js" />"></script>
    <script src="<c:url value="/resources/bootstrap-3.3.2-dist/js/bootstrap.min.js" />"></script>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/bootstrap-3.3.2-dist/css/bootstrap.css"/>">

    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/dropzone.css"/>">
    <script src="<c:url value="/resources/js/myuploadfunction.js" />"></script>
</head>
<body>
<div class="container">
    <div style="width:500px;padding:20px">

        <input id="fileupload" type="file" name="files[]" data-url="/upload" multiple>

        <div id="dropzone" class="fade well">Drop files here</div>
        <div id="progress" class="progress">
            <div class="bar" style="width: 0%;"></div>
        </div>

        <table id="uploaded-files" class="table">
            <tr>
                <th>File Name</th>
                <th>File Size</th>
                <th>File Type</th>
                <th>View</th>
            </tr>
            <tbody>
            <c:if test="${!empty files}">
                <c:forEach items="${files}" var="file">
                    <tr>
                        <td>${file.fileName}</td>
                        <td>${file.fileSize}</td>
                        <td>${file.fileType}</td>
                        <td>
                            <form name="form" action="/view" method="POST">
                                <input type="hidden" name="url" value="${file.url}">
                                <input type="submit" value="view it"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>

    </div>
</div>
</body>
</html>