package com.toughguy.committeeSystem.service.content.impl;

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
import com.toughguy.committeeSystem.model.content.Baojingqingkuang;
import com.toughguy.committeeSystem.model.content.JianLiA01;
import com.toughguy.committeeSystem.model.content.JianLiA08;
import com.toughguy.committeeSystem.model.content.JianLiA36;
import com.toughguy.committeeSystem.model.content.JianLiA39;
import com.toughguy.committeeSystem.persist.content.prototype.IBaojingqingkuangDao;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA01Dao;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA08Dao;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA36Dao;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA39Dao;
import com.toughguy.committeeSystem.service.content.prototype.IBaojingqingkuangService;
import com.toughguy.committeeSystem.service.content.prototype.IJianLiA01Service;
import com.toughguy.committeeSystem.service.impl.GenericServiceImpl;
import com.toughguy.committeeSystem.util.POIUtils;


/**
 * 简历表rs_a01Service实现类
 * @author zmk
 *
 */
@Service
public class JianLiA01ServiceImpl extends GenericServiceImpl<JianLiA01, Integer> implements IJianLiA01Service{
	
	
	@Autowired
	IJianLiA01Dao  jianLiA01Dao;
	
	@Autowired
	private IJianLiA08Dao jianLiA08Dao;
	
	@Autowired
	private IJianLiA36Dao jianLiA36Dao;

	@Autowired
	private IJianLiA39Dao jianLiA39Dao;
	
	@Override
	public JianLiA01 findExport(String ID) {
		// TODO Auto-generated method stub
		return ((IJianLiA01Dao)dao).findExport(ID);
	}
	
	
	@Override
	public JianLiA01 findJianli(String ID) {
		// TODO Auto-generated method stub
		return ((IJianLiA01Dao)dao).findJianli(ID);
	}

	
	
	public void ExportReport(HttpServletResponse response,String[] ids) { 
		try {
			
			//输入模板文件
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/人事人员查询表（模板） .xlsx"));
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
			workbook.setCompressTempFiles(false);
			POIUtils utils = new POIUtils();
			//对应Excel文件中的sheet，0代表第一个
			Sheet sh = xssfWorkbook.getSheetAt(0); 
			
			for(int j=0; j<ids.length; j++) {
				JianLiA01 jianLiA01 = jianLiA01Dao.findExport(ids[j]);
				Row row = sh.createRow(j+1);
				row.setHeightInPoints(18);
				
				Cell createCell = row.createCell(0);
				createCell.setCellStyle(utils.Style2(workbook));//序号
				createCell.setCellValue(j+1);
				
				Cell createCell2 = row.createCell(1);
				createCell2.setCellStyle(utils.Style2(workbook));
				createCell2.setCellValue(jianLiA01.getName());//A0101      姓名
				
				Cell createCell3 = row.createCell(2);
				createCell3.setCellStyle(utils.Style2(workbook));
				createCell3.setCellValue(jianLiA01.getIdCard());//A0184     身份证号
				
				Cell createCell4 = row.createCell(3);
				createCell4.setCellStyle(utils.Style2(workbook));
				createCell4.setCellValue(jianLiA01.getJobNameL());//A0172  职务层次
				
				Cell createCell5 = row.createCell(4);
				createCell5.setCellStyle(utils.Style2(workbook));
				createCell5.setCellValue(jianLiA01.getJobLevel());//A0172  职务层次
				
				Cell createCell6 = row.createCell(5);
				createCell6.setCellStyle(utils.Style2(workbook));
				String year =jianLiA01.getJobLevelTime().substring(0,4);			//2019
				String month =jianLiA01.getJobLevelTime().substring(5,7);
				String day =jianLiA01.getJobLevelTime().substring(8);//09
				if(day!=null){
					String  jobLevelTime= year+"."+month+"."+day+".";
					createCell6.setCellValue(jobLevelTime);//A0173  任现职务层次时间
				}else{
					String  jobLevelTime= year+"."+month;
					createCell6.setCellValue(jobLevelTime);//A0173  任现职务层次时间
				}
				
				Cell createCell7 = row.createCell(6);
				createCell7.setCellStyle(utils.Style2(workbook));
				createCell7.setCellValue(jianLiA01.getGrade()); //A0192d  现职级
				
				Cell createCell8 = row.createCell(7);
				createCell8.setCellStyle(utils.Style2(workbook));
				String year1 =jianLiA01.getGradeTime().substring(0,4);			//2019
				String month1 =jianLiA01.getGradeTime().substring(5,7);
				String day1 =jianLiA01.getGradeTime().substring(8);//09
				if(day1!=null){
					String  gradeTime= year1+"."+month1+"."+day1+".";
					createCell8.setCellValue(gradeTime); //A0192c  任现职级时间
				}else{
					String  gradeTime= year1+"."+month1;
					createCell8.setCellValue(gradeTime); //A0192c  任现职级时间
				}
				
			}
			
			String title = "人事人员查询";
			OutputStream out = response.getOutputStream();
//			response.reset();
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
	
	
	public void ExportJianLi(HttpServletResponse response,String ID) { 
		try {
			
			//输入模板文件
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/简历表（模板） .xlsx"));
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
			workbook.setCompressTempFiles(false);
			POIUtils utils = new POIUtils();
			//对应Excel文件中的sheet，0代表第一个
			Sheet sh = xssfWorkbook.getSheetAt(0); 
			
			
			
			String title = "简历表";
			OutputStream out = response.getOutputStream();
//			response.reset();
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
	public Map<String,Object> selectOne(JianLiA01 jianli) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<>();
		Map<String,Object> map2=new HashMap<>();
		String name = "%"+jianli.getName()+"%";
		map.put("name",name);		//模糊
		map.put("idCard",jianli.getIdCard());	//不模糊
		JianLiA01 selectA01 = jianLiA01Dao.selectA01(map);
		map2.put("A01", selectA01);
		List<JianLiA08> selectA08 = jianLiA08Dao.selectA08(selectA01.getID());
		map2.put("A08", selectA08);
		List<JianLiA36> selectA36 = jianLiA36Dao.selectA36(selectA01.getID());
		map2.put("A36", selectA36);
		JianLiA39 selectA39 = jianLiA39Dao.selectA39(selectA01.getID());
		map2.put("A39", selectA39);
		return map2;
	}

	


}
