package GameOfLife.example.controller;

import GameOfLife.example.entity.Game;
import GameOfLife.example.json.Message;
import GameOfLife.example.json.Position;
import GameOfLife.example.logik.BoardLogik;
import GameOfLife.example.service.BoerseService;
import GameOfLife.example.service.GameService;
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

    @Autowired
    BoerseService boerse;

    @Autowired
    GameService game;

    @MessageMapping("/game/start")
    public void start(Principal principal) throws Exception {
        game.start(principal.getName());
    }

    @MessageMapping("/game/ready")
    public void ready(Principal principal) throws Exception {
        game.ready(principal.getName());
    }

    @MessageMapping("/game/end")
    public void end(Principal principal) throws Exception {
        game.end(principal.getName());
    }

    @MessageMapping("/game/set")
    public void set(Position pos, Principal principal) throws Exception {
        game.set(principal.getName(),pos.getX(),pos.getY());
    }

    private Game getGame(String player) {
        return gRepo.findOneByN1OrN2(player, player);
    }
}