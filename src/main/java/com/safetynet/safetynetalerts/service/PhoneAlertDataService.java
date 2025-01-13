package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.dto.PhoneAlertData;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class PhoneAlertDataService {
    private static final Logger logger = LoggerFactory.getLogger(PhoneAlertDataService.class);
    private PersonService personService;

    public PhoneAlertDataService(PersonService personService) {
        this.personService = personService;
    }

    public PhoneAlertData getPhoneAlertData(int firestationId) throws IOException {
        List<Person> persons = new ArrayList<>();
        try {
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }
        Set<String> phones = persons.stream()
                .filter(p -> p.getFirestationIds().contains(firestationId))
                .map(p -> p.getPhone())
                .collect(Collectors.toSet());

        PhoneAlertData phoneAlertData = new PhoneAlertData();
        if (phones.isEmpty()) {
            logger.error("No phones related to station: " + firestationId);
            return null;
        } else {
            phoneAlertData.setPhones(phones);
            return phoneAlertData;
        }
    }
}
