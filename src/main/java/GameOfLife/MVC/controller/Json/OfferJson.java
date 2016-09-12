package GameOfLife.MVC.controller.Json;

import GameOfLife.MVC.controller.Enum.GameType;
import GameOfLife.MVC.controller.Enum.OfferState;
import GameOfLife.MVC.model.Entity.Offer;
import GameOfLife.MVC.model.Entity.Player;

/**
 * Created by sernowm on 30.08.2016.
 */
public class OfferJson {
    private int win;
    private int draw;
    private int lost;
    private String owner;
    private String image;
    private OfferState offerState;
    private int boardWidth;
    private int boardHeight;
    private int winCondition;
    private GameType gameType;
    private String gameTypeIcon;
    private String room;

    public OfferJson(Player player, Offer offer) {
        this.win = player.getWin();
        this.draw = player.getDraw();
        this.lost = player.getLost();
        this.owner = offer.getOfferGenerator();
        if(player.getAvatar()==null||player.getAvatar().equals(""))
            this.image = "warten.jpg";
        else
            this.image = player.getAvatar();
        this.offerState = offer.getOfferState();
        this.boardWidth = offer.getSettings().getBoardWidth();
        this.boardHeight = offer.getSettings().getBoardHeight();
        this.winCondition = offer.getSettings().getWinCondition();
        this.gameType = offer.getSettings().getGameType();
        switch(this.gameType){
            case duel:
                this.gameTypeIcon = "knight";
                break;
            case teamDuel:
                this.gameTypeIcon = "tower";
                break;
        }
        this.room = offer.getSettings().getRoom();

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

    public OfferState getOfferState() {
        return offerState;
    }

    public void setOfferState(OfferState offerState) {
        this.offerState = offerState;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGameTypeIcon() {
        return gameTypeIcon;
    }

    public void setGameTypeIcon(String gameTypeIcon) {
        this.gameTypeIcon = gameTypeIcon;
    }
}
