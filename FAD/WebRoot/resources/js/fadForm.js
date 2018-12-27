/**
 * 系统标准<form>标签的公用Function
 */

/**
 * 提交当前页面数据到action
 */
function fadFormSubimt(action) {
	if(action){
		$("#sysForm").attr("action", action);
	}
	$("#sysForm").submit();
}