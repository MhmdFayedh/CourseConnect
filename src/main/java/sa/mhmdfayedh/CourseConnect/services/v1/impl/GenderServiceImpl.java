package sa.mhmdfayedh.CourseConnect.services.v1.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sa.mhmdfayedh.CourseConnect.entities.Gender;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.GenderDAO;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.GenderService;

@Service
public class GenderServiceImpl implements GenderService {
    private GenderDAO genderDAO;

    public GenderServiceImpl(GenderDAO genderDAO) {
        this.genderDAO = genderDAO;
    }

    @Cacheable("genderCache")
    public Gender findGenderById(int id) {
        return genderDAO.findGenderById(id);
    }
}
