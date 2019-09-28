package com.toughguy.committeeSystem.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.toughguy.committeeSystem.service.content.prototype.IJianLiA08Service;

@RequestMapping("/jianliA08")
@RestController
public class JianLiA08Controller {
	
	@Autowired
	private IJianLiA08Service JianLiA08Service;
	
	

}
