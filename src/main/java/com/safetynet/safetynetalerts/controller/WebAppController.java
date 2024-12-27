package com.safetynet.safetynetalerts.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
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
        PhoneAlertDataService phoneAlertDataService;
        @Autowired
        FireDataService fireDataService;
        @Autowired
        FloodDataService floodDataService;
        @Autowired
        InfoDataService infoDataService;
        @Autowired
        CommunityEmailDataService communityEmailDataService;
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

        @GetMapping("/phoneAlert")
        public ResponseEntity<PhoneAlertData> getPhoneAlertData(
                        @RequestParam("firestation") final int stationNumber) throws IOException {

                PhoneAlertData phoneAlertData = new PhoneAlertData();
                try {
                        phoneAlertData = phoneAlertDataService.getPhoneAlertData(stationNumber);
                } catch (IOException e) {
                        logger.error("Error retrieving data");
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                ObjectMapper objMapper = new ObjectMapper();

                if (phoneAlertData.getPhones().isEmpty()) {
                        logger.error("phoneAlertData is empty");
                        objMapper.writeValue(new File("target/output.json"), null);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                        logger.info("phoneAlertData sent");
                        objMapper.writerWithDefaultPrettyPrinter()
                                        .writeValue(new File("target/output.json"),
                                                        phoneAlertData);
                        return new ResponseEntity<>(phoneAlertData, HttpStatus.OK);
                }
        }

        @GetMapping("/fire")
        public ResponseEntity<FireData> getFireData(
                        @RequestParam("address") final String address) throws IOException {

                FireData fireData = new FireData();
                try {
                        fireData = fireDataService.getFireData(address);
                } catch (IOException e) {
                        logger.error("Error retrieving data");
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                ObjectMapper objMapper = new ObjectMapper();

                if (fireData.getResidents().isEmpty()) {
                        logger.error("fireData is empty");
                        objMapper.writeValue(new File("target/output.json"), null);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                        logger.info("fireData sent");
                        objMapper.writerWithDefaultPrettyPrinter()
                                        .writeValue(new File("target/output.json"),
                                                        fireData);
                        return new ResponseEntity<>(fireData, HttpStatus.OK);
                }
        }

        @GetMapping("/flood/stations")
        public ResponseEntity<FloodData> getFloodData(
                        @RequestParam("stations") final List<Integer> listOfStationIds)
                        throws IOException {

                FloodData floodData = new FloodData();
                try {
                        floodData = floodDataService.getFloodData(listOfStationIds);
                } catch (IOException e) {
                        logger.error("Error retrieving data");
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                ObjectMapper objMapper = new ObjectMapper();

                if (floodData.getStationsForFlood().isEmpty()) {
                        logger.error("floodData is empty");
                        objMapper.writeValue(new File("target/output.json"), null);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                        logger.info("floodData sent");
                        objMapper.writerWithDefaultPrettyPrinter()
                                        .writeValue(new File("target/output.json"),
                                                        floodData);
                        return new ResponseEntity<>(floodData, HttpStatus.OK);
                }
        }

        @GetMapping("/personInfolastName={lastName}")
        public ResponseEntity<InfoData> getInfoData(@PathVariable("lastName") final String lastName)
                        throws IOException {

                InfoData infoData = new InfoData();
                try {
                        infoData = infoDataService.getInfoData(lastName);
                } catch (IOException e) {
                        logger.error("Error retrieving data");
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                ObjectMapper objMapper = new ObjectMapper();

                if (infoData.getPersons().isEmpty()) {
                        logger.error("infoData is empty");
                        objMapper.writeValue(new File("target/output.json"), null);
                        return new ResponseEntity<InfoData>(HttpStatus.NOT_FOUND);
                } else {
                        logger.info("infoData sent");
                        objMapper.writerWithDefaultPrettyPrinter()
                                        .writeValue(new File("target/output.json"),
                                                        infoData);
                        return new ResponseEntity<InfoData>(infoData, HttpStatus.OK);
                }
        }

        @GetMapping("/communityEmail")
        public ResponseEntity<CommunityEmailData> getCommunityEmailData(
                        @RequestParam("city") final String city) throws IOException {

                CommunityEmailData communityEmailData = new CommunityEmailData();
                try {
                        communityEmailData = communityEmailDataService.getCommunityEmailData(city);
                } catch (IOException e) {
                        logger.error("Error retrieving data");
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

                ObjectMapper objMapper = new ObjectMapper();

                if (communityEmailData.getEmails().isEmpty()) {
                        logger.error("communityEmailData is empty");
                        objMapper.writeValue(new File("target/output.json"), null);
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                        logger.info("communityEmailData sent");
                        objMapper.writerWithDefaultPrettyPrinter()
                                        .writeValue(new File("target/output.json"),
                                                        communityEmailData);
                        return new ResponseEntity<>(communityEmailData, HttpStatus.OK);
                }
        }

}
