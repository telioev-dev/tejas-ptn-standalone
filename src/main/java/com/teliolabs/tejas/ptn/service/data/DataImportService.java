package com.teliolabs.tejas.ptn.service.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.teliolabs.tejas.ptn.service.client.ApiClientAuthService;
import com.teliolabs.tejas.ptn.service.client.ApiClientInventoryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataImportService {

    private final ApiClientAuthService apiClientAuthService;
    private final ApiClientInventoryService apiClientInventoryService;

    public void importInventory() {
        apiClientAuthService.authenticate();
        apiClientInventoryService.getPTN1TopologyData();
        apiClientInventoryService.getPTN2TopologyData();
       apiClientInventoryService.getPtn1ServiceData();
       apiClientInventoryService.getPtn2ServiceData();
    }
}
