package project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.entity.Country;
import project.exception.IdNotFound;
import project.util.HibernateUtil;

import java.util.List;

public class CountryDAOImpl implements EntityDAO<Country, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDAOImpl.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void create(Country country) {
        LOGGER.info("Creating country: {}", country);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(country);
                transaction.commit();
                LOGGER.info("Country created: {}", country);
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating country: {}", e.getMessage(), e);
                throw new RuntimeException("Error creating country", e);
            }
        }
    }

    @Override
    public void createAll(List<Country> countries) {
        LOGGER.info("Creating countries: {}", countries);

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                countries.forEach(session::persist);
                transaction.commit();
                LOGGER.info("Countries {} were created.", countries);
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating countries: " + e.getMessage());
                throw new RuntimeException("Error creating countries", e);
            }
        }
    }

    @Override
    public Country createAndReturn(Country country) {
        LOGGER.info("Creating country: {}", country);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(country);
                transaction.commit();
                LOGGER.info("Country {} with ID {} was created.", country, country.getId());
                return country;
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating country: " + e.getMessage());
                throw new RuntimeException("Error creating country", e);
            }
        }
    }

    @Override
    public List<Country> getAll() {
        LOGGER.info("Getting all countries: ");
        try (Session session = sessionFactory.openSession()) {
            List<Country> countries = session.createQuery("from Country", Country.class).getResultList();
            LOGGER.info("Found countries: {} ", countries);
            return countries;
        } catch (Exception e) {
            LOGGER.error("Error getting all countries: {} ", e.getMessage(), e);
            throw new RuntimeException("Error getting all countries!", e);
        }
    }

    @Override
    public Country getById(int id) throws IdNotFound {
        LOGGER.info("Getting country with id: {}", id);

        try (Session session = sessionFactory.openSession()) {
            Country country = session.get(Country.class, id);

            if (country == null) {
                LOGGER.error("Country with id {} not found.", id);
                throw new IdNotFound("Country id not found!");
            }
            LOGGER.info("Found country: {}", country);
            return country;

        } catch (Exception e) {
            LOGGER.error("Error getting country with id {}: {} ", id, e.getMessage(), e);
            throw new RuntimeException("Error getting country with id " + id, e);
        }
    }

    @Override
    public Country update(Country country) {
        LOGGER.info("Updating country: {}", country);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(country);
                transaction.commit();
                LOGGER.info("Country updated: {}", country);
                return country;
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error updating country: {}", e.getMessage(), e);
                throw new RuntimeException("Error updating country", e);
            }

        } catch (Exception e) {
            LOGGER.error("Session failed: {}", e.getMessage(), e);
            throw new RuntimeException("Session operation failed", e);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting country with id: {} ", id);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Country country = session.get(Country.class, id);
                if (country != null) {
                    session.remove(country);
                    transaction.commit();
                    LOGGER.info("Country with id {} deleted.", id);
                } else {
                    LOGGER.error("Country id {} not found!", id);
                    throw new IdNotFound("Country id not found!");
                }
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error deleting country: {}", e.getMessage(), e);
                throw new RuntimeException("Error deleting country", e);
            }

        } catch (IdNotFound e) {
            LOGGER.error("Country id not found: {}", e.getMessage(), e);
        }
    }

    @Override
    public void deleteAll() {
        LOGGER.info("Deleting all countries. ");

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                List<Country> countries = session.createQuery("from Country", Country.class).getResultList();
                countries.forEach(session::remove);
                transaction.commit();
                LOGGER.info("All countries have been deleted.");
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error deleting all countries: {}", e.getMessage(), e);
                throw new RuntimeException("Error during delete all countries", e);
            }

        }
    }
}