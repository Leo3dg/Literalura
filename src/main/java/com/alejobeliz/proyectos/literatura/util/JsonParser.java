package com.alejobeliz.proyectos.literatura.util;

import com.alejobeliz.proyectos.literatura.model.LibroRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JsonParser {

    private ObjectMapper objectMapper = new ObjectMapper();


    public LibroRecord parsearLibro(String json) {
        try {
            return objectMapper.readValue(json, LibroRecord.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


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
