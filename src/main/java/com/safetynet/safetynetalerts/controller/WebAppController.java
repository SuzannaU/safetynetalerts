package com.safetynet.safetynetalerts.controller;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.repository.JsonRepository;
import com.safetynet.safetynetalerts.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class WebAppController {
        private static final Logger logger = LoggerFactory.getLogger(WebAppController.class);

        @Autowired
        JsonRepository jsonRepository;
        @Autowired
        PersonService personService;
        @Autowired
        FirestationService firestationService;
        @Autowired
        MedicalRecordService medicalRecordService;
        @Autowired
        FirestationDataService firestationDataService;
        @Autowired
        ChildAlertDataService childAlertDataService;
        @Autowired
        Mapper mapper;

        @GetMapping("/firestation")
        public ResponseEntity<FirestationData> getFirestationData(
                        @RequestParam("stationNumber") final int stationNumber) throws IOException {
                FirestationData firestationData = new FirestationData();
                try {
                        firestationData = firestationDataService.getFirestationData(stationNumber);
                } catch (IOException e) {
                        logger.error("Error retrieving data");
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                ObjectMapper objMapper = new ObjectMapper();

                if (firestationData.getPersons().isEmpty()) {
                        logger.error("firestationData is empty");
                        objMapper.writeValue(new File("target/output.json"), null);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                        logger.info("firestationData sent");
                        objMapper.writerWithDefaultPrettyPrinter()
                                        .writeValue(new File("target/output.json"),
                                                        firestationData);
                        return new ResponseEntity<>(firestationData, HttpStatus.OK);
                }
        }

        @GetMapping("/childAlert")
        public ResponseEntity<ChildAlertData> getChildAlertData(
                        @RequestParam("address") final String address) throws IOException {

                ChildAlertData childAlertData = new ChildAlertData();
                try {
                        childAlertData = childAlertDataService.getChildAlertData(address);
                } catch (IOException e) {
                        logger.error("Error retrieving data");
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                ObjectMapper objMapper = new ObjectMapper();

                if (childAlertData.getChildren().isEmpty()) {
                        logger.error("childAlertData is empty");
                        objMapper.writeValue(new File("target/output.json"), null);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                        logger.info("childAlertData sent");
                        objMapper.writerWithDefaultPrettyPrinter()
                                        .writeValue(new File("target/output.json"),
                                                        childAlertData);
                        return new ResponseEntity<>(childAlertData, HttpStatus.OK);
                }
        }

}
