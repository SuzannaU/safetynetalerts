package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.dto.MedicalRecordDTO;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.JsonRepository;

@Service
public class MedicalRecordService {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
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

    private int getAge(String birthdate) {
        LocalDate formattedBirthdate = getFormattedBirthdate(birthdate);
        Period period = Period.between(formattedBirthdate, LocalDate.now());
        return period.getYears();
    }

    private LocalDate getFormattedBirthdate(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(birthdate, formatter);
    }

    public MedicalRecordDTO createMedicalRecordDTO(MedicalRecordDTO medicalRecordDTO)
            throws IOException {
        List<MedicalRecordDTO> medicalRecordsDTO = jsonRepository.getMedicalRecordsDTO();

        Optional<MedicalRecordDTO> existingMedicalRecord = medicalRecordsDTO.stream()
                .filter(m -> m.getFirstName().equals(medicalRecordDTO.getFirstName()))
                .filter(m -> m.getLastName().equals(medicalRecordDTO.getLastName()))
                .findFirst();

        if (existingMedicalRecord.isPresent()) {
            logger.error("This medical record already exists");
            return null;
        }

        medicalRecordsDTO.add(medicalRecordDTO);
        jsonRepository.updateMedicalRecords(medicalRecordsDTO);

        return medicalRecordDTO;
    }

    public MedicalRecordDTO updateMedicalRecordDTO(MedicalRecordDTO medicalRecordDTO)
            throws IOException {
        List<MedicalRecordDTO> medicalRecordsDTO = jsonRepository.getMedicalRecordsDTO();

        Optional<MedicalRecordDTO> existingMedicalRecord = medicalRecordsDTO.stream()
                .filter(m -> m.getFirstName().equals(medicalRecordDTO.getFirstName()))
                .filter(m -> m.getLastName().equals(medicalRecordDTO.getLastName()))
                .findFirst();

        if (existingMedicalRecord.isEmpty()) {
            logger.error("This medical record doesn't exist");
            return null;
        }

        MedicalRecordDTO updatedMedicalRecord = new MedicalRecordDTO();
        updatedMedicalRecord.setFirstName(medicalRecordDTO.getFirstName());
        updatedMedicalRecord.setLastName(medicalRecordDTO.getLastName());
        updatedMedicalRecord.setBirthdate(
                Optional.ofNullable(medicalRecordDTO.getBirthdate())
                        .orElse(existingMedicalRecord.get().getBirthdate()));
        updatedMedicalRecord.setAllergies(
                Optional.ofNullable(medicalRecordDTO.getAllergies())
                        .orElse(existingMedicalRecord.get().getAllergies()));
        updatedMedicalRecord.setMedications(
                Optional.ofNullable(medicalRecordDTO.getMedications())
                        .orElse(existingMedicalRecord.get().getMedications()));

        medicalRecordsDTO.remove(existingMedicalRecord.get());
        medicalRecordsDTO.add(updatedMedicalRecord);
        jsonRepository.updateMedicalRecords(medicalRecordsDTO);

        return updatedMedicalRecord;
    }

    public MedicalRecordDTO deleteMedicalRecordDTO(MedicalRecordDTO medicalRecordDTO)
            throws IOException {
        List<MedicalRecordDTO> medicalRecords = jsonRepository.getMedicalRecordsDTO();

        Optional<MedicalRecordDTO> existingMedicalRecord = medicalRecords.stream()
                .filter(m -> m.getFirstName().equals(medicalRecordDTO.getFirstName()))
                .filter(m -> m.getLastName().equals(medicalRecordDTO.getLastName()))
                .findFirst();

        if (existingMedicalRecord.isEmpty()) {
            logger.error("This medical record doesn't exist");
            return null;
        }

        medicalRecords.remove(existingMedicalRecord.get());
        jsonRepository.updateMedicalRecords(medicalRecords);

        return existingMedicalRecord.get();
    }
}
