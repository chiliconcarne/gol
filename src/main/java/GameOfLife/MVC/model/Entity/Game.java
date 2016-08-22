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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "game")
    private Settings settings;

    @OneToMany(mappedBy = "game")
    private List<Team> tams;

    public Game() {
    }

    public Game(int gameId, int round, GameState gameState, Settings settings) {
        this.gameId = gameId;
        this.round = round;
        this.gameState = gameState;
        this.settings = settings;
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
}
