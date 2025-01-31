package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.safetynet.safetynetalerts.dto.FirestationData;
import com.safetynet.safetynetalerts.dto.PersonForStation;
import com.safetynet.safetynetalerts.mapper.Mapper;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class FirestationDataServiceTest {
    @MockitoBean
    private PersonService personService;
    @MockitoBean
    private Mapper mapper;
    @Autowired
    private FirestationDataService firestationDataService;
    private List<Person> persons;

    @BeforeEach
    private void setUp() {
        Person john = new Person(
                "john", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        Set<Integer> firestationsIds = Set.of(1, 2);
        john.setFirestationIds(firestationsIds);
        john.setCategory("Adult");
        Person jane = new Person(
                "jane", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        jane.setFirestationIds(firestationsIds);
        jane.setCategory("Child");
        persons = new ArrayList<>();
        persons.add(john);
        persons.add(jane);
    }

    @Test
    public void getFirestationData_withCorrectParameters_returnsData() throws IOException {

        PersonForStation personForStation = new PersonForStation();
        when(personService.getPersons()).thenReturn(persons);
        when(mapper.toPersonForStation(any(Person.class))).thenReturn(personForStation);

        FirestationData result = firestationDataService.getFirestationData(1);

        Map<String, Long> expectedCount = new HashMap<>();
        expectedCount.put("children", 1L);
        expectedCount.put("adults", 1L);

        assertNotNull(result);
        assertEquals(expectedCount, result.getCount());
        assertEquals(2, result.getPersons().size());
        verify(personService).getPersons();
        verify(mapper, Mockito.times(2)).toPersonForStation(any(Person.class));
    }

    @Test
    public void getFirestationData_withUnknownStationNumber_returnsNull() throws IOException {

        when(personService.getPersons()).thenReturn(persons);

        FirestationData result = firestationDataService.getFirestationData(100);

        assertNull(result);
        verify(personService).getPersons();
    }

    @Test
    public void getFirestationData_withIOException_throwsException() throws IOException {

        when(personService.getPersons()).thenThrow(new IOException());

        assertThrows(IOException.class, () -> firestationDataService.getFirestationData(1));

        verify(personService).getPersons();
    }
}
