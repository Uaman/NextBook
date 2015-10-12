<%--
  Created by IntelliJ IDEA.
  User: Lena
  Date: 22.09.2015
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="sign-in-form" class="popup-default block-login" style="display: none;">
  <span class="block-title"><spring:message code="global.signIn" /></span>
  <div class="block-content">
    <form:form action="static/j_spring_security_check" method="POST">
      <label><spring:message code="user.info.email" />:<input type="text" name="j_username" class="input-text"/></label><br />
      <label><spring:message code="user.info.password" />:<input type="password"  name="j_password" class="input-text"/></label><br />
      <input id="remember_me" name="_spring_security_remember_me" type="checkbox"/>
      <label for="remember_me"> <spring:message code="global.rememberMe" /> </label><br />
      <input type="submit" value='<spring:message code="global.signIn" />' class="button but-orange"/>
    </form:form>
    <button class="close but-close but-gray" title='<spring:message code="button.close"/>'>x</button>
  </div>
</div>
<div class="shadow" style="display: none;"></div>
