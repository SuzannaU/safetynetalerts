package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.safetynet.safetynetalerts.dto.CommunityEmailData;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.CommunityEmailDataService;
import com.safetynet.safetynetalerts.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailDataServiceTest {
    @Mock
    private static PersonService personService;
    CommunityEmailDataService communityEmailDataService;
    List<Person> persons;
    List<MedicalRecord> medicalRecords;
    Set<Firestation> firestations;

    @BeforeEach
    private void setUp() {
        communityEmailDataService = new CommunityEmailDataService(personService);
        Person john = new Person(
                "john", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email1");
        Person jane = new Person(
                "jane", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email1");
        Person jack = new Person(
                "jack", "doe", "test_address",
                "test_city", "test_zip", "test_phone", "test_email2");
        persons = new ArrayList<>();
        persons.add(john);
        persons.add(jane);
        persons.add(jack);
    }

    @Test
    public void getCommunityEmailData_withCorrectParameters_returnsData() throws IOException {
        when(personService.getPersons()).thenReturn(persons);

        CommunityEmailData result = communityEmailDataService.getCommunityEmailData("test_city");

        assertNotNull(result);
        assertEquals(2, result.getEmails().size());
        verify(personService).getPersons();
    }

    @Test
    public void getCommunityEmailData_withUnknownCity_returnsNull() throws IOException {

        when(personService.getPersons()).thenReturn(persons);

        CommunityEmailData result = communityEmailDataService.getCommunityEmailData("unknown_city");

        assertNull(result);
        verify(personService).getPersons();
    }

    @Test
    public void getChildAlertData_withIOException_throwsException() throws IOException {

        when(personService.getPersons()).thenThrow(new IOException());

        assertThrows(IOException.class,
                () -> communityEmailDataService.getCommunityEmailData("test_city"));

        verify(personService).getPersons();
    }

}
