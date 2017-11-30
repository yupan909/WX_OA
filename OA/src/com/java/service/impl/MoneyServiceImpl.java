package com.java.service.impl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.sf.json.JSONObject;
import com.java.common.Constant;
import com.java.common.util.JStringUtil;
import com.java.common.util.MessageUtil;
import com.java.dao.OaMoneyDao;
import com.java.entity.OaMoney;
import com.java.entity.OaMoneyDetail;
import com.java.entity.Query;
import com.java.entity.User;
import com.java.service.MoneyService;
@Service
@Transactional
public class MoneyServiceImpl implements MoneyService {

	@Resource
	private OaMoneyDao oaMoneyDao;

	/**
	 * 回显管理中心数据列表--工资条
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
		
		List<Map<String,Object>> list = oaMoneyDao.selectManagerList(query);
		Integer count = oaMoneyDao.selectManagerListCount(query);
		result.put("list",list);
		result.put("count",count);
		
		//sesion中移除query
		if(null!=(Query)session.getAttribute("query")){
			session.removeAttribute("query");
		}
		return result;
	}
	
	/**
	 * 回显管理中心数据列表--工资条明细
	 * @throws Exception 
	 */
	public JSONObject managerDetailList(HttpServletRequest request, Query query) throws Exception {
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
		
		List<Map<String,Object>> list = oaMoneyDao.selectManagerDetailList(query);
		Integer count = oaMoneyDao.selectManagerDetailListCount(query);
		result.put("list",list);
		result.put("count",count);
		
		//sesion中移除query
		if(null!=(Query)session.getAttribute("query")){
			session.removeAttribute("query");
		}
		return result;
	}
	
	/**
	 * 新增工资条
	 */
	public JSONObject managerSave(OaMoney money, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		//保存工资条
		String id = JStringUtil.getUUID(); //工资条id
		money.setId(id);
		money.setCreateTime(JStringUtil.getNowDate());
		int i = oaMoneyDao.addMoney(money);
		if(i>0){
			String toUser = ""; //发送人
			//读取导入的工资条数据
			String filePath = request.getParameter("filePath"); //文件路径
			List<OaMoneyDetail> list = this.getMoneyData(filePath, id);
			for(OaMoneyDetail moneyDetail : list){
				oaMoneyDao.addMoneyDetail(moneyDetail); //保存工资条明细
				toUser += moneyDetail.getEmpId() + "|";
			}
			
			if(!JStringUtil.isEmpty(toUser)){
				toUser = toUser.substring(0, toUser.length()-1);
				//发消息
				MessageUtil.send(toUser, "发工资啦，请前往“个人中心->工资条”查看");
			}
			
			result.put("msg", "1"); //保存成功
		}else{
			result.put("msg", "0"); //保存失败
		}
		return result;
	}

	/**
	 * 读取指定Excel文件的内容，封装到List中
	 * @param filePath
	 * @param recordId 工资条ID
	 * @return
	 */
	public List<OaMoneyDetail> getMoneyData(String filePath, String recordId){
		List<OaMoneyDetail> list = new ArrayList<OaMoneyDetail>();
		try {
			filePath = filePath.replaceAll("\\\\", "//");
			File file = new File(filePath);
			Workbook book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);// 获得第一个工作表对象
			int rows = sheet.getRows();// 获得工作表的行数
			if (rows != 0) {
				for (int i = 1; i < rows; i++) { //从第二行开始读取
					Cell[] cell = sheet.getRow(i);  //获取当前行的所有列集合
					if (cell.length != 0) {
						OaMoneyDetail moneyDetail = new OaMoneyDetail();
						moneyDetail.setId(JStringUtil.getUUID());
						moneyDetail.setRecordId(recordId);
						moneyDetail.setEmpId(sheet.getCell(3, i).getContents().replace(" ", "")); //员工账号
						moneyDetail.setEmpName(sheet.getCell(1, i).getContents().replace(" ", "")); //员工姓名
						moneyDetail.setEmpDeptName(sheet.getCell(2, i).getContents().replace(" ", "")); //部门
						moneyDetail.setMoneyBase(sheet.getCell(4, i).getContents().replace(" ", "")); //基本工资
						moneyDetail.setMoneyJx(sheet.getCell(5, i).getContents().replace(" ", "")); //绩效
						moneyDetail.setMoneyJj(sheet.getCell(6, i).getContents().replace(" ", "")); //奖金
						moneyDetail.setMoneyCb(sheet.getCell(7, i).getContents().replace(" ", "")); //餐补
						moneyDetail.setMoneyBx(sheet.getCell(8, i).getContents().replace(" ", "")); //五险一金
						moneyDetail.setMoneyAll(sheet.getCell(9, i).getContents().replace(" ", "")); //合计
						list.add(moneyDetail);
					}
				}	
			}
			file.delete(); //删除文件
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 删除工资条
	 */
	public JSONObject managerDelete(HttpServletRequest request) {
		String id = request.getParameter("id");
		JSONObject result = new JSONObject();
		
		if(!JStringUtil.isEmpty(id)){
			int i = oaMoneyDao.deleteMoneyDetailAll(id); //删除工资条下的所有明细
			int j = oaMoneyDao.deleteMoney(id); //删除工资条
			if(i>0 && j>0){
				result.put("msg", "1");
			}else{
				result.put("msg", "0");
			}
		}else{
			result.put("msg", "0");
		}
		return result;
	}

	/**
	 * 回显个人工资条列表
	 */
	public JSONObject editList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user"); //获取当前用户
		String userId = user.getUserId(); //获取当前用户ID
		
		JSONObject result = new JSONObject();
		List<Map<String,Object>> list = oaMoneyDao.selectList(userId);
		result.put("list",list);
		return result;
	}

	/**
	 * 回显个人工资条详情
	 */
	public OaMoneyDetail edit(HttpServletRequest request) {
		String id = request.getParameter("id");
		OaMoneyDetail moneyDetail = new OaMoneyDetail();
		if(!JStringUtil.isEmpty(id)){
			moneyDetail = oaMoneyDao.selectMoneyDetail(id);
		}
		return moneyDetail;
	}
}
