package com.alejobeliz.proyectos.literatura;

import com.alejobeliz.proyectos.literatura.principal.Principal;
import com.alejobeliz.proyectos.literatura.repository.AutorRepository;
import com.alejobeliz.proyectos.literatura.repository.LibroRepository;
import com.alejobeliz.proyectos.literatura.service.AutorService;
import com.alejobeliz.proyectos.literatura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Autowired
	private LibroService libroService;
	@Autowired
	private AutorService autorService;

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroService,autorService);
		principal.EjecutarAplicacion();
	}
}
