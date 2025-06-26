package sa.mhmdfayedh.CourseConnect.repositories.interfaces;

import sa.mhmdfayedh.CourseConnect.entities.Gender;

public interface GenderDAO {
    Gender findGenderById(int id);
}
