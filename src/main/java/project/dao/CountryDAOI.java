package project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.entity.Country;
import project.exception.IdNotFound;
import project.util.HibernateUtil;

import java.util.List;
import java.util.Set;

public class CountryDAOI implements EntityDAO<Country, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDAOI.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private Session session;
    private Transaction transaction;

    @Override
    public void create(Country country) {
        LOGGER.info("Creating country: {}", country);
        openSessionAndTransaction();
        session.persist(country);
        closeSessionAndTransaction();
        LOGGER.info("Country {} was created.", country);
    }

    @Override
    public void createAll(Set<Country> countries) {
        LOGGER.info("Creating countries: {}", countries);
        openSessionAndTransaction();
        countries.forEach(country -> session.persist(country));
        closeSessionAndTransaction();
        LOGGER.info("Countries {} was created.", countries);
    }

    @Override
    public Country createAndReturn(Country country) {
        LOGGER.info("Creating country: {}.", country);
        openSessionAndTransaction();
        int id = (int) session.save(country);
        closeSessionAndTransaction();
        country.setId(id);
        LOGGER.info("Country {} was created.", country);
        return country;
    }

    @Override
    public List<Country> getAll() {
        LOGGER.info("Getting all countries: ");
        Session session = sessionFactory.openSession();
        String hql = "from Country";
        Query query = session.createQuery(hql);
        List<Country> countries = query.list();
        session.close();
        LOGGER.info("Found countries: {} .", countries);
        return countries;
    }

    @Override
    public Country getById(int id) throws IdNotFound {
        LOGGER.info("Getting countries with id: {}", id);
        session = sessionFactory.openSession();
        Country country = session.get(Country.class, id);
        session.close();
        if (country == null)
            throw new IdNotFound("Country id not found!");
        else
            LOGGER.info("Found country: {}", country);
        return country;
    }

    @Override
    public Country update(Country country) {
        LOGGER.info("Updating country: {}", country);
        openSessionAndTransaction();
        session.merge(country);
        closeSessionAndTransaction();
        return country;
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting country with id: {}", id);
        try {
            final Country countryId = getById(id);
            openSessionAndTransaction();
            session.remove(countryId);
        } catch (IdNotFound e) {
            e.printStackTrace();
            LOGGER.error("Country id not found!");
        }
        closeSessionAndTransaction();
    }

    @Override
    public void deleteAll() {
        openSessionAndTransaction();
        List<Country> countries = getAll();
        countries.forEach(country -> session.remove(country));
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
