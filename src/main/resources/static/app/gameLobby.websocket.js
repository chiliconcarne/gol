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

        stompClient.subscribe('/topic/gameLobby/'+room+'/user', userArrived);
        stompClient.subscribe('/topic/gameLobby/'+room+'/msg', msgArrived);
        stompClient.send("/app/gameLobby/"+room+"/connect");

        function userArrived(userJson){
            userChanged(userJson);
        }

        function msgArrived(msgJson){
            msgChanged(msgJson);
        }
    }

    return websocket;
}