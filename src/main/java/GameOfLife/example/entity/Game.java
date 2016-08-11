package GameOfLife.example.entity;

import GameOfLife.example.logik.GamePhase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by sernowm on 10.08.2016.
 */
@Entity
public class Game {
    @Id
    int id;
    String spieler1,spieler2,winner;
    GamePhase phase;
    int ready=0;
    @Column(length = 16581375)
    private int[][] board = new int[30][30];

    public Game(){}

    public Game(int id, String spieler1, String spieler2,int[][] board) {
        this.id = id;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
        this.board=board;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpieler1() {
        return spieler1;
    }

    public void setSpieler1(String spieler1) {
        this.spieler1 = spieler1;
    }

    public String getSpieler2() {
        return spieler2;
    }

    public void setSpieler2(String spieler2) {
        this.spieler2 = spieler2;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    public int getReady() {
        return ready;
    }

    public void setReady(int ready) {
        this.ready = ready;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
