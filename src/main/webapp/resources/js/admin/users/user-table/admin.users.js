$(document).ready(function(){
    $('#send-filter').click(function(){
        var data = {
            name: $('#name').val(),
            email: $('#email').val(),
            roleId: $('#role-filter').val(),
            from: $('#from').val(),
            max: $('#max').val()
        };

        if($('#all').is(':checked')){
            data.state = 'all';
        } else {
            data.state = $('#active').is(':checked') ? 'active' : 'not-active';
        }

        $.ajax({
            url: '/admin/users/users-filter',
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data) {
                $('#added').empty();
                var html = '';
                for(var i = 0; i < data.length; ++i){
                    html += formUser(data[i]);
                }
                $('#added').append(html);
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
    });

    $('body').on('click', '.deactivate', function(){
        var self = this;
        var id = $(self).attr('value');
        $.ajax({
            url: '/admin/users/change-active-user-status/'+id+'/false',
            type: 'POST',
            success: function(data){
                $(self).removeClass('deactivate').addClass('activate').text('Activate');
            }
        })
    });

    $('body').on('click', '.activate', function(){
        var self = this;
        var id = $(self).attr('value');
        $.ajax({
            url: '/admin/users/change-active-user-status/'+id+'/true',
            type: 'POST',
            success: function(data){
                $(self).removeClass('activate').addClass('deactivate').text('Deactivate');
            }
        })
    });

    $('body').on('click', '.delete', function(){
        var id = $(this).attr('value');
        $.ajax({
            url: '/admin/users/delete-user/'+id,
            type: 'POST',
            success: function(data){
                if(data){
                    $('#row-'+id).remove();
                }
            }
        });
    });

});

function formUser(item){
    var html = '<tr>';
    html += '<td width="10%">'+item.id+'</td>';
    html += '<td width="20%">'+item.name+'</td>';
    html += '<td width="20%">'+item.email+'</td>';
    html += '<td width="20%">'+item.password+'</td>';
    html += '<td width="10%">';
    if(item.active){
        html += '<button class="deactivate" value="'+item.id+'">Deactivate</button>';
    } else {
        html += '<button class="activate" value="'+item.id+'">Activate</button>';
    }
    html +=    '</td>';
    html += '<td width="10%">'+item.role.name+'</td>';
    html += '<td width="10%"><button class="delete" value="'+item.id+'">Delete</button>';
    html += '<form action="/admin/users/edit-user"><input type="hidden" name="userId" value="'+item.id+'">';
    html += '<input type="submit" value="Edit"></form></td></tr>';
    return html;
}