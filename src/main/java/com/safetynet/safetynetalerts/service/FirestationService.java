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

    public Firestation getFirestationById(int id) throws IOException{

        Set<Firestation> firestations = getFirestations();
        Firestation firestation = null;
        for (Firestation f : firestations){
            if(f.getFirestationId() == id){
                firestation = f;
            }
        }

        return firestation;
    }


    private List<String> getAddresses(int firestationId) throws IOException {

        List<FirestationDTO> firestationsDTO = jsonRepository.getFirestationsDTO();
        List<String> addresses = firestationsDTO
                .stream()
                .filter(firestationDTO -> Integer
                        .parseInt(firestationDTO.getFirestationId()) == firestationId)
                .map(firestationDTO -> firestationDTO.getAddress())
                .collect(Collectors.toList());

        return addresses;
    }
}


