<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.utils.FadHelper"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	Spittle spittle = (Spittle) request.getAttribute("spittle");
	String spittleId = spittle.getId();
	String title = spittle.getTitle();
	String content = spittle.getContent();
	long admireCount = spittle.getAdmireCount();
	long viewCount = spittle.getViewCount();
	ArrayList<SpittleFollow> alSpittleFollow = spittle.getAlSpittleFollow();
%>
<script type="text/javascript">
	$(document).ready(function() {
		/**
		*	保存提交
		*/
		$("#saveBtn").click(function() {
			fadFormSubimt(contentPath + "/spittle/spittleFollowNew");
		});

		/**
		*	文章点赞
		*/
		$("#admire_spittle").click(function() {
			var $link = $(this);
			var spittleId = $link.attr("spittleId");
			$.ajax({
				type : "POST",
				url : contentPath + '/spittle/ajax/admireSpittle',
				data : {
					spittleId : spittleId
				},
				async : false, //设为false就是同步请求
				cache : false,
				dataType : 'json',
				success : function(data) {
					$link.next().text(data.admireCount);
				}
			});
		});

		/**
		*	文章跟帖点赞
		*/
		$("[id^='admire_spittleFollow_']").click(function() {
			var $link = $(this);
			var spittleFollowId = $link.attr("spittleFollowId");
			$.ajax({
				type : "POST",
				url : contentPath + '/spittle/ajax/admireSpittleFollow',
				data : {
					spittleFollowId : spittleFollowId
				},
				async : false, //设为false就是同步请求
				cache : false,
				dataType : 'json',
				success : function(data) {
					$link.next().text(data.admireCount);
				}
			});
		});
	});
</script>
<input type="hidden" name="spittleId" value="<%=spittleId%>" />
<table class="layui-table">
	<thead>
		<tr>
			<th colspan="2"><%=title%></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><%=content%></td>
			<td><a id="admire_spittle" spittleId="<%=spittleId%>" href="javascript:void(0)"
				target="_self">赞</a><span><%=admireCount%></span>&nbsp;&nbsp;<span>浏览次数: <%=viewCount%></span></td>
		</tr>
		<%
			for (SpittleFollow spittleFollow : alSpittleFollow) {
				out.print("<tr>");
				out.print("<td>" + spittleFollow.getContent() + "</td>");
				out.print("<td><a id='admire_spittleFollow_" + spittleFollow.getId() + "' spittleFollowId='" + spittleFollow.getId()
						+ "' href='javascript:void(0)' arget='_self'>赞<a><span>" + spittleFollow.getAdmireCount() + "</span></td>");
				out.print("</tr>");
			}
		%>
		<tr>
			<td colspan="2"><textarea rows="10" cols="50" name="content"></textarea> <input
				type="button" value="保存" id="saveBtn" /></td>
		</tr>
	</tbody>
</table>