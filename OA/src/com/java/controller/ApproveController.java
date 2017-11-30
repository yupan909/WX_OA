package com.java.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.common.util.MakeWordUtil;
import com.java.entity.OaApproveBuy;
import com.java.entity.OaApproveFinance;
import com.java.entity.Query;
import com.java.service.ApproveService;
import com.java.service.MainService;
/**
 * 审批管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/approve")
public class ApproveController {
		
	@Resource
	private ApproveService approveService;
	
	@Resource
	private MainService mainService;
	
	/**
	 * 下载导出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/managerDownload")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject result = approveService.managerList(request,null);
		MakeWordUtil.downLoad(result, "approve.ftl", "审批统计.xls", request, response);
	}
	
	/**
	 * 后台管理中心回显列表
	 */
	@RequestMapping("/managerList")
	@ResponseBody
	public String managerList(HttpServletRequest request, Query query) throws IOException{
		JSONObject result = approveService.managerList(request, query);
		return result.toString();
	}
	
	/**
	 * 回显列表
	 */
	@RequestMapping("/editList")
	@ResponseBody
	public String editList(HttpServletRequest request) throws IOException{
		JSONObject result = approveService.editList(request);
		return result.toString();
	}
	
	/**
	 * 回显 --采购申请单
	 */
	@RequestMapping("/editBuy")
	@ResponseBody
	public String editBuy(HttpServletRequest request) throws IOException{
		OaApproveBuy buy = approveService.editBuy(request);
		return JSONObject.fromObject(buy).toString();
	}
	
	/**
	 * 保存 --采购申请单
	 */
	@RequestMapping("/saveBuy")
	@ResponseBody
	public String saveBuy(OaApproveBuy approveBuy, HttpServletRequest request) throws IOException{
		JSONObject result = approveService.saveBuy(approveBuy,request);
		return result.toString();
	}
	
	
	/**
	 * 回显 --费用报销申请表
	 */
	@RequestMapping("/editFinance")
	@ResponseBody
	public String editFinance(HttpServletRequest request) throws IOException{
		OaApproveFinance finance = approveService.editFinance(request);
		return JSONObject.fromObject(finance).toString();
	}
	
	/**
	 * 保存 --费用报销申请表
	 */
	@RequestMapping("/saveFinance")
	@ResponseBody
	public String saveFinance(OaApproveFinance approveFinance, HttpServletRequest request) throws IOException{
		JSONObject result = approveService.saveFinance(approveFinance, request);
		return result.toString();
	}
	
	/**
	 * 删除 --费用报销申请表 /采购申请单
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delFinance(HttpServletRequest request) throws IOException{
		JSONObject result = approveService.delete(request);
		return result.toString();
	}
	
	
	/**
	 * 提交
	 */
	@RequestMapping("/submitFLow")
	@ResponseBody
	public String submitFLow(HttpServletRequest request) throws IOException{
		JSONObject result = mainService.submitFLow(request);
		return result.toString();
	}
	
	/**
	 * 办结
	 */
	@RequestMapping("/endFLow")
	@ResponseBody
	public String endFLow(HttpServletRequest request) throws IOException{
		JSONObject result = mainService.endFLow(request);
		return result.toString();
	}
	
}
