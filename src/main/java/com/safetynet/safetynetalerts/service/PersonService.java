package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonRawData;
import com.safetynet.safetynetalerts.repository.JsonReadingRepository;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;

@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    JsonReadingRepository jsonReadingRepository;
    JsonWritingRepository jsonWritingRepository;
    MedicalRecordService medicalRecordService;
    FirestationService firestationService;

    public PersonService(JsonReadingRepository jsonReadingRepository,
            JsonWritingRepository jsonWritingRepository, MedicalRecordService medicalRecordService,
            FirestationService firestationService) {
        this.jsonReadingRepository = jsonReadingRepository;
        this.jsonWritingRepository = jsonWritingRepository;
        this.medicalRecordService = medicalRecordService;
        this.firestationService = firestationService;
    }

    public List<Person> getPersons() throws IOException {
        List<PersonRawData> personsRawData = jsonReadingRepository.getPersonsRawData();
        List<Person> persons = new ArrayList<Person>();
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        Set<Firestation> firestations = firestationService.getFirestations();

        try {
            for (PersonRawData personRawData : personsRawData) {
                Person person = new Person();
                person.setFirstName(personRawData.getFirstName());
                person.setLastName(personRawData.getLastName());
                person.setAddress(personRawData.getAddress());
                person.setCity(personRawData.getCity());
                person.setZip(personRawData.getZip());
                person.setPhone(personRawData.getPhone());
                person.setEmail(personRawData.getEmail());
                person.setPersonId(personRawData.getFirstName().concat(personRawData.getLastName()));
                person.setBirthdate(getBirthdate(person.getPersonId(), medicalRecords));
                person.setAge(getAge(person.getPersonId(), medicalRecords));
                if (person.getAge() <= 18)
                    person.setCategory("Child");
                else
                    person.setCategory("Adult");
                person.setMedications(getMedications(person.getPersonId(), medicalRecords));
                person.setAllergies(getAllergies(person.getPersonId(), medicalRecords));
                person.setFirestationId(getFirestationId(person.getAddress(), firestations));

                persons.add(person);
            }
        } catch (NullPointerException e) {
            logger.error("personsRawData list is empty");
        }

        return persons;
    }

    private LocalDate getBirthdate(String personId, List<MedicalRecord> medicalRecords) {
        Optional<LocalDate> birthdate = medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getPersonId().equals(personId))
                .map(medicalRecord -> medicalRecord.getBirthdate())
                .findFirst();

        if (birthdate.isPresent()) {
            return birthdate.get();
        } else {
            logger.error("No birthdate");
            return null;
        }
    }

    private int getAge(String personId, List<MedicalRecord> medicalRecords) {
        Optional<Integer> age = medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getPersonId().equals(personId))
                .map(medicalRecord -> medicalRecord.getAge())
                .findFirst();

        if (age.isPresent()) {
            return age.get();
        } else {
            logger.error("No age");
            return 999;
        }
    }

    private List<String> getMedications(String personId, List<MedicalRecord> medicalRecords) {
        List<String> medications = medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getPersonId().equals(personId))
                .map(medicalRecord -> medicalRecord.getMedications())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return medications;
    }

    private List<String> getAllergies(String personId, List<MedicalRecord> medicalRecords) {
        List<String> allergies = medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getPersonId().equals(personId))
                .map(medicalRecord -> medicalRecord.getAllergies())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return allergies;
    }

    private int getFirestationId(String address, Set<Firestation> firestations) {
        Optional<Integer> firestationId = firestations.stream()
                .filter(firestation -> firestation.getAddresses().contains(address))
                .map(firestation -> firestation.getFirestationId())
                .findFirst();

        if (firestationId.isPresent()) {
            return firestationId.get();
        } else {
            logger.error("No firestationId");
            return 0;
        }
    }

    public PersonRawData createPersonRawData(PersonRawData personRawData) throws IOException{
        List<PersonRawData> personsRawData = jsonReadingRepository.getPersonsRawData();

        Optional<PersonRawData> existingPerson = personsRawData.stream()
                .filter(p -> p.getFirstName().equals(personRawData.getFirstName()))
                .filter(p -> p.getLastName().equals(personRawData.getLastName()))
                .findFirst();

        if (existingPerson.isPresent()) {
            logger.error("This person already exists");
            return null;
        }

        personsRawData.add(personRawData);
        jsonWritingRepository.updatePersons(personsRawData);

        return personRawData;
    }

    public PersonRawData updatePersonRawData(PersonRawData personRawData) throws IOException {
        List<PersonRawData> personsRawData = jsonReadingRepository.getPersonsRawData();

        Optional<PersonRawData> existingPerson = personsRawData.stream()
                .filter(p -> p.getFirstName().equals(personRawData.getFirstName()))
                .filter(p -> p.getLastName().equals(personRawData.getLastName()))
                .findFirst();

        if (existingPerson.isEmpty()) {
            logger.error("This person doesn't exist");
            return null;
        }

        
        PersonRawData updatedPerson = new PersonRawData(personRawData.getFirstName(),personRawData.getLastName());
        updatedPerson.setAddress(
                Optional.ofNullable(personRawData.getAddress())
                        .orElse(existingPerson.get().getAddress()));
        updatedPerson.setCity(
                Optional.ofNullable(personRawData.getCity())
                        .orElse(existingPerson.get().getCity()));
        updatedPerson.setZip(
                Optional.ofNullable(personRawData.getZip())
                        .orElse(existingPerson.get().getZip()));
        updatedPerson.setPhone(
                Optional.ofNullable(personRawData.getPhone())
                        .orElse(existingPerson.get().getPhone()));
        updatedPerson.setEmail(
                Optional.ofNullable(personRawData.getEmail())
                        .orElse(existingPerson.get().getEmail()));

        personsRawData.remove(existingPerson.get());
        personsRawData.add(updatedPerson);
        jsonWritingRepository.updatePersons(personsRawData);

        return updatedPerson;
    }

    public PersonRawData deletePersonRawData(PersonRawData personRawData) throws IOException {
        List<PersonRawData> personsRawData = jsonReadingRepository.getPersonsRawData();

        Optional<PersonRawData> existingPerson = personsRawData.stream()
                .filter(p -> p.getFirstName().equals(personRawData.getFirstName()))
                .filter(p -> p.getLastName().equals(personRawData.getLastName()))
                .findFirst();

        if (existingPerson.isEmpty()) {
            logger.error("This person doesn't exist");
            return null;
        }

        personsRawData.remove(existingPerson.get());
        jsonWritingRepository.updatePersons(personsRawData);

        return existingPerson.get();
    }
}


