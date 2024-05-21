package project.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.util.HibernateUtil;

import java.util.List;

@Slf4j
public class EntityDAOImpl<E, I> implements EntityDAO<E, I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityDAOImpl.class);
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    private final Class<E> entityType;

    public EntityDAOImpl(Class<E> entityType) {
        this.entityType = entityType;
    }

    @Override
    public void create(E entity) {
        LOGGER.info("Creating entity: {}", entity);

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                LOGGER.info("Successfully created entity: {}", entity);
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating entity: {}", entity, e);
                throw new RuntimeException("Error creating entity: " + entity, e);
            }
        }
    }

    @Override
    public void createAll(List<E> entities) {
        LOGGER.info("Creating entities: {}", entities);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                entities.forEach(session::persist);
                transaction.commit();
                LOGGER.info("Successfully created entities: {}", entities);
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating entities: {}", entities, e);
                throw new RuntimeException("Error creating entities: " + entities, e);
            }
        }
    }

    @Override
    public E createAndReturn(E entity) {
        LOGGER.info("Creating entity: {}", entity);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                LOGGER.info("Successfully created entity: {}", entity);
                return entity;
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error creating entity: {}", entity, e);
                throw new RuntimeException("Error creating entity: " + entity, e);
            }
        }
    }

    @Override
    public List<E> getAll() {
        LOGGER.info("Retrieving all entities");
        try (Session session = sessionFactory.openSession()) {
            List<E> entities = session.createQuery("from " + entityType.getName(), entityType).getResultList();
            LOGGER.info("Found entities: {}", entities);
            return entities;
        } catch (Exception e) {
            LOGGER.error("Error getting all entities: {}", e.getMessage(), e);
            throw new RuntimeException("Error getting all entities: " + e.getMessage(), e);
        }
    }

    @Override
    public E getById(int id) throws Exception {
        LOGGER.info("Retrieving entity by id: {}", id);
        try (Session session = sessionFactory.openSession()) {
            E entity = session.get(entityType, id);
            if (entity == null) {
                LOGGER.error("Entity with id {} not found.", id);
                throw new Exception("Entity id not found!");
            }
            LOGGER.info("Successfully retrieved entity: {}", entity);
            return entity;
        } catch (RuntimeException e) {
            LOGGER.error("Error getting entity by id: {}", id, e);
            throw new RuntimeException("Error getting entity by id: " + id, e);
        }
    }

    @Override
    public E update(E entity) {
        LOGGER.info("Updating entity: {}", entity);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(entity);
                transaction.commit();
                LOGGER.info("Successfully updated entity: {}", entity);
                return entity;
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error updating entity: {}", entity, e);
                throw new RuntimeException("Error updating entity: " + entity, e);
            }
        } catch (Exception e) {
            LOGGER.error("Error updating entity: {}", entity, e);
            throw new RuntimeException("Error updating entity: " + entity, e);
        }
    }

    @Override
    public void delete(int id) {
        LOGGER.info("Deleting entity by id: {}", id);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                E entity = session.get(entityType, id);
                if (entity != null) {
                    session.remove(entity);
                    transaction.commit();
                    LOGGER.info("Successfully deleted entity: {}", entity);
                } else {
                    LOGGER.error("Entity with id {} not found.", id);
                    throw new RuntimeException("Entity with id " + id + " not found!");
                }
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error deleting entity: {}", id, e);
                throw new RuntimeException("Error deleting entity: " + id, e);
            }
        }
    }

    @Override
    public void deleteAll() {
        LOGGER.info("Deleting all entities");
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createMutationQuery("delete from " + entityType.getName()).executeUpdate();
                transaction.commit();
                LOGGER.info("Successfully deleted all entities");
            } catch (RuntimeException e) {
                transaction.rollback();
                LOGGER.error("Error deleting all entities", e);
                throw new RuntimeException("Error deleting all entities: " + e.getMessage(), e);
            }
        }
    }
}