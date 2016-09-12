package GameOfLife.MVC.controller.Json;

import GameOfLife.MVC.model.Entity.Team;

import java.util.List;

/**
 * Created by kulandas on 02.09.2016.
 */
public class GameLobbyUpdates {
    private List<Team>teamMemeberList;

    public List<Team> getTeamMemeberList() {
        return teamMemeberList;
    }

    public void setTeamMemeberList(List<Team> teamMemeberList) {
        this.teamMemeberList = teamMemeberList;
    }

    public GameLobbyUpdates(List<Team> teamMemeberList) {
        this.teamMemeberList = teamMemeberList;
    }

    public GameLobbyUpdates()
    {

    }
}
