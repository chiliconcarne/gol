'use strict';

function gameLobbyWebsocket(userChanged, msgChanged)
{
    var websocket = {};

    var stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, onstart);

    var room = getQueryVariable("room");

    function onstart()
    {
        console.log('Connected');

        stompClient.subscribe('/topic/gameLobby/'+room+'/update', update);
        stompClient.subscribe('/topic/gameLobby/'+room+'/msg', msgArrived);
        stompClient.send("/app/gameLobby/"+room+"/connect");

        function update(userJson){
            userChanged(JSON.parse(userJson.body));
        }

        function msgArrived(msgJson){
            msgChanged(msgJson);
        }

    }

    websocket.joinTeam = function(side){
        stompClient.send("/app/gameLobby/"+room+"/selectTeam",{},side);
    }

    return websocket;
}