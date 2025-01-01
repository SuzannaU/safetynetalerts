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
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.MedicalRecordRawData;
import com.safetynet.safetynetalerts.repository.JsonReadingRepository;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;

@Service
public class MedicalRecordService {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
    JsonReadingRepository jsonReadingRepository;
    JsonWritingRepository jsonWritingRepository;

    public MedicalRecordService(JsonReadingRepository jsonReadingRepository,
            JsonWritingRepository jsonWritingRepository) {
        this.jsonReadingRepository = jsonReadingRepository;
        this.jsonWritingRepository = jsonWritingRepository;
    }

    public List<MedicalRecord> getMedicalRecords() throws IOException {
        List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
        List<MedicalRecordRawData> medicalRecordsRawData =
                jsonReadingRepository.getMedicalRecordsRawData();

        for (MedicalRecordRawData medicalRecordRawData : medicalRecordsRawData) {

            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setPersonId(medicalRecordRawData.getFirstName()
                    .concat(medicalRecordRawData.getLastName()));
            medicalRecord.setMedications(medicalRecordRawData.getMedications());
            medicalRecord.setAllergies(medicalRecordRawData.getAllergies());
            medicalRecord.setBirthdate(getFormattedBirthdate(medicalRecordRawData.getBirthdate()));
            medicalRecord.setAge(getAge(medicalRecordRawData.getBirthdate()));

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

    public MedicalRecordRawData createMedicalRecordRawData(
            MedicalRecordRawData medicalRecordRawData)
            throws IOException {
        List<MedicalRecordRawData> medicalRecordsRawData =
                jsonReadingRepository.getMedicalRecordsRawData();

        Optional<MedicalRecordRawData> existingMedicalRecord = medicalRecordsRawData.stream()
                .filter(m -> m.getFirstName().equals(medicalRecordRawData.getFirstName()))
                .filter(m -> m.getLastName().equals(medicalRecordRawData.getLastName()))
                .findFirst();

        if (existingMedicalRecord.isPresent()) {
            logger.error("This medical record already exists");
            return null;
        }

        medicalRecordsRawData.add(medicalRecordRawData);
        jsonWritingRepository.updateMedicalRecords(medicalRecordsRawData);

        return medicalRecordRawData;
    }

    public MedicalRecordRawData updateMedicalRecordRawData(
            MedicalRecordRawData medicalRecordRawData)
            throws IOException {
        List<MedicalRecordRawData> medicalRecordsRawData =
                jsonReadingRepository.getMedicalRecordsRawData();

        Optional<MedicalRecordRawData> existingMedicalRecord = medicalRecordsRawData.stream()
                .filter(m -> m.getFirstName().equals(medicalRecordRawData.getFirstName()))
                .filter(m -> m.getLastName().equals(medicalRecordRawData.getLastName()))
                .findFirst();

        if (existingMedicalRecord.isEmpty()) {
            logger.error("This medical record doesn't exist");
            return null;
        }

        MedicalRecordRawData updatedMedicalRecord = new MedicalRecordRawData(
                medicalRecordRawData.getFirstName(),
                medicalRecordRawData.getLastName());
        updatedMedicalRecord.setBirthdate(
                Optional.ofNullable(medicalRecordRawData.getBirthdate())
                        .orElse(existingMedicalRecord.get().getBirthdate()));
        updatedMedicalRecord.setAllergies(
                Optional.ofNullable(medicalRecordRawData.getAllergies())
                        .orElse(existingMedicalRecord.get().getAllergies()));
        updatedMedicalRecord.setMedications(
                Optional.ofNullable(medicalRecordRawData.getMedications())
                        .orElse(existingMedicalRecord.get().getMedications()));

        medicalRecordsRawData.remove(existingMedicalRecord.get());
        medicalRecordsRawData.add(updatedMedicalRecord);
        jsonWritingRepository.updateMedicalRecords(medicalRecordsRawData);

        return updatedMedicalRecord;
    }

    public MedicalRecordRawData deleteMedicalRecordRawData(
            MedicalRecordRawData medicalRecordRawData)
            throws IOException {
        List<MedicalRecordRawData> medicalRecords =
                jsonReadingRepository.getMedicalRecordsRawData();

        Optional<MedicalRecordRawData> existingMedicalRecord = medicalRecords.stream()
                .filter(m -> m.getFirstName().equals(medicalRecordRawData.getFirstName()))
                .filter(m -> m.getLastName().equals(medicalRecordRawData.getLastName()))
                .findFirst();

        if (existingMedicalRecord.isEmpty()) {
            logger.error("This medical record doesn't exist");
            return null;
        }

        medicalRecords.remove(existingMedicalRecord.get());
        jsonWritingRepository.updateMedicalRecords(medicalRecords);

        return existingMedicalRecord.get();
    }
}
