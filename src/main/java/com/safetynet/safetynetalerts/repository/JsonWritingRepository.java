package com.safetynet.safetynetalerts.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FirestationRawData;
import com.safetynet.safetynetalerts.model.MedicalRecordRawData;
import com.safetynet.safetynetalerts.model.PersonRawData;

@Repository
public class JsonWritingRepository {
    private static final Logger logger = LoggerFactory.getLogger(JsonWritingRepository.class);
    private final String sourceFilePath = "src/main/resources/data.json";

    public void updatePersons(List<PersonRawData> personsRawData) throws IOException {
        Map<String, Object> jsonData = getJsonData();
        jsonData.put("persons", personsRawData);

        writeDataInFile(jsonData);
        logger.debug("Json file updated with modified persons");
    }

    public void updateMedicalRecords(List<MedicalRecordRawData> medicalRecordsRawData) throws IOException {
        Map<String, Object> jsonData = getJsonData();
        jsonData.put("medicalrecords", medicalRecordsRawData);

        writeDataInFile(jsonData);
        logger.debug("Json file updated with modified medical records");
    }

    public void updateFirestations(List<FirestationRawData> firestationsRawData) throws IOException {
        Map<String, Object> jsonData = getJsonData();
        jsonData.put("firestations", firestationsRawData);

        writeDataInFile(jsonData);
        logger.debug("Json file updated with modified firestations");
    }

    private Map<String, Object> getJsonData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonData = null;

        try {
            jsonData = objectMapper.readValue(new File(sourceFilePath), new TypeReference<>() {});
            logger.debug("Data recoverd at path: " + sourceFilePath);
        } catch (IOException e) {
            logger.error("File not found at path: " + sourceFilePath);
            throw e;
        }

        return jsonData;
    }

    private void writeDataInFile(Map<String, Object> jsonData) throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        try {
            objMapper.writeValue(new File(sourceFilePath), jsonData);
            logger.debug("Json file updated. Path: " + sourceFilePath);
        } catch (IOException e) {
            logger.error("Unable to write Json file");
            throw e;
        }
    }
}
