package com.toughguy.committeeSystem.persist.content.prototype;


import java.util.List;
import java.util.Map;

import com.toughguy.committeeSystem.model.content.JianLiA36;
import com.toughguy.committeeSystem.persist.prototype.IGenericDao;

/**
 * 简历表rs_a36Dao接口类
 * @author zmk
 *
 */
public interface IJianLiA36Dao extends IGenericDao<JianLiA36, Integer>{
	
	/**
	 * 根据id查询某人家庭组成情况
	 * @param id
	 * @return
	 */
	public List<JianLiA36> selectA36(String id);
}
