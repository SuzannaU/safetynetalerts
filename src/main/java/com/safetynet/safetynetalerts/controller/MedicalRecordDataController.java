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
import com.safetynet.safetynetalerts.dto.MedicalRecordDTO;
import com.safetynet.safetynetalerts.service.MedicalRecordService;

@RestController
public class MedicalRecordDataController {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordDataController.class);

    @Autowired
    MedicalRecordService medicalRecordService;

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDTO> createMedicalRecordDTO(
            @RequestBody MedicalRecordDTO medicalRecordDTO) throws IOException {

        MedicalRecordDTO newMedicalRecordDTO = new MedicalRecordDTO();
        try {
            newMedicalRecordDTO = medicalRecordService.createMedicalRecordDTO(medicalRecordDTO);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (newMedicalRecordDTO == null) {
            return new ResponseEntity<>(medicalRecordDTO, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(newMedicalRecordDTO, HttpStatus.CREATED);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecordDTO(
            @RequestBody MedicalRecordDTO medicalRecordDTO) throws IOException {

        MedicalRecordDTO updatedMedicalRecordDTO = new MedicalRecordDTO();
        try {
            updatedMedicalRecordDTO = medicalRecordService.updateMedicalRecordDTO(medicalRecordDTO);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedMedicalRecordDTO == null) {
            return new ResponseEntity<>(medicalRecordDTO, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(updatedMedicalRecordDTO, HttpStatus.ACCEPTED);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDTO> deleteMedicalRecordDTO(
            @RequestBody MedicalRecordDTO medicalRecordDTO) throws IOException {

        MedicalRecordDTO deletedMedicalRecordDTO = new MedicalRecordDTO();
        try {
            deletedMedicalRecordDTO = medicalRecordService.deleteMedicalRecordDTO(medicalRecordDTO);
        } catch (IOException e) {
            logger.error("Error retrieving/writing data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (deletedMedicalRecordDTO == null) {
            return new ResponseEntity<>(medicalRecordDTO, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(deletedMedicalRecordDTO, HttpStatus.ACCEPTED);
        }
    }

}
