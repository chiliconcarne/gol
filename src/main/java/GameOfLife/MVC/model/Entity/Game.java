package GameOfLife.MVC.model.Entity;

import GameOfLife.MVC.controller.Enum.GameState;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kulandas on 22.08.2016.
 */
@Entity
public class Game {
    @Id
    @GeneratedValue
    private int gameId;
    private int round;
    private GameState gameState;
    private String room;
    private String player;

    @OneToOne(mappedBy = "game")
    private Settings settings;

    @OneToMany(mappedBy = "game")
    private List<Team> tams;

    public Game() {
    }

    public Game(Settings settings) {
        this.gameId = 0;
        this.round = 0;
        this.gameState = GameState.Open;
        this.settings = settings;
        this.room = settings.getRoom();
        this.player = settings.getOffer().getOfferGenerator();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public List<Team> getTams() {
        return tams;
    }

    public void setTams(List<Team> tams) {
        this.tams = tams;
    }
}
