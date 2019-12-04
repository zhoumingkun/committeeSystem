package com.toughguy.committeeSystem.service.content.prototype;



import java.util.List;
import java.util.Map;

import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.service.prototype.IGenericService;

public interface IRsUnmkService extends IGenericService<RsUnmk, Integer> {
	
	//根据法人编号查询政法委的各个根目录
	public List<RsUnmk> findZFWCode(Map<String,String> map);
	
	//根据法人编号查询除政法委外的其他根目录代码
	public RsUnmk findQTCode(String code);
	
	//查询政法委的组织架构
	public Map<String,Object> findZFWFramework();
	
	//查询其他地方的组织架构
	public Map<String,Object> findQTFramework(String card);

}
