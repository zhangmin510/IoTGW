<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="org.ciotc.gateway.*" %>
<%
	if(request.getParameter("start") != null){
	   GWManager.getInstance().start();
	}
	if(request.getParameter("stop") != null){
	   GWManager.getInstance().stop();
	}
	response.sendRedirect("index.jsp");

%>