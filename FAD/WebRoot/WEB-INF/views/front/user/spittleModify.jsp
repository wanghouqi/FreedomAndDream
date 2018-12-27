<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="hq.fad.utils.Definition"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="hq.fad.utils.FadHelper"%>
<%@page import="hq.fad.dao.pojo.*"%>
<%
	String path = request.getContextPath();
	ArrayList<SpittleType> alSpittleType = (ArrayList<SpittleType>) request.getAttribute("alSpittleType");
	Spittle spittle = (Spittle) request.getAttribute("spittle");
	String title = "";
	String content = "";
	if (spittle != null) {
		title = spittle.getTitle();
		content = spittle.getContent();
	}
%>
<script type="text/javascript">
	$(document).ready(function() {
		//注意：layedit.set 一定要放在 build 前面，否则配置全局接口将无效。

		layui.use('layedit', function() {
			var layedit = layui.layedit;
			layedit.set({
				uploadImage : {
					url : contentPath + '/utils/uploadImg', //接口url
					type : 'post' //默认post
				}
			});
			layedit.build('content'); //建立编辑器
		});
		/**
		*	保存提交
		*/
		$("#saveBtn").click(function() {
			fadFormSubimt(contentPath + "/u/spittleSave");
		});
	});
</script>
<input type="button" value="保存" id="saveBtn" />
<table class="layui-table">
	<thead>
		<tr>
			<th><input type="hidden" name="id" value="<%=spittle != null ? spittle.getId() : ""%>"><input
				type="text" name="title" value="<%=title%>" style="width:100%;height:30px;"></th>
			<th><select name="spittleTypeId" style="height:30px;">
					<%
						for (SpittleType spittleType : alSpittleType) {
							String selected = "";
							if (spittleType.getId().equals(spittle != null ? spittle.getSpittleType().getId() : "")) {
								selected = "selected";
							}
							out.print("<option " + selected + " value='" + spittleType.getId() + "'>" + spittleType.getName() + "</option>");
						}
					%>
			</select></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="2"><textarea id="content" name="content" style="display: none;"><%=content%></textarea></td>
		</tr>
	</tbody>
</table>