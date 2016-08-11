'use strict';

function gameWebsocket(stateChanged, messageChanged)
{
    var websocket = {};

    var stompClient = Stomp.over(new SockJS('/game'));
    stompClient.connect({}, onstart);

    function onstart()
    {
        console.log('Connected');

        stompClient.subscribe('/game/state', stateArrived);
        stompClient.subscribe('/game/message', messageArrived);
        stompClient.send("/app/start");

        function stateArrived(stateJson)
        {
            var state = JSON.parse(stateJson.body);
            console.log(state);
            stateChanged(state.g);
        }

        function messageArrived(messageJson)
        {
            var message = JSON.parse(messageJson.body);
            console.log(message);
            messageChanged(message.msg);
        }
    }

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