package com.java.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.java.entity.OaNotice;
import com.java.entity.Query;
@Repository
public interface OaNoticeDao {
	
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
	 * 公告列表
	 * @return
	 */
	public List<Map<String,Object>> selectList();
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public OaNotice select(String id);
	
	/**
	 * 新增
	 * @return
	 */
	public int add(OaNotice notice);
	
	/**
	 * 修改
	 * @return
	 */
	public int update(OaNotice notice);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(String id);
}
