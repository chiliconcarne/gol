package GameOfLife.example.json;

import GameOfLife.example.logik.OfferState;

/**
 * Created by raedschk on 11.08.2016.
 */
public class JsonOffer {
    private String username;
    private String opponent;
    private OfferState state;
    private int width, height;
    private int winCondition;

    public JsonOffer(String username, String opponent, OfferState state, int width, int height, int winCondition) {
        this.username = username;
        this.opponent = opponent;
        this.state = state;
        this.width = width;
        this.height = height;
        this.winCondition = winCondition;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public OfferState getState() {
        return state;
    }

    public void setState(OfferState state) {
        this.state = state;
    }

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

    public int getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }
}
