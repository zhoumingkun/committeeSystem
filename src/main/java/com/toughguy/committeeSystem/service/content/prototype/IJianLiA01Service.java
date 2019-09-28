package com.toughguy.committeeSystem.service.content.prototype;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
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
	public JianLiA01 findExport(String ID);
	
	public void ExportReport(HttpServletResponse response,String[] ids);
	/**
	 * 导出大excel简历表查询方法
	 * 
	 */
	public JianLiA01 findJianli(String ID);
	
	public void ExportJianLi(HttpServletResponse response,String ID);
	
	/**
	 * 通过名字模糊查询和通过身份证完全匹配查询
	 * @param jianli
	 * @return
	 */
	public Map<String,Object> selectOne(JianLiA01 jianli);
}
