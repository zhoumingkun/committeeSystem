package com.toughguy.committeeSystem.service.content.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toughguy.committeeSystem.model.content.Dropdowndata;
import com.toughguy.committeeSystem.persist.content.prototype.IDropdowndataDao;
import com.toughguy.committeeSystem.service.content.prototype.IDropdowndataService;
import com.toughguy.committeeSystem.service.impl.GenericServiceImpl;

@Service
public class DropdowndataServiceImpl extends GenericServiceImpl<Dropdowndata, Integer> implements IDropdowndataService {
	
	@Autowired
	private IDropdowndataDao dropdowndataDao;

	@Override
	public Dropdowndata selectAll() {
		// TODO Auto-generated method stub
		Dropdowndata data = new Dropdowndata();
		List<String> Department = dropdowndataDao.selectDepartment();		//部门
		data.setDepartment(Department);
		List<String> list = new ArrayList<String>();						//现职务
		list.add("书记");
		list.add("处长");
		list.add("主任");
		list.add("科长");
		list.add("科员");
		list.add("调研员");
		list.add("巡视员");
		list.add("干部");
		data.setPresent_duties(list);
		List<String> DutyLevel = dropdowndataDao.selectDutyLevel();		//职务层次
		data.setDuty_level(DutyLevel);
		List<String> personnelCategory = dropdowndataDao.selectPersonnelCategory();			//人员类别
		data.setPersonnel_category(personnelCategory);
		List<String> managementCategory = dropdowndataDao.selectManagementCategory();
		data.setManagement_category(managementCategory); 							//管理类别
		List<String> compilationType = dropdowndataDao.selectCompilationType();		//编制类型
		data.setCompilation_type(compilationType);
		List<String> currentLevel = dropdowndataDao.selectCurrentLevel();
		data.setCurrent_level(currentLevel);
		return data;
	}

}
