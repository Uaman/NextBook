/**
 * Created by Polomani on 01.08.2015.
 */

$(document).ready(function() {
    $("#send").click(function () {
        var id = ($("#id")==null)?-1:$("#id").val();
        var data = {
            id: id,
            nameUa: $("#nameUa").val(),
            nameRu: $("#nameRu").val(),
            nameEn: $("#nameEn").val(),
            description: $("#description").val()
        };
        $.ajax({
            url: '/admin/publishers/update',
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data) {
                if (EDIT) {
                    alert("Publisher updated");
                } else {
                    alert("Publisher added");
                    $("#nameUa").val("");
                    $("#nameRu").val("");
                    $("#nameEn").val("");
                    $("#description").val("");
                }
            },
            error: function (e) {
                console.log('Error: ' + JSON.stringify(e));
            }
        });

        return false;
    });
});