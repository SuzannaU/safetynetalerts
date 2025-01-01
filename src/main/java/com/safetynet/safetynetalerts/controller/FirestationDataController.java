package com.safetynet.safetynetalerts.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.safetynet.safetynetalerts.model.FirestationRawData;
import com.safetynet.safetynetalerts.service.FirestationService;

@RestController
public class FirestationDataController {
    private static final Logger logger = LoggerFactory.getLogger(FirestationDataController.class);

    @Autowired
    FirestationService firestationService;
        
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing data: station and address are mandatory");
    }

    @PostMapping("/firestation")
    public ResponseEntity<FirestationRawData> createFirestationRawData(
            @RequestBody FirestationRawData firestationRawData)
            throws IOException {

        FirestationRawData newFirestationRawData = null;
        try {
            newFirestationRawData = firestationService.createFirestationRawData(firestationRawData);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (newFirestationRawData == null) {
            return new ResponseEntity<>(firestationRawData, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(newFirestationRawData, HttpStatus.CREATED);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<FirestationRawData> updateFirestationRawData(
            @RequestBody FirestationRawData firestationRawData)
            throws IOException {

        FirestationRawData updatedFirestationRawData = null;
        try {
            updatedFirestationRawData = firestationService.updateFirestationRawData(firestationRawData);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedFirestationRawData == null) {
            return new ResponseEntity<>(firestationRawData, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(updatedFirestationRawData, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<FirestationRawData> deleteFirestationRawData(
            @RequestBody FirestationRawData firestationRawData)
            throws IOException {

        FirestationRawData deletedFirestationRawData = null;
        try {
            deletedFirestationRawData = firestationService.deletePersonRawData(firestationRawData);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (deletedFirestationRawData == null) {
            return new ResponseEntity<>(firestationRawData, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(deletedFirestationRawData, HttpStatus.ACCEPTED);
        }
    }

}
