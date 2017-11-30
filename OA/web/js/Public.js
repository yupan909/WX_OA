/**
* 公共js
*/
var Public = {
		/**
		 * 提示框 1：成功 2：失败 3：警告
		 */
		alert:function(type,msg,fn){
			var icon = 1; //成功
			if(2==type){ //失败
				icon = 2;
			}else if(3==type){ //警告
				icon = 7;
			}
			
			layer.open({
				type:0,
				content:'<div style="font-size:20px;">'+msg+'</div>',
				icon: icon,
				btn: ['确定'],
			  	yes: fn,
			  	cancel: fn
			}); 
		},
		
		/**
		 * 获取url地址栏指定的参数
		 * @param name
		 */
		urlParam:function(name){
			var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
			var r = window.location.search.substr(1).match(reg);  //匹配目标参数
			if (r!=null){
				return unescape(r[2]);
			}else{
				 return null; //返回参数值
			} 
		},
		
		/**
		 * 回显数据
		 */
		editData:function(url){
			var id =$("#id").val();
			var wid =$("#wid").val();
			if(id == "" || id == null || typeof(id) == "undefined"){
				id = Public.urlParam("id");
				wid = Public.urlParam("wid");
				$("#wid").val(wid);
			}
			$.ajax({
				type:"post",
				url:url,
				data:{id:id,wid:wid},
				dataType:"json",
				async:false,
				success:function(data){
					if(""==data || null==data || typeof(data)=="undefined"){
						Public.alert(2, "回显数据失败！");
						return false;
					}
					for(var keys in data){
						var value = data[keys];
	                    var $key = $("#"+keys);
	                    if($key.is("select")){
							$key.find("option[value='"+value+"']").prop("selected",true);						
						}else if($("[name='"+keys+"']").attr("type")=="radio" || $("[name='"+keys+"']").attr("type")=="checkbox"){
							$("input[name="+keys+"][value="+value+"]").prop("checked",true);
						}else{
							if($key.is("span") || $key.is("font")){
								if("vacationType"==keys){ //请假类型
									if("01"==value){
										value = "出差";
									}else if("02"==value){
										value = "事假";
									}else if("03"==value){
										value = "病假";
									}else if("04"==value){
										value = "产假";
									}else if("05"==value){
										value = "年假";
									}else if("06"==value){
										value = "婚假";
									}else if("07"==value){
										value = "丧假";
									}
								}
								$key.html(value);
							}else{
								$key.val(value);
							}
						}
					}
				},
				error:function(){
					Public.alert(2, "系统异常！");
				}
			});
			
			
		},
		
		/**
		 * 保存表单
		 * 参数1：表单ID
		 * 参数2：提交路径
		 * type：提交或保存
		**/
		saveData:function(frm,action,type){
			var bool = false;
			try{
				$.ajax({
					type:'post',
					url:action,
					data:$("#"+frm).serialize(),
					dataType:"json",
					async:false,
					success:function(data){
						if(""!=data && null!=data && typeof(data)!="undefined"){
							if("1"==data.msg){
								$("#id").val(data.id); //回显id
								if("submit"!=type){ //提交时不提醒
									Public.alert(1,"保存成功！");
								}
								bool = true;
							}else{
								Public.alert(2,"保存失败！");
								bool = false;
							}
						}else{
							Public.alert(2,"保存时出现异常！");
							bool = false;
						}
					},
					error:function(data){
						Public.alert(2,"请求出现异常！");
						bool = false;
					}		
				});
			}catch(e){
			}
			return bool;
		},
		
		//选择审批人
		selectPeople:function(){
			layer.open({
				id:'workflow',
				type: 2, 
				title:'选择人员',
				content: '/views/workflow.html', //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
				area: ['100%', '100%']
			});
		},
		
		/**
		 * 页面跳转
		 */
		viewJump:function(idVal, widVal, viewVal){
			var id = "";
			if(idVal){
				id = idVal;
			}
			var wid = "";
			if(widVal){
				wid = widVal;
			}
			var view = "";
			if(viewVal){
				view = viewVal;
			}
			window.location.href = "/public/viewJump.do?id="+id+"&wid="+wid+"&view="+view;
		},
		
		/**
		 * 回显审批记录
		**/
		editflowLog:function(){
			var id = $("#id").val();
			if(id!=""){
				$.ajax({
					type:'post',
					url:'/public/getFLowLog.do?id='+id,
					dataType:"json",
					async:false,
					success:function(data){
						if(""!=data && null!=data && typeof(data)!="undefined"){
							var list = data.list;
							if(list.length>0){
								$("#flowLog").append('<div class="title"><span class="label label-primary">审批记录</span></div>');
								for(var i=0; i<list.length; i++){
									var html = '<div class="log">';
									if("receiveUser" in list[i]){
										if(list[i].isBack=="1"){ //退回
											html += (i+1) + "、" + list[i].sendTime + " " + list[i].sendUser + "退回给" + list[i].receiveUser + "；";
											if("idea" in list[i] && list[i].idea!=""){
												html += "（退回原因：" + list[i].idea + "）";
											}
										}else{
											html += (i+1) + "、" + list[i].sendTime + " " + list[i].sendUser + "提交给" + list[i].receiveUser + "审批；";
										}
									}else{ //办结
										html += (i+1) + "、" + list[i].sendTime + " " + list[i].sendUser + "办结。";
									}
									
									html += '</div>';
									$("#flowLog").append(html);
								}
							}
						}else{
							Public.alert(2,"请求出现异常！");
						}
					},
					error:function(data){
						Public.alert(2,"请求出现异常！");
					}		
				});
			}
		},
		
		/**
		 * 回显接收人（工作报告专用）
		 **/
		editJSR:function(){
			var id = $("#id").val();
			if(id!=""){
				$.ajax({
					type:'post',
					url:'/public/getFLowLog.do?id='+id,
					dataType:"json",
					async:false,
					success:function(data){
						if(""!=data && null!=data && typeof(data)!="undefined"){
							var list = data.list;
							if(list.length>0){
								$("#flowLogJSR").append('<span class="label label-primary">接收人</span> <span>'+list[0].receiveUser+'</span>');
							}
						}else{
							Public.alert(2,"请求出现异常！");
						}
					},
					error:function(data){
						Public.alert(2,"请求出现异常！");
					}		
				});
			}
		},
		
		saveSession:function(frm){
			var flag = false;
			$.ajax({
				type:'post',
				url:'/public/managerSaveSession.do?',
				data:$("#"+frm).serialize(),
				dataType:"json",
				async:false,
				success:function(data){
					if(""!=data && null!=data && typeof(data)!="undefined"){
						flag = true;
					}
				},
				error:function(data){
					Public.alert(2,"请求出现异常！");
				}		
			});
			return flag;
		}	
}