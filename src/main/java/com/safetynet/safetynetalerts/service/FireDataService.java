package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.mapper.Mapper;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class FireDataService {
    private static final Logger logger = LoggerFactory.getLogger(FireDataService.class);
    private PersonService personService;
    private Mapper mapper;

    public FireDataService(PersonService personService, Mapper mapper) {
        this.personService = personService;
        this.mapper = mapper;
    }

    public FireData getFireData(String address) throws IOException {
        List<Person> persons;
        try {
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }

        Optional<Set<Integer>> firestationIds = persons.stream()
                .filter(p -> p.getAddress().equals(address))
                .map(p -> p.getFirestationIds())
                .findFirst();

        List<PersonForFire> personsForFire = persons.stream()
                .filter(p -> p.getAddress().equals(address))
                .map(p -> mapper.toPersonForFire(p))
                .collect(Collectors.toList());

        if (firestationIds.isEmpty() || personsForFire.isEmpty()) {
            logger.error("No firestationIds or persons for this address: " + address);
            return null;
        }
        FireData fireData = new FireData();
        fireData.setFirestationIds(firestationIds.get());
        fireData.setResidents(personsForFire);
        return fireData;
    }
}
