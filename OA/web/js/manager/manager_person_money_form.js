/**
 * 新建工资条
 */
$(function(){
	
	//添加验证
	$('#frm').bootstrapValidator({
		message: '不能为空', //为每个字段指定通用错误提示语
		fields: { /*键名username和input name值对应*/
			moneyTitle: {
				validators: {
					notEmpty: {message: '工资条名称不能为空'}, //非空提示	
					stringLength: { max: 50, message: '工资条名称长度不能超过50'} ///长度提示
				}	
			},
			moneyMonth: {
				validators: {
					notEmpty: {message: '月份不能为空'}, //非空提示	
					stringLength: { max: 10, message: '月份长度不能超过10'} ///长度提示
				}	
			}
		}
	});
	
	//月份
	$("#moneyMonth").click(function(){
		WdatePicker({
			dateFmt:'yyyy-MM',
			readOnly:true,
			onpicked:function(){  //重新验证
				$('#frm').data("bootstrapValidator").updateStatus('moneyMonth', 'NOT_VALIDATED', null).validateField('moneyMonth');
			},
			oncleared:function(){  //重新验证
				$('#frm').data("bootstrapValidator").updateStatus('moneyMonth', 'NOT_VALIDATED', null).validateField('moneyMonth');
			}
		});
	});
	
	
	 $('#fileupload').fileupload({
		url: '/money/upload.do',
		dataType: 'json',
		formData: {oldFilePath : $("#filePath").val()},
        change: function(e, data) {
            if(data.files.length > 1){
            	Public.alert(3,"只允许上传一个文件！");
                return false;
            }
        },
        done: function (e, data) {
        	if(data.result.msg=="1"){
        		$("#filePath").val(data.result.filePath); //回显文件路径
        		$(".fileDiv").empty().append('<p>'+data.result.fileName+'</p>');
        	}else if(data.result.msg=="2"){
        		Public.alert(3,"只允许上传xls类型文件！");
        	}else{
        		Public.alert(2,"上传出现异常！");
        	}
        }
    });
});

//保存
function save(){
	var bool = false;
	
	//触发全部验证
	$('#frm').data("bootstrapValidator").validate();
	//获取当前表单验证状态
	var flag = $('#frm').data("bootstrapValidator").isValid();
	
	if(flag){
		if($("#filePath").val()==""){
			Public.alert(3,"请上传工资条");
			return false;
		}
		$.ajax({
			type:'post',
			url:'/money/managerSave.do',
			data:$("#frm").serialize(),
			dataType:"json",
			async:false,
			success:function(data){
				if(""!=data && null!=data && typeof(data)!="undefined"){
					if("1"==data.msg){
						bool = true;
					}else{
						Public.alert(2,"保存失败！");
					}
				}else{
					Public.alert(2,"保存时出现异常！");
				}
			},
			error:function(data){
				Public.alert(2,"请求出现异常！");
			}		
		});
	}
	return bool;
}

/**
 * 下载工资条模板
 */
function downModule(){
	window.location.href="/money/managerDownload.do";
}
