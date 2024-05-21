package project.dao;

import project.entity.TouristAttraction;

public class TouristAttractionDAOImpl extends EntityDAOImpl<TouristAttraction, Long> implements TouristAttractionDAO {

    public TouristAttractionDAOImpl(Class<TouristAttraction> entityType) {
        super(TouristAttraction.class);
    }
}