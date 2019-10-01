package com.toughguy.committeeSystem.persist.content.prototype;


import java.util.List;
import java.util.Map;
import com.toughguy.committeeSystem.model.content.JianLiA08;
import com.toughguy.committeeSystem.persist.prototype.IGenericDao;

/**
 * 简历表rs_a08Dao接口类
 * @author zmk
 *
 */
public interface IJianLiA08Dao extends IGenericDao<JianLiA08, Integer>{
	
	/**
	 * 根据id查询某人的全日制教育和在职教育情况
	 * @param id
	 * @return
	 */
	public List<JianLiA08> selectQRZ(String id);
	public List<JianLiA08> selectZZ(String id);
	
}
