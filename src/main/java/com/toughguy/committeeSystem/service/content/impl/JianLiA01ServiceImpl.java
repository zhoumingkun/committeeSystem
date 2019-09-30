package com.toughguy.committeeSystem.service.content.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

	
	
	public void ExportReport(HttpServletResponse response,String ids) { 
		try {
			
			//输入模板文件
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/人事人员查询表（模板）.xlsx"));
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
			workbook.setCompressTempFiles(false);
			POIUtils utils = new POIUtils();
			//对应Excel文件中的sheet，0代表第一个
			Sheet sh = xssfWorkbook.getSheetAt(0); 
			String[] id = ids.split(",");
			for(int j=0; j<id.length; j++) {
				JianLiA01 jianLiA01 = jianLiA01Dao.findExport(id[j]);
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
				if(jianLiA01.getJobLevelTime()!=null&&jianLiA01.getJobLevelTime().length()==0){
					createCell6.setCellValue("");//A0173  任现职务层次时间
			    }else if(jianLiA01.getJobLevelTime()!=null&&jianLiA01.getJobLevelTime().length()>4){
					String year =jianLiA01.getJobLevelTime().substring(0,4);		//2019
					createCell6.setCellValue(year);//A0173  任现职务层次时间
				}else if(jianLiA01.getJobLevelTime()!=null&&jianLiA01.getJobLevelTime().length()>7){
					String year =jianLiA01.getJobLevelTime().substring(0,4);		//2019
					String month =jianLiA01.getJobLevelTime().substring(5,7);
					createCell6.setCellValue(year+"."+month);//A0173  任现职务层次时间
				}else if(jianLiA01.getJobLevelTime()!=null&&jianLiA01.getJobLevelTime().length()>8){
					String year =jianLiA01.getJobLevelTime().substring(0,4);		//2019
					String month =jianLiA01.getJobLevelTime().substring(5,7);
					String day =jianLiA01.getJobLevelTime().substring(8);//09
					String  jobLevelTime= year+"."+month+"."+day+".";
					createCell6.setCellValue(jobLevelTime);//A0173  任现职务层次时间
				}
					
				Cell createCell7 = row.createCell(6);
				createCell7.setCellStyle(utils.Style6(workbook));
				createCell7.setCellValue(jianLiA01.getGrade()); //A0192d  现职级
				
				Cell createCell8 = row.createCell(7);
				createCell8.setCellStyle(utils.Style6(workbook));
				if(jianLiA01.getGradeTime()!=null&&jianLiA01.getGradeTime().length()==0){
					createCell8.setCellValue(""); //A0192c  任现职级时间
			    }else if(jianLiA01.getGradeTime()!=null&&jianLiA01.getGradeTime().length()>4){
					String year =jianLiA01.getGradeTime().substring(0,4);		//2019
					createCell8.setCellValue(year); //A0192c  任现职级时间
				}else if(jianLiA01.getGradeTime()!=null&&jianLiA01.getGradeTime().length()>7){
					String year =jianLiA01.getGradeTime().substring(0,4);		//2019
					String month =jianLiA01.getGradeTime().substring(5,7);
					createCell8.setCellValue(year+"."+month); //A0192c  任现职级时间
				}else if(jianLiA01.getGradeTime()!=null&&jianLiA01.getGradeTime().length()>8){
					String year =jianLiA01.getGradeTime().substring(0,4);		//2019
					String month =jianLiA01.getGradeTime().substring(5,7);
					String day =jianLiA01.getGradeTime().substring(8);//09
					String  gradeTime= year+"."+month+"."+day+".";
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
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/简历表（模板）.xlsx"));
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
	        
	        Row row10 = sh.createRow(10);
	        CellRangeAddress region14 = new CellRangeAddress(10, (short) 10, 4, (short) 23);
			Cell cell19=row10.createCell(4);
			utils.setRegionStyle(sh, region14, utils.Style6(workbook));
			sh.addMergedRegion(region14);
	        cell19.setCellValue(jianLiA01.getJobNameL());//A0192A  工作单位及职务全称
	        
	        Row row11 = sh.createRow(11);
	        CellRangeAddress region15 = new CellRangeAddress(11, (short) 11, 4, (short) 23);
			Cell cell20=row11.createCell(4);
			utils.setRegionStyle(sh, region15, utils.Style6(workbook));
			sh.addMergedRegion(region15);
	        cell20.setCellValue("");//拟任职务
	        
	        Row row12 = sh.createRow(12);
	        CellRangeAddress region16 = new CellRangeAddress(12, (short) 12, 4, (short) 23);
			Cell cell21=row12.createCell(4);
			utils.setRegionStyle(sh, region16, utils.Style6(workbook));
			sh.addMergedRegion(region16);
	        cell21.setCellValue("");//拟免职务
	        
	        Row row13 = sh.createRow(13);
	        Row row14 = sh.createRow(14);
	        CellRangeAddress region17 = new CellRangeAddress(13, (short) 14, 1, (short) 23);
			Cell cell22=row13.createCell(1);
			utils.setRegionStyle(sh, region17, utils.Style6(workbook));
			sh.addMergedRegion(region17);
	        cell22.setCellValue(jianLiA01.getResume());////A1701  简历
	        
	      //对应Excel文件中的sheet，0代表第一个
			Sheet sh1 = xssfWorkbook.getSheetAt(1); 
			
			Row row001 = sh1.createRow(1);
	        CellRangeAddress region18 = new CellRangeAddress(1, (short) 1, 2, (short) 23);
			Cell cell23=row001.createCell(2);
			utils.setRegionStyle1(sh1, region18, utils.Style6(workbook));
			sh.addMergedRegion(region18);
	        cell23.setCellValue(jianLiA01.getAwardAndPenalty());//A14Z101  奖罚情况
	        
	        Row row002 = sh1.createRow(2);
	        CellRangeAddress region19 = new CellRangeAddress(2, (short) 2, 2, (short) 23);
			Cell cell24=row002.createCell(2);
			utils.setRegionStyle1(sh1, region19, utils.Style6(workbook));
			sh.addMergedRegion(region19);
	        cell24.setCellValue(jianLiA01.getYearResult());//A15Z101  年度考核结果
	        
	        Row row003 = sh1.createRow(3);
	        CellRangeAddress region20 = new CellRangeAddress(3, (short) 5, 2, (short) 23);
			Cell cell25=row003.createCell(2);
			utils.setRegionStyle1(sh1, region20, utils.Style6(workbook));
			sh.addMergedRegion(region20);
	        cell25.setCellValue(" ");//任免理由
	        
	        List<JianLiA36> jianLiA36 = jianLiA36Dao.selectA36(id);
	        for(int j=0; j<jianLiA36.size(); j++) {
	        	Row row = sh1.createRow(j+8);
	        	CellRangeAddress region21 = new CellRangeAddress(j+8, (short) j+8, 2, (short) 4);
				Cell cell26=row.createCell(2);
				utils.setRegionStyle1(sh1, region21, utils.Style6(workbook));
				sh.addMergedRegion(region21);
		        cell26.setCellValue(jianLiA36.get(j).getJRcall());//A3604A 家人称谓
		        
		        Cell cell27=row.createCell(5);
				cell27.setCellStyle(utils.Style6(workbook));
				cell27.setCellValue(jianLiA36.get(j).getJRname());//A3601   家人姓名
				
				Cell cell28=row.createCell(6);
				cell28.setCellStyle(utils.Style6(workbook));
				String JRbirthDay=jianLiA36.get(j).getJRbirthDay();
				String JRyear =JRbirthDay.substring(0,4);			
				int i = Integer.parseInt(JRyear);
				Calendar date = Calendar.getInstance();
				String year = String.valueOf(date.get(Calendar.YEAR));
				int k = Integer.parseInt(year);
				int age =k-i;
				cell28.setCellValue(age);//A3607 家人出生年月  算出年龄
				
				Cell cell29=row.createCell(7);
				cell29.setCellStyle(utils.Style6(workbook));
				cell29.setCellValue(jianLiA36.get(j).getJRpolitics());//A3627 家人政治面貌
	        	
	        }
	        
	        
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
		map.put("ID",jianli.getAid());	
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
				list.get(i).setAid("-");
			}
			if(list.get(i).getName()==null || list.get(i).getName()=="") {
				list.get(i).setName("-");
			}
			if(list.get(i).getIdCard()==null || list.get(i).getIdCard()=="") {
				list.get(i).setIdCard("-");
			}
			if(list.get(i).getJobNameL()==null ||list.get(i).getJobNameL()=="") {
				list.get(i).setJobNameL("-");
			}
			if(list.get(i).getGradeTime()==null || list.get(i).getGradeTime()=="" || list.get(i).getGradeTime().length()==0) {
				list.get(i).setGradeTime("-");
			}else {
				if(list.get(i).getGradeTime().length()<7) {
					list.get(i).setGradeTime(list.get(i).getGradeTime().substring(0, 4)+"."+list.get(i).getGradeTime().substring(4));
				}else {
					list.get(i).setGradeTime(list.get(i).getGradeTime().substring(0, 4)+"."+list.get(i).getGradeTime().substring(4,6)+"."+list.get(i).getGradeTime().substring(6));
				}
			}
			if(list.get(i).getJobLevel()==null ||list.get(i).getJobLevel()=="") {
				list.get(i).setJobLevel("-");
			}
			if(list.get(i).getGrade()==null || list.get(i).getGrade()=="") {
				list.get(i).setGrade("-");
			}
			if(list.get(i).getJobLevelTime()==null || list.get(i).getJobLevelTime()=="" || list.get(i).getJobLevelTime().length()==0) {
				list.get(i).setJobLevelTime("-");
			}else {
				if(list.get(i).getJobLevelTime().length()<7) {
					list.get(i).setJobLevelTime(list.get(i).getJobLevelTime().substring(0, 4)+"."+list.get(i).getJobLevelTime().substring(4));
				}else {
					list.get(i).setJobLevelTime(list.get(i).getJobLevelTime().substring(0, 4)+"."+list.get(i).getJobLevelTime().substring(4,6)+"."+list.get(i).getJobLevelTime().substring(6));
				}
			}
		}
		return list ;
	}


	@Override
	public List<JianLiA01> selectAllList() {
		// TODO Auto-generated method stub
		List<JianLiA01> list = jianLiA01Dao.selectAllList();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getAid()==null || list.get(i).getAid()=="") {
				list.get(i).setAid("-");
			}
			if(list.get(i).getName()==null || list.get(i).getName()=="") {
				list.get(i).setName("-");
			}
			if(list.get(i).getIdCard()==null || list.get(i).getIdCard()=="") {
				list.get(i).setIdCard("-");
			}
			if(list.get(i).getJobNameL()==null ||list.get(i).getJobNameL()=="") {
				list.get(i).setJobNameL("-");
			}
			if(list.get(i).getGradeTime()==null || list.get(i).getGradeTime()=="" || list.get(i).getGradeTime().length()==0) {
				list.get(i).setGradeTime("-");
			}else {
				if(list.get(i).getGradeTime().length()<7) {
					list.get(i).setGradeTime(list.get(i).getGradeTime().substring(0, 4)+"."+list.get(i).getGradeTime().substring(4));
				}else {
					list.get(i).setGradeTime(list.get(i).getGradeTime().substring(0, 4)+"."+list.get(i).getGradeTime().substring(4,6)+"."+list.get(i).getGradeTime().substring(6));
				}
			}
			if(list.get(i).getJobLevel()==null ||list.get(i).getJobLevel()=="") {
				list.get(i).setJobLevel("-");
			}
			if(list.get(i).getGrade()==null || list.get(i).getGrade()=="") {
				list.get(i).setGrade("-");
			}
			if(list.get(i).getJobLevelTime()==null || list.get(i).getJobLevelTime()=="" || list.get(i).getJobLevelTime().length()==0) {
				list.get(i).setJobLevelTime("-");
			}else {
				if(list.get(i).getJobLevelTime().length()<7) {
					list.get(i).setJobLevelTime(list.get(i).getJobLevelTime().substring(0, 4)+"."+list.get(i).getJobLevelTime().substring(4));
				}else {
					list.get(i).setJobLevelTime(list.get(i).getJobLevelTime().substring(0, 4)+"."+list.get(i).getJobLevelTime().substring(4,6)+"."+list.get(i).getJobLevelTime().substring(6));
				}
			}
		}
		return list ;
	}

	


}
