package com.toughguy.committeeSystem.persist.content.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.toughguy.committeeSystem.dto.ScreeningDTO;
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

	@Override
	public List<JianLiA01> screenA01(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".screenA01",map);
	}

	@Override
	public List<JianLiA01> screenEducationBgOne(JianLiA01 jl) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".screenEducationBgOne",jl);
	}

	@Override
	public List<JianLiA01> screenEducationBgTwo(JianLiA01 jl) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".screenEducationBgTwo",jl);
	}

	@Override
	public List<JianLiA01> screenDepartment(JianLiA01 jl) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".screenDepartment",jl);
	}

	@Override
	public List<JianLiA01> screenDuty(JianLiA01 jl) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".screenDuty",jl);
	}

	@Override
	public List<JianLiA01> screenJoinTime(JianLiA01 jl) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".screenJoinTime",jl);
	}

	@Override
	public JianLiA01 selectImgUrl(JianLiA01 jl) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(typeNameSpace + ".selectImgUrl",jl);
	}

	@Override
	public JianLiA01 selectIdList(JianLiA01 jl) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(typeNameSpace + ".selectIdList",jl);
	}

	@Override
	public List<JianLiA01> selectSexRatio(List<String> list) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectSexRatio",list);
	}

	@Override
	public List<JianLiA01> selectAGE(List<String> list) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectAGE",list);
	}

	@Override
	public List<JianLiA01> selectGradeTime(List<String> list) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectGradeTime",list);
	}

	@Override
	public List<JianLiA01> selectJobLevel(JianLiA01 jianli) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectJobLevel",jianli);
	}

	@Override
	public JianLiA01 selectAll(Map<String, String> map) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(typeNameSpace + ".selectAll",map);
	}

	@Override
	public List<JianLiA01> selectJobLevelIsNull() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectJobLevelIsNull");
	}

	@Override
	public List<JianLiA01> findQTCodeID(String userID) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".findQTCodeID",userID);
	}

	@Override
	public List<JianLiA01> findZFWCodeID(String userID) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".findZFWCodeID",userID);
	}

	@Override
	public List<JianLiA01> findUidAllList(String UID) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".findUidAllList",UID);
	}

	@Override
	public JianLiA01 selectIdData(String Aid) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(typeNameSpace + ".selectIdData",Aid);
	}


}
