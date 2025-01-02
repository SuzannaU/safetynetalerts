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
import com.safetynet.safetynetalerts.model.MedicalRecordRawData;
import com.safetynet.safetynetalerts.service.MedicalRecordService;

@RestController
public class MedicalRecordDataController {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordDataController.class);

    @Autowired
    MedicalRecordService medicalRecordService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Missing data: firstName and lastName are mandatory");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving/writing data");
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordRawData> createMedicalRecordRawData(
            @RequestBody MedicalRecordRawData medicalRecordRawData) throws IOException {

        MedicalRecordRawData newMedicalRecordRawData =
                medicalRecordService.createMedicalRecordRawData(medicalRecordRawData);

        if (newMedicalRecordRawData == null) {
            logger.error("newMedicalRecordRawData is null");
            return new ResponseEntity<>(medicalRecordRawData, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("newMedicalRecordRawData sent");
            return new ResponseEntity<>(newMedicalRecordRawData, HttpStatus.CREATED);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordRawData> updateMedicalRecordRawData(
            @RequestBody MedicalRecordRawData medicalRecordRawData) throws IOException {

        MedicalRecordRawData updatedMedicalRecordRawData =
                medicalRecordService.updateMedicalRecordRawData(medicalRecordRawData);

        if (updatedMedicalRecordRawData == null) {
            logger.error("updatedMedicalRecordRawData is null");
            return new ResponseEntity<>(medicalRecordRawData, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("updatedMedicalRecordRawData sent");
            return new ResponseEntity<>(updatedMedicalRecordRawData, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordRawData> deleteMedicalRecordRawData(
            @RequestBody MedicalRecordRawData medicalRecordRawData) throws IOException {

        MedicalRecordRawData deletedMedicalRecordRawData =
                medicalRecordService.deleteMedicalRecordRawData(medicalRecordRawData);

        if (deletedMedicalRecordRawData == null) {
            logger.error("deletedMedicalRecordRawData is null");
            return new ResponseEntity<>(medicalRecordRawData, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("deletedMedicalRecordRawData sent");
            return new ResponseEntity<>(deletedMedicalRecordRawData, HttpStatus.ACCEPTED);
        }
    }

}
