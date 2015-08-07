/**
 * Created by Stacy on 7/29/15.
 */
$(document).ready(function(){
    $("body").on("click", ".deleteButton", function(){
        var elem = $(this);
        $.ajax({
            url: "/admin/authors/delete-author/" + elem.attr("value"),
            type: 'GET',
            success: function(data) {
                $(elem).closest('tr').remove();
                alert("Author deleted");
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
        return false;
    });

    $('#requestAuthor').submit(function(){
        var elem = this;
        var data = {
            firstName:$('#fU').val(),
            lastName:$('#lU').val()
        };
        $.ajax({
            url: '/admin/authors/authors-filter',
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data) {
                $("#added").empty();
                var html = '';
                for(var i = 0; i < data.length; ++i){
                    html += formAuthor(data[i]);
                }
                $('#added').append(html);
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
        return false;
    });
});
function formAuthor(data){
    var html = '<tr><td width="5%">'+data.id+'</td>';
    html+='<td width="10%">'+data.firstNameUa +'</td>';
    html+='<td width="10%">' +data.lastNameUa+' </td>';
    html+='<td width="10%"> '+data.firstNameEn+' </td>';
    html+='<td width="10%"> '+data.lastNameEn +'</td>';
    html+='<td width="10%"> '+data.firstNameRu+ '</td>';
    html+='<td width="10%"> '+data.lastNameRu +'</td>';
    html+='<th width="10%"> <form action="/admin/authors/edit-author/'+data.id+'"><input type="submit" value="Edit"></form>';
    html+='<button value="'+data.id+'" class="deleteButton">Delete</button> </th> </tr>';
    return html;
}
