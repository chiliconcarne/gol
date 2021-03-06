package GameOfLife.MVC.model.Repository;

import GameOfLife.MVC.model.Entity.Offer;
import GameOfLife.MVC.model.Entity.Player;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kulandas on 23.08.2016.
 */
public interface OfferRepository extends CrudRepository<Offer, Integer> {
    public Offer findOneByOfferGenerator(String offerGenerator);
}
