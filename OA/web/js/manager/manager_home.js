/**
 * 后台管理中心页面检查登录
 */
$.ajaxSetup({     
    contentType:"application/x-www-form-urlencoded;charset=utf-8",     
    complete:function(XMLHttpRequest,textStatus){   
		//通过XMLHttpRequest取得响应头，sessionstatus，    
		var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");  
        if(sessionstatus=="timeout"){  
        	 Public.alert(3,"用户信息过期,请重新登录！",function(){
				window.location.href="/home.html";
			 });  
        }     
    }     
});

$(function(){
	
	$("div.main").load($("ul.nav-sidebar li.active").attr("id"));
	
	/**
	 * 左侧列表切换事件
	 */
	$("ul.nav-sidebar li").click(function(e){
		$("ul.nav-sidebar li.active").removeClass("active");
		$(this).addClass("active");
		
		if($(this).attr("id")){
			$("div.main").load($(this).attr("id"));
		}else{
			var $span = $(this).children("a").children("span:last");
			if($span.attr("class")=="glyphicon glyphicon-menu-down"){
				$span.removeClass("glyphicon-menu-down").addClass("glyphicon-menu-up");
				//展开子列表
				$(".child_li").show(200);
			}else{
				$span.removeClass("glyphicon-menu-up").addClass("glyphicon-menu-down");
				//隐藏子列表
				$(".child_li").hide(200);
			}
		}
		
		e.stopPropagation();
	});
	
	//回显当前日期和登录人
	$.ajax({
		type:'post',
		url:"/public/getLoginInfo.do",
		dataType:"json",
		async:false,
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				$("#currentTimeInfo").html(data.date);
				$("#loginName").html(data.name);
			}else{
				Public.alert(2,"请求出现异常！");
			}
		},
		error:function(data){
			Public.alert(2,"请求出现异常！");
		}		
	});
});

//退出
function loginOut(){
	layer.confirm('您确定要退出系统吗？', {icon: 3, title:'提示'}, function(index){
		window.location.href="/home.html";
		layer.close(index);
	});
}