package com.safetynet.safetynetalerts.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;

@RestController
public class FirestationDataController {
    private static final Logger logger = LoggerFactory.getLogger(FirestationDataController.class);
    FirestationService firestationService;

    public FirestationDataController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing data: station and address are mandatory");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving/writing data");
    }

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> createFirestation(
            @RequestBody Firestation firestation)
            throws IOException {

        Firestation newFirestation = firestationService.createFirestation(firestation);

        if (newFirestation == null) {
            logger.error("newFirestation is null");
            return new ResponseEntity<>(firestation, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("newFirestation sent");
            return new ResponseEntity<>(newFirestation, HttpStatus.CREATED);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(
            @RequestBody Firestation firestation)
            throws IOException {

        Firestation updatedFirestation = firestationService.updateFirestation(firestation);

        if (updatedFirestation == null) {
            logger.error("updatedFirestation is null");
            return new ResponseEntity<>(firestation, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("updatedFirestation sent");
            return new ResponseEntity<>(updatedFirestation, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Firestation> deleteFirestation(
            @RequestBody Firestation firestation)
            throws IOException {

        Firestation deletedFirestation = firestationService.deleteFirestation(firestation);

        if (deletedFirestation == null) {
            logger.error("deletedFirestation is null");
            return new ResponseEntity<>(firestation, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("deletedFirestation sent");
            return new ResponseEntity<>(deletedFirestation, HttpStatus.ACCEPTED);
        }
    }
}
