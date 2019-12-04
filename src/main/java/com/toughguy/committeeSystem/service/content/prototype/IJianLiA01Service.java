package com.toughguy.committeeSystem.service.content.prototype;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.toughguy.committeeSystem.dto.ScreeningDTO;
import com.toughguy.committeeSystem.model.content.JianLiA01;
import com.toughguy.committeeSystem.service.prototype.IGenericService;

/**
 * 简历表rs_a01Service层接口类
 * @author zmk
 *
 */
public interface IJianLiA01Service extends IGenericService<JianLiA01, Integer>{
	/**
	 * 导出小excel表查询查询方法
	 * 
	 */
	public JianLiA01 findExport(String id);
	
	public void ExportReport(HttpServletResponse response,String ids);
	/**
	 * 导出大excel简历表查询方法
	 * 
	 */
	public JianLiA01 findJianli(String id);
	
	public void ExportJianLi(HttpServletResponse response,String id);
	
	/**
	 * 通过名字模糊查询和通过身份证完全匹配查询
	 * @param jianli
	 * @return
	 */
	public List<Map<String,Object>> selectOne(JianLiA01 jianli);
	
	/**
	 * 通过姓名身份证查询列表信息
	 * @param jianli
	 * @return
	 */
	public List<JianLiA01> selectList(JianLiA01 jianli);
	
	/**
	 * 通过姓名身份证查询全部列表信息
	 * @param jianli
	 * @return
	 */
	public List<JianLiA01> selectAllList();
	
	/**
	 * 通过很多筛选条件查询政法委用户
	 * @return
	 */
	public List<JianLiA01> screenZFWList(ScreeningDTO sc);
	
	
	/**
	 * 通过很多筛选条件查询除政法委外的用户
	 * @return
	 */
	public List<JianLiA01> screenQTList(ScreeningDTO sc);
	
	
	/**
	 * 政法委数据分析
	 * @return
	 */
	public Map<String,Object> ZFWdataAnalysis();
	
	
	/**
	 * 除政法委的其他数据分析
	 * @return
	 */
	public Map<String,Object> QTdataAnalysis(String card);
	
	
	/**
	 * 根据政法委部门id查询人员id
	 * @param code
	 * @return
	 */
	public List<JianLiA01> findZFWCodeID(String userID);
	
	/**
	 * 根据除政法委外部门id查询人员id
	 * @param code
	 * @return
	 */
	public List<JianLiA01> findQTCodeID(String userID);
	
	/**
	 * 通过用户id查询数据库返回列表信息
	 * @return
	 */
	public JianLiA01 selectIdList(JianLiA01 jl);

	/**
	 * 根据组织架构id查询列表
	 * @param uID
	 * @return
	 */
	public List<JianLiA01> findUidAllList(String UID);

	/**
	 * 查询首页数据统计
	 * @return
	 */
	public Map<String, Object> findIndexData();
}
