package com.safetynet.safetynetalerts.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.safetynet.safetynetalerts.dto.ChildAlertData;
import com.safetynet.safetynetalerts.dto.CommunityEmailData;
import com.safetynet.safetynetalerts.dto.FireData;
import com.safetynet.safetynetalerts.dto.FirestationData;
import com.safetynet.safetynetalerts.dto.FloodData;
import com.safetynet.safetynetalerts.dto.InfoData;
import com.safetynet.safetynetalerts.dto.PhoneAlertData;

@SpringBootTest
public class WebAppServiceTest {

    @MockitoBean
    private FirestationDataService firestationDataService;
    @MockitoBean
    private ChildAlertDataService childAlertDataService;
    @MockitoBean
    private PhoneAlertDataService phoneAlertDataService;
    @MockitoBean
    private FireDataService fireDataService;
    @MockitoBean
    private FloodDataService floodDataService;
    @MockitoBean
    private InfoDataService infoDataService;
    @MockitoBean
    private CommunityEmailDataService communityEmailDataService;
    @Autowired
    private WebAppService webAppService;

    @Test
    public void getFirestationData_callsService() throws Exception {

        when(firestationDataService.getFirestationData(anyInt())).thenReturn(new FirestationData());

        assertNotNull(webAppService.getFirestationData(1));
        verify(firestationDataService).getFirestationData(anyInt());
    }

    @Test
    public void getChildAlertData_callsService() throws Exception {
        when(childAlertDataService.getChildAlertData(any(String.class)))
                .thenReturn(new ChildAlertData());

        assertNotNull(webAppService.getChildAlertData("address"));
        verify(childAlertDataService).getChildAlertData(any(String.class));
    }

    @Test
    public void getPhoneAlertData_callsService() throws Exception {

        when(phoneAlertDataService.getPhoneAlertData(anyInt())).thenReturn(new PhoneAlertData());

        assertNotNull(webAppService.getPhoneAlertData(1));
        verify(phoneAlertDataService).getPhoneAlertData(anyInt());
    }

    @Test
    public void getFireData_callsService() throws Exception {

        when(fireDataService.getFireData(any(String.class))).thenReturn(new FireData());

        assertNotNull(webAppService.getFireData("address"));
        verify(fireDataService).getFireData(any(String.class));
    }

    @Test
    public void getFloodData_callsService() throws Exception {

        when(floodDataService.getFloodData(anyList())).thenReturn(new FloodData());

        assertNotNull(webAppService.getFloodData(Arrays.asList()));
        verify(floodDataService).getFloodData(anyList());
    }

    @Test
    public void getInfoData_callsService() throws Exception {

        when(infoDataService.getInfoData(any(String.class))).thenReturn(new InfoData());

        assertNotNull(webAppService.getInfoData("lastName"));
        verify(infoDataService).getInfoData(any(String.class));
    }

    @Test
    public void getCommunityEmailData_callsService() throws Exception {

        when(communityEmailDataService.getCommunityEmailData(any(String.class)))
                .thenReturn(new CommunityEmailData());

        assertNotNull(webAppService.getCommunityEmailData("city"));
        verify(communityEmailDataService).getCommunityEmailData(any(String.class));
    }
}
