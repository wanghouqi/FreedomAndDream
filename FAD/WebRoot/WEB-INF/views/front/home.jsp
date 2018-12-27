<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.utils.FadHelper"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	ArrayList<SpittleType> alSpittleType = (ArrayList<SpittleType>) request.getAttribute("alSpittleType");
	ArrayList<Spittle> alSpittle = (ArrayList<Spittle>) request.getAttribute("alSpittle");
	String spittleTypeId = (String) request.getAttribute("spittleTypeId");
	Boolean existType = (Boolean) request.getAttribute("existType");
%>
<script type="text/javascript">
	$(document).ready(function() {
		/**
		*	页签切换
		*/
		$(".layui-tab-title li").click(function() {
			window.location.href = contentPath + "/spittle/t/" + $(this).attr("spittleTypeId") + "/0"
		});
	});
</script>
<div class="layui-tab">
	<ul class="layui-tab-title">

		<%
			if (existType) {
				out.print("<li spittleTypeId='new'>最新</li>");
			} else {
				out.print("<li spittleTypeId='new' class='layui-this'>最新</li>");
			}
			for (SpittleType spittleType : alSpittleType) {
				String spittleTypeId_t = spittleType.getId();
				String spittleTypeName = spittleType.getName();
				String styleClass = "";
				if (spittleTypeId_t.equals(spittleTypeId)) {
					styleClass = " class='layui-this'";
				}
				out.print("<li" + styleClass + " spittleTypeId='" + spittleTypeId_t + "'>" + spittleTypeName + "</li>");
			}
		%>

	</ul>
	<div class="layui-tab-content">
		<div class="layui-tab-item layui-show">
			<%
				for (Spittle spittle : alSpittle) {
					String title = spittle.getTitle();
					String content = spittle.getContent();
					HashMap<String, String> hm = FadHelper.analyzeHTMLByContent(content);
					String firstImgSrc = hm.get("firstImgSrc");
					String text = hm.get("text");
					text = FadHelper.splitShowValue(text, 200);
					out.print("<br/>firstImgSrc=" + firstImgSrc);
					out.print("<br/>title=<a href='" + path + "/spittle/i/" + spittle.getId() + "'>" + title + "</a>");
					out.print("<br/>text=" + text);
					out.print("<br/>=========================================================================================================================");
				}
			%>
		</div>
	</div>
</div>