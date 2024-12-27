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
import com.safetynet.safetynetalerts.dto.PersonDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JsonRepository;

@Service
public class PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    JsonRepository jsonRepository;
    MedicalRecordService medicalRecordService;
    FirestationService firestationService;

    public PersonService(JsonRepository jsonRepository, MedicalRecordService medicalRecordService,
            FirestationService firestationService) {
        this.jsonRepository = jsonRepository;
        this.medicalRecordService = medicalRecordService;
        this.firestationService = firestationService;
    }

    public List<Person> getPersons() throws IOException {
        List<PersonDTO> personsDTO = jsonRepository.getPersonsDTO();
        List<Person> persons = new ArrayList<Person>();

        try {
            for (PersonDTO personDTO : personsDTO) {
                Person person = new Person();
                person.setFirstName(personDTO.getFirstName());
                person.setLastName(personDTO.getLastName());
                person.setAddress(personDTO.getAddress());
                person.setCity(personDTO.getCity());
                person.setZip(personDTO.getZip());
                person.setPhone(personDTO.getPhone());
                person.setEmail(personDTO.getEmail());
                person.setPersonId(personDTO.getFirstName().concat(personDTO.getLastName()));
                person.setBirthdate(getBirthdate(person.getPersonId()));
                person.setAge(getAge(person.getPersonId()));
                if (person.getAge() <= 18) {
                    person.setCategory("Child");
                } else {
                    person.setCategory("Adult");
                } ;
                person.setMedications(getMedications(person.getPersonId()));
                person.setAllergies(getAllergies(person.getPersonId()));
                person.setFirestationId(getFirestationId(person.getAddress()));

                persons.add(person);
            }
        } catch (NullPointerException e) {
            logger.error("personsDTO list is empty");
        }

        return persons;
    }

    private LocalDate getBirthdate(String personId) throws IOException {
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        Optional<LocalDate> birthdate = medicalRecords
                .stream()
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

    private int getAge(String personId) throws IOException {
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        Optional<Integer> age = medicalRecords
                .stream()
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

    private List<String> getMedications(String personId) throws IOException {

        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        List<String> medications = medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getPersonId().equals(personId))
                .map(medicalRecord -> medicalRecord.getMedications())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return medications;
    }

    private List<String> getAllergies(String personId) throws IOException {
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        List<String> allergies = medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.getPersonId().equals(personId))
                .map(medicalRecord -> medicalRecord.getAllergies())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return allergies;
    }

    private int getFirestationId(String address) throws IOException {
        Set<Firestation> firestations = firestationService.getFirestations();
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

    public PersonDTO createPersonDTO(PersonDTO personDTO) throws IOException {
        List<PersonDTO> persons = jsonRepository.getPersonsDTO();

        Optional<PersonDTO> existingPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(personDTO.getFirstName()))
                .filter(p -> p.getLastName().equals(personDTO.getLastName()))
                .findFirst();

        PersonDTO savedPerson = new PersonDTO();
        if (existingPerson.isPresent()) {
            savedPerson = null;
            logger.error("This person already exists");
        } else {
            savedPerson = jsonRepository.createPerson(personDTO);
        }

        return savedPerson;
    }

    public PersonDTO updatePersonDTO(PersonDTO personDTO) throws IOException {
        List<PersonDTO> persons = jsonRepository.getPersonsDTO();

        Optional<PersonDTO> existingPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(personDTO.getFirstName()))
                .filter(p -> p.getLastName().equals(personDTO.getLastName()))
                .findFirst();

        PersonDTO updatedPerson = new PersonDTO();
        if (existingPerson.isPresent()) {
            updatedPerson = jsonRepository.updatePerson(personDTO);
        } else {
            updatedPerson = null;
            logger.error("This person doesn't exist");
        }

        return updatedPerson;
    }

    public PersonDTO deletePersonDTO(PersonDTO personDTO) throws IOException {
        List<PersonDTO> persons = jsonRepository.getPersonsDTO();

        Optional<PersonDTO> existingPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(personDTO.getFirstName()))
                .filter(p -> p.getLastName().equals(personDTO.getLastName()))
                .findFirst();

        PersonDTO deletedPerson = new PersonDTO();
        if (existingPerson.isPresent()) {
            deletedPerson = jsonRepository.deletePerson(personDTO);
        } else {
            deletedPerson = null;
            logger.error("This person doesn't exist");
        }

        return deletedPerson;
    }
}


