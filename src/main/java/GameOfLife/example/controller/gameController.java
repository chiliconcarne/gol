package GameOfLife.example.controller;

import GameOfLife.example.json.Board;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by sernowm on 10.08.2016.
 */
@Controller
public class gameController {
    @MessageMapping("/ready")
    @SendTo("/game/board")
    public Board ready() throws Exception {
        return new Board();
    }

    @MessageMapping("/set")
    @SendTo("/game/board")
    public void set() throws Exception {

    }
}
