package GameOfLife.example.logik;

import GameOfLife.example.entity.Game;
import GameOfLife.example.entity.Profil;
import GameOfLife.example.json.Message;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Random;

/**
 * Created by sernowm on 11.08.2016.
 */
public class BoardLogik {
    @Autowired
    GameRepository gRepo;
    @Autowired
    ProfilRepository pRepo;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
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
        if(this.g.getPhase()==GamePhase.Start){
            if(this.p.getUsername()==player)
                if(x<(Math.ceil(this.p.getWidth()/5)*2)) {
                    this.g.getBoard()[y][x] = this.p.getColor1();
                }
            else
                if(x>(Math.ceil(this.p.getWidth()/5)*3)) {
                    this.g.getBoard()[y][x] = this.secontColor;
                }
        }
    }
    int[][] bold;
    int[][] bnew;
    public void step(){
        if(this.g.getPhase()==GamePhase.Spiel){
            bold = this.g.getBoard();
            bnew = new int[bold.length][bold[0].length];
            for(int y = 0; y < p.getHeight(); y++){
                for(int x = 0; x < p.getWidth(); x++){
                    if(bold[y][x]>0){//Living Cell
                        random(x,y);
                        //rulesLiving(x,y);
                    } else { // Dead Cell
                        random(x,y);
                        //rulesDead(x,y);
                    }
                }
            }
            this.g.setBoard(bnew);
            checkWin();
        }
    }
    Random rnd = new Random();
    int[] farben = new int[]{0,0,0,0,this.p.getColor1(),this.secontColor};
    public void random(int x,int y){
        bnew[y][x]=farben[rnd.nextInt(farben.length-1)];
    }
    public void rulesLiving(int x,int y){
        CheckCell cc = checkCell(x,y);
        if(bold[y][x]==this.p.getColor1()){
            if(cc.getSpieler1()<2||cc.getSpieler1()>3||cc.getSpieler2()==2) {
                bnew[y][x] = 0;
            } else if(cc.getSpieler2()==3) {
                bnew[y][x]=this.secontColor;
            } else {
                bnew[y][x]=this.p.getColor1();
            }
        }
        if(bold[y][x]==this.secontColor){
            if(cc.getSpieler2()<2||cc.getSpieler2()>3||cc.getSpieler1()==2) {
                bnew[y][x] = 0;
            } else if(cc.getSpieler1()==3) {
                bnew[y][x] = this.p.getColor1();
            } else {
                bnew[y][x]=this.secontColor;
            }
        }
    }
    public void rulesDead(int x,int y){
        CheckCell cc = checkCell(x,y);
        if(cc.getSpieler1()==3&&cc.getSpieler2()!=3){
            bnew[y][x]=this.p.getColor1();
            return;
        }
        if(cc.getSpieler2()==3){
            bnew[y][x]=this.secontColor;
            return;
        }
        bnew[y][x]=0;
    }
    public CheckCell checkCell(int x,int y){
        int spieler1=0;
        int spieler2=0;
        if(bold[y][x]==this.p.getColor1())spieler1++;
        if(bold[y][x]==this.secontColor)spieler2++;
        if(x>0){
            if(bold[y][x-1]==this.p.getColor1())spieler1++;
            if(bold[y][x-1]==this.secontColor)spieler2++;
        }
        if(x<this.p.getWidth()-1){
            if(bold[y][x+1]==this.p.getColor1())spieler1++;
            if(bold[y][x+1]==this.secontColor)spieler2++;
        }
        if(y>0){
            if(bold[y-1][x]==this.p.getColor1())spieler1++;
            if(bold[y-1][x]==this.secontColor)spieler2++;
        }
        if(y<this.p.getHeight()-1){
            if(bold[y+1][x]==this.p.getColor1())spieler1++;
            if(bold[+1][x]==this.secontColor)spieler2++;
        }
        if(x>0&&y>0){
            if(bold[y-1][x-1]==this.p.getColor1())spieler1++;
            if(bold[y-1][x-1]==this.secontColor)spieler2++;
        }
        if(x>0&&y<this.p.getHeight()-1){
            if(bold[y+1][x-1]==this.p.getColor1())spieler1++;
            if(bold[y+1][x-1]==this.secontColor)spieler2++;
        }
        if(x<this.p.getWidth()-1&&y>0){
            if(bold[y-1][x+1]==this.p.getColor1())spieler1++;
            if(bold[y-1][x+1]==this.secontColor)spieler2++;
        }
        if(x<this.p.getWidth()-1&&y<this.p.getHeight()-1){
            if(bold[y+1][x+1]==this.p.getColor1())spieler1++;
            if(bold[y+1][x+1]==this.secontColor)spieler2++;
        }
        return new CheckCell(9-spieler1-spieler2,spieler1,spieler2);
    }
    public void checkWin(){
        int p1=0,p2=0;
        for(int y = 0; y < p.getHeight(); y++){
            for(int x = 0; x < p.getWidth(); x++){
                int cell = this.g.getBoard()[y][x];
                if(cell==this.secontColor)
                    p2++;
                else if (cell==this.p.getColor1())
                    p1++;
            }
        }
        if(p1>(p.getHeight()*p.getWidth())/2) {
            this.g.setPhase(GamePhase.Ende);
            this.g.setWinner(this.g.getSpieler1());
            this.messagingTemplate.convertAndSend("/game/message",new Message(this.g.getSpieler1()+" gewinnt das Spiel!"));
        }
        if(p2>(p.getHeight()*p.getWidth())/2) {
            this.g.setPhase(GamePhase.Ende);
            this.g.setWinner(this.g.getSpieler2());
            this.messagingTemplate.convertAndSend("/game/message",new Message(this.g.getSpieler2()+" gewinnt das Spiel!"));
        }
    }
    public Game finish(){
        gRepo.save(this.g);
        return this.g;
    }
}