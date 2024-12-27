package com.safetynet.safetynetalerts.repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private String sourceFilePath = "src/main/resources/data.json";

    private JsonNode getNode(String nodeName) throws IOException {
        JsonNode node = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
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

        List<PersonDTO> personsDTO = objectMapper.convertValue(
                personsNode, new TypeReference<List<PersonDTO>>() {});

        return personsDTO;
    }

    public List<FirestationDTO> getFirestationsDTO() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode firestationsNode = getNode("firestations");

        List<FirestationDTO> firestationsDTO = objectMapper.convertValue(
                firestationsNode, new TypeReference<List<FirestationDTO>>() {});

        return firestationsDTO;
    }

    public List<MedicalRecordDTO> getMedicalRecordsDTO() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode medicalRecordsNode = getNode("medicalrecords");

        List<MedicalRecordDTO> medicalRecordsDTO = objectMapper.convertValue(
                medicalRecordsNode, new TypeReference<List<MedicalRecordDTO>>() {});

        return medicalRecordsDTO;
    }

    public Map<String, Object> getJsonData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonData = objectMapper.readValue(
                new File(sourceFilePath), new TypeReference<>() {});

        return jsonData;
    }

    public PersonDTO createPerson(PersonDTO personDTO) throws IOException {
        Map<String, Object> jsonData = getJsonData();
        List<PersonDTO> personsDTO = getPersonsDTO();

        PersonDTO newPersonDTO = personDTO;
        personsDTO.add(newPersonDTO);
        jsonData.put("persons", personsDTO);

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.writeValue(new File(sourceFilePath), jsonData);
        logger.info("Json file updated with new person");

        return newPersonDTO;
    }

    public PersonDTO updatePerson(PersonDTO personDTO) throws IOException {
        Map<String, Object> jsonData = getJsonData();
        List<PersonDTO> personsDTO = getPersonsDTO();

        Optional<PersonDTO> updatingPerson = personsDTO.stream()
                .filter(p -> p.getFirstName().equals(personDTO.getFirstName()))
                .filter(p -> p.getLastName().equals(personDTO.getLastName()))
                .findFirst();

        updatingPerson.ifPresentOrElse(
                p -> personsDTO.add(p),
                () -> logger.error("This person doesn't exist"));

        jsonData.put("persons", personsDTO);

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.writeValue(new File(sourceFilePath), jsonData);
        logger.info("Json file updated with updated person");

        return updatingPerson.get();
    }

    public PersonDTO deletePerson(PersonDTO personDTO) throws IOException {
        Map<String, Object> jsonData = getJsonData();
        List<PersonDTO> personsDTO = getPersonsDTO();

        Optional<PersonDTO> deletingPerson = personsDTO.stream()
                .filter(p -> p.getFirstName().equals(personDTO.getFirstName()))
                .filter(p -> p.getLastName().equals(personDTO.getLastName()))
                .findFirst();

        deletingPerson.ifPresentOrElse(
                p -> personsDTO.remove(p),
                () -> logger.error("This person doesn't exist"));

        jsonData.put("persons", personsDTO);

        ObjectMapper objMapper = new ObjectMapper();
        objMapper.writeValue(new File(sourceFilePath), jsonData);
        logger.info("Deleted person removed from Json file");

        return deletingPerson.get();
    }
}
