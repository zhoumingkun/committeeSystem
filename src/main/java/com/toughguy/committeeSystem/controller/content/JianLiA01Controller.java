package com.toughguy.committeeSystem.controller.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.toughguy.committeeSystem.dto.ScreeningDTO;
import com.toughguy.committeeSystem.model.content.JianLiA01;
import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.service.content.prototype.IJianLiA01Service;
import com.toughguy.committeeSystem.service.content.prototype.IRsUnmkService;

@RequestMapping("/jianliA01")
@RestController
public class JianLiA01Controller {
	
	@Autowired
	private IJianLiA01Service JianLiA01Service;
	
	@Autowired
	private IRsUnmkService rsUnmkServiceImpl;
	
	
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
		 * 根据名字和身份证查询政法委列表
		 * @param name
		 * @param idCard
		 * @param NowPage
		 * @param Nums
		 * @return
		 */
		@RequestMapping("/selectZFWNameAndCardList")
		public Map<String,Object> selectZFWNameAndCardList(String name,String idCard,String NowPage,String Nums){
			JianLiA01 jl = new JianLiA01();
			jl.setName(name);
			jl.setIdCard(idCard);
			Map<String,String> rsUnmkMap = new HashMap<>();
			//rsUnmkMap.put("gaj", "");		//公安局
			rsUnmkMap.put("jcy", "A49.F09.291");		//检察院
			rsUnmkMap.put("sfj", "A49.F09.435");		//司法局
			rsUnmkMap.put("fy", "A49.F09.281");			//法院
			List<RsUnmk> rsUnmkList = rsUnmkServiceImpl.findZFWCode(rsUnmkMap);		//1.查询出全政法委的全部部门id
			RsUnmk r = new RsUnmk();
			r.setUID("0");
			r.setName("中国共产党太原市委员会政法委员会");
			r.setCode("A49.F09.329");
			r.setSID("-1");
			rsUnmkList.add(r);
			Set<String> set = new HashSet<>();
			for(int i=0;i<rsUnmkList.size();i++) {
				List<JianLiA01> rsA02UserId = JianLiA01Service.findZFWCodeID(rsUnmkList.get(i).getUID());
				if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
					for(int j=0;j<rsA02UserId.size();j++) {
						set.add(rsA02UserId.get(j).getAid());
					}
				}
			}
			List<JianLiA01> nameCardList = JianLiA01Service.selectList(jl);
			List<JianLiA01> selectList = new ArrayList<>();
			for(int i=0;i<nameCardList.size();i++) {
				for(String id:set) {
					if(nameCardList.get(i).getAid()==id || nameCardList.get(i).getAid().equals(id)) {
						selectList.add(nameCardList.get(i));
					}
				}
			}
			Map<String,Object> map = new HashMap<String, Object>();
			if(selectList!=null) {
				map.put("total", selectList.size());
				if(selectList.size()<=9) {
					map.put("rows",selectList);
					return map;
				}
				int i =(Integer.parseInt(NowPage)*9);
				if(i>(selectList.size())) {			
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<selectList.size();g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i<(selectList.size())) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i==selectList.size()) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			 return obj;
		}
		
		
		/**
		 * 司法局  公安局  检查院  法院 根据名字和身份证查询
		 * @param name
		 * @param idCard
		 * @param NowPage
		 * @param Nums
		 * @param card
		 * @return
		 */
		@RequestMapping("/selectQTNameAndCardList")
		public Map<String,Object> selectQTNameAndCardList(String name,String idCard,String NowPage,String Nums,String card){
			JianLiA01 jl = new JianLiA01();
			jl.setName(name);
			jl.setIdCard(idCard);
			String ss=null;
			/*if(card=="gaj" || card.equals("gaj")) {
				ss="公安局的法人编号";
			}*/
			if(card=="jcy" || card.equals("jcy")) {
				ss="A49.F09.291";
			}
			if(card=="fy" || card.equals("fy")) {
				ss="A49.F09.281";
			}
			if(card=="sfj" || card.equals("sfj")) {
				ss="A49.F09.435";
			}
			
			RsUnmk rsUnmkList = rsUnmkServiceImpl.findQTCode(ss);		//1.查询出全政法委的全部部门id
			Set<String> set = new HashSet<>();
			List<JianLiA01> rsA02UserId = JianLiA01Service.findQTCodeID(rsUnmkList.getUID());
			if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
				for(int j=0;j<rsA02UserId.size();j++) {
					set.add(rsA02UserId.get(j).getAid());
				}
			}
			List<JianLiA01> nameCardList = JianLiA01Service.selectList(jl);
			List<JianLiA01> selectList = new ArrayList<>();
			for(int i=0;i<nameCardList.size();i++) {
				for(String id:set) {
					if(nameCardList.get(i).getAid()==id || nameCardList.get(i).getAid().equals(id)) {
						selectList.add(nameCardList.get(i));
					}
				}
			}
			Map<String,Object> map = new HashMap<String, Object>();
			if(selectList!=null) {
				map.put("total", selectList.size());
				if(selectList.size()<=9) {
					map.put("rows",selectList);
					return map;
				}
				int i =(Integer.parseInt(NowPage)*9);
				if(i>(selectList.size())) {			
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<selectList.size();g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i<(selectList.size())) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i==selectList.size()) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			 return obj;
			
			
		}
		

		
		/**
		 * 查询政法委全部列表数据
		 * @param name
		 * @param idCard
		 * @param NowPage
		 * @param Nums
		 * @return
		 */
		@RequestMapping("/selectZFWAllList")
		public Map<String,Object> selectZFWAllList(String NowPage,String Nums){
			Map<String,String> rsUnmkMap = new HashMap<>();
			//rsUnmkMap.put("gaj", "");		//公安局
			rsUnmkMap.put("jcy", "A49.F09.291");		//检察院
			rsUnmkMap.put("sfj", "A49.F09.435");		//司法局
			rsUnmkMap.put("fy", "A49.F09.281");			//法院
			List<RsUnmk> rsUnmkList = rsUnmkServiceImpl.findZFWCode(rsUnmkMap);		//1.查询出全政法委的全部部门id
			RsUnmk r = new RsUnmk();
			r.setUID("0");
			r.setName("中国共产党太原市委员会政法委员会");
			r.setCode("A49.F09.329");
			r.setSID("-1");
			rsUnmkList.add(r);
			Set<String> set = new HashSet<>();			//存放唯一政法委的用户id
			for(int i=0;i<rsUnmkList.size();i++) {
				List<JianLiA01> rsA02UserId = JianLiA01Service.findZFWCodeID(rsUnmkList.get(i).getUID());		//根据部门id查询全部的人员id
				if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
					for(int j=0;j<rsA02UserId.size();j++) {
						set.add(rsA02UserId.get(j).getAid());
					}
				}
			}
			List<JianLiA01> selectList = new ArrayList<>();
			for(String aa:set) {
				JianLiA01 jl = new JianLiA01();
				jl.setAid(aa);
				JianLiA01 jla01 = JianLiA01Service.selectIdList(jl);
				selectList.add(jla01);
			}
			for(int i=0;i<selectList.size();i++) {
				if(selectList.get(i).getAid()==null || selectList.get(i).getAid().equals("")) {
					selectList.get(i).setAid("-");
				}
				if(selectList.get(i).getName()==null || selectList.get(i).getName().equals("")) {
					selectList.get(i).setName("-");
				}
				if(selectList.get(i).getIdCard()==null || selectList.get(i).getIdCard().equals("")) {
					selectList.get(i).setIdCard("-");
				}
				if(selectList.get(i).getJobNameL()==null ||selectList.get(i).getJobNameL().equals("")) {
					selectList.get(i).setJobNameL("-");
				}
				if(selectList.get(i).getGradeTime()==null || selectList.get(i).getGradeTime().equals("") || selectList.get(i).getGradeTime().length()==0) {
					selectList.get(i).setGradeTime("-");
				}else {
					if(selectList.get(i).getGradeTime().length()<7 && selectList.get(i).getGradeTime().length()>4) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4));
					}else if(selectList.get(i).getGradeTime().length()>7) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4,6)+"."+selectList.get(i).getGradeTime().substring(6));
					}
				}
				if(selectList.get(i).getJobLevel()==null ||selectList.get(i).getJobLevel().equals("")) {
					selectList.get(i).setJobLevel("-");
				}
				if(selectList.get(i).getGrade()==null || selectList.get(i).getGrade().equals("")) {
					selectList.get(i).setGrade("-");
				}
				if(selectList.get(i).getJobLevelTime()==null || selectList.get(i).getJobLevelTime().equals("") || selectList.get(i).getJobLevelTime().length()==0) {
					selectList.get(i).setJobLevelTime("-");
				}else {
					if(selectList.get(i).getJobLevelTime().length()<7 && selectList.get(i).getJobLevelTime().length()>4) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4));
					}else if(selectList.get(i).getJobLevelTime().length()>7) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4,6)+"."+selectList.get(i).getJobLevelTime().substring(6));
					}
				}
			}
			Map<String,Object> map = new HashMap<String, Object>();
			if(selectList!=null) {
				map.put("total", selectList.size());
				if(selectList.size()<=9) {
					map.put("rows",selectList);
					return map;
				}
				int i =(Integer.parseInt(NowPage)*9);
				if(i>(selectList.size())) {			
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<selectList.size();g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i<(selectList.size())) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i==selectList.size()) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			return obj;
		}
		
		
		/**
		 * 查询除政法委的其他单位的全部列表信息
		 * @param NowPage
		 * @param Nums
		 * @param card
		 * @return
		 */
		@RequestMapping("/selectQTAllList")
		public Map<String,Object> selectQTAllList(String NowPage,String Nums,String card){
			String ss=null;
			/*if(card=="gaj" || card.equals("gaj")) {
				ss="公安局的法人编号";
			}*/
			if(card=="jcy" || card.equals("jcy")) {
				ss="A49.F09.291";
			}
			if(card=="fy" || card.equals("fy")) {
				ss="A49.F09.281";
			}
			if(card=="sfj" || card.equals("sfj")) {
				ss="A49.F09.435";
			}
			RsUnmk rsUnmkList = rsUnmkServiceImpl.findQTCode(ss);		//1.查询出全政法委的全部部门id
			Set<String> set = new HashSet<>();			//存放唯一政法委的用户id
			List<JianLiA01> rsA02UserId = JianLiA01Service.findQTCodeID(rsUnmkList.getUID());		//根据部门id查询全部的人员id
			if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
				for(int j=0;j<rsA02UserId.size();j++) {
					set.add(rsA02UserId.get(j).getAid());
				}
			}
			List<JianLiA01> selectList = new ArrayList<>();
			for(String aa:set) {
				JianLiA01 jl = new JianLiA01();
				jl.setAid(aa);
				JianLiA01 jla01 = JianLiA01Service.selectIdList(jl);
				selectList.add(jla01);
			}
			for(int i=0;i<selectList.size();i++) {
				if(selectList.get(i).getAid()==null || selectList.get(i).getAid().equals("")) {
					selectList.get(i).setAid("-");
				}
				if(selectList.get(i).getName()==null || selectList.get(i).getName().equals("")) {
					selectList.get(i).setName("-");
				}
				if(selectList.get(i).getIdCard()==null || selectList.get(i).getIdCard().equals("")) {
					selectList.get(i).setIdCard("-");
				}
				if(selectList.get(i).getJobNameL()==null ||selectList.get(i).getJobNameL().equals("")) {
					selectList.get(i).setJobNameL("-");
				}
				if(selectList.get(i).getGradeTime()==null || selectList.get(i).getGradeTime().equals("") || selectList.get(i).getGradeTime().length()==0) {
					selectList.get(i).setGradeTime("-");
				}else {
					if(selectList.get(i).getGradeTime().length()<7 && selectList.get(i).getGradeTime().length()>4) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4));
					}else if(selectList.get(i).getGradeTime().length()>7) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4,6)+"."+selectList.get(i).getGradeTime().substring(6));
					}
				}
				if(selectList.get(i).getJobLevel()==null ||selectList.get(i).getJobLevel().equals("")) {
					selectList.get(i).setJobLevel("-");
				}
				if(selectList.get(i).getGrade()==null || selectList.get(i).getGrade().equals("")) {
					selectList.get(i).setGrade("-");
				}
				if(selectList.get(i).getJobLevelTime()==null || selectList.get(i).getJobLevelTime().equals("") || selectList.get(i).getJobLevelTime().length()==0) {
					selectList.get(i).setJobLevelTime("-");
				}else {
					if(selectList.get(i).getJobLevelTime().length()<7 && selectList.get(i).getJobLevelTime().length()>4) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4));
					}else if(selectList.get(i).getJobLevelTime().length()>7) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4,6)+"."+selectList.get(i).getJobLevelTime().substring(6));
					}
				}
			}
			Map<String,Object> map = new HashMap<String, Object>();
			if(selectList!=null) {
				map.put("total", selectList.size());
				if(selectList.size()<=9) {
					map.put("rows",selectList);
					return map;
				}
				int i =(Integer.parseInt(NowPage)*9);
				if(i>(selectList.size())) {			
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<selectList.size();g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i<(selectList.size())) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i==selectList.size()) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			return obj;
			
		}
		
		
		
		
		
		
		/**
		 * 政法委的人员筛选
		 * @param sc
		 * @return
		 */
		@RequestMapping("/screenZFWList")
		public Map<String,Object> screenZFWList(ScreeningDTO sc){
			List<JianLiA01> selectList = JianLiA01Service.screenZFWList(sc);
			for(int i=0;i<selectList.size();i++) {
				if(selectList.get(i).getAid()==null || selectList.get(i).getAid().equals("")) {
					selectList.get(i).setAid("-");
				}
				if(selectList.get(i).getName()==null || selectList.get(i).getName().equals("")) {
					selectList.get(i).setName("-");
				}
				if(selectList.get(i).getIdCard()==null || selectList.get(i).getIdCard().equals("")) {
					selectList.get(i).setIdCard("-");
				}
				if(selectList.get(i).getJobNameL()==null ||selectList.get(i).getJobNameL().equals("")) {
					selectList.get(i).setJobNameL("-");
				}
				if(selectList.get(i).getGradeTime()==null || selectList.get(i).getGradeTime().equals("") || selectList.get(i).getGradeTime().length()==0) {
					selectList.get(i).setGradeTime("-");
				}else {
					if(selectList.get(i).getGradeTime().length()<7 && selectList.get(i).getGradeTime().length()>4) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4));
					}else if(selectList.get(i).getGradeTime().length()>7) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4,6)+"."+selectList.get(i).getGradeTime().substring(6));
					}
				}
				if(selectList.get(i).getJobLevel()==null ||selectList.get(i).getJobLevel().equals("")) {
					selectList.get(i).setJobLevel("-");
				}
				if(selectList.get(i).getGrade()==null || selectList.get(i).getGrade().equals("")) {
					selectList.get(i).setGrade("-");
				}
				if(selectList.get(i).getJobLevelTime()==null || selectList.get(i).getJobLevelTime().equals("") || selectList.get(i).getJobLevelTime().length()==0) {
					selectList.get(i).setJobLevelTime("-");
				}else {
					if(selectList.get(i).getJobLevelTime().length()<7 && selectList.get(i).getJobLevelTime().length()>4) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4));
					}else if(selectList.get(i).getJobLevelTime().length()>7) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4,6)+"."+selectList.get(i).getJobLevelTime().substring(6));
					}
				}
			}
			Map<String,Object> map = new HashMap<String, Object>();
			if(selectList!=null) {
				map.put("total", selectList.size());
				if(selectList.size()<=9) {
					map.put("rows",selectList);
					return map;
				}
				int i =(Integer.parseInt(sc.getNowPage())*9);
				if(i>(selectList.size())) {			
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<selectList.size();g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i<(selectList.size())) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i==selectList.size()) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			return obj;
		}
		
		
		/**
		 * 除政法委外的其他各机构人员的筛选
		 * @param sc
		 * @return
		 */
		@RequestMapping("/screenQTList")
		public Map<String,Object> screenQTList(ScreeningDTO sc){
			List<JianLiA01> selectList = JianLiA01Service.screenQTList(sc);
			for(int i=0;i<selectList.size();i++) {
				if(selectList.get(i).getAid()==null || selectList.get(i).getAid().equals("")) {
					selectList.get(i).setAid("-");
				}
				if(selectList.get(i).getName()==null || selectList.get(i).getName().equals("")) {
					selectList.get(i).setName("-");
				}
				if(selectList.get(i).getIdCard()==null || selectList.get(i).getIdCard().equals("")) {
					selectList.get(i).setIdCard("-");
				}
				if(selectList.get(i).getJobNameL()==null ||selectList.get(i).getJobNameL().equals("")) {
					selectList.get(i).setJobNameL("-");
				}
				if(selectList.get(i).getGradeTime()==null || selectList.get(i).getGradeTime().equals("") || selectList.get(i).getGradeTime().length()==0) {
					selectList.get(i).setGradeTime("-");
				}else {
					if(selectList.get(i).getGradeTime().length()<7 && selectList.get(i).getGradeTime().length()>4) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4));
					}else if(selectList.get(i).getGradeTime().length()>7) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4,6)+"."+selectList.get(i).getGradeTime().substring(6));
					}
				}
				if(selectList.get(i).getJobLevel()==null ||selectList.get(i).getJobLevel().equals("")) {
					selectList.get(i).setJobLevel("-");
				}
				if(selectList.get(i).getGrade()==null || selectList.get(i).getGrade().equals("")) {
					selectList.get(i).setGrade("-");
				}
				if(selectList.get(i).getJobLevelTime()==null || selectList.get(i).getJobLevelTime().equals("") || selectList.get(i).getJobLevelTime().length()==0) {
					selectList.get(i).setJobLevelTime("-");
				}else {
					if(selectList.get(i).getJobLevelTime().length()<7 && selectList.get(i).getJobLevelTime().length()>4) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4));
					}else if(selectList.get(i).getJobLevelTime().length()>7) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4,6)+"."+selectList.get(i).getJobLevelTime().substring(6));
					}
				}
			}
			Map<String,Object> map = new HashMap<String, Object>();
			if(selectList!=null) {
				map.put("total", selectList.size());
				if(selectList.size()<=9) {
					map.put("rows",selectList);
					return map;
				}
				int i =(Integer.parseInt(sc.getNowPage())*9);
				if(i>(selectList.size())) {			
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<selectList.size();g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i<(selectList.size())) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i==selectList.size()) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			return obj;
		}
		
		@RequestMapping("/ZFWdataAnalysis")
		public Map<String,Object> ZFWdataAnalysis(){
			return JianLiA01Service.ZFWdataAnalysis();
		}
		
		@RequestMapping("/QTdataAnalysis")
		public Map<String,Object> QTdataAnalysis(String card){
			return JianLiA01Service.QTdataAnalysis(card);
		}
		
		/**
		 * 根据组织id查询数据库
		 * @return
		 */
		@RequestMapping("/findUidAllList")
		public Map<String,Object> findUidAllList(String UID,String NowPage,String Nums){
			List<JianLiA01> selectList = JianLiA01Service.findUidAllList(UID);
			for(int i=0;i<selectList.size();i++) {
				if(selectList.get(i).getAid()==null || selectList.get(i).getAid().equals("")) {
					selectList.get(i).setAid("-");
				}
				if(selectList.get(i).getName()==null || selectList.get(i).getName().equals("")) {
					selectList.get(i).setName("-");
				}
				if(selectList.get(i).getIdCard()==null || selectList.get(i).getIdCard().equals("")) {
					selectList.get(i).setIdCard("-");
				}
				if(selectList.get(i).getJobNameL()==null ||selectList.get(i).getJobNameL().equals("")) {
					selectList.get(i).setJobNameL("-");
				}
				if(selectList.get(i).getGradeTime()==null || selectList.get(i).getGradeTime().equals("") || selectList.get(i).getGradeTime().length()==0) {
					selectList.get(i).setGradeTime("-");
				}else {
					if(selectList.get(i).getGradeTime().length()<7 && selectList.get(i).getGradeTime().length()>4) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4));
					}else if(selectList.get(i).getGradeTime().length()>7) {
						selectList.get(i).setGradeTime(selectList.get(i).getGradeTime().substring(0, 4)+"."+selectList.get(i).getGradeTime().substring(4,6)+"."+selectList.get(i).getGradeTime().substring(6));
					}
				}
				if(selectList.get(i).getJobLevel()==null ||selectList.get(i).getJobLevel().equals("")) {
					selectList.get(i).setJobLevel("-");
				}
				if(selectList.get(i).getGrade()==null || selectList.get(i).getGrade().equals("")) {
					selectList.get(i).setGrade("-");
				}
				if(selectList.get(i).getJobLevelTime()==null || selectList.get(i).getJobLevelTime().equals("") || selectList.get(i).getJobLevelTime().length()==0) {
					selectList.get(i).setJobLevelTime("-");
				}else {
					if(selectList.get(i).getJobLevelTime().length()<7 && selectList.get(i).getJobLevelTime().length()>4) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4));
					}else if(selectList.get(i).getJobLevelTime().length()>7) {
						selectList.get(i).setJobLevelTime(selectList.get(i).getJobLevelTime().substring(0, 4)+"."+selectList.get(i).getJobLevelTime().substring(4,6)+"."+selectList.get(i).getJobLevelTime().substring(6));
					}
				}
			}
			Map<String,Object> map = new HashMap<String, Object>();
			if(selectList!=null) {
				map.put("total", selectList.size());
				if(selectList.size()<=9) {
					map.put("rows",selectList);
					return map;
				}
				int i =(Integer.parseInt(NowPage)*9);
				if(i>(selectList.size())) {			
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<selectList.size();g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i<(selectList.size())) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
				if(i==selectList.size()) {
					int s=i-9;
					List<JianLiA01> list = new ArrayList<JianLiA01>();
					for(int g =s;g<i;g++) {
						list.add(selectList.get(g));
					}
					map.put("rows",list);
					return map;
				}
			}
			Map<String,Object> obj = new HashMap<>();
			obj.put("错误", "报错了");
			return obj;
		}
		
		
		/**
		 * 查询首页数据统计
		 * @return
		 */
		@RequestMapping("/findIndexData")
		public Map<String,Object> findIndexData(){
			return JianLiA01Service.findIndexData();
		}
		
		
		@RequestMapping("/screenDataAnalysis")
		public Map<String,Object> screenDataAnalysis(String UID){
			return JianLiA01Service.screenDataAnalysis(UID);
		}
		
		
		
}