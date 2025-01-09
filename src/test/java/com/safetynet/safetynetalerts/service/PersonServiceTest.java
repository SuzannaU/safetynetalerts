package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JsonReadingRepository;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;

@SpringBootTest
public class PersonServiceTest {
    @MockitoBean
    private static JsonReadingRepository jsonReadingRepository;
    @MockitoBean
    private static JsonWritingRepository jsonWritingRepository;
    @MockitoBean
    private static MedicalRecordService medicalRecordService;
    @MockitoBean
    private static FirestationService firestationService;
    @Autowired
    PersonService personService;
    List<Person> persons;
    List<MedicalRecord> medicalRecords;
    Set<Firestation> firestations;

    @BeforeEach
    private void setUp() {
        Person john = new Person(
                "john", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        Person jane = new Person(
                "jane", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        persons = new ArrayList<>();
        persons.add(john);
        persons.add(jane);

        MedicalRecord medicalRecord1 = new MedicalRecord(
                "john", "doe", "01/01/2000",
                Arrays.asList("med1", "med2"), Arrays.asList("allergy1"));
        medicalRecord1.setLocalBirthdate(LocalDate.of(2000, 1, 1));
        medicalRecord1.setAge(25);
        MedicalRecord medicalRecord2 = new MedicalRecord(
                "jane", "doe", "01/01/2020",
                Arrays.asList(), Arrays.asList());
        medicalRecord2.setLocalBirthdate(LocalDate.of(2020, 1, 1));
        medicalRecord2.setAge(5);
        medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecord1);
        medicalRecords.add(medicalRecord2);

        Firestation firestation1 = new Firestation("test_address", "1");
        firestation1.setAddresses(Set.of("testaddress1", "test_address"));
        firestation1.setFirestationId(1);
        Firestation firestation2 = new Firestation("test_address", "2");
        firestation2.setAddresses(Set.of("testaddress2", "test_address"));
        firestation2.setFirestationId(2);
        firestations = Set.of(firestation1, firestation2);
    }

    @Test
    public void getPersons_withCorrectParameters_setsAttributes() throws IOException {

        when(jsonReadingRepository.getPersons()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecords()).thenReturn(medicalRecords);
        when(firestationService.getFirestations()).thenReturn(firestations);

        List<Person> personsTest = personService.getPersons();

        Person john = personsTest.stream()
                .filter(p -> p.getFirstName().equals("john"))
                .findFirst().orElseThrow();
        Person jane = personsTest.stream()
                .filter(p -> p.getFirstName().equals("jane"))
                .findFirst().orElseThrow();

        assertEquals("johndoe", john.getPersonId());
        assertEquals(LocalDate.of(2000, 1, 1), john.getBirthdate());
        assertEquals(25, john.getAge());
        assertEquals("Adult", john.getCategory());
        assertTrue(john.getMedications().contains("med1"));
        assertTrue(john.getAllergies().contains("allergy1"));
        assertTrue(john.getFirestationIds().containsAll(Set.of(1, 2)));
        assertEquals("Child", jane.getCategory());
        assertTrue(jane.getMedications().isEmpty());

        verify(jsonReadingRepository).getPersons();
        verify(medicalRecordService).getMedicalRecords();
        verify(firestationService).getFirestations();
    }

    @Test
    public void getPersons_withWrongBirthdate_throwsException() throws IOException {

        when(jsonReadingRepository.getPersons()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecords()).thenReturn(medicalRecords);
        when(firestationService.getFirestations()).thenReturn(firestations);


        Person person = new Person(
                "jack", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        persons.add(person);

        MedicalRecord medicalRecord3 = new MedicalRecord(
                "jack", "doe", "15/32/2020",
                Arrays.asList(), Arrays.asList());
        medicalRecord3.setLocalBirthdate(null);
        medicalRecord3.setAge(0);
        medicalRecords.add(medicalRecord3);

        assertThrows(NullPointerException.class, () -> personService.getPersons());
        verify(jsonReadingRepository).getPersons();
        verify(medicalRecordService).getMedicalRecords();
        verify(firestationService).getFirestations();
    }

    @Test
    public void createPerson_withCorrectParameters_addsNewPerson() throws IOException {

        when(jsonReadingRepository.getPersons()).thenReturn(persons);
        when(jsonWritingRepository.updatePersons(persons)).thenReturn(true);
        Person personToAdd = new Person(
                "jack", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "added_email");

        personService.createPerson(personToAdd);

        String addedEmail = persons.stream()
                .filter(p -> p.getFirstName().equals(personToAdd.getFirstName()))
                .map(p -> p.getEmail())
                .findFirst().orElseThrow();

        assertEquals(personToAdd.getEmail(), addedEmail);
        verify(jsonReadingRepository).getPersons();
        verify(jsonWritingRepository).updatePersons(persons);
    }

    @Test
    public void createPerson_withAlreadyExistingPerson_returnsNull() throws IOException {

        when(jsonReadingRepository.getPersons()).thenReturn(persons);
        lenient().when(jsonWritingRepository.updatePersons(persons)).thenReturn(true);
        Person personToAdd = new Person(
                "john", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");

        assertNull(personService.createPerson(personToAdd));
        verify(jsonReadingRepository).getPersons();
        verify(jsonWritingRepository, Mockito.times(0)).updatePersons(persons);
    }

    @Test
    public void updatePerson_withCorrectParameters_modifiesPerson() throws IOException {

        when(jsonReadingRepository.getPersons()).thenReturn(persons);
        when(jsonWritingRepository.updatePersons(persons)).thenReturn(true);
        Person personToUpdate = new Person(
                "john", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "updated_email");

        personService.updatePerson(personToUpdate);

        String updatedEmail = persons.stream()
                .filter(p -> p.getFirstName().equals(personToUpdate.getFirstName()))
                .map(p -> p.getEmail())
                .findFirst().orElseThrow();

        assertEquals(personToUpdate.getEmail(), updatedEmail);
        verify(jsonReadingRepository).getPersons();
        verify(jsonWritingRepository).updatePersons(persons);
    }

    @Test
    public void updatePerson_withNonExistingPerson_returnsNull() throws IOException {

        when(jsonReadingRepository.getPersons()).thenReturn(persons);
        lenient().when(jsonWritingRepository.updatePersons(persons)).thenReturn(true);
        Person newPerson = new Person(
                "new", "person", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");

        assertNull(personService.updatePerson(newPerson));
        verify(jsonReadingRepository).getPersons();
        verify(jsonWritingRepository, Mockito.times(0)).updatePersons(persons);
    }

    @Test
    public void updatePerson_withNullAttributes_doesNotErase() throws IOException {

        when(jsonReadingRepository.getPersons()).thenReturn(persons);
        when(jsonWritingRepository.updatePersons(persons)).thenReturn(true);
        Person personToUpdate = new Person(
                "john", "doe", null, null, null, null, null);

        personService.updatePerson(personToUpdate);

        Person updatedPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(personToUpdate.getFirstName()))
                .findFirst().orElseThrow();

        assertEquals("test_address", updatedPerson.getAddress());
        assertEquals("test_city", updatedPerson.getCity());
        assertEquals("test_zip", updatedPerson.getZip());
        assertEquals("test_phone", updatedPerson.getPhone());
        assertEquals("test_email", updatedPerson.getEmail());
        verify(jsonReadingRepository).getPersons();
        verify(jsonWritingRepository).updatePersons(persons);
    }

    @Test
    public void deletePerson_withCorrectParameters_deletesPerson() throws IOException {

        when(jsonReadingRepository.getPersons()).thenReturn(persons);
        when(jsonWritingRepository.updatePersons(persons)).thenReturn(true);
        Person personToDelete = new Person(
                "john", "doe", null, null, null, null, null);

        personService.deletePerson(personToDelete);

        Optional<Person> deletedPerson = persons.stream()
                .filter(p -> p.getFirstName().equals(personToDelete.getFirstName()))
                .findFirst();

        assertTrue(deletedPerson.isEmpty());
        verify(jsonReadingRepository).getPersons();
        verify(jsonWritingRepository).updatePersons(persons);
    }

    @Test
    public void deletePerson_withNonExistingPerson_returnsNull() throws IOException {

        when(jsonReadingRepository.getPersons()).thenReturn(persons);
        lenient().when(jsonWritingRepository.updatePersons(persons)).thenReturn(true);
        Person newPerson = new Person(
                "new", "person", null, null, null, null, null);

        assertNull(personService.deletePerson(newPerson));
        verify(jsonReadingRepository).getPersons();
        verify(jsonWritingRepository, Mockito.times(0)).updatePersons(persons);
    }
}

