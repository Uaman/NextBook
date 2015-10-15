<%--
  Created by IntelliJ IDEA.
  User: KutsykV
  Date: 11.10.2015
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/catalog/categories" var="categories"/>
<script>
    $(document).ready(
            function () {
                $.getJSON('${categories}', {
                    ajax: 'true'
                }, function (data) {
                    var html = '';
                    var length = data.length;
                    for (var i = 0; i < length; i++) {
                        html += '<li><a href="/catalog/' + data[i].link + '">' + data[i].name + '</a>';
                        var subCategories = data[i].subcategories;
                        var len = subCategories.length;
                        html += '<ul>';
                        for (var j = 0; j < len; j++)
                            html += '<li><a href="/catalog/' + data[i].link + '/' + subCategories[j].link + '">'
                                    + subCategories[j].name + '</a></li>';
                        html += '</ul></li>'
                    }
                    $('#filter').html(html);
                });
            });
</script>
<ul id="filter" class="categories">
</ul>
