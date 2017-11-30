/**
 * 移动页面--用户登录检查
 */
$.ajaxSetup({     
    contentType:"application/x-www-form-urlencoded;charset=utf-8",     
    complete:function(XMLHttpRequest,textStatus){   
     //通过XMLHttpRequest取得响应头，sessionstatus，    
      var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");   
             if(sessionstatus=="timeout"){  
 				 window.location.href="/login.jsp";
             }     
       }     
});