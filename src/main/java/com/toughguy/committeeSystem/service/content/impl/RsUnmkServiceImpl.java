package com.toughguy.committeeSystem.service.content.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.toughguy.committeeSystem.model.content.JianLiA01;
import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.persist.content.prototype.IRsUnmkDao;
import com.toughguy.committeeSystem.service.content.prototype.IRsUnmkService;
import com.toughguy.committeeSystem.service.impl.GenericServiceImpl;

@Service
public class RsUnmkServiceImpl extends GenericServiceImpl<RsUnmk, Integer> implements IRsUnmkService{
	
	@Autowired
	private IRsUnmkDao rsUnmkDaoImpl;
	
	public Map<String,Object> map = new HashMap<>();
	
	public Map<String,Object> map2 = new HashMap<>();
	
	private int aa=3;
	
	private int bb=2;
	
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

	@Override
	public List<RsUnmk> findZFWCode(Map<String,String> map) {
		// TODO Auto-generated method stub
		return rsUnmkDaoImpl.findZFWCode(map);
	}

	@Override
	public RsUnmk findQTCode(String code) {
		// TODO Auto-generated method stub
		return rsUnmkDaoImpl.findQTCode(code);
	}

	@Override
	public Map<String, Object> findZFWFramework() {
		// TODO Auto-generated method stub
		//Map<String,Object> map = new HashMap<>();
		map= new HashMap<>();
		LinkedHashMap<String,String> rsUnmkMap = new LinkedHashMap<>();
		rsUnmkMap.put("gaj", Gwygaj);		//公安局
		rsUnmkMap.put("jcy", Gwyjcy);		//检察院
		rsUnmkMap.put("sfj", Gwysfj);		//司法局
		rsUnmkMap.put("fy", Gwyfy);			//法院
		List<RsUnmk> rsUnmkList = rsUnmkDaoImpl.findZFWCode(rsUnmkMap);		//1.查询出全政法委的全部部门id
		List<RsUnmk> findRootID = rsUnmkDaoImpl.findRootID();		//调用方法查询数据库父id是-1 的并返回一个map
		map.put("1", findRootID);
		map.put("2", rsUnmkList);
		aa=3;
		findBottomID(rsUnmkList);
		return map;
	}

	@Override
	public Map<String, Object> findQTFramework(String card) {
		// TODO Auto-generated method stub
		String s=null;
		if(card=="gaj" || card.equals("gaj")) {
			s=Gwygaj;
		}
		if(card=="jcy" || card.equals("jcy")) {
			s=Gwyjcy;
		}
		if(card=="fy" || card.equals("fy")) {
			s=Gwyfy;
		}
		if(card=="sfj" || card.equals("sfj")) {
			s=Gwysfj;
		}
		List<RsUnmk> list = new ArrayList<>();
		RsUnmk rsUnmkList = rsUnmkDaoImpl.findQTCode(s);		//1.查询出全政法委的全部部门id
		rsUnmkList.setSID("-1");
		list.add(rsUnmkList);
		map2.put("1", list);
		findQTBottomID(list);
		bb=2;
		return map2;
	}
	
	
	
	/**
	 * 通过上级id查询下级id
	 * @param list
	 * @return
	 */
	public void findBottomID(List<RsUnmk> list){
		List<RsUnmk> allList = new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			RsUnmk r = new RsUnmk();
			r.setUID(list.get(i).getUID());
			List<RsUnmk> findID = rsUnmkDaoImpl.findID(r);
			if(findID!=null && findID.size()>0) {
				for(int ii=0;ii<findID.size();ii++) {
					allList.add(findID.get(ii));		
				}
			}
		}
		map.put(aa+"", allList);
		if(allList!=null && allList.size()>0) {
			aa++;
			findBottomID(allList);
			//allList.clear();
			
		}
	}
	
	
	public void findQTBottomID(List<RsUnmk> list){
		List<RsUnmk> allList = new ArrayList<>();
		for(int i=0;i<list.size();i++) {
			RsUnmk r = new RsUnmk();
			r.setUID(list.get(i).getUID());
			List<RsUnmk> findID = rsUnmkDaoImpl.findID(r);
			if(findID!=null && findID.size()>0) {
				for(int ii=0;ii<findID.size();ii++) {
					allList.add(findID.get(ii));		
				}
			}
		}
		map2.put(bb+"", allList);
		if(allList!=null && allList.size()>0) {
			bb++;
			findQTBottomID(allList);
			//allList.clear();
			
		}
	}
}
