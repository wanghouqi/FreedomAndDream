<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	ArrayList<FBoolean> alFBoolean = (ArrayList<FBoolean>) request.getAttribute("alFBoolean");
	UserC userC = (UserC) request.getAttribute("userC");
	String userOptionBase = "";
	if (userC != null && userC.getUser() != null) {
		userOptionBase = "<option value='" + userC.getUser().getId() + "'>" + userC.getUser().getLoginName()
				+ "</option>";
	}
%>

<script type="text/javascript">
	var $userOptionBase = $("<%=userOptionBase%>"); // 记录当前管理的用户
	$(document).ready(function() {
		/**
		*	保存提交
		*/
		$("#saveBtn").click(function() {
			fadFormSubimt(backPath + "/userCSave");
		});

		/**
		* 用户的FilterSelect的相应事件
		*/
		$("#userId").closest("td").on("input.fs propertychange.fs", "input", function(event) {
			//绑定的方法进行防抖处理, 防止输入过程中频繁的执行处理方法触发AJAX
			debounce(loadUser, this, event, 500);
		});
	});
</script>
<input type="button" value="保存" id="saveBtn" />
<table class="formTable">
	<tr>
		<th>工号:</th>
		<td><input type="hidden" name="id" value="<%=userC != null ? userC.getId() : ""%>"> <input
			type="text" name="no" value="<%=userC != null ? userC.getNo() : ""%>"></td>
	</tr>
	<tr>
		<th>登录名:</th>
		<td><input type="text" name="loginName"
			value="<%=userC != null ? userC.getLoginName() : ""%>"></td>
	</tr>
	<tr>
		<th>密码:</th>
		<td><input type="password" name="password" value=""></td>
	</tr>
	<tr>
		<th>用户名:</th>
		<td><input type="text" name="name" value="<%=userC != null ? userC.getName() : ""%>"></td>
	</tr>
	<tr>
		<th>关联用户名:</th>
		<td><%=(userC != null && userC.getUser() != null) ? userC.getUser().getLoginName() : ""%></td>
	</tr>
	<tr>
		<th>是否超级管理员:</th>
		<td><select name="supperFlag">
				<%
					for (FBoolean fBoolean : alFBoolean) {
						String selected = "";
						if (fBoolean.getId().equals(userC != null ? userC.getSupperFlag().getId() : "")) {
							selected = "selected";
						}
						out.print("<option " + selected + " value='" + fBoolean.getId() + "'>" + fBoolean.getName() + "</option>");
					}
				%>
		</select></td>
	</tr>
	<tr>
		<th>是否活动:</th>
		<td><select name="activeFlag">
				<%
					for (FBoolean fBoolean : alFBoolean) {
						String selected = "";
						if (fBoolean.getId().equals(userC != null ? userC.getActiveFlag().getId() : "")) {
							selected = "selected";
						}
						out.print("<option " + selected + " value='" + fBoolean.getId() + "'>" + fBoolean.getName() + "</option>");
					}
				%>
		</select></td>
	</tr>
</table>