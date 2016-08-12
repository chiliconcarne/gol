package GameOfLife.example.repository;

import GameOfLife.example.entity.Offer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by raeschk on 10.08.2016.
 */
public interface OfferRepository extends CrudRepository<Offer, String> {

}
