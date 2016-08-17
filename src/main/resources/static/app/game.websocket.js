'use strict';

function gameWebsocket(stateChanged, messageChanged)
{
    var websocket = {};

    var stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, onstart);

    function onstart()
    {
        console.log('Connected');

        stompClient.subscribe('/user/out/game/state', stateArrived);
        stompClient.subscribe('/user/out/game/message', messageChanged);
        stompClient.send("/in/game/start");

        function stateArrived(stateJson)
        {
            var state = JSON.parse(stateJson.body);
            stateChanged(state);
        }
    }

    websocket.userClicked = function(x, y)
    {
        stompClient.send("/in/game/set", {}, JSON.stringify({ 'x': x, 'y': y }));
    };

    websocket.ready = function()
    {
        stompClient.send("/in/game/ready");
    };

    websocket.end = function()
    {
        stompClient.send("/in/game/end");
        location.href="/lobby";
    };

    return websocket;
}