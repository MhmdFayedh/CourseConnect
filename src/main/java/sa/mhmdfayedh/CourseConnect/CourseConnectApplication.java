package sa.mhmdfayedh.CourseConnect;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition(
		info = @Info(
				title = "CourseConnect API",
				version = "1.0.0",
				description = "API documentation for CourseConnect application"
		)
)
@SpringBootApplication
@EnableCaching
public class CourseConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseConnectApplication.class, args);
	}


}
