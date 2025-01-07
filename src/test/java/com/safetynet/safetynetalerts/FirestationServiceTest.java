package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.JsonReadingRepository;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;
import com.safetynet.safetynetalerts.service.FirestationService;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Mock
    private static JsonReadingRepository jsonReadingRepository;
    @Mock
    private static JsonWritingRepository jsonWritingRepository;
    FirestationService firestationService;
    List<Firestation> firestations;

    @BeforeEach
    private void setUp() {
        firestationService = new FirestationService(jsonReadingRepository, jsonWritingRepository);

        Firestation firestation1 = new Firestation("test_address1", "1");
        Firestation firestation2 = new Firestation("test_address1", "2");
        Firestation firestation3 = new Firestation("test_address2", "2");
        firestations = new ArrayList<>();
        firestations.add(firestation1);
        firestations.add(firestation2);
        firestations.add(firestation3);
    }

    @Test
    public void getFirestations__withCorrectParameters_setsAttributes() throws IOException {

        when(jsonReadingRepository.getFirestations()).thenReturn(firestations);

        Set<Firestation> firestationsTest = firestationService.getFirestations();

        Firestation testFirestation = firestationsTest.stream()
                .filter(f -> f.getStation().equals("2"))
                .findFirst().orElseThrow();

        assertEquals("test_address1", testFirestation.getAddress());
        assertEquals(2, testFirestation.getFirestationId());
        assertTrue(testFirestation.getAddresses().contains("test_address1"));
        assertTrue(testFirestation.getAddresses().contains("test_address2"));
        verify(jsonReadingRepository).getFirestations();
    }

    @Test
    public void createFirestation_withCorrectParameters_addsNewFirestation() throws IOException {

        when(jsonReadingRepository.getFirestations()).thenReturn(firestations);
        when(jsonWritingRepository.updateFirestations(firestations)).thenReturn(true);
        Firestation firestationToAdd = new Firestation("new_address", "2");
        firestationService.createFirestation(firestationToAdd);

        String addedAddress = firestations.stream()
                .filter(f -> f.getAddress().equals(firestationToAdd.getAddress()))
                .map(f -> f.getAddress())
                .findFirst().orElseThrow();

        assertEquals(firestationToAdd.getAddress(), addedAddress);
        verify(jsonReadingRepository).getFirestations();
        verify(jsonWritingRepository).updateFirestations(firestations);
    }

    @Test
    public void createFirestation_withAlreadyExistingAddress_returnsNull() throws IOException {

        when(jsonReadingRepository.getFirestations()).thenReturn(firestations);
        lenient().when(jsonWritingRepository.updateFirestations(firestations)).thenReturn(true);
        Firestation firestationToAdd1 = new Firestation("test_address1", "1");
        Firestation firestationToAdd2 = new Firestation("test_address1", "100");

        assertTrue(firestationService.createFirestation(firestationToAdd1) == null);
        assertTrue(firestationService.createFirestation(firestationToAdd2) == null);
        verify(jsonReadingRepository, Mockito.times(2)).getFirestations();
        verify(jsonWritingRepository, Mockito.times(0)).updateFirestations(firestations);
    }

    @Test
    public void updateFirestation_withCorrectParameters_modifiesFirestation()
            throws IOException {

        when(jsonReadingRepository.getFirestations()).thenReturn(firestations);
        when(jsonWritingRepository.updateFirestations(firestations)).thenReturn(true);
        Firestation firestationToUpdate = new Firestation("test_address1", "3");

        firestationService.updateFirestation(firestationToUpdate);

        List<String> updatedStation = firestations.stream()
                .filter(f -> f.getAddress().equals(firestationToUpdate.getAddress()))
                .map(f -> f.getStation())
                .collect(Collectors.toList());

        assertTrue(updatedStation.contains(firestationToUpdate.getStation()));
        verify(jsonReadingRepository).getFirestations();
        verify(jsonWritingRepository).updateFirestations(firestations);
    }

    @Test
    public void updateFirestation_withNonExistingAddress_returnsNull() throws IOException {

        when(jsonReadingRepository.getFirestations()).thenReturn(firestations);
        lenient().when(jsonWritingRepository.updateFirestations(firestations)).thenReturn(true);
        Firestation firestationToUpdate = new Firestation("new_address", "2");

        assertTrue(firestationService.updateFirestation(firestationToUpdate) == null);
        verify(jsonReadingRepository).getFirestations();
        verify(jsonWritingRepository, Mockito.times(0)).updateFirestations(firestations);
    }

    @Test
    public void updateFirestation_withAlreadyExistingMapping_returnsNull() throws IOException {

        when(jsonReadingRepository.getFirestations()).thenReturn(firestations);
        lenient().when(jsonWritingRepository.updateFirestations(firestations)).thenReturn(true);
        Firestation firestationToUpdate = new Firestation("test_address1", "1");

        assertTrue(firestationService.updateFirestation(firestationToUpdate) == null);
        verify(jsonReadingRepository).getFirestations();
        verify(jsonWritingRepository, Mockito.times(0)).updateFirestations(firestations);
    }

    @Test
    public void deleteFirestation_withCorrectParameters_deletesFirestation() throws IOException {

        when(jsonReadingRepository.getFirestations()).thenReturn(firestations);
        when(jsonWritingRepository.updateFirestations(firestations)).thenReturn(true);
        Firestation firestationToDelete = new Firestation("test_address1", "1");

        firestationService.deleteFirestation(firestationToDelete);

        Optional<Firestation> deletedStation = firestations.stream()
                .filter(f -> f.getAddress().equals(firestationToDelete.getAddress()))
                .filter(f -> f.getStation().equals(firestationToDelete.getStation()))
                .findFirst();

        assertTrue(deletedStation.isEmpty());
        verify(jsonReadingRepository).getFirestations();
        verify(jsonWritingRepository).updateFirestations(firestations);
    }

    @Test
    public void deleteFirestation_withNonExistingFirestation_returnNull() throws IOException {

        when(jsonReadingRepository.getFirestations()).thenReturn(firestations);
        lenient().when(jsonWritingRepository.updateFirestations(firestations)).thenReturn(true);
        Firestation firestationToDelete = new Firestation("test_address1", "100");

        assertTrue(firestationService.deleteFirestation(firestationToDelete)==null);
        verify(jsonReadingRepository).getFirestations();
        verify(jsonWritingRepository, Mockito.times(0)).updateFirestations(firestations);
    }
}
