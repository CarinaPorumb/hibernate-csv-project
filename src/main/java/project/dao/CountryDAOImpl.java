package project.dao;

import project.entity.Country;

public class CountryDAOImpl extends EntityDAOImpl<Country, Long> implements CountryDAO {

    public CountryDAOImpl(Class<Country> entityType) {
        super(Country.class);
    }

}
