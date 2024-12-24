package com.safetynet.safetynetalerts.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.dto.FirestationDTO;
import com.safetynet.safetynetalerts.dto.MedicalRecordDTO;
import com.safetynet.safetynetalerts.dto.PersonDTO;

@Repository
public class JsonRepository {
    private static final Logger logger = LoggerFactory.getLogger(JsonRepository.class);

    List<PersonDTO> personsDTO;
    List<FirestationDTO> firestationsDTO;
    List<MedicalRecordDTO> medicalRecordsDTO;

    private JsonNode getNode(String nodeName) throws IOException {
        JsonNode node = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data2.json"));
            node = rootNode.get(nodeName);
        } catch (IOException e) {
            logger.error("File not found");
            throw e;
        }
        return node;
    }

    public List<PersonDTO> getPersonsDTO() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode personsNode = getNode("persons");

        personsDTO = objectMapper.convertValue(
                personsNode, new TypeReference<List<PersonDTO>>() {});

        return personsDTO;
    }

    public List<FirestationDTO> getFirestationsDTO() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode firestationsNode = getNode("firestations");

        firestationsDTO = objectMapper.convertValue(
                firestationsNode, new TypeReference<List<FirestationDTO>>() {});

        return firestationsDTO;
    }

    public List<MedicalRecordDTO> getMedicalRecordsDTO() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode medicalRecordsNode = getNode("medicalrecords");

        medicalRecordsDTO = objectMapper.convertValue(
                medicalRecordsNode, new TypeReference<List<MedicalRecordDTO>>() {});

        return medicalRecordsDTO;
    }
}
