package GameOfLife.example.controller;

import GameOfLife.example.entity.Game;
import GameOfLife.example.json.Board;
import GameOfLife.example.json.Position;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.repository.ProfilRepository;
import GameOfLife.example.security.UserManager;
import org.jboss.logging.annotations.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Created by sernowm on 10.08.2016.
 */
@Controller
public class gameController {
    @Autowired
    GameRepository gRepo;
    @Autowired
    ProfilRepository pRepo;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/start")
    @SendTo("/game/board")
    public Board start(Principal principal) throws Exception {
        return new Board();
    }
    @MessageMapping("/ready")
    @SendTo("/game/board")
    public Board ready() throws Exception {
        return new Board();
    }
    @MessageMapping("/set")
    @SendTo("/game/board")
    public Board set(Position pos, Principal principal) throws Exception {
        System.out.println(pRepo.findOne(principal.getName()));
        return new Board(principal.getName());
    }
    @Scheduled(fixedRate = 5000)
    public void update(){
        this.messagingTemplate.convertAndSend("/game/board",new Board());
    }
}