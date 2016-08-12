package GameOfLife.example.logik.listener;

import GameOfLife.example.entity.Game;
import GameOfLife.example.logik.Cell;
import GameOfLife.example.state.CellState;

/**
 * Created by sernowm on 12.08.2016.
 */
public class CellEvent {
    private Cell cell;
    private Game game;
    private CellState transform;
    private int[][] board;

    public CellEvent(Cell cell) {
        this.cell = cell;
        this.game = cell.getGame();
        this.board = game.getBoard();
        this.transform = cell.getType();
    }

    public Cell getCell() {
        return cell;
    }

    public Game getGame() {
        return game;
    }

    public int[][] getBoard() {
        return board;
    }

    public CellState getTransform() {
        return transform;
    }

    public void setTransform(CellState transform) {
        this.transform = transform;
    }
}
