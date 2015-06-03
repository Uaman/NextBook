<%--
  Created by IntelliJ IDEA.
  User: Kutsyk
  Date: 16.03.15
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="<c:url value="../../../resources/bootstrap-3.3.2-dist/css/bootstrap.min.css"/>">
<link rel="stylesheet" href="<c:url value="../../../resources/css/search.css"/>">
<!-- Optional theme -->
<link rel="stylesheet" href="<c:url value="../../../resources/bootstrap-3.3.2-dist/css/bootstrap-theme.min.css"/>">
<!-- Latest compiled and minified JavaScript -->
<script src="<c:url value="../../../resources/bootstrap-3.3.2-dist/js/bootstrap.min.js" />"></script>


<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <a class="navbar-brand blue" href="/">Knowledge finder</a>
    </div>
    <div class="col-sm-12 col-md-3">
        <form class="navbar-form navbar-left" action="search?page=1" method="post">
            <div class="input-group">
                <div class="input-group stylish-input-group">
                    <input type="text" name="query" class="form-control" placeholder="Search">
                    <span class="input-group-addon">
                        <button type="submit">
                            <span class="glyphicon glyphicon-search"></span>
                        </button>
                    </span>
                </div>
            </div>
        </form>
    </div>
</nav>