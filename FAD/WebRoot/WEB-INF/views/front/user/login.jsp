<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
	$(document).ready(function() {
		/**
		*	保存提交
		*/
		$("#loginBtn").click(function() {
			fadFormSubimt();
		});
	});
</script>
<input type="button" value="登录" id="loginBtn" />
<table class="formTable">
	<tr>
		<th>帐号:</th>
		<td><input type="text" name="c" value=""  placeholder="用户名,邮箱,手机号"></td>
	</tr>
	<tr>
		<th>密码:</th>
		<td><input type="password" name="a" value=""></td>
	</tr>
</table>