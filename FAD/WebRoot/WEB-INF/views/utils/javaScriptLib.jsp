<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="java.lang.reflect.*"%>
<%@page import="hq.fad.utils.Definition"%>
<%
	String path = request.getContextPath();
	JSONObject userJSONObj = new JSONObject();
	String processingPleaseWait_alert = "";
%>


<script type="text/javascript" src="<%=path%>/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/jquery.base64.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/jquery.md5.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/event.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/fadForm.js"></script>

<!-- FilterSelect -->
<link type="text/css" rel="stylesheet" href="<%=path%>/resources/lib/filterSelect/filterSelect.css">
<script type="text/javascript" src="<%=path%>/resources/lib/filterSelect/filterSelect.js"></script>

<!-- layui -->
<link type="text/css" rel="stylesheet" href="<%=path%>/resources/lib/layui/css/layui.css">
<script type="text/javascript" src="<%=path%>/resources/lib/layui/layui.js"></script>

<!-- 临时的Table样式,以后可以删除 -->
<link type="text/css" rel="stylesheet" href="<%=path%>/resources/back/tmp/table.css">
<script type="text/javascript" src="<%=path%>/resources/back/tmp/table.js"></script>



<script>
	var contentPath = '<%=path%>';
	var backPath = contentPath + "/back";
</script>

<%
	// JS 中使用的宏定义
	JSONObject definitionJSONObj = new JSONObject();
	Field[] field = Definition.class.getFields();
	for (int i = 0; i < field.length; i++) {
		int mod = field[i].getModifiers();
		if (Modifier.isPublic(mod) && Modifier.isStatic(mod) && Modifier.isFinal(mod)) {
			definitionJSONObj.put(field[i].getName(), field[i].get(field[i].getGenericType()));
		}
	}
%>
<script>
	var Definition = <%=definitionJSONObj.toString()%>;
</script>