/**
 * 工资条明细
 */
var pageNum = 10; //每页显示的记录数

$(function(){
	$("#recordId").val(Public.urlParam("id"));
	
	editList();
});

//回显列表
function editList(curr){
	
	$("#data").empty(); //清空列表
	
	$.ajax({
		type:'post',
		url: "/money/managerDetailList.do?curr="+(curr || 1),
		data: $("#frm").serialize(),
		dataType:"json",
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				var list = data.list;
				var html = "";
				for(var i=0; i<list.length; i++){
					html += '<tr>';
					html += '	<td>'+parseInt(((curr || 1)-1)*pageNum+(i+1))+'</td>';
					html += '	<td>'+list[i].user+'</td>';
					html += '	<td>'+list[i].dept+'</td>';
					html += '	<td>'+list[i].userId+'</td>';
					html += '	<td>'+list[i].jb+'</td>';
					html += '	<td>'+list[i].jx+'</td>';
					html += '	<td>'+list[i].jj+'</td>';
					html += '	<td>'+list[i].cb+'</td>';
					html += '	<td>'+list[i].bx+'</td>';
					html += '	<td>'+list[i].all+'</td>';
					html += '</tr>';
				}
				
				if(""==html){
					html += '<tr><td colspan="10">查询不到记录...</td></tr>';
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
