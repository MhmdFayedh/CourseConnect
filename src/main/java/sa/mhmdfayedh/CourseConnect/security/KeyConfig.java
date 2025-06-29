package sa.mhmdfayedh.CourseConnect.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
public class KeyConfig {


    @Value("${app.api.config.secret.key}")
    private String key;

    @Bean
    public Key key() {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

}
