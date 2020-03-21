package com.nin.xloyalty.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nin.xloyalty.model.Store;
import com.nin.xloyalty.service.StoreService;
import com.nin.xloyalty.service.UtilityService;

@RestController
@RequestMapping("/api/v1")
public class ApiStoreController {
    private StoreService storeService;
    private UtilityService utilityService;

    @Autowired
    public ApiStoreController(StoreService storeService, UtilityService utilityService) {
        this.utilityService = utilityService;
        this.storeService = storeService;
    }


    @GetMapping("/store")
    public ResponseEntity<Map<String, Object>> listStore(@RequestParam(value = "productId") Long productId,
                                                         @RequestParam(value = "key") String key) {
        try {
            List<Store> currentStore = storeService.findByProductId(productId,key);
            List<Map<String, Object>> result = new ArrayList<>();
            for (Store store : currentStore) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("storeId", store.getStoreId());
                obj.put("name", store.getName());
                obj.put("address", store.getAddress());
                obj.put("phone", store.getPhone());
                obj.put("latitude",store.getLatitude());
                obj.put("longitude",store.getLongitude());
                List<String> utilities = new ArrayList<>();
                if (store.getUtilities() != null){
                    utilities = utilityService.findByListId(store.getUtilities());
                }

                obj.put("utilities", utilities);
                result.add(obj);
            }
            Map<String, Object> out = new HashMap<String, Object>() {{
                put("data", result);
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
}
