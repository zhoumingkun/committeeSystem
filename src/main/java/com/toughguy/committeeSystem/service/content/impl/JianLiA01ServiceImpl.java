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
	public JianLiA01 findExport(String id) {
		// TODO Auto-generated method stub
		return ((IJianLiA01Dao)dao).findExport(id);
	}
	
	
	@Override
	public JianLiA01 findJianli(String id) {
		// TODO Auto-generated method stub
		return ((IJianLiA01Dao)dao).findJianli(id);
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
				createCell.setCellStyle(utils.Style6(workbook));//序号
				createCell.setCellValue(j+1);
				
				Cell createCell2 = row.createCell(1);
				createCell2.setCellStyle(utils.Style6(workbook));
				createCell2.setCellValue(jianLiA01.getName());//A0101      姓名
				
				Cell createCell3 = row.createCell(2);
				createCell3.setCellStyle(utils.Style6(workbook));
				createCell3.setCellValue(jianLiA01.getIdCard());//A0184     身份证号
				
				Cell createCell4 = row.createCell(3);
				createCell4.setCellStyle(utils.Style6(workbook));
				createCell4.setCellValue(jianLiA01.getJobNameL());//A0172  职务层次
				
				Cell createCell5 = row.createCell(4);
				createCell5.setCellStyle(utils.Style6(workbook));
				createCell5.setCellValue(jianLiA01.getJobLevel());//A0172  职务层次
				
				Cell createCell6 = row.createCell(5);
				createCell6.setCellStyle(utils.Style6(workbook));
				String year =jianLiA01.getJobLevelTime().substring(0,4);		//2019
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
				createCell7.setCellStyle(utils.Style6(workbook));
				createCell7.setCellValue(jianLiA01.getGrade()); //A0192d  现职级
				
				Cell createCell8 = row.createCell(7);
				createCell8.setCellStyle(utils.Style6(workbook));
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
	
	
	public void ExportJianLi(HttpServletResponse response,String id) { 
		try {
			
			//输入模板文件
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/简历表（模板） .xlsx"));
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
			workbook.setCompressTempFiles(false);
			POIUtils utils = new POIUtils();
			
			//对应Excel文件中的sheet，0代表第一个
			Sheet sh = xssfWorkbook.getSheetAt(0); 
			JianLiA01 jianLiA01 = jianLiA01Dao.findJianli(id);
			
			Row row1 = sh.createRow(1);
//	        row.setHeightInPoints(16);
			CellRangeAddress region0 = new CellRangeAddress(1, (short) 2, 2, (short) 2);
			Cell cell1=row1.createCell(2);
			utils.setRegionStyle(sh, region0, utils.Style6(workbook));
			sh.addMergedRegion(region0);
	        cell1.setCellValue(jianLiA01.getSex());//A0101      姓名 
	        
			CellRangeAddress region1 = new CellRangeAddress(1, (short) 2, 6, (short) 10);
			Cell cell2=row1.createCell(6);
			utils.setRegionStyle(sh, region1, utils.Style6(workbook));
			sh.addMergedRegion(region1);
	        cell2.setCellValue(jianLiA01.getSex());//A0104    性别
			
	        CellRangeAddress region2 = new CellRangeAddress(1, (short) 1, 16, (short) 21);
			Cell cell3=row1.createCell(16);
			utils.setRegionStyle(sh, region2, utils.Style6(workbook));
			sh.addMergedRegion(region2);
	        cell3.setCellValue(jianLiA01.getBirthDay());//A0107     出生年月1
	        
	        Row row2 = sh.createRow(2);
	        CellRangeAddress region02 = new CellRangeAddress(2, (short) 2, 16, (short) 21);
			Cell cell03=row2.createCell(16);
			utils.setRegionStyle(sh, region02, utils.Style6(workbook));
			sh.addMergedRegion(region02);
	        cell03.setCellValue(jianLiA01.getBirthDay());//A0107     出生年月2 算年龄
	        
	        Row row3 = sh.createRow(3);
	        
	        Cell cell4=row3.createCell(1);
			cell4.setCellStyle(utils.Style6(workbook));
			cell4.setCellValue(jianLiA01.getNation());//A0117     民族
			
			Cell cell5=row3.createCell(3);
			cell5.setCellStyle(utils.Style6(workbook));
			cell5.setCellValue(jianLiA01.getNation());//A0111A 籍贯
	        
			CellRangeAddress region3 = new CellRangeAddress(3, (short) 3, 16, (short) 21);
			Cell cell6=row3.createCell(16);
			utils.setRegionStyle(sh, region3, utils.Style6(workbook));
			sh.addMergedRegion(region3);
	        cell6.setCellValue(jianLiA01.getBirthPlace());//A0114A  出生地
	        
            Row row4 = sh.createRow(4);
            JianLiA39 jianLiA39 = jianLiA39Dao.selectA39(id);
	        Cell cell7=row4.createCell(1);
			cell7.setCellStyle(utils.Style6(workbook));
			cell7.setCellValue(jianLiA39.getJoinTime());//A0144  入党时间
			
			Cell cell8=row4.createCell(3);
			cell8.setCellStyle(utils.Style6(workbook));
			cell8.setCellValue(jianLiA01.getWorkingTime());//A0134     参加工作时间
	        
			CellRangeAddress region4 = new CellRangeAddress(4, (short) 4, 16, (short) 21);
			Cell cell9=row4.createCell(16);
			utils.setRegionStyle(sh, region4, utils.Style6(workbook));
			sh.addMergedRegion(region4);
	        cell9.setCellValue(jianLiA01.getHealthState());//A0128   健康状况
	        
	        Row row5 = sh.createRow(5);
	        CellRangeAddress region5 = new CellRangeAddress(5, (short) 5, 2, (short) 5);
			Cell cell10=row5.createCell(5);
			utils.setRegionStyle(sh, region5, utils.Style6(workbook));
			sh.addMergedRegion(region5);
	        cell10.setCellValue(jianLiA01.getSpecialityPost());//A0196  专业技术职务
	        
	        CellRangeAddress region6 = new CellRangeAddress(5, (short) 5, 11, (short) 21);
			Cell cell11=row5.createCell(11);
			utils.setRegionStyle(sh, region6, utils.Style6(workbook));
			sh.addMergedRegion(region6);
	        cell11.setCellValue(jianLiA01.getSpeciality());//A0187A   熟悉专业有何专长
	        
	        CellRangeAddress region7 = new CellRangeAddress(1, (short) 4, 22, (short) 23);
			Cell cell12=row5.createCell(22);
			utils.setRegionStyle(sh, region7, utils.Style6(workbook));
			sh.addMergedRegion(region7);
	        cell12.setCellValue("照片");//照片图片
	        
	        Row row6 = sh.createRow(6);
	        JianLiA08 jianLiA08QRZ = jianLiA08Dao.selectQRZ(id);
	        CellRangeAddress region8 = new CellRangeAddress(6, (short) 6, 3, (short) 10);
			Cell cell13=row6.createCell(3);
			utils.setRegionStyle(sh, region8, utils.Style6(workbook));
			sh.addMergedRegion(region8);
	        cell13.setCellValue(jianLiA08QRZ.getEducational());//A0801A  全日制学历  
	        
	        Row row7 = sh.createRow(7);
	        CellRangeAddress region9 = new CellRangeAddress(7, (short) 7, 3, (short) 10);
			Cell cell14=row7.createCell(3);
			utils.setRegionStyle(sh, region9, utils.Style6(workbook));
			sh.addMergedRegion(region9);
	        cell14.setCellValue(jianLiA08QRZ.getDegree());//A0901A  全日制学位
	        
	        CellRangeAddress region10 = new CellRangeAddress(6, (short) 7, 16, (short) 23);
			Cell cell15=row6.createCell(16);
			utils.setRegionStyle(sh, region10, utils.Style6(workbook));
			sh.addMergedRegion(region10);
	        cell15.setCellValue(jianLiA01.getGraduateSchool1());//ZZXLXX  毕业院校系及专业上 
	        
	        Row row8 = sh.createRow(8);
	        JianLiA08 jianLiA08ZZ = jianLiA08Dao.selectZZ(id);
	        CellRangeAddress region11 = new CellRangeAddress(8, (short) 8, 3, (short) 10);
			Cell cell16=row8.createCell(3);
			utils.setRegionStyle(sh, region11, utils.Style6(workbook));
			sh.addMergedRegion(region11);
	        cell16.setCellValue(jianLiA08ZZ.getEducational());//A0801A  在职学历  
	        
	        Row row9 = sh.createRow(9);
	        CellRangeAddress region12 = new CellRangeAddress(9, (short) 9, 3, (short) 10);
			Cell cell17=row9.createCell(3);
			utils.setRegionStyle(sh, region12, utils.Style6(workbook));
			sh.addMergedRegion(region12);
	        cell17.setCellValue(jianLiA08ZZ.getDegree());//A0901A  在职学位
	        
	        CellRangeAddress region13 = new CellRangeAddress(8, (short) 9, 16, (short) 23);
			Cell cell18=row8.createCell(16);
			utils.setRegionStyle(sh, region13, utils.Style6(workbook));
			sh.addMergedRegion(region13);
	        cell18.setCellValue(jianLiA01.getGraduateSchool2());//QRZXLXX 毕业院校系及专业下 
	        
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
	public List<Map<String,Object>> selectOne(JianLiA01 jianli) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		String name = "%"+jianli.getName()+"%";
		map.put("name",name);		//模糊
		map.put("idCard",jianli.getIdCard());	//不模糊
		List<JianLiA01> selectA01 = jianLiA01Dao.selectA01(map);
		for(int i =0;i<selectA01.size();i++) {
			Map<String,Object> map2=new HashMap<>();
			map2.put("A01", selectA01.get(i));
			List<JianLiA08> selectA08 = jianLiA08Dao.selectA08(selectA01.get(i).getAid());
			map2.put("A08", selectA08);
			List<JianLiA36> selectA36 = jianLiA36Dao.selectA36(selectA01.get(i).getAid());
			map2.put("A36", selectA36);
			JianLiA39 selectA39 = jianLiA39Dao.selectA39(selectA01.get(i).getAid());
			map2.put("A39", selectA39);
			list.add(map2);
		}
		return list;
	}


	@Override
	public List<JianLiA01> selectList(JianLiA01 jianli) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<>();
		String name = "%"+jianli.getName()+"%";
		map.put("name",name);		//模糊
		map.put("idCard",jianli.getIdCard());	//不模糊
		List<JianLiA01> list = jianLiA01Dao.selectList(map);
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getAid()==null || list.get(i).getAid()=="") {
				list.get(i).setAid("null");
			}
			if(list.get(i).getName()==null || list.get(i).getName()=="") {
				list.get(i).setName("null");
			}
			if(list.get(i).getIdCard()==null || list.get(i).getIdCard()=="") {
				list.get(i).setIdCard("null");
			}
			if(list.get(i).getJobNameL()==null ||list.get(i).getJobNameL()=="") {
				list.get(i).setJobNameL("null");
			}
			if(list.get(i).getGradeTime()==null || list.get(i).getGradeTime()=="") {
				list.get(i).setGradeTime("null");
			}
			if(list.get(i).getJobLevel()==null ||list.get(i).getJobLevel()=="") {
				list.get(i).setJobLevel("null");
			}
			if(list.get(i).getGrade()==null || list.get(i).getGrade()=="") {
				list.get(i).setGrade("null");
			}
			if(list.get(i).getJobLevelTime()==null || list.get(i).getJobLevelTime()=="") {
				list.get(i).setJobLevelTime("null");
			}		
		}
		return list ;
	}

	


}
