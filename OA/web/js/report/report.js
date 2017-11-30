/**
 * 工作报告单---公共js
 */
$(function(){
	
	//回显表单数据
	Public.editData("/report/edit.do");
	
	//回显接收人
	Public.editJSR();
	
	//是否查看报告给我的工作报告
	isShow();
	
	//添加验证
	$('#frm').bootstrapValidator({
		message: '不能为空', //为每个字段指定通用错误提示语
		fields: { /*键名username和input name值对应*/
			reportType: {
				validators: {
					notEmpty: {message: '报告类型不能为空'} //非空提示
				}	
			},
			reportTitle: {
				validators: {
					notEmpty: {message: '报告标题不能为空'}, //非空提示
					stringLength: { max: 100, message: '报告标题长度不能超过100'} ///长度提示
				}
			},
			reportSum: {
				validators: {
					notEmpty: {message: '工作总结不能为空'}, //非空提示
					stringLength: { max: 500, message: '工作总结长度不能超过500'} ///长度提示
				}
			},
			reportPlan: {
				validators: {
					stringLength: { max: 500, message: '工作计划长度不能超过500'} ///长度提示
				}
			},
			reportRealize: {
				validators: {
					stringLength: { max: 500, message: '工作体会长度不能超过500'} ///长度提示
				}
			}
		}
	});
	
});

//切换报告类型时，自动帮助你回显标题
function changeTitle(obj){
	var myDate = new Date();
	var year = myDate.getFullYear();    //获取完整的年份(4位,1970-????)
	var month = myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
	var day = myDate.getDate();        //获取当前日(1-31)
	
	if($(obj).val()=="日报"){
		$("#reportTitle").val(year+"年"+month+"月"+day+"日 日报");
	}else if($(obj).val()=="周报"){
		$("#reportTitle").val(year+"年"+month+"月 第1周 周报");
	}else if($(obj).val()=="月报"){
		$("#reportTitle").val(year+"年"+month+"月 月报");
	}
}

//保存
function save(){
	//触发全部验证
	$('#frm').data("bootstrapValidator").validate();
	//获取当前表单验证状态
	var flag = $('#frm').data("bootstrapValidator").isValid();
	
	if(flag){
		Public.saveData("frm", "/report/save.do");
	}
}

//保存并提交
function saveAndSubmit(){
	//触发全部验证
	$('#frm').data("bootstrapValidator").validate();
	//获取当前表单验证状态
	var flag = $('#frm').data("bootstrapValidator").isValid();
	
	if(flag){
		if($("#userId").val()==""){
			Public.alert(3,"请选择接收人！");
			return false;
		}
		
			
		layer.confirm('您确定要提交吗？', {icon: 3, title:'提示'}, function(index){
				
			if(Public.saveData("frm", "/report/save.do","submit")){
				$.ajax({
					type:'post',
					url:'/report/submitFLow.do',
					data:{
						id: $("#id").val(),
						wid: $("#wid").val(),
						nextUserId: $("#userId").val(),
						nextUserName: $("#userName").html()
					},
					dataType:"json",
					async:false,
					success:function(data){
						if(""!=data && null!=data && typeof(data)!="undefined"){
							if("1"==data.msg){
								Public.alert(1,"提交成功！",function(){
									window.location.href = "/views/report/report_home.html";
								});
							}else{
								Public.alert(2,"提交失败！");
							}
						}
					},
					error:function(data){
						Public.alert(2,"请求出现异常！");
					}		
				});
			}
			
			layer.close(index);
		});
	}
}

//是否点击查看
function isShow(){
    var wid = $("#wid").val();
	if(""!=wid){
		$.ajax({
			type:'post',
			url:'/report/endFLow.do',
			data:{
				id: $("#id").val(),
				wid: $("#wid").val(),
			},
			dataType:"json",
			async:false,
			success:function(data){
				if(""!=data && null!=data && typeof(data)!="undefined"){
					if("1"==data.msg){
						//刷新父页面
					}
				}
			},
			error:function(data){
			}		
		});
	}
}
