package com.toughguy.committeeSystem.model.content;

import java.util.List;

import scala.annotation.meta.param;

public class Dropdowndata {
	private List<String> department;				//部门
	private List<String> present_duties;			//现职务
	private List<String> current_level;				//现职级
	private List<String> duty_level;				//职务层次
	private List<String> personnel_category;		//人员类别
	private List<String> management_category;		//管理类别
	private List<String> compilation_type;			//编制类型
	
	
	public Dropdowndata() {
		super();
	}


	public Dropdowndata(List<String> department, List<String> present_duties, List<String> current_level,
			List<String> duty_level, List<String> personnel_category, List<String> management_category,
			List<String> compilation_type) {
		super();
		this.department = department;
		this.present_duties = present_duties;
		this.current_level = current_level;
		this.duty_level = duty_level;
		this.personnel_category = personnel_category;
		this.management_category = management_category;
		this.compilation_type = compilation_type;
	}


	public List<String> getDepartment() {
		return department;
	}

	public void setDepartment(List<String> department) {
		this.department = department;
	}

	public List<String> getPresent_duties() {
		return present_duties;
	}

	public void setPresent_duties(List<String> present_duties) {
		this.present_duties = present_duties;
	}

	public List<String> getDuty_level() {
		return duty_level;
	}

	public void setDuty_level(List<String> duty_level) {
		this.duty_level = duty_level;
	}

	public List<String> getPersonnel_category() {
		return personnel_category;
	}

	public void setPersonnel_category(List<String> personnel_category) {
		this.personnel_category = personnel_category;
	}

	public List<String> getManagement_category() {
		return management_category;
	}

	public void setManagement_category(List<String> management_category) {
		this.management_category = management_category;
	}

	public List<String> getCompilation_type() {
		return compilation_type;
	}

	public void setCompilation_type(List<String> compilation_type) {
		this.compilation_type = compilation_type;
	}

	
	public List<String> getCurrent_level() {
		return current_level;
	}


	public void setCurrent_level(List<String> current_level) {
		this.current_level = current_level;
	}


	@Override
	public String toString() {
		return "Dropdowndata [department=" + department + ", present_duties=" + present_duties + ", current_level="
				+ current_level + ", duty_level=" + duty_level + ", personnel_category=" + personnel_category
				+ ", management_category=" + management_category + ", compilation_type=" + compilation_type + "]";
	}






}
