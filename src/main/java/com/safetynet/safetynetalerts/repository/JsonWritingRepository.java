package com.safetynet.safetynetalerts.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Repository
public class JsonWritingRepository {
    private static final Logger logger = LoggerFactory.getLogger(JsonWritingRepository.class);
    @Value("${data.file.sourcepath}")
    private String sourceFilePath;
    @Value("${data.file.outputpath}")
    private String outputFilePath;

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

    public boolean updatePersons(List<Person> persons) throws IOException {
        Map<String, Object> jsonData = getJsonData();
        jsonData.put("persons", persons);

        updateDataInFile(jsonData);
        logger.debug("Json file updated with modified persons");
        return true;
    }

    public boolean updateMedicalRecords(List<MedicalRecord> medicalRecords) throws IOException {
        Map<String, Object> jsonData = getJsonData();
        jsonData.put("medicalrecords", medicalRecords);

        updateDataInFile(jsonData);
        logger.debug("Json file updated with modified medical records");
        return true;
    }

    public boolean updateFirestations(List<Firestation> firestations) throws IOException {
        Map<String, Object> jsonData = getJsonData();
        jsonData.put("firestations", firestations);

        updateDataInFile(jsonData);
        logger.debug("Json file updated with modified firestations");
        return true;
    }

    private void updateDataInFile(Map<String, Object> jsonData) throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        try {
            objMapper.writeValue(new File(sourceFilePath), jsonData);
            logger.debug("Json file updated. Path: " + sourceFilePath);
        } catch (IOException e) {
            logger.error("Unable to write Json file");
            throw e;
        }
    }

    public void writeOutputFile(Object o) throws IOException {
        ObjectMapper objMApper = new ObjectMapper();
        try {
            objMApper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(outputFilePath), o);
            logger.debug("Data written at: " + outputFilePath);
        } catch (IOException e) {
            logger.error("Unable to write Json file");
            throw e;
        }
    }
}
