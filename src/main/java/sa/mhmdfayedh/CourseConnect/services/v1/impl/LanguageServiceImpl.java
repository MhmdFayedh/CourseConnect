package sa.mhmdfayedh.CourseConnect.services.v1.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sa.mhmdfayedh.CourseConnect.entities.Language;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.LanguageDAO;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {
    LanguageDAO languageDAO;

    @Autowired
    public LanguageServiceImpl(LanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    @Cacheable("languageCache")
    public Language findLanguageById(int id) {
        return languageDAO.findLanguageById(id);
    }
}
