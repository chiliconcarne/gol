'use strict';

function gameWebsocket(boardchanged)
{
    var socket = new SockJS('/hello');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame)
    {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/game/board', function(boardJson)
        {
            var board = JSON.parse(board.body);
            console.log(board);
            boardchanged(board);
        });
    });

    this.userClicked = function(x, y)
    {
        console.log('userClicked: x = ' + x + " y = " + y);
        stompClient.send("/app/set", {}, JSON.stringify({ 'x': x, 'y': y }));
    }
}