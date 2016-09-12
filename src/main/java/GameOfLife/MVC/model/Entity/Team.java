package GameOfLife.MVC.model.Entity;

import GameOfLife.MVC.controller.Enum.Color;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kulandas on 22.08.2016.
 */
@Entity
public class Team {
    @Id
    @GeneratedValue
    private int teamId;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "gameId")
    private Game game;

    @OneToMany(mappedBy = "team")
    private List<GamePlayer> gamePlayers;

    private Color color;
    private String teamName;

    public Team(Game game, Color color, String teamName) {
        this.teamId = 0;
        this.game = game;
        this.color = color;
        this.teamName = teamName;
    }

    public Team() {
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(List<GamePlayer> gamePlayers)
    {
        this.gamePlayers = gamePlayers;
    }

    public void addGamePlayer(GamePlayer gamePlayer)
    {
        this.gamePlayers.add(gamePlayer);

    }
}
