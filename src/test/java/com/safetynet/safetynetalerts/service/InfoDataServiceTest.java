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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.safetynet.safetynetalerts.dto.InfoData;
import com.safetynet.safetynetalerts.dto.PersonForInfo;
import com.safetynet.safetynetalerts.mapper.Mapper;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class InfoDataServiceTest {
    @MockitoBean
    private PersonService personService;
    @MockitoBean
    private Mapper mapper;
    @Autowired
    private InfoDataService infoDataService;
    private List<Person> persons;

    @BeforeEach
    private void setUp() {
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
    public void getInfoData_withCorrectParameters_returnsData() throws IOException {

        PersonForInfo personForInfo = new PersonForInfo();
        when(personService.getPersons()).thenReturn(persons);
        when(mapper.toPersonForInfo(any(Person.class))).thenReturn(personForInfo);

        InfoData result = infoDataService.getInfoData("doe");

        assertNotNull(result);
        assertEquals(2, result.getPersons().size());
        verify(personService).getPersons();
        verify(mapper, Mockito.times(2)).toPersonForInfo(any(Person.class));
    }

    @Test
    public void getInfoData_withUnknownLastName_returnsNull() throws IOException {

        when(personService.getPersons()).thenReturn(persons);

        InfoData result = infoDataService.getInfoData("unknown_lastName");

        assertNull(result);
        verify(personService).getPersons();
    }

    @Test
    public void getInfoData_withIOException_throwsException() throws IOException {

        when(personService.getPersons()).thenThrow(new IOException());
        
        assertThrows(IOException.class, () -> infoDataService.getInfoData("doe"));

        verify(personService).getPersons();
    }

}
