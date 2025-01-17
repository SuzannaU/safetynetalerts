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
import com.safetynet.safetynetalerts.dto.AdultForChildAlert;
import com.safetynet.safetynetalerts.dto.ChildAlertData;
import com.safetynet.safetynetalerts.dto.ChildForChildAlert;
import com.safetynet.safetynetalerts.dto.CommunityEmailData;
import com.safetynet.safetynetalerts.dto.FireData;
import com.safetynet.safetynetalerts.dto.FirestationData;
import com.safetynet.safetynetalerts.dto.FloodData;
import com.safetynet.safetynetalerts.dto.InfoData;
import com.safetynet.safetynetalerts.dto.PhoneAlertData;
import com.safetynet.safetynetalerts.repository.JsonWritingRepository;
import com.safetynet.safetynetalerts.service.WebAppService;

@WebMvcTest(controllers = WebAppController.class)
public class WebAppControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private WebAppService webAppService;
    @MockitoBean
    private JsonWritingRepository jsonWritingRepository;


    @Test
    public void anyMethod_withIOException_returnsNotFound() throws Exception {

        when(webAppService.getFirestationData(anyInt()))
                .thenThrow(IOException.class);
        when(webAppService.getChildAlertData(any(String.class)))
                .thenThrow(IOException.class);
        when(webAppService.getPhoneAlertData(anyInt()))
                .thenThrow(IOException.class);
        when(webAppService.getFireData(any(String.class)))
                .thenThrow(IOException.class);
        when(webAppService.getFloodData(anyList()))
                .thenThrow(IOException.class);
        when(webAppService.getInfoData(any(String.class)))
                .thenThrow(IOException.class);
        when(webAppService.getCommunityEmailData(any(String.class)))
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

        verify(webAppService).getFirestationData(anyInt());
        verify(webAppService).getChildAlertData(any(String.class));
        verify(webAppService).getPhoneAlertData(anyInt());
        verify(webAppService).getFireData(any(String.class));
        verify(webAppService).getFloodData(anyList());
        verify(webAppService).getInfoData(any(String.class));
        verify(webAppService).getCommunityEmailData(any(String.class));
    }

    @Test
    public void anyMethod_withNullPointerException_returnsBadRequest() throws Exception {

        when(webAppService.getFirestationData(anyInt()))
                .thenThrow(NullPointerException.class);
        when(webAppService.getChildAlertData(any(String.class)))
                .thenThrow(NullPointerException.class);
        when(webAppService.getPhoneAlertData(anyInt()))
                .thenThrow(NullPointerException.class);
        when(webAppService.getFireData(any(String.class)))
                .thenThrow(NullPointerException.class);
        when(webAppService.getFloodData(anyList()))
                .thenThrow(NullPointerException.class);
        when(webAppService.getInfoData(any(String.class)))
                .thenThrow(NullPointerException.class);
        when(webAppService.getCommunityEmailData(any(String.class)))
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

        verify(webAppService).getFirestationData(anyInt());
        verify(webAppService).getChildAlertData(any(String.class));
        verify(webAppService).getPhoneAlertData(anyInt());
        verify(webAppService).getFireData(any(String.class));
        verify(webAppService).getFloodData(anyList());
        verify(webAppService).getInfoData(any(String.class));
        verify(webAppService).getCommunityEmailData(any(String.class));
    }

    @Test
    public void getFirestationData_withCorrectRequest_returnsIsOk() throws Exception {

        when(webAppService.getFirestationData(anyInt())).thenReturn(new FirestationData());

        mockMvc.perform(get("/firestation").param("stationNumber", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(webAppService).getFirestationData(anyInt());
        verify(jsonWritingRepository).writeOutputFile(any(FirestationData.class));
    }

    @Test
    public void getFirestationData_withNoData_returnsNoContent() throws Exception {

        when(webAppService.getFirestationData(anyInt())).thenReturn(null);

        mockMvc.perform(get("/firestation").param("stationNumber", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(webAppService).getFirestationData(anyInt());
    }

    @Test
    public void getChildAlertData_withCorrectRequest_returnsIsOk() throws Exception {
        ChildAlertData childAlertData = new ChildAlertData();
        childAlertData.setChildren(
                Arrays.asList(new ChildForChildAlert(), new ChildForChildAlert()));
        when(webAppService.getChildAlertData(any(String.class)))
                .thenReturn(childAlertData);

        mockMvc.perform(get("/childAlert").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(webAppService).getChildAlertData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(any(ChildAlertData.class));
    }

    @Test
    public void getChildAlertData_withNoChildren_returnsNotFound() throws Exception {
        ChildAlertData childAlertData = new ChildAlertData();
        childAlertData.setChildren(Arrays.asList());
        childAlertData.setAdults(Arrays.asList(new AdultForChildAlert()));
        when(webAppService.getChildAlertData(any(String.class)))
                .thenReturn(childAlertData);

        mockMvc.perform(get("/childAlert").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(webAppService).getChildAlertData(any(String.class));
    }

    @Test
    public void getChildAlertData_withNoData_returnsNoContent() throws Exception {

        when(webAppService.getChildAlertData(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/childAlert").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(webAppService).getChildAlertData(any(String.class));
    }

    @Test
    public void getPhoneAlertData_withCorrectRequest_returnsIsOk() throws Exception {

        when(webAppService.getPhoneAlertData(anyInt())).thenReturn(new PhoneAlertData());

        mockMvc.perform(get("/phoneAlert").param("firestation", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(webAppService).getPhoneAlertData(anyInt());
        verify(jsonWritingRepository).writeOutputFile(any(PhoneAlertData.class));
    }

    @Test
    public void getPhoneAlertData_withNoData_returnsNoContent() throws Exception {

        when(webAppService.getPhoneAlertData(anyInt())).thenReturn(null);

        mockMvc.perform(get("/phoneAlert").param("firestation", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(webAppService).getPhoneAlertData(anyInt());
    }

    @Test
    public void getFireData_withCorrectRequest_returnsIsOk() throws Exception {

        when(webAppService.getFireData(any(String.class))).thenReturn(new FireData());

        mockMvc.perform(get("/fire").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(webAppService).getFireData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(any(FireData.class));
    }

    @Test
    public void getFireData_withNoData_returnsNoContent() throws Exception {

        when(webAppService.getFireData(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/fire").param("address", "address")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(webAppService).getFireData(any(String.class));
    }

    @Test
    public void getFloodData_withCorrectRequest_returnsIsOk() throws Exception {

        when(webAppService.getFloodData(anyList())).thenReturn(new FloodData());

        mockMvc.perform(get("/flood/stations").param("stations", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(webAppService).getFloodData(anyList());
        verify(jsonWritingRepository).writeOutputFile(any(FloodData.class));
    }

    @Test
    public void getFloodData_withNoData_returnsNoContent() throws Exception {

        when(webAppService.getFloodData(anyList())).thenReturn(null);

        mockMvc.perform(get("/flood/stations").param("stations", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(webAppService).getFloodData(anyList());
    }

    @Test
    public void getInfoData_withCorrectRequest_returnsIsOk() throws Exception {

        when(webAppService.getInfoData(any(String.class))).thenReturn(new InfoData());

        mockMvc.perform(get("/personInfolastName={lastName}", "doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(webAppService).getInfoData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(any(InfoData.class));
    }

    @Test
    public void getInfoData_withNoData_returnsNoContent() throws Exception {

        when(webAppService.getInfoData(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/personInfolastName={lastName}", "doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(webAppService).getInfoData(any(String.class));

    }

    @Test
    public void getCommunityEmailData_withCorrectRequest_returnsIsOk() throws Exception {

        when(webAppService.getCommunityEmailData(any(String.class)))
                .thenReturn(new CommunityEmailData());

        mockMvc.perform(get("/communityEmail").param("city", "city")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(webAppService).getCommunityEmailData(any(String.class));
        verify(jsonWritingRepository).writeOutputFile(any(CommunityEmailData.class));

    }

    @Test
    public void getCommunityEmailData_withNoData_returnsNoContent() throws Exception {

        when(webAppService.getCommunityEmailData(any(String.class))).thenReturn(null);

        mockMvc.perform(get("/communityEmail").param("city", "city")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(webAppService).getCommunityEmailData(any(String.class));
    }
}
