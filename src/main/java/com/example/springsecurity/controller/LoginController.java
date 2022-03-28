package com.example.springsecurity.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.Security.JwtTokenUtil;
import com.example.springsecurity.exceptions.InvalidInputException;
import com.example.springsecurity.models.Response;
import com.example.springsecurity.models.ResponseCode;
import com.example.springsecurity.models.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.requestVO.LoginRequestVO;
import com.example.springsecurity.requestVO.UserDetailVO;
import com.example.springsecurity.requestVO.UserRegistrationRequest;
import com.example.springsecurity.utilities.KeyWords;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "api/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> createUser(@RequestBody UserRegistrationRequest form) throws ParseException {
		Response res = new Response();

		formValidation(form);
		User userMailCheck = userRepository.findDuplicateEmail(form.getEmail());
		if (userMailCheck != null) {
			ResponseCode resCode = new ResponseCode();
			resCode.setCode(KeyWords.FAILURE_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("User Email already registered");
			res.setResponseCode(resCode);
			return new ResponseEntity<Response>(res, HttpStatus.OK);
		}
		User usermobileCheck = userRepository.findDuplicateMobileNo(form.getMobileNo());
		if (usermobileCheck != null) {
			ResponseCode resCode = new ResponseCode();
			resCode.setCode(KeyWords.FAILURE_CODE);
			resCode.setStatus(KeyWords.FAILURE);
			resCode.setMessage("User MobileNo already registered");
			res.setResponseCode(resCode);
			return new ResponseEntity<Response>(res, HttpStatus.OK);
		}
		User user = new User();
		user.setUserName(form.getUserName());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		String sDate1 = form.getDob();
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
		user.setDob(date1);
		user.setEmail(form.getEmail());
		user.setMobileNo(form.getMobileNo());
		user.setRole(form.getRole());
		user.setStatus("Active");
		user.setDesignation(form.getDesignation());
		userRepository.saveAndFlush(user);

		ResponseCode resCode = new ResponseCode();
		resCode.setCode(KeyWords.SUCCESS_CODE);
		resCode.setStatus(KeyWords.SUCCESS);
		resCode.setMessage("User registered successfully");
		res.setResponseCode(resCode);
		return new ResponseEntity<Response>(res, HttpStatus.OK);
	}

	public void formValidation(UserRegistrationRequest form) {

		if (form.getUserName().trim().length() == 0 || form.getUserName() == null) {
			throw new InvalidInputException(KeyWords.BAD_REQUEST_CODE, KeyWords.INVALID_NAME);
		}
	}

	@RequestMapping(value = "api/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestVO authenticationRequest)
			throws Exception {
		HttpHeaders responseHeaders = new HttpHeaders();

		Response res = new Response();
		ResponseCode resCode = new ResponseCode();
		resCode = authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		if (resCode.getCode() == 200) {
			Authentication auth = resCode.getAuth();
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("email", auth.getName());
			final String token = jwtTokenUtil.generateToken(auth.getName(), claims);
			resCode.setMessage("Login Successfully");
			User userDetails = userRepository.findUserByEmail(authenticationRequest.getEmail());

			UserDetailVO userDetailVo = new UserDetailVO(userDetails.getId() ,userDetails.getUserName(), userDetails.getEmail(),
					userDetails.getMobileNo(), userDetails.getDob().toString(), token,userDetails.getRole(),
					userDetails.getStatus(),userDetails.getDesignation(),userDetails.getCreatedDate());
			res.setResponseBody(userDetailVo);

		}

		res.setResponseCode(resCode);
		return ResponseEntity.ok().headers(responseHeaders).body(res);
	}

	private ResponseCode authenticate(String username, String password) {
		ResponseCode response = new ResponseCode();
		try {
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			User user = userRepository.findDuplicateEmail(username);
			if(user.getStatus().equalsIgnoreCase("Active")) {
				response.setAuth(auth);
				response.setCode(KeyWords.SUCCESS_CODE);
				response.setStatus(KeyWords.SUCCESS);
			}else {
				response.setCode(KeyWords.FAILURE_CODE);
				response.setStatus(KeyWords.FAILURE);
				response.setMessage("Deactivated user");
			}
			
		} catch (DisabledException e) {
			response.setCode(KeyWords.FAILURE_CODE);
			response.setStatus(KeyWords.FAILURE);
			response.setMessage("USER_DISABLED");

			e.printStackTrace();
		} catch (BadCredentialsException e) {
			response.setCode(KeyWords.FAILURE_CODE);
			response.setStatus(KeyWords.FAILURE);
			response.setMessage("Incorrect username or password. Please try again");

			e.printStackTrace();
		}
		return response;
	}

}
