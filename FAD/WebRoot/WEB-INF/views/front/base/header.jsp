<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	User user = (User) session.getAttribute(Definition.SESSION_ATTR_KEY_USER);
%>


<div style="width:100%;height:40px;border:1px solid red;">
	<div style="width:200px;">
		您好:
		<%=user != null ? user.getUserProfile().getNickName() : ""%></div>
	<a href="<%=path%>/">主页</a>
	<a href="<%=path%>/u/register">注册</a>
	<a href="<%=path%>/u/login">登录</a>
	<a href="<%=path%>/logout">登出</a>
	<a href="<%=path%>/u/base">用户基础信息</a>
	<a href="<%=path%>/u/profile">用户个人信息</a>
	<a href="<%=path%>/u/spittleList">用户发帖信息</a>
	<a href="<%=path%>/u/spittleModify/n">用户发帖维护</a>

</div>
