package GameOfLife.example.repository;

import GameOfLife.example.entity.User;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by sernowm on 04.08.2016.
 */
public interface UserRepository extends Repository<User,Integer> {
    List<User> findAll();
    User findByName(String name);
}
