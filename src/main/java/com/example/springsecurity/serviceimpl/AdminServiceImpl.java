package com.example.springsecurity.serviceimpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springsecurity.models.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.requestVO.UserDetailVO;
import com.example.springsecurity.services.AdminService;
import com.example.springsecurity.utilities.MailUtil;


@Component
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		
		List<User> list = userRepo.findUserByUserRole();
		return list;
	}

	@Override
	public List<UserDetailVO> prepareForm(List<User> users) {
		// TODO Auto-generated method stub
		
		List<UserDetailVO> userVoList = new ArrayList<UserDetailVO>();
		for(User obj : users) {
			UserDetailVO uvo =  new UserDetailVO();
			uvo.setId(obj.getId().toString());
			uvo.setDob(obj.getDob().toString());
			uvo.setUserName(obj.getUserName());
			uvo.setMobileNo(obj.getMobileNo());
			uvo.setEmail(obj.getEmail());
			uvo.setRole(obj.getRole());
			uvo.setStatus(obj.getStatus());
			uvo.setDesignation(obj.getDesignation());
			userVoList.add(uvo);
		}
		return userVoList;
	}

	@Override
	public boolean changeStatus(Long id) {
		// TODO Auto-generated method stub
		
		User user = userRepo.findUserByID(id);
		if(user == null) {
			return false;
		}
		String status = user.getStatus().equalsIgnoreCase("active") ? "Deactivate" : "Active";
		userRepo.setStatusValue(status, id);
		
		try {
			boolean isSent = MailUtil.sendMail(user,status);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public boolean changeStatusValue(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
