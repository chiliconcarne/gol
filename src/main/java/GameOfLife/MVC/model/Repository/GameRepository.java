package GameOfLife.MVC.model.Repository;

import GameOfLife.MVC.model.Entity.Game;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kulandas on 23.08.2016.
 */
public interface GameRepository extends CrudRepository<Game,Integer> {
    public Game findOneByPlayer(String player);
    public Game findOneByRoom(String room);
}
