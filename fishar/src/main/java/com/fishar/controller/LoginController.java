package com.fishar.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fishar.common.FisharContext;
import com.fishar.domain.User;
import com.fishar.service.IUserService;

@Controller
public class LoginController extends BaseController{
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	IUserService userService;
	
	@RequestMapping(value="/index.html")
	public String index(HttpServletRequest request,ModelMap root){
		String sessionID = request.getSession().getId();
		Map<String,Object>  map = FisharContext.getLoginSessionMap().get(sessionID);
		User user = (User)map.get("user");
		root.put("user",user);
		return "index";
	}
	
	@RequestMapping(value="/index_v2.html")
	public String indexV2(){
		return "index_v2";
	}
	
	@ResponseBody 
	@RequestMapping(value="/doLogin.do")
	public String doLogin(User user, HttpServletRequest request,HttpServletResponse response, ModelMap root){
		user = userService.selectUser(user);
		if(user != null){
			//更新用户登录时间
			user.setLastIP(request.getRemoteHost());
			user.setLastVisit(new Date());
			userService.updateUser(user);
			String sessionID = request.getSession().getId();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("user",user);
			map.put("actionTime", System.currentTimeMillis());
			FisharContext.getLoginSessionMap().put(sessionID, map);//记录用户的登录状态
		}else{
			return resultInfo("fail","用户名或者密码错误，请重新输入");
		}
		logger.info(user.getUserName()+"登录成功");
		return resultInfo("success","登录成功");
	}
	

	@RequestMapping(value="/login.html")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response){
		String sessionID = request.getSession().getId();
		Map<String,Object> map = FisharContext.getLoginSessionMap().get(sessionID);
		if(map != null){
			
		}
		
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	
	@RequestMapping(value="/404.html")
	public String notFound(){
		return "404";
	}
	
	@RequestMapping(value="/500.html")
	public String error(){
		return "500";
	}
	
	
}
