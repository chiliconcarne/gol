package GameOfLife.example.repository;

import GameOfLife.example.entity.User;
import org.springframework.data.repository.*;
import org.springframework.data.domain.*;

/**
 * Created by sernowm on 04.08.2016.
 */
public interface IUserRepository extends Repository<User,Integer> {
    Page<User> findAll(Pageable pageable);
    User findByUsername(String username);
}
