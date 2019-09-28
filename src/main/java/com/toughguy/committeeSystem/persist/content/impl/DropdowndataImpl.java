package com.toughguy.committeeSystem.persist.content.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.toughguy.committeeSystem.model.content.Dropdowndata;
import com.toughguy.committeeSystem.persist.content.prototype.IDropdowndataDao;
import com.toughguy.committeeSystem.persist.impl.GenericDaoImpl;

@Repository
public class DropdowndataImpl extends GenericDaoImpl<Dropdowndata, Integer> implements IDropdowndataDao {

	@Override
	public List<String> selectDepartment() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectDepartment");
	}

	@Override
	public List<String> selectDutyLevel() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectDutyLevel");
	}

	@Override
	public List<String> selectPersonnelCategory() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectPersonnelCategory");
	}

	@Override
	public List<String> selectManagementCategory() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectManagementCategory");
	}

	@Override
	public List<String> selectCompilationType() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectCompilationType");
	}

	@Override
	public List<String> selectCurrentLevel() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(typeNameSpace + ".selectCurrentLevel");
	}


}
