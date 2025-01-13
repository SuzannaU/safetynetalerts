package com.safetynet.safetynetalerts.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Repository
public class JsonReadingRepository {
    private static final Logger logger = LoggerFactory.getLogger(JsonReadingRepository.class);
    @Value("${data.file.sourcepath}")
    private String sourceFilePath;

    private JsonNode getNode(String nodeName) throws IOException {
        JsonNode node = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(sourceFilePath));
            node = rootNode.get(nodeName);
            logger.debug("Data recoverd at path: " + sourceFilePath);
        } catch (IOException e) {
            logger.error("File not found at path: " + sourceFilePath);
            throw e;
        }
        return node;
    }

    public List<Person> getPersons() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode personsNode = getNode("persons");

        List<Person> persons = objectMapper.convertValue(
                personsNode, new TypeReference<List<Person>>() {});

        if (persons == null || persons.isEmpty())
            logger.error("Issue retrieving persons");
        else
            logger.debug("persons retrieved");

        return persons;
    }

    public List<Firestation> getFirestations() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode firestationsNode = getNode("firestations");

        List<Firestation> firestations = objectMapper.convertValue(
                firestationsNode, new TypeReference<List<Firestation>>() {});

        if (firestations == null || firestations.isEmpty())
            logger.error("Issue retrieving firestations");
        else
            logger.debug("firestations retrieved");

        return firestations;
    }

    public List<MedicalRecord> getMedicalRecords() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode medicalRecordsNode = getNode("medicalrecords");

        List<MedicalRecord> medicalRecords = objectMapper.convertValue(
                medicalRecordsNode, new TypeReference<List<MedicalRecord>>() {});

        if (medicalRecords == null || medicalRecords.isEmpty())
            logger.error("Issue retrieving medicalRecords");
        else
            logger.debug("medicalRecords retrieved");

        return medicalRecords;
    }
}
