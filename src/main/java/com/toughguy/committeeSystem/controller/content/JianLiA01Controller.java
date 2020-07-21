package com.toughguy.committeeSystem.controller.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.toughguy.committeeSystem.dto.ScreeningDTO;
import com.toughguy.committeeSystem.model.content.JianLiA01;
import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.service.content.impl.JianLiA01ServiceImpl;
import com.toughguy.committeeSystem.service.content.prototype.IJianLiA01Service;
import com.toughguy.committeeSystem.service.content.prototype.IRsUnmkService;
import com.toughguy.committeeSystem.util.RedisUtils;



@RequestMapping("/jianliA01")
@RestController
public class JianLiA01Controller {
	
	@Autowired
	private IJianLiA01Service JianLiA01Service;
	
	@Autowired
	private IRsUnmkService rsUnmkServiceImpl;
	
	@Autowired
	private  RedisUtils redisUtils;
	
	
	@Autowired
	private  RedisTemplate<String, String> rTrmplate; 	
	
	@Value("${Gwy.jcy}")
	private String Gwyjcy;
	
	@Value("${Gwy.sfj}")
	private String Gwysfj;
	
	@Value("${Gwy.fy}")
	private String Gwyfy;
	
	@Value("${Gwy.zfw}")
	private String Gwyzfw;
	
	@Value("${Gwy.gaj}")
	private String Gwygaj;
	
	
	
	
	@RequestMapping("/selectUnitAllList")
	 public List<JianLiA01> selectUnitAllList(String ss){
		 	RsUnmk rsUnmkList = rsUnmkServiceImpl.findQTCode(ss);		//1.查询出单位的部门id
			Set<String> set = new HashSet<>();			//存放单位用户的唯一id
			List<JianLiA01> rsA02UserId = JianLiA01Service.findQTCodeID(rsUnmkList.getUID());		//根据部门id查询全部的人员id
			if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
				for(int j=0;j<rsA02UserId.size();j++) {
					set.add(rsA02UserId.get(j).getAid());
				}
			}
			List<JianLiA01> selectList = new LinkedList<>();
			for(String aa:set) {
				JianLiA01 jla01 = JianLiA01Service.selectIdData(aa);
				selectList.add(jla01);
			}
			return selectList;
	 }
	
	
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
			rsUnmkMap.put("gaj", "Gwygaj");		//公安局
			rsUnmkMap.put("jcy", Gwyjcy);		//检察院
			rsUnmkMap.put("sfj", Gwysfj);		//司法局
			rsUnmkMap.put("fy", Gwyfy);			//法院
			List<RsUnmk> rsUnmkList = rsUnmkServiceImpl.findZFWCode(rsUnmkMap);		//1.查询出全政法委的全部部门id
			RsUnmk r = new RsUnmk();
			r.setUID("0");
			r.setName("中国共产党太原市委员会政法委员会");
			r.setCode(Gwyzfw);
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
			if(card=="gaj" || card.equals("gaj")) {
				ss=Gwygaj;
			}
			if(card=="jcy" || card.equals("jcy")) {
				ss=Gwyjcy;
			}
			if(card=="fy" || card.equals("fy")) {
				ss=Gwyfy;
			}
			if(card=="sfj" || card.equals("sfj")) {
				ss=Gwysfj;
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
			rsUnmkMap.put("gaj", "Gwygaj");		//公安局
			rsUnmkMap.put("jcy", Gwyjcy);		//检察院
			rsUnmkMap.put("sfj", Gwysfj);		//司法局
			rsUnmkMap.put("fy", Gwyfy);			//法院
			List<RsUnmk> rsUnmkList = rsUnmkServiceImpl.findZFWCode(rsUnmkMap);		//1.查询出全政法委的全部部门id
			RsUnmk r = new RsUnmk();
			r.setUID("0");
			r.setName("中国共产党太原市委员会政法委员会");
			r.setCode(Gwyzfw);
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
			List<JianLiA01> selectList=new ArrayList<>();
			if(card=="gaj" || card.equals("gaj")) {
				ss=Gwygaj;
				Object object = redisUtils.get("gajAllList");
				if(object==null|| object=="") {
					selectList= selectUnitAllList(ss);
					redisUtils.setMap("gajAllList", selectList,600);
				}else {
					String s =(String)redisUtils.get("gajAllList");
					selectList= JSONObject.parseArray(s,JianLiA01.class);
				}	
			}
			if(card=="jcy" || card.equals("jcy")) {
				ss=Gwyjcy;
				selectList= selectUnitAllList(ss);
			}
			if(card=="fy" || card.equals("fy")) {
				ss=Gwyfy;
				selectList= selectUnitAllList(ss);
			}
			if(card=="sfj" || card.equals("sfj")) {
				ss=Gwysfj;
				selectList= selectUnitAllList(ss);
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
		public Object ZFWdataAnalysis(){
			Object indexData  =redisUtils.get("zfw");
			 if(indexData==null|| indexData=="") {
				Object data = JianLiA01Service.ZFWdataAnalysis();
				redisUtils.setMap("zfw", data,600);
				return data;
			 }else {
				return indexData;
			 }
		}
		
		@RequestMapping("/QTdataAnalysis")
		public Object QTdataAnalysis(String card){
			Object indexData  =redisUtils.get(card);
			 if(indexData==null|| indexData=="") {
				Object data = JianLiA01Service.QTdataAnalysis(card);
				redisUtils.setMap(card, data,600);
				return data;
			 }else {
				return indexData;
			 }
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
		public Object findIndexData(){
			//redis读取数据   有  返回   没有调用数据放到redis并返回 
			 Object indexData  =redisUtils.get("index");
			 if(indexData==null|| indexData=="") {
				Object data = JianLiA01Service.findIndexData();
				redisUtils.setMap("index", data,600);
				return data;
			 }else {
				return indexData;
			 } 
		}
		
		
		@RequestMapping("/screenDataAnalysis")
		public Map<String,Object> screenDataAnalysis(String UID){
			return JianLiA01Service.screenDataAnalysis(UID);
		}
		
		@RequestMapping("/updataRedisData")
		public void updataRedisData() {
			Timer timer = new Timer();		
			timer.schedule(new TimerTask() {				//schedule方法是执行时间定时任务的方法					
				//run方法就是具体需要定时执行的任务			
				@Override			
				public void run() {			
					System.out.println("执行定时任务!!!");			
					long startTime=System.currentTimeMillis();
					//项目启动缓存首页findIndexData数据
					Object indexData  =redisUtils.get("index");
					if(indexData==null|| indexData=="") {
						Object data = JianLiA01Service.findIndexData();
						redisUtils.setMap("index", data,600);
						System.out.println("添加数据成功");
					}else {
						Object data = JianLiA01Service.findIndexData(); 
						redisUtils.getAndSet("index",data);
						System.out.println("更新数据成功");
					 }
					
					
					//项目启动缓存检察院数据
					Object jcy  =redisUtils.get("jcy");
					if(jcy==null|| jcy=="") {
						Object data = JianLiA01Service.QTdataAnalysis("jcy");
						redisUtils.setMap("jcy", data,600);
					}else {
						Object data = JianLiA01Service.QTdataAnalysis("jcy");
						redisUtils.getAndSet("jcy",data);
					 }
					
					
					//项目启动缓存法院数据
					Object fy  =redisUtils.get("fy");
					if(fy==null|| fy=="") {
						Object data = JianLiA01Service.QTdataAnalysis("fy");
						redisUtils.setMap("fy", data,600);
					}else {
						Object data = JianLiA01Service.QTdataAnalysis("fy");
						redisUtils.getAndSet("fy",data);
					 }
					
					//项目启动缓存司法局数据
					Object sfj  =redisUtils.get("sfj");
					if(sfj==null|| sfj=="") {
						Object data = JianLiA01Service.QTdataAnalysis("sfj");
						redisUtils.setMap("sfj", data,600);
					}else {
						Object data = JianLiA01Service.QTdataAnalysis("sfj");
						redisUtils.getAndSet("sfj",data);
					}
					 
					//项目启动缓存公安局数据
					Object gaj  =redisUtils.get("gaj");
					if(gaj==null|| gaj=="") {
						Object data = JianLiA01Service.QTdataAnalysis("gaj");
						redisUtils.setMap("gaj", data,600);
					}else {
						Object data = JianLiA01Service.QTdataAnalysis("gaj");
						redisUtils.getAndSet("gaj",data);
					 }
					
					//项目启动缓存政法委数据
					Object zfw  =redisUtils.get("zfw");
					if(zfw==null|| zfw=="") {
						Object data = JianLiA01Service.ZFWdataAnalysis();
						redisUtils.setMap("zfw", data,600);
					}else {
						Object data = JianLiA01Service.ZFWdataAnalysis();
						redisUtils.getAndSet("zfw",data);
					 }
					 
					
					
					//存或更新公安局全部人员信息
					Object gajAllList  =redisUtils.get("gajAllList");
					if(gajAllList==null|| gajAllList=="") {
						redisUtils.setMap("gajAllList", selectUnitAllList(Gwygaj),600);
					}else {
						redisUtils.getAndSet("gajAllList",selectUnitAllList(Gwygaj));
					 }
					
					long endTime=System.currentTimeMillis();
					 float excTime=(float)(endTime-startTime)/1000;
					 System.out.println("执行时间："+excTime+"s");

				}		
			}, 1,450000);		//定时间隔450秒
			
		}
			
		
		
}