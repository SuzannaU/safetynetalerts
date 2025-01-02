package com.safetynet.safetynetalerts.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import com.safetynet.safetynetalerts.model.PersonRawData;
import com.safetynet.safetynetalerts.service.PersonService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
public class PersonDataController {
    private static final Logger logger = LoggerFactory.getLogger(PersonDataController.class);

    @Autowired
    PersonService personService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing data: firstName and lastName are mandatory");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving/writing data");
    }

    @PostMapping("/person")
    public ResponseEntity<PersonRawData> createPersonRawData(
            @RequestBody PersonRawData personRawData)
            throws IOException, IllegalArgumentException {

        PersonRawData newPersonRawData = personService.createPersonRawData(personRawData);

        if (newPersonRawData == null) {
            logger.error("newPersonRawData is null");
            return new ResponseEntity<>(personRawData, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("newPersonRawData sent");
            return new ResponseEntity<>(newPersonRawData, HttpStatus.CREATED);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<PersonRawData> updatePersonRawData(
            @RequestBody PersonRawData personRawData)
            throws IOException {

        PersonRawData updatedPersonRawData = personService.updatePersonRawData(personRawData);

        if (updatedPersonRawData == null) {
            logger.error("updatedPersonRawData is null");
            return new ResponseEntity<>(personRawData, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("updatedPersonRawData sent");
            return new ResponseEntity<>(updatedPersonRawData, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<PersonRawData> deletePersonRawData(
            @RequestBody PersonRawData personRawData)
            throws IOException {

        PersonRawData deletedPersonRawData = personService.deletePersonRawData(personRawData);

        if (deletedPersonRawData == null) {
            logger.error("deletedPersonRawData is null");
            return new ResponseEntity<>(personRawData, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("deletedPersonRawData sent");
            return new ResponseEntity<>(deletedPersonRawData, HttpStatus.ACCEPTED);
        }
    }
}
