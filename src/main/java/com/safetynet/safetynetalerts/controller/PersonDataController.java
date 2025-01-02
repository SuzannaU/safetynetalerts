package com.safetynet.safetynetalerts.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import com.safetynet.safetynetalerts.model.Person;
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
    public ResponseEntity<Person> createPerson(
            @RequestBody Person person)
            throws IOException, IllegalArgumentException {

        Person newPerson = personService.createPerson(person);

        if (newPerson == null) {
            logger.error("newPerson is null");
            return new ResponseEntity<>(person, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("newPerson sent");
            return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(
            @RequestBody Person person)
            throws IOException {

        Person updatedPerson = personService.updatePerson(person);

        if (updatedPerson == null) {
            logger.error("updatedPerson is null");
            return new ResponseEntity<>(person, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("updatedPerson sent");
            return new ResponseEntity<>(updatedPerson, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<Person> deletePerson(
            @RequestBody Person person)
            throws IOException {

        Person deletedPerson = personService.deletePerson(person);

        if (deletedPerson == null) {
            logger.error("deletedPerson is null");
            return new ResponseEntity<>(person, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("deletedPerson sent");
            return new ResponseEntity<>(deletedPerson, HttpStatus.ACCEPTED);
        }
    }
}
