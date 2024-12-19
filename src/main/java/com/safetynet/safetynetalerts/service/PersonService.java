package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonFromJson;
import com.safetynet.safetynetalerts.repository.JsonRepository;

@Service
public class PersonService {
    JsonRepository jsonRepository;
    MedicalRecordService medicalRecordService;

    public PersonService(JsonRepository jsonRepository, MedicalRecordService medicalRecordService) {
        this.jsonRepository = jsonRepository;
        this.medicalRecordService = medicalRecordService;
    }

    public List<Person> getPersons() throws IOException {
        List<PersonFromJson> personsFromJson = jsonRepository.getPersonsFromJson();
        List<Person> persons = new ArrayList<Person>();

        for (PersonFromJson personFromJson : personsFromJson) {
            Person person = new Person();
            person.setFirstName(personFromJson.getFirstName());
            person.setLastName(personFromJson.getLastName());
            person.setAddress(personFromJson.getAddress());
            person.setCity(personFromJson.getCity());
            person.setZip(personFromJson.getZip());
            person.setPhone(personFromJson.getPhone());
            person.setEmail(personFromJson.getEmail());
            person.setPersonId(personFromJson.getFirstName().concat(personFromJson.getLastName()));
            person.setBirthdate(setBirthdate(person.getPersonId()));
            person.setAge(setAge(person.getPersonId()));

            persons.add(person);
        }

        return persons;
    }

    private LocalDate setBirthdate(String personId) throws IOException {
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        Optional<LocalDate> birthdate = medicalRecords
                .stream()
                .filter(medicalRecord -> medicalRecord.getPersonId().equals(personId))
                .map(medicalRecord -> medicalRecord.getBirthdate())
                .findFirst();

        return birthdate.get();
    }

    private int setAge(String personId) throws IOException {
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        Optional<Integer> age = medicalRecords
                .stream()
                .filter(medicalRecord -> medicalRecord.getPersonId().equals(personId))
                .map(medicalRecord -> medicalRecord.getAge())
                .findFirst();

        /*
         * age.ifPresentOrElse(n -> System.out.println ("appel de setAge" + n), () ->
         * System.out.println ("no one matches")); => for loggers?
         */

        return age.orElse(-1);
    }


}


