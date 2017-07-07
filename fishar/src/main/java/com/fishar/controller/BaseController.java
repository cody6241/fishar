package com.fishar.controller;

public abstract class BaseController {
	
	public String resultInfo(String result,String msg){
		return "{\"result\":\""+result+"\",\"msg\":\""+msg+"\"}";
	}
}
