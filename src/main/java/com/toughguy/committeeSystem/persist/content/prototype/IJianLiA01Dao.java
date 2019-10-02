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
	 * 查询全部列表信息
	 * @param jianli
	 * @return
	 */
	public List<JianLiA01> selectAllList();
	
	/**
	 * 通过用户id查询数据库返回列表信息
	 * @return
	 */
	public JianLiA01 selectIdList(JianLiA01 jl);
	
	/**
	 * 通过很多筛选条件查询用户
	 * @return
	 */
	public List<JianLiA01> screenA01(Map<String,Object> map);
	
	/**
	 * 通过第一学历筛选条件查询用户
	 * @return
	 */
	public List<JianLiA01> screenEducationBgOne(JianLiA01 jl);
	
	/**
	 * 通过第二学历筛选条件查询用户
	 * @return
	 */
	public List<JianLiA01> screenEducationBgTwo(JianLiA01 jl);
	
	/**
	 * 通过所属部门筛选条件查询用户
	 * @return
	 */
	public List<JianLiA01> screenDepartment(JianLiA01 jl);
	
	/**
	 * 通过职务筛选条件查询用户
	 * @return
	 */
	public List<JianLiA01> screenDuty(JianLiA01 jl);
	
	/**
	 * 通过入党时间筛选条件查询用户
	 * @return
	 */
	public List<JianLiA01> screenJoinTime(JianLiA01 jl);
	
	/**
	 * 查询男女性别数量
	 * @return
	 */
	public List<JianLiA01> selectSexRatio();
	
	/**
	 * 查询年龄
	 * @return
	 */
	public List<JianLiA01> selectAGE();
	
	/**
	 * 查询职务层次
	 * @return
	 */
	public List<JianLiA01> selectGradeTime();

}
