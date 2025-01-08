package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.safetynet.safetynetalerts.controller.Mapper;
import com.safetynet.safetynetalerts.dto.AdultForChildAlert;
import com.safetynet.safetynetalerts.dto.ChildAlertData;
import com.safetynet.safetynetalerts.dto.ChildForChildAlert;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.ChildAlertDataService;
import com.safetynet.safetynetalerts.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class ChilAlertDataServiceTest {
    @Mock
    private static PersonService personService;
    @Mock
    private static Mapper mapper;
    ChildAlertDataService childAlertDataService;
    List<Person> persons;

    @BeforeEach
    private void setUp() {
        childAlertDataService = new ChildAlertDataService(personService, mapper);
        Person john = new Person(
                "john", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        john.setCategory("Adult");
        Person jane = new Person(
                "jane", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        jane.setCategory("Child");
        persons = new ArrayList<>();
        persons.add(john);
        persons.add(jane);
    }

    @Test
    public void getChildAlertData_withCorrectParameters_returnsData() throws IOException {

        AdultForChildAlert adultForChildAlert = new AdultForChildAlert();
        ChildForChildAlert childForChildAlert = new ChildForChildAlert();
        when(personService.getPersons()).thenReturn(persons);
        when(mapper.toAdultForChildAlert(any(Person.class))).thenReturn(adultForChildAlert);
        when(mapper.toChildForChildAlert(any(Person.class))).thenReturn(childForChildAlert);

        ChildAlertData result = childAlertDataService.getChildAlertData("test_address");
        assertNotNull(result);
        assertEquals(1, result.getAdults().size());
        assertEquals(1, result.getChildren().size());
        verify(personService).getPersons();
        verify(mapper).toAdultForChildAlert(any(Person.class));
        verify(mapper).toAdultForChildAlert(any(Person.class));
    }

    @Test
    public void getChildAlertData_withUnknownAddress_returnsNull() throws IOException {

        when(personService.getPersons()).thenReturn(persons);

        ChildAlertData result = childAlertDataService.getChildAlertData("unknown_address");

        assertNull(result);
        verify(personService).getPersons();
    }

    @Test
    public void getChildAlertData_withIOException_throwsException() throws IOException {

        when(personService.getPersons()).thenThrow(new IOException());
        assertThrows(IOException.class,
                () -> childAlertDataService.getChildAlertData("test_address"));

        verify(personService).getPersons();
    }
}
