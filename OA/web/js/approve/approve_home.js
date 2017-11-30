/**
 * 假期管理列表
 */

var pageNum = 10; //每页显示的记录数

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
		url: "/approve/editList.do",
		data:{flag:flag, type:"count"},
		dataType:"json",
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				laypage({
					cont: 'page', //容器。值支持id名、原生dom对象，jquery对象,
					pages: Math.ceil(data.count / pageNum), //总页数
					groups: 0, //连续分数数0
					prev: false, //不显示上一页
					next: '查看更多',
					skin: 'flow', //设置信息流模式的样式
					jump: function(obj){
						showList2(flag, obj.curr);
						if(obj.curr === Math.ceil(data.count / pageNum)-1){
							this.next = '没有更多了';
						}
					}
				});
			}
		}	
	});	
}
/**
 * 回显对应的列表数据
 * flag: 区分列表标识
 */
function showList2(flag,curr){
	$.ajax({
		type:'post',
		url: "/approve/editList.do?curr="+(curr || 1),
		data:{flag:flag},
		dataType:"json",
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				var list = data.list;
				var html = "";
				if("db"==flag){ //待处理
					for(var i=0; i<list.length; i++){
						html += '<div class="row rowNew" onclick="Public.viewJump(\''+list[i].id+'\',\''+list[i].wid+'\',\'\');">';
						html += '	<div class="col-xs-1 col-left"><span class="glyphicon glyphicon-list-alt"></span></div>';
						html += '	<div class="col-xs-7">'+list[i].user+'的'+list[i].title+'<br/>'+list[i].time+'</div>';
						html += '	<div class="col-xs-3 col-right col-sendUser"><span class="label label-info">发送人</span><br>'+list[i].preUser+'</div>';
						html += '</div>';
					}
				}else if("cg"==flag){ //草稿箱
					for(var i=0; i<list.length; i++){
						html += '<div class="row rowNew">';
						html += '	<div class="col-xs-1 col-left"><span class="glyphicon glyphicon-list-alt"></span></div>';
						html += '	<div class="col-xs-7" onclick="Public.viewJump(\''+list[i].id+'\',\'\',\'\');">'+list[i].user+'的'+list[i].title+'<br/>'+list[i].time+'</div>';
						html += '	<div class="col-xs-3 col-right"><button class="btn btn-danger btn-xs" onclick="del(this,\''+list[i].id+'\');">删除</button></div>';
						html += '</div>';
					}
				}else{ //已处理 、我申请的
					for(var i=0; i<list.length; i++){
						html += '<div class="row rowNew" onclick="Public.viewJump(\''+list[i].id+'\',\'\',\'y\');">';
						html += '	<div class="col-xs-1 col-left"><span class="glyphicon glyphicon-list-alt"></span></div>';
						html += '	<div class="col-xs-7">'+list[i].user+'的'+list[i].title+'<br/>'+list[i].time+'</div>';
						var subflag = '<span class="label label-info">办理中</span>';
						if("02"==list[i].subflag){
							subflag = '<span class="label label-success">已办结</span>';
						}
						html += '	<div class="col-xs-3 col-right">'+subflag+'</div>';
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
				url:"/approve/delete.do?id="+id,
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
