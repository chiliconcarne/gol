package GameOfLife.example.repository;



import GameOfLife.example.entity.Game;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sernowm on 10.08.2016.
 */
public interface GameRepository extends CrudRepository<Game, Integer> {
    Game findOneByN1OrN2(String Player1, String Player2);
}
