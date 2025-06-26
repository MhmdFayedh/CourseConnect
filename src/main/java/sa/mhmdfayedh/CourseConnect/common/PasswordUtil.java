package sa.mhmdfayedh.CourseConnect.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hash(String password){
        return encoder.encode(password);
    }

    public static boolean verify(String rawPassword, String hashedPassword){
        return encoder.matches(rawPassword, hashedPassword);
    }
}
