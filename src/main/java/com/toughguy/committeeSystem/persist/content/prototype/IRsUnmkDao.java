package com.toughguy.committeeSystem.persist.content.prototype;

import java.util.List;
import java.util.Map;

import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.persist.prototype.IGenericDao;

public interface IRsUnmkDao extends IGenericDao<RsUnmk, Integer> {
	
	//根据法人编号查询政法委的各个根目录
	public List<RsUnmk> findZFWCode(Map<String,String> map);
	
	//根据法人编号查询除政法委外的其他根目录代码
	public RsUnmk findQTCode(String code);
	
	//根据上级id查询下级id
	public List<RsUnmk> findID(RsUnmk r);
	
	//查询根id
	public List<RsUnmk> findRootID();

}
