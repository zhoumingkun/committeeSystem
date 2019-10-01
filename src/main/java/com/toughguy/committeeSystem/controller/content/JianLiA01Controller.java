package com.toughguy.committeeSystem.controller.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.toughguy.committeeSystem.dto.ScreeningDTO;
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
	public String ExportReport(HttpServletResponse response,String ids) {
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
		 * 根据用户id 查询用户信息
		 * @param jianli
		 * @return
		 */
		@RequestMapping("/findNameAndCard")
		public List<Map<String,Object>> selectOne(JianLiA01 jianli) {
			
			return JianLiA01Service.selectOne(jianli);
		}
	
		/**
		 * 根据名字和身份证查询列表
		 * @param name
		 * @param idCard
		 * @param NowPage
		 * @param Nums
		 * @return
		 */
		@RequestMapping("/selectList")
		public Map<String,Object> selectList(String name,String idCard,String NowPage,String Nums){
			JianLiA01 jl = new JianLiA01();
			jl.setName(name);
			jl.setIdCard(idCard);
			List<JianLiA01> selectList = JianLiA01Service.selectList(jl);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("total", selectList.size());
			if(selectList.size()<=9) {
				map.put("rows",selectList);
				return map;
			}
			int i =(Integer.parseInt(NowPage)*9)-1;
			if(i>(selectList.size()-1)) {			
				int s=i-8;
				List<JianLiA01> list = new ArrayList<JianLiA01>();
				for(int g =s;g<selectList.size();g++) {
					list.add(selectList.get(g));
				}
				map.put("rows",list);
				return map;
			}
			if(i<(selectList.size()-1)) {
				int s=i-8;
				List<JianLiA01> list = new ArrayList<JianLiA01>();
				for(int g =s;g<=i;g++) {
					list.add(selectList.get(g));
				}
				map.put("rows",list);
				return map;
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			 return obj;
		}
		
		/**
		 * 根据名字和身份证查询列表
		 * @param name
		 * @param idCard
		 * @param NowPage
		 * @param Nums
		 * @return
		 */
		@RequestMapping("/selectAllList")
		public Map<String,Object> selectAllList(String NowPage,String Nums){
			System.out.println(NowPage+""+Nums);
			List<JianLiA01> selectList = JianLiA01Service.selectAllList();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("total", selectList.size());
			if(selectList.size()<=9) {
				map.put("rows",selectList);
				return map;
			}
			int i =(Integer.parseInt(NowPage)*9)-1;
			if(i>(selectList.size()-1)) {			
				int s=i-8;
				List<JianLiA01> list = new ArrayList<JianLiA01>();
				for(int g =s;g<selectList.size();g++) {
					list.add(selectList.get(g));
				}
				map.put("rows",list);
				return map;
			}
			if(i<(selectList.size()-1)) {
				int s=i-8;
				List<JianLiA01> list = new ArrayList<JianLiA01>();
				for(int g =s;g<=i;g++) {
					list.add(selectList.get(g));
				}
				map.put("rows",list);
				return map;
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			return obj;
		}
		
		
		@RequestMapping("/screenList")
		public Map<String,Object> screenList(ScreeningDTO sc){
			
			List<JianLiA01> selectList = JianLiA01Service.screenList(sc);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("total", selectList.size());
			if(selectList.size()<=9) {
				map.put("rows",selectList);
				return map;
			}
			int i =(Integer.parseInt(sc.getNowPage())*9)-1;
			if(i>(selectList.size()-1)) {			
				int s=i-8;
				List<JianLiA01> list = new ArrayList<JianLiA01>();
				for(int g =s;g<selectList.size();g++) {
					list.add(selectList.get(g));
				}
				map.put("rows",list);
				return map;
			}
			if(i<(selectList.size()-1)) {
				int s=i-8;
				List<JianLiA01> list = new ArrayList<JianLiA01>();
				for(int g =s;g<=i;g++) {
					list.add(selectList.get(g));
				}
				map.put("rows",list);
				return map;
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			return obj;
		}
}