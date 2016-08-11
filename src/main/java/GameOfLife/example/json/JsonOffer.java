package GameOfLife.example.json;

import GameOfLife.example.logik.OfferState;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by raedschk on 11.08.2016.
 */
public class JsonOffer {
    private String username;
    private String opponent;
    private OfferState state;

    public JsonOffer(String username, String opponent, OfferState state) {
        this.username = username;
        this.opponent = opponent;
        this.state = state;
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
}
