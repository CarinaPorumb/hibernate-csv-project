package project.dao;

import project.entity.City;

public class CityDAOImpl extends EntityDAOImpl<City, Long> implements CityDAO {

    public CityDAOImpl(Class<City> entityType) {
        super(City.class);
    }
}