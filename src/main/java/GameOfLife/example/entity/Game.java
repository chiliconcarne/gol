package GameOfLife.example.entity;

import GameOfLife.example.state.CellState;
import GameOfLife.example.state.GamePhase;
import GameOfLife.example.state.PlayerState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by sernowm on 10.08.2016.
 */
@Entity
public class Game {
    @Id
    private int id;

    private String player1, player2, winner;

    private GamePhase phase;

    private PlayerState statePlayer1;
    private PlayerState statePlayer2;

    @Column(length = 16581375)
    private int[][] board;

    private int colorPlayer1, colorPlayer2;

    private int winCondition;

    public Game()
    {

    }

    public Game(Profil p1, Profil p2)
    {
        id = 0;

        player1 = p1.getUsername();
        player2 = p2.getUsername();

        phase = GamePhase.Start;

        statePlayer1 = PlayerState.Disconnected;
        statePlayer2 = PlayerState.Disconnected;

        board = new int[p1.getHeight()][p1.getWidth()];
        for(int y = 0; y < p1.getHeight(); y++){
            for(int x = 0; x < p1.getWidth(); x++){
                board[y][x] = 0;
            }
        }

        colorPlayer1 = p1.getColor1();
        colorPlayer1 = p2.getColor1() == colorPlayer1 ? p2.getColor2() : p2.getColor1();

        winCondition = p1.getWin();
    }

    public Game(int id, String player1, String player2, String winner, GamePhase phase, PlayerState statePlayer1, PlayerState statePlayer2, int[][] board, int colorPlayer1, int colorPlayer2, int winCondition) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.phase = phase;
        this.statePlayer1 = statePlayer1;
        this.statePlayer2 = statePlayer2;
        this.board = board;
        this.colorPlayer1 = colorPlayer1;
        this.colorPlayer2 = colorPlayer2;
        this.winCondition = winCondition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    public PlayerState getStatePlayer1() {
        return statePlayer1;
    }

    public void setStatePlayer1(PlayerState statePlayer1) {
        this.statePlayer1 = statePlayer1;
    }

    public PlayerState getStatePlayer2() {
        return statePlayer2;
    }

    public void setStatePlayer2(PlayerState statePlayer2) {
        this.statePlayer2 = statePlayer2;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getColorPlayer1() {
        return colorPlayer1;
    }

    public void setColorPlayer1(int colorPlayer1) {
        this.colorPlayer1 = colorPlayer1;
    }

    public int getColorPlayer2() {
        return colorPlayer2;
    }

    public void setColorPlayer2(int colorPlayer2) {
        this.colorPlayer2 = colorPlayer2;
    }

    public int getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    public String getOpponent(String player)
    {
        return player == player1 ? player2 : player1;
    }

    public PlayerState getState(String player) {
        return player == player1 ? statePlayer1 : statePlayer2;
    }

    public void setState(String player, PlayerState state) {
        if(player == player1) statePlayer1 = state;
        else statePlayer2 = state;
    }

    public CellState getCellState(int x, int y) {
        if(board[x][y] == colorPlayer1) return CellState.player1;
        else if(board[x][y] == colorPlayer2) return CellState.player2;
        else return CellState.neutral;
    }

    public int getHeight() {
        return board.length;
    }

    public int getWidth() {
        return board[0].length;
    }
}
