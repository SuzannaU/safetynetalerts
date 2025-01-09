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

@RestController
public class MedicalRecordDataController {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordDataController.class);
    MedicalRecordService medicalRecordService;

    public MedicalRecordDataController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing data: firstName and lastName are mandatory");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving/writing data");
    }    
    
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format for birthdate. Should be MM/dd/YYYY");
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> createMedicalRecord(
            @RequestBody MedicalRecord medicalRecord) throws IOException {

        MedicalRecord newMedicalRecord = medicalRecordService.createMedicalRecord(medicalRecord);

        if (newMedicalRecord == null) {
            logger.error("newMedicalRecord is null");
            return new ResponseEntity<>(medicalRecord, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("newMedicalRecord sent");
            return new ResponseEntity<>(newMedicalRecord, HttpStatus.CREATED);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(
            @RequestBody MedicalRecord medicalRecord) throws IOException {

        MedicalRecord updatedMedicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);

        if (updatedMedicalRecord == null) {
            logger.error("updatedMedicalRecord is null");
            return new ResponseEntity<>(medicalRecord, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("updatedMedicalRecord sent");
            return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(
            @RequestBody MedicalRecord medicalRecord) throws IOException {

        MedicalRecord deletedMedicalRecord = medicalRecordService.deleteMedicalRecord(medicalRecord);

        if (deletedMedicalRecord == null) {
            logger.error("deletedMedicalRecord is null");
            return new ResponseEntity<>(medicalRecord, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("deletedMedicalRecord sent");
            return new ResponseEntity<>(deletedMedicalRecord, HttpStatus.ACCEPTED);
        }
    }

}
