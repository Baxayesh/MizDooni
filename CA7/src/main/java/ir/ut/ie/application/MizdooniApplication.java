package ir.ut.ie.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ir.ut.ie")
@EntityScan(basePackages = {"ir.ut.ie.models"})
public class MizdooniApplication {

	public static void main(String[] args) {

		SpringApplication.run(MizdooniApplication.class, args);

	}
}


