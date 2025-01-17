package com.safetynet.safetynetalerts.controller;

import java.io.IOException;
import java.time.format.DateTimeParseException;
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
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;

/**
 * Controller for handling requests related to medical records. Provides endpoints to create,
 * update, and delete medical records.
 * 
 * <p>
 * This controller is responsible for managing the medical records data within the SafetyNet Alerts
 * application. It interacts with the service layer to perform CRUD operations on medical records.
 * </p>
 * 
 */
@RestController
public class MedicalRecordDataController {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordDataController.class);
    private MedicalRecordService medicalRecordService;

    public MedicalRecordDataController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
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
     * Handles DateTimeParseException.
     * 
     * @param e the exception to handle
     * @return a response entity with BAD_REQUEST HTTP status code
     */
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid format for birthdate. Should be MM/dd/YYYY");
    }

    /**
     * Creates new medical record.
     * 
     * @param medicalRecord to be added (mandatory fields: firstName, lastName)
     * @return a response entity with the created medical record and either a CREATED HTTP status
     *         code in case of success or BAD_REQUEST HTTP status code if record with same first and
     *         last name already exists
     * @throws IOException
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> createMedicalRecord(
            @RequestBody MedicalRecord medicalRecord) throws IOException {

        logger.debug("POST request received for " + medicalRecord.toString());
        MedicalRecord newMedicalRecord = medicalRecordService.createMedicalRecord(medicalRecord);

        if (newMedicalRecord == null) {
            logger.error("newMedicalRecord is null");
            return new ResponseEntity<>(medicalRecord, HttpStatus.NO_CONTENT);
        } else {
            logger.info("newMedicalRecord sent");
            return new ResponseEntity<>(newMedicalRecord, HttpStatus.CREATED);
        }
    }

    /**
     * Updates an existing medical record.
     * 
     * @param medicalRecord the record to be updated (mandatory fields: firstName, lastName)
     * @return a response entity with the updated record and either a ACCEPTED HTTP status code in
     *         case of success or BAD_REQUEST HTTP status code if medical record doesn't exist
     * @throws IOException
     */
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @RequestBody MedicalRecord medicalRecord) throws IOException {

        logger.debug("PUT request received for " + medicalRecord.toString());
        MedicalRecord updatedMedicalRecord =
                medicalRecordService.updateMedicalRecord(medicalRecord);

        if (updatedMedicalRecord == null) {
            logger.error("updatedMedicalRecord is null");
            return new ResponseEntity<>(medicalRecord, HttpStatus.NO_CONTENT);
        } else {
            logger.info("updatedMedicalRecord sent");
            return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);
        }
    }

    /**
     * Deletes a medical record.
     * 
     * @param medicalRecord record to be deleted (mandatory fields: firstName, lastName)
     * @return a response entity with the deleted record and either ACCEPTED HTTP status code in
     *         case of success or BAD_REQUEST HTTP status code if matching record doesn't exist
     * @throws IOException
     */
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(
            @RequestBody MedicalRecord medicalRecord) throws IOException {

        logger.debug("DELDETE request received for " + medicalRecord.toString());
        MedicalRecord deletedMedicalRecord =
                medicalRecordService.deleteMedicalRecord(medicalRecord);

        if (deletedMedicalRecord == null) {
            logger.error("deletedMedicalRecord is null");
            return new ResponseEntity<>(medicalRecord, HttpStatus.NO_CONTENT);
        } else {
            logger.info("deletedMedicalRecord sent");
            return new ResponseEntity<>(deletedMedicalRecord, HttpStatus.OK);
        }
    }
}
