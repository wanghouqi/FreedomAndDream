<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	ArrayList<UserC> alUserC = (ArrayList<UserC>) request.getAttribute("alUserC");
%>

<script type="text/javascript">
	modifyURL = backPath + "/userCModify";
	$(document).ready(function() {
		$("#newBtn").click(function() {
			window.location.href = modifyURL + "/n";
		});
		$("#deleteBtn").click(function() {
			fadFormSubimt(backPath + "/userCDelete");
		});
	});
</script>
<input type="button" value="新增" id="newBtn" />
<input type="button" value="删除" id="deleteBtn" />
<table class="gridtable">
	<thead>
		<tr>
			<th>&nbsp;</th>
			<th>工号</th>
			<th>登录名</th>
			<th>用户名</th>
			<th>关联用户名</th>
			<th>是否超级管理员</th>
			<th>是否活动</th>
		</tr>
	</thead>
	<tbody>
		<%
			for (UserC userC : alUserC) {
				out.print("<tr class='a1'>");
				out.print("<td><input type='checkbox' name='primaryId' value='" + userC.getId() + "' /></td>");
				out.print("<td>" + userC.getNo() + "</td>");
				out.print("<td>" + userC.getLoginName() + "</td>");
				out.print("<td>" + userC.getName() + "</td>");
				out.print("<td>" + (userC.getUser() != null ? userC.getUser().getLoginName() : "&nbsp;") + "</td>");
				out.print("<td>" + userC.getSupperFlag().getName() + "</td>");
				out.print("<td>" + userC.getActiveFlag().getName() + "</td>");
				out.print("</tr>");
			}
		%>
	</tbody>
</table>