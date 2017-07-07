package com.fishar.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fishar.common.FisharContext;
import com.fishar.domain.User;

public class DispatcherFilter implements Filter {
	private Logger logger = Logger.getLogger(getClass());

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;

		String uri = req.getRequestURI();
		//静态资源请求不做过滤
		if(isJingTai(uri)){
			chain.doFilter(request, response);
			return;
		}
		
		if(uri.contains("login.html")){
			String sessionID = req.getSession().getId();
			if(FisharContext.getLoginSessionMap().get(sessionID) != null){
				resp.sendRedirect("index.html");
				return;
			}
			chain.doFilter(request, response);
			return;
		}
		
		if(req.getSession().getAttribute("user") != null){
			logger.info("用户已登录");
			User user = (User)req.getSession().getAttribute("user");
			logger.info(user.getUserName());
			chain.doFilter(request, response);
			return;
		}
		
		String sessionID = req.getSession().getId();
		if(FisharContext.getLoginSessionMap().containsKey(sessionID)){
			//更新用户最后一次操作的时间
			Map<String,Object> map = FisharContext.getLoginSessionMap().get(sessionID);
			map.put("actionTime", System.currentTimeMillis());
			FisharContext.getLoginSessionMap().put(sessionID, map);
			chain.doFilter(request, response);
		}else{
			if(!uri.endsWith("doLogin.do") && !uri.endsWith("register.html") && !uri.endsWith("doRegister.do")){
				resp.sendRedirect("login.html");
			}else{
				chain.doFilter(request, response);
			}
		}
		
	}

	/**
	 * @Author Arthur 2017年5月5日
	 * @param uri
	 * @return
	 * @Description: 判断是否是静态资源请求
	 */
	private boolean isJingTai(String uri) {
		if(uri.contains("/css/")){
			return true;
		}else if(uri.contains("/js/")){
			return true;
		}else if(uri.contains("/fonts/")){
			return true;
		}else if(uri.contains("/img/")){
			return true;
		}else if(uri.contains("/plugins/")){
			return true;
		}
		return false;
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
