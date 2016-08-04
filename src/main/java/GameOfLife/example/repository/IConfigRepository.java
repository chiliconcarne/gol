package GameOfLife.example.repository;

import GameOfLife.example.entity.Config;
import GameOfLife.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 * Created by sernowm on 04.08.2016.
 */
public interface IConfigRepository extends Repository<Config,User> {
    Page<Config> findAll(Pageable pageable);
    Config findByUser(User user);
}
