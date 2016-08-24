package GameOfLife.MVC.controller.Service;

import GameOfLife.MVC.controller.Controller.WebsocketController;
import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;
import GameOfLife.MVC.controller.Listener.GameLobbyWebsocketListener;
import GameOfLife.MVC.controller.Listener.GameWebsocketListener;
import GameOfLife.MVC.controller.Listener.LobbyWebsocketListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by kulandas on 24.08.2016.
 */
@Service
public class TestWebsocket implements GameLobbyWebsocketListener, GameWebsocketListener, LobbyWebsocketListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TestWebsocket(WebsocketController websocketController){
        websocketController.addLobbyWebsocketListener(this);
        websocketController.addGameLobbyWebsocketListener(this);
        websocketController.addGameWebsocketListener(this);
    }

    @Override
    public void onStart(WebsocketEvent event) {
        send();
    }

    @Override
    public void onLeave(WebsocketEvent event) {
        send();
    }

    @Override
    public void onReady(WebsocketEvent event) {
        send();
    }

    @Override
    public void onAddGameOffer(WebsocketEvent event) {
        send();
    }

    @Override
    public void onDeleteGameOffer(WebsocketEvent event) {
        send();
    }

    @Override
    public void onGoToTheProfile(WebsocketEvent event) {
        send();
    }

    @Override
    public void onReadyToPlay(WebsocketEvent event) {
        send();
    }

    @Override
    public void onConnect(WebsocketEvent event) {
        send();
    }

    @Override
    public void onEnd(WebsocketEvent event) {
        send();
    }

    @Override
    public void onClickCells(WebsocketEvent event) {
        send();
    }

    @Override
    public void onSelectTeam(WebsocketEvent event) {
        send();
    }

    private void send(){
        this.messagingTemplate.convertAndSend("/topic/msg","Korrekt");
    }
}
