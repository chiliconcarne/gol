package GameOfLife.MVC.model.Entity;

import GameOfLife.MVC.controller.Enum.Color;
import GameOfLife.MVC.controller.Enum.GameType;

import javax.persistence.*;

/**
 * Created by kulandas on 22.08.2016.
 */
@Entity
public class Settings {
    @Id
    @GeneratedValue
    private int settingsId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settings")
    private Offer offer;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settings")
    private Game game;
    private int boardWidth;
    private int boardHeight;
    private int winCondition;
    private Color team1Color;
    private Color team2Color;
    private GameType gameType;

    public Settings() {
    }

    public Settings(int settingsId, Offer offer, Game game, int boardWidth, int boardHeight, int winCondition, Color team1Color, Color team2Color, GameType gameType) {
        this.settingsId = settingsId;
        this.offer = offer;
        this.game = game;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.winCondition = winCondition;
        this.team1Color = team1Color;
        this.team2Color = team2Color;
        this.gameType = gameType;
    }

    public int getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(int settingsId) {
        this.settingsId = settingsId;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

    public Color getTeam1Color() {
        return team1Color;
    }

    public void setTeam1Color(Color team1Color) {
        this.team1Color = team1Color;
    }

    public Color getTeam2Color() {
        return team2Color;
    }

    public void setTeam2Color(Color team2Color) {
        this.team2Color = team2Color;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
