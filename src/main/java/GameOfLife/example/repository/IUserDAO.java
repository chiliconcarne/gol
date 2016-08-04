package GameOfLife.example.repository;

import GameOfLife.example.entity.User;

import java.util.List;

/**
 * Created by sernowm on 04.08.2016.
 */
public interface IUserDAO {
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    List<User> findAllUser();
    User findUser(Integer id);
}
