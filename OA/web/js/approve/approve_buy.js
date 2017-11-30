/**
 * 采购申请单---公共js
 */
$(function(){
	
	//回显表单数据
	Public.editData("/approve/editBuy.do");
	
	//回显审批记录
	Public.editflowLog();
	
	//添加验证
	$('#frm').bootstrapValidator({
		message: '不能为空', //为每个字段指定通用错误提示语
		fields: { /*键名username和input name值对应*/
			buyName: {
				validators: {
					notEmpty: {message: '申请采购物品不能为空'}, //非空提示
					stringLength: { max: 100, message: '申请采购物品长度不能超过100'} ///长度提示
				}	
			},
			buyNumber: {
				validators: {
					notEmpty: {message: '采购数量不能为空'}, //非空提示
					stringLength: { max: 10, message: '采购数量长度不能超过10'}, ///长度提示
					regexp: {
				        regexp: /^[0-9]+$/,
				        message: '采购数量只能填写数字'
				    }
				}
			},
			buyStandard: {
				validators: {
					stringLength: { max: 100, message: '规格及型号长度不能超过100'} ///长度提示
				}
			},
			buyPrice: {
				validators: {
					stringLength: { max: 10, message: '单价长度不能超过10'} ///长度提示
				}
			},
			buyReason: {
				validators: {
					notEmpty: {message: '采购事由不能为空'}, //非空提示
					stringLength: { max: 250, message: '采购事由长度不能超过250'} ///长度提示
				}
			},
			buyDept: {
				validators: {
					notEmpty: {message: '使用部门不能为空'}, //非空提示
					stringLength: { max: 100, message: '使用部门长度不能超过100'} ///长度提示
				}
			}
		}
	});
	
});

//保存
function save(){
	//触发全部验证
	$('#frm').data("bootstrapValidator").validate();
	//获取当前表单验证状态
	var flag = $('#frm').data("bootstrapValidator").isValid();
	
	if(flag){
		Public.saveData("frm", "/approve/saveBuy.do");
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
			Public.alert(3,"请选择审批人！");
			return false;
		}
		
			
		layer.confirm('您确定要提交吗？', {icon: 3, title:'提示'}, function(index){
				
			if(Public.saveData("frm", "/approve/saveBuy.do","submit")){
				$.ajax({
					type:'post',
					url:'/approve/submitFLow.do',
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
									window.location.href = "/views/approve/approve_home.html";
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

//提交或退回
function submitFlow(flag){
	
	if(flag=="go"){
		if($("#userId").val()==""){
			Public.alert(3,"请选择审批人！");
			return false;
		}
	}else{
		if($("#idea").val().length>100){
			Public.alert(3,"退回原因长度不能超过100！");
			return false;
		}
	}
	
	var tip = "";
	if(flag=="go"){
		tip = "提交";
	}else{
		tip = "退回申请人";
	}
		
	layer.confirm('您确定要'+tip+'吗？', {icon: 3, title:'提示'}, function(index){
		
		$.ajax({
			type:'post',
			url:'/approve/submitFLow.do',
			data:{
				id: $("#id").val(),
				wid: $("#wid").val(),
				nextUserId: $("#userId").val(),
				nextUserName: $("#userName").html(),
				idea:$("#idea").val(),
				isBack: flag
			},
			dataType:"json",
			async:false,
			success:function(data){
				if(""!=data && null!=data && typeof(data)!="undefined"){
					if("1"==data.msg){
						Public.alert(1, tip + "成功！",function(){
							window.location.href = "/views/approve/approve_home.html";
						});
					}else{
						Public.alert(2, tip + "失败！");
					}
				}
			},
			error:function(data){
				Public.alert(2,"请求出现异常！");
			}		
		});
		
		layer.close(index);
	});
}


//办结
function endFlow(){
	
	layer.confirm('您确定要办结吗？', {icon: 3, title:'提示'}, function(index){
		
		$.ajax({
			type:'post',
			url:'/approve/endFLow.do',
			data:{
				id: $("#id").val(),
				wid: $("#wid").val()
			},
			dataType:"json",
			async:false,
			success:function(data){
				if(""!=data && null!=data && typeof(data)!="undefined"){
					if("1"==data.msg){
						Public.alert(1, "办结成功！",function(){
							window.location.href = "/views/approve/approve_home.html";
						});
					}else{
						Public.alert(2, "办结失败！");
					}
				}
			},
			error:function(data){
				Public.alert(2,"请求出现异常！");
			}		
		});
		
		layer.close(index);
	});
}

