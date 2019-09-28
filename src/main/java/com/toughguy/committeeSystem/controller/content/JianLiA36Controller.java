package com.toughguy.committeeSystem.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.toughguy.committeeSystem.service.content.prototype.IJianLiA36Service;

@RequestMapping("/jianliA36")
@RestController
public class JianLiA36Controller {
	
	@Autowired
	private IJianLiA36Service JianLiA36Service;
	
	

}
