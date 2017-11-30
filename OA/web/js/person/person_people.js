/**
 * 个人信息---公共js
 */
$(function(){
	
	//回显表单数据
	var userId = Public.urlParam("userId");
	
	$.ajax({
		type:"post",
		url:"/public/getUserInfo.do",
		data:{userId:userId},
		dataType:"json",
		async:false,
		success:function(data){
			if(""==data || null==data || typeof(data)=="undefined"){
				Public.alert(2, "回显数据失败！");
				return false;
			}
			for(var keys in data){
				var value = data[keys];
                var $key = $("#"+keys);
                if($key.is("select")){
					$key.find("option[value='"+value+"']").prop("selected",true);						
				}else if($("[name='"+keys+"']").attr("type")=="radio" || $("[name='"+keys+"']").attr("type")=="checkbox"){
					$("input[name="+keys+"][value="+value+"]").prop("checked",true);
				}else{
					if($key.is("span") || $key.is("font")){
						if("gender"==keys){
							if("1"==value){
								value="男";
							}else if("2"==value){
								value="女";
							}else{
								value="";
							}
						}
						$key.html(value);
					}else{
						$key.val(value);
					}
				}
                
                if("avatar"==keys){
                	$("#tx").prop("src",value);
                }
			}
		},
		error:function(){
			Public.alert(2, "系统异常！");
		}
	});
	
	
	//添加验证
	$('#frm').bootstrapValidator({
		message: '不能为空', //为每个字段指定通用错误提示语
		fields: { /*键名username和input name值对应*/
			gender: {
				validators: {
					stringLength: { max: 1, message: '性别长度不能超过1'} ///长度提示
				}	
			},
			position: {
				validators: {
					stringLength: { max: 100, message: '职位长度不能超过100'} ///长度提示
				}	
			},
			weixinid: {
				validators: {
					stringLength: { max: 20, message: '微信号长度不能超过20'} ///长度提示
				}	
			},
			mobile: {
				validators: {
					stringLength: { max: 20, message: '手机长度不能超过20'}, ///长度提示
					regexp: {
				        regexp: /(^1\d{10}$)|(^0\d{2,3}-?\d{7,8}$)/,
				        message: '手机格式不正确'
				    }
				}
			},
			email: {
				validators: {
					stringLength: { max: 64, message: '邮箱不能超过64'}, ///长度提示
					emailAddress: { message: '邮箱格式不正确'}
				}
			}
		}
	});
	
});

//保存
function save(){
	//微信号、手机、邮箱这三种为身份验证信息，不可同时为空
	if($("#weixinid").val()=="" && $("#mobile").val()=="" && $("#email").val()==""){
		Public.alert(3,"微信号、手机、邮箱不可同时为空！");
		return false;
	}
	
	//触发全部验证
	$('#frm').data("bootstrapValidator").validate();
	//获取当前表单验证状态
	var flag = $('#frm').data("bootstrapValidator").isValid();
	
	if(flag){
		layer.confirm('您确定要保存吗？', {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type:'post',
				url:'/public/updateUserInfo.do',
				data:$("#frm").serialize(),
				dataType:"json",
				async:false,
				success:function(data){
					if(""!=data && null!=data && typeof(data)!="undefined"){
						if("updated"==data.errmsg){
							Public.alert(1,"保存成功！");
						}else{
							Public.alert(2,"保存失败！");
						}
					}else{
						Public.alert(2,"保存时出现异常！");
					}
				},
				error:function(data){
					Public.alert(2,"请求出现异常！");
					bool = false;
				}		
			});
			
			layer.close(index);
		});
	}
}
