package GameOfLife.MVC.controller.Service;

import GameOfLife.MVC.controller.Controller.WebsocketController;
import GameOfLife.MVC.controller.Json.Position;
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
        send("onStart");
    }

    @Override
    public void onLeave(WebsocketEvent event) {
        send("onLeave");
    }

    @Override
    public void onReady(WebsocketEvent event) {
        send("onReady");
    }

    @Override
    public void onAddGameOffer(WebsocketEvent event) {
        send("onAddGameOffer");
    }

    @Override
    public void onDeleteGameOffer(WebsocketEvent event) {
        send("onDeleteGameOffer");
    }

    @Override
    public void onGoToTheProfile(WebsocketEvent event) {
        send("onGoToTheProfile");
    }

    @Override
    public void onReadyToPlay(WebsocketEvent event) {
        send("onReadyToPlay "+(String)event.getDaten());
    }

    @Override
    public void onConnect(WebsocketEvent event) {
        send("onConnect");
    }

    @Override
    public void onEnd(WebsocketEvent event) {
        send("onEnd");
    }

    @Override
    public void onClickCells(WebsocketEvent event) {
        Position pos = (Position) event.getDaten();
        send("onClickCells "+pos.getX()+" "+pos.getY());
    }

    @Override
    public void onSelectTeam(WebsocketEvent event) {
        send("onSelectTeam");
    }

    private void send(String name){
        this.messagingTemplate.convertAndSend("/topic/msg","Korrekt "+name);
    }
}
