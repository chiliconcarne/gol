package GameOfLife.MVC.controller.Service;

import GameOfLife.MVC.controller.Controller.WebsocketController;
import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;
import GameOfLife.MVC.controller.Listener.GameLobbyWebsocketListener;
import GameOfLife.MVC.model.Repository.OfferRepository;
import GameOfLife.MVC.model.Repository.PlayerRepository;
import GameOfLife.MVC.model.Repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameLobby implements GameLobbyWebsocketListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    public GameLobby(WebsocketController websocketController){
        websocketController.addGameLobbyWebsocketListener(this);
    }

    @Override
    public void onStart(WebsocketEvent event) {

    }

    @Override
    public void onLeave(WebsocketEvent event) {

    }

    @Override
    public void onConnect(WebsocketEvent event) {
        System.out.println("Hallo hier ist das Connect Event bei dem Game Lobby Server "+event.getRoom());
    }

    @Override
    public void onSelectTeam(WebsocketEvent event) {
        System.out.println("Der Spieler möchte dem Team "+event.getDaten()+" Joinen");
    }
}
