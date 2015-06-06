<%--
  Created by IntelliJ IDEA.
  User: Kutsyk
  Date: 03.06.2015
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="<c:url value="../../resources/bootstrap-3.3.2-dist/css/bootstrap.min.css"/>">
    <!-- Optional theme -->
    <link rel="stylesheet" href="<c:url value="../../resources/bootstrap-3.3.2-dist/css/bootstrap-theme.min.css"/>">
    <link rel="stylesheet" href="<c:url value="../../resources/bootstrap-3.3.2-dist/css/bootstrap-theme.css"/>">
    <!-- Latest compiled and minified JavaScript -->
    <script src="<c:url value="../../resources/bootstrap-3.3.2-dist/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="../../resources/js/jquery.min.js" />"></script>
    <title>Upload File Request Page</title>
</head>
<body>
<div class="container">
    <form method="POST" action="uploadFile" enctype="multipart/form-data">
        File to upload: <input type="file" name="file"><br/>
        <input type="submit" class="btn btn-primary btn-lg" value="Upload"> Press here to upload the file!
    </form>
</div>
</body>
</html>