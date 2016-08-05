package GameOfLife.example.repository;

import GameOfLife.example.entity.Profil;
import GameOfLife.example.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by sernowm on 04.08.2016.
 */
public interface ProfilRepository extends CrudRepository<Profil, Integer> { }