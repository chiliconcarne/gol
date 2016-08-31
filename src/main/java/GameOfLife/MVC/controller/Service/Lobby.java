package GameOfLife.MVC.controller.Service;

import GameOfLife.MVC.controller.Controller.WebsocketController;
import GameOfLife.MVC.controller.Enum.OfferState;
import GameOfLife.MVC.controller.Json.OfferJson;
import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;
import GameOfLife.MVC.controller.Listener.LobbyWebsocketListener;
import GameOfLife.MVC.model.Entity.GamePlayer;
import GameOfLife.MVC.model.Entity.Offer;
import GameOfLife.MVC.model.Entity.Player;
import GameOfLife.MVC.model.Entity.Settings;
import GameOfLife.MVC.model.Repository.OfferRepository;
import GameOfLife.MVC.model.Repository.PlayerRepository;
import GameOfLife.MVC.model.Repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kulandas on 29.08.2016.
 */
@Service
public class Lobby implements LobbyWebsocketListener {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    public Lobby(WebsocketController websocketController) {
        websocketController.addLobbyWebsocketListener(this);
    }

    private void sendList(){
        List<Offer> offers = (List<Offer>) offerRepository.findAll();
        offers.sort((e1,e2) -> {
            if(e1.getOfferState()==e2.getOfferState()){
                return e1.getOfferGenerator().compareTo(e2.getOfferGenerator());
            } else {
                 if(e1.getOfferState()==OfferState.Available){
                     return -1;
                 }
                 if(e2.getOfferState()==OfferState.Available){
                     return 1;
                 }
                 if(e1.getOfferState()==OfferState.InProgress){
                     return -1;
                 }
                 if(e2.getOfferState()==OfferState.InProgress) {
                     return 1;
                 }
                return 0;
            }
        });
        ArrayList<OfferJson> offerJsons = new ArrayList();
        for ( Offer o : offers ){
            offerJsons.add(new OfferJson(playerRepository.findOneByName(o.getOfferGenerator()),o));
        }
        messagingTemplate.convertAndSend("/topic/lobby/list",offerJsons);
    }

    @Override
    public void onAddGameOffer(WebsocketEvent event)  {
        Player player=playerRepository.findOneByName(event.getUser().getUsername());
        Offer offer=offerRepository.findOneByOfferGenerator(player.getName());
        if(offer==null) {
            Settings settings = new Settings(
                    player.getBoard_width(),
                    player.getBoard_height(),
                    player.getWinCondition(),
                    player.getColor1(),
                    player.getColor2(),
                    player.getGameType()
            );
            offer = new Offer(0, player.getName(), OfferState.Available);
            settings.setOffer(offer);
            settingsRepository.save(settings);
            offerRepository.save(offer);
            sendList();
        }
    }

    @Override
    public void onDeleteGameOffer(WebsocketEvent event) {
        Player player=playerRepository.findOneByName(event.getUser().getUsername());
        Offer offer=offerRepository.findOneByOfferGenerator(player.getName());
        if(offer!=null) {
            settingsRepository.delete(offer.getSettings());
            offerRepository.delete(offer);
            sendList();
        }
    }

    @Override
    public void onGoToTheProfile(WebsocketEvent event) {
        Player player=playerRepository.findOneByName(event.getUser().getUsername());
        Offer offer=offerRepository.findOneByOfferGenerator(player.getName());
        if(offer!=null) {
            offer.setOfferState(OfferState.Unavailable);
            offerRepository.save(offer);
            sendList();
        }
    }

    @Override
    public void onReadyToPlay(WebsocketEvent event) {
        String room = (String) event.getDaten();
        Player player = playerRepository.findOneByName(event.getUser().getUsername());
        Offer offer = offerRepository.findOneByOfferGenerator(player.getName());
        GamePlayer gamePlayer = new GamePlayer();
    }

    @Override
    public void onConnect(WebsocketEvent event) {
        Player player=playerRepository.findOneByName(event.getUser().getUsername());
        Offer offer=offerRepository.findOneByOfferGenerator(player.getName());
        if(offer!=null) {
            offer.setOfferState(OfferState.Available);
            offerRepository.save(offer);
        }
        sendList();
    }
}
