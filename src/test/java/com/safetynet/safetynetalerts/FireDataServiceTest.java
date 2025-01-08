package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.safetynet.safetynetalerts.controller.Mapper;
import com.safetynet.safetynetalerts.dto.FireData;
import com.safetynet.safetynetalerts.dto.PersonForFire;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.FireDataService;
import com.safetynet.safetynetalerts.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class FireDataServiceTest {
    @Mock
    private static PersonService personService;
    @Mock
    private static Mapper mapper;
    FireDataService fireDataService;
    List<Person> persons;

    @BeforeEach
    private void setUp() {
        fireDataService = new FireDataService(personService, mapper);
        Person john = new Person(
                "john", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        Set<Integer> firestationsIds = Set.of(1, 2);
        john.setFirestationIds(firestationsIds);
        Person jane = new Person(
                "jane", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        jane.setFirestationIds(firestationsIds);
        persons = new ArrayList<>();
        persons.add(john);
        persons.add(jane);
    }

    @Test
    public void getFireData_withCorrectParameters_returnsData() throws IOException {

        PersonForFire personForFire = new PersonForFire();
        when(personService.getPersons()).thenReturn(persons);
        when(mapper.toPersonForFire(any(Person.class))).thenReturn(personForFire);

        FireData result = fireDataService.getFireData("test_address");

        assertNotNull(result);
        assertEquals(2, result.getResidents().size());        
        Set<Integer> firestationsIds = Set.of(1, 2);
        assertIterableEquals(firestationsIds,result.getFirestationIds());
        verify(personService).getPersons();
        verify(mapper, Mockito.times(2)).toPersonForFire(any(Person.class));
    }

    @Test
    public void getFireData_withUnknownAddress_returnsNull() throws IOException {

        when(personService.getPersons()).thenReturn(persons);

        FireData result = fireDataService.getFireData("unknown_address");

        assertNull(result);
        verify(personService).getPersons();
    }

    @Test
    public void getFireData_withIOException_throwsException() throws IOException {

        when(personService.getPersons()).thenThrow(new IOException());
        
        assertThrows(IOException.class, () -> fireDataService.getFireData("test_address"));

        verify(personService).getPersons();
    }
}
