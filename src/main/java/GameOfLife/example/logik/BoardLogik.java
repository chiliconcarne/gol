package GameOfLife.example.logik;

import GameOfLife.example.entity.Game;
import GameOfLife.example.json.Message;
import GameOfLife.example.logik.listener.CellEvent;
import GameOfLife.example.logik.listener.RuleObserver;
import GameOfLife.example.repository.GameRepository;
import GameOfLife.example.state.CellState;
import GameOfLife.example.state.GamePhase;
import GameOfLife.example.state.PlayerState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
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
        switch(g.getPhase()) {
            case Start:
                if(g.getPlayer(player).getPlayerState() != PlayerState.Ready) {
                    int maxCells = (g.getWidth() * g.getHeight() / 5);
                    int bereich = g.getWidth() / 5 * 2;
                    if (player.equals(g.getPlayer1().getName()))
                        if (x > bereich) return;
                    if (player.equals(g.getPlayer2().getName()))
                        if (x < g.getWidth() - bereich - 1) return;
                    if (g.getBoard()[y][x] > 0)
                        g.getBoard()[y][x] = 0;
                    else if (g.getPlayer(player).getCells() < maxCells)
                        g.getBoard()[y][x] = g.getPlayer(player).getColor();
                }
                break;
            case Spiel:
                if (g.getPlayer(player).getLager() > 0) {
                    g.copyBoard();
                    Cell cell = new Cell(g, x, y);
                    if (cell.isFriendly(player)) {
                        CellEvent cellEvent = new CellEvent(cell);
                        cellEvent.setTransform(g.getPlayer(player).getCellState());
                        RuleObserver.getInstance().onSpread(cellEvent);
                    } else if (cell.isNeutral()) {
                        CellEvent cellEvent = new CellEvent(cell);
                        cellEvent.setTransform(g.getPlayer(player).getCellState());
                        RuleObserver.getInstance().onPlayerRevive(cellEvent);
                    } else if (cell.isEnemy(player))
                        RuleObserver.getInstance().onKill(new CellEvent(cell));
                    g.getPlayer(player).addLager(-1);
                    g.switchBoard();
                }
                break;
        }
        g.cellCount();
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
                g.addEnergy();
                checkWin();
                break;
        }
    }
    public void checkWin(){
        if(this.g.getPhase()==GamePhase.Spiel) {
            if(true) { //Vernichtender Sieg
                if (g.getPlayer1().getCells() == 0 && g.getPlayer1().getLager() <= 1 && g.getPlayer2().getCells() == 0 && g.getPlayer2().getLager() <= 1) {
                    unentschieden();
                    return;
                }
                if (g.getPlayer2().getCells() == 0 && g.getPlayer2().getLager() <= 1) {
                    g.getPlayer1().addPoints(50);
                    calcWinner("Vernichtender Sieg");
                }
                if (g.getPlayer1().getCells() == 0 && g.getPlayer1().getLager() <= 1) {
                    g.getPlayer2().addPoints(50);
                    calcWinner("Vernichtender Sieg");
                }
            }
            if(true) {//Eroberungssieg
                double winCon = (g.getWidth() * g.getHeight()) * (g.getWinCondition() / 100.0);
                if (g.getPlayer1().getCells() > winCon) {
                    g.getPlayer1().addPoints(25);
                    calcWinner("Eroberungssieg");
                }
                if (g.getPlayer2().getCells() > winCon) {
                    g.getPlayer2().addPoints(25);
                    calcWinner("Eroberungssieg");
                }
            }
            if(false) {//Überlebender Sieg
                if (g.getRunde() > 200) {
                    calcWinner("Überlebenden Sieg");
                }
            }
        }
    }
    public void calcWinner(String sieg){
        if(g.getPlayer1().getCells() == g.getPlayer2().getCells()){
            unentschieden();
            return;
        }
        if(g.getPlayer1().getCells() > g.getPlayer2().getCells())
            winner(g.getPlayer1().getName(),sieg);
        if(g.getPlayer2().getCells() > g.getPlayer1().getCells())
            winner(g.getPlayer2().getName(),sieg);
    }
    public void winner(String winner, String reason){
        g.setPhase(GamePhase.Ende);
        messagingTemplate.convertAndSendToUser(winner,"/out/game/message","Du gewinnt das Spiel durch "+reason+"!");
        messagingTemplate.convertAndSendToUser(g.getOpponent(winner).getName(),"/out/game/message",winner + " gewinnt das Spiel durch "+reason+"!");
    }
    public void unentschieden(){
        g.setPhase(GamePhase.Ende);
        messagingTemplate.convertAndSendToUser(g.getPlayer1().getName(),"/out/game/message","Das Spiel ging Unentschieden aus!");
        messagingTemplate.convertAndSendToUser(g.getPlayer2().getName(),"/out/game/message","Das Spiel ging Unentschieden aus!");
    }
    public Game finish(){
        gRepo.save(this.g);
        return this.g;
    }
}