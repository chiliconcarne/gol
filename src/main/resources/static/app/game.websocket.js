'use strict';

function gameWebsocket(stateChanged, messageChanged)
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
            stateChanged(state.g);
        });
        stompClient.subscribe('/game/message', function(messageJson)
        {
            var message = JSON.parse(messageJson.body);
            console.log(message);
            messageChanged(message.msg);
        });
        stompClient.send("/app/start");
    });

    websocket.userClicked = function(x, y)
    {
        console.log('userClicked: x = ' + x + " y = " + y);
        stompClient.send("/app/set", {}, JSON.stringify({ 'x': x, 'y': y }));
    };

    websocket.ready = function()
    {
        console.log('ready');
        stompClient.send("/app/ready");
    };

    return websocket;
}