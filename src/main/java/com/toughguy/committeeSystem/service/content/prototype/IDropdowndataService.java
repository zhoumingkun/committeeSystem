package com.toughguy.committeeSystem.service.content.prototype;


import com.toughguy.committeeSystem.model.content.Dropdowndata;
import com.toughguy.committeeSystem.service.prototype.IGenericService;

public interface IDropdowndataService  extends IGenericService<Dropdowndata, Integer> {
	
	//查询政法委页面部分下拉框中数据
	public Dropdowndata findZFWPullData();

	//查询其他机构页面部分下拉框中数据
	public Dropdowndata findQTPullData(String card);

}
