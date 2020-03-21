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
import org.springframework.web.bind.annotation.RestController;

import com.nin.xloyalty.model.SpecialOffer;
import com.nin.xloyalty.service.SpecialOfferService;

@RestController
@RequestMapping("/api/v1/specialoffer")
public class ApiSpecialOfferController {
    private SpecialOfferService specialOfferService;

    @Autowired
    public ApiSpecialOfferController(SpecialOfferService specialOfferService) {
        this.specialOfferService = specialOfferService;
    }

    

    @GetMapping()
    public ResponseEntity<Map<String, Object>> listSpecialOffer() {
        try {
			List<SpecialOffer> currentSpecialOffer = specialOfferService.findAllActive();
			List<Map<String, Object>> result = new ArrayList<>();
			for(SpecialOffer specialOffer : currentSpecialOffer) {
			    Map<String, Object> obj = new HashMap<>();
			    obj.put("id", specialOffer.getSpecialOfferId());
			    obj.put("image", specialOffer.getImage());
			    obj.put("webLink", specialOffer.getWebLink());
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
