package com.example.springsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.models.Address;
import com.example.springsecurity.models.Response;
import com.example.springsecurity.models.ResponseCode;
import com.example.springsecurity.models.User;
import com.example.springsecurity.repository.UserAddressRepository;
import com.example.springsecurity.requestVO.AddressVo;
import com.example.springsecurity.requestVO.UserDetailVO;
import com.example.springsecurity.services.UserService;
import com.example.springsecurity.utilities.KeyWords;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAddressRepository userAddressRepository;
	
	@RequestMapping(value = "api/user/getUserDetails", method = RequestMethod.GET)
	public ResponseEntity<Response> getUserDetails(){
		Response res = new Response();
		UserDetails usercheck = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = usercheck.getUsername();
		
		User userDetails = userService.findUserByEmail(username);
		Address address = userDetails.getAddress();
		if(address == null) {
			address = new Address();
			address.setAddress1("west street");
			address.setAddress2("Alagapuri");
			address.setCity("Virudhunagar");
			address.setState("Tamil Nadu");
			address.setCountry("India");
			address.setPincode("626001");
			address = userAddressRepository.saveAndFlush(address);
			userDetails.setAddress(address);
			userService.save(userDetails);
		}

		AddressVo addressVo = new AddressVo(address.getId(), address.getAddress1(), address.getAddress2(),
				address.getPincode(), address.getState(), address.getCity(), address.getCountry());
		UserDetailVO userDetailVo = new UserDetailVO(userDetails.getId() ,userDetails.getUserName(), userDetails.getEmail(),
				userDetails.getMobileNo(), userDetails.getDob().toString(),userDetails.getRole(),
				userDetails.getStatus(),userDetails.getDesignation(),userDetails.getCreatedDate());
		userDetailVo.setAddress(addressVo);
		ResponseCode resCode = new ResponseCode();
		resCode.setCode(KeyWords.SUCCESS_CODE);
		resCode.setStatus(KeyWords.SUCCESS);
		resCode.setMessage("User details");
		res.setResponseCode(resCode);
		res.setResponseBody(userDetailVo);
		
		return new ResponseEntity<Response>(res,HttpStatus.OK);
	}

}
