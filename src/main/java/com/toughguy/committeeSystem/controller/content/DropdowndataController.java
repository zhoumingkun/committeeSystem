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
	 * 查询政法委页面部分下拉框中数据
	 * @return
	 */
	@RequestMapping("/findZFWPullData")
	public Dropdowndata findZFWPullData() {
		return dropdowndataService.findZFWPullData();
	}
	
	
	/**
	 * 查询其他机构页面部分下拉框中数据
	 * @return
	 */
	@RequestMapping("/findQTPullData")
	public Dropdowndata findQTPullData(String card) {
		return dropdowndataService.findQTPullData(card);
	}

}
