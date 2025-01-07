package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.time.LocalDate;
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

    public List<Person> getPersons() throws IOException, NullPointerException {

        List<Person> persons = jsonReadingRepository.getPersons();
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        Set<Firestation> firestations = firestationService.getFirestations();

        try {
            for (Person person : persons) {
                person.setBirthdate(getBirthdate(person.getPersonId(), medicalRecords));
                person.setAge(getAge(person.getPersonId(), medicalRecords));
                if (person.getAge() <= 18)
                    person.setCategory("Child");
                else
                    person.setCategory("Adult");
                person.setMedications(getMedications(person.getPersonId(), medicalRecords));
                person.setAllergies(getAllergies(person.getPersonId(), medicalRecords));
                person.setFirestationIds(getFirestationIds(person.getAddress(), firestations));
            }
        } catch (NullPointerException e) {
            logger.error("Missing attribute age or birthdate");
            throw e;
        }

        return persons;
    }

    private LocalDate getBirthdate(String personId, List<MedicalRecord> medicalRecords) {
        try {
            Optional<LocalDate> birthdate = medicalRecords.stream()
                    .filter(medicalRecord -> medicalRecord.getPersonId().equals(personId))
                    .map(medicalRecord -> medicalRecord.getLocalBirthdate())
                    .findFirst();


            if (birthdate.isPresent() && birthdate.get() != null) {
                return birthdate.get();
            } else {
                logger.error("No birthdate");
                return null;
            }
        } catch (NullPointerException e) {
            logger.error("birthdate is missing from medical records");
            throw e;
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
            throw new IllegalArgumentException();
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

    private Set<Integer> getFirestationIds(String address, Set<Firestation> firestations) {
        Set<Integer> firestationIds = firestations.stream()
                .filter(firestation -> firestation.getAddresses().contains(address))
                .map(firestation -> firestation.getFirestationId())
                .collect(Collectors.toSet());

        if (firestationIds.isEmpty()) {
            logger.error("No firestationId");
            return null;
        } else {
            return firestationIds;
        }
    }

    public Person createPerson(Person person) throws IOException {
        List<Person> persons = jsonReadingRepository.getPersons();

        Optional<Person> existingPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(person.getFirstName()))
                .filter(p -> p.getLastName().equals(person.getLastName()))
                .findFirst();

        if (existingPerson.isPresent()) {
            logger.error("This person already exists");
            return null;
        }

        persons.add(person);
        jsonWritingRepository.updatePersons(persons);

        return person;
    }

    public Person updatePerson(Person person) throws IOException {
        List<Person> persons = jsonReadingRepository.getPersons();

        Optional<Person> existingPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(person.getFirstName()))
                .filter(p -> p.getLastName().equals(person.getLastName()))
                .findFirst();

        if (existingPerson.isEmpty()) {
            logger.error("This person doesn't exist");
            return null;
        }

        Person updatedPerson = new Person(
                person.getFirstName(), person.getLastName(), null, null, null, null, null);

        if (person.getAddress() == null
                || person.getAddress().trim().isEmpty())
            updatedPerson.setAddress(existingPerson.get().getAddress());
        else
            updatedPerson.setAddress(person.getAddress());

        if (person.getCity() == null
                || person.getCity().trim().isEmpty())
            updatedPerson.setCity(existingPerson.get().getCity());
        else
            updatedPerson.setCity(person.getCity());

        if (person.getZip() == null
                || person.getZip().trim().isEmpty())
            updatedPerson.setZip(existingPerson.get().getZip());
        else
            updatedPerson.setZip(person.getZip());

        if (person.getPhone() == null
                || person.getPhone().trim().isEmpty())
            updatedPerson.setPhone(existingPerson.get().getPhone());
        else
            updatedPerson.setPhone(person.getPhone());

        if (person.getEmail() == null
                || person.getEmail().trim().isEmpty())
            updatedPerson.setEmail(existingPerson.get().getEmail());
        else
            updatedPerson.setEmail(person.getEmail());

        persons.remove(existingPerson.get());
        persons.add(updatedPerson);
        jsonWritingRepository.updatePersons(persons);

        return updatedPerson;
    }

    public Person deletePerson(Person person) throws IOException {
        List<Person> persons = jsonReadingRepository.getPersons();

        Optional<Person> existingPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(person.getFirstName()))
                .filter(p -> p.getLastName().equals(person.getLastName()))
                .findFirst();

        if (existingPerson.isEmpty()) {
            logger.error("This person doesn't exist");
            return null;
        }

        persons.remove(existingPerson.get());
        jsonWritingRepository.updatePersons(persons);

        return existingPerson.get();
    }
}


