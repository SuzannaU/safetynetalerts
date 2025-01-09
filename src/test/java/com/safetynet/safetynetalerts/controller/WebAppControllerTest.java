package com.safetynet.safetynetalerts.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.safetynet.safetynetalerts.Mapper;
import com.safetynet.safetynetalerts.dto.ChildAlertData;
import com.safetynet.safetynetalerts.dto.ChildForChildAlert;
import com.safetynet.safetynetalerts.dto.CommunityEmailData;
import com.safetynet.safetynetalerts.dto.FireData;
import com.safetynet.safetynetalerts.dto.FirestationData;
import com.safetynet.safetynetalerts.dto.FloodData;
import com.safetynet.safetynetalerts.dto.InfoData;
import com.safetynet.safetynetalerts.dto.PhoneAlertData;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;
import com.safetynet.safetynetalerts.service.ChildAlertDataService;
import com.safetynet.safetynetalerts.service.CommunityEmailDataService;
import com.safetynet.safetynetalerts.service.FireDataService;
import com.safetynet.safetynetalerts.service.FirestationDataService;
import com.safetynet.safetynetalerts.service.FirestationService;
import com.safetynet.safetynetalerts.service.FloodDataService;
import com.safetynet.safetynetalerts.service.InfoDataService;
import com.safetynet.safetynetalerts.service.MedicalRecordService;
import com.safetynet.safetynetalerts.service.PersonService;
import com.safetynet.safetynetalerts.service.PhoneAlertDataService;

@WebMvcTest(controllers = WebAppController.class)
public class WebAppControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    PersonService personService;
    @MockitoBean
    FirestationService firestationService;
    @MockitoBean
    MedicalRecordService medicalRecordService;
    @MockitoBean
    FirestationDataService firestationDataService;
    @MockitoBean
    ChildAlertDataService childAlertDataService;
    @MockitoBean
    PhoneAlertDataService phoneAlertDataService;
    @MockitoBean
    FireDataService fireDataService;
    @MockitoBean
    FloodDataService floodDataService;
    @MockitoBean
    InfoDataService infoDataService;
    @MockitoBean
    CommunityEmailDataService communityEmailDataService;
    @MockitoBean
    Mapper mapper;
    @MockitoBean
    JsonWritingRepository jsonWritingRepository;


    @Test
    public void anyMethod_withIOException_returnsNotFound() throws Exception {

        when(firestationDataService.getFirestationData(anyInt()))
                .thenThrow(IOException.class);
        when(childAlertDataService.getChildAlertData(any(String.class)))
                .thenThrow(IOException.class);
        when(phoneAlertDataService.getPhoneAlertData(anyInt()))
                .thenThrow(IOException.class);
        when(fireDataService.getFireData(any(String.class)))
                .thenThrow(IOException.class);
        when(floodDataService.getFloodData(anyList()))
                .thenThrow(IOException.class);
        when(infoDataService.getInfoData(any(String.class)))
                .thenThrow(IOException.class);
        when(communityEmailDataService.getCommunityEmailData(any(String.class)))
                .thenThrow(IOException.class);


        mockMvc.perform(get("/firestation").param("stationNumber", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/childAlert").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/phoneAlert").param("firestation", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/fire").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/flood/stations").param("stations", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/personInfolastName={lastName}", "doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        mockMvc.perform(get("/communityEmail").param("city", "city")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(firestationDataService).getFirestationData(anyInt());
        verify(childAlertDataService).getChildAlertData(any(String.class));
        verify(phoneAlertDataService).getPhoneAlertData(anyInt());
        verify(fireDataService).getFireData(any(String.class));
        verify(floodDataService).getFloodData(anyList());
        verify(infoDataService).getInfoData(any(String.class));
        verify(communityEmailDataService).getCommunityEmailData(any(String.class));
    }

    @Test
    public void anyMethod_withNullPointerException_returnsBadRequest() throws Exception {

        when(firestationDataService.getFirestationData(anyInt()))
                .thenThrow(NullPointerException.class);
        when(childAlertDataService.getChildAlertData(any(String.class)))
                .thenThrow(NullPointerException.class);
        when(phoneAlertDataService.getPhoneAlertData(anyInt()))
                .thenThrow(NullPointerException.class);
        when(fireDataService.getFireData(any(String.class)))
                .thenThrow(NullPointerException.class);
        when(floodDataService.getFloodData(anyList()))
                .thenThrow(NullPointerException.class);
        when(infoDataService.getInfoData(any(String.class)))
                .thenThrow(NullPointerException.class);
        when(communityEmailDataService.getCommunityEmailData(any(String.class)))
                .thenThrow(NullPointerException.class);


        mockMvc.perform(get("/firestation").param("stationNumber", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/childAlert").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/phoneAlert").param("firestation", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/fire").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/flood/stations").param("stations", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/personInfolastName={lastName}", "doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        mockMvc.perform(get("/communityEmail").param("city", "city")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(firestationDataService).getFirestationData(anyInt());
        verify(childAlertDataService).getChildAlertData(any(String.class));
        verify(phoneAlertDataService).getPhoneAlertData(anyInt());
        verify(fireDataService).getFireData(any(String.class));
        verify(floodDataService).getFloodData(anyList());
        verify(infoDataService).getInfoData(any(String.class));
        verify(communityEmailDataService).getCommunityEmailData(any(String.class));
    }

    @Test
    public void getFirestationData_withCorrectRequest_returnsIsOk() throws Exception {

        when(firestationDataService.getFirestationData(anyInt())).thenReturn(new FirestationData());

        mockMvc.perform(get("/firestation").param("stationNumber", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(firestationDataService).getFirestationData(anyInt());
        verify(jsonWritingRepository).writeOutputFile(any(FirestationData.class));
    }

    @Test
    public void getFirestationData_withWrongRequest_returnsNotFound() throws Exception {

        when(firestationDataService.getFirestationData(anyInt())).thenReturn(null);

        mockMvc.perform(get("/firestation").param("stationNumber", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(firestationDataService).getFirestationData(anyInt());
        verify(jsonWritingRepository).writeOutputFile(null);
    }

    @Test
    public void getChildAlertData_withCorrectRequest_returnsIsOk() throws Exception {
        ChildAlertData childAlertData = new ChildAlertData();
        childAlertData.setChildren(
                Arrays.asList(new ChildForChildAlert(), new ChildForChildAlert()));
        when(childAlertDataService.getChildAlertData(any(String.class)))
                .thenReturn(childAlertData);

        mockMvc.perform(get("/childAlert").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(childAlertDataService).getChildAlertData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(any(ChildAlertData.class));
    }

    @Test
    public void getChildAlertData_withNoChildren_returnsBadRequest() throws Exception {
        ChildAlertData childAlertData = new ChildAlertData();
        childAlertData.setChildren(Arrays.asList());
        when(childAlertDataService.getChildAlertData(any(String.class)))
                .thenReturn(childAlertData);

        mockMvc.perform(get("/childAlert").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(childAlertDataService).getChildAlertData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(null);
    }

    @Test
    public void getChildAlertData_withWrongRequest_returnsNotFound() throws Exception {

        when(childAlertDataService.getChildAlertData(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/childAlert").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(childAlertDataService).getChildAlertData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(null);
    }

    @Test
    public void getPhoneAlertData_withCorrectRequest_returnsIsOk() throws Exception {

        when(phoneAlertDataService.getPhoneAlertData(anyInt())).thenReturn(new PhoneAlertData());

        mockMvc.perform(get("/phoneAlert").param("firestation", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(phoneAlertDataService).getPhoneAlertData(anyInt());
        verify(jsonWritingRepository).writeOutputFile(any(PhoneAlertData.class));
    }

    @Test
    public void getPhoneAlertData_withWrongRequest_returnsNotFound() throws Exception {

        when(phoneAlertDataService.getPhoneAlertData(anyInt())).thenReturn(null);

        mockMvc.perform(get("/phoneAlert").param("firestation", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(phoneAlertDataService).getPhoneAlertData(anyInt());
        verify(jsonWritingRepository).writeOutputFile(null);
    }

    @Test
    public void getFireData_withCorrectRequest_returnsIsOk() throws Exception {

        when(fireDataService.getFireData(any(String.class))).thenReturn(new FireData());

        mockMvc.perform(get("/fire").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(fireDataService).getFireData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(any(FireData.class));
    }

    @Test
    public void getFireData_withWrongRequest_returnsNotFound() throws Exception {

        when(fireDataService.getFireData(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/fire").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(fireDataService).getFireData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(null);
    }

    @Test
    public void getFloodData_withCorrectRequest_returnsIsOk() throws Exception {

        when(floodDataService.getFloodData(anyList())).thenReturn(new FloodData());

        mockMvc.perform(get("/flood/stations").param("stations", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(floodDataService).getFloodData(anyList());
        verify(jsonWritingRepository).writeOutputFile(any(FloodData.class));
    }

    @Test
    public void getFloodData_withWrongRequest_returnsNotFound() throws Exception {

        when(floodDataService.getFloodData(anyList())).thenReturn(null);

        mockMvc.perform(get("/flood/stations").param("stations", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(floodDataService).getFloodData(anyList());
        verify(jsonWritingRepository).writeOutputFile(null);
    }

    @Test
    public void getInfoData_withCorrectRequest_returnsIsOk() throws Exception {

        when(infoDataService.getInfoData(any(String.class))).thenReturn(new InfoData());

        mockMvc.perform(get("/personInfolastName={lastName}", "doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(infoDataService).getInfoData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(any(InfoData.class));
    }

    @Test
    public void getInfoData_withWrongRequest_returnsNotFound() throws Exception {

        when(infoDataService.getInfoData(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/personInfolastName={lastName}", "doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(infoDataService).getInfoData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(null);

    }

    @Test
    public void getCommunityEmailData_withCorrectRequest_returnsIsOk() throws Exception {

        when(communityEmailDataService.getCommunityEmailData(any(String.class)))
                .thenReturn(new CommunityEmailData());

        mockMvc.perform(get("/communityEmail").param("city", "city")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(communityEmailDataService).getCommunityEmailData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(any(CommunityEmailData.class));

    }

    @Test
    public void getCommunityEmailData_withWrongRequest_returnsNotFound() throws Exception {

        when(communityEmailDataService.getCommunityEmailData(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/communityEmail").param("city", "city")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(communityEmailDataService).getCommunityEmailData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(null);
    }
}
