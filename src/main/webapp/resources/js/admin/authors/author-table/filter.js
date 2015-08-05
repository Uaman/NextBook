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
    $("body").on("click", ".editButton", function(){
        var elem = $(this);
        var author = {
            firstNameUa:$('#firstNameUa'+elem.attr("value")).val(),
            lastNameUa: $('#lastNameUa'+elem.attr("value")).val(),
            firstNameEn:$('#firstNameEn'+elem.attr("value")).val(),
            lastNameEn: $('#lastNameEn'+elem.attr("value")).val(),
            firstNameRu:$('#firstNameRu'+elem.attr("value")).val(),
            lastNameRu: $('#lastNameRu'+elem.attr("value")).val()
        };
        if(elem.attr("value")!='Add'){
            $('input[name="id"]').val(elem.attr("value"));
            $('input[name="firstNameUa"]').val(author.firstNameUa);
            $('input[name="lastNameUa"]').val(author.lastNameUa);
            $('input[name="firstNameEn"]').val(author.firstNameEn);
            $('input[name="lastNameEn"]').val(author.lastNameEn);
            $('input[name="firstNameRu"]').val(author.firstNameRu);
            $('input[name="lastNameRu"]').val(author.lastNameRu);
        }
        else{ $('input[name="id"]').val('0');
            $('input[name="firstNameUa"]').val('');
            $('input[name="lastNameUa"]').val('');
            $('input[name="firstNameEn"]').val('');
            $('input[name="lastNameEn"]').val('');
            $('input[name="firstNameRu"]').val('');
            $('input[name="lastNameRu"]').val('');
    }
        $('#edit-form').show();
        $('.shadow').show();
    });
    $('#editButton').click(function(){
        $('#edit-form').show();
        $('.shadow').show();
    });
    $('#closeButton').click(function(){
        $('#edit-form').hide();
        $('.shadow').hide();
    });
    $('#requestAuthor').submit(function(){
        var elem = this;
        var data = {
            id:'',
            firstName:$('#fU').val(),
            lastName:$('#lU').val(),
            firstNameUa:'',
            lastNameUa:'',
            firstNameEn: '',
            lastNameEn: '',
            firstNameRu:'',
            lastNameRu: ''
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
                var header = '<tr> <th width="5%">id</th> <th width="15%">firstNameUa</th> <th width="15%">lastNameUa</th> <th width="10%">firstNameEn</th> <th width="10%">lastNameEn</th> <th width="10%">firstNameRu</th> <th width="10%">lastNameRu</th> <th width="20%">Action</th> </tr>';

                $("#added").empty();

                var html = '';
                for(var i = 0; i < data.length; ++i){
                    html += formAuthor(data[i]);
                }

                $('#added').append(header);
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
    var html = '<tr><td width="5%">'+data.id+'</td> <td width="10%">';
    html+='data.firstNameUa </td>';
    html+='<td width="10%">' +data.lastNameUa+' </td>';
    html+='<td width="10%"> '+data.firstNameEn+' </td>';
    html+='<td width="10%"> '+data.lastNameEn +'</td>';
    html+='<td width="10%"> '+data.firstNameRu+ '</td>';
    html+='<td width="10%"> '+data.lastNameRu +'</td>';
    html+='<th width="10%"> <button value="'+data.id+'" class="editButton">Update</button>';
    html+='<button value="'+data.id+'" class="deleteButton">Delete</button> </th> </tr>';
    return html;
}
