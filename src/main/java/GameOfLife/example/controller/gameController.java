package GameOfLife.example.controller;

import GameOfLife.example.json.Board;
import GameOfLife.example.json.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * Created by sernowm on 10.08.2016.
 */
@Controller
public class gameController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/ready")
    @SendTo("/game/board")
    public Board ready() throws Exception {
        return new Board();
    }
    @MessageMapping("/set")
    @SendTo("/game/board")
    public Board set(Position pos) throws Exception {
        return new Board();
    }
    @Scheduled(fixedRate = 500)
    public void update(){
        this.messagingTemplate.convertAndSend("/game/board",new Board());
    }
}