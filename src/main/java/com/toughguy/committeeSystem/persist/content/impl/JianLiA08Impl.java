package com.toughguy.committeeSystem.persist.content.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.toughguy.committeeSystem.model.content.JianLiA08;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA08Dao;
import com.toughguy.committeeSystem.persist.impl.GenericDaoImpl;
/**
 * 简历表rs_a08 Dao实现类
 * @author zmk
 *
 */
@Repository
public class JianLiA08Impl extends GenericDaoImpl<JianLiA08, Integer> implements IJianLiA08Dao {

	@Override
	public List<JianLiA08> selectQRZ(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectQRZ", id);
	}
	
	@Override
	public List<JianLiA08> selectZZ(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectZZ", id);
	}

	@Override
	public List<JianLiA08> selectQRZYX(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectQRZYX", id);
	}

	@Override
	public List<JianLiA08> selectZZYX(String id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectZZYX", id);
	}


}
