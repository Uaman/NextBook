$(document).ready(function(){
    $('#sign-in').click(function(){
        $('#sign-in-form').show();
        $('.shadow').show();
    });
    $('#close').click(function(){
        $('#sign-in-form').hide();
        $('.shadow').hide();
    });
});