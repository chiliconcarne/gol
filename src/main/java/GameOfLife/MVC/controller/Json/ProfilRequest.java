package GameOfLife.MVC.controller.Json;

import GameOfLife.MVC.controller.Enum.Color;
import GameOfLife.MVC.controller.Enum.GameType;

/**
 * Created by sernowm on 01.09.2016.
 */
public class ProfilRequest {
    private int width;
    private int height;
    private int win;
    private String GameType;
    private Color color1;
    private Color color2;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public String getGameType() {
        return GameType;
    }

    public GameOfLife.MVC.controller.Enum.GameType getGameT() {
        if(this.GameType=="duell") return GameOfLife.MVC.controller.Enum.GameType.duel;
        if(this.GameType=="teamduell") return GameOfLife.MVC.controller.Enum.GameType.teamDuel;
        return GameOfLife.MVC.controller.Enum.GameType.duel;
    }

    public void setGameType(String gameType) {
        GameType = gameType;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }
}
