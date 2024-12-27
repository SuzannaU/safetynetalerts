package com.safetynet.safetynetalerts.controller;

import org.springframework.web.bind.annotation.RestController;
import com.safetynet.safetynetalerts.dto.PersonDTO;
import com.safetynet.safetynetalerts.repository.JsonRepository;
import com.safetynet.safetynetalerts.service.PersonService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
public class PersonDataController {
    private static final Logger logger = LoggerFactory.getLogger(PersonDataController.class);

    @Autowired
    JsonRepository jsonRepository;

    @Autowired
    PersonService personService;

    @PostMapping("/person")
    public ResponseEntity<PersonDTO> createPersonDTO(@RequestBody PersonDTO personDTO)
            throws IOException {

        PersonDTO newPersonDTO = new PersonDTO();
        try {
            newPersonDTO = personService.createPersonDTO(personDTO);
        } catch (IOException e) {
            logger.error("Error retrieving data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (newPersonDTO == null) {
            return new ResponseEntity<>(personDTO, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(newPersonDTO, HttpStatus.CREATED);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<PersonDTO> updatePersonDTO(@RequestBody PersonDTO personDTO)
            throws IOException {

        PersonDTO updatedPersonDTO = new PersonDTO();
        try {
            updatedPersonDTO = personService.updatePersonDTO(personDTO);
        } catch (IOException e) {
            logger.error("Error retrieving data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedPersonDTO == null) {
            return new ResponseEntity<>(personDTO, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(updatedPersonDTO, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<PersonDTO> deletePersonDTO(@RequestBody PersonDTO personDTO)
            throws IOException {

        PersonDTO deletedPersonDTO = new PersonDTO();
        try {
            deletedPersonDTO = personService.deletePersonDTO(personDTO);
        } catch (IOException e) {
            logger.error("Error retrieving data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (deletedPersonDTO == null) {
            return new ResponseEntity<>(personDTO, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(deletedPersonDTO, HttpStatus.ACCEPTED);
        }
    }

}
