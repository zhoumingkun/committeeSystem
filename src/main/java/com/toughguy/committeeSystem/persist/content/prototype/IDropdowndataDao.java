package com.toughguy.committeeSystem.persist.content.prototype;

import java.util.List;

import com.toughguy.committeeSystem.model.content.Dropdowndata;
import com.toughguy.committeeSystem.persist.prototype.IGenericDao;

public interface IDropdowndataDao extends IGenericDao<Dropdowndata, Integer> {
	
	/**
	 * 查询部门详细信息
	 * @return
	 */
	public List<String> selectDepartment(String card);
	
	/**
	 * 查询职务层次详细信息
	 * @return
	 */
	public List<String> selectDutyLevel();
	
	/**
	 * 查询人员类别详细信息
	 * @return
	 */
	public List<String> selectPersonnelCategory();
	
	/**
	 * 查询管理类别详细信息
	 * @return
	 */
	public List<String> selectManagementCategory();
	
	/**
	 * 查询编制类型详细信息
	 * @return
	 */
	public List<String> selectCompilationType();
	
	/**
	 * 查询现职级详细信息
	 * @return
	 */
	public List<String> selectCurrentLevel();
	
}
