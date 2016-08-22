package GameOfLife.MVC.model.Entity;

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
    private int user_id;
    private int color1;
    private int color2;
    private int win;
    private int draw;
    private int lost;
    private int img_width;
    private int img_height;
    private int winCondition;
    private GameType gameType;
    private String avatar;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "player")
    private GamePlayer gamePlayer;

    public Player() {}

    public Player(int playerId, int user_id, int color1, int color2, int win, int draw, int lost, int img_width, int img_height, int winCondition, GameType gameType, String avatar, GamePlayer gamePlayer) {
        this.playerId = playerId;
        this.user_id = user_id;
        this.color1 = color1;
        this.color2 = color2;
        this.win = win;
        this.draw = draw;
        this.lost = lost;
        this.img_width = img_width;
        this.img_height = img_height;
        this.winCondition = winCondition;
        this.gameType = gameType;
        this.avatar = avatar;
        this.gamePlayer = gamePlayer;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getColor1() {
        return color1;
    }

    public void setColor1(int color1) {
        this.color1 = color1;
    }

    public int getColor2() {
        return color2;
    }

    public void setColor2(int color2) {
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
}
