package com.nin.xloyalty.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nin.springsecurity.user.User;
import com.nin.springsecurity.user.UserService;
import com.nin.xloyalty.model.LoyaltyProgram;
import com.nin.xloyalty.model.Voucher;
import com.nin.xloyalty.service.CustomerRewardsLogService;
import com.nin.xloyalty.service.LoyaltyProgramService;
import com.nin.xloyalty.service.VoucherService;

@RestController
@RequestMapping("/api/v1")
public class ApiLoyaltyProgramController {

    private LoyaltyProgramService loyaltyProgramService;
    private VoucherService voucherService;
    private UserService userService;
    private CustomerRewardsLogService customerRewardsLogService;

    public ApiLoyaltyProgramController(LoyaltyProgramService loyaltyProgramService, VoucherService voucherService, UserService userService, CustomerRewardsLogService customerRewardsLogService) {
        this.loyaltyProgramService = loyaltyProgramService;
        this.voucherService = voucherService;
        this.userService = userService;
        this.customerRewardsLogService = customerRewardsLogService;
    }

    @GetMapping("/program")
    public ResponseEntity<Map<String, Object>> findLoyaltyProgramByDate() {
        try {
            List<LoyaltyProgram> currentLoyaltyProgram = loyaltyProgramService.findLoyaltyProgramByDate();
            User currentUser = userService.getCurrentUser();
            Map<Long, Long> mapLoyaltyIdUAvailables = loadMapLoyaltyIdUAvailables(currentUser.getId());
            List<Map<String, Object>> result = new ArrayList<>();
            for (LoyaltyProgram loyaltyProgram : currentLoyaltyProgram) {
                Voucher voucher =voucherService.findByVoucherId(loyaltyProgram.getVoucherId());
                Map<String, Object> obj = new HashMap<>();
                obj.put("voucherId", voucher.getVoucherId());
                obj.put("loyaltyProgramId", loyaltyProgram.getLoyaltyProgramId());
                obj.put("voucherName", voucher.getName());
                obj.put("image", voucher.getImage());
                obj.put("price", voucher.getValue());
                obj.put("currency", voucher.getCurrency());
                obj.put("releasesTotal", loyaltyProgram.getTotal_release());
                obj.put("point", loyaltyProgram.getPoint());
                long uAvailables = mapLoyaltyIdUAvailables.get(loyaltyProgram.getLoyaltyProgramId()) != null ? mapLoyaltyIdUAvailables.get(loyaltyProgram.getLoyaltyProgramId()) :  0;
                obj.put("uAvailables", uAvailables);
                obj.put("status",true);
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

	private Map<Long, Long> loadMapLoyaltyIdUAvailables(Long currentCustomerId) {
		Map<Long, Long> mapLoyaltyIdUAvailables = new HashMap<Long, Long>();
		List<Object[]> listCustomerRewardsLog = customerRewardsLogService.findCustomerRewardsLogByCustomerId(currentCustomerId);
        
        System.out.println(listCustomerRewardsLog.toString());
        for (Object[] customerRewardsLog : listCustomerRewardsLog) {
        	long loyaltyId = 0;
        	try {
				loyaltyId = Long.parseLong(customerRewardsLog[0].toString());
			} catch (Exception e) {
				
			}
        	
        	long uAvailables = 0;
        	try {
        		uAvailables = Long.parseLong(customerRewardsLog[1].toString());
			} catch (Exception e) {
				
			}
        	mapLoyaltyIdUAvailables.put(loyaltyId, uAvailables);
        }
		return mapLoyaltyIdUAvailables;
	}
}
