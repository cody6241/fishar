package com.fishar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fishar.dao.IUserDAO;
import com.fishar.domain.User;
import com.fishar.service.IUserService;

@Service(value="userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDAO userDAO;
	
	public User selectUser(User user) {
		return userDAO.selectUser(user);
	}

	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	public void add(User user) {
		userDAO.add(user);
	}

}
