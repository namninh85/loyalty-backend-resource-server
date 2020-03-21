package com.nin.xloyalty.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nin.springsecurity.user.User;
import com.nin.springsecurity.user.UserService;
import com.nin.xloyalty.model.Contact;
import com.nin.xloyalty.model.Customer;
import com.nin.xloyalty.service.CustomerService;
import com.nin.xloyalty.service.InterestedFieldService;
import com.nin.xloyalty.util.DateUtil;

@RestController
@RequestMapping("/api/v1/user")
public class ApiUserController {

	private UserService userService;
	private InterestedFieldService interestedFieldService;
	private CustomerService customerService;

	@Autowired
	public ApiUserController(UserService userService, InterestedFieldService interestedFieldService, CustomerService customerService) {
		this.userService = userService;
		this.interestedFieldService = interestedFieldService;
		this.customerService = customerService;
	}

	@GetMapping
	public ResponseEntity<Map<String, Object>> userDetails() {

		User currentUser = userService.getCurrentUser();
		Map<String, Object> out = new HashMap<String, Object>() {
			{
				put("id", currentUser.getId());
				put("email", currentUser.getUsername());

			}
		};
		return new ResponseEntity<>(out, HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity<Map<String, Object>> userProfile() {

		try {
			User currentUser = userService.getCurrentUser();
			Customer dbCustomer = customerService.findByCustomerId(currentUser.getId());
			Contact dbContact = customerService.findByContactId(currentUser.getId());
			
			if(dbCustomer == null) {
				dbCustomer = customerService.createOrUpdateCustomer(new Customer(currentUser.getId().longValue(), currentUser.getUsername()));
			}
			
			if(dbContact == null) {
				dbContact = customerService.createOrUpdateContact(new Contact(currentUser.getId().longValue(),"New Customer", currentUser.getUsername()));
			}
			
			Customer aCustomer = dbCustomer;
			Contact aContact = dbContact;;
			
			
			Map<String, Object> out = new HashMap<String, Object>() {
				{
					put("id", currentUser.getId());
					
					put("email", aCustomer.getEmail());
					
					if (aCustomer != null && aContact != null) {
						String nameOfContact =  ((aContact.getFirstName() == null ? "" : aContact.getFirstName()) + " " + (aContact.getLastName() == null ? "": aContact.getLastName())).trim();
						if(nameOfContact.length() > 0 && !nameOfContact.equalsIgnoreCase("New Customer")) {
							put("name",nameOfContact);
						}
						else {
							put("name",(( aCustomer.getFirstName() == null ? "" : aCustomer.getFirstName()) + " " + (aCustomer.getLastName() == null ? "": aCustomer.getLastName())).trim());
						}
						String phoneOfContact = aContact.getPhone() == null ? "" : aContact.getPhone();
						if(phoneOfContact.length() > 0) {
							put("phone", phoneOfContact);
						}
						else {
							put("phone", aCustomer.getPhone() == null ? "" : aCustomer.getPhone());
						}
						
						String addressOfContact = aContact.getAddress() == null ? "" : aContact.getAddress();
						if(addressOfContact.length() > 0) {
							put("address", addressOfContact);
						}
						else {
							put("address", aCustomer.getAddress() == null ? "" : aCustomer.getAddress());
						}
						String qrcodeImageOfContact = aContact.getQrcodeImg() == null ? "" : aContact.getQrcodeImg();
						if(qrcodeImageOfContact.length() > 0) {
							put("qrcodeImage", qrcodeImageOfContact);
						}
						else {
							put("qrcodeImage", aCustomer.getQrcodeImg() == null ? "" : aCustomer.getQrcodeImg());
						}
						
						String dobOfContact = aContact.getDob() != null ? DateUtil.dateToString(aContact.getDob()) : "";
						if(dobOfContact.length() > 0) {
							put("dateOfBirth", dobOfContact);
						}
						else {
							if (aCustomer.getDob() != null) {
								put("dateOfBirth", DateUtil.dateToString(aCustomer.getDob()));
							} else {
								put("dateOfBirth", "");
							}
						}
						
						
						put("language", aCustomer.getLang() == null ? "" : aCustomer.getLang());
						put("avatarImage", aCustomer.getAvartarImg() == null ? "" : aCustomer.getAvartarImg());
						put("registerDate", DateUtil.instantToString(currentUser.getCreated()));
						put("currentBalancePoints", aCustomer.getTotalPoint());
						put("vouchers", aCustomer.getTotalVoucher());
						put("offers", aCustomer.getTotalOffer());
						put("bannerHeaderImage",
								aCustomer.getBannerHeaderImg() == null ? "" : aCustomer.getBannerHeaderImg());
						

						String interestedFields = aCustomer.getInterestedFields();
						ArrayList<Map<String, Object>> interestedFieldsMap = getInfoInterestFields(interestedFields);
						
						put("interestedFields", interestedFieldsMap);

					}
				}

				
			};
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("data", out);
			responseMap.put("error", 0);
			return new ResponseEntity<>(responseMap, HttpStatus.OK);
		} catch (Exception e) {
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("Message", e.getMessage());
			responseMap.put("data", responseMap);
			responseMap.put("error", -1);
			return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/profile")
	public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody Map<String, Object> profileDTO) {

		try {
			User currentUser = userService.getCurrentUser();
			Customer customerDB = customerService.findByCustomerId(currentUser.getId());
			Contact contactDB = customerService.findByContactId(currentUser.getId());
			
			Customer aCustomer = new Customer();
			Contact aContact = new Contact();
			
			if (customerDB != null) {
				aCustomer = customerDB;
			}
			
			if (contactDB != null) {
				aContact = contactDB;
			}
			
			aCustomer.setCustomerId(currentUser.getId());
			aCustomer.setEmail(currentUser.getUsername());
			
			aContact.setCustomerId(currentUser.getId());
			aContact.setEmail(currentUser.getUsername());

			if (profileDTO.get("firstName") != null) {
				aCustomer.setFirstName(profileDTO.get("firstName").toString());
				aContact.setLastName(profileDTO.get("firstName").toString());
			}
			
			if (profileDTO.get("lastName") != null) {
				aCustomer.setLastName(profileDTO.get("lastName").toString());
			}
			
			
			if (profileDTO.get("dateOfBirth") != null) {
				aCustomer.setDob(DateUtil.stringToDate(profileDTO.get("dateOfBirth").toString()));
				aContact.setDob(DateUtil.stringToDate(profileDTO.get("dateOfBirth").toString()));
			}
			
			if (profileDTO.get("phone") != null) {
				aCustomer.setPhone(profileDTO.get("phone").toString());
				aContact.setPhone(profileDTO.get("phone").toString());
			}
			
			
			if (profileDTO.get("address") != null) {
				aCustomer.setAddress(profileDTO.get("address").toString());
				aContact.setAddress(profileDTO.get("address").toString());
			}

			if (profileDTO.get("lang") != null) {
				aCustomer.setLang(profileDTO.get("lang").toString());
			}

			if (profileDTO.get("avatarImage") != null) {
				aCustomer.setAvartarImg(profileDTO.get("avatarImage").toString());
			}

			if (profileDTO.get("bannerHeaderImage") != null) {
				aCustomer.setBannerHeaderImg(profileDTO.get("bannerHeaderImage").toString());
			}
			
			//update email, must logout user, if not the app will be crashed
// 			if (profileDTO.get("email") != null) {
// 				if(!StringUtils.isEmpty(profileDTO.get("email").toString())) {
// 					aCustomer.setEmail(profileDTO.get("email").toString());
// 				}
				
// 			}

			if (profileDTO.get("interestedFields") != null) {
				String myNum = profileDTO.get("interestedFields").toString() ;
				String joined = myNum.replace("[", "").replace("]", "").replace(" ", "");
				aCustomer.setInterestedFields(joined);
			}

			aCustomer.setQrcodeImg(
					generateQRCode(aCustomer.getCustomerId(), aCustomer.getEmail(), aCustomer.getFirstName(), aContact.getSfid()));
			aContact.setQrcodeImg(
					generateQRCode(aContact.getCustomerId(), aContact.getEmail(), aContact.getLastName(), aContact.getSfid()));

			Customer saved = customerService.createOrUpdateCustomer(aCustomer);
			Contact contactSaved = customerService.createOrUpdateContact(aContact);
			
			
			Map<String, Object> out = new HashMap<String, Object>();
			out.put("data", profileDTO);
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

	private String generateQRCode(long customerId, String email, String name, String sfid) {
		String q = "CustomerId:" + customerId + "|||" + "email:" + email + "|||" + "Name:" + name + "|||sfid:" + sfid;
		String url = null;
		try {
			url = "http://chart.apis.google.com/chart?cht=qr&chs=300x300&chl=" + URLEncoder.encode(q, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	private ArrayList<Map<String,Object>> getInfoInterestFields(String interestedFields) {
		ArrayList<Map<String, Object>> interestedFieldsMap = new ArrayList<Map<String,Object>>();
		if (!StringUtils.isEmpty(interestedFields)) {
			String[] ary = interestedFields.split(",");
			if(ary != null && ary.length > 0) {
				 List<Object[]> ifObjects = interestedFieldService.findByListId(interestedFields.replaceAll("\\s+",""));
				
				for (Object[] field : ifObjects) {
	                Map<String, Object> fieldObj = new HashMap<>();
	                fieldObj.put("value", field[0]);
	                fieldObj.put("name", field[1]);
	                interestedFieldsMap.add(fieldObj);
				}
				System.out.println(interestedFields);
			}
		}
		return interestedFieldsMap;
	}

}
