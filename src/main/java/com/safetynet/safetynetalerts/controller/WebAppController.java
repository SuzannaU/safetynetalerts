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

/**
 * Controller for handling requests that need specific data as response
 * 
 * <p>
 * This controller is responsible for handling only GET requests by returning the required data.
 * </p>
 * 
 */
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
     * Handles NullPointerException.
     * 
     * @param e the exception to handle
     * @return a response entity with BAD_REQUEST HTTP status code
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error due to missing age or birthdate");
    }

    /**
     * Retrieves firestation data based on the station number. It returns the list of residents
     * served by this station, with their name, address, and phone number. It also gives a count of
     * adults and children.
     *
     * @param stationNumber the station number to retrieve data for
     * @return a response entity with the firestation data and OK HTTP status code, or NOT_FOUND
     *         HTTP status code if no firestation matches the number
     * @throws IOException if an I/O error occurs
     */
    @GetMapping("/firestation")
    public ResponseEntity<Object> getFirestationData(
            @RequestParam("stationNumber") final int stationNumber) throws IOException {

        logger.debug("GET request for firestation received for station number: " + stationNumber);
        FirestationData firestationData = webAppService.getFirestationData(stationNumber);

        if (firestationData == null) {
            logger.error("firestationData is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("firestationData sent");
            jsonWritingRepository.writeOutputFile(firestationData);
            return new ResponseEntity<>(firestationData, HttpStatus.OK);
        }
    }

    /**
     * Retrieves child alert data based on the address. It returns a list of children living at this
     * address, with their name and age, and a list of other persons living at that address.
     *
     * @param address the address to retrieve data for
     * @return a response entity with the child alert data and OK HTTP status code, or NOT_FOUND
     *         HTTP status code if the address is not found in the source data.
     * @throws IOException if an I/O error occurs
     */
    @GetMapping("/childAlert")
    public ResponseEntity<Object> getChildAlertData(
            @RequestParam("address") final String address) throws IOException {

        logger.debug("GET request for child alert received for address: " + address);
        ChildAlertData childAlertData = webAppService.getChildAlertData(address);

        if (childAlertData == null) {
            logger.error("childAlertData is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (childAlertData.getChildren().isEmpty()) {
            logger.error("No children live at this address");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("childAlertData sent");
            jsonWritingRepository.writeOutputFile(childAlertData);
            return new ResponseEntity<>(childAlertData, HttpStatus.OK);
        }
    }

    /**
     * Retrieves phone alert data based on a station number. It returns a list of all the phone
     * numbers of the people served by the firestation.
     *
     * @param stationNumber the station number to retrieve data for
     * @return a response entity with the phone alert data and OK HTTP status code, or NOT_FOUND
     *         HTTP status code if no firestation matches the number.
     * @throws IOException if an I/O error occurs
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<Object> getPhoneAlertData(
            @RequestParam("firestation") final int stationNumber) throws IOException {

        logger.debug("GET request for phone alert received for station number: " + stationNumber);
        PhoneAlertData phoneAlertData = webAppService.getPhoneAlertData(stationNumber);

        if (phoneAlertData == null) {
            logger.error("phoneAlertData is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("phoneAlertData sent");
            jsonWritingRepository.writeOutputFile(phoneAlertData);
            return new ResponseEntity<>(phoneAlertData, HttpStatus.OK);
        }
    }

    /**
     * Retrieves fire data based on the address. It returns a list of persons living at this
     * address, with their name, phone number, age, and medical records.
     *
     * @param address the address to retrieve data for
     * @return a response entity with the fire data and OK HTTP status code, or NOT_FOUND HTTP
     *         status code if the address is not found in the source data.
     * @throws IOException if an I/O error occurs
     */
    @GetMapping("/fire")
    public ResponseEntity<Object> getFireData(
            @RequestParam("address") final String address) throws IOException {

        logger.debug("GET request for fire alert received for address: " + address);
        FireData fireData = webAppService.getFireData(address);

        if (fireData == null) {
            logger.error("fireData is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("fireData sent");
            jsonWritingRepository.writeOutputFile(fireData);
            return new ResponseEntity<>(fireData, HttpStatus.OK);
        }
    }

    /**
     * Retrieves flood station data based on a list of station numbers. For each of the station
     * numbers, it returns the addresses it serves and a list of persons living at each address,
     * with their name, phone number, age, and medical records.
     *
     * @param listOfStationIds the station numbers to retrieve data for
     * @return a response entity with the stations data and OK HTTP status code, or NOT_FOUND HTTP
     *         status code if no firestation matches any of the numbers.
     * @throws IOException if an I/O error occurs
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<Object> getFloodData(
            @RequestParam("stations") final List<Integer> listOfStationIds)
            throws IOException {

        logger.debug(
                "GET request for flood alert received for station numbers: " + listOfStationIds);
        FloodData floodData = webAppService.getFloodData(listOfStationIds);

        if (floodData == null) {
            logger.error("floodData is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("floodData sent");
            jsonWritingRepository.writeOutputFile(floodData);
            return new ResponseEntity<>(floodData, HttpStatus.OK);
        }
    }

    /**
     * Retrieves persons info data based on the last name. It returns a list of persons sharing that
     * last name, with their first name, address, age, email, and medical records.
     *
     * @param lastName the last name to retrieve data for
     * @return a response entity with the info data and OK HTTP status code, or NOT_FOUND HTTP
     *         status code if the last name is not found in the source data.
     * @throws IOException if an I/O error occurs
     */
    @GetMapping("/personInfolastName={lastName}")
    public ResponseEntity<Object> getInfoData(@PathVariable("lastName") final String lastName)
            throws IOException {

        logger.debug("GET request for info received for last name: " + lastName);
        InfoData infoData = webAppService.getInfoData(lastName);

        if (infoData == null) {
            logger.error("infoData is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("infoData sent");
            jsonWritingRepository.writeOutputFile(infoData);
            return new ResponseEntity<>(infoData, HttpStatus.OK);
        }
    }

    /**
     * Retrieves community email data based on a city name. It returns a list of the emails of the
     * peole of that city.
     *
     * @param city the name of the city to retrieve data for
     * @return a response entity with the community email data and OK HTTP status code, or NOT_FOUND
     *         HTTP status code if the city is not in the source data.
     * @throws IOException if an I/O error occurs
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<Object> getCommunityEmailData(
            @RequestParam("city") final String city) throws IOException {

        logger.debug("GET request for email data received for city: " + city);
        CommunityEmailData communityEmailData = webAppService.getCommunityEmailData(city);

        if (communityEmailData == null) {
            logger.error("communityEmailData is empty");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            logger.info("communityEmailData sent");
            jsonWritingRepository.writeOutputFile(communityEmailData);
            return new ResponseEntity<>(communityEmailData, HttpStatus.OK);
        }
    }
}
