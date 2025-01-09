package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.JsonReadingRepository;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;

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
    }

    @Test
    public void getMedicalRecords_withWrongBirthdate_throwsException() throws IOException {

        when(jsonReadingRepository.getMedicalRecords()).thenReturn(medicalRecords);
        MedicalRecord medicalRecord3 = new MedicalRecord(
                "jack", "doe", "15/32/2020",
                Arrays.asList(), Arrays.asList());
        medicalRecords.add(medicalRecord3);

        assertThrows(DateTimeParseException.class, () -> medicalRecordService.getMedicalRecords());
        verify(jsonReadingRepository).getMedicalRecords();
    }

    @Test
    public void createMedicalRecord_withCorrectParameters_addsNewMedicalRecord()
            throws IOException {

        when(jsonReadingRepository.getMedicalRecords()).thenReturn(medicalRecords);
        when(jsonWritingRepository.updateMedicalRecords(medicalRecords)).thenReturn(true);
        MedicalRecord recordToAdd = new MedicalRecord(
                "new", "record", "01/01/2000", null, null);
        medicalRecordService.createMedicalRecord(recordToAdd);

        String addedFirstName = medicalRecords.stream()
                .filter(r -> r.getFirstName().equals(recordToAdd.getFirstName()))
                .map(r -> r.getFirstName())
                .findFirst().orElseThrow();

        assertEquals(recordToAdd.getFirstName(), addedFirstName);
        verify(jsonReadingRepository).getMedicalRecords();
        verify(jsonWritingRepository).updateMedicalRecords(medicalRecords);
    }

    @Test
    public void createMedicalRecord_withAlreadyExistingRecord_returnsNull() throws IOException {

        when(jsonReadingRepository.getMedicalRecords()).thenReturn(medicalRecords);
        lenient().when(jsonWritingRepository.updateMedicalRecords(medicalRecords)).thenReturn(true);
        MedicalRecord recordToAdd = new MedicalRecord(
                "john", "doe", "01/01/2000", null, null);

        assertNull(medicalRecordService.createMedicalRecord(recordToAdd));
        verify(jsonReadingRepository).getMedicalRecords();
        verify(jsonWritingRepository, Mockito.times(0)).updateMedicalRecords(medicalRecords);
    }

    @Test
    public void updatedMedicalRecord_withCorrectParameters_updatesMedicalRecord()
            throws IOException {

        when(jsonReadingRepository.getMedicalRecords()).thenReturn(medicalRecords);
        when(jsonWritingRepository.updateMedicalRecords(medicalRecords)).thenReturn(true);
        MedicalRecord recordToUpdate = new MedicalRecord(
                "john", "doe", "01/01/2020", null, null);

        medicalRecordService.updateMedicalRecord(recordToUpdate);

        String updatedRawBirthdate = medicalRecords.stream()
                .filter(r -> r.getFirstName().equals(recordToUpdate.getFirstName()))
                .map(r -> r.getRawBirthdate())
                .findFirst().orElseThrow();


        assertEquals(recordToUpdate.getRawBirthdate(), updatedRawBirthdate);
        verify(jsonReadingRepository).getMedicalRecords();
        verify(jsonWritingRepository).updateMedicalRecords(medicalRecords);
    }

    @Test
    public void updatedMedicalRecord_withNonExistingRecord_returnsNull() throws IOException {

        when(jsonReadingRepository.getMedicalRecords()).thenReturn(medicalRecords);
        lenient().when(jsonWritingRepository.updateMedicalRecords(medicalRecords)).thenReturn(true);
        MedicalRecord recordToUpdate = new MedicalRecord(
                "new", "record", "01/01/2000", null, null);

        assertNull(medicalRecordService.updateMedicalRecord(recordToUpdate));
        verify(jsonReadingRepository).getMedicalRecords();
        verify(jsonWritingRepository, Mockito.times(0)).updateMedicalRecords(medicalRecords);
    }

    @Test
    public void updatedMedicalRecord_withNullAttributes_doesNotErase() throws IOException {

        when(jsonReadingRepository.getMedicalRecords()).thenReturn(medicalRecords);
        when(jsonWritingRepository.updateMedicalRecords(medicalRecords)).thenReturn(true);
        MedicalRecord recordToUpdate = new MedicalRecord(
                "john", "doe", null, null, null);

        medicalRecordService.updateMedicalRecord(recordToUpdate);

        MedicalRecord updatedRecord = medicalRecords.stream()
                .filter(r -> r.getFirstName().equals(recordToUpdate.getFirstName()))
                .findFirst().orElseThrow();

        assertTrue(updatedRecord.getRawBirthdate().contains("01/01/2000"));
        assertTrue(updatedRecord.getMedications().contains("med1"));
        assertTrue(updatedRecord.getAllergies().contains("allergy1"));
        verify(jsonReadingRepository).getMedicalRecords();
        verify(jsonWritingRepository).updateMedicalRecords(medicalRecords);
    }

    @Test
    public void deleteMedicalRecord_withCorrectParameters_deletesMedicalRecord()
            throws IOException {

        when(jsonReadingRepository.getMedicalRecords()).thenReturn(medicalRecords);
        when(jsonWritingRepository.updateMedicalRecords(medicalRecords)).thenReturn(true);
        MedicalRecord recordToDelete = new MedicalRecord(
                "john", "doe", "01/01/2020", null, null);

        medicalRecordService.deleteMedicalRecord(recordToDelete);

        Optional<MedicalRecord> deletedRecord = medicalRecords.stream()
                .filter(r -> r.getFirstName().equals(recordToDelete.getFirstName()))
                .findFirst();

        assertTrue(deletedRecord.isEmpty());
        verify(jsonReadingRepository).getMedicalRecords();
        verify(jsonWritingRepository).updateMedicalRecords(medicalRecords);
    }

    @Test
    public void deleteMedicalRecord__withNonExistingRecord_returnsNull() throws IOException {

        when(jsonReadingRepository.getMedicalRecords()).thenReturn(medicalRecords);
        lenient().when(jsonWritingRepository.updateMedicalRecords(medicalRecords)).thenReturn(true);
        MedicalRecord recordToDelete = new MedicalRecord(
                "new", "record", "01/01/2020", null, null);

        assertNull(medicalRecordService.deleteMedicalRecord(recordToDelete));
        verify(jsonReadingRepository).getMedicalRecords();
        verify(jsonWritingRepository, Mockito.times(0)).updateMedicalRecords(medicalRecords);
    }
}
