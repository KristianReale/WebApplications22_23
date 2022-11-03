package it.unical.mat.webapp23.catena_ristoranti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CatenaRistorantiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatenaRistorantiApplication.class, args);
	}

} 
