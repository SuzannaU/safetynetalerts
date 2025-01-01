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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing data: firstName and lastName are mandatory");
    }

    @PostMapping("/person")
    public ResponseEntity<PersonRawData> createPersonRawData(@RequestBody PersonRawData personRawData)
            throws IOException, IllegalArgumentException {

        PersonRawData newPersonRawData = null;
        try {
            newPersonRawData = personService.createPersonRawData(personRawData);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (newPersonRawData == null) {
            return new ResponseEntity<>(personRawData, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(newPersonRawData, HttpStatus.CREATED);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<PersonRawData> updatePersonRawData(@RequestBody PersonRawData personRawData)
            throws IOException {

        PersonRawData updatedPersonRawData = null;
        try {
            updatedPersonRawData = personService.updatePersonRawData(personRawData);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedPersonRawData == null) {
            return new ResponseEntity<>(personRawData, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(updatedPersonRawData, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<PersonRawData> deletePersonRawData(@RequestBody PersonRawData personRawData)
            throws IOException {

        PersonRawData deletedPersonRawData = null;
        try {
            deletedPersonRawData = personService.deletePersonRawData(personRawData);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (deletedPersonRawData == null) {
            return new ResponseEntity<>(personRawData, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(deletedPersonRawData, HttpStatus.ACCEPTED);
        }
    }

}
