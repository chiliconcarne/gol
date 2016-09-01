package GameOfLife.MVC.controller.Service;

import GameOfLife.MVC.controller.Controller.WebsocketController;
import GameOfLife.MVC.controller.Listener.Event.WebsocketEvent;
import GameOfLife.MVC.controller.Listener.GameLobbyWebsocketListener;
import GameOfLife.MVC.model.Entity.Game;
import GameOfLife.MVC.model.Entity.Player;
import GameOfLife.MVC.model.Entity.Team;
import GameOfLife.MVC.model.Repository.GameRepository;
import GameOfLife.MVC.model.Repository.OfferRepository;
import GameOfLife.MVC.model.Repository.PlayerRepository;
import GameOfLife.MVC.model.Repository.TeamRepository;
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
        Team team = null;
        for(Team t : game.getTams()){
            if(t.getTeamName()==event.getDaten()){
                team = t;
            }
        }
        if(team==null){
            team = new Team(game,((game.getTams().size()>0)?game.getSettings().getTeam1Color():game.getSettings().getTeam2Color()),(String)event.getDaten());
            teamRepository.save(team);
        }
        messagingTemplate.convertAndSend("/topic/gameLobby/"+event.getRoom()+"/msg",event.getUser().getUsername()+" join team "+event.getDaten());
        System.out.println(event.getUser().getUsername()+" join team "+event.getDaten());
    }
}
