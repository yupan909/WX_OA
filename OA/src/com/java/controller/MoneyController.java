package com.java.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.java.common.util.MakeWordUtil;
import com.java.entity.OaMoney;
import com.java.entity.OaMoneyDetail;
import com.java.entity.Query;
import com.java.service.MoneyService;
/**
 * 工资条
 * @author Administrator
 */
@Controller
@RequestMapping("/money")
public class MoneyController {
		
	@Resource
	private MoneyService moneyService;
	
	/**
	 * 上传工资条
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload",method = RequestMethod.POST)  
	@ResponseBody
    public String upload(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws Exception {
    	  response.setContentType("text/html;charset=UTF-8");  
    	  JSONObject result = new JSONObject();
    	  result.put("msg", "0");
    	  try {
	    	  for(Iterator it = multipartRequest.getFileNames(); it.hasNext(); ){
	    		   String key = (String) it.next();
	    		   MultipartFile orderFile = multipartRequest.getFile(key);
	    		   if (orderFile.getOriginalFilename().length() > 0) {
	    			   if(orderFile.getOriginalFilename().indexOf(".xls")>-1){ //只允许xls类型的文件
	    				   String realPath = multipartRequest.getSession().getServletContext().getRealPath("/file");
	    				   FileUtils.copyInputStreamToFile(orderFile.getInputStream(),new File(realPath, orderFile.getOriginalFilename()));
	    				   
	    				   result.put("filePath", realPath + File.separator + orderFile.getOriginalFilename()); //文件路径
	    				   result.put("fileName", orderFile.getOriginalFilename()); //文件名称
	    				   result.put("msg", "1");
	    			   }else{
	    				   result.put("msg", "2"); //文件类型不正确
	    			   }
	    		   }
	    	  }
    	  } catch (Exception ex) {
    	      ex.printStackTrace();
    	  }
    	  return result.toString();
    } 
	
	/**
	 * 下载工资条模板
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/managerDownload")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject result = new JSONObject();
		MakeWordUtil.downLoad(result, "money.ftl", "工资条模板.xls", request, response);
	}
	
	/**
	 * 后台管理中心回显列表--工资条
	 */
	@RequestMapping("/managerList")
	@ResponseBody
	public String managerList(HttpServletRequest request, Query query) throws Exception{
		JSONObject result = moneyService.managerList(request, query);
		return result.toString();
	}
	
	/**
	 * 后台管理中心回显列表--工资条明细表
	 */
	@RequestMapping("/managerDetailList")
	@ResponseBody
	public String managerDetailList(HttpServletRequest request, Query query) throws Exception{
		JSONObject result = moneyService.managerDetailList(request, query);
		return result.toString();
	}
	
	/**
	 * 新建工资条
	 */
	@RequestMapping("/managerSave")
	@ResponseBody
	public String managerSave(OaMoney money, HttpServletRequest request) throws IOException{
		JSONObject result = moneyService.managerSave(money, request);
		return result.toString();
	}
	
	/**
	 * 删除工资条
	 */
	@RequestMapping("/managerDelete")
	@ResponseBody
	public String managerDelete(HttpServletRequest request) throws IOException{
		JSONObject result = moneyService.managerDelete(request);
		return result.toString();
	}
	
	/**
	 * 回显个人工资条列表
	 */
	@RequestMapping("/editList")
	@ResponseBody
	public String editList(HttpServletRequest request) throws IOException{
		JSONObject result = moneyService.editList(request);
		return result.toString();
	}
	
	/**
	 * 回显个人工资明细
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(HttpServletRequest request) throws IOException{
		OaMoneyDetail moneyDetail = moneyService.edit(request);
		return JSONObject.fromObject(moneyDetail).toString();
	}
	
}
