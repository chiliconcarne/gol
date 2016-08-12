'use strict';

function boerseWebsocket(listChanged, messageChanged)
{
    var websocket = {};

    var stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, onstart);

    function onstart()
    {
        console.log('Connected');

        stompClient.subscribe('/user/out/boerse/list', listArrived);
        stompClient.subscribe('/out/boerse/list', listArrived);
        stompClient.subscribe('/user/out/boerse/message', messageArrived);
        stompClient.subscribe('/user/out/boerse/game', gameArrived);
        stompClient.send('/in/boerse/start');

        function listArrived(listJSON)
        {
            var list = JSON.parse(listJSON.body);
            console.log(list);
            listChanged(list);
        }

        function messageArrived(messageJson)
        {
            var message = JSON.parse(messageJson.body);
            console.log(message);
            messageChanged(message.msg);
        }

        function gameArrived(gameJson){
            location.href="/game";
        }
    }

    websocket.add = function() {
        stompClient.send('/in/boerse/add');
    }

    websocket.remove = function() {
        stompClient.send('/in/boerse/remove');
    }

    websocket.accept = function(username){
        stompClient.send('/in/boerse/accept',{},username);
    }

    return websocket;
}