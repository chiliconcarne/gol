package GameOfLife.MVC.model.Entity;

import GameOfLife.MVC.controller.Enum.Color;
import GameOfLife.MVC.controller.Enum.GameType;

import javax.persistence.*;

/**
 * Created by kulandas on 22.08.2016.
 */
@Entity
public class Player {
    @Id
    @GeneratedValue
    private int playerId;
    private String name;
    private Color color1;
    private Color color2;
    private int win;
    private int draw;
    private int lost;
    private int img_width;
    private int img_height;
    private int board_width;
    private int board_height;
    private int winCondition;
    private GameType gameType;
    private String avatar;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "player")
    private GamePlayer gamePlayer;

    public Player() {}

    public Player(String name) {
        this.playerId = 0;
        this.name = name;
        this.color1 = Color.blue;
        this.color2 = Color.green;
        this.win = 0;
        this.draw = 0;
        this.lost = 0;
        this.winCondition = 50;
        this.board_height = 67;
        this.board_width = 90;
        this.gameType = GameType.duel;
        this.gamePlayer = null;
    }

    public int getImg_width() {
        return img_width;
    }

    public void setImg_width(int img_width) {
        this.img_width = img_width;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getImg_height() {
        return img_height;
    }

    public void setImg_height(int img_height) {
        this.img_height = img_height;
    }

    public int getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public int getBoard_width() {
        return board_width;
    }

    public void setBoard_width(int board_width) {
        this.board_width = board_width;
    }

    public int getBoard_height() {
        return board_height;
    }

    public void setBoard_height(int board_height) {
        this.board_height = board_height;
    }
}
