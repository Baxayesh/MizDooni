package ir.ut.ie.application;

import co.elastic.apm.attach.ElasticApmAttacher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ir.ut.ie")
@EntityScan(basePackages = {"ir.ut.ie.models"})
public class MizdooniApplication {

	public static void main(String[] args) {
		ElasticApmAttacher.attach();
		SpringApplication.run(MizdooniApplication.class, args);

	}
}


