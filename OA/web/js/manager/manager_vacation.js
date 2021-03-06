/**
 * 假期管理
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
		url: "/vacation/managerList.do?curr="+(curr || 1),
		data: $("#frm").serialize(),
		dataType:"json",
		success:function(data){
			if(""!=data && null!=data && typeof(data)!="undefined"){
				var list = data.list;
				var html = "";
				for(var i=0; i<list.length; i++){
					html += '<tr>';
					html += '	<td>'+parseInt(((curr || 1)-1)*pageNum+(i+1))+'</td>';
					html += '	<td>'+list[i].time+'</td>';
					html += '	<td>'+list[i].dept+'</td>';
					html += '	<td>'+list[i].user+'</td>';
					var type = "";
					if("01"==list[i].type){
						type = "出差";
					}else if("02"==list[i].type){
						type = "事假";
					}else if("03"==list[i].type){
						type = "病假";
					}else if("04"==list[i].type){
						type = "产假";
					}else if("05"==list[i].type){
						type = "年假";
					}else if("06"==list[i].type){
						type = "婚假";
					}else if("07"==list[i].type){
						type = "丧假";
					}
					html += '	<td>'+type+'</td>';
					html += '	<td>'+list[i].startDate+'</td>';
					html += '	<td>'+list[i].endDate+'</td>';
					var subflag = '<span class="label label-info">办理中</span>';
					if("02"==list[i].subflag){
						subflag = '<span class="label label-success">已办结</span>';
					}
					html += '	<td>'+subflag+'</td>';
					html += '	<td><button class="btn btn-primary btn-xs" onclick="view(\''+list[i].id+'\');">查看详情</button></td>';
					html += '</tr>';
				}
				
				if(""==html){
					html += '<tr><td colspan="9">查询不到记录...</td></tr>';
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

//查看详情
function view(id){
	layer.open({
		id:'vacation',
		type: 2, 
		title:'假期申请单',
		content: '/views/vacation/vacation_view.html?id='+id, //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
		area: ['400px', '550px'],
		btn: ['关闭'],
	    yes: function(index, layero){
	    	layer.close(index);
	    }
	});
}

//导出excel
function download(){
	if(Public.saveSession("frm")){
		window.location.href="/vacation/managerDownload.do";	
	}else{
		Public.alert(2,"导出出现异常！");
	}
}
