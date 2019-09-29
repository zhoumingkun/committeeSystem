package com.toughguy.committeeSystem.persist.content.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.toughguy.committeeSystem.model.content.JianLiA01;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA01Dao;
import com.toughguy.committeeSystem.persist.impl.GenericDaoImpl;
/**
 * 简历表rs_a01 Dao实现类
 * @author zmk
 *
 */
@Repository
public class JianLiA01Impl extends GenericDaoImpl<JianLiA01, Integer> implements IJianLiA01Dao {
	@Override
	public JianLiA01 findExport(String id) {		
		return sqlSessionTemplate.selectOne(typeNameSpace + ".findExport", id);
		
	}
	
	@Override
	public JianLiA01 findJianli(String id) {		
		return sqlSessionTemplate.selectOne(typeNameSpace + ".findJianli", id);
		
	}


	@Override
	public List<JianLiA01> selectA01(Map<String, String> map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectA01", map);
	}

	@Override
	public List<JianLiA01> selectList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectList", map);
	}
	@Override
	public List<JianLiA01> selectAllList() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectAllList");
	}


}
