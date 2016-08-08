package GameOfLife.example.repository;

import GameOfLife.example.entity.Profil;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by sernowm on 04.08.2016.
 */
public interface ProfilRepository extends CrudRepository<Profil, String> { }