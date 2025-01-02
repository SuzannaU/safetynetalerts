package com.safetynet.safetynetalerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.*;

@Component
public class Mapper {
    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

    public PersonForStation toPersonForStation(Person person) {
        PersonForStation personForStation = new PersonForStation();
        personForStation.setFirstName(person.getFirstName());
        personForStation.setLastName(person.getLastName());
        personForStation.setAddress(person.getAddress());
        personForStation.setCity(person.getCity());
        personForStation.setZip(person.getZip());
        personForStation.setPhone(person.getPhone());

        logger.debug("Mapped to PersonForStation");
        return personForStation;
    }

    public AdultForChildAlert toAdultForChildAlert(Person person) {
        AdultForChildAlert adultForChildAlert = new AdultForChildAlert();
        adultForChildAlert.setFirstName(person.getFirstName());
        adultForChildAlert.setLastName(person.getLastName());

        logger.debug("Mapped to AdultForChildAlert");
        return adultForChildAlert;
    }

    public ChildForChildAlert toChildForChildAlert(Person person) {
        ChildForChildAlert childForChildAlert = new ChildForChildAlert();
        childForChildAlert.setFirstName(person.getFirstName());
        childForChildAlert.setLastName(person.getLastName());
        childForChildAlert.setAge(person.getAge());

        logger.debug("Mapped to ChildForChildAlert");
        return childForChildAlert;
    }

    public PersonForFire toPersonForFire(Person person) {
        PersonForFire personForFire = new PersonForFire();
        personForFire.setLastName(person.getLastName());
        personForFire.setAge(person.getAge());
        personForFire.setPhone(person.getPhone());
        personForFire.setMedications(person.getMedications());
        personForFire.setAllergies(person.getAllergies());

        logger.debug("Mapped to PersonForFire");
        return personForFire;
    }

    public PersonForFlood toPersonForFlood(Person person) {
        PersonForFlood personForFlood = new PersonForFlood();
        personForFlood.setLastName(person.getLastName());
        personForFlood.setPhone(person.getPhone());
        personForFlood.setAge(person.getAge());
        personForFlood.setMedications(person.getMedications());
        personForFlood.setAllergies(person.getAllergies());

        logger.debug("Mapped to PersonForFlodd");
        return personForFlood;
    }

    public PersonForInfo toPersonForInfo(Person p) {
        PersonForInfo personForInfo = new PersonForInfo();
        personForInfo.setLastName(p.getLastName());
        personForInfo.setAddress(p.getAddress());
        personForInfo.setAge(p.getAge());
        personForInfo.setEmail(p.getEmail());
        personForInfo.setMedications(p.getMedications());
        personForInfo.setAllergies(p.getAllergies());

        logger.debug("Mapped to PersonForInfo");
        return personForInfo;
    }

}
