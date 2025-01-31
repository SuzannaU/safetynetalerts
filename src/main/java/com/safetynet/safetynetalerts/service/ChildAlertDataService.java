package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.mapper.Mapper;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class ChildAlertDataService {
    private static final Logger logger = LoggerFactory.getLogger(ChildAlertDataService.class);
    private PersonService personService;
    private Mapper mapper;

    public ChildAlertDataService(PersonService personService, Mapper mapper) {
        this.personService = personService;
        this.mapper = mapper;
    }

    public ChildAlertData getChildAlertData(String address) throws IOException {

        List<Person> persons;
        try {
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }

        List<ChildForChildAlert> childrenForChildAlert = persons.stream()
                .filter(p -> p.getAddress().equals(address))
                .filter(p -> p.getCategory().equals("Child"))
                .map(p -> mapper.toChildForChildAlert(p))
                .collect(Collectors.toList());

        List<AdultForChildAlert> adultsForChildAlert = persons.stream()
                .filter(p -> p.getAddress().equals(address))
                .filter(p -> p.getCategory().equals("Adult"))
                .map(p -> mapper.toAdultForChildAlert(p))
                .collect(Collectors.toList());

        if (childrenForChildAlert.isEmpty() && adultsForChildAlert.isEmpty()) {
            return null;
        }

        ChildAlertData childAlertData = new ChildAlertData();
        childAlertData.setChildren(childrenForChildAlert);
        childAlertData.setAdults(adultsForChildAlert);
        return childAlertData;
    }
}
