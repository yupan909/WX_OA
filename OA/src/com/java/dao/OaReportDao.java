package com.java.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.java.entity.OaReport;
import com.java.entity.Query;
@Repository
public interface OaReportDao {
	
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
	 * 我的报告
	 * @return
	 */
	public List<Map<String,Object>> selectListMy(String userId);
	
	/**
	 * 报告给我
	 * @return
	 */
	public List<Map<String,Object>> selectListDYB(String userId);
	
	/**
	 * 草稿箱列表
	 * @return
	 */
	public List<Map<String,Object>> selectListCG(String userId);
	
	
	/**
	 * 查询申请单
	 * @param id
	 * @return
	 */
	public OaReport select(String id);
	
	/**
	 * 新增申请单
	 * @param report
	 * @return
	 */
	public int add(OaReport report);
	
	/**
	 * 修改申请单
	 * @param report
	 * @return
	 */
	public int update(OaReport report);
	
	/**
	 * 删除申请单
	 * @param id
	 * @return
	 */
	public int delete(String id);
}
