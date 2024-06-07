package com.alejobeliz.proyectos.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroRecord(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorRecord> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer numeroDeDescargas
) {
}
