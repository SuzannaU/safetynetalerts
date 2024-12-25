package com.safetynet.safetynetalerts.controller;

import org.springframework.stereotype.Component;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.*;

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

    public AdultForChildAlert toAdultForChildAlert(Person person) {
        AdultForChildAlert adultForChildAlert = new AdultForChildAlert();
        adultForChildAlert.setFirstName(person.getFirstName());
        adultForChildAlert.setLastName(person.getLastName());

        return adultForChildAlert;
    }

    public ChildForChildAlert toChildForChildAlert(Person person) {
        ChildForChildAlert childForChildAlert = new ChildForChildAlert();
        childForChildAlert.setFirstName(person.getFirstName());
        childForChildAlert.setLastName(person.getLastName());
        childForChildAlert.setAge(person.getAge());

        return childForChildAlert;
    }

    public PersonForFire toPersonForFire(Person person){
        PersonForFire personForFire = new PersonForFire();
        personForFire.setLastName(person.getLastName());
        personForFire.setPhone(person.getPhone());
        personForFire.setMedicalRecords(getMedicalRecordForFire(person));

        return personForFire;
    }

    public MedicalRecordForFire getMedicalRecordForFire(Person person){
        MedicalRecordForFire medicalRecordForFire = new MedicalRecordForFire();
        medicalRecordForFire.setMedications(person.getMedications());
        medicalRecordForFire.setAllergies(person.getAllergies());

        return medicalRecordForFire;
    }
}
