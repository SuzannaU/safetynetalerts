package com.safetynet.safetynetalerts.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;
import com.safetynet.safetynetalerts.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class WebAppController {
    private static final Logger logger = LoggerFactory.getLogger(WebAppController.class);
    private WebAppService webAppService;
    private JsonWritingRepository jsonWritingRepository;

    public WebAppController(WebAppService webAppService,
            JsonWritingRepository jsonWritingRepository) {
        this.webAppService = webAppService;
        this.jsonWritingRepository = jsonWritingRepository;
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving/writing data");
    }
    
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error due to missing age or birthdate");
    }

    @GetMapping("/firestation")
    public ResponseEntity<FirestationData> getFirestationData(
            @RequestParam("stationNumber") final int stationNumber) throws IOException {

        FirestationData firestationData = webAppService.getFirestationData(stationNumber);

        if (firestationData == null) {
            logger.error("firestationData is empty");
            jsonWritingRepository.writeOutputFile(null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("firestationData sent");
            jsonWritingRepository.writeOutputFile(firestationData);
            return new ResponseEntity<>(firestationData, HttpStatus.OK);
        }
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertData> getChildAlertData(
            @RequestParam("address") final String address) throws IOException {

        ChildAlertData childAlertData = webAppService.getChildAlertData(address);

        if (childAlertData == null) {
            logger.error("childAlertData is empty");
            jsonWritingRepository.writeOutputFile(null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (childAlertData.getChildren().isEmpty()) {
            logger.error("No children live at this address");
            jsonWritingRepository.writeOutputFile(null);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            logger.info("childAlertData sent");
            jsonWritingRepository.writeOutputFile(childAlertData);
            return new ResponseEntity<>(childAlertData, HttpStatus.OK);
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneAlertData> getPhoneAlertData(
            @RequestParam("firestation") final int stationNumber) throws IOException {

        PhoneAlertData phoneAlertData = webAppService.getPhoneAlertData(stationNumber);

        if (phoneAlertData == null) {
            logger.error("phoneAlertData is empty");
            jsonWritingRepository.writeOutputFile(null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("phoneAlertData sent");
            jsonWritingRepository.writeOutputFile(phoneAlertData);
            return new ResponseEntity<>(phoneAlertData, HttpStatus.OK);
        }
    }

    @GetMapping("/fire")
    public ResponseEntity<FireData> getFireData(
            @RequestParam("address") final String address) throws IOException {

        FireData fireData = webAppService.getFireData(address);

        if (fireData == null) {
            logger.error("fireData is empty");
            jsonWritingRepository.writeOutputFile(null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("fireData sent");
            jsonWritingRepository.writeOutputFile(fireData);
            return new ResponseEntity<>(fireData, HttpStatus.OK);
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<FloodData> getFloodData(
            @RequestParam("stations") final List<Integer> listOfStationIds)
            throws IOException {

        FloodData floodData = webAppService.getFloodData(listOfStationIds);

        if (floodData == null) {
            logger.error("floodData is empty");
            jsonWritingRepository.writeOutputFile(null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("floodData sent");
            jsonWritingRepository.writeOutputFile(floodData);
            return new ResponseEntity<>(floodData, HttpStatus.OK);
        }
    }

    @GetMapping("/personInfolastName={lastName}")
    public ResponseEntity<InfoData> getInfoData(@PathVariable("lastName") final String lastName)
            throws IOException {

        InfoData infoData = webAppService.getInfoData(lastName);

        if (infoData == null) {
            logger.error("infoData is empty");
            jsonWritingRepository.writeOutputFile(null);
            return new ResponseEntity<InfoData>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("infoData sent");
            jsonWritingRepository.writeOutputFile(infoData);
            return new ResponseEntity<InfoData>(infoData, HttpStatus.OK);
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailData> getCommunityEmailData(
            @RequestParam("city") final String city) throws IOException {

        CommunityEmailData communityEmailData = webAppService.getCommunityEmailData(city);

        if (communityEmailData == null) {
            logger.error("communityEmailData is empty");
            jsonWritingRepository.writeOutputFile(null);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("communityEmailData sent");
            jsonWritingRepository.writeOutputFile(communityEmailData);
            return new ResponseEntity<>(communityEmailData, HttpStatus.OK);
        }
    }
}
