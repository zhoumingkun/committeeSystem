package com.toughguy.committeeSystem.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toughguy.committeeSystem.model.content.Dropdowndata;
import com.toughguy.committeeSystem.service.content.prototype.IDropdowndataService;

@RequestMapping("/dropdowndata")
@RestController
public class DropdowndataController {
	
	@Autowired
	private IDropdowndataService dropdowndataService;
	
	/**
	 * 查询页面部分下拉框中数据
	 * @return
	 */
	@RequestMapping("/selectAll")
	public Dropdowndata selectAll() {
		return dropdowndataService.selectAll();
	}

}
