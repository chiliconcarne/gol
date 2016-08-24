package GameOfLife.MVC.model.Repository;

import GameOfLife.MVC.model.Entity.Users;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sernowm on 24.08.2016.
 */
public interface UsersRepository extends CrudRepository<Users,String> {
}
