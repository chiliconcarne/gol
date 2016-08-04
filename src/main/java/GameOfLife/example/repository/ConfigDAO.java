package GameOfLife.example.repository;

import GameOfLife.example.entity.Config;
import GameOfLife.example.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by sernowm on 04.08.2016.
 */
@Repository
public class ConfigDAO implements IConfigDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(Config config) {
        entityManager.persist(config);
    }

    @Override
    public void update(Config config) {
        entityManager.merge(config);
    }

    @Override
    public void delete(Config config) {
        entityManager.remove(config);
    }

    @Override
    public List<Config> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Config> cq = builder.createQuery(Config.class);
        Root<Config> root = cq.from(Config.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Config find(Config user) {
        return entityManager.find(Config.class,user);
    }
}
