package GameOfLife.example.logik;

import GameOfLife.example.entity.Game;
import GameOfLife.example.state.CellState;

public class Cell {
    private int neutral, player1, player2;
    private CellState cellState;
    private int x,y;
    private Game g;

    public Cell(Game g, int x, int y) {
        this.g = g;
        this.x = x;
        this.y = y;
        this.cellState = g.getCellState(x,y);

        for(int dx = -1; dx <= 1; dx++) {
            for(int dy = -1; dy <= 1; dy++) {
                int nx = dx + x;
                int ny = dy + y;
                if (dy >= 0 && dx >= 0 && dy < g.getHeight() && dx < g.getWidth())
                {
                    CellState cellState = g.getCellState(nx, ny);
                    switch (cellState)
                    {
                        case player1: player1++; break;
                        case player2: player2++; break;
                    }
                }
            }
        }
        neutral = 9 - player1 - player2;
    }

    public Game getGame() { return g; }

    public CellState getType() { return cellState; }

    public int getNeutral() {
        return neutral;
    }

    public int getPlayer1() { return player1; }

    public int getPlayer2() { return player2; }

    public CellState getCellState() {
        return cellState;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFriendly() {
        switch(cellState){
            case player1:
                return player1;
            case player2:
                return player2;
            default:
                return 0;
        }
    }

    public int getEnemy() {
        switch(cellState){
            case player1:
                return player2;
            case player2:
                return player1;
            default:
                return 0;
        }
    }

    public boolean isNeutral() {
        return getCellState() == CellState.neutral;
    }

    public boolean isFriendly(String player) {
        return getCellState() == ( player.equals(g.getPlayer1()) ? CellState.player1 : CellState.player2 );
    }

    public boolean isEnemy(String player) {
        return getCellState() == ( player.equals(g.getPlayer1()) ? CellState.player2 : CellState.player1 );
    }

}
