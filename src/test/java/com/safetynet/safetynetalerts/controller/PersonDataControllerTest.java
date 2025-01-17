package com.safetynet.safetynetalerts.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.service.PersonService;

@WebMvcTest(controllers = PersonDataController.class)
public class PersonDataControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PersonService personService;
    private Person person;

    @BeforeEach
    private void setUp() {
        person = new Person("john", "doe", null, null, null, null, null);
    }

    @Test
    public void anyMethod_withIOException_returnsNotFound() throws Exception {

        when(personService.createPerson(any(Person.class)))
                .thenThrow(IOException.class);
        when(personService.updatePerson(any(Person.class)))
                .thenThrow(IOException.class);
        when(personService.deletePerson(any(Person.class)))
                .thenThrow(IOException.class);

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isNotFound());
        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isNotFound());

        verify(personService).createPerson(any(Person.class));
        verify(personService).updatePerson(any(Person.class));
        verify(personService).deletePerson(any(Person.class));
    }

    @Test
    public void anyMethod_withIllegalArgumentException_returnsBadRequest() throws Exception {

        when(personService.createPerson(any(Person.class)))
                .thenThrow(IllegalArgumentException.class);
        when(personService.updatePerson(any(Person.class)))
                .thenThrow(IllegalArgumentException.class);
        when(personService.deletePerson(any(Person.class)))
                .thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isBadRequest());

        verify(personService).createPerson(any(Person.class));
        verify(personService).updatePerson(any(Person.class));
        verify(personService).deletePerson(any(Person.class));
    }

    @Test
    public void createPerson_withCorrectRequest_returnsIsCreated() throws Exception {

        when(personService.createPerson(any(Person.class))).thenReturn(person);

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isCreated());

        verify(personService).createPerson(any(Person.class));
    }

    @Test
    public void createPerson_withNoData_returnsNoContent() throws Exception {

        when(personService.createPerson(any(Person.class))).thenReturn(null);

        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isNoContent());

        verify(personService).createPerson(any(Person.class));
    }

    @Test
    public void updatePerson_withCorrectRequest_returnsIsOk() throws Exception {

        when(personService.updatePerson(any(Person.class))).thenReturn(person);

        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(personService).updatePerson(any(Person.class));
    }

    @Test
    public void updatePerson_withNoData_returnsNoContent() throws Exception {

        when(personService.updatePerson(any(Person.class))).thenReturn(null);

        mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isNoContent());

        verify(personService).updatePerson(any(Person.class));
    }

    @Test
    public void deletePerson_withCorrectRequest_returnsIsOk() throws Exception {

        when(personService.deletePerson(any(Person.class))).thenReturn(person);

        mockMvc.perform(delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isOk());

        verify(personService).deletePerson(any(Person.class));
    }

    @Test
    public void deletePerson_withNoData_returnsNoContent() throws Exception {

        when(personService.deletePerson(any(Person.class))).thenReturn(null);

        mockMvc.perform(delete("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(person)))
                .andExpect(status().isNoContent());

        verify(personService).deletePerson(any(Person.class));
    }
}
