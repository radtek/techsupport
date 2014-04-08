<%@page import="java.io.File"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.BufferedInputStream"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String filestorepath = (String) request
			.getAttribute("filefullname");
	if (filestorepath == null)
		return;
	boolean need_remove = request.getAttribute("need_remove") == null ? false
			: ((Boolean) request.getAttribute("need_remove"))
					.booleanValue();
	String default_filename = (String) request.getAttribute("filename");
	if (default_filename == null)
		default_filename = new Date().getTime() + "";

    String filename = null;
    if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0)
        filename = URLEncoder.encode(default_filename,"utf8");
    else
        filename = new String(default_filename.getBytes(),"ISO8895-1");
	response.reset();
	response.addHeader("Content-Disposition", "attachment;filename="
			+ filename + "");
	response.setContentType("application/x-download");
	
	//response.sendRedirect(filestorepath);
	File tempfile = new File(filestorepath);
	BufferedInputStream in = new BufferedInputStream(
			new FileInputStream(tempfile));
	ServletOutputStream os = response.getOutputStream();
	byte[] buff = new byte[8192];
	int bufflen = 0;
	while ((bufflen = in.read(buff)) != -1)
		os.write(buff, 0, bufflen);
	in.close();
	os.flush();
	os.close();
%>