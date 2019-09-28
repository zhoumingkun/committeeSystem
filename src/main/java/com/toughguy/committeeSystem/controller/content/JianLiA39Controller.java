package com.toughguy.committeeSystem.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.toughguy.committeeSystem.service.content.prototype.IJianLiA39Service;

@RequestMapping("/jianliA39")
@RestController
public class JianLiA39Controller {
	
	@Autowired
	private IJianLiA39Service JianLiA39Service;
	
	

}
