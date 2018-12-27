<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	User user = (User) request.getSession().getAttribute(Definition.SESSION_ATTR_KEY_USER);
%>
<script type="text/javascript">

</script>

<table class="formTable">
	<tr>
		<th>帐号:</th>
		<td><%=user.getLoginName()%></td>
	</tr>
	<tr>
		<th>邮件地址:</th>
		<td><%=user.getEmail()%></td>
	</tr>
	<tr>
		<th>手机号:</th>
		<td><%=user.getMobileNo()%></td>
	</tr>
</table>