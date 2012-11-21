<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%
	String filename = "技术支持单流转系统操作手册V1.2.doc";
	String docpath = "/help/"+filename;
	
	response.setContentType("application/msword;charset=UTF-8'");
	  response.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(filename));
	byte[] buf = new byte[8192];
	FileInputStream in = new FileInputStream(application.getRealPath("/")+docpath);
	ServletOutputStream fout = response.getOutputStream();
	int len = 0;
	while((len = in.read(buf,0,buf.length)) >= 0)
		fout.write(buf,0,len);
	in.close();
	fout.close();
	response.flushBuffer();  
	out.clear();  
	out = pageContext.pushBody();  
%>