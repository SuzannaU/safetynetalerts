package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.MedicalRecordFromJson;
import com.safetynet.safetynetalerts.repository.JsonRepository;

@Service
public class MedicalRecordService {
    JsonRepository jsonRepository;


    public MedicalRecordService(JsonRepository jsonRepository) {
        this.jsonRepository = jsonRepository;
    }

    public List<MedicalRecord> getMedicalRecords() throws IOException {
        List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
        List<MedicalRecordFromJson> medicalRecordsFromJson =
                jsonRepository.getMedicalRecordsFromJson();

        for (MedicalRecordFromJson medicalRecordFromJson : medicalRecordsFromJson) {

            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setPersonId(medicalRecordFromJson.getFirstName()
                    .concat(medicalRecordFromJson.getLastName()));
            medicalRecord.setMedications(medicalRecordFromJson.getMedications());
            medicalRecord.setAllergies(medicalRecordFromJson.getAllergies());
            medicalRecord.setBirthdate(getFormattedBirthdate(medicalRecordFromJson.getBirthdate()));
            medicalRecord.setAge(getAge(medicalRecordFromJson.getBirthdate()));

            medicalRecords.add(medicalRecord);
        }

        return medicalRecords;
    }

    public int getAge(String birthdate) {
        LocalDate formattedBirthdate = getFormattedBirthdate(birthdate);
        Period period = Period.between(formattedBirthdate, LocalDate.now());
        return period.getYears();
    }

    public LocalDate getFormattedBirthdate(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(birthdate, formatter);
    }


}
