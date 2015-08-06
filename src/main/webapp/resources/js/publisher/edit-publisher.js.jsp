
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script>
    $(document).ready(function() {

        if (${first&&edit}) {
            $("#message").html('<spring:message code="publisher.register.form.saved" /><br/>' +
            '<a href="/book/new-book"><spring:message code="publisher.register.form.returnNewBookPage" /></a>');
        }

        $("#send").click(function () {
            var id = ($("#id")==null)?-1:$("#id").val();
            var data = {
                id: id,
                nameUa: $("#nameUa").val(),
                nameRu: $("#nameRu").val(),
                nameEn: $("#nameEn").val(),
                description: $("#description").val()
            };
            $.ajax({
                url: '/publisher/update',
                data: JSON.stringify(data),
                type: 'POST',
                dataType: 'json',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                },
                success: function (data) {
                    if (${edit}) {
                        $("#message").html('<spring:message code="publisher.register.form.saved" />');
                    } else {
                        window.location = "/publisher/update/"+data.id+"?first=true";
                        $("#message").html('<spring:message code="publisher.register.form.saved" /><br/>' +
                        '<a href="/book/new-book"><spring:message code="publisher.register.form.returnNewBookPage" /></a>');
                    }
                },
                error: function (e) {
                    $("#message").html('error');
                    console.log('Error: ' + JSON.stringify(e));
                }
            });

            return false;
        });
    });
</script>