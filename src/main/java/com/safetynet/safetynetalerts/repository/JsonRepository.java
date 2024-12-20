package com.safetynet.safetynetalerts.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.dto.FirestationDTO;
import com.safetynet.safetynetalerts.dto.MedicalRecordDTO;
import com.safetynet.safetynetalerts.dto.PersonDTO;

@Repository
public class JsonRepository {

    // Contains methods that interact JSON source file

    List<PersonDTO> personsDTO;
    List<FirestationDTO> firestationsDTO;
    List<MedicalRecordDTO> medicalRecordsDTO;

    public List<PersonDTO> getPersonsDTO() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data.json"));
        JsonNode personsNode = rootNode.get("persons");
        personsDTO = objectMapper
                .convertValue(personsNode, new TypeReference<List<PersonDTO>>() {});

        return personsDTO;
    }

    public List<FirestationDTO> getFirestationsDTO() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data.json"));
        JsonNode firestationsNode = rootNode.get("firestations");
        firestationsDTO = objectMapper
                .convertValue(firestationsNode, new TypeReference<List<FirestationDTO>>() {});

        return firestationsDTO;
    }

    public List<MedicalRecordDTO> getMedicalRecordsDTO() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File("src/main/resources/data.json"));
        JsonNode medicalRecordsNode = rootNode.get("medicalrecords");
        medicalRecordsDTO = objectMapper
                .convertValue(
                        medicalRecordsNode, new TypeReference<List<MedicalRecordDTO>>() {});

        return medicalRecordsDTO;
    }
}
