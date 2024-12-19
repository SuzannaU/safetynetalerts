package com.safetynet.safetynetalerts;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.MedicalRecordFromJson;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.model.PersonFromJson;
import com.safetynet.safetynetalerts.repository.JsonRepository;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;

@SpringBootApplication
public class SafetynetalertsApplication {

	public static void main(String[] args)
			throws StreamWriteException, DatabindException, IOException {
		SpringApplication.run(SafetynetalertsApplication.class, args);

		JsonRepository jsonRepository = new JsonRepository();

		/*
		 * List<PersonFromJson> personsFromJson = jsonRepository.getPersonsFromJson(); for
		 * (PersonFromJson personFromJson : personsFromJson) {
		 * System.out.println(personFromJson.getAddress());
		 * System.out.println(personFromJson.getFirstName());
		 * System.out.println(personFromJson.getZip()); }
		 */

		 MedicalRecordService medicalRecordService = new MedicalRecordService(jsonRepository);
		PersonService personService = new PersonService(jsonRepository, medicalRecordService);
		List<Person> persons = personService.getPersons();
		for (Person person : persons){
			System.out.print(person.getAddress()+ ", ");
			System.out.print(person.getPersonId()+ ", ");
			System.out.print(person.getBirthdate()+ ", ");
			System.out.println(person.getAge());

		} 

		/* List<MedicalRecordFromJson> recordsFromJson = jsonRepository.getMedicalRecordsFromJson();
		for (MedicalRecordFromJson recordFromJson : recordsFromJson) {
			System.out.println(recordFromJson.getBirthdate());
			System.out.println(recordFromJson.getAllergies());
			System.out.println(recordFromJson.getMedications());
		}
		MedicalRecordService medicalRecordService = new MedicalRecordService(jsonRepository);
		List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
		for (MedicalRecord medicalRecord : medicalRecords) {
			System.out.println(medicalRecord.getAge());
			System.out.println(medicalRecord.getPersonId());
			System.out.println(medicalRecord.getMedications());
			System.out.println(medicalRecord.getAllergies());
		} */

		/*
		 * FirestationService firestationService = new FirestationService(jsonRepository);
		 * Set<Firestation> firestations = firestationService.getFirestations(); for (Firestation
		 * firestation : firestations) { System.out.println(firestation.getfirestationId());
		 * System.out.println(firestation.getAddresses()); }
		 */


		/*FirestationService firestationService = new FirestationService(jsonRepository);
		Set<Firestation> firestations = firestationService.getFirestations();
		for (Firestation firestation : firestations){
			System.out.println(firestation.getFirestationId() + ", " + firestation.getAddresses());
		}*/


	}

}
