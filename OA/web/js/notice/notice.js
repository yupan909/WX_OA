/**
 * 通知公告---公共js
 */
$(function(){
	
	//回显表单数据
	Public.editData("/notice/edit.do");
	
	//添加验证
	$('#frm').bootstrapValidator({
		message: '不能为空', //为每个字段指定通用错误提示语
		fields: { /*键名username和input name值对应*/
			noticeTitle: {
				validators: {
					notEmpty: {message: '公告标题不能为空'}, //非空提示
					stringLength: { max: 50, message: '公告标题长度不能超过50'} ///长度提示
				}	
			},
			noticeContent: {
				validators: {
					notEmpty: {message: '公告内容不能为空'}, //非空提示
					stringLength: { max: 1000, message: '公告内容长度不能超过1000'} ///长度提示
				}
			}
		}
	});
	
});

//保存
function save(){
	var bool = false;
	//触发全部验证
	$('#frm').data("bootstrapValidator").validate();
	//获取当前表单验证状态
	var flag = $('#frm').data("bootstrapValidator").isValid();
	
	if(flag){
		bool = Public.saveData("frm", "/notice/save.do", "submit");
	}
	return bool;
}
