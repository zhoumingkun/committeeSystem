package com.toughguy.alarmSystem.service.content.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.toughguy.alarmSystem.model.content.Baojingqingkuang;
import com.toughguy.alarmSystem.model.content.Saoheichue;
import com.toughguy.alarmSystem.persist.content.prototype.IBaojingqingkuangDao;
import com.toughguy.alarmSystem.persist.content.prototype.ISaoheichueDao;
import com.toughguy.alarmSystem.service.content.prototype.IBaojingqingkuangService;
import com.toughguy.alarmSystem.service.impl.GenericServiceImpl;
import com.toughguy.alarmSystem.util.POIUtils;


/**
 * 报警情况Service实现类
 * @author zmk
 *
 */
@Service
public class BaojingqingkuangServiceImpl extends GenericServiceImpl<Baojingqingkuang, Integer> implements IBaojingqingkuangService{
	
	
	@Autowired
	IBaojingqingkuangDao  baojingqingkuangDao;

	@Override
	public Map<String ,Baojingqingkuang> findAllBJ() {
		// TODO Auto-generated method stub
		Date date = new Date();
		Map<String ,String> map = new HashMap<String, String>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String year =sf.format(date).substring(0,4);			//2019
		String month =sf.format(date).substring(5,7);			//09
		int time2 = Integer.parseInt(year);					   //转换为数字2019
		int month2 = Integer.parseInt(month)-1;				   //转换为数字08    0801-0901
		if(month=="01" || month.equals("01")) {		
			String stoptime=year+"-"+month+"-"+"01";					//2019-01-01
			time2=time2-1;
			month="12";
			String starttime=time2+"-"+month+"-"+"01";				//2018-12-01
			map.put("starttime", starttime);
			map.put("stoptime", stoptime);
		}else {
			String s="";
			if(month2<10) {
				s ="0"+month2;
			}else {
				s=month2+"";
			}
			String starttime=year+"-"+s+"-"+"01";				//2019-08-01
			String stoptime=year+"-"+month+"-"+"01";				//2019-09-01
			map.put("starttime", starttime);
			map.put("stoptime", stoptime);
		}
		Baojingqingkuang bj = ((IBaojingqingkuangDao)dao).findAllBJ(map);
		if(bj==null) {
			Baojingqingkuang baojing = new Baojingqingkuang();
			baojing.setWffzaj(0);
			baojing.setZaaj(0);
			baojing.setHzsg(0);
			baojing.setJtsg(0);
			baojing.setZazhsg(0);
			baojing.setZhsg(0);
			baojing.setZs(0);
			baojing.setJf(0);
			baojing.setJtbl(0);
			baojing.setGmqz(0);
			baojing.setZsxr(0);
			baojing.setJwjd(0);
			baojing.setQt(0);
			Map<String,Baojingqingkuang> bjqk = new HashMap<>();
			bjqk.put("bjqk", baojing);
			return bjqk ;
		}
		Map<String,Baojingqingkuang> bjqk = new HashMap<>();
		bjqk.put("bjqk", bj);
		return bjqk ;
	}


	@Override
	public Map<String ,Baojingqingkuang> findOneBJ(String xzqh) {
		// TODO Auto-generated method stub
		Date date = new Date();
		Map<String ,String> map = new HashMap<String, String>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String year =sf.format(date).substring(0,4);			//2019
		String month =sf.format(date).substring(5,7);			//09
		String starttime=year+"-"+month+"-"+"01";				//2019-09-01
		int month2 = Integer.parseInt(month)-1;					//月+1
		int year2=Integer.parseInt(year)-1;						//年-1
		String s="";
		if(month2<10) {
			s ="0"+month2;
		}else {
			s=month2+"";
		}
		String stoptime=year+"-"+s+"-"+"01";
		if(month=="01" || month.equals("01")) {
			stoptime=year2+"-12-01";
		}
		map.put("starttime", stoptime);		//2019-08-01
		map.put("stoptime", starttime);			//2019-09-01
		map.put("xzqh", xzqh);
		Baojingqingkuang bj = ((IBaojingqingkuangDao)dao).findOneBJ(map);
		if(bj==null) {
			Baojingqingkuang baojing = new Baojingqingkuang();
			baojing.setWffzaj(0);
			baojing.setZaaj(0);
			baojing.setHzsg(0);
			baojing.setJtsg(0);
			baojing.setZazhsg(0);
			baojing.setZhsg(0);
			baojing.setZs(0);
			baojing.setJf(0);
			baojing.setJtbl(0);
			baojing.setGmqz(0);
			baojing.setZsxr(0);
			baojing.setJwjd(0);
			baojing.setQt(0);
			Map<String,Baojingqingkuang> bjqk = new HashMap<>();
			bjqk.put("djshce", baojing);
			return bjqk ;
		}
		Map<String,Baojingqingkuang> bjqk = new HashMap<>();
		bjqk.put("djshce", bj);
		return bjqk ;
/*		if(baojingqingkuang==null) {		//截至当前还未提交
			if(month=="01" || month.equals("01")) {	
				String stopdate=year+"-"+month+"-"+"01";					//2019-01-01
				month="12";
				String startdate=year2+"-"+month+"-"+"01";					//2018-12-01    
				map.put("starttime", startdate);
				map.put("stoptime", stopdate);
				map.put("xzqh", xzqh);
				Map<String,Baojingqingkuang> bjqk = new HashMap<>();
				bjqk.put("bjqk",((IBaojingqingkuangDao)dao).findOneBJ(map) );
				return bjqk ;
			}else {
				int parseInt = Integer.parseInt(month)-1;
				String ss="";
				if(parseInt<10) {
					ss="0"+parseInt;
				}else {
					ss=parseInt+"";
				}
				String startdate=year+"-"+ss+"-"+"01";					//2019-08-01
				String stopdate=year+"-"+month+"-"+"01";					//2019-09-01
				map.put("starttime", startdate);
				map.put("stoptime", stopdate);
				map.put("xzqh", xzqh);
				Map<String,Baojingqingkuang> bjqk = new HashMap<>();
				bjqk.put("bjqk", ((IBaojingqingkuangDao)dao).findOneBJ(map));
				return bjqk ;
			}
		}else {
			Map<String,Baojingqingkuang> bjqk = new HashMap<>();
			bjqk.put("djshce", baojingqingkuang);
			return bjqk ;
		}*/
	}



	@Override
	public Map<String,Object> selectAll(String time) {
		// TODO Auto-generated method stub
		String date = "%"+time+"%";
		List<Baojingqingkuang> list = ((IBaojingqingkuangDao)dao).selectAll(date);
		Baojingqingkuang findShenHj = ((IBaojingqingkuangDao)dao).findShenHj(time);
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		int i =findShenHj.getWffzaj()+findShenHj.getZaaj()+findShenHj.getHzsg()+findShenHj.getJtsg()+findShenHj.getZazhsg()+findShenHj.getZhsg()+findShenHj.getZs()+findShenHj.getJf()+findShenHj.getJtbl()+findShenHj.getGmqz()+findShenHj.getZsxr()+findShenHj.getJwjd()+findShenHj.getQt();
		map.put("hj", i);
		map.put("HJ", findShenHj);
		return map;
	}


	@Override
	public Map<String,Object> selectOne(String time,String xzqh) {
		// TODO Auto-generated method stub
		String date = "%"+time+"%";
		Map<String ,String> map2 = new HashMap<String, String>();
		map2.put("time", date);
		map2.put("xzqh", xzqh);
		List<Baojingqingkuang> list = ((IBaojingqingkuangDao)dao).selectOne(map2);
		Map<String ,String> map3 = new HashMap<String, String>();
		map3.put("tjyf", time);
		map3.put("xzqh", xzqh);
		Baojingqingkuang findShenHj = ((IBaojingqingkuangDao)dao).findShiHj(map3);
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		int i =findShenHj.getWffzaj()+findShenHj.getZaaj()+findShenHj.getHzsg()+findShenHj.getJtsg()+findShenHj.getZazhsg()+findShenHj.getZhsg()+findShenHj.getZs()+findShenHj.getJf()+findShenHj.getJtbl()+findShenHj.getGmqz()+findShenHj.getZsxr()+findShenHj.getJwjd()+findShenHj.getQt();
		map.put("hj", i);
		map.put("HJ", findShenHj);
		return map;
	}


	@Override
	public  Map<String,Object> selectList(String time, String xzqh) {
		
		// TODO Auto-generated method stub
		if(time.equals("null") && xzqh.equals("全省")) {
			//什么都没选
			Map<String,String> map = new HashMap<>();
			map.put("xzqh", xzqh);
			map.put("time",time);
			List<Baojingqingkuang> list = ((IBaojingqingkuangDao)dao).selectAllList(map);
			if(list.size()<=0) {
				return null;
			}
			Map<String,Object> baojingList = new HashMap<>();
			for(int i =0;i<list.size();i++) {
				Map<Object ,String> aaa = new HashMap<>();
				String mc=list.get(i).getTjyf().substring(0, 4)+"年"+list.get(i).getTjyf().substring(5, 7)+"月全省报警情况统计月表";
				aaa.put("mc", mc);
				baojingList.put(list.get(i).getTjyf().substring(0, 7), aaa);
			}
			return baojingList;	
		}else if(!time.equals("null") && xzqh.equals("全省")){
			//选了时间 没选地市
			String date = "%"+time+"%";
			Map<String,String> map = new HashMap<>();
			map.put("xzqh", xzqh);
			map.put("time",date);
			List<Baojingqingkuang> list = ((IBaojingqingkuangDao)dao).selectAllList(map);
			if(list.size()<=0) {
				return null;
			}
			Map<String,Object> baojingList = new HashMap<>();
			for(int i = 0;i<list.size();i++) {
				Map<Object ,String> aaa = new HashMap<>();
				String mc=list.get(i).getTjyf().substring(0, 4)+"年"+list.get(i).getTjyf().substring(5, 7)+"月全省报警情况统计月表";
				aaa.put("mc", mc);
				baojingList.put(list.get(i).getTjyf().substring(0, 7), aaa);
			}
			return baojingList;
	
		}else if(!time.equals("null") && !xzqh.equals("全省")) {
			//选了时间  选了地市
			String date = "%"+time+"%";
			Map<String,String> map = new HashMap<>();
			map.put("xzqh", xzqh);
			map.put("time",date);
			List<Baojingqingkuang> list = ((IBaojingqingkuangDao)dao).selectList(map);
			if(list.size()<=0) {
				return null;
			}
			Map<String,Object> baojingList = new HashMap<>();
			for(int i =0; i<list.size();i++) {	
			Map<Object ,String> aaa = new HashMap<>();
			aaa.put("tbr",list.get(i).getTbr());
			aaa.put("createtime", list.get(i).getBjqk());
			aaa.put("xzqh", list.get(i).getXzqh());
			String mc=list.get(i).getTjyf().substring(0, 4)+"年"+list.get(i).getTjyf().substring(5, 7)+"月"+list.get(i).getXzqh()+"市报警情况统计月表";
			aaa.put("mc",mc);
			baojingList.put(list.get(i).getTjyf().substring(0, 7), aaa);
			}
			
			return baojingList;
		}else {
			//没选时间  选了地市
			Map<String,String> map = new HashMap<>();
			map.put("xzqh", xzqh);
			map.put("time",time);
			List<Baojingqingkuang> list = ((IBaojingqingkuangDao)dao).selectList(map);
			if(list.size()<=0) {
				return null;
			}
			Map<String,Object> baojingList = new HashMap<>();
			for(int i =0;i<list.size();i++) {
				Map<Object ,String> aaa = new HashMap<>();
				aaa.put("xzqh",list.get(i).getXzqh());
				aaa.put("tbr", list.get(i).getTbr());
				String mc=list.get(i).getTjyf().substring(0, 4)+"年"+list.get(i).getTjyf().substring(5, 7)+"月"+list.get(i).getXzqh()+"市报警情况统计月表";
				aaa.put("mc",mc);
				aaa.put("createtime",list.get(i).getBjqk());
				baojingList.put(list.get(i).getTjyf().substring(0, 7), aaa);
			}
			return baojingList;
		}
		


	}


	public List<Baojingqingkuang> findByStartEndTime(Map<String, String> map) {
		// TODO Auto-generated method stub
		return ((IBaojingqingkuangDao)dao).findByTjyfTime(map);
	}
	
	//导出省报警情况表
		public void ExportShenBjqk(HttpServletResponse response, String tjyf){
			try {
				
				//输入模板文件
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/报警情况统计月报表（模板） .xlsx"));
				SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
				workbook.setCompressTempFiles(false);
				POIUtils utils = new POIUtils();
				//对应Excel文件中的sheet，0代表第一个
				Sheet sh = xssfWorkbook.getSheetAt(0); 
				
				Map<String,String> map = new HashMap<>();
				map.put("tjyf", tjyf);
				
				List<Baojingqingkuang> BaojingReport = baojingqingkuangDao.findByTjyfTime(map);
				Date date = new Date();
				SimpleDateFormat dateBaojing = new SimpleDateFormat("yyyy-MM-dd");
				String time = dateBaojing.format(date);
				String year =tjyf.substring(0,4);			//2019
				String month =tjyf.substring(5,7);         //09
				
				Row row0 = sh.createRow(0);
				CellRangeAddress region0 = new CellRangeAddress(0, (short) 0, 0, (short) 16);
				Cell cell00000=row0.createCell(0);
				utils.setRegionStyle(sh, region0, utils.Style10(workbook));
				sh.addMergedRegion(region0);
		        cell00000.setCellValue(year+"年"+month+"月"+"报警情况统计月报表");//填报时间（当前导出时间）
				//第一行数据内容
				Row row1 = sh.createRow(1);
		        
		        CellRangeAddress region010 = new CellRangeAddress(1, (short) 1, 0, (short) 1);
				Cell cell000000=row1.createCell(0);
				utils.setRegionStyle(sh, region010, utils.Style81(workbook));
				sh.addMergedRegion(region010);
				cell000000.setCellValue("填报单位:山西省公安厅");
				
		        Baojingqingkuang baojingHj = baojingqingkuangDao.findShenHj(tjyf);
		        //合计1
		        
		        Row row4 = sh.createRow(4);
		        row4.setHeightInPoints(16);
				
		        Row row017 = sh.createRow(17);
		        row017.setHeightInPoints(16);
		        
		        Cell cell0001=row4.createCell(1);
				cell0001.setCellStyle(utils.Style6(workbook));
				cell0001.setCellValue("合计");
				
				Cell cell000001=row4.createCell(2);
				cell000001.setCellStyle(utils.Style6(workbook));
				cell000001.setCellValue("1");
				
		        Cell cell04=row4.createCell(3);
				cell04.setCellStyle(utils.Style6(workbook));
				cell04.setCellValue(baojingHj.getHj());
				//合计2
		        Cell cell05=row4.createCell(4);
				cell05.setCellStyle(utils.Style6(workbook));
				cell05.setCellValue(baojingHj.getWffzaj());
				//合计3
		        Cell cell06=row4.createCell(5);
				cell06.setCellStyle(utils.Style6(workbook));
				cell06.setCellValue(baojingHj.getZaaj());
				//合计4
		        Cell cell07=row4.createCell(6);
				cell07.setCellStyle(utils.Style6(workbook));
				cell07.setCellValue(baojingHj.getHzsg());
				//合计5
		        Cell cell08=row4.createCell(7);
				cell08.setCellStyle(utils.Style6(workbook));
				cell08.setCellValue(baojingHj.getJtsg());
				//合计6
		        Cell cell09=row4.createCell(8);
				cell09.setCellStyle(utils.Style6(workbook));
				cell09.setCellValue(baojingHj.getZazhsg());
				//合计7
		        Cell cell010=row4.createCell(9);
				cell010.setCellStyle(utils.Style6(workbook));
				cell010.setCellValue(baojingHj.getZhsg());
				//合计8
		        Cell cell012=row4.createCell(10);
				cell012.setCellStyle(utils.Style6(workbook));
				cell012.setCellValue(baojingHj.getZs());
				//合计9
		        Cell cell013=row4.createCell(11);
				cell013.setCellStyle(utils.Style6(workbook));
				cell013.setCellValue(baojingHj.getJf());
				//合计10
		        Cell cell014=row4.createCell(12);
				cell014.setCellStyle(utils.Style6(workbook));
				cell014.setCellValue(baojingHj.getJtbl());
				//合计11
		        Cell cell015=row4.createCell(13);
				cell015.setCellStyle(utils.Style6(workbook));
				cell015.setCellValue(baojingHj.getGmqz());
				//合计12
		        Cell cell016=row4.createCell(14);
				cell016.setCellStyle(utils.Style6(workbook));
				cell016.setCellValue(baojingHj.getZsxr());
				//合计13
		        Cell cell017=row4.createCell(15);
				cell017.setCellStyle(utils.Style6(workbook));
				cell017.setCellValue(baojingHj.getJwjd());
				//合计14
		        Cell cell018=row4.createCell(16);
				cell018.setCellStyle(utils.Style6(workbook));
				cell018.setCellValue(baojingHj.getQt());
		        
				for(int j=0; j<BaojingReport.size(); j++) {
					if(BaojingReport.get(j).getBjqk().equals("110接警")){
						System.out.println("进来啦省");
						Row row5 = sh.createRow(5);
						row5.setHeightInPoints(16);
						Cell cell0003=row5.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("110接警");
						
						Cell cell0004=row5.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("2");
						
						Cell cell00=row5.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row5.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row5.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row5.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row5.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row5.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row5.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row5.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row5.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row5.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row5.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row5.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row5.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row5.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("执勤巡逻发现")){
						Row row6 = sh.createRow(6);
						row6.setHeightInPoints(16);
						Cell cell0003=row6.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("执勤巡逻发现");
						
						Cell cell0004=row6.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("3");
						
						Cell cell00=row6.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row6.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row6.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row6.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row6.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row6.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row6.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row6.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row6.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row6.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row6.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row6.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row6.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row6.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("器材报警")){
						Row row7 = sh.createRow(7);
						row7.setHeightInPoints(16);
						Cell cell0003=row7.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("器材报警");
						
						Cell cell0004=row7.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("4");
						
						Cell cell00=row7.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row7.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row7.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row7.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row7.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row7.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row7.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row7.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row7.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row7.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row7.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row7.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row7.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row7.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("口头报警")){
						Row row8 = sh.createRow(8);
						row8.setHeightInPoints(16);
						Cell cell0003=row8.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("口头报警");
						
						Cell cell0004=row8.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("5");
						
						Cell cell00=row8.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row8.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row8.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row8.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row8.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row8.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row8.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row8.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row8.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row8.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row8.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row8.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row8.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row8.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("电话报警")){
						Row row9 = sh.createRow(9);
						row9.setHeightInPoints(16);
						Cell cell0003=row9.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("电话报警");
						
						Cell cell0004=row9.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("6");
						Cell cell00=row9.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row9.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row9.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row9.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row9.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row9.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row9.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row9.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row9.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row9.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row9.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row9.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row9.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row9.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("短信微信报警")){
						Row row10 = sh.createRow(10);
						row10.setHeightInPoints(16);
						Cell cell0003=row10.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("短信微信报警");
						
						Cell cell0004=row10.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("7");
						
						Cell cell00=row10.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row10.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row10.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row10.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row10.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row10.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row10.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row10.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row10.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row10.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row10.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row10.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row10.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row10.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("举报")){
						Row row11 = sh.createRow(11);
						row11.setHeightInPoints(16);
						Cell cell0003=row11.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("举报");
						
						Cell cell0004=row11.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("8");
						
						Cell cell00=row11.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row11.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row11.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row11.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row11.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row11.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row11.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row11.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row11.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row11.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row11.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row11.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row11.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row11.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("扭送现行")){
						Row row12 = sh.createRow(12);
						row12.setHeightInPoints(16);
						Cell cell0003=row12.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("扭送现行");
						
						Cell cell0004=row12.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("9");
						
						Cell cell00=row12.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row12.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row12.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row12.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row12.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row12.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row12.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row12.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row12.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row12.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row12.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row12.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row12.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row12.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("投案自首")){
						Row row13 = sh.createRow(13);
						row13.setHeightInPoints(16);
						Cell cell0003=row13.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("投案自首");
						
						Cell cell0004=row13.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("10");
						
						Cell cell00=row13.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row13.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row13.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row13.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row13.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row13.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row13.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row13.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row13.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row13.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row13.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row13.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row13.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row13.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("其他部门移送")){
						Row row14 = sh.createRow(14);
						row14.setHeightInPoints(16);
						Cell cell0003=row14.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("其他部门移送");
						
						Cell cell0004=row14.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("11");
						
						Cell cell00=row14.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row14.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row14.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row14.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row14.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row14.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row14.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row14.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row14.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row14.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row14.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row14.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row14.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row14.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("其他")){
						Row row15 = sh.createRow(15);
						row15.setHeightInPoints(16);
						Cell cell0003=row15.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("其他");
						
						Cell cell0004=row15.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("12");
						
						Cell cell00=row15.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row15.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row15.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row15.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row15.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row15.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row15.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row15.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row15.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row15.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row15.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row15.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row15.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row15.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			            

			        	}
					else if(BaojingReport.get(j).getBjqk().equals("出动警力")){
						Row row16 = sh.createRow(16);
						row16.setHeightInPoints(16);
						CellRangeAddress region1 = new CellRangeAddress(16, (short) 16, 0, (short) 1);
						Cell cell0003=row16.createCell(0);
						utils.setRegionStyle(sh, region1, utils.Style6(workbook));
						sh.addMergedRegion(region1);
						cell0003.setCellValue("出动警力");
						
						Cell cell0004=row16.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("13");
						
						Cell cell00=row16.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row16.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row16.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row16.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row16.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row16.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row16.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row16.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row16.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row16.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row16.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row16.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row16.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row16.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("处置报警(起)")){
						Row row17 = sh.createRow(17);
						row17.setHeightInPoints(16);
						
						Cell cell0003=row17.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("处置报警(起)");
						
						Cell cell0004=row17.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("14");
						
						Cell cell00=row17.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row17.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row17.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row17.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row17.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row17.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row17.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row17.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row17.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row17.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row17.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row17.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row17.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row17.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("现场抓获违法犯罪人员(人)")){
						Row row18 = sh.createRow(18);
						row18.setHeightInPoints(32);
						Cell cell0003=row18.createCell(1);
						cell0003.setCellStyle(utils.Style9(workbook));
						cell0003.setCellValue("现场抓获违法\r\n犯罪人员(人)");
						
						Cell cell0004=row18.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("15");
						
						Cell cell00=row18.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row18.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row18.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row18.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row18.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row18.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row18.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row18.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row18.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row18.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row18.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row18.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row18.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row18.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("逃犯(人)")){
						Row row19 = sh.createRow(19);
						row19.setHeightInPoints(16);
						Cell cell0003=row19.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("逃犯(人)");
						
						Cell cell0004=row19.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("16");
						
						Cell cell00=row19.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row19.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row19.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row19.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row19.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row19.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row19.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row19.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row19.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row19.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row19.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row19.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row19.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row19.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("救助伤员(人)")){
						Row row20 = sh.createRow(20);
						row20.setHeightInPoints(16);
						Cell cell0003=row20.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("救助伤员(人)");
						
						Cell cell0004=row20.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("17");
						
						Cell cell00=row20.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row20.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row20.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row20.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row20.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row20.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row20.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row20.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row20.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row20.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row20.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row20.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row20.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row20.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("救助群众(人)")){
						Row row21 = sh.createRow(21);
						row21.setHeightInPoints(16);
						Cell cell0003=row21.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("救助群众(人)");
						
						Cell cell0004=row21.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("18");
						
						Cell cell00=row21.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row21.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row21.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row21.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row21.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row21.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row21.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row21.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row21.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row21.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row21.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row21.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row21.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row21.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("继续盘问(人)")){
						Row row22 = sh.createRow(22);
						row22.setHeightInPoints(16);
						Cell cell0003=row22.createCell(1);
						cell0003.setCellStyle(utils.Style6(workbook));
						cell0003.setCellValue("继续盘问(人)");
						
						Cell cell0004=row22.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("19");
						
						Cell cell00=row22.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row22.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row22.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row22.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row22.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row22.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row22.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row22.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row22.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row22.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row22.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row22.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row22.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row22.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("无效报警(起)")){
						Row row23 = sh.createRow(23);
						row23.setHeightInPoints(16);
						CellRangeAddress region01 = new CellRangeAddress(23, (short) 23, 0, (short) 1);
						Cell cell0003=row23.createCell(0);
						utils.setRegionStyle(sh, region01, utils.Style6(workbook));
						sh.addMergedRegion(region01);
						cell0003.setCellValue("无效报警(起)");
						
						Cell cell0004=row23.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("20");
						
						Cell cell00=row23.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row23.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row23.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row23.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row23.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row23.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row23.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row23.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row23.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row23.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row23.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row23.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row23.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row23.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("死亡(人)")){
						Row row24 = sh.createRow(24);
						row24.setHeightInPoints(16);
						CellRangeAddress region02 = new CellRangeAddress(24, (short) 24, 0, (short) 1);
						Cell cell0003=row24.createCell(0);
						utils.setRegionStyle(sh, region02, utils.Style6(workbook));
						sh.addMergedRegion(region02);
						cell0003.setCellValue("死亡(人)");
						
						Cell cell0004=row24.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("21");
						
						Cell cell00=row24.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row24.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row24.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row24.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row24.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row24.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row24.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row24.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row24.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row24.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row24.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row24.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row24.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row24.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
					else if(BaojingReport.get(j).getBjqk().equals("受伤(人)")){
						Row row25 = sh.createRow(25);
						row25.setHeightInPoints(16);
						CellRangeAddress region03 = new CellRangeAddress(25, (short) 25, 0, (short) 1);
						Cell cell0003=row25.createCell(0);
						utils.setRegionStyle(sh, region03, utils.Style6(workbook));
						sh.addMergedRegion(region03);
						cell0003.setCellValue("受伤(人)");
						
						Cell cell0004=row25.createCell(2);
						cell0004.setCellStyle(utils.Style6(workbook));
						cell0004.setCellValue("22");
						
						Cell cell00=row25.createCell(3);
			            cell00.setCellStyle(utils.Style6(workbook));
			            cell00.setCellValue(BaojingReport.get(j).getHj());
			            
						Cell cell1=row25.createCell(4);
			            cell1.setCellStyle(utils.Style6(workbook));
			            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
			            
			            Cell cell2=row25.createCell(5);
			            cell2.setCellStyle(utils.Style6(workbook));
			            cell2.setCellValue(BaojingReport.get(j).getZaaj());
			            
			            Cell cell3=row25.createCell(6);
			            cell3.setCellStyle(utils.Style6(workbook));
			            cell3.setCellValue(BaojingReport.get(j).getHzsg());
			            
			            Cell cell4=row25.createCell(7);
			            cell4.setCellStyle(utils.Style6(workbook));
			            cell4.setCellValue(BaojingReport.get(j).getJtsg());
			            
			            Cell cell5=row25.createCell(8);
			            cell5.setCellStyle(utils.Style6(workbook));
			            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
			            
			            Cell cell6=row25.createCell(9);
			            cell6.setCellStyle(utils.Style6(workbook));
			            cell6.setCellValue(BaojingReport.get(j).getZhsg());
			            
			            Cell cell7=row25.createCell(10);
			            cell7.setCellStyle(utils.Style6(workbook));
			            cell7.setCellValue(BaojingReport.get(j).getZs());
			            
			            Cell cell8=row25.createCell(11);
			            cell8.setCellStyle(utils.Style6(workbook));
			            cell8.setCellValue(BaojingReport.get(j).getJf());
			            
			            Cell cell9=row25.createCell(12);
			            cell9.setCellStyle(utils.Style6(workbook));
			            cell9.setCellValue(BaojingReport.get(j).getJtbl());
			            
			            Cell cell10=row25.createCell(13);
			            cell10.setCellStyle(utils.Style6(workbook));
			            cell10.setCellValue(BaojingReport.get(j).getGmqz());
			            
			            Cell cell11=row25.createCell(14);
			            cell11.setCellStyle(utils.Style6(workbook));
			            cell11.setCellValue(BaojingReport.get(j).getZsxr());
			            
			            Cell cell12=row25.createCell(15);
			            cell12.setCellStyle(utils.Style6(workbook));
			            cell12.setCellValue(BaojingReport.get(j).getJwjd());
			            
			            Cell cell13=row25.createCell(16);
			            cell13.setCellStyle(utils.Style6(workbook));
			            cell13.setCellValue(BaojingReport.get(j).getQt());
			        	}
				}
				
//	            Row row4 = sh.createRow(4);
		        row4.setHeightInPoints(16);
		        CellRangeAddress region2 = new CellRangeAddress(4,15,(short) 0, (short) 0);
		        
				Cell cell00001=row4.createCell(0);
				utils.setRegionStyle(sh, region2, utils.Style11(workbook));
				sh.addMergedRegion(region2);
				cell00001.setCellValue("报\r\n警\r\n情\r\n况\r\n(起)");
				
				CellRangeAddress region002 = new CellRangeAddress(17, (short) 22, 0, (short) 0);
//				Row row017 = sh.createRow(17);
				Cell cell0002=row017.createCell(0);
				utils.setRegionStyle(sh, region002, utils.Style11(workbook));
				sh.addMergedRegion(region002);
				cell0002.setCellValue("处\r\n警\r\n情\r\n况");
				
				String year1 =tjyf.substring(0,4);			//2019
				String month2 =tjyf.substring(5,7);         //09
				String title = year1+"年"+month2+"月"+"报警情况统计月报表";
				
				OutputStream out = response.getOutputStream();
//				response.reset();
				response.setHeader("Content-disposition", "attachment; filename="+new String( title.getBytes("gb2312"), "ISO8859-1" )+".xlsx");
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
		        out.flush();
				workbook.write(out);
		        out.close();
		        workbook.dispose();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		//导出市报警情况表
			public void ExportShiBjqk(HttpServletResponse response, String tjyf, String xzqh){
				try {
					
					//输入模板文件
					XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/报警情况统计月报表（模板） .xlsx"));
					SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
					workbook.setCompressTempFiles(false);
					POIUtils utils = new POIUtils();
					//对应Excel文件中的sheet，0代表第一个
					Sheet sh = xssfWorkbook.getSheetAt(0); 
					
					Map<String,String> map = new HashMap<>();
					map.put("tjyf", tjyf);
					map.put("xzqh", xzqh);
					List<Baojingqingkuang> BaojingReport = baojingqingkuangDao.findByTjyfAndRegion(map);
					System.out.println(BaojingReport);
					System.out.println(BaojingReport.size());
					Date date = new Date();
					SimpleDateFormat dateBaojing = new SimpleDateFormat("yyyy-MM-dd");
					String time = dateBaojing.format(date);
					String year =tjyf.substring(0,4);			//2019
					String month =tjyf.substring(5,7);         //09
					
					Row row0 = sh.createRow(0);
					CellRangeAddress region0 = new CellRangeAddress(0, (short) 0, 0, (short) 16);
					Cell cell00000=row0.createCell(0);
					utils.setRegionStyle(sh, region0, utils.Style10(workbook));
					sh.addMergedRegion(region0);
			        cell00000.setCellValue(year+"年"+month+"月"+"报警情况统计月报表");//填报时间（当前导出时间）
					
					
//					Cell cell0=row1.createCell(7);
//					cell0.setCellStyle(utils.Style8(workbook));
//			        cell0.setCellValue(year+"年"+month+"月");//填报时间（当前导出时间）
			        
//			        Cell cell01=row1.createCell(15);
//					cell01.setCellStyle(utils.Style8(workbook));
//			        cell01.setCellValue("填报人:");////填报人
			        
			        Map<String,String> map2 = new HashMap<>();
					map2.put("tjyf", tjyf);
					map2.put("xzqh", xzqh);
			        Baojingqingkuang baojingHj = baojingqingkuangDao.findShiHj(map2);
			        //合计1
			        Row row4 = sh.createRow(4);
			        row4.setHeightInPoints(16);
					
			        Row row017 = sh.createRow(17);
			        row017.setHeightInPoints(16);
			        
			        Cell cell0001=row4.createCell(1);
					cell0001.setCellStyle(utils.Style6(workbook));
					cell0001.setCellValue("合计");
					
					Cell cell000001=row4.createCell(2);
					cell000001.setCellStyle(utils.Style6(workbook));
					cell000001.setCellValue("1");
					
					Cell cell04=row4.createCell(3);
					cell04.setCellStyle(utils.Style6(workbook));
					cell04.setCellValue(baojingHj.getHj());
					
					//合计2
			        Cell cell05=row4.createCell(4);
					cell05.setCellStyle(utils.Style6(workbook));
					cell05.setCellValue(baojingHj.getWffzaj());
					//合计3
			        Cell cell06=row4.createCell(5);
					cell06.setCellStyle(utils.Style6(workbook));
					cell06.setCellValue(baojingHj.getZaaj());
					//合计4
			        Cell cell07=row4.createCell(6);
					cell07.setCellStyle(utils.Style6(workbook));
					cell07.setCellValue(baojingHj.getHzsg());
					//合计5
			        Cell cell08=row4.createCell(7);
					cell08.setCellStyle(utils.Style6(workbook));
					cell08.setCellValue(baojingHj.getJtsg());
					//合计6
			        Cell cell09=row4.createCell(8);
					cell09.setCellStyle(utils.Style6(workbook));
					cell09.setCellValue(baojingHj.getZazhsg());
					//合计7
			        Cell cell010=row4.createCell(9);
					cell010.setCellStyle(utils.Style6(workbook));
					cell010.setCellValue(baojingHj.getZhsg());
					//合计8
			        Cell cell012=row4.createCell(10);
					cell012.setCellStyle(utils.Style6(workbook));
					cell012.setCellValue(baojingHj.getZs());
					//合计9
			        Cell cell013=row4.createCell(11);
					cell013.setCellStyle(utils.Style6(workbook));
					cell013.setCellValue(baojingHj.getJf());
					//合计10
			        Cell cell014=row4.createCell(12);
					cell014.setCellStyle(utils.Style6(workbook));
					cell014.setCellValue(baojingHj.getJtbl());
					//合计11
			        Cell cell015=row4.createCell(13);
					cell015.setCellStyle(utils.Style6(workbook));
					cell015.setCellValue(baojingHj.getGmqz());
					//合计12
			        Cell cell016=row4.createCell(14);
					cell016.setCellStyle(utils.Style6(workbook));
					cell016.setCellValue(baojingHj.getZsxr());
					//合计13
			        Cell cell017=row4.createCell(15);
					cell017.setCellStyle(utils.Style6(workbook));
					cell017.setCellValue(baojingHj.getJwjd());
					//合计14
			        Cell cell018=row4.createCell(16);
					cell018.setCellStyle(utils.Style6(workbook));
					cell018.setCellValue(baojingHj.getQt());
					for(int j=0; j<BaojingReport.size(); j++) {
						if(BaojingReport.get(j).getBjqk().equals("110接警")){
							System.out.println("进来啦");
							
							//第一行数据内容
							Row row1 = sh.createRow(1);
					        
					        CellRangeAddress region010 = new CellRangeAddress(1, (short) 1, 0, (short) 1);
							Cell cell000000=row1.createCell(0);
							utils.setRegionStyle(sh, region010, utils.Style81(workbook));
							sh.addMergedRegion(region010);
							cell000000.setCellValue("填报单位:"+BaojingReport.get(j).getTbdw());
							
							CellRangeAddress region011 = new CellRangeAddress(1, (short) 1, 14, (short) 16);
							Cell cell001=row1.createCell(14);
							utils.setRegionStyle(sh, region011, utils.Style8(workbook));
							sh.addMergedRegion(region011);
							cell001.setCellValue("填报人:"+BaojingReport.get(j).getTbr());
							
//							Cell cell001=row1.createCell(16);
//							cell001.setCellStyle(utils.Style8(workbook));
//						    cell001.setCellValue(BaojingReport.get(j).getTbr());////填报人
						    
							Row row5 = sh.createRow(5);
							row5.setHeightInPoints(16);
							Cell cell0003=row5.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("110接警");
							
							Cell cell0004=row5.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("2");
							
							Cell cell00=row5.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row5.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row5.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row5.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row5.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row5.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row5.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row5.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row5.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row5.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row5.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row5.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row5.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row5.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("执勤巡逻发现")){
							Row row6 = sh.createRow(6);
							row6.setHeightInPoints(16);
							Cell cell0003=row6.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("执勤巡逻发现");
							
							Cell cell0004=row6.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("3");
							
							Cell cell00=row6.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row6.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row6.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row6.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row6.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row6.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row6.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row6.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row6.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row6.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row6.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row6.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row6.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row6.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("器材报警")){
							Row row7 = sh.createRow(7);
							row7.setHeightInPoints(16);
							Cell cell0003=row7.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("器材报警");
							
							Cell cell0004=row7.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("4");
							
							Cell cell00=row7.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row7.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row7.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row7.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row7.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row7.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row7.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row7.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row7.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row7.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row7.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row7.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row7.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row7.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("口头报警")){
							Row row8 = sh.createRow(8);
							row8.setHeightInPoints(16);
							Cell cell0003=row8.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("口头报警");
							
							Cell cell0004=row8.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("5");
							
							Cell cell00=row8.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row8.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row8.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row8.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row8.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row8.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row8.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row8.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row8.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row8.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row8.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row8.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row8.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row8.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("电话报警")){
							Row row9 = sh.createRow(9);
							row9.setHeightInPoints(16);
							Cell cell0003=row9.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("电话报警");
							
							Cell cell0004=row9.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("6");
							Cell cell00=row9.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row9.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row9.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row9.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row9.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row9.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row9.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row9.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row9.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row9.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row9.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row9.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row9.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row9.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("短信微信报警")){
							Row row10 = sh.createRow(10);
							row10.setHeightInPoints(16);
							Cell cell0003=row10.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("短信微信报警");
							
							Cell cell0004=row10.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("7");
							
							Cell cell00=row10.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row10.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row10.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row10.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row10.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row10.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row10.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row10.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row10.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row10.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row10.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row10.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row10.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row10.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("举报")){
							Row row11 = sh.createRow(11);
							row11.setHeightInPoints(16);
							Cell cell0003=row11.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("举报");
							
							Cell cell0004=row11.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("8");
							
							Cell cell00=row11.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row11.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row11.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row11.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row11.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row11.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row11.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row11.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row11.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row11.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row11.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row11.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row11.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row11.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("扭送现行")){
							Row row12 = sh.createRow(12);
							row12.setHeightInPoints(16);
							Cell cell0003=row12.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("扭送现行");
							
							Cell cell0004=row12.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("9");
							
							Cell cell00=row12.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row12.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row12.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row12.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row12.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row12.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row12.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row12.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row12.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row12.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row12.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row12.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row12.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row12.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("投案自首")){
							Row row13 = sh.createRow(13);
							row13.setHeightInPoints(16);
							Cell cell0003=row13.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("投案自首");
							
							Cell cell0004=row13.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("10");
							
							Cell cell00=row13.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row13.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row13.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row13.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row13.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row13.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row13.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row13.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row13.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row13.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row13.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row13.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row13.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row13.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("其他部门移送")){
							Row row14 = sh.createRow(14);
							row14.setHeightInPoints(16);
							Cell cell0003=row14.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("其他部门移送");
							
							Cell cell0004=row14.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("11");
							
							Cell cell00=row14.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row14.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row14.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row14.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row14.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row14.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row14.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row14.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row14.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row14.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row14.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row14.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row14.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row14.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("其他")){
							Row row15 = sh.createRow(15);
							row15.setHeightInPoints(16);
							Cell cell0003=row15.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("其他");
							
							Cell cell0004=row15.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("12");
							
							Cell cell00=row15.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row15.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row15.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row15.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row15.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row15.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row15.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row15.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row15.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row15.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row15.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row15.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row15.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row15.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("出动警力")){
							Row row16 = sh.createRow(16);
							row16.setHeightInPoints(16);
							CellRangeAddress region1 = new CellRangeAddress(16, (short) 16, 0, (short) 1);
							Cell cell0003=row16.createCell(0);
							utils.setRegionStyle(sh, region1, utils.Style6(workbook));
							sh.addMergedRegion(region1);
							cell0003.setCellValue("出动警力");
							
							Cell cell0004=row16.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("13");
							
							Cell cell00=row16.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row16.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row16.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row16.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row16.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row16.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row16.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row16.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row16.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row16.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row16.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row16.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row16.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row16.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("处置报警(起)")){
							Row row17 = sh.createRow(17);
							row17.setHeightInPoints(16);
							
							Cell cell0003=row17.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("处置报警(起)");
							
							Cell cell0004=row17.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("14");
							
							Cell cell00=row17.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row17.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row17.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row17.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row17.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row17.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row17.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row17.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row17.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row17.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row17.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row17.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row17.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row17.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("现场抓获违法犯罪人员(人)")){
							Row row18 = sh.createRow(18);
							row18.setHeightInPoints(32);
							Cell cell0003=row18.createCell(1);
							cell0003.setCellStyle(utils.Style9(workbook));
							cell0003.setCellValue("现场抓获违法\r\n犯罪人员(人)");
							
							Cell cell0004=row18.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("15");
							
							Cell cell00=row18.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row18.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row18.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row18.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row18.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row18.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row18.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row18.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row18.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row18.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row18.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row18.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row18.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row18.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("逃犯(人)")){
							Row row19 = sh.createRow(19);
							row19.setHeightInPoints(16);
							Cell cell0003=row19.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("逃犯(人)");
							
							Cell cell0004=row19.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("16");
							
							Cell cell00=row19.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row19.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row19.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row19.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row19.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row19.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row19.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row19.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row19.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row19.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row19.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row19.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row19.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row19.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("救助伤员(人)")){
							Row row20 = sh.createRow(20);
							row20.setHeightInPoints(16);
							Cell cell0003=row20.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("救助伤员(人)");
							
							Cell cell0004=row20.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("17");
							
							Cell cell00=row20.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row20.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row20.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row20.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row20.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row20.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row20.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row20.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row20.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row20.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row20.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row20.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row20.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row20.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("救助群众(人)")){
							Row row21 = sh.createRow(21);
							row21.setHeightInPoints(16);
							Cell cell0003=row21.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("救助群众(人)");
							
							Cell cell0004=row21.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("18");
							
							Cell cell00=row21.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row21.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row21.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row21.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row21.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row21.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row21.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row21.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row21.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row21.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row21.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row21.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row21.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row21.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("继续盘问(人)")){
							Row row22 = sh.createRow(22);
							row22.setHeightInPoints(16);
							Cell cell0003=row22.createCell(1);
							cell0003.setCellStyle(utils.Style6(workbook));
							cell0003.setCellValue("继续盘问(人)");
							
							Cell cell0004=row22.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("19");
							
							Cell cell00=row22.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row22.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row22.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row22.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row22.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row22.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row22.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row22.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row22.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row22.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row22.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row22.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row22.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row22.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("无效报警(起)")){
							Row row23 = sh.createRow(23);
							row23.setHeightInPoints(16);
							CellRangeAddress region01 = new CellRangeAddress(23, (short) 23, 0, (short) 1);
							Cell cell0003=row23.createCell(0);
							utils.setRegionStyle(sh, region01, utils.Style6(workbook));
							sh.addMergedRegion(region01);
							cell0003.setCellValue("无效报警(起)");
							
							Cell cell0004=row23.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("20");
							
							Cell cell00=row23.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row23.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row23.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row23.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row23.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row23.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row23.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row23.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row23.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row23.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row23.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row23.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row23.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row23.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("死亡(人)")){
							Row row24 = sh.createRow(24);
							row24.setHeightInPoints(16);
							CellRangeAddress region02 = new CellRangeAddress(24, (short) 24, 0, (short) 1);
							Cell cell0003=row24.createCell(0);
							utils.setRegionStyle(sh, region02, utils.Style6(workbook));
							sh.addMergedRegion(region02);
							cell0003.setCellValue("死亡(人)");
							
							Cell cell0004=row24.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("21");
							
							Cell cell00=row24.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row24.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row24.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row24.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row24.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row24.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row24.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row24.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row24.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row24.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row24.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row24.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row24.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row24.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
						else if(BaojingReport.get(j).getBjqk().equals("受伤(人)")){
							Row row25 = sh.createRow(25);
							row25.setHeightInPoints(16);
							CellRangeAddress region03 = new CellRangeAddress(25, (short) 25, 0, (short) 1);
							Cell cell0003=row25.createCell(0);
							utils.setRegionStyle(sh, region03, utils.Style6(workbook));
							sh.addMergedRegion(region03);
							cell0003.setCellValue("受伤(人)");
							
							Cell cell0004=row25.createCell(2);
							cell0004.setCellStyle(utils.Style6(workbook));
							cell0004.setCellValue("22");
							
							Cell cell00=row25.createCell(3);
				            cell00.setCellStyle(utils.Style6(workbook));
				            cell00.setCellValue(BaojingReport.get(j).getHj());
				            
							Cell cell1=row25.createCell(4);
				            cell1.setCellStyle(utils.Style6(workbook));
				            cell1.setCellValue(BaojingReport.get(j).getWffzaj());
				            
				            Cell cell2=row25.createCell(5);
				            cell2.setCellStyle(utils.Style6(workbook));
				            cell2.setCellValue(BaojingReport.get(j).getZaaj());
				            
				            Cell cell3=row25.createCell(6);
				            cell3.setCellStyle(utils.Style6(workbook));
				            cell3.setCellValue(BaojingReport.get(j).getHzsg());
				            
				            Cell cell4=row25.createCell(7);
				            cell4.setCellStyle(utils.Style6(workbook));
				            cell4.setCellValue(BaojingReport.get(j).getJtsg());
				            
				            Cell cell5=row25.createCell(8);
				            cell5.setCellStyle(utils.Style6(workbook));
				            cell5.setCellValue(BaojingReport.get(j).getZazhsg());
				            
				            Cell cell6=row25.createCell(9);
				            cell6.setCellStyle(utils.Style6(workbook));
				            cell6.setCellValue(BaojingReport.get(j).getZhsg());
				            
				            Cell cell7=row25.createCell(10);
				            cell7.setCellStyle(utils.Style6(workbook));
				            cell7.setCellValue(BaojingReport.get(j).getZs());
				            
				            Cell cell8=row25.createCell(11);
				            cell8.setCellStyle(utils.Style6(workbook));
				            cell8.setCellValue(BaojingReport.get(j).getJf());
				            
				            Cell cell9=row25.createCell(12);
				            cell9.setCellStyle(utils.Style6(workbook));
				            cell9.setCellValue(BaojingReport.get(j).getJtbl());
				            
				            Cell cell10=row25.createCell(13);
				            cell10.setCellStyle(utils.Style6(workbook));
				            cell10.setCellValue(BaojingReport.get(j).getGmqz());
				            
				            Cell cell11=row25.createCell(14);
				            cell11.setCellStyle(utils.Style6(workbook));
				            cell11.setCellValue(BaojingReport.get(j).getZsxr());
				            
				            Cell cell12=row25.createCell(15);
				            cell12.setCellStyle(utils.Style6(workbook));
				            cell12.setCellValue(BaojingReport.get(j).getJwjd());
				            
				            Cell cell13=row25.createCell(16);
				            cell13.setCellStyle(utils.Style6(workbook));
				            cell13.setCellValue(BaojingReport.get(j).getQt());
				        	}
					}
					
//					Row row4 = sh.createRow(4);
			        row4.setHeightInPoints(16);
			        CellRangeAddress region2 = new CellRangeAddress(4,15,(short) 0, (short) 0);
			        
					Cell cell00001=row4.createCell(0);
					utils.setRegionStyle(sh, region2, utils.Style11(workbook));
					sh.addMergedRegion(region2);
					cell00001.setCellValue("报\r\n警\r\n情\r\n况\r\n(起)");
					
					CellRangeAddress region002 = new CellRangeAddress(17, (short) 22, 0, (short) 0);
//					Row row017 = sh.createRow(17);
					Cell cell0002=row017.createCell(0);
					utils.setRegionStyle(sh, region002, utils.Style11(workbook));
					sh.addMergedRegion(region002);
					cell0002.setCellValue("处\r\n警\r\n情\r\n况");
					
					String year1 =tjyf.substring(0,4);			//2019
					String month2 =tjyf.substring(5,7);         //09
					String title = year1+"年"+month2+"月"+"报警情况统计月报表";
					
					OutputStream out = response.getOutputStream();
//					response.reset();
					response.setHeader("Content-disposition", "attachment; filename="+new String( title.getBytes("gb2312"), "ISO8859-1" )+".xlsx");
					response.setContentType("application/vnd.ms-excel;charset=utf-8");
			        out.flush();
					workbook.write(out);
			        out.close();
			        workbook.dispose();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		@Override
		public List<Baojingqingkuang> findOne(Map<String, String> map) {
			// TODO Auto-generated method stub
			return ((IBaojingqingkuangDao)dao).findOne(map);
		}


		@Override
		public void updateAll(Baojingqingkuang baojingqingkuang) {
			// TODO Auto-generated method stub
			((IBaojingqingkuangDao)dao).updateAll(baojingqingkuang);
		}

}
