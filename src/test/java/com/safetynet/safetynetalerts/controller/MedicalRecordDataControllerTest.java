package com.safetynet.safetynetalerts.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.service.MedicalRecordService;

@WebMvcTest(controllers = MedicalRecordDataController.class)
public class MedicalRecordDataControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private MedicalRecordService medicalRecordService;
    private MedicalRecord medicalRecord;

    @BeforeEach
    private void setUp() {
        medicalRecord = new MedicalRecord("john", "doe", null, null, null);
    }

    @Test
    public void anyMethod_withIllegalArgumentException_returnsBadRequest() throws Exception {

        when(medicalRecordService.createMedicalRecord(any(MedicalRecord.class)))
                .thenThrow(IllegalArgumentException.class);
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class)))
                .thenThrow(IllegalArgumentException.class);
        when(medicalRecordService.deleteMedicalRecord(any(MedicalRecord.class)))
                .thenThrow(IllegalArgumentException.class);

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService).createMedicalRecord(any(MedicalRecord.class));
        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecord.class));
        verify(medicalRecordService).deleteMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void anyMethod_withIOException_returnsNotFound() throws Exception {

        when(medicalRecordService.createMedicalRecord(any(MedicalRecord.class)))
                .thenThrow(IOException.class);
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class)))
                .thenThrow(IOException.class);
        when(medicalRecordService.deleteMedicalRecord(any(MedicalRecord.class)))
                .thenThrow(IOException.class);

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isNotFound());
        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isNotFound());
        mockMvc.perform(delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isNotFound());

        verify(medicalRecordService).createMedicalRecord(any(MedicalRecord.class));
        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecord.class));
        verify(medicalRecordService).deleteMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void anyMethod_withDateTimeParseException_returnsBadRequest() throws Exception {

        when(medicalRecordService.createMedicalRecord(any(MedicalRecord.class)))
                .thenThrow(DateTimeParseException.class);
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class)))
                .thenThrow(DateTimeParseException.class);
        when(medicalRecordService.deleteMedicalRecord(any(MedicalRecord.class)))
                .thenThrow(DateTimeParseException.class);

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest());
        mockMvc.perform(delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService).createMedicalRecord(any(MedicalRecord.class));
        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecord.class));
        verify(medicalRecordService).deleteMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void createMedicalRecord_withCorrectRequest_returnsIsCreated() throws Exception {

        when(medicalRecordService.createMedicalRecord(any(MedicalRecord.class)))
                .thenReturn(medicalRecord);

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isCreated());

        verify(medicalRecordService).createMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void createMedicalRecord_withWrongRequest_returnsBadRequest() throws Exception {

        when(medicalRecordService.createMedicalRecord(any(MedicalRecord.class))).thenReturn(null);

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService).createMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void updateMedicalRecord_withCorrectRequest_returnsIsCreated() throws Exception {

        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class)))
                .thenReturn(medicalRecord);

        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isAccepted());

        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void updateMedicalRecord_withWrongRequest_returnsBadRequest() throws Exception {

        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(null);

        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void deleteMedicalRecord_withCorrectRequest_returnsIsAccepted() throws Exception {

        when(medicalRecordService.deleteMedicalRecord(any(MedicalRecord.class)))
                .thenReturn(medicalRecord);

        mockMvc.perform(delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isAccepted());

        verify(medicalRecordService).deleteMedicalRecord(any(MedicalRecord.class));
    }

    @Test
    public void deleteMedicalRecord_withWrongRequest_returnsBadRequest() throws Exception {

        when(medicalRecordService.deleteMedicalRecord(any(MedicalRecord.class))).thenReturn(null);

        mockMvc.perform(delete("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalRecord)))
                .andExpect(status().isBadRequest());

        verify(medicalRecordService).deleteMedicalRecord(any(MedicalRecord.class));
    }
}
