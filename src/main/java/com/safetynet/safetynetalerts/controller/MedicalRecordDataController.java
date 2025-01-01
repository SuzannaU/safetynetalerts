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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing data: firstName and lastName are mandatory");
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordRawData> createMedicalRecordRawData(
            @RequestBody MedicalRecordRawData medicalRecordRawData) throws IOException {

        MedicalRecordRawData newMedicalRecordRawData = null;
        try {
            newMedicalRecordRawData = medicalRecordService.createMedicalRecordRawData(medicalRecordRawData);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (newMedicalRecordRawData == null) {
            return new ResponseEntity<>(medicalRecordRawData, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(newMedicalRecordRawData, HttpStatus.CREATED);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordRawData> updateMedicalRecordRawData(
            @RequestBody MedicalRecordRawData medicalRecordRawData) throws IOException {

        MedicalRecordRawData updatedMedicalRecordRawData = null;
        try {
            updatedMedicalRecordRawData = medicalRecordService.updateMedicalRecordRawData(medicalRecordRawData);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedMedicalRecordRawData == null) {
            return new ResponseEntity<>(medicalRecordRawData, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(updatedMedicalRecordRawData, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordRawData> deleteMedicalRecordRawData(
            @RequestBody MedicalRecordRawData medicalRecordRawData) throws IOException {

        MedicalRecordRawData deletedMedicalRecordRawData = null;
        try {
            deletedMedicalRecordRawData = medicalRecordService.deleteMedicalRecordRawData(medicalRecordRawData);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (deletedMedicalRecordRawData == null) {
            return new ResponseEntity<>(medicalRecordRawData, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(deletedMedicalRecordRawData, HttpStatus.ACCEPTED);
        }
    }

}
