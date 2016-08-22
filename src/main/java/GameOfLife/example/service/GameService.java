package GameOfLife.example.service;

import GameOfLife.example.entity.Game;
import GameOfLife.example.entity.Player;
import GameOfLife.example.logik.BoardLogik;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.repository.OfferRepository;
import GameOfLife.example.repository.ProfilRepository;
import GameOfLife.example.state.GamePhase;
import GameOfLife.example.state.PlayerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sernowm on 17.08.2016.
 */
@Service
public class GameService {
    @Autowired
    private OfferRepository oRepo;

    @Autowired
    private GameRepository gRepo;

    @Autowired
    private ProfilRepository pRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private BoardLogik boardLogik;

    @Autowired
    private BoerseService boerse;

    public void start(String sender){
        Game g = getGame(sender);
        if(g != null)
        {
            Player p1 = g.getPlayer(sender);
            Player p2 = p1.getOpponent(g);

            p1.setPlayerState(PlayerState.Connected);
            gRepo.save(g);

            if(getGame(sender).getOpponent(sender).getPlayerState().equals(PlayerState.Connected)){
                msg(p1.getName(),"Alle Spieler sind verbunden. Das Zellen setzten kann beginnen.");
                msg(p2.getName(),"Alle Spieler sind verbunden. Das Zellen setzten kann beginnen.");
                send(p1.getName(),"state",g);
                send(p2.getName(),"state",g);
                g.setPhase(GamePhase.Start);
                gRepo.save(g);
            } else {
                msg(p1.getName(),String.format("Warte auf %s.",p2.getName()));
            }
        } else {
            msg(sender,"Es ist ein Fehler aufgetretten!");
        }
    }

    public void ready(String sender){
        Game g = getGame(sender);
        if(g != null)
        {
            Player p1 = g.getPlayer(sender);
            Player p2 = p1.getOpponent(g);

            if(p1.getPlayerState().equals(PlayerState.Connected)){
                p1.setPlayerState(PlayerState.Ready);
                gRepo.save(g);
                if(getGame(sender).getOpponent(sender).getPlayerState().equals(PlayerState.Ready)){
                    msg(p1.getName(),"Das Spiel beginnt.");
                    msg(p2.getName(),"Das Spiel beginnt.");
                    g.setPhase(GamePhase.Spiel);
                    gRepo.save(g);
                } else {
                    msg(p1.getName(),String.format("Du bist Bereit. Und wartest auf %s.",p2.getName()));
                    msg(p2.getName(),String.format("%s ist Bereit. Und wartet auf dich.",p1.getName()));
                }

            }
        } else {
            msg(sender,"Es ist ein Fehler aufgetretten!");
        }
    }

    public void end(String sender){
        Game g = getGame(sender);
        if(g != null)
        {
            Player p1 = g.getPlayer(sender);
            Player p2 = p1.getOpponent(g);

            p1.setPlayerState(PlayerState.Leave);

            if(p2.getPlayerState().equals(PlayerState.Leave)){
                oRepo.delete(oRepo.findOne(g.getPlayer1().getName()));
                gRepo.delete(g);
                boerse.update();
            } else {
                msg(p2.getName(),String.format("%s ist gegangen.",p1.getName()));
                gRepo.save(g);
            }
        } else {
            msg(sender,"Es ist ein Fehler aufgetretten!");
        }
    }

    public void set(String sender,int x,int y){
        Game g = getGame(sender);
        if(g != null)
        {
            Player p1 = g.getPlayer(sender);
            Player p2 = p1.getOpponent(g);

            boardLogik.init(g);
            boardLogik.set(x,y,p1.getName());
            update(boardLogik.finish());
        } else {
            msg(sender,"Es ist ein Fehler aufgetretten!");
        }
    }

    public void update(Game g) {
        send(g.getPlayer1().getName(),"state",g);
        send(g.getPlayer2().getName(),"state",g);
    }

    //@Scheduled(fixedRate = 700)
    public void step() {
        List<Game> games = (List<Game>) gRepo.findAll();
        for(Game g : games){
            if(g.getPhase() == GamePhase.Open){
                if(g.getPlayer1().getPlayerState()==PlayerState.Connected&&g.getPlayer2().getPlayerState()==PlayerState.Connected){
                    msg(g.getPlayer1().getName(),"Alle Spieler sind verbunden. Das Zellen setzten kann beginnen.");
                    msg(g.getPlayer2().getName(),"Alle Spieler sind verbunden. Das Zellen setzten kann beginnen.");
                    send(g.getPlayer1().getName(),"state",g);
                    send(g.getPlayer2().getName(),"state",g);
                    g.setPhase(GamePhase.Start);
                }
            }
            if(g.getPhase() == GamePhase.Start){
                if(g.getPlayer1().getPlayerState()==PlayerState.Ready&&g.getPlayer2().getPlayerState()==PlayerState.Ready){
                    msg(g.getPlayer1().getName(),"Das Spiel beginnt.");
                    msg(g.getPlayer2().getName(),"Das Spiel beginnt.");
                    g.setPhase(GamePhase.Spiel);
                }
            }
            if(g.getPhase() == GamePhase.Spiel){
                if(g.getPlayer1().getPlayerState()==PlayerState.Leave||g.getPlayer2().getPlayerState()==PlayerState.Leave){
                    g.setPhase(GamePhase.Ende);
                    gRepo.save(g);
                }
                boardLogik.init(g);
                boardLogik.step();
                update(boardLogik.finish());
            }
        }
    }

    private Game getGame(String player) {
        return gRepo.findOneByN1OrN2(player, player);
    }

    private void msg(String user,String message){
        send(user,"message",message);
    }

    private void send(String user,String topic,Object response){
        this.messagingTemplate.convertAndSendToUser(user,"/out/game/"+topic,response);
    }
}
