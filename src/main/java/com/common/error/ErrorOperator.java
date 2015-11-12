package com.common.error;

import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorOperator
{
	@RequestMapping("/error404")
	public String error404(HttpServletRequest request)
	{
		return "error/error404";
	}
}
