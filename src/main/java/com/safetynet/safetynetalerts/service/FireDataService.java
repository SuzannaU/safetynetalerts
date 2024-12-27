package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.controller.Mapper;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class FireDataService {
    private static final Logger logger = LoggerFactory.getLogger(FireDataService.class);
    @Autowired
    PersonService personService;
    @Autowired
    Mapper mapper;

    public FireData getFireData(String address) throws IOException {
        List<Person> persons = new ArrayList<>();
        try {
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }

        List<PersonForFire> personsForFire = persons.stream()
                .filter(p -> p.getAddress().equals(address))
                .map(p -> mapper.toPersonForFire(p))
                .collect(Collectors.toList());

        Optional<Integer> firestationId = persons.stream()
                .filter(p -> p.getAddress().equals(address))
                .map(p -> p.getFirestationId())
                .findFirst();

        FireData fireData = new FireData();
        firestationId.ifPresentOrElse(
                id -> fireData.setFirestationId(id),
                () -> logger.error("No firestationId"));
        fireData.setResidents(personsForFire);

        return fireData;
    }
}
