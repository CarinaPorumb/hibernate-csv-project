package project.dao;

import project.entity.Continent;

public class ContinentDAOImpl extends EntityDAOImpl<Continent, Long> implements ContinentDAO {

    public ContinentDAOImpl(Class<Continent> entityType) {
        super(Continent.class);
    }
}