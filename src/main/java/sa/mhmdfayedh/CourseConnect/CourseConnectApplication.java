package sa.mhmdfayedh.CourseConnect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sa.mhmdfayedh.CourseConnect.enums.Role;

@SpringBootApplication
public class CourseConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseConnectApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner() {

		return runner -> {

		};
	}
}
