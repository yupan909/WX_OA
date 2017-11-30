package com.java.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

import com.java.common.Constant;
import com.java.common.util.JStringUtil;
import com.java.common.util.MessageUtil;
import com.java.dao.OaNoticeDao;
import com.java.entity.OaNotice;
import com.java.entity.Query;
import com.java.service.NoticeService;
@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

	@Resource
	private OaNoticeDao oaNoticeDao;
	
	/**
	 * 回显管理中心数据列表
	 * @throws Exception 
	 */
	public JSONObject managerList(HttpServletRequest request, Query query) throws Exception {
		HttpSession session = request.getSession();
		
		JSONObject result = new JSONObject();
		String curr = request.getParameter("curr"); //当前页
		
		if(null==query){ //下载时,通过session取query
			query = (Query)session.getAttribute("query");
		}
		
		if(!JStringUtil.isEmpty(curr)){
			int startIndex = (Integer.parseInt(curr)-1) * Constant.PAGE_NUM + 1; //开始索引
			int endIndex = Integer.parseInt(curr) * Constant.PAGE_NUM; //结束索引
			query.setStartIndex(startIndex);
			query.setEndIndex(endIndex);
		}
		
		List<Map<String,Object>> list = oaNoticeDao.selectManagerList(query);
		Integer count = oaNoticeDao.selectManagerListCount(query);
		result.put("list",list);
		result.put("count",count);
		
		//sesion中移除query
		if(null!=(Query)session.getAttribute("query")){
			session.removeAttribute("query");
		}
		return result;
	}
	
	/**
	 * 回显列表数据
	 */
	public JSONObject editList(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		List<Map<String,Object>> list = oaNoticeDao.selectList();
		result.put("list",list);
		return result;
	}

	/**
	 * 回显
	 */
	public OaNotice edit(HttpServletRequest request) {
		String id = request.getParameter("id");
		OaNotice notice = new OaNotice();
		if(!JStringUtil.isEmpty(id)){
			notice = oaNoticeDao.select(id);
		}
		return notice;
	}

	/**
	 * 保存
	 */
	public JSONObject save(OaNotice notice, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		if(JStringUtil.isEmpty(notice.getId())){ //第一次保存
			String id = JStringUtil.getUUID();
			notice.setId(id);
			notice.setNoticeTime(JStringUtil.getNowDate());
			int i = oaNoticeDao.add(notice); //保存业务表
			if(i>0){
				//发消息
				MessageUtil.send("@all", "有一条新的通知公告，请前往“通知公告”查看");
				
				result.put("msg", "1"); //保存成功
				result.put("id", notice.getId());
			}else{
				result.put("msg", "0"); //保存失败
			}
		}else{ //更新
			int i = oaNoticeDao.update(notice); //更新业务表
			if(i>0){
				result.put("msg", "1"); //保存成功
				result.put("id", notice.getId());
			}else{
				result.put("msg", "0"); //保存失败
			}
		}
		return result;
	}

	/**
	 * 删除
	 */
	public JSONObject delete(HttpServletRequest request) {
		String id = request.getParameter("id");
		JSONObject result = new JSONObject();
		
		if(!JStringUtil.isEmpty(id)){
			int i = oaNoticeDao.delete(id);
			if(i>0){
				result.put("msg", "1");
			}else{
				result.put("msg", "0");
			}
		}else{
			result.put("msg", "0");
		}
		return result;
	}

}
