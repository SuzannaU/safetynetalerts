package com.safetynet.safetynetalerts.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.safetynet.safetynetalerts.dto.FirestationDTO;
import com.safetynet.safetynetalerts.service.FirestationService;

@RestController
public class FirestationDataController {
    private static final Logger logger = LoggerFactory.getLogger(FirestationDataController.class);

    @Autowired
    FirestationService firestationService;

    @PostMapping("/firestation")
    public ResponseEntity<FirestationDTO> createFirestationDTO(
            @RequestBody FirestationDTO firestationDTO)
            throws IOException {

        FirestationDTO newFirestationDTO = new FirestationDTO();
        try {
            newFirestationDTO = firestationService.createFirestationDTO(firestationDTO);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (newFirestationDTO == null) {
            return new ResponseEntity<>(firestationDTO, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(newFirestationDTO, HttpStatus.CREATED);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<FirestationDTO> updateFirestationDTO(
            @RequestBody FirestationDTO firestationDTO)
            throws IOException {

        FirestationDTO updatedFirestationDTO = new FirestationDTO();
        try {
            updatedFirestationDTO = firestationService.updateFirestationDTO(firestationDTO);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedFirestationDTO == null) {
            return new ResponseEntity<>(firestationDTO, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(updatedFirestationDTO, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<FirestationDTO> deleteFirestationDTO(
            @RequestBody FirestationDTO firestationDTO)
            throws IOException {

        FirestationDTO deletedFirestationDTO = new FirestationDTO();
        try {
            deletedFirestationDTO = firestationService.deletePersonDTO(firestationDTO);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (deletedFirestationDTO == null) {
            return new ResponseEntity<>(firestationDTO, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(deletedFirestationDTO, HttpStatus.ACCEPTED);
        }
    }

}
