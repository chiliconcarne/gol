package GameOfLife.example.repository;

import GameOfLife.example.entity.Config;
import GameOfLife.example.entity.User;

import java.util.List;

/**
 * Created by sernowm on 04.08.2016.
 */
public interface IConfigDAO {
    void insert(Config config);
    void update(Config config);
    void delete(Config config);
    List<Config> findAll();
    Config find(Config config);
}
