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
    $(".colorContainer.first .color").on('click',function(){
        $("#color1").val($(this).data("color"));
        if($(this).data("color")==1)
            $("#color2").val(2);
        else
            $("#color2").val(1);
        refresh();
    });
    $(".colorContainer.second .color").on('click',function(){
        if(!$(this).hasClass("inactive"))
            $("#color2").val($(this).data("color"));
        refresh();
    });
})

function refresh(){
    $(".color").removeClass("active").removeClass("inactive");
    $(".first .c"+$("#color1").val()).addClass("active");
    $(".second .c"+$("#color1").val()).addClass("inactive");
    $(".second .c"+$("#color2").val()).addClass("active");
}