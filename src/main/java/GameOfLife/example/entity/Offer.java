package GameOfLife.example.entity;

import GameOfLife.example.state.OfferState;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by raedschk on 11.08.2016.
 */
@Entity
public class Offer {
    @Id
    private String username;
    private String opponent;
    private OfferState offerState;
    private int winCondition;
    private int boardHeight,boardWidth;
    private int colorPlayer1,colorPlayer2;

    public Offer() {
    }

    public Offer(String username, OfferState offerState, int winCondition, int boardHeight, int boardWidth, int colorPlayer1) {
        this.username = username;
        this.offerState = offerState;
        this.winCondition = winCondition;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.colorPlayer1 = colorPlayer1;
    }

    public Offer(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OfferState getOfferState() {
        return offerState;
    }

    public void setOfferState(OfferState offerState) {
        this.offerState = offerState;
    }

    public int getWinCondition() {
        return winCondition;
    }

    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public int getColorPlayer1() {
        return colorPlayer1;
    }

    public void setColorPlayer1(int colorPlayer1) {
        this.colorPlayer1 = colorPlayer1;
    }

    public int getColorPlayer2() {
        return colorPlayer2;
    }

    public void setColorPlayer2(int colorPlayer2) {
        this.colorPlayer2 = colorPlayer2;
    }

    public void setOpponent(Profil opponent){
        setOpponent(opponent.getUsername());
        setColorPlayer2(opponent.getColor1() == getColorPlayer1() ? opponent.getColor2() : opponent.getColor1());
    }
}
