package com.safetynet.safetynetalerts.service;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.safetynet.safetynetalerts.dto.ChildAlertData;
import com.safetynet.safetynetalerts.dto.CommunityEmailData;
import com.safetynet.safetynetalerts.dto.FireData;
import com.safetynet.safetynetalerts.dto.FirestationData;
import com.safetynet.safetynetalerts.dto.FloodData;
import com.safetynet.safetynetalerts.dto.InfoData;
import com.safetynet.safetynetalerts.dto.PhoneAlertData;

@Service
public class WebAppService {

    private FirestationDataService firestationDataService;
    private ChildAlertDataService childAlertDataService;
    private PhoneAlertDataService phoneAlertDataService;
    private FireDataService fireDataService;
    private FloodDataService floodDataService;
    private InfoDataService infoDataService;
    private CommunityEmailDataService communityEmailDataService;

    public WebAppService(
            FirestationDataService firestationDataService,
            ChildAlertDataService childAlertDataService,
            PhoneAlertDataService phoneAlertDataService,
            FireDataService fireDataService,
            FloodDataService floodDataService,
            InfoDataService infoDataService,
            CommunityEmailDataService communityEmailDataService) {
        this.firestationDataService = firestationDataService;
        this.childAlertDataService = childAlertDataService;
        this.phoneAlertDataService = phoneAlertDataService;
        this.fireDataService = fireDataService;
        this.floodDataService = floodDataService;
        this.infoDataService = infoDataService;
        this.communityEmailDataService = communityEmailDataService;
    }

    public FirestationData getFirestationData(int stationNumber) throws IOException {
        return firestationDataService.getFirestationData(stationNumber);
    }

    public ChildAlertData getChildAlertData(String address) throws IOException {
        return childAlertDataService.getChildAlertData(address);
    }

    public PhoneAlertData getPhoneAlertData(int stationNumber) throws IOException {
        return phoneAlertDataService.getPhoneAlertData(stationNumber);
    }

    public FireData getFireData(String address) throws IOException {
        return fireDataService.getFireData(address);
    }

    public FloodData getFloodData(List<Integer> listOfStationIds) throws IOException {
        return floodDataService.getFloodData(listOfStationIds);
    }

    public InfoData getInfoData(String lastName) throws IOException {
        return infoDataService.getInfoData(lastName);
    }

    public CommunityEmailData getCommunityEmailData(String city) throws IOException {
        return communityEmailDataService.getCommunityEmailData(city);
    }
}
