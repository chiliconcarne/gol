'use strict';
$(document).ready(function(){
    var wait = "Spielsuche abbrechen";
    var start = "Freies Spiel suchen";
    $("#start").text(start);
    $('#start').on('click',function(){
        $(".options").toggle();
        $(".warten").toggle();
        if($("#start").text()==wait)
            $("#start").text(start);
        else
            $("#start").text(wait);
    });
    $(".color").on('mouseover',function(){
        $(this).css('width','70px');
        $(this).css('height','70px');
        $(this).css('marginTop','10px');
    });
    $(".color").on('mouseout',function(){
        $(this).css('width','50px');
        $(this).css('height','50px');
        $(this).css('marginTop','30px');
    });
})