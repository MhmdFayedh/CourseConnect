package sa.mhmdfayedh.CourseConnect.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Retrieve user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, is_active AS enabled FROM users where username = ?");

        // Retrieve roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, role AS authority FROM users where username = ?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("INSTRUCTOR", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("INSTRUCTOR", "STUDENT")
                        .requestMatchers(HttpMethod.PUT, "/api/users").hasAnyRole("INSTRUCTOR", "STUDENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("INSTRUCTOR", "STUDENT")

                        .requestMatchers(HttpMethod.POST, "/api/courses").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/courses/*/students").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/courses").hasAnyRole("INSTRUCTOR", "STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/courses/**").hasAnyRole("INSTRUCTOR", "STUDENT")
                        .requestMatchers(HttpMethod.PUT, "/api/courses").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasRole("INSTRUCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/courses/register").hasRole("STUDENT")


        );

        httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }

}
