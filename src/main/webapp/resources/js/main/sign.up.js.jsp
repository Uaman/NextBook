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
                    return element.value.match(/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i);
                },
                '<spring:message code="sign.up.error.wrong.email.format" />'
        )
        $('#register-form').validate({
            rules:{
                name: {
                    required: true,
                    minlength: ${minNameLength},
                    maxlength: ${maxNameLength}
                },
                email: {
                    required: true,
                    emailRegex: true,
                    maxlength: ${maxEmailLength}
                },
                password: {
                    required: true,
                    minlength: ${minPasswordLength},
                    maxlength: ${maxPasswordLength}
                },
                confirm_pass: {
                    required: true,
                    passwordEquals: true
                }
            },
            messages: {
                name: {
                    required: '<spring:message code="sign.up.error.blank.name" />',
                    minlength: '<spring:message code="sign.up.error.length.name" />',
                    maxlength: '<spring:message code="sign.up.error.length.name" />'
                },
                email: {
                    required: '<spring:message code="sign.up.error.blank.email" />',
                    maxlength: '<spring:message code="sign.up.error.length.email" />'
                },
                password: {
                    required: '<spring:message code="sign.up.error.blank.password" />',
                    minlength: '<spring:message code="sign.up.error.length.password" />',
                    maxlength: '<spring:message code="sign.up.error.length.password" />'
                },
                confirm_pass: {
                    required: '<spring:message code="sign.up.error.blank.password.confirm" />'
                }
            },
            errorLabelContainer: $("div.errorblock ul"),
            wrapper: 'li',
            submitHandler: function(form) {
                var spinner = new Spinner(opts).spin(document.getElementById('spin'));
                $('#send-popup').show();
                $('.shadow').show();
                $('.errorblock ul').empty();
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
                        spinner.stop();
                        $('.close').trigger('click');
                        if(response.code == 1){
                            $('#registration-ok').show();
                            $('.shadow').show();
                        } else if(response.code == 0){
                            $('#problems-with-service').show();
                            $('.shadow').show();
                        } else {
                            $('.errorblock ul').show();
                            console.log(response.errors);
                            for(var i = 0; i < response.errors.length; ++i){
                                $('.errorblock ul').append('<li>'+response.errors[i]+'</li>');
                            }
                        }
                    },
                    error: function(e){
                        spinner.stop();
                        $('.close').trigger('click');
                        $('#problems-with-service').show();
                        $('.shadow').show();
                        console.log(e);
                    }
                });
            }
        })
    });

    function formData(){
        var data = {
            name: $('#name').val(),
            email: $('#email').val(),
            password: $('#password').val()
        };
        return data;
    }


    var opts = {
        lines: 12             // The number of lines to draw
        , length: 7             // The length of each line
        , width: 5              // The line thickness
        , radius: 10            // The radius of the inner circle
        , scale: 1.0            // Scales overall size of the spinner
        , corners: 1            // Roundness (0..1)
        , color: '#000'         // #rgb or #rrggbb
        , opacity: 1/4          // Opacity of the lines
        , rotate: 0             // Rotation offset
        , direction: 1          // 1: clockwise, -1: counterclockwise
        , speed: 1              // Rounds per second
        , trail: 100            // Afterglow percentage
        , fps: 20               // Frames per second when using setTimeout()
        , zIndex: 2e9           // Use a high z-index by default
        , className: 'spinner'  // CSS class to assign to the element
        , top: '50%'            // center vertically
        , left: '50%'           // center horizontally
        , shadow: false         // Whether to render a shadow
        , hwaccel: false        // Whether to use hardware acceleration (might be buggy)
        , position: 'absolute'  // Element positioning
    };
</script>