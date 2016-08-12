package GameOfLife.example.logik.listener;

import GameOfLife.example.entity.Game;
import GameOfLife.example.logik.Cell;
import GameOfLife.example.state.SourceState;

/**
 * Created by sernowm on 12.08.2016.
 */
public class CellEvent {
    private Cell cell;
    private SourceState source;
    private SourceState target;
    private Game game;
    private int[][] board;

    public CellEvent(Cell cell, SourceState source) {
        this.cell = cell;
        this.source = source;
        this.game = cell.getGame();
        this.board = game.getBoard();
    }

    public Cell getCell() {
        return cell;
    }

    public SourceState getSource() {
        return source;
    }

    public Game getGame() {
        return game;
    }

    public int[][] getBoard() {
        return board;
    }

    public SourceState getTarget() {
        return target;
    }

    public void setTarget(SourceState target) {
        this.target = target;
    }
}
