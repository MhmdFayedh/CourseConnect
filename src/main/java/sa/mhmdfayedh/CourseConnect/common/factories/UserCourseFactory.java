package sa.mhmdfayedh.CourseConnect.common.factories;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.CourseDAO;
import sa.mhmdfayedh.CourseConnect.repositories.interfaces.UserDAO;

import java.util.Locale;
import java.util.Random;

@Component
public class UserCourseFactory implements CommandLineRunner {
    private final UserDAO userDAO;
    private final CourseDAO courseDAO;
    private final Faker faker = new Faker(new Locale("en"));
    private final Random random = new Random();



    public UserCourseFactory(UserDAO userDAO, CourseDAO courseDAO){
        this.userDAO = userDAO;
        this.courseDAO = courseDAO;
    }


    @Override
    public void run(String... args) throws Exception {
//
//        Role randomRole = random.nextBoolean() ? Role.ROLE_INSTRUCTOR : Role.ROLE_STUDENT;
//        Gender randomGender = random.nextBoolean() ? Gender.FEMALE : Gender.MALE;
//        for (int i = 0; i < 1000; i++){
//            User user = new User();
//
//            String hashedPassword = PasswordUtil.hash(faker.internet().password());
//
//            user.setUsername(faker.name().username());
//            user.setEmail(faker.internet().emailAddress());
//            user.setPassword("{bcrypt}" + hashedPassword);
//            user.setFirstName(faker.name().firstName());
//            user.setLastName(faker.name().lastName());
//            user.setRole(randomRole);
//            user.setBackground(faker.lorem().paragraph());
//            user.setGender(randomGender);
//            user.setDateOfBirth(LocalDate.of(1999, 10, 28));
//
//            userDAO.save(user);
//
//        }
//
//        System.out.println("✅ Seeded 1000 users.");

    }
}
