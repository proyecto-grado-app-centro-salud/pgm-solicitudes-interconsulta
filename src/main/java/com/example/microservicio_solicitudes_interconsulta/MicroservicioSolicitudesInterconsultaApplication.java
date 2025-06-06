package com.example.microservicio_solicitudes_interconsulta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@SpringBootApplication
public class MicroservicioSolicitudesInterconsultaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioSolicitudesInterconsultaApplication.class, args);
	}

}
