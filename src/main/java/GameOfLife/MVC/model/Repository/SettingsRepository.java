package GameOfLife.MVC.model.Repository;

import GameOfLife.MVC.model.Entity.Settings;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kulandas on 23.08.2016.
 */
public interface SettingsRepository extends CrudRepository<Settings,Integer> {
}
