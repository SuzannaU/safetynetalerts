package com.safetynet.safetynetalerts.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
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
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.service.FirestationService;

@WebMvcTest(controllers = FirestationDataController.class)
public class FirestationDataControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private FirestationService firestationService;
    private Firestation firestation;

    @BeforeEach
    private void setUp() {
        firestation = new Firestation("address", "1");
    }

    @Test
    public void anyMethod_withIOException_returnsNotFound() throws Exception {

        when(firestationService.createFirestation(any(Firestation.class)))
                .thenThrow(IOException.class);
        when(firestationService.updateFirestation(any(Firestation.class)))
                .thenThrow(IOException.class);
        when(firestationService.deleteFirestation(any(Firestation.class)))
                .thenThrow(IOException.class);

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isNotFound());
        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isNotFound());

        verify(firestationService).createFirestation(any(Firestation.class));
        verify(firestationService).updateFirestation(any(Firestation.class));
        verify(firestationService).deleteFirestation(any(Firestation.class));
    }

    @Test
    public void anyMethod_withIllegalArgumentException_returnsBadRequest() throws Exception {

        when(firestationService.createFirestation(any(Firestation.class)))
                .thenThrow(IllegalArgumentException.class);
        when(firestationService.updateFirestation(any(Firestation.class)))
                .thenThrow(IllegalArgumentException.class);
        when(firestationService.deleteFirestation(any(Firestation.class)))
                .thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isBadRequest());

        verify(firestationService).createFirestation(any(Firestation.class));
        verify(firestationService).updateFirestation(any(Firestation.class));
        verify(firestationService).deleteFirestation(any(Firestation.class));
    }

    @Test
    public void createFirestation_withCorrectRequest_returnsIsCreated() throws Exception {

        when(firestationService.createFirestation(any(Firestation.class))).thenReturn(firestation);

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isCreated());
        verify(firestationService).createFirestation(any(Firestation.class));
    }

    @Test
    public void createFirestation_withWrongRequest_returnsBadRequest() throws Exception {

        when(firestationService.createFirestation(any(Firestation.class))).thenReturn(null);

        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isBadRequest());
        verify(firestationService).createFirestation(any(Firestation.class));
    }

    @Test
    public void updateFirestation_withCorrectRequest_returnsIsCreated() throws Exception {

        when(firestationService.updateFirestation(any(Firestation.class))).thenReturn(firestation);

        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isAccepted());
        verify(firestationService).updateFirestation(any(Firestation.class));
    }

    @Test
    public void updateFirestation_withWrongRequest_returnsBadRequest() throws Exception {

        when(firestationService.updateFirestation(any(Firestation.class))).thenReturn(null);

        mockMvc.perform(put("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isBadRequest());
        verify(firestationService).updateFirestation(any(Firestation.class));
    }

    @Test
    public void deleteFirestation_withCorrectRequest_returnsIsAccepted() throws Exception {

        when(firestationService.deleteFirestation(any(Firestation.class))).thenReturn(firestation);

        mockMvc.perform(delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isAccepted());
        verify(firestationService).deleteFirestation(any(Firestation.class));
    }

    @Test
    public void deleteFirestation_withWrongRequest_returnsBadRequest() throws Exception {

        when(firestationService.deleteFirestation(any(Firestation.class))).thenReturn(null);

        mockMvc.perform(delete("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isBadRequest());
        verify(firestationService).deleteFirestation(any(Firestation.class));
    }
}
