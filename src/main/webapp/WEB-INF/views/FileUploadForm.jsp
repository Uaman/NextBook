<%--
  Created by IntelliJ IDEA.
  User: Kutsyk
  Date: 16.03.15
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
<!-- Latest compiled and minified CSS -->
<!-- Optional theme -->
<link rel="stylesheet"  type="text/css"  href="<c:url value="../../resources/css/style.css"/>">
<!-- Latest compiled and minified JavaScript -->
<script src="<c:url value="../../resources/js/jquery.min.js" />"></script>
<script src="<c:url value="../../resources/js/jquery.searchable-1.0.0.min.js" />"></script>
<script src="<c:url value="../../resources/js/jquery.searchable.js" />"></script>
<script src="<c:url value="../../resources/js/table.js" />"></script>
<head>
    <title></title>
  <style>
    .error {
      color: #ff0000;
    }

    .errorblock {
      color: #000;
      background-color: #ffEEEE;
      border: 3px solid #ff0000;
      padding: 8px;
      margin: 16px;
    }
  </style>
</head>
<body>
<h2>Spring MVC file upload example</h2>

<form:form method="POST" commandName="fileUploadForm"
           enctype="multipart/form-data">

  <form:errors path="*" cssClass="errorblock" element="div" />

  Please select a file to upload : <input type="file" name="file" />
  <input type="submit" value="upload" />
		<span><form:errors path="file" cssClass="error" />
		</span>

</form:form>

</body>
</html>