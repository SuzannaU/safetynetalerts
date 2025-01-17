package com.safetynet.safetynetalerts.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.*;

@Component
public class Mapper {
    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

    public PersonForStation toPersonForStation(Person p) {
        PersonForStation personForStation = new PersonForStation();
        personForStation.setFirstName(p.getFirstName());
        personForStation.setLastName(p.getLastName());
        personForStation.setAddress(p.getAddress());
        personForStation.setPhone(p.getPhone());

        logger.debug("Mapped to PersonForStation");
        return personForStation;
    }

    public AdultForChildAlert toAdultForChildAlert(Person p) {
        AdultForChildAlert adultForChildAlert = new AdultForChildAlert();
        adultForChildAlert.setFirstName(p.getFirstName());
        adultForChildAlert.setLastName(p.getLastName());

        logger.debug("Mapped to AdultForChildAlert");
        return adultForChildAlert;
    }

    public ChildForChildAlert toChildForChildAlert(Person p) {
        ChildForChildAlert childForChildAlert = new ChildForChildAlert();
        childForChildAlert.setFirstName(p.getFirstName());
        childForChildAlert.setLastName(p.getLastName());
        childForChildAlert.setAge(p.getAge());

        logger.debug("Mapped to ChildForChildAlert");
        return childForChildAlert;
    }

    public PersonForFire toPersonForFire(Person p) {
        PersonForFire personForFire = new PersonForFire();
        personForFire.setFirstName(p.getFirstName());
        personForFire.setLastName(p.getLastName());
        personForFire.setAge(p.getAge());
        personForFire.setPhone(p.getPhone());
        personForFire.setMedications(p.getMedications());
        personForFire.setAllergies(p.getAllergies());

        logger.debug("Mapped to PersonForFire");
        return personForFire;
    }

    public PersonForFlood toPersonForFlood(Person p) {
        PersonForFlood personForFlood = new PersonForFlood();
        personForFlood.setFirstName(p.getFirstName());
        personForFlood.setLastName(p.getLastName());
        personForFlood.setPhone(p.getPhone());
        personForFlood.setAge(p.getAge());
        personForFlood.setMedications(p.getMedications());
        personForFlood.setAllergies(p.getAllergies());

        logger.debug("Mapped to PersonForFlodd");
        return personForFlood;
    }

    public PersonForInfo toPersonForInfo(Person p) {
        PersonForInfo personForInfo = new PersonForInfo();
        personForInfo.setFirstName(p.getFirstName());
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
