package com.java.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.common.Constant;
import com.java.common.util.JStringUtil;
import com.java.dao.OaApproveDao;
import com.java.dao.OaMainDao;
import com.java.entity.OaApproveBuy;
import com.java.entity.OaApproveFinance;
import com.java.entity.OaMain;
import com.java.entity.Query;
import com.java.entity.User;
import com.java.service.ApproveService;
@Service
@Transactional
public class ApproveServiceImpl implements ApproveService {
	
	@Resource
	private OaApproveDao oaApproveDao;
	
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
		
		List<Map<String,Object>> list = oaApproveDao.selectManagerList(query);
		Integer count = oaApproveDao.selectManagerListCount(query);
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
		String curr = request.getParameter("curr"); //当前页
		String type = request.getParameter("type"); //查询标识
		
		Query query = new Query();
		if(!JStringUtil.isEmpty(curr)){
			int startIndex = (Integer.parseInt(curr)-1) * Constant.PAGE_NUM + 1; //开始索引
			int endIndex = Integer.parseInt(curr) * Constant.PAGE_NUM; //结束索引
			query.setStartIndex(startIndex);
			query.setEndIndex(endIndex);
		}
		
		JSONObject result = new JSONObject();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Integer count = 0;
		
		if(!JStringUtil.isEmpty(userId)){
			query.setUser(userId);
			
			if("count".equals(type)){ //查询总条数
				if("db".equals(flag)){ //待处理
					count = oaApproveDao.selectListDBCount(query);
				}else if("yb".equals(flag)){ //已处理
					count = oaApproveDao.selectListYBCount(query);
				}else if("cg".equals(flag)){ //草稿箱
					count = oaApproveDao.selectListCGCount(query);
				}else if("my".equals(flag)){ //我申请的
					count = oaApproveDao.selectListMyCount(query);
				}
			}else{
				if("db".equals(flag)){ //待处理
					list = oaApproveDao.selectListDB(query);
				}else if("yb".equals(flag)){ //已处理
					list = oaApproveDao.selectListYB(query);
				}else if("cg".equals(flag)){ //草稿箱
					list = oaApproveDao.selectListCG(query);
				}else if("my".equals(flag)){ //我申请的
					list = oaApproveDao.selectListMy(query);
				}
			}
		}
		result.put("list",list);
		result.put("count",count);
		return result;
	}
	
	/**
	 * 回显--采购申请表
	 */
	public OaApproveBuy editBuy(HttpServletRequest request) {
		String id = request.getParameter("id");
		OaApproveBuy buy = new OaApproveBuy();
		if(!JStringUtil.isEmpty(id)){
			buy = oaApproveDao.selectBuy(id);
		}
		return buy;
	}
	
	/**
	 * 保存--采购申请表
	 * @param approveBuy
	 * @return
	 */
	public JSONObject saveBuy(OaApproveBuy approveBuy,HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user"); //获取当前用户
		String userId = user.getUserId(); //获取当前用户ID
		String userName = user.getUserName(); //获取当前用户名
		
		JSONObject result = new JSONObject();
		if(JStringUtil.isEmpty(approveBuy.getId())){ //第一次保存
			String id = JStringUtil.getUUID();
			approveBuy.setId(id);
			int i = oaApproveDao.addBuy(approveBuy); //保存业务表
			if(i>0){
				OaMain main = new OaMain();
				main.setId(approveBuy.getId());
				main.setType(Constant.TYPE_APPROVE_BUY);
				main.setCreTime(JStringUtil.getNowDate());
				main.setCreUserId(userId);
				main.setCreUserName(userName);
				main.setCreDeptId(user.getDeptId());
				main.setCreDeptName(user.getDeptName());
				main.setTitle(Constant.TITLE_APPROVE_BUY);
				main.setSubflag(Constant.SUBFLAG_CG);
				i = oaMainDao.add(main); //保存主表
				if(i>0){
					result.put("msg", "1"); //保存成功
					result.put("id", approveBuy.getId());
				}else{
					result.put("msg", "0"); //保存失败
				}	
			}else{
				result.put("msg", "0"); //保存失败
			}
		}else{ //更新
			int i = oaApproveDao.updateBuy(approveBuy); //更新业务表
			if(i>0){
				result.put("msg", "1"); //保存成功
				result.put("id", approveBuy.getId());
			}else{
				result.put("msg", "0"); //保存失败
			}
		}
		return result;
	}

	/**
	 * 回显--费用报销申请表
	 */
	public OaApproveFinance editFinance(HttpServletRequest request) {
		String id = request.getParameter("id");
		OaApproveFinance finance = new OaApproveFinance();
		if(!JStringUtil.isEmpty(id)){
			finance = oaApproveDao.selectFinance(id);
		}
		return finance;
	}

	/**
	 * 保存--费用报销申请表
	 */
	public JSONObject saveFinance(OaApproveFinance approveFinance,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user"); //获取当前用户
		String userId = user.getUserId(); //获取当前用户ID
		String userName = user.getUserName(); //获取当前用户名
		
		JSONObject result = new JSONObject();
		if(JStringUtil.isEmpty(approveFinance.getId())){ //第一次保存
			String id = JStringUtil.getUUID();
			approveFinance.setId(id);
			int i = oaApproveDao.addFinance(approveFinance); //保存业务表
			if(i>0){
				OaMain main = new OaMain();
				main.setId(approveFinance.getId());
				main.setType(Constant.TYPE_APPROVE_FINANCE);
				main.setCreTime(JStringUtil.getNowDate());
				main.setCreUserId(userId);
				main.setCreUserName(userName);
				main.setCreDeptId(user.getDeptId());
				main.setCreDeptName(user.getDeptName());
				main.setTitle(Constant.TITLE_APPROVE_FINANCE);
				main.setSubflag(Constant.SUBFLAG_CG);
				i = oaMainDao.add(main); //保存主表
				if(i>0){
					result.put("msg", "1"); //保存成功
					result.put("id", approveFinance.getId());
				}else{
					result.put("msg", "0"); //保存失败
				}	
			}else{
				result.put("msg", "0"); //保存失败
			}
		}else{ //更新
			int i = oaApproveDao.updateFinance(approveFinance); //更新业务表
			if(i>0){
				result.put("msg", "1"); //保存成功
				result.put("id", approveFinance.getId());
			}else{
				result.put("msg", "0"); //保存失败
			}
		}
		return result;
	}

	/**
	 * 删除--费用报销申请表/采购申请单
	 */
	public JSONObject delete(HttpServletRequest request) {
		String id = request.getParameter("id");
		JSONObject result = new JSONObject();
		
		if(!JStringUtil.isEmpty(id)){
			OaMain main = oaMainDao.select(id);
			if(null!=main){
				int i = 0;
				if(Constant.TYPE_APPROVE_BUY.equals(main.getType())){ //采购申请单
					i = oaApproveDao.deleteBuy(id);
				}else{
					i = oaApproveDao.deleteFinance(id);
				}
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
