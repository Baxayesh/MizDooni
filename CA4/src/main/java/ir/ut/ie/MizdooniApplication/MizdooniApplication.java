package ir.ut.ie.MizdooniApplication;

import ir.ut.ie.service.MizdooniProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ir.ut.ie.controllers")
public class MizdooniApplication {

	public static void main(String[] args) {

		MizdooniProvider.ReloadInstance();
		SpringApplication.run(MizdooniApplication.class, args);

	}

}
