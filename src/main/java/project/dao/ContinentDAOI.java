package project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.entity.Continent;
import project.exception.IdNotFound;
import project.util.HibernateUtil;

import java.util.List;
import java.util.Set;

public class ContinentDAOI implements EntityDAO<Continent, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContinentDAOI.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session session;
    private Transaction transaction;

    @Override
    public void create(Continent continent) {
        LOGGER.info("Creating continent: {}", continent);
        openSessionAndTransaction();
        session.persist(continent);
        closeSessionAndTransaction();
        LOGGER.info("Continent {} was created.", continent);
    }

    @Override
    public void createAll(Set<Continent> continents) {
        LOGGER.info("Creating continents: {}", continents);
        openSessionAndTransaction();
        continents.forEach(continent -> session.persist(continent));
        closeSessionAndTransaction();
        LOGGER.info("Continents {} was created.", continents);
    }

    @Override
    public Continent createAndReturn(Continent continent) {
        LOGGER.info("Creating continent: {}.", continent);
        openSessionAndTransaction();
        int id = (int) session.save(continent);
        closeSessionAndTransaction();
        continent.setId(id);
        LOGGER.info("Continent {} was created.", continent);
        return continent;
    }

    @Override
    public List<Continent> getAll() {
        LOGGER.info("Getting all continents: ");
        Session session = sessionFactory.openSession();
        String hql = "from Continent";
        Query query = session.createQuery(hql);
        List<Continent> continents = query.list();
        session.close();
        LOGGER.info("Found continents: {} .", continents);
        return continents;
    }

    @Override
    public Continent getById(int id) throws IdNotFound {
        LOGGER.info("Getting continents with id: {}", id);
        session = sessionFactory.openSession();
        Continent continent = session.get(Continent.class, id);
        session.close();
        if (continent == null)
            throw new IdNotFound("Continent id not found!");
        else
            LOGGER.info("Found continent: {}", continent);
        return continent;
    }

    @Override
    public Continent update(Continent continent) {
        LOGGER.info("Updating continent: {}", continent);
        openSessionAndTransaction();
        session.merge(continent);
        closeSessionAndTransaction();
        return continent;
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting continent with id: {}", id);
        try {
            final Continent continentId = getById(id);
            openSessionAndTransaction();
            session.remove(continentId);
        } catch (IdNotFound e) {
            e.printStackTrace();
            LOGGER.error("Continent id not found!");
        }
        closeSessionAndTransaction();
    }

    @Override
    public void deleteAll() {
        openSessionAndTransaction();
        List<Continent> continents = getAll();
        continents.forEach(continent -> session.remove(continent));
        closeSessionAndTransaction();
    }

    private void openSessionAndTransaction() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    private void closeSessionAndTransaction() {
        transaction.commit();
        session.close();
    }
}
