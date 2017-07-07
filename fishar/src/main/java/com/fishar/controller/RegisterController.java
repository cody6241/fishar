package com.fishar.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fishar.domain.User;
import com.fishar.service.IUserService;

@Controller
public class RegisterController extends BaseController{
	Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	IUserService userService;
	
	@RequestMapping(value="/register.html")
	public String register(){
		return "register";
	}
	
	@ResponseBody
	@RequestMapping(value="/doRegister.do")
	public String doRegister(User user){
		if(hasSameName(user)){
			logger.info("用户名："+user.getUserName()+"已被注册。");
			return this.resultInfo("fail", "用户名："+user.getUserName()+"已被注册。");
		}
		userService.add(user);
		return this.resultInfo("success", "恭喜您，注册成功");
	}

	private boolean hasSameName(User user) {
		
		User user2 = userService.selectUser(user);
		if(user2 == null){
			return false;
		}
		return true;
	}
	
	
}
