'use strict';

function boerseWebsocket(listChanged, messageChanged)
{
    var websocket = {};

    var stompClient = Stomp.over(new SockJS('/websocket'));
    stompClient.connect({}, onstart);

    function onstart()
    {
        console.log('Connected');

        stompClient.subscribe('/out/boerse/list', listArrived);
        stompClient.subscribe('/out/boerse/message', messageArrived);
        stompCLient.send('/in/boerse/start');

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

    function add(){
        stompClient.send('/in/boerse/add');
    }

    function remove(){
        stompClient.send('/in/boerse/remove');
    }

    function accept(username){
        stompClient.send('/in/boerse/accept',{},username);
    }

    return websocket;
}