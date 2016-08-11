package GameOfLife.example.controller;

import GameOfLife.example.entity.Game;
import GameOfLife.example.entity.Profil;
import GameOfLife.example.json.Board;
import GameOfLife.example.json.Message;
import GameOfLife.example.json.Position;
import GameOfLife.example.logik.BoardLogik;
import GameOfLife.example.logik.GamePhase;
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
    @MessageMapping("/start")
    @SendTo("/game/message")
    public Message start(Principal principal) throws Exception {
        Game g = gRepo.findOne(1);
        if(g == null){
            Profil p = pRepo.findOne(g.getSpieler1());
            int[][] board = new int[p.getHeight()][p.getWidth()];
            for(int y = 0; y < p.getHeight(); y++){
                for(int x = 0; x < p.getWidth(); x++){
                    board[y][x]=0;
                }
            }
            gRepo.save(new Game(1,principal.getName(),null,board));
        }else {
            g.setSpieler2(principal.getName());
            gRepo.save(g);
        }
        return new Message(principal.getName()+" tritt dem Spiel bei.");

    }
    @MessageMapping("/ready")
    @SendTo("/game/message")
    public Message ready(Principal principal) throws Exception {
        Game g = gRepo.findOne(1);
        if(g.getReady()==0&&g.getPhase()==GamePhase.Start) {
            g.setReady(1);
            return new Message(principal.getName()+" ist bereit.");
        }
        if(g.getReady()==1&&g.getPhase()==GamePhase.Start) {
            g.setReady(0);
            g.setPhase(GamePhase.Spiel);
            return new Message(principal.getName()+" ist bereit.\nDas Spiel beginnt.");
        }
        return new Message("Undefiniert");
    }
    @MessageMapping("/set")
    @SendTo("/game/board")
    public Board set(Position pos, Principal principal) throws Exception {
        BoardLogik bl = ctx.getBean(BoardLogik.class);
        bl.init(gRepo.findOne(1));
        bl.set(pos.getX(),pos.getY(),principal.getName());
        return new Board(bl.finish());
    }
    @Scheduled(fixedRate = 5000)
    public void update(){
        Game g = gRepo.findOne(1);
        if(g!=null){
            if(g.getSpieler1()!=null&&g.getSpieler2()!=null) {
                BoardLogik bl = ctx.getBean(BoardLogik.class);
                bl.init(gRepo.findOne(1));
                bl.step();
                this.messagingTemplate.convertAndSend("/game/board",new Board(bl.finish()));
            }
        }
    }
}