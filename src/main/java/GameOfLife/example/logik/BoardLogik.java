package GameOfLife.example.logik;

import GameOfLife.example.entity.Game;
import GameOfLife.example.entity.Profil;
import GameOfLife.example.json.Message;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.repository.ProfilRepository;
import GameOfLife.example.state.GamePhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

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
    private int[][] bold,bnew;
    private int p1=0,p2=0;
    public void init(Game g){
        this.g=g;
        this.p=pRepo.findOne(g.getPlayer1());
        Profil p = pRepo.findOne(g.getPlayer2());
        if(p.getColor1()==this.p.getColor1())
            this.secontColor=p.getColor2();
        else
            this.secontColor=p.getColor1();
    }
    public void set(int x,int y,String player){
        if(this.g.getPhase()== GamePhase.Start){
            int percent = Math.round(this.p.getWidth()/5.0f) * 2;
            if(this.p.getUsername()==player) {
                if (x < percent) {
                    if (this.g.getBoard()[y][x]==0)
                        this.g.getBoard()[y][x] = this.p.getColor1();
                    else
                        this.g.getBoard()[y][x] = 0;
                }
            } else {
                if (x >= this.p.getWidth()-percent) {
                    if(this.g.getBoard()[y][x]==0)
                        this.g.getBoard()[y][x] = this.secontColor;
                    else
                        this.g.getBoard()[y][x] = 0;
                }
            }
        } else if (this.g.getPhase()==GamePhase.Spiel){
            if(this.p.getUsername()==player){ // Spieler 1
                if(this.g.getBoard()[y][x]==0){ //Neutrale Zelle wird wiedergeboren
                    this.g.getBoard()[y][x]=this.p.getColor1();
                } else if (this.g.getBoard()[y][x]==this.p.getColor1()) { //Freundliche Zelle explodiert
                    int dy=0,dx=0;
                    for(int i=1;i<=9;i++){
                        dy=(i/9-2)+y;
                        dx=(i%3-1)+x;
                        if(dy<0||dx<0||dy>this.p.getHeight()-1||dx>this.p.getWidth()-1)continue;
                        if(this.g.getBoard()[dy][dx]==0)
                            this.g.getBoard()[dy][dx]=this.p.getColor1();
                        else
                            this.g.getBoard()[dy][dx]=0;
                    }
                } else if (this.g.getBoard()[y][x]==this.secontColor) { //Feindliche Zelle stirbt
                    this.g.getBoard()[y][x]=0;
                }
            } else { // Spieler 2
                if(this.g.getBoard()[y][x]==0){ //Neutrale Zelle wird wiedergeboren
                    this.g.getBoard()[y][x]=this.secontColor;
                } else if (this.g.getBoard()[y][x]==this.secontColor) { //Freundliche Zelle explodiert
                    int dy=0,dx=0;
                    for(int i=1;i<=9;i++){
                        dy=(i/9-2)+y;
                        dx=(i%3-1)+x;
                        if(dy<0||dx<0||dy>this.p.getHeight()-1||dx>this.p.getWidth()-1)continue;
                        if(this.g.getBoard()[dy][dx]==0)
                            this.g.getBoard()[dy][dx]=this.secontColor;
                        else
                            this.g.getBoard()[dy][dx]=0;
                    }
                } else if (this.g.getBoard()[y][x]==this.p.getColor1()) { //Feindliche Zelle stirbt
                    this.g.getBoard()[y][x]=0;
                }
            }
        }
    }

    public void step(){
        if(this.g.getPhase()==GamePhase.Spiel){
            bold = this.g.getBoard();
            bnew = new int[bold.length][bold[0].length];
            for(int y = 0; y < p.getHeight(); y++){
                for(int x = 0; x < p.getWidth(); x++){
                    if(bold[y][x]>0){//Living Cell
                    } else { // Dead Cell
                    }
                }
            }
            this.g.setBoard(bnew);
            cellCount();
            checkWin();
        }
    }
    public void cellCount(){
        p1=0;p2=0;
        for(int y = 0; y < p.getHeight(); y++){
            for(int x = 0; x < p.getWidth(); x++){
                int cell = this.g.getBoard()[y][x];
                if(cell==this.secontColor)
                    p2++;
                else if (cell==this.p.getColor1())
                    p1++;
            }
        }
    }
    public void checkWin(){
        if(this.g.getPhase()==GamePhase.Spiel) {
            int max = p.getHeight() * p.getWidth();
            if ((p1 > max * (p.getWin() / 100.0)) || p2 == 0) {
                this.g.setPhase(GamePhase.Ende);
                this.g.setWinner(this.g.getPlayer1());
                this.messagingTemplate.convertAndSend("/game/message", new Message(this.g.getPlayer1() + " gewinnt das Spiel!"));
            }
            if (p2 > max * (p.getWin() / 100.0) || p1 == 0) {
                this.g.setPhase(GamePhase.Ende);
                this.g.setWinner(this.g.getPlayer2());
                this.messagingTemplate.convertAndSend("/game/message", new Message(this.g.getPlayer2() + " gewinnt das Spiel!"));
            }
        }
    }
    public Game finish(){
        gRepo.save(this.g);
        return this.g;
    }
}