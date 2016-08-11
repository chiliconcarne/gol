package GameOfLife.example.entity;

import GameOfLife.example.logik.GamePhase;
import GameOfLife.example.logik.PlayerState;

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

    public Game(){}

    public Game(int id, String player1, String player2, int[][] board) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        phase = GamePhase.Start;
        statePlayer1 = PlayerState.Disconnected;
        statePlayer2 = PlayerState.Disconnected;
    }

    public String getOpponent(String player)
    {
        return player == player1 ? player2 : player1;
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

    public PlayerState getState(String player) {
        return player == player1 ? statePlayer1 : statePlayer2;
    }

    public void setState(String player, PlayerState state) {
        if(player == player1) statePlayer1 = state;
        else statePlayer2 = state;
    }
}
