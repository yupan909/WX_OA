package com.java.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.java.entity.OaMoney;
import com.java.entity.OaMoneyDetail;
import com.java.entity.Query;
@Repository
public interface OaMoneyDao {
	
	/**
	 * 所有数据列表 --工资条
	 * @return
	 */
	public List<Map<String,Object>> selectManagerList(Query query);
	
	/**
	 * 所有数据列表总条数 --工资条
	 * @return
	 */
	public Integer selectManagerListCount(Query query);
	
	/**
	 * 所有数据列表 --工资条明细
	 * @return
	 */
	public List<Map<String,Object>> selectManagerDetailList(Query query);
	
	/**
	 * 所有数据列表总条数 --工资条明细
	 * @return
	 */
	public Integer selectManagerDetailListCount(Query query);
	
	/**
	 * 个人工资条列表
	 * @return
	 */
	public List<Map<String,Object>> selectList(String userId);
	
	/**
	 * 查询工资条明细
	 * @param id
	 * @return
	 */
	public OaMoneyDetail selectMoneyDetail(String id);
	
	/**
	 * 新增工资条明细
	 * @return
	 */
	public int addMoneyDetail(OaMoneyDetail moneyDetail);
	
	/**
	 * 新增工资条
	 * @return
	 */
	public int addMoney(OaMoney money);
	
	/**
	 * 修改工资条明细
	 * @return
	 */
	public int updateMoneyDetail(OaMoneyDetail moneyDetail);
	
	/**
	 * 删除工资条
	 * @param id
	 * @return
	 */
	public int deleteMoney(String id);
	
	/**
	 * 删除工资条下所有的明细
	 * @param id
	 * @return
	 */
	public int deleteMoneyDetailAll(String recordId);
	
	/**
	 * 删除工资条明细
	 * @param id
	 * @return
	 */
	public int deleteMoneyDetail(String id);
}
