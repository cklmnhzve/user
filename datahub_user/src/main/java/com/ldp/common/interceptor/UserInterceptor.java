package com.ldp.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserInterceptor implements HandlerInterceptor
{
	private List<String> uncheckUrls;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception
	{
		System.out.println("登录处理： preHandle");
		String requestUrl = request.getRequestURI();
		System.out.println("request.getRequestURL() : " + request.getRequestURL());
		System.out.println("requestUrl: " + requestUrl);
		if (uncheckUrls.contains(requestUrl)) 
		{
			System.out.println("uncheckUrls.contains(requestUrl) : " + uncheckUrls.contains(requestUrl));
			return true;
		}
		else 
		{
			/*
			 
			String username = (String) request.getSession().getAttribute("username");
			if (username == null) 
			{
				System.out.println("username: " + username);
				request.getContextPath();
				request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
				return false;
				
			}
			else
			{
				return true;
			}
			*/
			return true ;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView arg3)
			throws Exception
	{
		System.out.println("登录处理： postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception
	{
		System.out.println("登录处理： afterCompletion,  可以在此处记录相关日志");
	}

	public void setUncheckUrls(List<String> uncheckUrls)
	{
		this.uncheckUrls = uncheckUrls;
	}
}
