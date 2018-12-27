<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	User user = (User) request.getSession().getAttribute(Definition.SESSION_ATTR_KEY_USER);
	UserProfile userProfile = user.getUserProfile();
%>
<script type="text/javascript">

</script>

<table class="formTable">
	<tr>
		<th>用户姓名:</th>
		<td><%=userProfile.getName()%></td>
	</tr>
	<tr>
		<th>昵称:</th>
		<td><%=userProfile.getNickName()%></td>
	</tr>
	<tr>
		<th>头像URI:</th>
		<td><%=userProfile.getHeadImg()%></td>
	</tr>
	<tr>
		<th>个人签名:</th>
		<td><%=userProfile.getRemark()%></td>
	</tr>
</table>
