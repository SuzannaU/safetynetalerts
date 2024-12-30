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
public class JsonReadingRepository {
    private static final Logger logger = LoggerFactory.getLogger(JsonReadingRepository.class);
    private String sourceFilePath = "src/main/resources/datatest.json";

    private JsonNode getNode(String nodeName) throws IOException {
        JsonNode node = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(sourceFilePath));
            node = rootNode.get(nodeName);
        } catch (IOException e) {
            logger.error("File not found at path: " + sourceFilePath);
            throw e;
        }
        return node;
    }

    public List<PersonDTO> getPersonsDTO() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode personsNode = getNode("persons");

        List<PersonDTO> personsDTO = null;
        personsDTO = objectMapper.convertValue(
                personsNode, new TypeReference<List<PersonDTO>>() {});

        if (personsDTO != null)
            logger.debug("personsDTO retrieved");
        else
            logger.error("Issue retrieving personsDTO");

        return personsDTO;
    }

    public List<FirestationDTO> getFirestationsDTO() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode firestationsNode = getNode("firestations");

        List<FirestationDTO> firestationsDTO = null;
        firestationsDTO = objectMapper.convertValue(
                firestationsNode, new TypeReference<List<FirestationDTO>>() {});

        if (firestationsDTO != null)
            logger.debug("firestationsDTO retrieved");
        else
            logger.error("Issue retrieving firestationsDTO");

        return firestationsDTO;
    }

    public List<MedicalRecordDTO> getMedicalRecordsDTO() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode medicalRecordsNode = getNode("medicalrecords");

        List<MedicalRecordDTO> medicalRecordsDTO = null;
        medicalRecordsDTO = objectMapper.convertValue(
                medicalRecordsNode, new TypeReference<List<MedicalRecordDTO>>() {});

        if (medicalRecordsDTO != null)
            logger.debug("medicalRecordsDTO retrieved");
        else
            logger.error("Issue retrieving medicalRecordsDTO");

        return medicalRecordsDTO;
    }
}
