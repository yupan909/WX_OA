/**
 * 个人中心列表
 */
$(function(){
	
	//回显通讯录
	showPeople();
	
	//列表标签页切换事件
	$("nav.navbar-fixed-top ul li").click(function(){
		$("nav.navbar-fixed-top ul li.active").removeClass("active");
		$(this).addClass("active");
		
		if("people"==$(this).attr("id")){ //通讯录
			$("#personInfo").show();
			showPeople();
		}else{ //工资条
			$("#personInfo").hide();
			showMoney();
		}
	});
	
});

/**
 * 回显通讯录
 */
function showPeople(){
	$("#listDiv").empty(); //清空列表
	//回显所有部门
	$.ajax({
		type:'post',
		url:'/public/getDeptlist.do',
		dataType:"json",
		async:false,
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				$("#listDiv").append('<div class="tree"><ul class="nav-people"></ul></div>');
				
				var dept = data.department;
				for(var i=0; i<dept.length; i++){
					var parentid = dept[i].parentid; //当前父部门id
					if($("ul.nav-people").find("li#"+parentid).size()==0){ 
						$("ul.nav-people").append('<li id="'+dept[i].id+'"><div class="root"><span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;'+dept[i].name+'</div></li>');
					}else{
						if($("ul.nav-people").find("li#"+parentid).children("ul").size()==0){
							$("ul.nav-people").find("li#"+parentid).append('<ul class="nav-child"><li class="dept" id="'+dept[i].id+'"><a><span class="glyphicon glyphicon-folder-close"></span><label>'+dept[i].name+'</label></a></li></ul>');
						}else{
							$("ul.nav-people").find("li#"+parentid).children("ul").append('<li class="dept" id="'+dept[i].id+'"><a><span class="glyphicon glyphicon-folder-close"></span><label>'+dept[i].name+'</label></a></li>');
						}
					}
				}
			}
		},
		error:function(data){
			Public.alert(2,"请求出现异常！");
		}		
	});
	
	//默认第一列展开
	$("ul.nav-people").children("li").children("ul").show();
	
	//部门点击事件
	$("ul.nav-people").find(".dept").unbind("click").click(function(e){
		if($(this).children(":last").is("ul")){
			if($(this).children(":last").is(":visible")){
				$(this).children(":last").hide(200);
				$(this).children("a").children("span").removeClass("glyphicon-folder-open").addClass("glyphicon-folder-close");
			}else{
				$(this).children(":last").show(200);
				$(this).children("a").children("span").removeClass("glyphicon-folder-close").addClass("glyphicon-folder-open");
			}
		}
		
		//回显部门下人员信息
		if($(this).attr("name")!="1"){
			$(this).attr("name","1");
			var deptId = $(this).attr("id");
			var now = this;
			//回显当前部门下的所有用户
			$.ajax({
				type:'post',
				url:'/public/getUserlist.do',
				data:{deptId:deptId},
				dataType:"json",
				async:false,
				success:function(data){
					if(""!=data && null!=data && typeof(data)!="undefined"){
						var user = data.userlist;
						for(var i=0; i<user.length; i++){
							var userid = user[i].userid; //当前用户ID
							var name = user[i].name; //当前用户名称
							var url = "/images/user.jpg";
							if("avatar" in user[i]){
								url = user[i].avatar; //头像url
							}
							if($(now).children("ul").size()==0){
								$(now).append('<ul class="nav-child"><li class="user" id="'+userid+'"><a><img src="'+url+'"><label>'+name+'</label></a></li></ul>');
							}else{
								$(now).children("ul").append('<li class="user" id="'+userid+'"><a><img src="'+url+'"><label>'+name+'</label></a></li>');
							}
						}
						if($(now).children("ul").size()>0){
							$(now).children("ul").show(200);
							$(now).children("a").children("span").removeClass("glyphicon-folder-close").addClass("glyphicon-folder-open");
							
							//用户点击事件
							$(now).children("ul").children(".user").unbind("click").click(function(e){
								window.location.href = "/views/person/person_people_view.html?userId="+$(this).attr("id");
								e.stopPropagation();
							});
						}
						
					}
				},
				error:function(data){
					Public.alert(2,"请求出现异常！");
				}		
			});
		}
		
		e.stopPropagation();
	});
}

/**
 * 回显工资条
 */
function showMoney(){
	$("#listDiv").empty(); //清空列表
	
	$.ajax({
		type:'post',
		url: "/money/editList.do",
		dataType:"json",
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				var list = data.list;
				var html = "";
				for(var i=0; i<list.length; i++){
					html += '<div class="row rowNew" onclick="show(\''+list[i].id+'\');">';
					html += '	<div class="col-xs-1 col-left"><span class="glyphicon glyphicon-yen"></span></div>';
					html += '	<div class="col-xs-7">'+list[i].title+'<br/>'+list[i].time+'</div>';
					html += '	<div class="col-xs-3 col-right"><span class="label label-info">'+list[i].month+'</span></div>';
					html += '</div>';
				}
				
				//显示无数据
				if(""==html){
					html += '<div class="row">';
					html += '	<div class="col-xs-11 col-center">当前没有任何记录...</div>';
					html += '</div>';
				}
				
				$("#listDiv").append(html);
			}
		},
		error:function(data){
			Public.alert(2,"请求出现异常！");
		}	
	});
}

//显示工资条详情
function show(id){
	window.location.href = "/views/person/person_money_detail.html?id="+id;
}

