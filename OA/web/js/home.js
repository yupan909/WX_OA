/**
* 后台管理登录页
*/
$(function(){
	
	$("#userName").focus(); //为用户名设置焦点
	
	//背景自适应屏幕
	if($(window).height()<=736){
	  	$(".bg-top").css("display","none");
	}else{
	  	$(".bg-top").css("height",$(window).height()-736);
	}
	if($(window).width()>1260){
	    var width=$(window).width()-1261;
		var wid=(width/2);
		$(".bg-add").css("display","block");
		$(".bg-top").css("display","block");
		$(".bg-add").css("width",wid);
	}else{
	  	$(".bg-add").css("display","none");
	}
	
	//回车事件
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	login(); //登录
	    }
	}
	
});


//验证
function check(){
	if($("#username").val()==""){
		Public.alert(3,"请输入用户名！");
		return false;
	}else if($("#password").val()==""){
		Public.alert(3,"请输入密码！");
		return false;
	}
	return true;
}

//登录
function login(){
	//验证
	if(!check()){
		return false;
	}
	
	$.ajax({
		type:"POST",
		url:"/loginHome.do",
		data:$("#frm").serialize(),
		dataType:"json",
		async:false,
		success:function(data){
			if(data.msg=="1"){
				window.location.href = "/views/manager/manager_home.html";
			}else{
				Public.alert(2,"用户名或密码不正确!");
			}
		},
		error:function(){
			Public.alert(2,"系统异常，请联系管理员！");
		}
	});
	
	
}