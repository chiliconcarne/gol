package GameOfLife.example.controller;

import GameOfLife.example.json.Board;
import GameOfLife.example.json.Position;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by sernowm on 10.08.2016.
 */
@Controller
public class gameController {
    @MessageMapping("/game")
    @SendTo("/game/board")
    public Board ready() throws Exception {
        return new Board();
    }
    @MessageMapping("/set")
    @SendTo("/game/board")
    public Board set(Position pos) throws Exception {
        return new Board();
    }
}