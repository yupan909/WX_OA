package com.java.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.common.Constant;
import com.java.common.util.JStringUtil;
import com.java.common.util.MessageUtil;
import com.java.dao.OaMainDao;
import com.java.entity.FlowLog;
import com.java.entity.FlowRead;
import com.java.entity.FlowWrite;
import com.java.entity.OaMain;
import com.java.entity.User;
import com.java.service.MainService;

@Service
@Transactional
public class MainServiceImpl implements MainService {

	@Resource
	private OaMainDao oaMainDao;
	
	public OaMain select(String id) {
		return oaMainDao.select(id);
	}
	
	/**
	 * 提交
	 */
	public JSONObject submitFLow(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user"); //获取当前用户
		String userId = user.getUserId(); //获取当前用户ID
		String userName = user.getUserName(); //获取当前用户名
		
		String id = request.getParameter("id");
		String wid = request.getParameter("wid");
		String nextUserId = request.getParameter("nextUserId"); //审批人ID
		String nextUserName = request.getParameter("nextUserName");//审批人名称
		String isBack = request.getParameter("isBack");//是否退回标识
		
		
		
		JSONObject result = new JSONObject();
		result.put("msg", "0");
		
		if(!JStringUtil.isEmpty(id)){
			OaMain main = oaMainDao.select(id);
			
			String idea = "" ;//退回原因
			
			if("back".equals(isBack)){ //退回申请人
				isBack = "1";
				nextUserId = main.getCreUserId();
				nextUserName = main.getCreUserName();
				idea = request.getParameter("idea");//退回原因
			}
			
			//1.插入流程记录
			String logId = JStringUtil.getUUID();
			String nowTime = JStringUtil.getNowDate(); //当前时间
			FlowLog flowLog = new FlowLog(logId, id, nowTime, userId, userName, nextUserId, nextUserName, idea, isBack);
			int i = oaMainDao.addFlowLog(flowLog);
			if(i>0){
				
				//2.插待办
				FlowWrite flowWrite = new FlowWrite(JStringUtil.getUUID(), id, logId, nowTime, nextUserId, nextUserName, userId, userName);
				i = oaMainDao.addFLowWrite(flowWrite);
				if(i>0){
					//如果刚起草提交的话，修改状态为办理
					if(Constant.SUBFLAG_CG.equals(main.getSubflag())){ 
						main.setSubflag(Constant.SUBFLAG_BLZ); //修改状态为办理中
						i = oaMainDao.update(main);
						if(i>0){
							result.put("msg", "1");
						}
					//否则	
					}else{ 
						//3.删以前的已办，新插已办
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("id", id);
						map.put("userId", userId);
						oaMainDao.delFlowRead(map);
						
						FlowRead flowRead = new FlowRead(JStringUtil.getUUID(), id, logId, nowTime, userId, userName);
						i = oaMainDao.addFlowRead(flowRead);
						
						if(i>0){
							if(!JStringUtil.isEmpty(wid)){
								//4.删除待办
								i = oaMainDao.delFlowWrite(wid);
								if(i>0){
									result.put("msg", "1");
								}
							}
						}
						
					}
				}
			}
			
			//发消息
			String msg = (String)result.get("msg");
			if("1".equals(msg)){
				String content = "您有一条新的消息，请注意查收";
				if(Constant.TYPE_APPROVE_BUY.equals(main.getType())){
					content = "收到一份采购申请单，前往“审批管理”办理";
				}else if(Constant.TYPE_APPROVE_FINANCE.equals(main.getType())){
					content = "收到一份费用报销申请单，前往“审批管理”办理";
				}else if(Constant.TYPE_VACATION.equals(main.getType())){
					content = "收到一份请假申请单，前往“假期管理”办理";
				}else if(Constant.TYPE_REPORT.equals(main.getType())){
					content = "收到一份工作报告单，前往“工作报告”查看";
				}
				MessageUtil.send(nextUserId, content);
			}
		}
		
		return result;
	}
	
	/**
	 * 办结
	 */
	public JSONObject endFLow(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user"); //获取当前用户
		String userId = user.getUserId(); //获取当前用户ID
		String userName = user.getUserName(); //获取当前用户名
		
		String id = request.getParameter("id");
		String wid = request.getParameter("wid");
		
		JSONObject result = new JSONObject();
		result.put("msg", "0");
		
		if(!JStringUtil.isEmpty(id)){
			//1.插入流程记录
			String logId = JStringUtil.getUUID();
			String nowTime = JStringUtil.getNowDate(); //当前时间
			FlowLog flowLog = new FlowLog(logId, id, nowTime, userId, userName, "", "", "", "");
			int i = oaMainDao.addFlowLog(flowLog);
			if(i>0){
				//2.删以前的已办，新插已办
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", id);
				map.put("userId", userId);
				oaMainDao.delFlowRead(map);
				
				FlowRead flowRead = new FlowRead(JStringUtil.getUUID(), id, logId, nowTime, userId, userName);
				i = oaMainDao.addFlowRead(flowRead);
				if(i>0){
					if(!JStringUtil.isEmpty(wid)){
						//3.删待办
						i = oaMainDao.delFlowWrite(wid);
						if(i>0){
							//4.修改状态为办结
							OaMain main = new OaMain();
							main.setId(id);
							main.setSubflag(Constant.SUBFLAG_BJ);
							main.setEndTime(nowTime);
							i = oaMainDao.banjie(main);
							if(i>0){
								result.put("msg", "1");
							}
						}
					}
				}
			}
			
			//发消息
			String msg = (String)result.get("msg");
			if("1".equals(msg)){
				String content = "您有一条新的消息，请注意查收";
				OaMain main = oaMainDao.select(id);
				if(Constant.TYPE_APPROVE_BUY.equals(main.getType())){
					content = "您申请的采购申请单已办结，请注意查看";
				}else if(Constant.TYPE_APPROVE_FINANCE.equals(main.getType())){
					content = "您申请的费用报销申请单已办结，请注意查看";
				}else if(Constant.TYPE_VACATION.equals(main.getType())){
					content = "您申请的请假申请单已办结，请注意查看";
				}
				MessageUtil.send(main.getCreUserId(), content);
			}
		}
		return result;
	}

	/**
	 * 审批记录
	 */
	public JSONObject getFLowLog(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		
		String id = request.getParameter("id");
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(!JStringUtil.isEmpty(id)){
			 list = oaMainDao.getFLowLog(id);
		}
		result.put("list", list);
		return result;
	}

}
