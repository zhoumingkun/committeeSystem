package com.toughguy.committeeSystem.persist.content.prototype;


import java.util.List;
import java.util.Map;

import com.toughguy.committeeSystem.dto.ScreeningDTO;
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
	 * 通过用户id查询该用户图片存储路径
	 * @param jianli
	 * @return
	 */
	public JianLiA01 selectImgUrl(JianLiA01 jianli);
	
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
	
	/**
	 * 通过很多筛选条件查询用户
	 * @return
	 */
	public List<JianLiA01> screenA01(Map<String,Object> map);
	
	/**
	 * 通过很多筛选条件查询用户
	 * @return
	 */
	public JianLiA01 screenA02(JianLiA01 jl);
	
	/**
	 * 通过很多筛选条件查询用户
	 * @return
	 */
	public JianLiA01 screenA03(JianLiA01 jl);
	
	/**
	 * 通过很多筛选条件查询用户
	 * @return
	 */
	public JianLiA01 screenA04(JianLiA01 jl);
	
	/**
	 * 通过很多筛选条件查询用户
	 * @return
	 */
	public JianLiA01 screenA05(JianLiA01 jl);
	
	/**
	 * 通过很多筛选条件查询用户
	 * @return
	 */
	public JianLiA01 screenA06(JianLiA01 jl);

}
