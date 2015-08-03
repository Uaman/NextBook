$(document).ready(function(){
    $('#send-form-password').click(function(){
        if($('#newPassword').val() !== $('#confirmNewPassword').val()){
            alert('unequals new password and confirm password');
            return;
        }
        $.ajax({
            url: '/cabinet/change-password',
            type: 'POST',
            data: JSON.stringify({
                oldPassword: $('#oldPassword').val(),
                newPassword: $('#newPassword').val()
            }),
            dataType: 'json',
            beforeSend: beforeSend,
            success: function(response) {
                $('#result-update-password').empty();
                for(var i = 0; i < response.length; ++i){
                    $('#result-update-password').append(response[i] + '<br />');
                }
            },
            error: function(e){
                alert('error');
            }
        });
    });
    $('#update-submit').click(function(){
        $.ajax({
            url: '/cabinet/update',
            type: 'POST',
            data: JSON.stringify({
                email: $('#email').val(),
                name: $('#name').val()
            }),
            dataType: 'json',
            beforeSend: beforeSend,
            success: function(response) {
                $('#result-update-name-email').text(response ? 'Saved' : 'Fail');
            }
        });
    });
});

function beforeSend(xhr) {
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
}