$(document).ready(function(){
    $('#edit').click(function(){
        var data = {
            id: $('#userId').val(),
            email: $('#email').val(),
            name: $('#name').val(),
            roleId: $('#role').val()
        };

        $.ajax({
            url: '/admin/users/update-user',
            type: 'POST',
            data: JSON.stringify(data),
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data){
                if(data){
                    $('#result').text('Saved');
                } else {
                    $('#result').text('Fail');
                }
            }
        });
    });
});