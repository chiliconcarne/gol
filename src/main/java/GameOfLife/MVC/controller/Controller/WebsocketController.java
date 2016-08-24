package GameOfLife.MVC.controller.Controller;

import GameOfLife.MVC.controller.Configuration.UserManager;
import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;
import GameOfLife.MVC.controller.Listener.GameLobbyWebsocketListener;
import GameOfLife.MVC.controller.Listener.GameWebsocketListener;
import GameOfLife.MVC.controller.Listener.LobbyWebsocketListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
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

    @MessageMapping("/lobby/{command}")
    public void lobby(@DestinationVariable String command,Principal principal){
        WebsocketEvent event=new WebsocketEvent(command,userManager.getUserByName(principal.getName()));

        for (LobbyWebsocketListener lwl : lobbyWebsocketListeners) {
            switch (command) {
                case "add":
                    lwl.onAddGameOffer(event);
                    break;
                case "ready":
                    lwl.onReadyToPlay(event);
                    break;
                case "leave":
                    lwl.onGoToTheProfile(event);
                    break;
                case "connect":
                    lwl.onConnect(event);
                    break;
                case "delete":
                    lwl.onDeleteGameOffer(event);
                    break;
                default:
            }
        }
    }
    @MessageMapping("/game/{command}")
    public void game(@DestinationVariable String command,Principal principal){
        WebsocketEvent event=new WebsocketEvent(command,userManager.getUserByName(principal.getName()));

        for (GameWebsocketListener lwl : gameWebsocketListeners) {
            switch (command) {
                case "click":
                    lwl.onClickCells(event);
                    break;
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
    @MessageMapping("/gameLobby/{command}")
    public void gameLobby(@DestinationVariable String command,Principal principal){
        WebsocketEvent event=new WebsocketEvent(command,userManager.getUserByName(principal.getName()));

        for (GameLobbyWebsocketListener lwl : gameLobbyWebsocketListeners) {
            switch (command) {
                case "start":
                    lwl.onStart(event);
                    break;
                case "selectTeam":
                    lwl.onSelectTeam(event);
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
}
