/**
 * 工资条
 */
var pageNum = 10; //每页显示的记录数

$(function(){
	editList();
});

//回显列表
function editList(curr){
	
	$("#data").empty(); //清空列表
	
	$.ajax({
		type:'post',
		url: "/money/managerList.do?curr="+(curr || 1),
		data: $("#frm").serialize(),
		dataType:"json",
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				var list = data.list;
				var html = "";
				for(var i=0; i<list.length; i++){
					html += '<tr>';
					html += '	<td>'+parseInt(((curr || 1)-1)*pageNum+(i+1))+'</td>';
					html += '	<td>'+list[i].title+'</td>';
					html += '	<td>'+list[i].month+'</td>';
					html += '	<td>'+list[i].time+'</td>';
					html += '	<td><button class="btn btn-primary btn-xs" onclick="view(\''+list[i].id+'\',\''+list[i].title+'\');">查看详情</button> <button class="btn btn-default btn-xs" onclick="del(\''+list[i].id+'\');">删除</button></td>';
					html += '</tr>';
				}
				
				if(""==html){
					html += '<tr><td colspan="5">查询不到记录...</td></tr>';
				}
				$("#data").append(html);
				
				//显示分页
			    laypage({
			        cont: 'page', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
			        pages: Math.ceil(data.count / pageNum), //通过后台拿到的总页数
			        skin:'#428bca',
			        groups: 3, //连续显示分页数
			        skip: true, //是否开启跳页
			        curr: curr || 1, //当前页
			        jump: function(obj, first){ //触发分页后的回调
			        	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
			        		editList(obj.curr);
			        	}
			        }
			    });
			    
			}
		},
		error:function(data){
			Public.alert(2,"请求出现异常！");
		}	
	});
}

//新建工资条
function newForm(){
	layer.open({
		id:'money',
		type: 2, 
		title:'新建工资条',
		content: '/views/manager/manager_person_money_form.html', //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
		area: ['400px', '550px'],
		btn: ['保存','关闭'],
	    yes: function(index, layero){
	    	var bool = $(layero).find("iframe")[0].contentWindow.save();
	    	if(bool){
	    		layer.close(index);
	    		Public.alert(1,"保存成功！");
	    		editList(); //刷新列表
	    	}
	    },
	    btn2: function(index, layero){
	    	layer.close(index);
	    }
	});
}

//查看工资条详情
function view(id, title){
	layer.open({
		id: 'moneyDetail',
		type: 2, 
		title: title,
		content: '/views/manager/manager_person_money_detail.html?id='+id, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
		area: ['900px', '550px'],
		btn: ['关闭'],
	    yes: function(index, layero){
	    	layer.close(index);
	    }
	});
}

//删除
function del(id){
	if(id==""||id=="undefined"){
		Public.alert(2,"删除出现异常！");   
	}else{
		layer.confirm("您确定要删除吗?", {icon: 3, title:'提示'}, function(index){
			$.ajax({
				type:'post',
				url:"/money/managerDelete.do?id="+id,
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