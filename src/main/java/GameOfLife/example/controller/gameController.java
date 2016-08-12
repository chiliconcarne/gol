package GameOfLife.example.controller;

import GameOfLife.example.entity.Game;
import GameOfLife.example.json.Message;
import GameOfLife.example.json.Position;
import GameOfLife.example.logik.BoardLogik;
import GameOfLife.example.state.GamePhase;
import GameOfLife.example.state.PlayerState;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
            String opponent = g.getOpponent(player);

            g.setState(player,PlayerState.Connected);
            gRepo.save(g);

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

    @MessageMapping("/game/end")
    public void end(Principal principal) throws Exception {
        String player = principal.getName();
        Game g = getGame(player);
        if(g != null){
            String opponent = g.getOpponent(player);
            if(g.getPlayer1().equals(principal.getName())){
                g.setPlayer1("Disconnected");
            }
            if(g.getPlayer2().equals(principal.getName())){
                g.setPlayer2("Disconnected");
            }
            g.setPhase(GamePhase.Ende);
            if(opponent!="Disconnected") {
                this.messagingTemplate.convertAndSendToUser(opponent, "/out/game/message", new Message(player + " ist gegangen!"));
                gRepo.save(g);
            }else {
                gRepo.delete(g);
                boerseController boerseController = ctx.getBean(GameOfLife.example.controller.boerseController.class);
                boerseController.boerseChanged();
            }
        }
    }

    @MessageMapping("/game/set")
    public void set(Position pos, Principal principal) throws Exception {
        BoardLogik bl = ctx.getBean(BoardLogik.class);
        Game g = getGame(principal.getName());
        bl.init(g);
        bl.set(pos.getX(), pos.getY(), principal.getName());
        bl.finish();
        SendBoardToPlayer(g);
    }

    @Scheduled(fixedRate = 300)
    public void update(){
        List<Game> games = (List<Game>) gRepo.findAll();
        for(Game g : games){
            if(g.getPhase() == GamePhase.Spiel && g.getStatePlayer1() != PlayerState.Disconnected && g.getStatePlayer2() != PlayerState.Disconnected){
                BoardLogik bl = ctx.getBean(BoardLogik.class);
                bl.init(g);
                bl.step();
                bl.finish();
                SendBoardToPlayer(g);
            }
        }
    }

    private void SendBoardToPlayer(Game g)
    {
        this.messagingTemplate.convertAndSendToUser(g.getPlayer1(), "/out/game/state", g);
        this.messagingTemplate.convertAndSendToUser(g.getPlayer2(), "/out/game/state", g);
    }

    private Game getGame(String player)
    {
        return gRepo.findOneByPlayer1OrPlayer2(player, player);
    }
}