package com.alejobeliz.proyectos.literatura.principal;

import com.alejobeliz.proyectos.literatura.api.PeticionAPI;
import com.alejobeliz.proyectos.literatura.model.Autor;
import com.alejobeliz.proyectos.literatura.model.Idioma;
import com.alejobeliz.proyectos.literatura.model.Libro;
import com.alejobeliz.proyectos.literatura.model.LibroRecord;
import com.alejobeliz.proyectos.literatura.service.AutorService;
import com.alejobeliz.proyectos.literatura.service.LibroService;
import com.alejobeliz.proyectos.literatura.ui.Menu;
import com.alejobeliz.proyectos.literatura.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private PeticionAPI peticionAPI = new PeticionAPI();
    private Scanner sc = new Scanner(System.in);
    private LibroService libroService;
    private AutorService autorService;
    private Menu menu = new Menu();
    private JsonParser jsonParser = new JsonParser();

    @Autowired
    public Principal(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void EjecutarAplicacion() {
        Scanner teclado = new Scanner(System.in);
        int opcion = -1;
        System.out.println(menu.getBienvenida());
        while (opcion != 0) {
            System.out.println(menu.getMenu());
            opcion = teclado.nextInt();
            teclado.nextLine(); // Limpiar el buffer del teclado

            switch (opcion) {
                case 1:
                    guardarLibro();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo de literAlura...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
        teclado.close();
    }


    private void guardarLibro() {
        List<LibroRecord> librosObtenidos = obtenerLibrosApi();

        System.out.println("Escoja un libro para guardar");
        for (int i = 0; i < librosObtenidos.size(); i++) {
            System.out.println((i + 1) + " - " + librosObtenidos.get(i).titulo() + " - " + librosObtenidos.get(i).autores().get(0));
        }

        int opcion = sc.nextInt();
        sc.nextLine();

        if (opcion < 1 || opcion > librosObtenidos.size()) {
            System.out.println("Error: número erroneo");
            return;
        }

        LibroRecord libroRecord=librosObtenidos.get(opcion-1);
        Optional<Libro> libroTraidoDelRepo = libroService.obtenerLibroPorNombre(libroRecord.titulo());
        Optional<Autor> autorTraidodelRepo = autorService.obtenerAutorPorNombre(libroRecord.autores().get(0).nombre());

        if (libroTraidoDelRepo.isPresent()) {
            System.out.println("Error: no se puede registrar dos veces el mismo libro");
            return;
        }

        Libro libro = new Libro(libroRecord);

        if (!autorTraidodelRepo.isPresent()) {
            Autor autorNuevo = libro.getAutor();
            autorService.guardarAutor(autorNuevo);
        }

        libroService.guardarLibro(libro);
    }

    private List<LibroRecord> obtenerLibrosApi() {
        System.out.println("Ingrese el título del libro");
        String titulo = sc.nextLine();
        List<LibroRecord> librosObtenidos;
        librosObtenidos = jsonParser.parsearLibros(peticionAPI.obtenerDatos(titulo));
        return librosObtenidos;
    }


    private void listarLibrosRegistrados() {
        List<Libro> libros=libroService.obtenerTodosLosLibros();
        libros.forEach(libro -> libro.imprimirInformacion());
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorService.obtenerTodosLosAutores();
        autores.forEach(autor -> autor.imprimirInformacion());
    }

    private void listarAutoresVivosEnAnio() {
        System.out.print("Ingrese año: ");
        int anio=sc.nextInt();
        sc.nextLine();
        List<Autor>autores=autorService.obtenerAutoresVivosEnAnio(anio);
        autores.forEach(autor -> autor.imprimirInformacion());
    }

    private void listarLibrosPorIdioma() {
        Idioma.listarIdiomas();
        System.out.print("Ingrese el codigo del idioma: ");
        String idiomaBuscado=sc.nextLine();
        List<Libro>libros=libroService.obtenerLibrosPorIdioma(Idioma.stringToEnum(idiomaBuscado));
        libros.forEach(libro -> libro.imprimirInformacion());
    }

}

