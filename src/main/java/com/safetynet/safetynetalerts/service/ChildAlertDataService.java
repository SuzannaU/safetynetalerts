package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.controller.Mapper;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class ChildAlertDataService {
    private static final Logger logger = LoggerFactory.getLogger(FirestationDataService.class);
    @Autowired
    PersonService personService;
    @Autowired
    Mapper mapper;

    public ChildAlertData getChildAlertData(String address) throws IOException {
        List<Person> persons = new ArrayList<>();
        try {
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }

        List<ChildForChildAlert> childrenForChildAlert = persons.stream()
                .filter(p -> p.getAddress().equals(address))
                .filter(p -> p.getAge() <= 18)
                .map(p -> mapper.toChildForChildAlert(p))
                .collect(Collectors.toList());

        List<AdultForChildAlert> adultsForChildAlert = persons.stream()
                .filter(p -> p.getAddress().equals(address))
                .filter(p -> p.getAge() > 18)
                .map(p -> mapper.toAdultForChildAlert(p))
                .collect(Collectors.toList());

        ChildAlertData childAlertData = new ChildAlertData();
        childAlertData.setChildren(childrenForChildAlert);
        childAlertData.setAdults(adultsForChildAlert);

        return childAlertData;
    }

}
