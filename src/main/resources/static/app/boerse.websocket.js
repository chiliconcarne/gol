'use strict';

function boerseWebsocket(listChanged, messageChanged)
{
    var websocket = {};

    var stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, onstart);

    function onstart()
    {
        console.log('Connected');

        stompClient.subscribe('/user/topic/lobby/list', listArrived);
        stompClient.subscribe('/topic/lobby/list', listArrived);
        stompClient.subscribe('/user/topic/lobby/message', messageChanged);
        stompClient.subscribe('/user/topic/lobby/game', gameArrived);
        stompClient.send('/app/lobby/start');

        function listArrived(listJSON)
        {
            var list = JSON.parse(listJSON.body);
            listChanged(list);
        }

        function gameArrived(gameJson){
            location.href="/game";
        }
    }

    websocket.add = function() {
        stompClient.send('/app/lobby/add');
    }

    websocket.remove = function() {
        stompClient.send('/app/lobby/delete');
    }

    websocket.accept = function(username){
        stompClient.send('/app/lobby/accept',{},username);
    }

    websocket.leave = function(){
        stompClient.send('/app/lobby/leave');
        stompClient.disconnect();
    }

    return websocket;
}