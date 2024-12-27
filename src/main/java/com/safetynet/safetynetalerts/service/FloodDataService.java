package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.controller.Mapper;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.*;

@Service
public class FloodDataService {
    private static final Logger logger = LoggerFactory.getLogger(FloodDataService.class);
    @Autowired
    PersonService personService;
    @Autowired
    FirestationService firestationService;
    @Autowired
    Mapper mapper;

    public FloodData getFloodData(List<Integer> listOfStationIds) throws IOException {
        Set<Firestation> firestations = new HashSet<>();
        List<Person> persons = new ArrayList<>();
        try {
            firestations = firestationService.getFirestations();
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }

        Set<FirestationForFlood> firestationsForFlood = new HashSet<>();
        for (int id : listOfStationIds) {

            Optional<Set<String>> addresses = firestations.stream()
                    .filter(f -> f.getFirestationId() == id)
                    .map(f -> f.getAddresses())
                    .findFirst();

            Set<AddressForFlood> addressesForFlood = new HashSet<>();
            for (String a : addresses.get()) {                              // handle if absent
                AddressForFlood addressForFlood = new AddressForFlood();
                List<PersonForFire> residentsByAddress = persons.stream()
                        .filter(p -> p.getAddress().equals(a))
                        .map(p -> mapper.toPersonForFire(p))
                        .collect(Collectors.toList());
                addressForFlood.setAddress(a);
                addressForFlood.setResidents(residentsByAddress);

                addressesForFlood.add(addressForFlood);
            }
            
            FirestationForFlood firestationForFlood = new FirestationForFlood();
            firestationForFlood.setFirestationId(id);
            firestationForFlood.setAddressesForFlood(addressesForFlood);

            firestationsForFlood.add(firestationForFlood);
        }

        FloodData floodData = new FloodData();
        floodData.setStationsForFlood(firestationsForFlood);

        return floodData;
    }



}
