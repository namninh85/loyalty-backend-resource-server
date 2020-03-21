package com.nin.xloyalty.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nin.springsecurity.user.User;
import com.nin.springsecurity.user.UserService;
import com.nin.xloyalty.model.CustomerRewardsLog;
import com.nin.xloyalty.model.LoyaltyProgram;
import com.nin.xloyalty.model.Voucher;
import com.nin.xloyalty.service.CustomerRewardsLogService;
import com.nin.xloyalty.service.LoyaltyProgramService;
import com.nin.xloyalty.service.VoucherService;
import com.nin.xloyalty.util.DateUtil;

@RestController
@RequestMapping("/api/v1")
public class ApiCustomerRewardsLogController {
    private CustomerRewardsLogService customerRewardsLogService;
    private UserService userService;
    private LoyaltyProgramService loyaltyProgramService;
    private VoucherService voucherService;

    @Autowired
    public ApiCustomerRewardsLogController(CustomerRewardsLogService customerRewardsLogService, UserService userService, LoyaltyProgramService loyaltyProgramService, VoucherService voucherService) {
        this.customerRewardsLogService = customerRewardsLogService;
        this.userService = userService;
        this.loyaltyProgramService = loyaltyProgramService;
        this.voucherService = voucherService;
    }




    @GetMapping("/my-rewards")
    public ResponseEntity<Map<String,Object>> findCustomerRewardsLogByCustomerId(){
        try {
        	Long date = new Date().getTime()/1000;
            User currentUser = userService.getCurrentUser();
            List<Object[]> listCustomerRewardsLog = customerRewardsLogService.findCustomerRewardsLogByCustomerId(currentUser.getId());
            List<Map<String, Object>> result = new ArrayList<>();
            for (Object[] customerRewardsLog : listCustomerRewardsLog) {
                LoyaltyProgram loyaltyProgram = loyaltyProgramService.finByLoyaltyProgramId(Long.parseLong(customerRewardsLog[0].toString()));
                Voucher voucher = voucherService.findByVoucherId(loyaltyProgram.getVoucherId());
                Map<String, Object> obj = new HashMap<>();
                obj.put("voucherId", voucher.getVoucherId());
                obj.put("loyaltyProgramId", loyaltyProgram.getLoyaltyProgramId());
                obj.put("voucherName", voucher.getName());
                obj.put("image", voucher.getImage());
                obj.put("price", voucher.getValue());
                obj.put("currency", voucher.getCurrency());
                obj.put("releasesTotal", loyaltyProgram.getTotal_release());
                obj.put("point", loyaltyProgram.getPoint());
                obj.put("uAvailables", customerRewardsLog[1]);
				if (date >= loyaltyProgram.getStartDate().getTime()/1000 && date <= loyaltyProgram.getEndDate().getTime()/1000){
					obj.put("status",true);
				}	else{
					obj.put("status",false);
				}

                result.add(obj);
            }
            Map<String, Object> out = new HashMap<String, Object>() {{
                put("data", result);
                put("error", 0);
            }};
            return new ResponseEntity<>(out, HttpStatus.OK);
        }catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<String, Object>();
            responseMap.put("Message", e.getMessage());
            responseMap.put("data", responseMap);
            responseMap.put("error", -1);
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/redeem-histories")
	public ResponseEntity<Map<String, Object>> redeemHistories() {
		try {
			User currentUser = userService.getCurrentUser();
			List<CustomerRewardsLog> listCustomerRewardsLog = customerRewardsLogService
					.findAllCustomerRewardsLogByCustomerIdActive(currentUser.getId());
			List<Map<String, Object>> result = new ArrayList<>();
			Map<Long, LoyaltyProgram> mapLoyaltyProgram = new HashMap<Long, LoyaltyProgram>();
			if (listCustomerRewardsLog != null) {
				Map<String, List<CustomerRewardsLog>> mapCountCustomerRewardsLog = new HashMap<String, List<CustomerRewardsLog>>();
				for (CustomerRewardsLog customerRewardsLog : listCustomerRewardsLog) {
					if (customerRewardsLog.getLoyaltyProgramId() != null) {
						LoyaltyProgram loyaltyProgram = loyaltyProgramService
								.finByLoyaltyProgramId(customerRewardsLog.getLoyaltyProgramId());
						if (loyaltyProgram != null) {
							mapLoyaltyProgram.put(loyaltyProgram.getLoyaltyProgramId(), loyaltyProgram);
							String keyMap = customerRewardsLog.getRewardDate() + "_" +customerRewardsLog.getLoyaltyProgramId() ;
							if (mapCountCustomerRewardsLog.get(keyMap) == null) {
								List<CustomerRewardsLog> listObjects = new ArrayList<CustomerRewardsLog>();
								listObjects.add(customerRewardsLog);
								mapCountCustomerRewardsLog.put(keyMap, listObjects);
							} else {
								mapCountCustomerRewardsLog.get(keyMap).add(customerRewardsLog);
							}

						}

					}

				}
				
				List<String> sortedKeys=new ArrayList(mapCountCustomerRewardsLog.keySet());
				Collections.sort(sortedKeys, Collections.reverseOrder());

				for (String key : sortedKeys) {
					List<CustomerRewardsLog> customerRewardsLogs = mapCountCustomerRewardsLog.get(key);
					if (customerRewardsLogs != null && customerRewardsLogs.size() > 0) {
						CustomerRewardsLog customerRewardsLog = customerRewardsLogs.get(0);
						LoyaltyProgram loyaltyProgram = mapLoyaltyProgram.get(customerRewardsLog.getLoyaltyProgramId());
						if (loyaltyProgram != null) {
							Voucher voucher = voucherService.findByVoucherId(loyaltyProgram.getVoucherId());
							Map<String, Object> obj = new HashMap<>();

							if (voucher != null) {
								obj.put("voucherId", voucher.getVoucherId());
								obj.put("voucherName", voucher.getName());
								obj.put("image", voucher.getImage());
								obj.put("price", voucher.getValue());
								obj.put("currency", voucher.getCurrency());
								obj.put("uAvailables", customerRewardsLogs.size());
								obj.put("loyaltyProgramId", loyaltyProgram.getLoyaltyProgramId());
								if (customerRewardsLog.getPointBurnEarn() != null) {
									obj.put("point",
											customerRewardsLog.getPointBurnEarn() * -1 * customerRewardsLogs.size());
								}
								if (customerRewardsLog.getRewardDate() != null) {
									obj.put("rewardDate",
											DateUtil.longDateToString(customerRewardsLog.getRewardDate().getTime()/1000));
								} else {
									obj.put("rewardDate", "");
								}
								result.add(obj);

							}
						}

					}

				}

			}

			Map<String, Object> out = new HashMap<String, Object>() {
				{
					put("data", result);
					put("error", 0);
				}
			};
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
