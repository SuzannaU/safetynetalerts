package com.safetynet.safetynetalerts;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.repository.JsonReadingRepository;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;


public class RepositoryTest {
    String jsonTest =
            "{\"persons\": [{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" },{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" }],\"firestations\": [{ \"address\":\"1509 Culver St\", \"station\":\"3\" }],\"medicalrecords\": [{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] },{ \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1989\", \"medications\":[\"pharmacol:5000mg\", \"terazine:10mg\", \"noznazol:250mg\"], \"allergies\":[] }]}";

    private JsonNode getNode(String nodeName) throws IOException {
        JsonNode node = null;
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonTest);
        node = rootNode.get(nodeName);
        return node;
    }

    JsonReadingRepository jsonReadingRepository;

    @BeforeEach
    private void setUp() {
        jsonReadingRepository = new JsonReadingRepository();
    }


    // Integration tests
    // Set up testdata.json
    // Set up test repository to access testdata.json

    public void getNode_withCorrectParameters_returnsNode() {

    }

    public void getPersons_withCorrectParameters_doesNotThrow() {



    }



}
