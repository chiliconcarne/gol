package GameOfLife.MVC.model.Repository;

import GameOfLife.MVC.model.Entity.Team;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kulandas on 23.08.2016.
 */
public interface TeamRepository extends CrudRepository<Team,Integer> {
    public Team findOneByTeamNameAndGame(String teamName,int gameID);
}
