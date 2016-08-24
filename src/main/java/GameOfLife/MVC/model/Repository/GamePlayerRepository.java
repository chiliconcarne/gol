package GameOfLife.MVC.model.Repository;

import GameOfLife.MVC.model.Entity.GamePlayer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kulandas on 23.08.2016.
 */
public interface GamePlayerRepository extends CrudRepository<GamePlayer,Integer> {

}
