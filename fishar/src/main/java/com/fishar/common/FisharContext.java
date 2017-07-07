package com.fishar.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FisharContext {
	private static ConcurrentHashMap<String,Map<String,Object>> loginSessionMap;//用于记录登录用户的session，和最后一次操作的时间
	
	public static Map<String,Map<String,Object>> getLoginSessionMap(){
		if(loginSessionMap == null){
			loginSessionMap = new ConcurrentHashMap<String,Map<String,Object>>();
		}
		return loginSessionMap;
	}
}
