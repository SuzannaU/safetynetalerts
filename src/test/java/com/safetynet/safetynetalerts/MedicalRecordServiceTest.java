package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JsonReadingRepository;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    private static JsonReadingRepository jsonReadingRepository;
    @Mock
    private static JsonWritingRepository jsonWritingRepository;
    MedicalRecordService medicalRecordService;
    List<MedicalRecord> medicalRecords;
    
    @BeforeEach
    private void setUp() {
        medicalRecordService = new MedicalRecordService(
            jsonReadingRepository, jsonWritingRepository);

        MedicalRecord medicalRecord1 = new MedicalRecord(
                "john", "doe", "01/01/2000",
                Arrays.asList("med1", "med2"), Arrays.asList("allergy1"));
        MedicalRecord medicalRecord2 = new MedicalRecord(
                "jane", "doe", "01/01/2020",
                Arrays.asList(), Arrays.asList());
        medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecord1);
        medicalRecords.add(medicalRecord2);
    }

    @Test
    public void getMedicalRecords_withCorrectParameters_setsAttributes() throws IOException {

        when(jsonReadingRepository.getMedicalRecords()).thenReturn(medicalRecords);

        List<MedicalRecord> recordsTest = medicalRecordService.getMedicalRecords();

        MedicalRecord john = recordsTest.stream()
        .filter(r -> r.getFirstName().equals("john"))
        .findFirst().orElseThrow();

        assertEquals("johndoe", john.getPersonId());
        assertEquals(LocalDate.of(2000, 1, 1), john.getLocalBirthdate());
        assertEquals(25, john.getAge());
        assertTrue(john.getMedications().contains("med1"));
        assertTrue(john.getAllergies().contains("allergy1"));
        verify(jsonReadingRepository).getMedicalRecords();
}}
