package com.java.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.java.dao.OaMainDao;
import com.java.dao.OaVacationDao;
import com.java.entity.OaApproveBuy;
import com.java.entity.OaMain;
import com.java.entity.OaVacation;
import com.java.entity.Query;
import com.java.entity.User;
import com.java.service.VacationService;
@Service
@Transactional
public class VacationServiceImpl implements VacationService {
	
	@Resource
	private OaVacationDao oaVacationDao;
	
	@Resource
	private OaMainDao oaMainDao;
	
	/**
	 * 回显管理中心数据列表
	 */
	public JSONObject managerList(HttpServletRequest request, Query query) {
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
		
		List<Map<String,Object>> list = oaVacationDao.selectManagerList(query);
		Integer count = oaVacationDao.selectManagerListCount(query);
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
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user"); //获取当前用户
		String userId = user.getUserId(); //获取当前用户ID
		
		String flag = request.getParameter("flag"); //列表标识
		
		JSONObject result = new JSONObject();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(!JStringUtil.isEmpty(userId)){
			if("db".equals(flag)){ //待处理
				list = oaVacationDao.selectListDB(userId);
			}else if("yb".equals(flag)){ //已处理
				list = oaVacationDao.selectListYB(userId);
			}else if("cg".equals(flag)){ //草稿箱
				list = oaVacationDao.selectListCG(userId);
			}else if("my".equals(flag)){ //我申请的
				list = oaVacationDao.selectListMy(userId);
			}
		}
		result.put("list",list);
		return result;
	}

	/**
	 * 回显
	 */
	public OaVacation edit(HttpServletRequest request) {
		String id = request.getParameter("id");
		OaVacation vacation = new OaVacation();
		if(!JStringUtil.isEmpty(id)){
			vacation = oaVacationDao.select(id);
		}
		return vacation;
	}

	/**
	 * 保存
	 */
	public JSONObject save(OaVacation vacation, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user"); //获取当前用户
		String userId = user.getUserId(); //获取当前用户ID
		String userName = user.getUserName(); //获取当前用户名
		
		JSONObject result = new JSONObject();
		if(JStringUtil.isEmpty(vacation.getId())){ //第一次保存
			String id = JStringUtil.getUUID();
			vacation.setId(id);
			int i = oaVacationDao.add(vacation); //保存业务表
			if(i>0){
				OaMain main = new OaMain();
				main.setId(vacation.getId());
				main.setType(Constant.TYPE_VACATION);
				main.setCreTime(JStringUtil.getNowDate());
				main.setCreUserId(userId);
				main.setCreUserName(userName);
				main.setCreDeptId(user.getDeptId());
				main.setCreDeptName(user.getDeptName());
				main.setTitle(Constant.VACATION_MAP.get(vacation.getVacationType()) + Constant.TITLE_VACATION);
				main.setSubflag(Constant.SUBFLAG_CG);
				i = oaMainDao.add(main); //保存主表
				if(i>0){
					result.put("msg", "1"); //保存成功
					result.put("id", vacation.getId());
				}else{
					result.put("msg", "0"); //保存失败
				}	
			}else{
				result.put("msg", "0"); //保存失败
			}
		}else{ //更新
			int i = oaVacationDao.update(vacation); //更新业务表
			OaMain main = new OaMain();
			main.setId(vacation.getId());
			main.setTitle(Constant.VACATION_MAP.get(vacation.getVacationType()) + Constant.TITLE_VACATION);
			int j = oaMainDao.updateTitle(main); //更新主表
			if(i>0 && j>0){
				result.put("msg", "1"); //保存成功
				result.put("id", vacation.getId());
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
			OaMain main = oaMainDao.select(id);
			if(null!=main){
				int i = oaVacationDao.delete(id);
				int j = oaMainDao.delete(id);
				if(i>0 && j>0){
					result.put("msg", "1");
				}else{
					result.put("msg", "0");
				}
			}else{
				result.put("msg", "0");
			}
		}else{
			result.put("msg", "0");
		}
		return result;
	}

}
