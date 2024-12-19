package com.safetynet.safetynetalerts.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.FirestationFromJson;
import com.safetynet.safetynetalerts.model.MedicalRecordFromJson;
import com.safetynet.safetynetalerts.model.PersonFromJson;

@Repository
public class JsonRepository {

    // Contains methods that interact JSON source file

    List<PersonFromJson> personsFromJson;
    List<FirestationFromJson> firestationsFromJson;
    List<MedicalRecordFromJson> medicalRecordsFromJson;

    public List<PersonFromJson> getPersonsFromJson() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data.json"));
        JsonNode personsNode = rootNode.get("persons");
        personsFromJson = objectMapper
                .convertValue(personsNode, new TypeReference<List<PersonFromJson>>() {});

        return personsFromJson;
    }

    public List<FirestationFromJson> getFirestationsFromJson() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data.json"));
        JsonNode firestationsNode = rootNode.get("firestations");
        firestationsFromJson = objectMapper
                .convertValue(firestationsNode, new TypeReference<List<FirestationFromJson>>() {});

        return firestationsFromJson;
    }

    public List<MedicalRecordFromJson> getMedicalRecordsFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data.json"));
        JsonNode medicalRecordsNode = rootNode.get("medicalrecords");
        medicalRecordsFromJson = objectMapper
                .convertValue(
                        medicalRecordsNode, new TypeReference<List<MedicalRecordFromJson>>() {});

        return medicalRecordsFromJson;
    }
}
