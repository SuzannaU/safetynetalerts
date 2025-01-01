package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.FirestationRawData;
import com.safetynet.safetynetalerts.repository.*;

@Service
public class FirestationService {
    private static final Logger logger = LoggerFactory.getLogger(FirestationService.class);
    JsonReadingRepository jsonReadingRepository;
    JsonWritingRepository jsonWritingRepository;

    public FirestationService(JsonReadingRepository jsonReadingRepository,
            JsonWritingRepository jsonWritingRepository) {
        this.jsonReadingRepository = jsonReadingRepository;
        this.jsonWritingRepository = jsonWritingRepository;
    }

    public Set<Firestation> getFirestations() throws IOException {

        Set<Firestation> firestations = new LinkedHashSet<Firestation>();
        List<FirestationRawData> firestationsRawData =
                jsonReadingRepository.getFirestationsRawData();

        Set<Integer> firestationIds = firestationsRawData
                .stream()
                .map(firestationRawData -> Integer.parseInt(firestationRawData.getFirestationId()))
                .collect(Collectors.toSet());

        for (int firestationId : firestationIds) {
            Firestation firestation = new Firestation();
            firestation.setFirestationId(firestationId);
            firestation.setAddresses(getAddresses(firestationId, firestationsRawData));

            firestations.add(firestation);
        }

        return firestations;
    }

    private Set<String> getAddresses(int firestationId,
            List<FirestationRawData> firestationsRawData) {
        Set<String> addresses = firestationsRawData
                .stream()
                .filter(firestationRawData -> Integer
                        .parseInt(firestationRawData.getFirestationId()) == firestationId)
                .map(firestationRawData -> firestationRawData.getAddress())
                .collect(Collectors.toSet());

        return addresses;
    }

    public FirestationRawData createFirestationRawData(FirestationRawData firestationRawData)
            throws IOException {
        List<FirestationRawData> firestations = jsonReadingRepository.getFirestationsRawData();

        Optional<FirestationRawData> existingFirestation = firestations.stream()
                .filter(f -> f.getAddress().equals(firestationRawData.getAddress()))
                .findFirst();

        if (existingFirestation.isPresent()) {
            if (existingFirestation.get().getFirestationId()
                    .equals(firestationRawData.getFirestationId())) {
                logger.error("This address is already mapped to this firestation");
                return null;
            } else {
                logger.error("This address is already mapped to a firestation");
                return updateFirestationRawData(firestationRawData);
            }
        }

        firestations.add(firestationRawData);
        jsonWritingRepository.updateFirestations(firestations);

        return firestationRawData;
    }

    public FirestationRawData updateFirestationRawData(FirestationRawData firestationRawData)
            throws IOException {
        List<FirestationRawData> firestations = jsonReadingRepository.getFirestationsRawData();

        Optional<FirestationRawData> existingFirestation = firestations.stream()
                .filter(f -> f.getAddress().equals(firestationRawData.getAddress()))
                .findFirst();

        if (existingFirestation.isEmpty()) {
            logger.error("This address doesn't exist and cannot be updated");
            return null;
        }

        if (existingFirestation.isPresent() && existingFirestation.get().getFirestationId()
                .equals(firestationRawData.getFirestationId())) {
            logger.error("This address is already mapped to this firestation");
            return null;
        }

        FirestationRawData updatedFirestation = new FirestationRawData(
                firestationRawData.getAddress(),
                firestationRawData.getFirestationId());

        firestations.remove(existingFirestation.get());
        firestations.add(updatedFirestation);
        jsonWritingRepository.updateFirestations(firestations);

        return firestationRawData;
    }

    public FirestationRawData deletePersonRawData(FirestationRawData firestationRawData)
            throws IOException {
        List<FirestationRawData> firestations = jsonReadingRepository.getFirestationsRawData();

        Optional<FirestationRawData> existingFirestation = firestations.stream()
                .filter(f -> f.getFirestationId().equals(firestationRawData.getFirestationId()))
                .filter(f -> f.getAddress().equals(firestationRawData.getAddress()))
                .findFirst();

        if (existingFirestation.isEmpty()) {
            logger.error("This firestation/address combination doesn't exist");
            return null;
        }

        firestations.remove(existingFirestation.get());
        jsonWritingRepository.updateFirestations(firestations);

        return existingFirestation.get();
    }
}


