package GameOfLife.example.logik;

import GameOfLife.example.entity.Game;
import GameOfLife.example.entity.Profil;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by sernowm on 11.08.2016.
 */
public class BoardLogik {
    @Autowired
    GameRepository gRepo;
    @Autowired
    ProfilRepository pRepo;
    private Game g;
    private Profil p;
    private int secontColor;
    public void init(Game g){
        this.g=g;
        this.p=pRepo.findOne(g.getSpieler1());
        Profil p = pRepo.findOne(g.getSpieler2());
        if(p.getColor1()==this.p.getColor1())
            this.secontColor=p.getColor2();
        else
            this.secontColor=p.getColor1();
    }
    public void set(int x,int y,String player){
        if(this.p.getUsername()==player)
            this.g.getBoard()[y][x]=this.p.getColor1();
        else
            this.g.getBoard()[y][x]=this.secontColor;
    }
    public void step(){

    }
    public Game finish(){
        gRepo.save(this.g);
        return this.g;
    }
}