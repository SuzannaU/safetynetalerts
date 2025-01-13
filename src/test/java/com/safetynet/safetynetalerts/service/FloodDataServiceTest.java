package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.safetynet.safetynetalerts.Mapper;
import com.safetynet.safetynetalerts.dto.FloodData;
import com.safetynet.safetynetalerts.dto.PersonForFlood;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class FloodDataServiceTest {
    @MockitoBean
    private PersonService personService;
    @MockitoBean
    private FirestationService firestationService;
    @MockitoBean
    private Mapper mapper;
    @Autowired
    private FloodDataService floodDataService;
    private List<Person> persons;
    private Set<Firestation> firestations;

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

        Firestation firestation1 = new Firestation("test_address", "1");
        firestation1.setAddresses(Set.of("testaddress1", "test_address"));
        firestation1.setFirestationId(1);
        Firestation firestation2 = new Firestation("test_address", "2");
        firestation2.setAddresses(Set.of("testaddress2", "test_address"));
        firestation2.setFirestationId(2);
        firestations = Set.of(firestation1, firestation2);
    }

    @Test
    public void getFloodData_withCorrectParameters_returnsData() throws IOException {

        PersonForFlood personForFlood = new PersonForFlood();
        List<Integer> listOfStationIds = Arrays.asList(1,2);
        when(personService.getPersons()).thenReturn(persons);
        when(firestationService.getFirestations()).thenReturn(firestations);
        when(mapper.toPersonForFlood(any(Person.class))).thenReturn(personForFlood);

        FloodData result = floodDataService.getFloodData(listOfStationIds);

        assertNotNull(result);
        verify(personService).getPersons();
        verify(mapper, Mockito.times(4)).toPersonForFlood(any(Person.class));
    }

    @Test
    public void getFloodData_withOneUnknownId_returnsData() throws IOException {

        PersonForFlood personForFlood = new PersonForFlood();
        List<Integer> listOfStationIds = Arrays.asList(1,20);
        when(personService.getPersons()).thenReturn(persons);
        when(firestationService.getFirestations()).thenReturn(firestations);
        when(mapper.toPersonForFlood(any(Person.class))).thenReturn(personForFlood);

        FloodData result = floodDataService.getFloodData(listOfStationIds);

        assertNotNull(result);
        verify(personService).getPersons();
        verify(mapper, Mockito.times(2)).toPersonForFlood(any(Person.class));
    }

    @Test
    public void getFloodData_withUnknownIds_returnsNull() throws IOException {

        List<Integer> listOfUnknownStationIds = Arrays.asList(10,20);
        when(personService.getPersons()).thenReturn(persons);

        FloodData result = floodDataService.getFloodData(listOfUnknownStationIds);

        assertNull(result);
        verify(personService).getPersons();
    }

    @Test
    public void getFloodData_withIOException_throwsException() throws IOException {

        List<Integer> listOfStationIds = Arrays.asList(1,2);
        when(personService.getPersons()).thenThrow(new IOException());
        assertThrows(IOException.class,
                () -> floodDataService.getFloodData(listOfStationIds));

        verify(personService).getPersons();
    }
}
