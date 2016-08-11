'use strict';

function boerseWebsocket(listChanged, messageChanged)
{
    var websocket = {};

    var stompClient = Stomp.over(new SockJS('/boerse'));
    stompClient.connect({}, onstart);

    function onstart()
    {
        console.log('Connected');

        stompClient.subscribe('/boerse/list', listArrived);
        stompClient.subscribe('/boerse/message', messageArrived);
        stompCLient.send('/game/update');

        function listArrived(listJSON)
        {
            var list = JSON.parse(listJSON.body);
            console.log(list);
            listChanged(state.g);
        }

        function messageArrived(messageJson)
        {
            var message = JSON.parse(messageJson.body);
            console.log(message);
            messageChanged(message.msg);
        }
    }

    return websocket;
}