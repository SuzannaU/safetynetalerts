package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.dto.CommunityEmailData;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class CommunityEmailDataService {
    private static final Logger logger = LoggerFactory.getLogger(CommunityEmailDataService.class);
    private PersonService personService;

    public CommunityEmailDataService(PersonService personService) {
        this.personService = personService;
    }

    public CommunityEmailData getCommunityEmailData(String city) throws IOException {
        List<Person> persons;
        try {
            persons = personService.getPersons();
        } catch (IOException e) {
            logger.error("Unable to retrieve data");
            throw e;
        }

        Set<String> emails = persons.stream()
                .filter(p -> p.getCity().equals(city))
                .map(p -> p.getEmail())
                .collect(Collectors.toSet());

        if (emails.isEmpty()) {
            logger.error("No emails related to this city: " + city);
            return null;
        }

        CommunityEmailData communityEmailData = new CommunityEmailData();
        communityEmailData.setEmails(emails);
        return communityEmailData;
    }
}
