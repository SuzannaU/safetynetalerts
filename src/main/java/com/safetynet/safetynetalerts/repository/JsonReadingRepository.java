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
import com.safetynet.safetynetalerts.model.FirestationRawData;
import com.safetynet.safetynetalerts.model.MedicalRecordRawData;
import com.safetynet.safetynetalerts.model.PersonRawData;

@Repository
public class JsonReadingRepository {
    private static final Logger logger = LoggerFactory.getLogger(JsonReadingRepository.class);
    private String sourceFilePath = "src/main/resources/data.json";

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

    public List<PersonRawData> getPersonsRawData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode personsNode = getNode("persons");

        List<PersonRawData> personsRawData = null;
        personsRawData = objectMapper.convertValue(
                personsNode, new TypeReference<List<PersonRawData>>() {});

        if (personsRawData != null)
            logger.debug("personsRawData retrieved");
        else
            logger.error("Issue retrieving personsRawData");

        return personsRawData;
    }

    public List<FirestationRawData> getFirestationsRawData() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode firestationsNode = getNode("firestations");

        List<FirestationRawData> firestationsRawData = null;
        firestationsRawData = objectMapper.convertValue(
                firestationsNode, new TypeReference<List<FirestationRawData>>() {});

        if (firestationsRawData != null)
            logger.debug("firestationsRawData retrieved");
        else
            logger.error("Issue retrieving firestationsRawData");

        return firestationsRawData;
    }

    public List<MedicalRecordRawData> getMedicalRecordsRawData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode medicalRecordsNode = getNode("medicalrecords");

        List<MedicalRecordRawData> medicalRecordsRawData = null;
        medicalRecordsRawData = objectMapper.convertValue(
                medicalRecordsNode, new TypeReference<List<MedicalRecordRawData>>() {});

        if (medicalRecordsRawData != null)
            logger.debug("medicalRecordsRawData retrieved");
        else
            logger.error("Issue retrieving medicalRecordsRawData");

        return medicalRecordsRawData;
    }
}
