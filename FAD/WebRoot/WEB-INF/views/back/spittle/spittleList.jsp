<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.utils.FadHelper"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	ArrayList<Spittle> alSpittle = (ArrayList<Spittle>) request.getAttribute("alSpittle");
%>
<script type="text/javascript">
	modifyURL = backPath + "/spittle/spittleModify";
	$(document).ready(function() {});
</script>
<table class="layui-table gridtable">
	<thead>
		<tr>
			<th style="width:30px">&nbsp;</th>
			<th>标题</th>
			<th>审核状态</th>
		</tr>
	</thead>
	<tbody>
		<%
			for (Spittle spittle : alSpittle) {
				String title_title = spittle.getTitle();
				String title_show = FadHelper.splitShowValue(title_title, 30);
		%>

		<tr>
			<td><input type='checkbox' name='primaryId' value='<%=spittle.getId()%>' /></td>
			<td title="<%=title_title%>"><%=title_show%></td>
			<td><%=spittle.getAuthorizedFlag().getName()%></td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>