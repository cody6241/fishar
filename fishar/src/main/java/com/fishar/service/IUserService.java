package com.fishar.service;

import com.fishar.domain.User;

public interface IUserService {

	User selectUser(User user);

	void updateUser(User user);

	void add(User user);

}
