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

    @MessageMapping("/game/start")
    @SendTo("/out/game/message")
    public Message start(Principal principal) throws Exception {
        Game g = gRepo.findOne(1);
        if(g == null) {
            Profil p = pRepo.findOne(principal.getName());
            int[][] board = new int[p.getHeight()][p.getWidth()];
            for(int y = 0; y < p.getHeight(); y++){
                for(int x = 0; x < p.getWidth(); x++){
                    board[y][x]=0;
                }
            }
            gRepo.save(new Game(1, principal.getName(), null, board));
            return new Message(principal.getName() + " tritt dem Spiel bei.\nWarte auf anderen Spieler...");
        } else {
            g.setSpieler2(principal.getName());
            gRepo.save(g);
            return new Message(principal.getName() + " tritt dem Spiel bei.\nSpiel beginnt in einem kurzen Moment.");
        }
    }
    @MessageMapping("/game/ready")
    @SendTo("/out/game/message")
    public Message ready(Principal principal) throws Exception {
        Game g = gRepo.findOne(1);
        if (g.getReady() == 0 && g.getPhase() == GamePhase.Start) {
            g.setReady(1);
            gRepo.save(g);
            return new Message(principal.getName() + " ist bereit. Und wartet auf " + g.getSpieler2() + ".");
        }
        if (g.getReady() == 1 && g.getPhase() == GamePhase.Start) {
            g.setReady(0);
            g.setPhase(GamePhase.Spiel);
            gRepo.save(g);
            return new Message(principal.getName() + " ist bereit.\nDas Spiel beginnt.");
        }
        return new Message("Undefiniert");
    }
    @MessageMapping("/game/set")
    @SendTo("/out/game/state")
    public Board set(Position pos, Principal principal) throws Exception {
        BoardLogik bl = ctx.getBean(BoardLogik.class);
        bl.init(gRepo.findOne(1));
        bl.set(pos.getX(),pos.getY(),principal.getName());
        return new Board(bl.finish());
    }
    @Scheduled(fixedRate = 500)
    public void update(){
        Game g = gRepo.findOne(1);
        if(g!=null){
            if(g.getSpieler1()!=null&&g.getSpieler2()!=null) {
                BoardLogik bl = ctx.getBean(BoardLogik.class);
                bl.init(gRepo.findOne(1));
                bl.step();
                this.messagingTemplate.convertAndSend("/out/game/state",new Board(bl.finish()));
            }
        }
    }
}