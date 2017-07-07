package com.fishar.dao;

import org.apache.ibatis.annotations.Param;

import com.fishar.domain.User;

public interface IUserDAO {

	User selectUser(@Param("user")User user);

	void updateUser(@Param("user")User user);

	void add(@Param("user")User user);

}
