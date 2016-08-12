package GameOfLife.example.json;

import GameOfLife.example.entity.Game;

import java.util.Random;

/**
 * Created by sernowm on 10.08.2016.
 */
public class Board {
    private Game g;

    public Board(Game g) {
        this.g = g;
    }

    public Game getG() {
        return g;
    }

    public void setG(Game g) {
        this.g = g;
    }
}