package GameOfLife.example.controller;

import GameOfLife.example.entity.Game;
import GameOfLife.example.entity.Profil;
import GameOfLife.example.json.Board;
import GameOfLife.example.json.Message;
import GameOfLife.example.json.Position;
import GameOfLife.example.logik.BoardLogik;
import GameOfLife.example.logik.GamePhase;
import GameOfLife.example.logik.PlayerState;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

/**
 * Created by sernowm on 10.08.2016.
 */
@Controller
public class gameController {
    @Autowired
    private GameRepository gRepo;

    @Autowired
    private ProfilRepository pRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ApplicationContext ctx;

    @MessageMapping("/game/start")
    public void start(Principal principal) throws Exception {
        String player = principal.getName();
        Game g = getGame(player);

        if(g != null)
        {
            g.setState(player,PlayerState.Connected);
            gRepo.save(g);
            String opponent = g.getOpponent(player);

            this.messagingTemplate.convertAndSendToUser(player, "/out/game/state", g);
            this.messagingTemplate.convertAndSendToUser(player, "/out/game/message", new Message((g.getState(opponent) == PlayerState.Connected ? "Alle Spieler sind verbunden. Das Zellen setzten kann beginnen." : "Warten auf " + opponent + ".")));
            this.messagingTemplate.convertAndSendToUser(opponent, "/out/game/message", new Message((g.getState(opponent) == PlayerState.Connected ? "Alle Spieler sind verbunden. Das Zellen setzten kann beginnen." : player + " wartet auf dich.")));
        }
    }

    @MessageMapping("/game/ready")
    public void ready(Principal principal) throws Exception {
        String player = principal.getName();
        Game g = getGame(player);

        if(g != null)
        {
            String opponent = g.getOpponent(player);

            if (g.getState(player) != PlayerState.Ready)
            {
                g.setState(principal.getName(), PlayerState.Ready);

                this.messagingTemplate.convertAndSendToUser(player, "/out/game/message", new Message((g.getState(opponent) == PlayerState.Ready ? "Alle Spieler sind bereit. Spiel startet." : "Warten auf " + opponent + ".")));
                this.messagingTemplate.convertAndSendToUser(opponent, "/out/game/message", new Message((g.getState(opponent) == PlayerState.Ready ? "Alle Spieler sind bereit. Spiel startet." : player + " wartet auf dich.")));

                if (g.getStatePlayer1() == PlayerState.Ready && g.getStatePlayer2() == PlayerState.Ready){
                    g.setPhase(GamePhase.Spiel);
                }

                gRepo.save(g);
            }
        }
    }

    @MessageMapping("/game/set")
    public Board set(Position pos, Principal principal) throws Exception {
        BoardLogik bl = ctx.getBean(BoardLogik.class);
        bl.init(getGame(principal.getName()));
        bl.set(pos.getX(), pos.getY(), principal.getName());
        return new Board(bl.finish());
    }

    @Scheduled(fixedRate = 500)
    public void update(){
        List<Game> games = (List<Game>) gRepo.findAll();
        for(Game g : games){
            if(g.getStatePlayer1() != PlayerState.Disconnected && g.getStatePlayer2() != PlayerState.Disconnected){
                BoardLogik bl = ctx.getBean(BoardLogik.class);
                bl.init(g);
                bl.step();
                Board board = new Board(bl.finish());
                this.messagingTemplate.convertAndSendToUser(g.getPlayer1(), "/out/game/state", board);
                this.messagingTemplate.convertAndSendToUser(g.getPlayer2(), "/out/game/state", board);
            }
        }
    }

    private Game getGame(String player)
    {
        return gRepo.findOneByPlayer1OrPlayer2(player, player);
    }
}