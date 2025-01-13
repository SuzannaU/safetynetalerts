package com.safetynet.safetynetalerts.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Controller for handling requests related to persons. Provides endpoints to create, update, and
 * delete persons.
 * 
 * <p>
 * This controller is responsible for managing the persons data within the SafetyNet Alerts
 * application. It interacts with the service layer to perform CRUD operations on persons.
 * </p>
 * 
 */
@RestController
public class PersonDataController {
    private static final Logger logger = LoggerFactory.getLogger(PersonDataController.class);
    private PersonService personService;

    public PersonDataController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Handles IllegalArgumentException.
     * 
     * @param e the exception to handle
     * @return a response entity with BAD_REQUEST HTTP status code
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing data: firstName and lastName are mandatory");
    }

    /**
     * Handles IOException.
     * 
     * @param e the exception to handle
     * @return a response entity with NOT_FOUND HTTP status code
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving/writing data");
    }

    /**
     * Creates new person.
     * 
     * @param person to be added (mandatory fields: firstName, lastName)
     * @return a response entity with the created person and either a CREATED HTTP status code in
     *         case of success or BAD_REQUEST HTTP status code if person with same first and last
     *         name already exists
     * @throws IOException
     */
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

    /**
     * Updates an existing person.
     * 
     * @param person the person to be updated (mandatory fields: firstName, lastName)
     * @return a response entity with the updated person and either a ACCEPTED HTTP status code in
     *         case of success or BAD_REQUEST HTTP status code if person doesn't exist
     * @throws IOException
     */
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

    /**
     * Deletes a person.
     * 
     * @param person to be deleted (mandatory fields: firstName, lastName)
     * @return a response entity with the deleted person and either ACCEPTED HTTP status code in
     *         case of success or BAD_REQUEST HTTP status code if matching person doesn't exist
     * @throws IOException
     */
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
