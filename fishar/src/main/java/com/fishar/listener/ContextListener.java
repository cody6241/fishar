package com.fishar.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fishar.common.FisharContext;

public class ContextListener implements ServletContextListener  {

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		startLoginCheckSchedule();
	}

	/**
	 * @Author Arthur 2017年5月4日
	 * @Description: 定时检查用户登录状态的定时任务
	 */
	private void startLoginCheckSchedule() {
	    Runnable runnable = new Runnable() {
	        public void run() {
	        	Map<String,Map<String,Object>> map= FisharContext.getLoginSessionMap();
	        	if(map != null){
	        		Iterator<Entry<String,Map<String,Object>>> entryIterator =
	        				map.entrySet().iterator();
	        		while(entryIterator.hasNext()){
	        			Entry<String,Map<String,Object>> entry = entryIterator.next();
	        			Map<String,Object> sMap = entry.getValue();
	        			long lastActionTime = (Long)sMap.get("actionTime");
	        			long now = System.currentTimeMillis();
	        			if(now - lastActionTime > 1800000){
	        				entryIterator.remove();
	        			}
	        		}
	        	}
	        }
	      };
	      ScheduledExecutorService service = Executors
	                      .newSingleThreadScheduledExecutor();
	      service.scheduleAtFixedRate(runnable, 10, 60, TimeUnit.SECONDS);
	}
	
	   public static void main(String[] args) { 
		   Map map = new HashMap();
		   map.put(1,"one");  
		   map.put(2,"two");  
		   map.put(3,"three");  
		   map.put(4,"four");  
		   map.put(5,"five");  
		   map.put(6,"six");  
		   map.put(7,"seven");  
		   map.put(8,"eight");  
		   map.put(5,"five");  
		   map.put(9,"nine");  
		   map.put(10,"ten");  
		   Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();  
		   while(it.hasNext()){  
			   Map.Entry<Integer, String> entry=it.next();  
			   int key=entry.getKey();  
			   if(key%2==1){  
				   System.out.println("delete this: "+key+" = "+key);  
				   //map.put(key, "奇数");   //ConcurrentModificationException  
				   //map.remove(key);      //ConcurrentModificationException  
				   it.remove();        //OK   
			   }  
		   }  
		   System.out.println(map.size());
		   System.out.println(map);
	   }
	


}
