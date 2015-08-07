/**
 * Created by Stacy on 8/5/15.
 */
$(document).ready(function() {
    $("#send").click(function () {
        var id = ($('#id')==null)?-1:$('#id').val();
        var data = {
            id: id,
            firstNameUa:$('#firstNameUa').val(),
            lastNameUa: $('#lastNameUa').val(),
            firstNameEn:$('#firstNameEn').val(),
            lastNameEn: $('#lastNameEn').val(),
            firstNameRu:$('#firstNameRu').val(),
            lastNameRu: $('#lastNameRu').val()
        };
        $.ajax({
            url: '/admin/authors/update-author',
            data: JSON.stringify(data),
            type: 'POST',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data) {
                if (EDIT) {
                    alert("Author was updated");
                } else {
                    alert("Author was added");
                    $('#firstNameUa').val('');
                    $('#lastNameUa').val('');
                    $('#firstNameEn').val('');
                    $('#lastNameEn').val('');
                    $('#firstNameRu').val('');
                    $('#lastNameRu').val('');
                }
            },
            error: function (e) {
                console.log('Error: ' + JSON.stringify(e));
            }
        });

        return false;
    });
});
