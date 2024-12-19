package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.FirestationFromJson;
import com.safetynet.safetynetalerts.repository.JsonRepository;

@Service
public class FirestationService {

    JsonRepository jsonRepository;

    public FirestationService(JsonRepository jsonRepository) {
        this.jsonRepository = jsonRepository;
    }

    public Set<Firestation> getFirestations() throws IOException {

        Set<Firestation> firestations = new LinkedHashSet<Firestation>();
        List<FirestationFromJson> firestationsFromJson = jsonRepository.getFirestationsFromJson();

        Set<Integer> firestationIds = firestationsFromJson
                .stream()
                .map(firestationFromJson -> Integer.parseInt(firestationFromJson.getFirestationId()))
                .collect(Collectors.toSet());

        for (int firestationId : firestationIds) {
            Firestation firestation = new Firestation();
            firestation.setFirestationId(firestationId);
            firestation.setAddresses(setAdresses(firestationId));

            firestations.add(firestation);
        }

        return firestations;
    }


    private List<String> setAdresses(int firestationId) throws IOException {

        List<FirestationFromJson> firestationsFromJson = jsonRepository.getFirestationsFromJson();
        List<String> addresses = firestationsFromJson
                .stream()
                .filter(firestationFromJson -> Integer
                        .parseInt(firestationFromJson.getFirestationId()) == firestationId)
                .map(firestationFromJson -> firestationFromJson.getAddress())
                .collect(Collectors.toList());

        return addresses;
    }
}


