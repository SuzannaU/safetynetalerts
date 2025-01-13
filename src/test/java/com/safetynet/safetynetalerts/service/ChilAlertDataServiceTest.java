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
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.safetynet.safetynetalerts.Mapper;
import com.safetynet.safetynetalerts.dto.AdultForChildAlert;
import com.safetynet.safetynetalerts.dto.ChildAlertData;
import com.safetynet.safetynetalerts.dto.ChildForChildAlert;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class ChilAlertDataServiceTest {
    @MockitoBean
    private PersonService personService;
    @MockitoBean
    private Mapper mapper;
    @Autowired
    private ChildAlertDataService childAlertDataService;
    private List<Person> persons;
    private Person jane;

    @BeforeEach
    private void setUp() {
        Person john = new Person(
                "john", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email");
        john.setCategory("Adult");
        jane = new Person(
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
    public void getChildAlertData_withNoChildren_returnsData() throws IOException {

        jane.setCategory("Adult");
        when(personService.getPersons()).thenReturn(persons);

        ChildAlertData result = childAlertDataService.getChildAlertData("test_address");

        assertNotNull(result);
        verify(personService).getPersons();
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
