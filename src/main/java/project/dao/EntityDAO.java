package project.dao;

import java.util.List;
import java.util.Set;

public interface EntityDAO <E, I>{

    void create(E e);

    void createAll(Set<E> e);

    E createAndReturn(E e);

    List<E> getAll();

    E getById(int id) throws Exception;

    E update(E e);

    void delete(int id);

    void deleteAll();

}