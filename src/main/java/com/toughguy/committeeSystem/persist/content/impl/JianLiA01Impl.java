package com.toughguy.committeeSystem.persist.content.impl;

import java.util.List;
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
	public JianLiA01 findExport(String ID) {		
		return sqlSessionTemplate.selectOne(typeNameSpace + ".findExport", ID);
		
	}
	
	@Override
	public JianLiA01 findJianli(String ID) {		
		return sqlSessionTemplate.selectOne(typeNameSpace + ".findJianli", ID);
		
	}

	


}
