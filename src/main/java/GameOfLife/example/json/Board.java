package GameOfLife.example.json;

import java.util.Random;

/**
 * Created by sernowm on 10.08.2016.
 */
public class Board {
    private int[][] board = new int[30][30];
    private String spieler1;
    public Board(){
        Random rnd = new Random();
        for(int y = 0; y < 30; y++){
            for(int x = 0; x < 30; x++){
                board[y][x]=rnd.nextInt(8);
            }
        }
        spieler1 = "kai";
    }
    public Board(String spieler){
        Random rnd = new Random();
        for(int y = 0; y < 30; y++){
            for(int x = 0; x < 30; x++){
                board[y][x]=rnd.nextInt(8);
            }
        }
        spieler1 = spieler;
    }
    public int[][] getBoard() {
        return board;
    }
    public void setBoard(int[][] board) {
        this.board = board;
    }
    public String getSpieler1(){
        return this.spieler1;
    }
}