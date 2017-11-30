package com.java.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.common.util.JStringUtil;
import com.java.common.util.MakeWordUtil;
import com.java.entity.OaReport;
import com.java.entity.Query;
import com.java.service.MainService;
import com.java.service.ReportService;
/**
 * 工作报告
 * @author Administrator
 */
@Controller
@RequestMapping("/report")
public class ReportController {
		
	@Resource
	private ReportService reportService;
	
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
		JSONObject result = reportService.managerList(request, null);
		MakeWordUtil.downLoad(result, "report.ftl", "工作报告统计.xls", request, response);
	}
	
	/**
	 * 后台管理中心回显列表
	 */
	@RequestMapping("/managerList")
	@ResponseBody
	public String managerList(HttpServletRequest request, Query query) throws Exception{
		JSONObject result = reportService.managerList(request, query);
		return result.toString();
	}
	
	/**
	 * 回显列表
	 */
	@RequestMapping("/editList")
	@ResponseBody
	public String editList(HttpServletRequest request) throws IOException{
		JSONObject result = reportService.editList(request);
		return result.toString();
	}
	
	/**
	 * 回显
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(HttpServletRequest request) throws IOException{
		OaReport report = reportService.edit(request);
		return JSONObject.fromObject(report).toString();
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(OaReport report, HttpServletRequest request) throws IOException{
		JSONObject result = reportService.save(report,request);
		return result.toString();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(HttpServletRequest request) throws IOException{
		JSONObject result = reportService.delete(request);
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
	 * 查看时办结
	 */
	@RequestMapping("/endFLow")
	@ResponseBody
	public String endFLow(HttpServletRequest request) throws IOException{
		JSONObject result = mainService.endFLow(request);
		return result.toString();
	}
	
}
