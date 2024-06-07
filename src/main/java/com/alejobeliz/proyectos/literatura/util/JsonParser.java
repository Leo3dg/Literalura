package com.alejobeliz.proyectos.literatura.util;

import com.alejobeliz.proyectos.literatura.model.LibroRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que proporciona métodos para analizar JSON y convertirlo en objetos de tipo LibroRecord.
 */
public class JsonParser {

    // ObjectMapper se utiliza para convertir JSON en objetos Java y viceversa.
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convierte un JSON en un objeto LibroRecord.
     *
     * @param json JSON a ser convertido.
     * @return Objeto LibroRecord creado a partir del JSON.
     * @throws RuntimeException Si ocurre un error durante el procesamiento del JSON.
     */
    public LibroRecord parsearLibro(String json) {
        try {
            return objectMapper.readValue(json, LibroRecord.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convierte un JSON en una lista de objetos LibroRecord.
     *
     * @param json JSON a ser convertido.
     * @return Lista de objetos LibroRecord creados a partir del JSON.
     * @throws RuntimeException Si ocurre un error durante el procesamiento del JSON.
     */
    public List<LibroRecord> parsearLibros(String json) {
        List<LibroRecord> lista = new ArrayList<>();
        try {

            JsonNode jsonObject = objectMapper.readTree(json);
            JsonNode resultados = jsonObject.get("results");

            for (JsonNode node : (ArrayNode) resultados) {
                LibroRecord libro = objectMapper.treeToValue(node, LibroRecord.class);
                lista.add(libro);
            }

            return lista;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
