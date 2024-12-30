package com.safetynet.safetynetalerts;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.safetynetalerts.controller.WebAppController;
import com.safetynet.safetynetalerts.dto.MedicalRecordDTO;
import com.safetynet.safetynetalerts.dto.PersonDTO;
import com.safetynet.safetynetalerts.dto.PersonForStation;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.*;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;

@SpringBootApplication
public class SafetynetalertsApplication {
        private static final Logger logger = LoggerFactory.getLogger(SafetynetalertsApplication.class);

	public static void main(String[] args) throws StreamWriteException, DatabindException, IOException {
		SpringApplication.run(SafetynetalertsApplication.class, args);

		JsonWritingRepository jsonWritingRepository = new JsonWritingRepository();
		JsonReadingRepository jsonReadingRepository = new JsonReadingRepository();
		MedicalRecordService medicalRecordService = new MedicalRecordService(jsonReadingRepository, jsonWritingRepository);
		FirestationService firestationService = new FirestationService(jsonReadingRepository, jsonWritingRepository);
		PersonService personService = new PersonService(jsonReadingRepository, jsonWritingRepository, medicalRecordService,firestationService);

		/*List<Person> persons = personService.getPersons();
		for (Person person : persons) {
			System.out.println(person.getPersonId() + ", " + person.getAge()
					+ ", " + person.getMedications() + ", " + person.getFirestationId());
		}*/

		/*List<Person> persons = personService.getPersons();
		List<Person> personsForStation = persons.stream()
                .filter(person -> person.getFirestationId() == 1)
                .collect(Collectors.toList());
		for (Person person : personsForStation) {
			System.out.print(person.getAddress() + ", ");
			System.out.print(person.getPersonId() + ", ");
			System.out.print(person.getFirestationId() + ", ");
			System.out.println(person.getAge());
		}*/

		
		/*List<MedicalRecordDTO> recordsDTO = jsonReadingRepository.getMedicalRecordsDTO();
		for (MedicalRecordDTO recordDTO : recordsDTO) {
			System.out.println(recordDTO.getBirthdate() + ", " + recordDTO.getAllergies() + ", " + recordDTO.getMedications());
		}*/

		/*List<MedicalRecord>	medicalRecords = medicalRecordService.getMedicalRecords(); 
		for (MedicalRecord medicalRecord : medicalRecords) { 
			System.out.println(medicalRecord.getAge() + ", " + medicalRecord.getPersonId() + ", " + medicalRecord.getMedications() + ", " + medicalRecord.getAllergies());
		}*/
		
		/*Set<Firestation> firestations = firestationService.getFirestations(); 
		for (Firestation firestation : firestations){ 
			System.out.println(firestation.getFirestationId() + ", " + firestation.getAddresses()); 
		}*/

	}

}
