/**
 * Created by Polomani on 12.10.2015.
 */

$(document).ready(function(){

    updateFavoriteButtons();

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
                    elem.text("Delete from favorites");
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
                    elem.text("Add to favorites");
                    elem.prop("class", "addFavorite");
                }
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
        return false;
    });

});

function updateFavoriteButtons () {
    $('.favoriteButtonUndef').each(function() {
        var elem = $(this);
        elem.hide();
        elem.prop("class", "");

        $.ajax({
            url: "/book/is-" + elem.attr("id"),
            type: 'GET',
            success: function(data) {
                if (data==0) {
                    elem.text("Add to favorites");
                    elem.show();
                    elem.prop("class", "addFavorite");
                } else if (data==1) {
                    elem.text("Delete from favorites");
                    elem.show();
                    elem.prop("class", "deleteFavorite");
                }
            },
            error: function(e){
                console.log('Error: ' + JSON.stringify(e));
            }
        });
    });
}

function showSignInPopup() {
    $('#sign-in-form').show();
    $('.shadow').show();
}














