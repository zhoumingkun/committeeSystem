package com.toughguy.committeeSystem.persist.content.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.persist.content.prototype.IRsUnmkDao;
import com.toughguy.committeeSystem.persist.impl.GenericDaoImpl;

@Repository
public class RsUnmkImpl extends GenericDaoImpl<RsUnmk, Integer> implements IRsUnmkDao{
	
	@Value("${Gwy.jcy}")
	private String Gwyjcy;
	
	@Value("${Gwy.sfj}")
	private String Gwysfj;
	
	@Value("${Gwy.fy}")
	private String Gwyfy;
	
	@Value("${Gwy.zfw}")
	private String Gwyzfw;
	
	@Value("${Gwy.gaj}")
	private String Gwygaj;

	@Override
	public List<RsUnmk> findZFWCode(Map<String, String> map) {
		// TODO Auto-generated method stub
		List<RsUnmk> list = sqlSessionTemplate.selectList(typeNameSpace + ".findZFWCode", map);
		for(int i =0;i<list.size();i++) {
			if(list.get(i).getCode()!=null) {
				if(list.get(i).getCode().equals(Gwyjcy) || list.get(i).getCode().equals(Gwysfj) || list.get(i).getCode().equals(Gwyfy)|| list.get(i).getCode().equals(Gwygaj)) {		//公安有了代码  这需要加判断
					list.remove(i);
					i=-1;
				}
			}
			
		}
		return list;
	}

	@Override
	public RsUnmk findQTCode(String code) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(typeNameSpace + ".findQTCode", code);
	}

	@Override
	public List<RsUnmk> findID(RsUnmk r) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".findID", r);
	}

	@Override
	public List<RsUnmk> findRootID() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".findRootID");
	}

}
