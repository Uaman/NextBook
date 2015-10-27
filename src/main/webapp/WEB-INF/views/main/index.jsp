<%--
  Created by IntelliJ IDEA.
  User: borsch
  Date: 7/22/2015
  Time: 12:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="index.title"/></title>
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/main/sign.in.js"></script>
    <script src="/resources/js/jquery.bxSlider.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="/resources/js/jquery.nivo.slider.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            <c:if test="${numberOfLastBooks ne 0}">
                $('#book-slider').bxSlider({
                    displaySlideQty: 6,
                    moveSlideQty: 2,
                    auto: false,
                    autoControls: false,
                    autoHover: true,
                    pager: false,
                    speed: 300,
                    pause: 3000,
                    controls: true
                });
            </c:if>

            $('#slider').nivoSlider();
        });
    </script>

    <link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700italic,700&subset=la-tin-ext,cyrillic-ext'
          rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bx_styles/bx_styles.css"/>
    <link rel="stylesheet" href="/resources/css/nivo-slider/nivo-slider.css" type="text/css" media="screen" />
    <link rel="icon" href="/resources/favicon.ico" type="image/x-icon" />
</head>
<body>
<div class="wrapper index">
    <div class="page">
        <jsp:include page="../../template/default/headerContent.jsp"/>
    </div>
</div>
<jsp:include page="../auth/signinPopup.jsp"/>
<div class="page content index">
    <div class="slider-wrapper theme-default">
        <div id="slider" class="nivoSlider">
            <img src="../../resources/images/slides/autumn.jpg" data-thumb="../../resources/images/slides/autumn.jpg" alt="" />
            <img src="../../resources/images/slides/winter.jpg" data-thumb="../../resources/images/slides/winter.jpg" alt=""  />
            <img src="../../resources/images/slides/spring.jpg" data-thumb="../../resources/images/slides/spring.jpg" alt="" data-transition="slideInLeft" />
            <img src="../../resources/images/slides/summer.jpg" data-thumb="../../resources/images/slides/summer.jpg" alt="" />
        </div>
    </div>

    <div class="row">
        <div class="page-title"><h2><spring:message code="catalog.new"/></h2></div>
            <jsp:include page="book-shelf.jsp"/>
    </div>
</div>
<jsp:include page="footer-block.jsp"/>
<jsp:include page="../../template/default/footer.jsp"/>
</body>
</html>
