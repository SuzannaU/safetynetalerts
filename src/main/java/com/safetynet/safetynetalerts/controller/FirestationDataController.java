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

/**
 * Controller for handling requests related to firestations. Provides endpoints to create, update,
 * and delete firestations.
 * 
 * <p>
 * This controller is responsible for managing the firestations data within the SafetyNet Alerts
 * application. It interacts with the service layer to perform CRUD operations on firestations.
 * </p>
 * 
 */
@RestController
public class FirestationDataController {
    private static final Logger logger = LoggerFactory.getLogger(FirestationDataController.class);
    private FirestationService firestationService;

    public FirestationDataController(FirestationService firestationService) {
        this.firestationService = firestationService;
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
                .body("Missing data: station and address are mandatory");
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
     * Creates new mapping between a firestation and an address.
     * 
     * @param firestation to be added (mandatory fields: station and address)
     * @return a response entity with the created firestation and either a CREATED HTTP status code
     *         in case of success or BAD_REQUEST HTTP status code if mapping already exists or
     *         address already mapped to a station
     * @throws IOException
     */
    @PostMapping("/firestation")
    public ResponseEntity<Firestation> createFirestation(
            @RequestBody Firestation firestation) throws IOException {

        logger.debug("POST request received for " + firestation.toString());
        Firestation newFirestation = firestationService.createFirestation(firestation);

        if (newFirestation == null) {
            logger.error("newFirestation is null");
            return new ResponseEntity<>(firestation, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("newFirestation sent");
            return new ResponseEntity<>(newFirestation, HttpStatus.CREATED);
        }
    }

    /**
     * Updates the station mapped to an address.
     * 
     * @param firestation to be updated (mandatory fields: station and address)
     * @return a response entity with the updated firestation and either a ACCEPTED HTTP status code
     *         in case of success or BAD_REQUEST HTTP status code if mapping already exists or
     *         address doesn't exist
     * @throws IOException
     */
    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(
            @RequestBody Firestation firestation) throws IOException {

        logger.debug("PUT request received for " + firestation.toString());
        Firestation updatedFirestation = firestationService.updateFirestation(firestation);

        if (updatedFirestation == null) {
            logger.error("updatedFirestation is null");
            return new ResponseEntity<>(firestation, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("updatedFirestation sent");
            return new ResponseEntity<>(updatedFirestation, HttpStatus.ACCEPTED);
        }
    }

    /**
     * Deletes a mapping between a station and an address.
     * 
     * @param firestation to be deleted (mandatory fields: station and address)
     * @return a response entity with the deleted firestation and either ACCEPTED HTTP status code
     *         in case of success or BAD_REQUEST HTTP status code if mapping doesn't exist
     * @throws IOException
     */
    @DeleteMapping("/firestation")
    public ResponseEntity<Firestation> deleteFirestation(
            @RequestBody Firestation firestation) throws IOException {

        logger.debug("DELETE request received for " + firestation.toString());
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
