/**
 * 工作报告列表
 */
$(function(){
	//回显列表
	showList($("nav.navbar-fixed-top ul li.active").attr("id"));
	
	//列表标签页切换事件
	$("nav.navbar-fixed-top ul li").click(function(){
		$("nav.navbar-fixed-top ul li.active").removeClass("active");
		$(this).addClass("active");
		showList($(this).attr("id")); //显示数据
	});
	
});

/**
 * 回显对应的列表数据
 * flag: 区分列表标识
 */
function showList(flag){
	$("#listDiv").empty(); //清空列表
	
	$.ajax({
		type:'post',
		url: "/report/editList.do",
		data:{flag:flag},
		dataType:"json",
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				var list = data.list;
				var html = "";
				if("dyb"==flag){ //报告给我
					for(var i=0; i<list.length; i++){
						html += '<div class="row rowNew" onclick="Public.viewJump(\''+list[i].id+'\',\''+list[i].wid+'\',\'y\');">';
						html += '	<div class="col-xs-1 col-left"><span class="glyphicon glyphicon-edit"></span></div>';
						html += '	<div class="col-xs-9">'+list[i].user+'的'+list[i].title+'<br/>'+list[i].time+'</div>';
						if("wid" in list[i]){
							html += '<div class="col-xs-1"><label style="color: red;line-height:40px;font-style:italic;">new</label></div>';
						}
						html += '</div>';
					}
				}else if("cg"==flag){ //草稿箱
					for(var i=0; i<list.length; i++){
						html += '<div class="row rowNew">';
						html += '	<div class="col-xs-1 col-left"><span class="glyphicon glyphicon-edit"></span></div>';
						html += '	<div class="col-xs-7" onclick="Public.viewJump(\''+list[i].id+'\',\'\',\'\');">'+list[i].user+'的'+list[i].title+'<br/>'+list[i].time+'</div>';
						html += '	<div class="col-xs-3 col-right"><button class="btn btn-danger btn-xs" onclick="del(this,\''+list[i].id+'\');">删除</button></div>';
						html += '</div>';
					}
				}else{ //我的报告
					for(var i=0; i<list.length; i++){
						html += '<div class="row rowNew" onclick="Public.viewJump(\''+list[i].id+'\',\'\',\'y\');">';
						html += '	<div class="col-xs-1 col-left"><span class="glyphicon glyphicon-edit"></span></div>';
						html += '	<div class="col-xs-10">'+list[i].user+'的'+list[i].title+'<br/>'+list[i].time+'</div>';
						html += '</div>';
					}
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

//删除
function del(obj,id){
	if(id==""||id=="undefined"){
		Public.alert(2,"删除出现异常！");   
	}else{
		layer.confirm("您确定要删除吗?", {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type:'post',
				url:"/report/delete.do?id="+id,
				dataType:"json",
				async:false,
				success:function(data){
					if(""!=data && null!=data && typeof(data)!="undefined"){
						if("1"==data.msg){
							Public.alert(1,"删除成功！",function(){
								$(obj).parent().parent(".row").remove();
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
