package GameOfLife.example.controller;

import GameOfLife.example.entity.Game;
import GameOfLife.example.json.Board;
import GameOfLife.example.json.Position;
import GameOfLife.example.repository.GameRepository;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Created by sernowm on 10.08.2016.
 */
@Controller
public class gameController {
    @Autowired
    private GameRepository gRepo;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/start")
    @SendTo("/game/board")
    public void start(Principal principal) throws Exception {
        Game g = gRepo.findOne(1);
        if(g == null){
            gRepo.save(new Game(1,principal.getName(),null));
        }else {
            g.setSpieler2(principal.getName());
            gRepo.save(g);
        }
    }
    @MessageMapping("/ready")
    @SendTo("/game/board")
    public Board ready() throws Exception {
        return new Board(gRepo.findOne(1));
    }
    @MessageMapping("/set")
    @SendTo("/game/board")
    public Board set(Position pos, Principal principal) throws Exception {
        return new Board(gRepo.findOne(1));
    }
    @Scheduled(fixedRate = 5000)
    public void update(){
        Game g = gRepo.findOne(1);
        if(g!=null){
            if(g.getSpieler1()!=null&&g.getSpieler2()!=null)
                this.messagingTemplate.convertAndSend("/game/board",new Board(gRepo.findOne(1)));
        }
    }
}