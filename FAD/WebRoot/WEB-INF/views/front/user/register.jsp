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
		$("#regBtn").click(function() {
			fadFormSubimt();
		});
	});
</script>
<input type="button" value="注册" id="regBtn" />
<table class="formTable">
	<tr>
		<th>帐号:</th>
		<td><input type="text" name="c" value=""></td>
	</tr>
	<tr>
		<th>邮件地址:</th>
		<td><input type="text" name="d" value=""></td>
	</tr>
	<tr>
		<th>手机号:</th>
		<td><input type="text" name="e" value=""></td>
	</tr>
	<tr>
		<th>密码:</th>
		<td><input type="password" name="a" value=""></td>
	</tr>
	<tr>
		<th>确认密码:</th>
		<td><input type="password" name="b" value=""></td>
	</tr>

</table>