package com.aisino2.techsupport.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.aisino2.techsupport.common.Constants;

public class ConstantsInitFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filter) throws IOException, ServletException {
		filter.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		ServletContext sc = config.getServletContext();
		Constants.APPLICATION_SERVERCONTEXT_REALPATH = sc.getRealPath("/");
	}
}
