package GameOfLife.example.service;

import GameOfLife.example.entity.Game;
import GameOfLife.example.entity.Offer;
import GameOfLife.example.entity.Profil;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.repository.OfferRepository;
import GameOfLife.example.repository.ProfilRepository;
import GameOfLife.example.state.OfferState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by sernowm on 17.08.2016.
 */
@Service
public class BoerseService {
    @Autowired
    private OfferRepository oRepo;

    @Autowired
    private GameRepository gRepo;

    @Autowired
    private ProfilRepository pRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void start(String sender){
        Offer o = oRepo.findOne(sender);
        if(o != null){
            if(!o.getOfferState().equals(OfferState.InProgress)) {
                o.setOfferState(OfferState.Available);
                oRepo.save(o);
                update();
            }
        } else {
            send(sender,"list",getOfferList());
        }
    }

    public void update() {
        send("list",getOfferList());
    }

    public void add(String sender){
        Offer o = oRepo.findOne(sender);
        Profil p = pRepo.findOne(sender);
        if(o == null) {
            o = new Offer(sender,OfferState.Available,p.getWin(),p.getHeight(),p.getWidth(),p.getColor1());
            oRepo.save(o);
            update();
        } else {
            msg(sender,"Du hast bereits ein Angebot erstellt!");
        }
    }

    public void remove(String sender){
        Offer o = oRepo.findOne(sender);
        if(o != null){
            oRepo.delete(o);
            update();
        } else {
            msg(sender,"Du hast kein Angebot, welches gelöscht werden könnte!");
        }
    }

    public void accept(String sender,String gameOwner){
        if(!gameOwner.equals(sender)) {
            Offer o = oRepo.findOne(gameOwner);
            if (o != null && o.getOfferState().equals(OfferState.Available)) {
                leave(sender);
                o.setOfferState(OfferState.InProgress);
                Profil p = pRepo.findOne(sender);
                o.setOpponent(p);
                Game game = new Game(o);
                oRepo.save(o);
                gRepo.save(game);
                send(sender, "game", "");
                send(gameOwner, "game", "");
                update();
            } else {
                msg(sender, "Das Game ist nicht verfügbar!");
            }
        } else {
            msg(sender, "Du kannst deinem eigenen Game nicht beitretten!");
        }
    }

    public void leave(String sender) {
        Offer o = oRepo.findOne(sender);
        if(o != null){
            o.setOfferState(OfferState.Unavailable);
            oRepo.save(o);
            update();
        }
    }

    private Iterable<Offer> getOfferList()
    {
        return oRepo.findAll();
    }

    private void msg(String user,String message){
        send(user,"message",message);
    }

    private void send(String user,String topic,Object response){
        this.messagingTemplate.convertAndSendToUser(user,"/out/boerse/"+topic,response);
    }

    private void send(String topic,Object response){
        this.messagingTemplate.convertAndSend("/out/boerse/"+topic,response);
    }
}
