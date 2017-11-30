package com.java.controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.common.util.MakeWordUtil;
import com.java.entity.OaNotice;
import com.java.entity.Query;
import com.java.service.NoticeService;
/**
 * 通知公告
 * @author Administrator
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
		
	@Resource
	private NoticeService noticeService;
	
	/**
	 * 下载导出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/managerDownload")
	public void download(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject result = noticeService.managerList(request, null);
		MakeWordUtil.downLoad(result, "notice.ftl", "通知公告统计.xls", request, response);
	}
	
	/**
	 * 后台管理中心回显列表
	 */
	@RequestMapping("/managerList")
	@ResponseBody
	public String managerList(HttpServletRequest request, Query query) throws Exception{
		JSONObject result = noticeService.managerList(request, query);
		return result.toString();
	}
	
	/**
	 * 回显列表
	 */
	@RequestMapping("/editList")
	@ResponseBody
	public String editList(HttpServletRequest request) throws IOException{
		JSONObject result = noticeService.editList(request);
		return result.toString();
	}
	
	/**
	 * 回显
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(HttpServletRequest request) throws IOException{
		OaNotice notice = noticeService.edit(request);
		return JSONObject.fromObject(notice).toString();
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@ResponseBody
	public String save(OaNotice notice, HttpServletRequest request) throws IOException{
		JSONObject result = noticeService.save(notice,request);
		return result.toString();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(HttpServletRequest request) throws IOException{
		JSONObject result = noticeService.delete(request);
		return result.toString();
	}
	
}
