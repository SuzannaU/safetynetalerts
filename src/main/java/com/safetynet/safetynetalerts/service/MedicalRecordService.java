package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.dto.MedicalRecordDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.JsonRepository;

@Service
public class MedicalRecordService {
    JsonRepository jsonRepository;

    public MedicalRecordService(JsonRepository jsonRepository) {
        this.jsonRepository = jsonRepository;
    }

    public List<MedicalRecord> getMedicalRecords() throws IOException {
        List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
        List<MedicalRecordDTO> medicalRecordsDTO =
                jsonRepository.getMedicalRecordsDTO();

        for (MedicalRecordDTO medicalRecordDTO : medicalRecordsDTO) {

            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setPersonId(medicalRecordDTO.getFirstName()
                    .concat(medicalRecordDTO.getLastName()));
            medicalRecord.setMedications(medicalRecordDTO.getMedications());
            medicalRecord.setAllergies(medicalRecordDTO.getAllergies());
            medicalRecord.setBirthdate(getFormattedBirthdate(medicalRecordDTO.getBirthdate()));
            medicalRecord.setAge(getAge(medicalRecordDTO.getBirthdate()));

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
