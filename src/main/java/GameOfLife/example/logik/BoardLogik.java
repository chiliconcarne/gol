package GameOfLife.example.logik;

import GameOfLife.example.entity.Game;
import GameOfLife.example.json.Message;
import GameOfLife.example.logik.listener.CellEvent;
import GameOfLife.example.logik.listener.RuleObserver;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.state.CellState;
import GameOfLife.example.state.GamePhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class BoardLogik {
    @Autowired
    GameRepository gRepo;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Game g;

    private int[][] bold,bnew;

    public void init(Game g){
        this.g=g;
    }
    public void set(int x,int y,String player){
        if(x < 0 || y < 0 || x > g.getWidth() || y > g.getHeight())
            return; // Falsche Coordinaten
        switch(g.getPhase()){
            case Start:
                int bereich = g.getWidth()/5*2;
                if(player.equals(g.getPlayer1()))
                    if(x>bereich)return;
                if(player.equals(g.getPlayer2()))
                    if(x<g.getWidth()-bereich-1)return;
                if(g.getBoard()[y][x]>0)
                    g.getBoard()[y][x]=0;
                else
                    g.getBoard()[y][x] = player.equals(g.getPlayer1()) ? g.getColorPlayer1() : g.getColorPlayer2();
                break;
            case Spiel:
                Cell cell = new Cell(g,x,y);
                if(cell.isFriendly(player)) {
                    CellEvent cellEvent = new CellEvent(cell);
                    cellEvent.setTransform(player.equals(g.getPlayer1()) ? CellState.player1 : CellState.player2);
                    RuleObserver.getInstance().onSpread(cellEvent);
                } else if(cell.isNeutral()) {
                    CellEvent cellEvent = new CellEvent(cell);
                    cellEvent.setTransform(player.equals(g.getPlayer1()) ? CellState.player1 : CellState.player2);
                    RuleObserver.getInstance().onPlayerRevive(cellEvent);
                }else if(cell.isEnemy(player))
                    RuleObserver.getInstance().onKill(new CellEvent(cell));
                break;
        }
    }

    public void step(){
        switch(g.getPhase()){
            case Spiel:
                for(int y = 0; y < g.getHeight(); y++){
                    for(int x = 0; x < g.getWidth(); x++){
                        RuleObserver.getInstance().rule(new Cell(g,x,y));
                    }
                }
                g.switchBoard();
                g.addRunde();
                g.cellCount();
                checkWin();
                break;
        }
    }
    public void checkWin(){
        if(this.g.getPhase()==GamePhase.Spiel) {
            double winCon = (g.getWidth() * g.getHeight()) * (g.getWinCondition() / 100.0);
            if(g.getZellenPlayer1() == 0 && g.getZellenPlayer2() == 0)
                unentschieden(g);
            if(g.getZellenPlayer1() > winCon)
                winner(g.getPlayer1(),"Überzahl Sieg");
            if(g.getZellenPlayer2() == 0)
                winner(g.getPlayer1(),"Vernichtender Sieg");
            if(g.getZellenPlayer2() > winCon)
                winner(g.getPlayer2(),"Überzahl Sieg");
            if(g.getZellenPlayer1() == 0)
                winner(g.getPlayer2(),"Vernichtender Sieg");
        }
    }
    public void winner(String winner, String reason){
        g.setPhase(GamePhase.Ende);
        g.setWinner(winner);
        messagingTemplate.convertAndSendToUser(winner,"/out/game/message", new Message("Du gewinnt das Spiel durch "+reason+"!"));
        messagingTemplate.convertAndSendToUser(g.getOpponent(winner),"/out/game/message", new Message(winner + " gewinnt das Spiel durch "+reason+"!"));
    }
    public void unentschieden(){
        g.setPhase(GamePhase.Ende);
        g.setWinner("");
        messagingTemplate.convertAndSendToUser(g.getPlayer1(),"/out/game/message", new Message("Das Spiel ging Unentschieden aus!"));
        messagingTemplate.convertAndSendToUser(g.getPlayer2(),"/out/game/message", new Message("Das Spiel ging Unentschieden aus!"));
    }
    public Game finish(){
        gRepo.save(this.g);
        return this.g;
    }
}