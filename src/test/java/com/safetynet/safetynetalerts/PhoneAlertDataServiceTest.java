package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.mockito.junit.jupiter.MockitoExtension;
import com.safetynet.safetynetalerts.dto.PhoneAlertData;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.service.PhoneAlertDataService;

@ExtendWith(MockitoExtension.class)
public class PhoneAlertDataServiceTest {
    @Mock
    private static PersonService personService;
    PhoneAlertDataService phoneAlertDataService;
    List<Person> persons;

    @BeforeEach
    private void setUp() {
        phoneAlertDataService = new PhoneAlertDataService(personService);
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
    public void getPhoneAlertData_withCorrectParameters_returnsData() throws IOException {

        when(personService.getPersons()).thenReturn(persons);

        PhoneAlertData result = phoneAlertDataService.getPhoneAlertData(1);
        assertNotNull(result);
        assertEquals(1, result.getPhones().size());
        verify(personService).getPersons();
    }

    @Test
    public void getPhoneAlertData_withUnknownStationId_returnsNull() throws IOException {

        when(personService.getPersons()).thenReturn(persons);

        PhoneAlertData result = phoneAlertDataService.getPhoneAlertData(100);

        assertNull(result);
        verify(personService).getPersons();
    }

    @Test
    public void getPhoneAlertData_withIOException_throwsException() throws IOException {

        when(personService.getPersons()).thenThrow(new IOException());
        assertThrows(IOException.class,
                () -> phoneAlertDataService.getPhoneAlertData(1));

        verify(personService).getPersons();
    }

}
