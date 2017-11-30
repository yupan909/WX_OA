package com.java.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.java.entity.OaApproveBuy;
import com.java.entity.OaApproveFinance;
import com.java.entity.Query;
@Repository
public interface OaApproveDao {
	
	/**
	 * 所有数据列表
	 * @return
	 */
	public List<Map<String,Object>> selectManagerList(Query query);
	
	/**
	 * 所有数据列表总条数
	 * @return
	 */
	public Integer selectManagerListCount(Query query);
	
	/**
	 * 待处理列表
	 * @return
	 */
	public List<Map<String,Object>> selectListDB(Query query);
	
	/**
	 * 待处理列表总条数
	 * @return
	 */
	public Integer selectListDBCount(Query query);
	
	/**
	 * 已处理列表
	 * @return
	 */
	public List<Map<String,Object>> selectListYB(Query query);
	
	/**
	 * 已处理列表总条数
	 * @return
	 */
	public Integer selectListYBCount(Query query);
	
	/**
	 * 草稿箱列表
	 * @return
	 */
	public List<Map<String,Object>> selectListCG(Query query);
	
	/**
	 * 草稿箱列表总条数
	 * @return
	 */
	public Integer selectListCGCount(Query query);
	
	/**
	 * 我申请的列表
	 * @return
	 */
	public List<Map<String,Object>> selectListMy(Query query);
	
	/**
	 * 我申请的列表总条数
	 * @return
	 */
	public Integer selectListMyCount(Query query);
	
	/**
	 * 查询采购申请单
	 * @param id
	 * @return
	 */
	public OaApproveBuy selectBuy(String id);
	
	/**
	 * 新增采购申请单
	 * @param approveBuy
	 * @return
	 */
	public int addBuy(OaApproveBuy approveBuy);
	
	/**
	 * 修改采购申请单
	 * @param approveBuy
	 * @return
	 */
	public int updateBuy(OaApproveBuy approveBuy);
	
	/**
	 * 删除采购申请单
	 * @param id
	 * @return
	 */
	public int deleteBuy(String id);
	
	
	/**
	 * 查询费用报销申请单
	 * @param id
	 * @return
	 */
	public OaApproveFinance selectFinance(String id);
	
	/**
	 * 新增费用报销申请单
	 * @param approveFinance
	 * @return
	 */
	public int addFinance(OaApproveFinance approveFinance);
	
	/**
	 * 修改费用报销申请单
	 * @param approveFinance
	 * @return
	 */
	public int updateFinance(OaApproveFinance approveFinance);
	
	/**
	 * 删除费用报销申请单
	 * @param id
	 * @return
	 */
	public int deleteFinance(String id);
	
}
