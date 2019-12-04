package com.toughguy.committeeSystem.persist.content.impl;

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
		return sqlSessionTemplate.selectList(typeNameSpace + ".findZFWCode", map);
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
