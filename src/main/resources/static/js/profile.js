'use strict';
$(document).ready(function(){
    var wait = "Spielsuche abbrechen";
    var start = "Freies Spiel suchen";
    refresh();
    $("#start").text(start);
    $('#start').on('click',function(){
        $(".options").toggle();
        $(".warten").toggle();
        if($("#start").text()==wait)
            $("#start").text(start);
        else
            $("#start").text(wait);
    });
})

function refresh(){
    $(".first .c"+$("#color1").val()).addClass("active");
    $(".second .c"+$("#color2").val()).addClass("active");
}