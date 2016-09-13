package GameOfLife.MVC.controller.Service;

import GameOfLife.MVC.controller.Controller.WebsocketController;
import GameOfLife.MVC.controller.Json.GameLobbyUpdates;
import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;
import GameOfLife.MVC.controller.Listener.GameLobbyWebsocketListener;
import GameOfLife.MVC.model.Entity.Game;
import GameOfLife.MVC.model.Entity.GamePlayer;
import GameOfLife.MVC.model.Entity.Player;
import GameOfLife.MVC.model.Entity.Team;
import GameOfLife.MVC.model.Repository.*;
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
    private GameRepository gameRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private GamePlayerRepository gamePlayerRepository;


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
        messagingTemplate.convertAndSend("/topic/gameLobby/"+event.getRoom()+"/msg",event.getUser().getUsername()+" kommt in die GameLobby");
    }

    @Override
    public void onSelectTeam(WebsocketEvent event) {
        Game game = gameRepository.findOneByRoom(event.getRoom());
        Team team=teamRepository.findOneByTeamNameAndGame((String) event.getDaten(),game.getGameId());

        if(team==null) {
            team = new Team(game, game.getSettings().getTeam1Color(), (String) event.getDaten());
            teamRepository.save(team);
            game.addTeam(team);
            gameRepository.save(game);
        }
        if((team.getGamePlayers().size())<2)
        {
            GamePlayer gamePlayer= new GamePlayer(playerRepository.findOneByName(event.getUser().getUsername()),team);
            team.addGamePlayer(gamePlayer);
            teamRepository.save(team);
            gamePlayerRepository.save(gamePlayer);
            messagingTemplate.convertAndSend("/topic/gameLobby/"+event.getRoom()+"/msg",event.getUser().getUsername()+" join team "+event.getDaten());

            System.out.println(event.getUser().getUsername()+" join team "+event.getDaten());
        } else {
            messagingTemplate.convertAndSend("/topic/gameLobby/"+event.getRoom()+"/msg","The team is full");
        }


        messagingTemplate.convertAndSend("/topic/gameLobby/"+event.getRoom()+"/update",new GameLobbyUpdates(game.getTeams()));



    }
}
