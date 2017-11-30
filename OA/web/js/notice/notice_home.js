/**
 * 通知公告列表
 */
$(function(){
	//回显列表
	showList();
});

/**
 * 回显对应的列表数据
 * flag: 区分列表标识
 */
function showList(){
	$("#listDiv").empty(); //清空列表
	
	$.ajax({
		type:'post',
		url: "/notice/editList.do",
		dataType:"json",
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				var list = data.list;
				var html = "";
				for(var i=0; i<list.length; i++){
					html += '<div class="row rowNew" onclick="view(\''+list[i].id+'\');">';
					html += '	<div class="col-xs-1 col-left"><span class="glyphicon glyphicon-comment"></span></div>';
					html += '	<div class="col-xs-10">'+list[i].title+'<br/>'+list[i].time+'</div>';
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

//查看
function view(id){
	window.location.href = "/views/notice/notice_view.html?id="+id;
}

//删除
function del(obj,id){
	if(id==""||id=="undefined"){
		Public.alert(2,"删除出现异常！");   
	}else{
		layer.confirm("您确定要删除吗?", {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type:'post',
				url:"/notice/delete.do?id="+id,
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
