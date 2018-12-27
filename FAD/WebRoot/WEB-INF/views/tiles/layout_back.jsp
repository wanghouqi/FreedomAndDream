
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%
	String path = request.getContextPath();
%>
<title>自由与梦想</title>
<style>
a, address, b, big, blockquote, body, center, cite, code, dd, del, div,
	dl, dt, em, fieldset, font, form, h1, h2, h3, h4, h5, h6, html, i,
	iframe, img, ins, label, legend, li, ol, p, pre, small, span, strong, u,
	ul, var {
	margin: 0;
	padding: 0
}

body {
	overflow-y: hidden;
}

td {
	border: 0px;
}
</style>

</head>
<body style="background-color:white;height:100%;width:100%;text-align:center;">
	<table width="100%" align="center" cellspacing="0" cellpadding="0">
		<tr>
			<td id="javaScriptLibTD" style="border:0px;height:0px;"><t:insertAttribute
					name="javaScriptLib" ignore="true" /></td>
		</tr>
		<tr>
			<td id="headerTD" style="border:0px;height:0px;"><t:insertAttribute name="header"
					ignore="true" /></td>
		</tr>
		<tr>
			<td id="bodyTD" style="border:0px;" align="center">
				<form id="sysForm" name="sysForm" action="" method="post">
					<t:insertAttribute name="body" />
				</form>
			</td>
		</tr>
		<tr>
			<td id="footerTD" style="border:0px" valign="bottom"><t:insertAttribute name="footer"
					ignore="true" /></td>
		</tr>
		<tr>
			<td id="alertMsgTD" style="border:0px" valign="bottom"><t:insertAttribute name="alertMsg"
					ignore="true" /></td>
		</tr>
	</table>
</body>
</html>