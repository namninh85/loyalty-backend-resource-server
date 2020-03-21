package com.nin.xloyalty.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nin.xloyalty.model.AppDataConfig;
import com.nin.xloyalty.service.AppDataConfigService;
import com.nin.xloyalty.service.InterestedFieldService;

@RestController
@RequestMapping("/api/v1")
public class ApiAppDataConfigController {
    private AppDataConfigService appDataConfigService;
    private InterestedFieldService interestedFieldService;

    public ApiAppDataConfigController(AppDataConfigService appDataConfigService, InterestedFieldService interestedFieldService) {
        this.appDataConfigService = appDataConfigService;
        this.interestedFieldService = interestedFieldService;
    }

    @GetMapping("/app-data")
    public ResponseEntity<Map<String, Object>> findAppDataConfig() {
        try {
            AppDataConfig appDataConfig = appDataConfigService.findByIsActive();
            Map<String, Object> obj = new HashMap<>();
            if(appDataConfig != null ) {
            	obj.put("appDataConfigId", appDataConfig.getAppDataConfigId());
                obj.put("bannerHeaderImage", appDataConfig.getBannerHeaderImg());
                List<Map<String, Object>> listFields = new ArrayList<>();
                if (appDataConfig.getInterestedFields() != null) {
                    List<Object[]> interestedFields = interestedFieldService.findByListId(appDataConfig.getInterestedFields());
                    for (Object[] field : interestedFields) {
                        Map<String, Object> fieldObj = new HashMap<>();
                        fieldObj.put("value", field[0]);
                        fieldObj.put("name", field[1]);
                        listFields.add(fieldObj);
                    }
                }

                obj.put("interestedFields", listFields);
                obj.put("logo", appDataConfig.getLogo());
                obj.put("privacyLink", appDataConfig.getPrivacyLink());
                obj.put("termConditionsLink", appDataConfig.getTermConditionsLink());
            }
            
            Map<String, Object> out = new HashMap<String, Object>() {{
                put("data", obj);
                put("error", 0);
            }};

            return new ResponseEntity<>(out, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<String, Object>();
            responseMap.put("Message", e.getMessage());
            responseMap.put("data", responseMap);
            responseMap.put("error", -1);
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/app-data")
    public ResponseEntity<Map<String, Object>> createOrUpdateAppDataConfig(@RequestBody Map<String, Object> appDataConfigDTO) {
        try {
            Map<String, Object> obj = new HashMap<>();
            Map<String, Object> out = new HashMap<String, Object>();
            AppDataConfig appDataConfig = new AppDataConfig();
            Integer appDataConfigId = (Integer) appDataConfigDTO.get("appDataConfigId");
            if (appDataConfigId != null) {
                AppDataConfig appDataConfigUpdate = appDataConfigService.findByAppDataConfigId(appDataConfigId.longValue());
                if (appDataConfigUpdate != null){
                    appDataConfig = appDataConfigUpdate;
                }

            }
            if (appDataConfigDTO.get("bannerHeaderImg") != null) {
                appDataConfig.setBannerHeaderImg(appDataConfigDTO.get("bannerHeaderImg").toString());
            }
            if (appDataConfigDTO.get("logo") != null) {
                appDataConfig.setLogo(appDataConfigDTO.get("logo").toString());
            }
            if (appDataConfigDTO.get("privacyLink") != null) {
                appDataConfig.setPrivacyLink(appDataConfigDTO.get("privacyLink").toString());
            }
            if (appDataConfigDTO.get("termConditionsLink") != null) {
                appDataConfig.setTermConditionsLink(appDataConfigDTO.get("termConditionsLink").toString());
            }
            if (appDataConfigDTO.get("interestedFields") != null) {
                String interestedFields = appDataConfigDTO.get("interestedFields").toString();
                String replaceString = interestedFields.replace("[", "").replace("]", "").replace(" ", "");
                appDataConfig.setInterestedFields(replaceString);
            }
            appDataConfig.setDeleted(false);
            appDataConfigService.createOrUpdateAppDataConfig(appDataConfig);
            obj.put("Message", "Update app data config success !");
            out.put("data", obj);
            out.put("error", 0);
            return new ResponseEntity<>(out, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<String, Object>();
            responseMap.put("Message", e.getMessage());
            responseMap.put("data", responseMap);
            responseMap.put("error", -1);
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
}
