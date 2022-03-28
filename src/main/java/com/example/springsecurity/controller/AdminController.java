package com.example.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.models.Response;
import com.example.springsecurity.models.ResponseCode;
import com.example.springsecurity.models.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.requestVO.UserDetailVO;
import com.example.springsecurity.services.UserService;
import com.example.springsecurity.utilities.KeyWords;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	private UserService adminService;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "api/admin/getUsers", method = RequestMethod.GET)
	public ResponseEntity<Response> getUserDetails()  {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();

		UserDetails usercheck = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = usercheck.getUsername();

		User user = userRepository.findUserByEmail(username);

		if(user.getRole().equalsIgnoreCase("admin")) {
			List<User> userDetails = adminService.getUsers();

			if (userDetails == null || userDetails.size() == 0) {
				resCode.setCode(KeyWords.FAILURE_CODE);
				resCode.setMessage("No users found");
				resCode.setStatus(KeyWords.FAILURE);
				res.setResponseCode(resCode);
				return new ResponseEntity<Response>(res, HttpStatus.OK);

			}

			List<UserDetailVO> userVo = adminService.prepareForm(userDetails);
			resCode.setCode(KeyWords.SUCCESS_CODE);
			resCode.setMessage("Users details");
			resCode.setStatus(KeyWords.SUCCESS);
			res.setResponseCode(resCode);
			res.setResponseBody(userVo);
		}else {
			resCode.setCode(KeyWords.FAILURE_CODE);
			resCode.setMessage("User not an admin");
			resCode.setStatus(KeyWords.FAILURE);
			res.setResponseCode(resCode);
			res.setResponseBody(null);
		}


		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "api/admin/changeStatus/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> activateAndDeactivateuser(@PathVariable("id") String id) throws Exception {
		Response res = new Response();
		ResponseCode resCode = new ResponseCode();

		if (id == null) {
			resCode.setCode(KeyWords.FAILURE_CODE);
			resCode.setMessage("No user found");
			resCode.setStatus(KeyWords.FAILURE);
			res.setResponseCode(resCode);
			return new ResponseEntity<Response>(res, HttpStatus.OK);

		}

		boolean isDone = adminService.changeStatus(Long.valueOf(id));
		if (isDone) {
			resCode.setCode(KeyWords.SUCCESS_CODE);
			resCode.setMessage("Users status changed successfully...");
			resCode.setStatus(KeyWords.SUCCESS);
			res.setResponseCode(resCode);
		} else {
			resCode.setCode(KeyWords.FAILURE_CODE);
			resCode.setMessage("No user found");
			resCode.setStatus(KeyWords.FAILURE);
			res.setResponseCode(resCode);
		}

		return new ResponseEntity<Response>(res, HttpStatus.OK);

	}
}
