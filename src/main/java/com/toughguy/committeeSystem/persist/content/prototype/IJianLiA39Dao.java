package com.toughguy.committeeSystem.persist.content.prototype;


import java.util.List;
import java.util.Map;
import com.toughguy.committeeSystem.model.content.JianLiA39;
import com.toughguy.committeeSystem.persist.prototype.IGenericDao;

/**
 * 简历表rs_a39Dao接口类
 * @author zmk
 *
 */
public interface IJianLiA39Dao extends IGenericDao<JianLiA39, Integer>{
	
	/**
	 * 根据id查询某人的入党时间
	 * @param id
	 * @return
	 */
	public JianLiA39 selectA39(String id);
	
}
