
<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 04.08.2015
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <title>Edit publisher</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<jsp:include page="../../../resources/js/publisher/edit-publisher.js.jsp"/>
<body>

<c:if test="${first&&!edit}">
  <spring:message code="publisher.register.firstpublisher" />
</c:if>
<form>
    <input id="id" type="hidden" value="${publisher.id}"/><br />
    <spring:message code="publisher.register.publisherNameUa" /> <input id="nameUa" value="${publisher.nameUa}"/><br />
    <spring:message code="publisher.register.nameRu" /> <input id="nameRu" value="${publisher.nameRu}"/><br />
    <spring:message code="publisher.register.NameEn" /> <input id="nameEn" value="${publisher.nameEn}"/><br />
    <spring:message code="publisher.register.description" /> <input id="description" value="${publisher.description}"/><br />
    <button id="send"><spring:message code="publisher.register.updatePublisher" /></button><br />

    <%--
  <c:if test="${!edit}">
    <spring:message code="publisher.register.publisherNameUa" /> <input id="nameUa"/><br />
    <spring:message code="publisher.register.nameRu" /> <input id="nameRu"/><br />
    <spring:message code="publisher.register.NameEn" /> <input id="nameEn"/><br />
    <spring:message code="publisher.register.description" /> <input id="description"/><br />
    <button id="send"><spring:message code="publisher.register.addPublisher" /></button><br />
  </c:if>
  --%>
</form>
<p id="message"></p>

</body>
</html>
