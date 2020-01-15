package com.toughguy.committeeSystem.persist.content.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.persist.content.prototype.IRsUnmkDao;
import com.toughguy.committeeSystem.persist.impl.GenericDaoImpl;

@Repository
public class RsUnmkImpl extends GenericDaoImpl<RsUnmk, Integer> implements IRsUnmkDao{

	@Override
	public List<RsUnmk> findZFWCode(Map<String, String> map) {
		// TODO Auto-generated method stub
		List<RsUnmk> list = sqlSessionTemplate.selectList(typeNameSpace + ".findZFWCode", map);
		for(int i =0;i<list.size();i++) {
			if(list.get(i).getCode()!=null) {
				if(list.get(i).getCode().equals("A49.F09.291.000") || list.get(i).getCode().equals("A49.F09.435") || list.get(i).getCode().equals("A49.F09.281")) {		//公安有了代码  这需要加判断
					list.remove(i);
					System.out.println("11111111111111111++++"+i+"+++"+list+i);
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
