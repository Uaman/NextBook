<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 09.07.2015
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="global.signUp"/></title>
    <script src="/resources/js/jquery-2.1.3.min.js"></script>
    <script src="/resources/js/jquery.validate.min.js"></script>
    <script src="/resources/js/main/sign.in.js"></script>
    <link href='https://fonts.googleapis.com/css?family=PT+Sans:400,400italic,700italic,700&subset=latin-ext,cyrillic-ext' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/popup.css"/>
    <jsp:include page="/resources/js/main/sign.up.js.jsp"></jsp:include>
</head>
<body>

  <div class="wrapper">
    <div class="page">
      <jsp:include page="../../template/default/headerContent.jsp"/>
        <div class="main-container">
          <div class="main row clearfix">
            <div class="col-main">
      <div id="sign-up-form" class="block-login">
        <span class="block-title"><spring:message code="global.signUp"/></span>
        <div class="errorblock">
          <ul></ul>
        </div>
        <div class="block-content">
          <div class="popup-legend"><spring:message code="sign.up.personal.information"/></div>
          <form id="register-form" action="/register" method="POST">
            <label><spring:message code="user.info.name" /><span class="req-star">*</span>:<input type="text" name="name" id="name" class="input-text"/></label>
            <label><spring:message code="user.info.email" /><span class="req-star">*</span>:<input type="text" name="email" id="email" class="input-text"/></label>
            <!--<label><spring:message code="user.info.role" />:
            <select name="roleId" id="roleId" class="input-text">
              <option value="1"><spring:message code="role.user" /></option>
              <option value="2"><spring:message code="role.author" /></option>
              <option value="3"><spring:message code="role.publisher" /></option>
            </select></label>-->
            <br/><br/>
            <div class="popup-legend"><spring:message code="sign.up.password"/></div>
            <label><spring:message code="user.info.password" /><span class="req-star">*</span>:<input type="password"  name="password" id="password" class="input-text"/></label>
            <label><spring:message code="user.info.confPass" /><span class="req-star">*</span>:<input type="password"  name="confirm_pass" id="confirm_pass" class="input-text"/></label><br />
            <span class="req-star">*<spring:message code="required.fields"/></span><br/>
            <input type="submit" value='<spring:message code="global.signUp" />' class="button but-orange"/>
          </form>
        </div>
      </div>
            </div>

    </div>
  </div>

   <jsp:include page="signinPopup.jsp"/>
   <jsp:include page="../../template/default/footer.jsp"/>
</body>
</html>
