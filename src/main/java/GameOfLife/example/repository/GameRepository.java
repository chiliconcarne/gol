package GameOfLife.example.repository;

import GameOfLife.example.entity.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by sernowm on 10.08.2016.
 */
public interface GameRepository extends CrudRepository<Game, Integer> {
}
