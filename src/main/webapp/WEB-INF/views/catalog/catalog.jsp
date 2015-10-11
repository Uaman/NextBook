<%--
  Created by IntelliJ IDEA.
  User: KutsykV
  Date: 11.10.2015
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="catalog.title"/></title>
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>

    <link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700italic,700&subset=la-tin-ext,cyrillic-ext'
          rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>

</head>
<body>
<div class="wrapper">
    <div class="page">
        <jsp:include page="../../template/default/headerContent.jsp"/>
    </div>
</div>
<jsp:include page="../auth/signinPopup.jsp"/>
<jsp:include page="../catalog/bookShelf.jsp"/>
</body>
</html>
