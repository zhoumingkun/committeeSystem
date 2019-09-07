package com.toughguy.alarmSystem.service.content.prototype;
import java.util.List;
import java.util.Map;

import com.toughguy.alarmSystem.model.content.Baojingqingkuang;
import com.toughguy.alarmSystem.model.content.Saoheichue;
import com.toughguy.alarmSystem.service.prototype.IGenericService;

/**
 * 报警情况Service层接口类
 * @author zmk
 *
 */
public interface ISaoheichueService extends IGenericService<Saoheichue, Integer>{
	
	
	/**
	 * 省厅扫黑除恶柱状图
	 * @return
	 */
	public Map<String ,Saoheichue> findAllSH();

	/**
	 * 地级市扫黑除恶柱状图
	 * @return
	 */
	public Map<String ,Saoheichue> findOneSH(String xzqh);
	
	/**
	 * 省厅扫黑除恶表数据
	 * @return
	 */
	public List<Saoheichue> selectAll(String time);

	/**
	 * 地级市扫黑除表数据
	 * @return
	 */
	public Saoheichue selectOne(String xzqh,String time);
	
	/**
	 * 省厅扫黑除恶统计列表
	 * @return
	 */
	public  Map<String,Object> selectList(String time,String xzqh);
	
	/**
	 * 地级市扫黑除恶查询是否添加过数据
	 * @return
	 */
	public  List<Saoheichue> findOne(Map<String,String> map);
	
	/**
	 * 地级市扫黑除恶修改数据
	 * @return
	 */
	public void updateAll(Saoheichue saoheichue);
}