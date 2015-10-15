<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script>

/**
 * Created by Polomani on 12.10.2015.
 */

$(document).ready(function(){

    $("body").on("click", ".addFavorite", function(){
        var elem = $(this);
        elem.prop("disabled", true);
        $.ajax({
            url: "/book/add-" + elem.attr("id"),
            type: 'GET',
            success: function(data) {
                elem.prop("disabled", false);
                if (data==0) {
                    showSignInPopup();
                } else if (data==1) {
                    elem.text('<spring:message code="book.favorites.deletefromfavorites" />');
                    elem.prop("class", "deleteFavorite");
                }
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
        return false;
    });

    $("body").on("click", ".deleteFavorite", function(){
        var elem = $(this);
        elem.prop("disabled", true);
        $.ajax({
            url: "/book/delete-" + elem.attr("id"),
            type: 'GET',
            success: function(data) {
                elem.prop("disabled", false);
                if (data==0) {
                    showSignInPopup();
                } else if (data==1) {
                    elem.text('<spring:message code="book.favorites.addtofavorites" />');
                    elem.prop("class", "addFavorite");
                }
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
        return false;
    });

    function showSignInPopup() {
        $('#sign-in-form').show();
        $('.shadow').show();
    }

});

</script>














