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

import com.toughguy.committeeSystem.dto.ScreeningDTO;
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
import com.toughguy.committeeSystem.util.Base64Transformation;
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
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/简历表（模板2）.xlsx"));
			SXSSFWorkbook workbook = new SXSSFWorkbook(xssfWorkbook, 1000);
			workbook.setCompressTempFiles(false);
			POIUtils utils = new POIUtils();
			
			//对应Excel文件中的sheet，0代表第一个
			Sheet sh = xssfWorkbook.getSheetAt(0); 
			JianLiA01 jianLiA01 = jianLiA01Dao.findJianli(id);
			
			Row row1 = sh.createRow(1);
	        row1.setHeightInPoints(16);
	        
	        Row row2 = sh.createRow(2);
	        row2.setHeightInPoints(16);
	        
			CellRangeAddress region01 = new CellRangeAddress(1, (short) 2, 0, (short) 0);
			Cell cell01=row1.createCell(0);
			utils.setRegionStyle(sh, region01, utils.Style6(workbook));
			sh.addMergedRegion(region01);
	        cell01.setCellValue("姓名"); 
	        
			CellRangeAddress region0 = new CellRangeAddress(1, (short) 2, 1, (short) 1);
			Cell cell1=row1.createCell(1);
			utils.setRegionStyle(sh, region0, utils.Style6(workbook));
			sh.addMergedRegion(region0);
	        cell1.setCellValue(jianLiA01.getName());//A0101      姓名 
	        
	        CellRangeAddress region02 = new CellRangeAddress(1, (short) 2, 2, (short) 2);
			Cell cell02=row1.createCell(2);
			utils.setRegionStyle(sh, region02, utils.Style6(workbook));
			sh.addMergedRegion(region02);
	        cell02.setCellValue("性别");//A0104    性别
	        
			CellRangeAddress region1 = new CellRangeAddress(1, (short) 2, 3, (short) 3);
			Cell cell2=row1.createCell(3);
			utils.setRegionStyle(sh, region1, utils.Style6(workbook));
			sh.addMergedRegion(region1);
	        cell2.setCellValue(jianLiA01.getSex());//A0104    性别
			
	        Cell cell04=row1.createCell(4);
			cell04.setCellStyle(utils.Style7(workbook));
			cell04.setCellValue("出生年月");

			Cell cell3=row1.createCell(5);
			cell3.setCellStyle(utils.Style7(workbook));
			if(jianLiA01.getBirthDay()!=null&&jianLiA01.getBirthDay().length()==0){
				cell3.setCellValue("");//A0107     出生年月1
		    }else if(jianLiA01.getBirthDay()!=null&&jianLiA01.getBirthDay().length()>=3){
				String year =jianLiA01.getBirthDay().substring(0,4);		//2019
				cell3.setCellValue(year);//A0107     出生年月1
			}else if(jianLiA01.getBirthDay()!=null&&jianLiA01.getBirthDay().length()>=6){
				String year =jianLiA01.getBirthDay().substring(0,4);		//2019
				String month =jianLiA01.getBirthDay().substring(5,7);
				cell3.setCellValue(year+"."+month);//A0107     出生年月1
			}else if(jianLiA01.getBirthDay()!=null&&jianLiA01.getBirthDay().length()==8){
				String year =jianLiA01.getBirthDay().substring(0,4);		//2019
				String month =jianLiA01.getBirthDay().substring(5,7);
				String day =jianLiA01.getBirthDay().substring(8);//09
				String  BirthDay= year+"."+month+"."+day+".";
				cell3.setCellValue(BirthDay);//A0107     出生年月1
			}
	        
	        Cell cell05=row2.createCell(4);
		    cell05.setCellStyle(utils.Style8(workbook));
			cell05.setCellValue("（岁）");
			
			Cell cell03=row2.createCell(5);
			cell03.setCellStyle(utils.Style8(workbook));
			if(jianLiA01.getBirthDay()!=null&&jianLiA01.getBirthDay().length()==0){
				cell03.setCellValue("");//A0107     出生年月1
		    }else if(jianLiA01.getBirthDay()!=null&&jianLiA01.getBirthDay().length()>=3){
				String selfBirthDay =jianLiA01.getBirthDay().substring(0,4);		//2019
				int i = Integer.parseInt(selfBirthDay);
				Calendar date = Calendar.getInstance();
				String year = String.valueOf(date.get(Calendar.YEAR));
				int k = Integer.parseInt(year);
				int selfage =k-i;
				cell03.setCellValue("（"+selfage+"岁）");//A0107     出生年月2    算年龄
			}
	        
	        Row row3 = sh.createRow(3);
	        row3.setHeightInPoints(32);
	        Cell cell06=row3.createCell(0);
			cell06.setCellStyle(utils.Style6(workbook));
			cell06.setCellValue("民族");
			
	        Cell cell4=row3.createCell(1);
			cell4.setCellStyle(utils.Style6(workbook));
			cell4.setCellValue(jianLiA01.getNation());//A0117     民族
			
			Cell cell07=row3.createCell(2);
			cell07.setCellStyle(utils.Style6(workbook));
			cell07.setCellValue("籍贯");
			
			Cell cell5=row3.createCell(3);
			cell5.setCellStyle(utils.Style9(workbook));
			cell5.setCellValue(jianLiA01.getHomeTown());//A0111A 籍贯
			
			Cell cell08=row3.createCell(4);
			cell08.setCellStyle(utils.Style6(workbook));
			cell08.setCellValue("出生地");
			
			Cell cell6=row3.createCell(5);
			cell6.setCellStyle(utils.Style9(workbook));
	        cell6.setCellValue(jianLiA01.getBirthPlace());//A0114A  出生地
	        
            Row row4 = sh.createRow(4);
            row4.setHeightInPoints(32);
            Cell cell09=row4.createCell(0);
			cell09.setCellStyle(utils.Style6(workbook));
			cell09.setCellValue("入党时间");
			
            JianLiA39 jianLiA39 = jianLiA39Dao.selectA39(id);
	        Cell cell7=row4.createCell(1);
			cell7.setCellStyle(utils.Style6(workbook));
			cell7.setCellValue(jianLiA39.getJoinTime());//A0144  入党时间
			
			Cell cell010=row4.createCell(2);
			cell010.setCellStyle(utils.Style9(workbook));
			cell010.setCellValue("参加工作时间");
			
			Cell cell8=row4.createCell(3);
			cell8.setCellStyle(utils.Style6(workbook));
			cell8.setCellValue(jianLiA01.getWorkingTime());//A0134     参加工作时间
	        
			Cell cell011=row4.createCell(4);
			cell011.setCellStyle(utils.Style6(workbook));
			cell011.setCellValue("健康状况");
			
			Cell cell9=row4.createCell(5);
			cell9.setCellStyle(utils.Style6(workbook));
	        cell9.setCellValue(jianLiA01.getHealthState());//A0128   健康状况
	        
	        Row row5 = sh.createRow(5);
	        row5.setHeightInPoints(32);
	        
	        Cell cell0131=row5.createCell(6);
			cell0131.setCellStyle(utils.Style6(workbook));
	        cell0131.setCellValue("");
	        
	        Cell cell012=row5.createCell(0);
			cell012.setCellStyle(utils.Style9(workbook));
	        cell012.setCellValue("专业技术职务");
	        
	        CellRangeAddress region5 = new CellRangeAddress(5, (short) 5, 1, (short) 2);
			Cell cell10=row5.createCell(1);
			utils.setRegionStyle(sh, region5, utils.Style9(workbook));
			sh.addMergedRegion(region5);
	        cell10.setCellValue(jianLiA01.getSpecialityPost());//A0196  专业技术职务
	        
	        Cell cell013=row5.createCell(3);
			cell013.setCellStyle(utils.Style9(workbook));
	        cell013.setCellValue("熟悉专业有何专长");
	        
	        CellRangeAddress region6 = new CellRangeAddress(5, (short) 5, 4, (short) 5);
			Cell cell11=row5.createCell(4);
			utils.setRegionStyle(sh, region6, utils.Style9(workbook));
			sh.addMergedRegion(region6);
	        cell11.setCellValue(jianLiA01.getSpeciality());//A0187A   熟悉专业有何专长
	        
	        
	        CellRangeAddress region7 = new CellRangeAddress(1, (short) 5, 6, (short) 6);
			Cell cell12=row1.createCell(6);
			utils.setRegionStyle(sh, region7, utils.Style6(workbook));
			sh.addMergedRegion(region7);
	        cell12.setCellValue("照片");//照片图片
	        
	        Row row6 = sh.createRow(6);
	        row6.setHeightInPoints(16);
	        
	        Cell cell015=row6.createCell(1);
			cell015.setCellStyle(utils.Style7(workbook));
	        cell015.setCellValue("全日制");
	        List<JianLiA08> jianLiA08QRZ= jianLiA08Dao.selectQRZ(id);
	        
	        List<JianLiA08> aa = new ArrayList<>();
	        int ii=0;
			if(jianLiA08QRZ!=null) {
				for(int w=0;w<jianLiA08QRZ.size();w++) {
					if(jianLiA08QRZ.size()==1 && ii<1) {			//只查询出一条全日制
						aa.add(jianLiA08QRZ.get(w));
						ii++;
					}else if(jianLiA08QRZ.size()>1 && ii<1) {								//查询出多条全日制
						if(jianLiA08QRZ.get(w).getDegree()!=null && jianLiA08QRZ.get(w).getDegree()!="" && !jianLiA08QRZ.get(w).getDegree().equals("") ) {		
							aa.add(jianLiA08QRZ.get(w));
							ii++;
						}else if(w==(jianLiA08QRZ.size()-1)) {
							aa.add(jianLiA08QRZ.get(w));
							ii++;
						}
					}
				}
			}
			
	        CellRangeAddress region8 = new CellRangeAddress(6, (short) 6, 2, (short) 3);
			Cell cell13=row6.createCell(2);
			utils.setRegionStyle(sh, region8, utils.Style7(workbook));
			sh.addMergedRegion(region8);
	        cell13.setCellValue(aa.get(0).getEducational());//A0801A  全日制学历  
	        
	        Row row7 = sh.createRow(7);
	        row7.setHeightInPoints(16);
	        
	        CellRangeAddress region04 = new CellRangeAddress(6, (short) 7, 4, (short) 4);
			Cell cell016=row6.createCell(4);
			utils.setRegionStyle(sh, region04, utils.Style9(workbook));
			sh.addMergedRegion(region04);
	        cell016.setCellValue("毕业院校系及专业");
	        
	        Cell cell017=row7.createCell(1);
			cell017.setCellStyle(utils.Style8(workbook));
	        cell017.setCellValue("教育");
	        
	        CellRangeAddress region03 = new CellRangeAddress(6, (short) 7, 0, (short) 0);
			Cell cell014=row6.createCell(0);
			utils.setRegionStyle(sh, region03, utils.Style6(workbook));
			sh.addMergedRegion(region03);
	        cell014.setCellValue("学 历");  
	        
	        CellRangeAddress region9 = new CellRangeAddress(7, (short) 7, 2, (short) 3);
			Cell cell14=row7.createCell(2);
			utils.setRegionStyle(sh, region9, utils.Style8(workbook));
			sh.addMergedRegion(region9);
	        cell14.setCellValue(aa.get(0).getDegree());//A0901A  全日制学位
	        
	        CellRangeAddress region10 = new CellRangeAddress(6, (short) 7, 5, (short) 6);
			Cell cell15=row6.createCell(5);
			utils.setRegionStyle(sh, region10, utils.Style9(workbook));
			sh.addMergedRegion(region10);
	        cell15.setCellValue(jianLiA01.getGraduateSchool1());//ZZXLXX  毕业院校系及专业上 
	        
	        Row row8 = sh.createRow(8);
	        row8.setHeightInPoints(16);
	        
	        Cell cell018=row8.createCell(1);
			cell018.setCellStyle(utils.Style7(workbook));
	        cell018.setCellValue("在职");
	        
	        CellRangeAddress region05 = new CellRangeAddress(8, (short) 9, 4, (short) 4);
			Cell cell020=row8.createCell(4);
			utils.setRegionStyle(sh, region05, utils.Style9(workbook));
			sh.addMergedRegion(region05);
	        cell020.setCellValue("毕业院校系及专业");
	        
	        Row row9 = sh.createRow(9);
	        row9.setHeightInPoints(16);
	        
	        CellRangeAddress region13 = new CellRangeAddress(8, (short) 9, 5, (short) 6);
			Cell cell18=row7.createCell(5);
			utils.setRegionStyle(sh, region13, utils.Style9(workbook));
			sh.addMergedRegion(region13);
	        cell18.setCellValue(jianLiA01.getGraduateSchool2());//QRZXLXX 毕业院校系及专业下
	        
	        CellRangeAddress region130 = new CellRangeAddress(8, (short) 9, 0, (short) 0);
			Cell cell0018=row8.createCell(0);
			utils.setRegionStyle(sh, region130, utils.Style6(workbook));
			sh.addMergedRegion(region130);
	        cell0018.setCellValue("学 位"); 
	        
	        Cell cell019=row9.createCell(1);
			cell019.setCellStyle(utils.Style8(workbook));
	        cell019.setCellValue("教育");
	        
	        List<JianLiA08> jianLiA08ZZ= jianLiA08Dao.selectZZ(id);
	        ii=0;
	        if(jianLiA08ZZ!=null) {
				for(int w=0;w<jianLiA08ZZ.size();w++) {
					if(jianLiA08ZZ.size()==1 && ii<1) {			//只查询出一条在职教育
						aa.add(jianLiA08ZZ.get(w));
						ii++;
					}else if(jianLiA08ZZ.size()>1 && ii<1) {								//查询出多条在职教育
						if(jianLiA08ZZ.get(w).getDegree()!=null && jianLiA08ZZ.get(w).getDegree()!="" && !jianLiA08ZZ.get(w).getDegree().equals("") ) {		
							aa.add(jianLiA08ZZ.get(w));
							ii++;
						}else if(w==(jianLiA08ZZ.size()-1)) {
							aa.add(jianLiA08ZZ.get(w));
							ii++;
						}
					}
				}
			}
//	        if(jianLiA08ZZ==null){
//	        	CellRangeAddress region11 = new CellRangeAddress(8, (short) 8, 2, (short) 3);
//				Cell cell16=row8.createCell(2);
//				utils.setRegionStyle(sh, region11, utils.Style7(workbook));
//				sh.addMergedRegion(region11);
//				cell16.setCellValue(" ");//A0801A  在职学历
				
//				CellRangeAddress region12 = new CellRangeAddress(9, (short) 9, 2, (short) 3);
//				Cell cell17=row9.createCell(2);
//				utils.setRegionStyle(sh, region12, utils.Style7(workbook));
//				sh.addMergedRegion(region12);
//				cell17.setCellValue(" ");
//	        }else{
	        CellRangeAddress region11 = new CellRangeAddress(8, (short) 8, 2, (short) 3);
			Cell cell16=row8.createCell(2);
			utils.setRegionStyle(sh, region11, utils.Style7(workbook));
			sh.addMergedRegion(region11);
//				if(jianLiA08ZZ.getEducational()==null||jianLiA08ZZ.getEducational().equals("")){
//					cell16.setCellValue(" ");//A0801A  在职学历
//			    }else{
			cell16.setCellValue(aa.get(0).getEducational());//A0801A  在职学历
//			    }
				
			CellRangeAddress region12 = new CellRangeAddress(9, (short) 9, 2, (short) 3);
			Cell cell17=row9.createCell(2);
			utils.setRegionStyle(sh, region12, utils.Style8(workbook));
			sh.addMergedRegion(region12);
//				if(jianLiA08ZZ.getDegree()==null||jianLiA08ZZ.getDegree().equals("")){
//					 cell17.setCellValue(" ");//A0901A  在职学位
//			    }else{
			cell17.setCellValue(aa.get(0).getDegree());//A0901A  在职学位
			    
	        	
	        

	        Row row10 = sh.createRow(10);
	        row10.setHeightInPoints(32);
	        
	        CellRangeAddress region120 = new CellRangeAddress(10, (short) 10, 0, (short) 1);
	        Cell cell021=row10.createCell(0);
	        utils.setRegionStyle(sh, region120, utils.Style6(workbook));
			sh.addMergedRegion(region120);
			cell021.setCellValue("现 任 职 务");
			
	        CellRangeAddress region14 = new CellRangeAddress(10, (short) 10, 2, (short)6);
			Cell cell19=row10.createCell(2);
			utils.setRegionStyle(sh, region14, utils.Style81(workbook));
			sh.addMergedRegion(region14);
	        cell19.setCellValue(jianLiA01.getJobNameL());//A0192A  工作单位及职务全称(现在职务)
	        
	        Row row11 = sh.createRow(11);
	        row11.setHeightInPoints(32);
	        
	        CellRangeAddress region140 = new CellRangeAddress(11, (short) 11, 0, (short) 1);
	        Cell cell022=row11.createCell(0);
	        utils.setRegionStyle(sh, region140, utils.Style6(workbook));
			sh.addMergedRegion(region140);
			cell022.setCellValue("拟 任 职 务");
	        
	        CellRangeAddress region15 = new CellRangeAddress(11, (short) 11, 2, (short) 6);
			Cell cell20=row11.createCell(4);
			utils.setRegionStyle(sh, region15, utils.Style6(workbook));
			sh.addMergedRegion(region15);
	        cell20.setCellValue("");//拟任职务
	        
	        Row row12 = sh.createRow(12);
	        row12.setHeightInPoints(32);
	        
	        CellRangeAddress region150 = new CellRangeAddress(12, (short) 12, 0, (short) 1);
	        Cell cell023=row12.createCell(0);
	        utils.setRegionStyle(sh, region150, utils.Style6(workbook));
			sh.addMergedRegion(region150);
	        cell023.setCellValue("拟 免 职 务");
	        
	        CellRangeAddress region16 = new CellRangeAddress(12, (short) 12, 2, (short) 6);
			Cell cell21=row12.createCell(4);
			utils.setRegionStyle(sh, region16, utils.Style6(workbook));
			sh.addMergedRegion(region16);
	        cell21.setCellValue("");//拟免职务
	        
	        Row row13 = sh.createRow(13);
	        row13.setHeightInPoints(350);
	        
			Cell cell024=row13.createCell(0);
			cell024.setCellStyle(utils.Style6(workbook));
	        cell024.setCellValue("简历");
	        
	        CellRangeAddress region17 = new CellRangeAddress(13, (short) 13, 1, (short) 6);
			Cell cell22=row13.createCell(1);
			utils.setRegionStyle(sh, region17, utils.Style11(workbook));
			sh.addMergedRegion(region17);
			String jianli =jianLiA01.getResume();
			String[] jianli1 = jianli.split("\r\n");
	        cell22.setCellValue(jianLiA01.getResume());//A1701  简历
	        
	      //对应Excel文件中的sheet，0代表第一个
			Sheet sh1 = xssfWorkbook.getSheetAt(1); 
			
			Row row001 = sh1.createRow(1);
			row001.setHeightInPoints(66);
			
			Cell cell025=row001.createCell(0);
			cell025.setCellStyle(utils.Style9(workbook));
	        cell025.setCellValue("奖惩情况");
			
	        CellRangeAddress region18 = new CellRangeAddress(1, (short) 1, 1, (short) 6);
			Cell cell23=row001.createCell(1);
			utils.setRegionStyle1(sh1, region18, utils.Style6(workbook));
			sh1.addMergedRegion(region18);
	        cell23.setCellValue(jianLiA01.getAwardAndPenalty());//A14Z101  奖罚情况
	        
	        Row row002 = sh1.createRow(2);
	        row002.setHeightInPoints(53);
	        
	        Cell cell026=row002.createCell(0);
			cell026.setCellStyle(utils.Style9(workbook));
	        cell026.setCellValue("年度考核结果");
	        
	        CellRangeAddress region19 = new CellRangeAddress(2, (short) 2, 1, (short) 6);
			Cell cell24=row002.createCell(1);
			utils.setRegionStyle1(sh1, region19, utils.Style6(workbook));
			sh1.addMergedRegion(region19);
	        cell24.setCellValue(jianLiA01.getYearResult());//A15Z101  年度考核结果
	        
	        Row row003 = sh1.createRow(3);
	        row003.setHeightInPoints(66);
	        
	        Cell cell027=row003.createCell(0);
			cell027.setCellStyle(utils.Style9(workbook));
	        cell027.setCellValue("任免理由");
	        
	        CellRangeAddress region20 = new CellRangeAddress(3, (short) 3, 1, (short)6);
			Cell cell25=row003.createCell(1);
			utils.setRegionStyle1(sh1, region20, utils.Style6(workbook));
			sh1.addMergedRegion(region20);
	        cell25.setCellValue(" ");//任免理由
	        
	        Row row004 = sh1.createRow(4);
	        row004.setHeightInPoints(34);
	        
	        Cell cell27=row004.createCell(1);
			cell27.setCellStyle(utils.Style6(workbook));
	        cell27.setCellValue("称 谓");
	        
	        Cell cell28=row004.createCell(2);
			cell28.setCellStyle(utils.Style6(workbook));
	        cell28.setCellValue("姓 名");
	        
	        Cell cell29=row004.createCell(3);
			cell29.setCellStyle(utils.Style6(workbook));
	        cell29.setCellValue("年 龄");
	        
	        Cell cell30=row004.createCell(4);
			cell30.setCellStyle(utils.Style9(workbook));
	        cell30.setCellValue("政治面貌");
	        
	        Row row005 = sh1.createRow(5);
	        row005.setHeightInPoints(34);
	        Cell cell37=row005.createCell(1);
			cell37.setCellStyle(utils.Style6(workbook));
	        cell37.setCellValue("");
	        Cell cell38=row005.createCell(2);
			cell38.setCellStyle(utils.Style6(workbook));
	        cell38.setCellValue("");
	        Cell cell39=row005.createCell(3);
			cell39.setCellStyle(utils.Style6(workbook));
	        cell39.setCellValue("");
	        Cell cell40=row005.createCell(4);
			cell40.setCellStyle(utils.Style6(workbook));
	        cell40.setCellValue("");
	        Cell cell400=row005.createCell(5);
			cell400.setCellStyle(utils.Style6(workbook));
	        cell400.setCellValue("");
	        Cell cell4000=row005.createCell(6);
			cell4000.setCellStyle(utils.Style6(workbook));
	        cell4000.setCellValue("");
	        
	        Row row006 = sh1.createRow(6);
	        row006.setHeightInPoints(34);
	        Cell cell41=row006.createCell(1);
			cell41.setCellStyle(utils.Style6(workbook));
	        cell41.setCellValue("");
	        Cell cell42=row006.createCell(2);
			cell42.setCellStyle(utils.Style6(workbook));
	        Cell cell43=row006.createCell(3);
			cell43.setCellStyle(utils.Style6(workbook));
	        cell43.setCellValue("");
	        Cell cell44=row006.createCell(4);
			cell44.setCellStyle(utils.Style6(workbook));
	        cell44.setCellValue("");
	        Cell cell440=row006.createCell(5);
			cell440.setCellStyle(utils.Style6(workbook));
	        cell440.setCellValue("");
	        Cell cell4401=row006.createCell(6);
			cell4401.setCellStyle(utils.Style6(workbook));
	        cell4401.setCellValue("");
	        
	        Row row007 = sh1.createRow(7);
	        row007.setHeightInPoints(34);
	        Cell cell45=row007.createCell(1);
			cell45.setCellStyle(utils.Style6(workbook));
	        cell45.setCellValue("");
	        Cell cell46=row007.createCell(2);
			cell46.setCellStyle(utils.Style6(workbook));
	        cell46.setCellValue("");
	        Cell cell47=row007.createCell(3);
			cell47.setCellStyle(utils.Style6(workbook));
	        cell47.setCellValue("");
	        Cell cell48=row007.createCell(4);
			cell48.setCellStyle(utils.Style6(workbook));
	        cell48.setCellValue("");
	        Cell cell480=row007.createCell(5);
			cell480.setCellStyle(utils.Style6(workbook));
	        cell480.setCellValue("");
	        Cell cell4801=row007.createCell(6);
			cell4801.setCellStyle(utils.Style6(workbook));
	        cell4801.setCellValue("");
	        
	        Row row008 = sh1.createRow(8);
	        row008.setHeightInPoints(34);
	        Cell cell49=row008.createCell(1);
			cell49.setCellStyle(utils.Style6(workbook));
	        cell49.setCellValue("");
	        Cell cell50=row008.createCell(2);
			cell50.setCellStyle(utils.Style6(workbook));
	        cell50.setCellValue("");
	        Cell cell51=row008.createCell(3);
			cell51.setCellStyle(utils.Style6(workbook));
	        cell51.setCellValue("");
	        Cell cell52=row008.createCell(4);
			cell52.setCellStyle(utils.Style6(workbook));
	        cell52.setCellValue("");
	        Cell cell520=row008.createCell(5);
			cell520.setCellStyle(utils.Style6(workbook));
	        cell520.setCellValue("");
	        Cell cell5201=row008.createCell(6);
			cell5201.setCellStyle(utils.Style6(workbook));
	        cell5201.setCellValue("");
	        
	        Row row009 = sh1.createRow(9);
	        row009.setHeightInPoints(34);
	        Cell cell53=row009.createCell(1);
			cell53.setCellStyle(utils.Style6(workbook));
	        cell53.setCellValue("");
	        Cell cell54=row009.createCell(2);
			cell54.setCellStyle(utils.Style6(workbook));
	        cell54.setCellValue("");
	        Cell cell55=row009.createCell(3);
			cell55.setCellStyle(utils.Style6(workbook));
	        cell55.setCellValue("");
	        Cell cell56=row009.createCell(4);
			cell56.setCellStyle(utils.Style6(workbook));
	        cell56.setCellValue("");
	        Cell cell560=row009.createCell(5);
			cell560.setCellStyle(utils.Style6(workbook));
	        cell560.setCellValue("");
	        Cell cell5601=row009.createCell(6);
			cell5601.setCellStyle(utils.Style6(workbook));
	        cell5601.setCellValue("");
	        
	        Row row0010 = sh1.createRow(10);
	        row0010.setHeightInPoints(34);
	        Cell cell57=row0010.createCell(1);
			cell57.setCellStyle(utils.Style6(workbook));
	        cell57.setCellValue("");
	        Cell cell58=row0010.createCell(2);
			cell58.setCellStyle(utils.Style6(workbook));
	        cell58.setCellValue("");
	        Cell cell59=row0010.createCell(3);
			cell59.setCellStyle(utils.Style6(workbook));
	        cell59.setCellValue("");
	        Cell cell60=row0010.createCell(4);
			cell60.setCellStyle(utils.Style6(workbook));
	        cell60.setCellValue("");
	        Cell cell600=row0010.createCell(5);
			cell600.setCellStyle(utils.Style6(workbook));
	        cell600.setCellValue("");
	        Cell cell6001=row0010.createCell(6);
			cell6001.setCellStyle(utils.Style6(workbook));
	        cell6001.setCellValue("");
	        
	        Row row0011 = sh1.createRow(11);
	        row0011.setHeightInPoints(34);
	        Cell cell61=row0011.createCell(1);
			cell61.setCellStyle(utils.Style6(workbook));
	        cell61.setCellValue("");
	        Cell cell62=row0011.createCell(2);
			cell62.setCellStyle(utils.Style6(workbook));
	        cell62.setCellValue("");
	        Cell cell63=row0011.createCell(3);
			cell63.setCellStyle(utils.Style6(workbook));
	        cell63.setCellValue("");
	        Cell cell64=row0011.createCell(4);
			cell64.setCellStyle(utils.Style6(workbook));
	        cell64.setCellValue("");
	        Cell cell640=row0011.createCell(5);
			cell640.setCellStyle(utils.Style6(workbook));
	        cell640.setCellValue("");
	        Cell cell6401=row0011.createCell(6);
			cell6401.setCellStyle(utils.Style6(workbook));
	        cell6401.setCellValue("");
	        
	        CellRangeAddress region22 = new CellRangeAddress(4, (short) 4, 5, (short)6);
			Cell cell31=row004.createCell(5);
			utils.setRegionStyle1(sh1, region22, utils.Style9(workbook));
			sh1.addMergedRegion(region22);
	        cell31.setCellValue("工 作 单 位 及 职 位");
	        
	        List<JianLiA36> jianLiA36 = jianLiA36Dao.selectA36(id);
	        for(int j=0; j<jianLiA36.size(); j++) {
	        	Row row = sh1.createRow(j+5);
	        	row.setHeightInPoints(34);
				Cell cell32=row.createCell(1);
				cell32.setCellStyle(utils.Style6(workbook));
		        cell32.setCellValue(jianLiA36.get(j).getJRcall());//A3604A 家人称谓
		        
		        Cell cell33=row.createCell(2);
				cell33.setCellStyle(utils.Style6(workbook));
				cell33.setCellValue(jianLiA36.get(j).getJRname());//A3601   家人姓名
				
				Cell cell34=row.createCell(3);
				cell34.setCellStyle(utils.Style6(workbook));
				if(jianLiA36.get(j).getJRbirthDay()!=null&&jianLiA36.get(j).getJRbirthDay().length()==0){
					cell34.setCellValue(" ");//A0107     出生年月1
			    }else if(jianLiA36.get(j).getJRbirthDay()!=null&&jianLiA36.get(j).getJRbirthDay().length()>4){
			    	String JRbirthDay=jianLiA36.get(j).getJRbirthDay();		//2019
			    	String JRyear =JRbirthDay.substring(0,4);
			    	int i = Integer.parseInt(JRyear);
					Calendar date = Calendar.getInstance();
					String year = String.valueOf(date.get(Calendar.YEAR));
					int k = Integer.parseInt(year);
					int age =k-i;
					cell34.setCellValue(age);//A3607 家人出生年月  算出年龄
				}
				
				Cell cell35=row.createCell(4);
				cell35.setCellStyle(utils.Style6(workbook));
				cell35.setCellValue(jianLiA36.get(j).getJRpolitics());//A3627 家人政治面貌
				
				CellRangeAddress region23 = new CellRangeAddress(j+5, (short) j+5, 5, (short)6);
				Cell cell36=row.createCell(5);
				utils.setRegionStyle1(sh1, region23, utils.Style9(workbook));
				sh1.addMergedRegion(region23);
		        cell36.setCellValue(jianLiA36.get(j).getJRjobPlace());//A3611 家人工作单位及职位
		        
	        }
	        
//	        Row row0011 = sh1.createRow(11);
//	        row0011.setHeightInPoints(34);
	        CellRangeAddress region21 = new CellRangeAddress(4, (short) 11, 0, (short)0);
			Cell cell26=row004.createCell(0);
			utils.setRegionStyle1(sh1, region21, utils.Style9(workbook));
			sh1.addMergedRegion(region21);
	        cell26.setCellValue("家庭主要成员及重要社会关系");
	        
			String title = jianLiA01.getName()+"简历表";
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
		Map<String,Object> map2=new HashMap<>();
		for(int i =0;i<selectA01.size();i++) {
			if(selectA01.get(i).getBirthDay()!=null && selectA01.get(i).getBirthDay()!=" " && !selectA01.get(i).getBirthDay().equals("")) {
				if(selectA01.get(i).getBirthDay().length()<7) {
					selectA01.get(i).setBirthDay(selectA01.get(i).getBirthDay().substring(0, 4)+"."+selectA01.get(i).getBirthDay().substring(4));
				}else {
					selectA01.get(i).setBirthDay(selectA01.get(i).getBirthDay().substring(0, 4)+"."+selectA01.get(i).getBirthDay().substring(4,6)+"."+selectA01.get(i).getBirthDay().substring(6));
				}
			}
			
			if(selectA01.get(i).getJobLevelTime()!=null && selectA01.get(i).getJobLevelTime()!=" " && !selectA01.get(i).getJobLevelTime().equals("")) {
				if(selectA01.get(i).getJobLevelTime().length()<7) {
					selectA01.get(i).setJobLevelTime(selectA01.get(i).getJobLevelTime().substring(0, 4)+"."+selectA01.get(i).getJobLevelTime().substring(4));
				}else {
					selectA01.get(i).setJobLevelTime(selectA01.get(i).getJobLevelTime().substring(0, 4)+"."+selectA01.get(i).getJobLevelTime().substring(4,6)+"."+selectA01.get(i).getJobLevelTime().substring(6));
				}
			}
			
			if(selectA01.get(i).getWorkingTime()!=null && selectA01.get(i).getWorkingTime()!=" " &&  !selectA01.get(i).getWorkingTime().equals("")) {
				if(selectA01.get(i).getWorkingTime().length()<7) {
					selectA01.get(i).setWorkingTime(selectA01.get(i).getWorkingTime().substring(0, 4)+"."+selectA01.get(i).getWorkingTime().substring(4));
				}else {
					selectA01.get(i).setWorkingTime(selectA01.get(i).getWorkingTime().substring(0, 4)+"."+selectA01.get(i).getWorkingTime().substring(4,6)+"."+selectA01.get(i).getWorkingTime().substring(6));
				}
			}
			
			if(selectA01.get(i).getEmployTime()!=null && selectA01.get(i).getEmployTime()!=" " &&  !selectA01.get(i).getEmployTime().equals("")) {
				if(selectA01.get(i).getEmployTime().length()<7) {
					selectA01.get(i).setEmployTime(selectA01.get(i).getEmployTime().substring(0, 4)+"."+selectA01.get(i).getEmployTime().substring(4));
				}else {
					selectA01.get(i).setEmployTime(selectA01.get(i).getEmployTime().substring(0, 4)+"."+selectA01.get(i).getEmployTime().substring(4,6)+"."+selectA01.get(i).getEmployTime().substring(6));
				}
			}
			
			if(selectA01.get(i).getTransferringTime()!=null && selectA01.get(i).getTransferringTime()!=" " &&  !selectA01.get(i).getTransferringTime().equals("")) {
				if(selectA01.get(i).getWorkingTime().length()<7) {
					selectA01.get(i).setTransferringTime(selectA01.get(i).getTransferringTime().substring(0, 4)+"."+selectA01.get(i).getTransferringTime().substring(4));
				}else {
					selectA01.get(i).setTransferringTime(selectA01.get(i).getTransferringTime().substring(0, 4)+"."+selectA01.get(i).getTransferringTime().substring(4,6)+"."+selectA01.get(i).getTransferringTime().substring(6));
				}
			}
			map2.put("A01", selectA01.get(i));
			
			List<JianLiA08> aa = new ArrayList<>();
			List<JianLiA08> selectQRZ = jianLiA08Dao.selectQRZ(selectA01.get(0).getAid());
			int ii=0;
			if(selectQRZ!=null) {
				for(int w=0;w<selectQRZ.size();w++) {
					if(selectQRZ.size()==1 && ii<1) {			//只查询出一条全日制
						aa.add(selectQRZ.get(w));
						ii++;
					}else if(selectQRZ.size()>1 && ii<1) {								//查询出多条全日制
						if(selectQRZ.get(w).getDegree()!=null && selectQRZ.get(w).getDegree()!="" && !selectQRZ.get(w).getDegree().equals("") ) {		
							aa.add(selectQRZ.get(w));
							ii++;
						}else if(w==(selectQRZ.size()-1)) {
							aa.add(selectQRZ.get(w));
							ii++;
						}
					}
				}
			}
			ii=0;
			List<JianLiA08> selectZZ = jianLiA08Dao.selectZZ(selectA01.get(0).getAid());
			
			if(selectZZ!=null) {
				for(int w=0;w<selectZZ.size();w++) {
					if(selectZZ.size()==1 && ii<1) {			//只查询出一条在职教育
						aa.add(selectZZ.get(w));
						ii++;
					}else if(selectZZ.size()>1 && ii<1) {								//查询出多条在职教育
						if(selectZZ.get(w).getDegree()!=null && selectZZ.get(w).getDegree()!="" && !selectZZ.get(w).getDegree().equals("") ) {		
							aa.add(selectZZ.get(w));
							ii++;
						}else if(w==(selectZZ.size()-1)) {
							aa.add(selectZZ.get(w));
							ii++;
						}
					}
				}
			}
			map2.put("A08", aa);
			
		}		//A01 for循环结束
		List<JianLiA36> selectA36 = jianLiA36Dao.selectA36(selectA01.get(0).getAid());
		if(selectA36!=null) {
			for(int p = 0;p<selectA36.size();p++) {
				if(selectA36.get(p).getJRbirthDay()!=null && !selectA36.get(p).getJRbirthDay().equals("") && selectA36.get(p).getJRbirthDay()!=" ") {
					if(selectA36.get(p).getJRbirthDay().length()<7) {
						selectA36.get(p).setJRbirthDay(selectA36.get(p).getJRbirthDay().substring(0, 4)+"."+selectA36.get(p).getJRbirthDay().substring(4));
					}else {
						selectA36.get(p).setJRbirthDay(selectA36.get(p).getJRbirthDay().substring(0, 4)+"."+selectA36.get(p).getJRbirthDay().substring(4,6)+"."+selectA36.get(p).getJRbirthDay().substring(6));
					}
				}
			}
		}
		map2.put("A36", selectA36);
		
		
		JianLiA39 selectA39 = jianLiA39Dao.selectA39(selectA01.get(0).getAid());
		if(selectA39!=null) {
			if(selectA39.getJoinTime()!=null && selectA39.getJoinTime()!=" " && !selectA39.getJoinTime().equals("")) {
				if(selectA39.getJoinTime().length()<7) {
					selectA39.setJoinTime(selectA39.getJoinTime().substring(0, 4)+"."+selectA39.getJoinTime().substring(4));
				}else {
					selectA39.setJoinTime(selectA39.getJoinTime().substring(0, 4)+"."+selectA39.getJoinTime().substring(4,6)+"."+selectA39.getJoinTime().substring(6));
				}
			}
			map2.put("A39", selectA39);
		}else {
			JianLiA39 jl = new JianLiA39();
			jl.setJoinTime(null);
			map2.put("A39", jl);
		}
		
		JianLiA01 url = jianLiA01Dao.selectImgUrl(jianli);
		String imgurl="D:\\zzbhr\\apache-tomcat-6.0.26\\webapps\\zzbhr\\"+url.getImgUrl();
		String base64Str = Base64Transformation.imageToBase64Str(imgurl).replaceAll("\r\n","");
		map2.put("url", base64Str);
		
		String id=jianli.getAid();
		List<JianLiA08> selectQRZYX = jianLiA08Dao.selectQRZYX(id);

		
		
		list.add(map2);
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


	@Override
	public List<JianLiA01> screenList(ScreeningDTO sc) {
		// TODO Auto-generated method stuby
		Map<String,Object> map1 = new HashMap<>();
		map1.put("sexValue", sc.getSexValue());
		map1.put("age_highest",sc.getAge_highest() );
		map1.put("age_lowest", sc.getAge_lowest());
		map1.put("nationValue", sc.getNationValue());
		map1.put("place", sc.getPlace());
		map1.put("educationBg_one",sc.getEducationBg_one() );
		map1.put("educationBg_two", sc.getEducationBg_two());
		map1.put("department", sc.getDepartment());
		map1.put("duty", sc.getDuty());
		map1.put("CurrentLevel", sc.getCurrentLevel());
		map1.put("Joblevel", sc.getJoblevel());
		map1.put("personnel", sc.getPersonnel());
		map1.put("manage", sc.getManage());
		map1.put("Compile", sc.getCompile());
		map1.put("NewJoblevel", sc.getNewJoblevel());
		map1.put("employTime", sc.getEmployTime());
		map1.put("joinTime", sc.getJoinTime());
		map1.put("transferring", sc.getTransferring());
		List<JianLiA01> screenA01 = jianLiA01Dao.screenA01(map1);
		if(screenA01.size()<=0) {
			return null;
		}
		List<JianLiA01> list =  new ArrayList<>();		//存放查询完A02的  结果ID
		Map<String,Object> map = new HashMap<>();
		if(sc.getEducationBg_one()!=null && sc.getEducationBg_one()!="" && sc.getEducationBg_one()!="null") {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int a =0;a<screenA01.size();a++) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(screenA01.get(a).getAid());
				JianLiA01 screenA02 = jianLiA01Dao.screenA02(jl);
				list2.add(screenA02);
			}
			list.clear();
			for(int b=0;b<list2.size();b++) {
				list.add(list2.get(b));
			}
		}
	
		
		if(sc.getEducationBg_two()!=null && sc.getEducationBg_two()!="" && sc.getEducationBg_two()!="null" && list.size()>0) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int b=0;b<list.size();b++) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(b).getAid());
				JianLiA01 screenA03 = jianLiA01Dao.screenA03(jl);
				list2.add(screenA03);
			}
			list.clear();
			for(int b=0;b<list2.size();b++) {
				list.add(list2.get(b));
			}
		}
		
		if(sc.getDepartment()!=null && sc.getDepartment()!="" && sc.getDepartment()!="null" && list.size()>0) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int c=0;c<list.size();c++ ) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(c).getAid());
				JianLiA01 screenA04 = jianLiA01Dao.screenA04(jl);
				list2.add(screenA04);
			}
			list.clear();
			for(int b=0;b<list2.size();b++) {
				list.add(list2.get(b));
			}
		}
		
		if(sc.getDuty()!=null && sc.getDuty()!="" && sc.getDuty()!="null" && list.size()>0) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int c=0;c<list.size();c++ ) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(c).getAid());
				JianLiA01 screenA05 = jianLiA01Dao.screenA05(jl);
				list2.add(screenA05);
			}
			list.clear();
			for(int b=0;b<list2.size();b++) {
				list.add(list2.get(b));
			}
		}
		
		if(sc.getJoinTime()!=null && sc.getJoinTime()!="" && sc.getJoinTime()!="null" && list.size()>0) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int c=0;c<list.size();c++ ) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(c).getAid());
				JianLiA01 screenA06 = jianLiA01Dao.screenA06(jl);
				list2.add(screenA06);
			}
			list.clear();
			for(int b=0;b<list2.size();b++) {
				list.add(list2.get(b));
			}
		}
		return list;
	}

	


}
