package sa.mhmdfayedh.CourseConnect.services.v1.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sa.mhmdfayedh.CourseConnect.entities.DifficultyLevel;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.DifficultyLevelDAO;
import sa.mhmdfayedh.CourseConnect.services.v1.interfaces.DifficultyLevelService;

@Service
public class DifficultyLevelServiceImpl implements DifficultyLevelService {
    DifficultyLevelDAO difficultyLevelDAO;

    public DifficultyLevelServiceImpl(DifficultyLevelDAO difficultyLevelDAO) {
        this.difficultyLevelDAO = difficultyLevelDAO;
    }

    @Cacheable("DifficultyLevelCache")
    public DifficultyLevel findDifficultyLevelById(int id) {
        return difficultyLevelDAO.findDifficultyLevelById(id);
    }
}
