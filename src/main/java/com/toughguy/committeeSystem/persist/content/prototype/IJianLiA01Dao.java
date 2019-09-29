package com.toughguy.committeeSystem.persist.content.prototype;


import java.util.List;
import java.util.Map;
import com.toughguy.committeeSystem.model.content.JianLiA01;
import com.toughguy.committeeSystem.persist.prototype.IGenericDao;

/**
 * 简历表rs_a01Dao接口类
 * @author zmk
 *
 */
public interface IJianLiA01Dao extends IGenericDao<JianLiA01, Integer>{
	/**
	 * 导出小excel表查询方法
	 * 
	 */
	public JianLiA01 findExport(String id);
	
	/**
	 * 导出大excel简历表查询方法
	 * 
	 */
	public JianLiA01 findJianli(String id);
	
	/**
	 * 通过名字模糊查询和通过身份证完全匹配查询
	 * @param jianli
	 * @return
	 */
	public List<JianLiA01> selectA01(Map<String,String> map);
	
	/**
	 * 通过名字和身份证列表信息
	 * @param jianli
	 * @return
	 */
	public List<JianLiA01> selectList(Map<String,String> map);
	/**
	 * 通过名字和身份证列表信息
	 * @param jianli
	 * @return
	 */
	public List<JianLiA01> selectAllList();

}
