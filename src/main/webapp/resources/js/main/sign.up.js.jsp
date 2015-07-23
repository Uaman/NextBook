<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script>
    $(document).ready(function(){
        $.validator.addMethod(
                "passwordEquals",
                function(value, element){
                    if($('#password').val() !== element.value)
                        return false;
                    return true;
                },
                '<spring:message code="sign.up.error.not.equals.passwords" />'
        );
        $.validator.addMethod(
                "emailRegex",
                function(value, element){
                    return element.value.match(/^\w+@[a-zA-Z_]+?\.[a-zA-Z]{1,4}$/);
                },
                '<spring:message code="sign.up.error.wrong.email.format" />'
        )
        $('#register-form').validate({
            rules:{
                name: {
                    required: true,
                    minlength: 3,
                    maxlength: 30
                },
                email: {
                    required: true,
                    emailRegex: true
                },
                password: {
                    required: true,
                    minlength: 6,
                    maxlength: 30
                },
                confirm_pass: {
                    required: true,
                    passwordEquals: true
                }
            },
            messages: {
                name: {
                    required: '<spring:message code="sign.up.error.blank.name" />',
                    minlength: '<spring:message code="sign.up.error.min.length.name" />',
                    maxlength: '<spring:message code="sign.up.error.max.length.name" />'
                },
                email: {
                    required: '<spring:message code="sign.up.error.blank.email" />'
                },
                password: {
                    required: '<spring:message code="sign.up.error.blank.password" />',
                    minlength: '<spring:message code="sign.up.error.min.length.password" />',
                    maxlength: '<spring:message code="sign.up.error.max.length.password" />'
                },
                confirm_pass: {
                    required: '<spring:message code="sign.up.error.blank.password.confirm" />'
                }
            },
            errorLabelContainer: $("div.errorblock"),
            wrapper: 'li',
            submitHandler: function(form) {
                var data = formData();
                console.log(data);
                $.ajax({
                    url: '/register',
                    type: 'POST',
                    data: JSON.stringify(data),
                    dataType: 'json',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader("Accept", "application/json");
                        xhr.setRequestHeader("Content-Type", "application/json");
                    },
                    success: function(response) {
                        if(response){
                            window.location.href = '/signin';
                        } else {
                            alert('we have some problems with your registration');
                        }
                    },
                    error: function(e){
                        alert(e);
                    }
                });
            }
        })
    });

    function formData(){
        var data = {
            name: $('#name').val(),
            email: $('#email').val(),
            roleId: $('#roleId').val(),
            password: $('#password').val()
        };
        return data;
    }
</script>