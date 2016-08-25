package GameOfLife.MVC.model.Repository;

import GameOfLife.MVC.model.Entity.Player;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kulandas on 23.08.2016.
 */
public interface PlayerRepository extends CrudRepository<Player,Integer> {
    public Player findOneByName(String name);
}
