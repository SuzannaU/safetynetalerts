package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.repository.JsonReadingRepository;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;

@Service
public class MedicalRecordService {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
    private JsonReadingRepository jsonReadingRepository;
    private JsonWritingRepository jsonWritingRepository;

    public MedicalRecordService(JsonReadingRepository jsonReadingRepository,
            JsonWritingRepository jsonWritingRepository) {
        this.jsonReadingRepository = jsonReadingRepository;
        this.jsonWritingRepository = jsonWritingRepository;
    }

    public List<MedicalRecord> getMedicalRecords() throws IOException {
        List<MedicalRecord> medicalRecords = jsonReadingRepository.getMedicalRecords();

        for (MedicalRecord medicalRecord : medicalRecords) {
            medicalRecord.setMedications(medicalRecord.getMedications());
            medicalRecord.setAllergies(medicalRecord.getAllergies());
            medicalRecord.setLocalBirthdate(getLocalBirthdate(medicalRecord.getRawBirthdate()));
            medicalRecord.setAge(getAge(medicalRecord.getRawBirthdate()));
        }

        return medicalRecords;
    }

    private LocalDate getLocalBirthdate(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            return LocalDate.parse(birthdate, formatter);
        } catch (DateTimeParseException e) {
            logger.error("Invalid format for birthdate");
            throw e;
        }
    }

    private int getAge(String birthdate) {
        LocalDate localBirthdate = getLocalBirthdate(birthdate);
        Period period = Period.between(localBirthdate, LocalDate.now());
        return period.getYears();
    }

    public MedicalRecord createMedicalRecord(
            MedicalRecord medicalRecord) throws IOException {

        List<MedicalRecord> medicalRecords = jsonReadingRepository.getMedicalRecords();

        Optional<MedicalRecord> existingMedicalRecord = medicalRecords.stream()
                .filter(m -> m.getFirstName().equals(medicalRecord.getFirstName()))
                .filter(m -> m.getLastName().equals(medicalRecord.getLastName()))
                .findFirst();

        if (existingMedicalRecord.isPresent()) {
            logger.error("This medical record already exists");
            return null;
        }

        /*
         * Checks if rawBirthdate is in format MM/dd/YYYY.
         * 
         * If not, throws DateTimeParseException handled in Controller
         */
        if (!medicalRecord.getRawBirthdate().isEmpty()) {
            getLocalBirthdate(medicalRecord.getRawBirthdate());
        }

        medicalRecords.add(medicalRecord);
        jsonWritingRepository.updateMedicalRecords(medicalRecords);

        return medicalRecord;
    }

    public MedicalRecord updateMedicalRecord(
            MedicalRecord medicalRecord) throws IOException {

        List<MedicalRecord> medicalRecords = jsonReadingRepository.getMedicalRecords();

        Optional<MedicalRecord> existingMedicalRecord = medicalRecords.stream()
                .filter(m -> m.getFirstName().equals(medicalRecord.getFirstName()))
                .filter(m -> m.getLastName().equals(medicalRecord.getLastName()))
                .findFirst();

        if (existingMedicalRecord.isEmpty()) {
            logger.error("This medical record doesn't exist");
            return null;
        }

        /*
         * Checks if rawBirthdate is in format MM/dd/YYYY.
         * 
         * If not, throws DateTimeParseException handled in Controller
         */
        if (medicalRecord.getRawBirthdate() != null && !medicalRecord.getRawBirthdate().isEmpty()) {
            getLocalBirthdate(medicalRecord.getRawBirthdate());
        }

        MedicalRecord updatedMedicalRecord = new MedicalRecord(
                medicalRecord.getFirstName(),
                medicalRecord.getLastName(),
                null, null, null);


        if (medicalRecord.getRawBirthdate() == null
                || medicalRecord.getRawBirthdate().trim().isEmpty())
            updatedMedicalRecord.setRawBirthdate(existingMedicalRecord.get().getRawBirthdate());
        else
            updatedMedicalRecord.setRawBirthdate(medicalRecord.getRawBirthdate());

        updatedMedicalRecord.setAllergies(
                Optional.ofNullable(medicalRecord.getAllergies())
                        .orElse(existingMedicalRecord.get().getAllergies()));

        updatedMedicalRecord.setMedications(
                Optional.ofNullable(medicalRecord.getMedications())
                        .orElse(existingMedicalRecord.get().getMedications()));

        medicalRecords.remove(existingMedicalRecord.get());
        medicalRecords.add(updatedMedicalRecord);
        jsonWritingRepository.updateMedicalRecords(medicalRecords);

        return updatedMedicalRecord;
    }

    public MedicalRecord deleteMedicalRecord(
            MedicalRecord medicalRecord) throws IOException {

        List<MedicalRecord> medicalRecords = jsonReadingRepository.getMedicalRecords();

        Optional<MedicalRecord> existingMedicalRecord = medicalRecords.stream()
                .filter(m -> m.getFirstName().equals(medicalRecord.getFirstName()))
                .filter(m -> m.getLastName().equals(medicalRecord.getLastName()))
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