package GameOfLife.example.logik;

import GameOfLife.example.entity.Game;

/**
 * Created by sernowm on 11.08.2016.
 */
public class CheckCell {
    private int neutral, player1, player2;
    private Game g;

    public CheckCell(Game g, int x, int y) {
        this.g = g;

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

    public int getNeutral() {
        return neutral;
    }

    public int getEnemy(String player) {
        return player == g.getPlayer1() ? player2 : player1;
    }

    public int getFriendly(String player) {
        return player == g.getPlayer1() ? player1 : player2;
    }
}
