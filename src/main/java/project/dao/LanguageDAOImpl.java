package project.dao;

import project.entity.Language;

public class LanguageDAOImpl extends EntityDAOImpl<Language, Long> implements LanguageDAO {

    public LanguageDAOImpl(Class<Language> entityType) {
        super(Language.class);
    }
}