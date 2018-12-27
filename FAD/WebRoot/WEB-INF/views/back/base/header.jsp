<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String backPath = path+"/back";
%>


<div style="width:100%;height:40px;border:1px solid red;">

<a href="<%=backPath%>/userCList">超级管理员管理</a>
<a href="<%=backPath%>/userList">用户管理</a>
<a href="<%=backPath%>/spittle/spittleTypeList">文章类型管理</a>
<a href="<%=backPath%>/spittle/spittleList">文章管理</a>
<a href="<%=backPath%>/spittleFollowList">跟帖管理</a>

</div>
