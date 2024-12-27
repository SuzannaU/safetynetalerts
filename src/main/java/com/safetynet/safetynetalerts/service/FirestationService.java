package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.dto.FirestationDTO;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.JsonRepository;

@Service
public class FirestationService {

    JsonRepository jsonRepository;

    public FirestationService(JsonRepository jsonRepository) {
        this.jsonRepository = jsonRepository;
    }

    public Set<Firestation> getFirestations() throws IOException {

        Set<Firestation> firestations = new LinkedHashSet<Firestation>();
        List<FirestationDTO> firestationsDTO = jsonRepository.getFirestationsDTO();

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

        List<FirestationDTO> firestationsDTO = jsonRepository.getFirestationsDTO();
        Set<String> addresses = firestationsDTO
                .stream()
                .filter(firestationDTO -> Integer
                        .parseInt(firestationDTO.getFirestationId()) == firestationId)
                .map(firestationDTO -> firestationDTO.getAddress())
                .collect(Collectors.toSet());

        return addresses;
    }

    public FirestationDTO createFirestationDTO(FirestationDTO firestationDTO) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createFirestationDTO'");
    }

    public FirestationDTO updateFirestationDTO(FirestationDTO firestationDTO) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFirestationDTO'");
    }

    public FirestationDTO deletePersonDTO(FirestationDTO firestationDTO) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePersonDTO'");
    }
}


