package GameOfLife.example.json;

/**
 * Created by sernowm on 10.08.2016.
 */
public class Board {
    private int[][] board = new int[30][30];
    public Board(){
        for(int y = 0; y < 30; y++){
            for(int x = 0; x < 30; x++){
                board[y][x]=0;
            }
        }
    }
    public int[][] getBoard() {
        return board;
    }
    public void setBoard(int[][] board) {
        this.board = board;
    }
}
