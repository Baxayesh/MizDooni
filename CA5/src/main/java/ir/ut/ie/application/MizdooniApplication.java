package ir.ut.ie.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = "ir.ut.ie")
@EntityScan(basePackages = {"ir.ut.ie.models"})
public class MizdooniApplication {

	static final String FRONTEND_SERVER_ORIGIN = "http://localhost:3000";


	public static void main(String[] args) {

		SpringApplication.run(MizdooniApplication.class, args);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(FRONTEND_SERVER_ORIGIN);
			}
		};
	}
}
