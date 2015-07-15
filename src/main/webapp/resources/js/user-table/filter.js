$(document).ready(function(){
    $('#send-filter').click(function(){
        var data = {
            name: $('#name').val(),
            email: $('#email').val(),
            roleId: $('#roleId').val(),
            from: $('#from').val(),
            max: $('#max').val()
        };

        if($('#all').is(':checked')){
            data.state = 'all';
        } else {
            data.state = $('#active').is(':checked') ? 'active' : 'not-active';
        }

        $.ajax({
            url: '/filters',
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data) {
                if(data.length > 0){
                    $('#added').empty();
                    var html = '';
                    for(var i = 0; i < data.length; ++i){
                        html += formUser(data[i]);
                    }
                    $('#added').append(html);
                }
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
    });
});

function formUser(item){
    var html = '<tr><form action="/user/'+item.id+'/update" method="POST">';
    html += '<input type="hidden" value="'+item.id+'" name="id"/>';
    html += '<td width="10%">'+item.id+'</td>';
    html += '<td width="20%"><input type="text" value="'+item.name+'" name="name"/></td>';
    html += '<td width="20%"><input type="text" value="'+item.email+'" name="email"/></td>';
    html += '<td width="20%"><input type="text" value="'+item.password+'" name="password"/></td>';
    html += '<td width="10%"><input type="text" value="'+item.active+'" name="active"/></td>';
    html += '<td width="10%"><input type="text" value="'+item.roleId+'" name="roleId"/></td>';
    html += '<th width="10%"><input type="submit" value="Update" /></form>';
    html += '<form action="/user/'+item.id+'/delete" method="GET"><input type="submit" value="Delete"/></form></th></tr>';
    return html;
}