package GameOfLife.MVC.controller.Controller;

import GameOfLife.MVC.controller.Configuration.UserManager;
import GameOfLife.MVC.controller.Json.Position;
import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;
import GameOfLife.MVC.controller.Listener.GameLobbyWebsocketListener;
import GameOfLife.MVC.controller.Listener.GameWebsocketListener;
import GameOfLife.MVC.controller.Listener.LobbyWebsocketListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;

/**
 * Created by kulandas on 23.08.2016.
 */
@Controller
public class WebsocketController {
    private ArrayList<LobbyWebsocketListener> lobbyWebsocketListeners = new ArrayList<>();
    private ArrayList<GameLobbyWebsocketListener> gameLobbyWebsocketListeners = new ArrayList<>();
    private ArrayList<GameWebsocketListener> gameWebsocketListeners = new ArrayList<>();

    @Autowired
    private UserManager userManager;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/lobby/accept")
    public void ready(String data,Principal principal){
        if(principal.getName().equals(data))return;
        WebsocketEvent<String> event=new WebsocketEvent<String>(data,"ready",userManager.getUserByName(principal.getName()));
        for (LobbyWebsocketListener lwl : lobbyWebsocketListeners) {
            lwl.onReadyToPlay(event);
        }
    }

    @MessageMapping("/lobby/{command}")
    public void lobby(@DestinationVariable String command,Principal principal){
        WebsocketEvent event=new WebsocketEvent(command,userManager.getUserByName(principal.getName()));

        for (LobbyWebsocketListener lwl : lobbyWebsocketListeners) {
            switch (command) {
                case "add":
                    lwl.onAddGameOffer(event);
                    break;
                case "leave":
                    lwl.onGoToTheProfile(event);
                    break;
                case "start":
                    lwl.onConnect(event);
                    break;
                case "delete":
                    lwl.onDeleteGameOffer(event);
                    break;
                default:
            }
        }
    }

    @MessageMapping("/game/{room}/click")
    public void click(Position pos, @DestinationVariable String room, Principal principal){
        WebsocketEvent<Position> event=new WebsocketEvent<Position>(pos,"click",userManager.getUserByName(principal.getName()),room);
        for (GameWebsocketListener lwl : gameWebsocketListeners)
            lwl.onClickCells(event);
    }

    @MessageMapping("/game/{room}/{command}")
    public void game(@DestinationVariable String command,@DestinationVariable String room, Principal principal){
        WebsocketEvent event=new WebsocketEvent(command,userManager.getUserByName(principal.getName()),room);

        for (GameWebsocketListener lwl : gameWebsocketListeners) {
            switch (command) {
                case "ready":
                    lwl.onReady(event);
                    break;
                case "connect":
                    lwl.onConnect(event);
                    break;
                case "end":
                    lwl.onEnd(event);
                    break;
                default:
            }
        }
    }

    @MessageMapping("/gameLobby/{room}/selectTeam")
    public void click(String team, @DestinationVariable String room, Principal principal){
        WebsocketEvent<String> event=new WebsocketEvent<String>(team,"selectTeam",userManager.getUserByName(principal.getName()),room);
        for (GameLobbyWebsocketListener lwl : gameLobbyWebsocketListeners)
            lwl.onSelectTeam(event);
    }

    @MessageMapping("/gameLobby/{room}/{command}")
    public void gameLobby(@DestinationVariable String command,@DestinationVariable String room,Principal principal){
        WebsocketEvent event=new WebsocketEvent(command,userManager.getUserByName(principal.getName()),room);

        for (GameLobbyWebsocketListener lwl : gameLobbyWebsocketListeners) {
            switch (command) {
                case "start":
                    lwl.onStart(event);
                    break;
                case "leave":
                    lwl.onLeave(event);
                    break;
                case "connect":
                    lwl.onConnect(event);
                    break;
                default:
            }
        }
    }

    public void addLobbyWebsocketListener(LobbyWebsocketListener lobbyWebsocketListener)
    {
        lobbyWebsocketListeners.add(lobbyWebsocketListener);
    }

    public void addGameLobbyWebsocketListener(GameLobbyWebsocketListener gameLobbyWebsocketListener)
    {
        gameLobbyWebsocketListeners.add(gameLobbyWebsocketListener);
    }

    public void addGameWebsocketListener(GameWebsocketListener gameWebsocketListener)
    {
        gameWebsocketListeners.add(gameWebsocketListener);
    }

    public void send(String topic,Object data){
        this.messagingTemplate.convertAndSend(topic,data);
    }
}
