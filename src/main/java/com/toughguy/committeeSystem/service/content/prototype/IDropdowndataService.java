package com.toughguy.committeeSystem.service.content.prototype;


import com.toughguy.committeeSystem.model.content.Dropdowndata;
import com.toughguy.committeeSystem.service.prototype.IGenericService;

public interface IDropdowndataService  extends IGenericService<Dropdowndata, Integer> {
	
	public Dropdowndata selectAll();

}
