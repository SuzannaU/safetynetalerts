package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.dto.InfoData;
import com.safetynet.safetynetalerts.dto.PersonForInfo;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.controller.Mapper;

@Service
public class InfoDataService {
    private static final Logger logger = LoggerFactory.getLogger(FirestationDataService.class);
    @Autowired
    PersonService personService;
    @Autowired
    Mapper mapper;

    public InfoData getInfoData(String lastName) throws IOException {
        List<Person> persons = new ArrayList<>();
        try {
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }

        List<PersonForInfo> personsForInfo = persons.stream()
                .filter(p -> p.getLastName().equals(lastName))
                .map(p -> mapper.toPersonForInfo(p))
                .collect(Collectors.toList());

        InfoData infoData = new InfoData();
        infoData.setLastName(lastName);
        infoData.setPersons(personsForInfo);

        return infoData;
    }



}
