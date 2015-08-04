/**
 * Created by Polomani on 30.07.2015.
 */

$(document).ready(function(){

    $("body").on("click", ".deleteButton", function(){
        var elem = $(this);
        $.ajax({
            url: "/admin/publishers/delete/" + elem.attr("value"),
            type: 'GET',
            success: function(data) {
                $(elem).closest('tr').remove();
                alert("Publisher deleted");
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
        return false;
    });

    $('#filterForm').submit(function(){
        var elem = this;
        var data = {
            id:$(this.id).val(),
            from:$(this.from).val(),
            max:$(this.max).val(),
            name:$(this.name).val(),
            description:$(this.description).val()
        };
        $.ajax({
            url: '/admin/publishers/filter',
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data) {
                var first = '<tr><th width="50px">id</th><th>nameUa</th><th>nameRu</th><th>nameEn</th><th>description</th><th>users</th><th width="150px">action</th></tr>';

                $("#added").empty();

                var html = '';
                for(var i = 0; i < data.length; ++i){
                    html += formPublisher(data[i]);
                }

                $('#added').append(first);
                $('#added').append(html);
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
        return false;
    });
});

function formPublisher(publisher){
    var html = '<tr><td>'+publisher.id+'</td>';
    html += '<td>'+publisher.nameUa+'</td>';
    html += '<td>'+publisher.nameRu+'</td>';
    html += '<td>'+publisher.nameEn+'</td>';
    html += '<td>'+publisher.description+'</td>';
    html += '<td>'
    for (var i = 0; i < publisher.users.length; ++i) {
        html += publisher.users[i].email + '<br />';
    }
    html +='</td>';
    html += '<td><a href="/admin/publishers/edit-publisher/'+publisher.id+'">Edit</a>';
    html += '<br/><a href="/admin/publishers/manage-users/?publisher='+publisher.id+'">Manage users</a>';
    html += '<button value="'+publisher.id+'" class="deleteButton">Delete</button></td></tr>';
    return html;
}











