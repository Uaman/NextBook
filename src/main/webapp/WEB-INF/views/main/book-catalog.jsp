<%--
  Created by IntelliJ IDEA.
  User: Polomani
  Date: 26.09.2015
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="/resources/js/jquery-2.1.3.min.js"></script>
<script src="/resources/js/jquery.validate.min.js"></script>
<script src="/resources/js/main/index.js"></script>

<c:url var="categories" value="/categories" />
<script type="text/javascript">
    $(document).ready(
            function() {
                $.getJSON('${categories}', {
                    ajax : 'true'
                }, function(data) {
                    var html = '<option value="">Categories</option>';
                    var len = data.length;
                    for ( var i = 0; i < len; i++) {
                        html += '<option value="' + data[i] + '">'
                                + data[i] + '</option>';
                    }
                    html += '</option>';
                    $('#category').html(html);
                });
            });
</script>

<c:url var="subcategories" value="/subcategories" />
<script type="text/javascript">
    $(document).ready(function() {
        $('#category').change(
                function() {
                    $.getJSON('${subcategories}', {
                        stateName : $(this).val(),
                        ajax : 'true'
                    }, function(data) {
                        var html = '<option value="">Subcategory</option>';
                        var len = data.length;
                        for ( var i = 0; i < len; i++) {
                            html += '<option value="' + data[i] + '">'
                                    + data[i] + '</option>';
                        }
                        html += '</option>';
                        $('#subCategory').html(html);
                    });
                });
    });
</script>

<script type="text/javascript">
    $(document).ready(function(){
        $("#subCategory").change(onSelectChange);
    });

    function onSelectChange() {
        var selected = $("#subCategory option:selected");
        var output = "";
        if(selected.val() != 0){
            output = "You selected City " + selected.text();
        }
        $("#output").html(output);
    }
</script>

<form:form modelAttribute="bookCatalog">
    <fieldset>
        <form:select id="category" path="category">
        </form:select>

        <form:select id="subCategory" path="subCategory">
        </form:select>
    </fieldset>
    <p>
        <button type="submit">Sign Up</button>
    </p>
</form:form>
<div id="output">

</div>
