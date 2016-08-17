package GameOfLife.example.entity;

import GameOfLife.example.state.CellState;
import GameOfLife.example.state.PlayerState;

import java.io.Serializable;

/**
 * Created by sernowm on 17.08.2016.
 */
public class Player implements Serializable {
    private String name;
    private int color,points=0,cells=0,lager=0,energy=0;
    private PlayerState playerState=PlayerState.Disconnected;
    private CellState cellState;

    public Player(Game g, String name, int color, CellState cellState) {
        this.name = name;
        this.color = color;
        this.cellState = cellState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getCells() {
        return cells;
    }

    public void setCells(int cells) {
        this.cells = cells;
    }

    public void addCells(int cells) {
       this.cells += cells;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public Player getOpponent(Game game) {
        return game.getOpponent(this);
    }

    public int getLager() {
        return lager;
    }

    public void setLager(int lager) {
        this.lager = lager;
    }

    public void addLager(int lager) { this.lager += lager; }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addEnergy(int energy) { this.energy += energy; }

    public String toString() {
        return this.getName();
    }
}
