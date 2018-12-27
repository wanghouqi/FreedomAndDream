/**
 * 给Table的数据TR加一个双击跳转事件
 */

var modifyURL;
$(document).ready(function() {
	$(".gridtable").find("tr").on("dblclick", function() {
		var $tr = $(this);
		if ($tr.find("td").length > 0) {
			window.location.href = modifyURL + "/" + $tr.find("[name='primaryId']").val();
		}
	});
});