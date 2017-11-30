/* 个人中心 ---通讯录 */


$(function(){
	editList();
});

/**
 * 回显通讯录
 */
function editList(){
	
	$(".people-left").empty(); //清空列表
	//回显所有部门
	$.ajax({
		type:'post',
		url:'/public/getDeptlist.do',
		dataType:"json",
		async:false,
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				$(".people-left").append('<div class="tree"><ul class="nav-people"></ul></div>');
				
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
		$("ul.nav-people").find(".dept-active").removeClass("dept-active");
		$(this).children("a").addClass("dept-active");
		
		if($(this).children(":last").is("ul")){
			if($(this).children(":last").is(":visible")){
				$(this).children(":last").hide(200);
				$(this).children("a").children("span").removeClass("glyphicon-folder-open").addClass("glyphicon-folder-close");
			}else{
				$(this).children(":last").show(200);
				$(this).children("a").children("span").removeClass("glyphicon-folder-close").addClass("glyphicon-folder-open");
			}
		}
		
		//回显指定下的用户
		editListPeople($(this).attr("id"));
		
		e.stopPropagation();
	});
}

//显示指定部门下的用户
function editListPeople(deptId){
	$("#data").empty(); //清空列表
	
	//回显当前部门下的所有用户
	$.ajax({
		type:'post',
		url:'/public/getUserlist.do',
		data:{deptId:deptId},
		dataType:"json",
		async:false,
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				
				var html = "";
				var user = data.userlist;
				for(var i=0; i<user.length; i++){
					html += '<tr>';
					html += '	<td>'+(i+1)+'</td>';
					html += '	<td>'+user[i].name+'</td>';
					html += '	<td>'+user[i].userid+'</td>';
					//性别
					if("gender" in user[i]){
						var gender = "";
						if("1"==user[i].gender){
							gender="男";
						}else if("2"==user[i].gender){
							gender="女";
						}
						html += '	<td>'+gender+'</td>';
					}else{
						html += '	<td></td>';
					}
					//职位
					if("position" in user[i]){
						html += '	<td>'+user[i].position+'</td>';
					}else{
						html += '	<td></td>';
					}
					//微信号
					if("weixinid" in user[i]){
						html += '	<td>'+user[i].weixinid+'</td>';
					}else{
						html += '	<td></td>';
					}
					//手机
					if("mobile" in user[i]){
						html += '	<td>'+user[i].mobile+'</td>';
					}else{
						html += '	<td></td>';
					}
					//邮箱
					if("email" in user[i]){
						html += '	<td>'+user[i].email+'</td>';
					}else{
						html += '	<td></td>';
					}
					html += '	<td><button class="btn btn-primary btn-xs" onclick="view(\''+user[i].userid+'\');">查看详情</button></td>';
					html += '</tr>';
				}
				
				if(""==html){
					html += '<tr><td colspan="9">查询不到记录...</td></tr>';
				}
				$("#data").append(html);
			}
		},
		error:function(data){
			Public.alert(2,"请求出现异常！");
		}		
	});
}

//查看
function view(userId){
	layer.open({
		id:'report',
		type: 2, 
		title:'人员信息',
		content: '/views/person/person_people_view.html?userId='+userId, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
		area: ['400px', '550px'],
	});
}

//删除
function del(userId){
	if(userId==""||userId=="undefined"){
		Public.alert(2,"删除出现异常！");   
	}else{
		layer.confirm("您确定要删除吗?", {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type:'post',
				url:"/notice/delete.do?userId="+userId,
				dataType:"json",
				async:false,
				success:function(data){
					if(""!=data && null!=data && typeof(data)!="undefined"){
						if("1"==data.msg){
							Public.alert(1,"删除成功！",function(){
								editList();
								layer.close(layer.index);
							});
						}else{
							Public.alert(2,"删除失败！");
						}
					}else{
						Public.alert(2,"删除时出现异常！");
					}
				},
				error:function(data){
					Public.alert(2,"请求出现异常！");
				}		
			});
			
			layer.close(index);
		});
	}
}