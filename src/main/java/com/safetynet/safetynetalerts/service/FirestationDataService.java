package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.controller.Mapper;
import com.safetynet.safetynetalerts.dto.FirestationData;
import com.safetynet.safetynetalerts.dto.PersonForStation;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class FirestationDataService {
    private static final Logger logger = LoggerFactory.getLogger(FirestationDataService.class);
    @Autowired
    PersonService personService;
    @Autowired
    Mapper mapper;

    public FirestationData getFirestationData(int stationNumber) throws IOException {

        List<Person> persons = new ArrayList<>();
        try {
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }
        List<PersonForStation> personsForStation = persons.stream().filter(
                person -> person.getFirestationId() == stationNumber)
                .map(p -> mapper.toPersonForStation(p))
                .collect(Collectors.toList());

        Long numbOfChildren = persons.stream()
                .filter(p -> p.getFirestationId() == stationNumber)
                .filter(p -> p.getCategory().equals("Child")).count();
        Long numbOfAdults = persons.stream()
                .filter(p -> p.getFirestationId() == stationNumber)
                .filter(p -> p.getCategory().equals("Adult")).count();

        Map<String, Long> residents = new HashMap<>();
        residents.put("children", numbOfChildren);
        residents.put("adults", numbOfAdults);

        FirestationData firestationData = new FirestationData();
        firestationData.setPersons(personsForStation);
        firestationData.setCount(residents);

        return firestationData;

    }
}
