package com.toughguy.committeeSystem.controller.content;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.service.content.prototype.IRsUnmkService;

@RestController
@RequestMapping("/rsUnmk")
public class RsUnmkController {
	
	@Autowired
	private IRsUnmkService rsUnmkServiceImpl;
	
	/**
	 * 根据法人编号查询政法委的各个根目录
	 * @return
	 */
	@RequestMapping("/findZFWCode")
	public List<RsUnmk> findZFWCode(@RequestBody Map<String,String> map) {
		return rsUnmkServiceImpl.findZFWCode(map);
	}
	
	/**
	 * 根据法人编号查询除政法委外的其他根目录代码
	 * @param code
	 * @return
	 */
	@RequestMapping("/findQTCode")
	public RsUnmk findQTCode(String code) {
		return rsUnmkServiceImpl.findQTCode(code);
	} 
	
	/**
	 * 查询政法委的组织架构
	 * @return
	 */
	@RequestMapping("/findZFWFramework")
	public Map<String,Object> findZFWFramework(){
		return rsUnmkServiceImpl.findZFWFramework();
	}
	
	/**
	 * 查询其他地方的组织架构
	 * @return
	 */
	@RequestMapping("/findQTFramework")
	public Map<String,Object> findQTFramework(String card){
		return rsUnmkServiceImpl.findQTFramework(card);
	}
	
}