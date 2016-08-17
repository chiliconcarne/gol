package GameOfLife.example.entity;

import GameOfLife.example.state.CellState;
import GameOfLife.example.state.GamePhase;

import javax.persistence.*;

/**
 * Created by sernowm on 10.08.2016.
 */
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 65025)
    private Player player1, player2;

    private GamePhase phase = GamePhase.Open;

    //Only for DB Search
    private String n1,n2;

    private int winCondition;
    private int runde=0;

    @Column(length = 16581375)
    private int[][] board,newBoard;

    public Game() {
    }

    public Game(Offer offer){
        player1 = new Player(this,offer.getUsername(),offer.getColorPlayer1(),CellState.player1);
        player2 = new Player(this,offer.getOpponent(),offer.getColorPlayer2(),CellState.player2);
        n1=player1.getName();n2=player2.getName();
        winCondition = offer.getWinCondition();
        board = new int[offer.getBoardHeight()][offer.getBoardWidth()];
        for(int y = 0; y < offer.getBoardHeight(); y++){
            for(int x = 0; x < offer.getBoardWidth(); x++){
                board[y][x] = 0;
            }
        }
        newBoard = new int[getHeight()][getWidth()];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    public int getRunde() {
        return runde;
    }

    public void setRunde(int runde) {
        this.runde = runde;
    }

    public void addRunde(){
        this.runde++;
    }

    public int getHeight() {
        return board.length;
    }

    public int getWidth() {
        return board[0].length;
    }

    public int[][] getNewBoard() {
        return newBoard;
    }

    public void setNewBoard(int[][] newBoard) {
        this.newBoard = newBoard;
    }

    public void switchBoard() {
        setBoard(getNewBoard());
        this.newBoard = new int[getHeight()][getWidth()];
    }

    public void copyBoard() {
        setNewBoard(getBoard());
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getN1() {
        return n1;
    }

    public void setN1(String n1) {
        this.n1 = n1;
    }

    public String getN2() {
        return n2;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getOpponent(String player)
    {
        return player.equals(player1.getName()) ? player2 : player1;
    }

    public Player getOpponent(Player player)
    {
        return player.equals(player1) ? player2 : player1;
    }

    public Player getPlayer(String player) {
        return player.equals(player1.getName()) ? player1 : player2;
    }

    public Player getPlayer(Player player) {
        return player.equals(player1) ? player1 : player2;
    }

    public Player getPlayerByCellState(CellState cellState){
        if(cellState==player1.getCellState())
            return player1;
        if(cellState==player2.getCellState())
            return player2;
        return null;
    }

    public Player getPlayerByColor(int color){
        if(color==player1.getColor())
            return player1;
        if(color==player2.getColor())
            return player2;
        return null;
    }

    public void cellCount() {
        player1.setCells(0);
        player2.setCells(0);
        for(int[] row : board){
            for(int cell : row){
                if(getPlayerByColor(cell)!=null)
                    getPlayerByColor(cell).addCells(1);
            }
        }
    }

    public CellState getCellState(int x,int y){
        if(getPlayerByColor(board[y][x]) == null){
            return CellState.neutral;
        }
        return getPlayerByColor(board[y][x]).getCellState();
    }

    public void addEnergy() {
        int maxEnergy = getHeight() * getWidth() / 4;
        if(getPlayer1().getLager()<5){
            getPlayer1().addEnergy(getPlayer1().getCells());
            if(getPlayer1().getEnergy()>maxEnergy){
                getPlayer1().addLager(1);
                getPlayer1().addEnergy(-maxEnergy);
            }
        }
        if(getPlayer2().getLager()<5){
            getPlayer2().addEnergy(getPlayer2().getCells());
            if(getPlayer2().getEnergy()>maxEnergy){
                getPlayer2().addLager(1);
                getPlayer2().addEnergy(-maxEnergy);
            }
        }
    }
}