'use strict';

function gameWebsocket(statechanged)
{
    var websocket = {};

    var socket = new SockJS('/game');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame)
    {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/game/board', function(stateJson)
        {
            var state = JSON.parse(stateJson.body);
            console.log(state);
            statechanged(state);
        });
        stompClient.send("/app/start", {});
    });

    websocket.userClicked = function(x, y)
    {
        console.log('userClicked: x = ' + x + " y = " + y);
        stompClient.send("/app/set", {}, JSON.stringify({ 'x': x, 'y': y }));
    }

    return websocket;
}