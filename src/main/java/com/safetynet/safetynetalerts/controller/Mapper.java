package com.safetynet.safetynetalerts.controller;

import org.springframework.stereotype.Component;
import com.safetynet.safetynetalerts.dto.PersonForStation;
import com.safetynet.safetynetalerts.model.Person;

@Component
public class Mapper {

    /*
     * methods to convert: - model objects to DTO (GetMapping) - DTO to model objects (PostMapping,
     * PutMapping, DeleteMapping)
     */

    public PersonForStation toPersonForStation(Person person) {
        PersonForStation personForStation = new PersonForStation();
        personForStation.setFirstName(person.getFirstName());
        personForStation.setLastName(person.getLastName());
        personForStation.setAddress(person.getAddress());
        personForStation.setCity(person.getCity());
        personForStation.setZip(person.getZip());
        personForStation.setPhone(person.getPhone());

        return personForStation;
    }

}
