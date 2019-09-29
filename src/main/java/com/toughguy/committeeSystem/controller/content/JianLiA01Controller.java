package com.toughguy.committeeSystem.controller.content;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.toughguy.committeeSystem.model.content.JianLiA01;
import com.toughguy.committeeSystem.service.content.prototype.IJianLiA01Service;

@RequestMapping("/jianliA01")
@RestController
public class JianLiA01Controller {
	
	@Autowired
	private IJianLiA01Service JianLiA01Service;
	
	//导出人事报表
	@ResponseBody	
	@RequestMapping(value = "/ExportReport")
//	@RequiresPermissions("jianliA01:ExportReport")
	public String ExportReport(HttpServletResponse response,String[] ids) {
		try {
			JianLiA01Service.ExportReport(response, ids);
			return "{ \"success\" : true }";
		} catch (Exception e) {
			e.printStackTrace();
			return "{ \"success\" : false }";
		}
	}
	
	
	//导出简历报表
		@ResponseBody	
		@RequestMapping(value = "/ExportJianLi")
//		@RequiresPermissions("jianliA01:ExportReport")
		public String ExportJianLi(HttpServletResponse response,String id) {
			try {
				JianLiA01Service.ExportJianLi(response, id);
				return "{ \"success\" : true }";
			} catch (Exception e) {
				e.printStackTrace();
				return "{ \"success\" : false }";
			}
		}
	
		/**
		 * 根据名字  身份证 查询用户信息
		 * @param jianli
		 * @return
		 */
		@RequestMapping("/findNameAndCard")
		public List<Map<String,Object>> selectOne(JianLiA01 jianli) {
			
			return JianLiA01Service.selectOne(jianli);
		}
	

		@RequestMapping("/selectList")
		public List<JianLiA01> selectList(JianLiA01 jianli){
			return JianLiA01Service.selectList(jianli);
		}
		
		
		
}
