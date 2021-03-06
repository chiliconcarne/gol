package GameOfLife.MVC.model.Entity;

import GameOfLife.MVC.controller.Enum.Color;
import GameOfLife.MVC.controller.Enum.PlayerState;

import javax.persistence.*;

/**
 * Created by kulandas on 22.08.2016.
 */
@Entity
public class GamePlayer {
    @Id
    @GeneratedValue
    private int gamePlayerId;
    private int gameId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerId")
    private Player player;
    private int livingCells;
    private int clicks;
    private int killCellByRule;
    private int killCellByPlayer;
    private int convertCells;
    private int explodeCells;
    private int createCellByRule;
    private int createCellByPlayer;
    private PlayerState playerState;
    private Color color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamId")
    private Team team;

    public GamePlayer() {

    }

    public GamePlayer(Player player, Team team) {
        this.gamePlayerId = 0;
        this.gameId = team.getGame().getGameId();
        this.player = player;
        this.livingCells = 0;
        this.clicks = 0;
        this.killCellByRule = 0;
        this.killCellByPlayer = 0;
        this.convertCells = 0;
        this.explodeCells = 0;
        this.createCellByRule = 0;
        this.createCellByPlayer = 0;
        this.playerState = PlayerState.Disconnected;
        this.color = team.getColor();
        this.team = team;
    }

    public GamePlayer(int gameId, Player player, int livingCells, int clicks, int killCellByRule, int killCellByPlayer, int convertCells, int explodeCells, int createCellByRule, int createCellByPlayer, PlayerState playerState, Color color, Team team) {
        this.gamePlayerId = 0;
        this.gameId = gameId;
        this.player = player;
        this.livingCells = livingCells;
        this.clicks = clicks;
        this.killCellByRule = killCellByRule;
        this.killCellByPlayer = killCellByPlayer;
        this.convertCells = convertCells;
        this.explodeCells = explodeCells;
        this.createCellByRule = createCellByRule;
        this.createCellByPlayer = createCellByPlayer;
        this.playerState = playerState;
        this.color = color;
        this.team = team;
    }

    public int getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(int gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getLivingCells() {
        return livingCells;
    }

    public void setLivingCells(int livingCells) {
        this.livingCells = livingCells;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getKillCellByRule() {
        return killCellByRule;
    }

    public void setKillCellByRule(int killCellByRule) {
        this.killCellByRule = killCellByRule;
    }

    public int getKillCellByPlayer() {
        return killCellByPlayer;
    }

    public void setKillCellByPlayer(int killCellByPlayer) {
        this.killCellByPlayer = killCellByPlayer;
    }

    public int getConvertCells() {
        return convertCells;
    }

    public void setConvertCells(int convertCells) {
        this.convertCells = convertCells;
    }

    public int getExplodeCells() {
        return explodeCells;
    }

    public void setExplodeCells(int explodeCells) {
        this.explodeCells = explodeCells;
    }

    public int getCreateCellByRule() {
        return createCellByRule;
    }

    public void setCreateCellByRule(int createCellByRule) {
        this.createCellByRule = createCellByRule;
    }

    public int getCreateCellByPlayer() {
        return createCellByPlayer;
    }

    public void setCreateCellByPlayer(int createCellByPlayer) {
        this.createCellByPlayer = createCellByPlayer;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
