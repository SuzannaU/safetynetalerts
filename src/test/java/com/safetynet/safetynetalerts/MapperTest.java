package com.safetynet.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.safetynet.safetynetalerts.dto.AdultForChildAlert;
import com.safetynet.safetynetalerts.dto.ChildForChildAlert;
import com.safetynet.safetynetalerts.dto.PersonForFire;
import com.safetynet.safetynetalerts.dto.PersonForFlood;
import com.safetynet.safetynetalerts.dto.PersonForInfo;
import com.safetynet.safetynetalerts.dto.PersonForStation;
import com.safetynet.safetynetalerts.mapper.Mapper;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class MapperTest {
    @Autowired
    private Mapper mapper;
    private Person person;

    @BeforeEach
    private void setUp() {
        person = new Person(
                "john", "doe", "test_address", "test_city",
                "test_city", "test_phone", "test_email");
        person.setAge(5);
        person.setMedications(Arrays.asList("med1", "med2"));
        person.setAllergies(Arrays.asList("allergy1"));
    }

    @Test
    public void toPersonForStation_withCorrectParameters_returnsPersonForStation() {

        PersonForStation result = mapper.toPersonForStation(person);

        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
        assertEquals(person.getAddress(), result.getAddress());
        assertEquals(person.getPhone(), result.getPhone());
    }

    @Test
    public void toAdultForChildAlert_withCorrectParameters_returnsAdultForChildAlert() {

        AdultForChildAlert result = mapper.toAdultForChildAlert(person);

        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
    }

    @Test
    public void toChildForChildAlert_withCorrectParameters_returnsChildForChildAlert() {

        ChildForChildAlert result = mapper.toChildForChildAlert(person);

        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
        assertEquals(person.getAge(), result.getAge());
    }

    @Test
    public void toPersonForFire_withCorrectParameters_returnsPersonForFire() {

        PersonForFire result = mapper.toPersonForFire(person);

        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
        assertEquals(person.getAge(), result.getAge());
        assertEquals(person.getPhone(), result.getPhone());
        assertEquals(person.getAllergies(), result.getAllergies());
        assertEquals(person.getMedications(), result.getMedications());
    }

    @Test
    public void toPersonForFlood_withCorrectParameters_returnsPersonForFlood() {

        PersonForFlood result = mapper.toPersonForFlood(person);

        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
        assertEquals(person.getAge(), result.getAge());
        assertEquals(person.getPhone(), result.getPhone());
        assertEquals(person.getAllergies(), result.getAllergies());
        assertEquals(person.getMedications(), result.getMedications());
    }

    @Test
    public void toPersonForInfo_withCorrectParameters_returnsPersonForInfo() {

        PersonForInfo result = mapper.toPersonForInfo(person);

        assertEquals(person.getFirstName(), result.getFirstName());
        assertEquals(person.getLastName(), result.getLastName());
        assertEquals(person.getAddress(), result.getAddress());
        assertEquals(person.getAge(), result.getAge());
        assertEquals(person.getEmail(), result.getEmail());
        assertEquals(person.getAllergies(), result.getAllergies());
        assertEquals(person.getMedications(), result.getMedications());
    }
}
