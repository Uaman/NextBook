/**
 * Created by Stacy on 7/29/15.
 */
$(document).ready(function(){
    $('#send-filter').click(function() {
        var data = {
            firstNameUa: $('#firstNameUa').val(),
            lastNameUa: $('#lastNameUa').val(),
            firstNameEn: $('#firstNameEn').val(),
            lastNameEn: $('#lastNameEn').val(),
            firstNameRu: $('#firstNameRu').val(),
            lastNameRu: $('#lastNameRu').val(),
            from: $('#from').val(),
            max: $('#max').val()
        };
    });
});