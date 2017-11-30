package com.java.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.java.entity.OaVacation;
import com.java.entity.Query;
@Repository
public interface OaVacationDao {
	
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
	public List<Map<String,Object>> selectListDB(String userId);
	
	/**
	 * 已处理列表
	 * @return
	 */
	public List<Map<String,Object>> selectListYB(String userId);
	
	/**
	 * 草稿箱列表
	 * @return
	 */
	public List<Map<String,Object>> selectListCG(String userId);
	
	/**
	 * 我申请的列表
	 * @return
	 */
	public List<Map<String,Object>> selectListMy(String userId);
	
	/**
	 * 查询申请单
	 * @param id
	 * @return
	 */
	public OaVacation select(String id);
	
	/**
	 * 新增申请单
	 * @param vacation
	 * @return
	 */
	public int add(OaVacation vacation);
	
	/**
	 * 修改申请单
	 * @param vacation
	 * @return
	 */
	public int update(OaVacation vacation);
	
	/**
	 * 删除申请单
	 * @param id
	 * @return
	 */
	public int delete(String id);
}
