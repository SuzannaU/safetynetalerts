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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing data: station and address are mandatory");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving/writing data");
    }

    @PostMapping("/firestation")
    public ResponseEntity<FirestationRawData> createFirestationRawData(
            @RequestBody FirestationRawData firestationRawData)
            throws IOException {

        FirestationRawData newFirestationRawData =
                firestationService.createFirestationRawData(firestationRawData);

        if (newFirestationRawData == null) {
            logger.error("newFirestationRawData is null");
            return new ResponseEntity<>(firestationRawData, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("newFirestationRawData sent");
            return new ResponseEntity<>(newFirestationRawData, HttpStatus.CREATED);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<FirestationRawData> updateFirestationRawData(
            @RequestBody FirestationRawData firestationRawData)
            throws IOException {

        FirestationRawData updatedFirestationRawData =
                firestationService.updateFirestationRawData(firestationRawData);

        if (updatedFirestationRawData == null) {
            logger.error("updatedFirestationRawData is null");
            return new ResponseEntity<>(firestationRawData, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("updatedFirestationRawData sent");
            return new ResponseEntity<>(updatedFirestationRawData, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<FirestationRawData> deleteFirestationRawData(
            @RequestBody FirestationRawData firestationRawData)
            throws IOException {

        FirestationRawData deletedFirestationRawData =
                firestationService.deletePersonRawData(firestationRawData);

        if (deletedFirestationRawData == null) {
            logger.error("deletedFirestationRawData is null");
            return new ResponseEntity<>(firestationRawData, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("deletedFirestationRawData sent");
            return new ResponseEntity<>(deletedFirestationRawData, HttpStatus.ACCEPTED);
        }
    }
}
