package com.java.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.java.entity.FlowLog;
import com.java.entity.FlowRead;
import com.java.entity.FlowWrite;
import com.java.entity.OaMain;
@Repository
public interface OaMainDao {
	
	public OaMain select(String id);
	
	public int add(OaMain main);
	
	public int delete(String id);
	
	public int update(OaMain main);
	
	public int updateTitle(OaMain main);
	
	public int banjie(OaMain main);
	
	public int addFLowWrite(FlowWrite flowWrite);
	
	public int delFlowWrite(String id);
	
	public int addFlowRead(FlowRead flowRead);
	
	public int delFlowRead(Map<String,Object> map);
	
	public int addFlowLog(FlowLog flowLog);

	public List<Map<String,Object>> getFLowLog(String id);
}
