package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.Mapper;
import com.safetynet.safetynetalerts.dto.FirestationData;
import com.safetynet.safetynetalerts.dto.PersonForStation;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class FirestationDataService {
    private static final Logger logger = LoggerFactory.getLogger(FirestationDataService.class);
    private PersonService personService;
    private Mapper mapper;

    public FirestationDataService(PersonService personService, Mapper mapper) {
        this.personService = personService;
        this.mapper = mapper;
    }

    public FirestationData getFirestationData(int stationNumber) throws IOException {
        List<Person> persons;
        try {
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }

        List<PersonForStation> personsForStation = persons.stream()
                .filter(p -> p.getFirestationIds().contains(stationNumber))
                .map(p -> mapper.toPersonForStation(p))
                .collect(Collectors.toList());

        Long numbOfChildren = persons.stream()
                .filter(p -> p.getFirestationIds().contains(stationNumber))
                .filter(p -> p.getCategory().equals("Child"))
                .count();
        Long numbOfAdults = persons.stream()
                .filter(p -> p.getFirestationIds().contains(stationNumber))
                .filter(p -> p.getCategory().equals("Adult"))
                .count();

        Map<String, Long> count = new HashMap<>();
        count.put("children", numbOfChildren);
        count.put("adults", numbOfAdults);

        if (personsForStation.isEmpty()) {
            logger.error("No persons found for this station number: " + stationNumber);
            return null;
        }
        
        FirestationData firestationData = new FirestationData();
        firestationData.setPersons(personsForStation);
        firestationData.setCount(count);
        return firestationData;
    }
}
