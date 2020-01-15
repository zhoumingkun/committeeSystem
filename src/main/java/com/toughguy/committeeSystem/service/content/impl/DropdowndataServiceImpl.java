package com.toughguy.committeeSystem.service.content.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toughguy.committeeSystem.model.content.Dropdowndata;
import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.persist.content.prototype.IDropdowndataDao;
import com.toughguy.committeeSystem.persist.content.prototype.IRsUnmkDao;
import com.toughguy.committeeSystem.service.content.prototype.IDropdowndataService;
import com.toughguy.committeeSystem.service.content.prototype.IRsUnmkService;
import com.toughguy.committeeSystem.service.impl.GenericServiceImpl;

@Service
public class DropdowndataServiceImpl extends GenericServiceImpl<Dropdowndata, Integer> implements IDropdowndataService {
	
	@Autowired
	private IDropdowndataDao dropdowndataDao;
	
	@Autowired
	private IRsUnmkDao rsUnmkImpl;

	@Override
	public Dropdowndata findZFWPullData() {
		// TODO Auto-generated method stub
		Dropdowndata data = new Dropdowndata();
		Map<String,String> rsUnmkMap = new HashMap<>();
		//rsUnmkMap.put("gaj", "");		//公安局
		rsUnmkMap.put("jcy", "A49.F09.291.000");		//检察院
		rsUnmkMap.put("sfj", "A49.F09.435");		//司法局
		rsUnmkMap.put("fy", "A49.F09.281");			//法院
		List<RsUnmk> rsUnmkList = rsUnmkImpl.findZFWCode(rsUnmkMap);		//1.查询出全政法委的全部部门id
		RsUnmk r = new RsUnmk();
		r.setUID("0");
		r.setName("中国共产党太原市委员会政法委员会");
		r.setCode("A49.F09.329");
		r.setSID("-1");
		rsUnmkList.add(r);
		List<String> Department = new ArrayList<>();		//部门
		for(int i=0;i<rsUnmkList.size();i++) {
			Department.add(rsUnmkList.get(i).getName());
		}
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

	@Override
	public Dropdowndata findQTPullData(String card) {
		// TODO Auto-generated method stub
		Dropdowndata data = new Dropdowndata();
		String ss=null;
		/*if(card=="gaj" || card.equals("gaj")) {
			ss="公安局的法人编号";
		}*/
		if(card=="jcy" || card.equals("jcy")) {
			ss="A49.F09.291.000";
		}
		if(card=="fy" || card.equals("fy")) {
			ss="A49.F09.281";
		}
		if(card=="sfj" || card.equals("sfj")) {
			ss="A49.F09.435";
		}
		RsUnmk rsUnmkList = rsUnmkImpl.findQTCode(ss);		//1.查询出全政法委的全部部门id
		List<String> Department = dropdowndataDao.selectDepartment(rsUnmkList.getUID());		//部门
		Set<String> set = new HashSet<>();
		for(int i=0;i<Department.size();i++) {
			set.add(Department.get(i));
		}
		List<String> SDepartment = new ArrayList<>();
		for(String s:set) {
			SDepartment.add(s);
		}
		data.setDepartment(SDepartment);
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
