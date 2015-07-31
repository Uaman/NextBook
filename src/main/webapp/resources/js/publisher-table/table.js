/**
 * Created by Polomani on 30.07.2015.
 */

$(document).ready(function(){
    $("body").on ("click", '.updateButton', function(){
        var elem = $(this).closest('tr');
        var data = {
            id:elem.find("[name='id']").val(),
            nameUa:elem.find("[name='nameUa']").val(),
            nameRu:elem.find("[name='nameRu']").val(),
            nameEn:elem.find("[name='nameEn']").val(),
            description:elem.find("[name='description']").val()
        };
        $.ajax({
            url: '/admin/publishers/update',
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data) {
                alert("Publisher updated");
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });

        return false;
    });

    $("body").on("click", ".deleteButton", function(){
        var elem = $(this).closest('tr');
        $.ajax({
            url: "/admin/publishers/delete/" + elem.find("[name='id']").val(),
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

    $("body").on ("click", '.addButton', function(e){
        var elem = $(this).closest('tr');
        var data = {
            nameUa:elem.find("[name='nameUa']").val(),
            nameRu:elem.find("[name='nameRu']").val(),
            nameEn:elem.find("[name='nameEn']").val(),
            description:elem.find("[name='description']").val()
        };
        $.ajax({
            url: '/admin/publishers/update',
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(data) {
                $(elem).trigger('reset');
                $('#added tr:nth-last-child(2)').after(formPublisher(data));
                alert("Publisher added");
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
            nameUa:$(this.nameUa).val(),
            nameRu:$(this.nameRu).val(),
            nameEn:$(this.nameEn).val(),
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
                var first = '<tr><th width="50px">id</th><th>nameUa</th><th>nameRu</th><th>nameEn</th><th>description</th><th width="150px">action</th></tr>';
                var last = '<tr><td>#</td><td><input  class="sinput" name="nameUa"/></td><td><input name="nameRu"  class="sinput"/></td><td>' +
                            '<input name="nameEn"  class="sinput"/></td><td><input  class="sinput" name="description"/></td><td><input type="submit" value="Add" class="addButton"/></td></tr>';

                $("#added").empty();

                var html = '';
                for(var i = 0; i < data.length; ++i){
                    html += formPublisher(data[i]);
                }

                $('#added').append(first);
                $('#added').append(html);
                $('#added').append(last);
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
        return false;
    });
});

function formPublisher(publisher){
    var html = '<tr><td> <input type="hidden" value="'+publisher.id+'" name="id"/>'+publisher.id+'</td>';
    html += '<td><input value="'+publisher.nameUa+'" name="nameUa" class="sinput"/></td>';
    html += '<td><input value="'+publisher.nameRu+'" name="nameRu" class="sinput"/></td>';
    html += '<td><input value="'+publisher.nameEn+'" name="nameEn" class="sinput"/></td>';
    html += '<td><input value="'+publisher.description+'" name="description" class="sinput"/></td>';
    html += '<td><input type="submit" value="Update" class="updateButton"/>';
    html += '<input type="submit" value="Delete" class="deleteButton"/></td></tr>';
    return html;
}









