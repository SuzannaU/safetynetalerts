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
import com.safetynet.safetynetalerts.dto.FirestationDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.*;

@Service
public class FirestationService {
    private static final Logger logger = LoggerFactory.getLogger(FirestationService.class);
    JsonReadingRepository jsonReadingRepository;
    JsonWritingRepository jsonWritingRepository;

    public FirestationService(JsonReadingRepository jsonReadingRepository, JsonWritingRepository jsonWritingRepository) {
        this.jsonReadingRepository = jsonReadingRepository;
        this.jsonWritingRepository = jsonWritingRepository;
    }

    public Set<Firestation> getFirestations() throws IOException {

        Set<Firestation> firestations = new LinkedHashSet<Firestation>();
        List<FirestationDTO> firestationsDTO = jsonReadingRepository.getFirestationsDTO();

        Set<Integer> firestationIds = firestationsDTO
                .stream()
                .map(firestationDTO -> Integer.parseInt(firestationDTO.getFirestationId()))
                .collect(Collectors.toSet());

        for (int firestationId : firestationIds) {
            Firestation firestation = new Firestation();
            firestation.setFirestationId(firestationId);
            firestation.setAddresses(getAddresses(firestationId));

            firestations.add(firestation);
        }

        return firestations;
    }

    private Set<String> getAddresses(int firestationId) throws IOException {

        List<FirestationDTO> firestationsDTO = jsonReadingRepository.getFirestationsDTO();
        Set<String> addresses = firestationsDTO
                .stream()
                .filter(firestationDTO -> Integer
                        .parseInt(firestationDTO.getFirestationId()) == firestationId)
                .map(firestationDTO -> firestationDTO.getAddress())
                .collect(Collectors.toSet());

        return addresses;
    }

    public FirestationDTO createFirestationDTO(FirestationDTO firestationDTO) throws IOException {
        List<FirestationDTO> firestations = jsonReadingRepository.getFirestationsDTO();

        Optional<FirestationDTO> existingFirestation = firestations.stream()
                .filter(f -> f.getAddress().equals(firestationDTO.getAddress()))
                .findFirst();

        if (existingFirestation.isPresent()) {
            if (existingFirestation.get().getFirestationId()
                    .equals(firestationDTO.getFirestationId())) {
                logger.error("This address is already mapped to this firestation");
                return null;
            } else {
                logger.error("This address is already mapped to a firestation");
                return updateFirestationDTO(firestationDTO);
            }
        }

        firestations.add(firestationDTO);
        jsonWritingRepository.updateFirestations(firestations);

        return firestationDTO;
    }

    public FirestationDTO updateFirestationDTO(FirestationDTO firestationDTO) throws IOException {
        List<FirestationDTO> firestations = jsonReadingRepository.getFirestationsDTO();

        Optional<FirestationDTO> existingFirestation = firestations.stream()
                .filter(f -> f.getAddress().equals(firestationDTO.getAddress()))
                .findFirst();

        if (existingFirestation.isEmpty()) {
            logger.error("This address doesn't exist and cannot be updated");
            return null;
        }

        if (existingFirestation.isPresent() && existingFirestation.get().getFirestationId()
                .equals(firestationDTO.getFirestationId())) {
            logger.error("This address is already mapped to this firestation");
            return null;
        }

        FirestationDTO updatedFirestation = new FirestationDTO();
        updatedFirestation.setFirestationId(firestationDTO.getFirestationId());
        updatedFirestation.setAddress(firestationDTO.getAddress());

        firestations.remove(existingFirestation.get());
        firestations.add(updatedFirestation);
        jsonWritingRepository.updateFirestations(firestations);

        return firestationDTO;
    }

    public FirestationDTO deletePersonDTO(FirestationDTO firestationDTO) throws IOException {
        List<FirestationDTO> firestations = jsonReadingRepository.getFirestationsDTO();

        Optional<FirestationDTO> existingFirestation = firestations.stream()
                .filter(f -> f.getFirestationId().equals(firestationDTO.getFirestationId()))
                .filter(f -> f.getAddress().equals(firestationDTO.getAddress()))
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


