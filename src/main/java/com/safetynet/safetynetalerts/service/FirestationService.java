package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.model.Firestation;
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

        List<Firestation> firestationsList = jsonReadingRepository.getFirestations();

        for (Firestation firestation : firestationsList) {
            firestation.setFirestationId(Integer.parseInt(firestation.getStation()));
            firestation
                    .setAddresses(getAddresses(firestation.getFirestationId(), firestationsList));
        }

        Set<Firestation> firestations = new HashSet<>();
        for (Firestation firestation : firestationsList) {
            firestations.add(firestation);
        }

        return firestations;
    }

    private Set<String> getAddresses(int firestationId, List<Firestation> firestations) {
        Set<String> addresses = firestations
                .stream()
                .filter(f -> Integer.parseInt(f.getStation()) == firestationId)
                .map(f -> f.getAddress())
                .collect(Collectors.toSet());

        return addresses;
    }

    public Firestation createFirestation(Firestation firestation)
            throws IOException {
        List<Firestation> firestations = jsonReadingRepository.getFirestations();

        Optional<Firestation> existingFirestation = firestations.stream()
                .filter(f -> f.getAddress().equals(firestation.getAddress()))
                .findFirst();

        if (existingFirestation.isPresent()) {
            if (existingFirestation.get().getStation().equals(firestation.getStation())) {
                logger.error("This address is already mapped to this firestation");
                return null;
            } else {
                logger.error("This address is already mapped to a firestation");
                return null;
            }
        }

        firestations.add(firestation);
        jsonWritingRepository.updateFirestations(firestations);

        return firestation;
    }

    public Firestation updateFirestation(Firestation firestation)
            throws IOException {

        List<Firestation> firestations = jsonReadingRepository.getFirestations();

        Optional<Firestation> existingFirestation = firestations.stream()
                .filter(f -> f.getAddress().equals(firestation.getAddress()))
                .findFirst();

        if (existingFirestation.isEmpty()) {
            logger.error("This address doesn't exist and cannot be updated");
            return null;
        }

        if (existingFirestation.isPresent()
                && existingFirestation.get().getStation().equals(firestation.getStation())) {
            logger.error("This address is already mapped to this firestation");
            return null;
        }

        Firestation updatedFirestation = new Firestation(
                firestation.getAddress(),
                firestation.getStation());

        firestations.remove(existingFirestation.get());
        firestations.add(updatedFirestation);
        jsonWritingRepository.updateFirestations(firestations);

        return firestation;
    }

    public Firestation deletePerson(Firestation firestation)
            throws IOException {
        List<Firestation> firestations = jsonReadingRepository.getFirestations();

        Optional<Firestation> existingFirestation = firestations.stream()
                .filter(f -> f.getStation().equals(firestation.getStation()))
                .filter(f -> f.getAddress().equals(firestation.getAddress()))
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


