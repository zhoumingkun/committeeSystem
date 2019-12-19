package com.toughguy.committeeSystem.service.content.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import com.toughguy.committeeSystem.model.content.RsUnmk;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA01Dao;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA08Dao;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA36Dao;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA39Dao;
import com.toughguy.committeeSystem.persist.content.prototype.IRsUnmkDao;
import com.toughguy.committeeSystem.service.content.prototype.IJianLiA01Service;
import com.toughguy.committeeSystem.service.content.prototype.IRsUnmkService;
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
	
	@Autowired
	private IRsUnmkDao rsUnmkDaoImpl;
	
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
//				if(jianLiA01.getJobLevelTime()!=null&&jianLiA01.getJobLevelTime().length()==0){
//					createCell6.setCellValue("");//A0173  任现职务层次时间
//			    }else if(jianLiA01.getJobLevelTime()!=null&&jianLiA01.getJobLevelTime().length()>4){
//					String year =jianLiA01.getJobLevelTime().substring(0,4);		//2019
//					createCell6.setCellValue(year);//A0173  任现职务层次时间
//				}else if(jianLiA01.getJobLevelTime()!=null&&jianLiA01.getJobLevelTime().length()>7){
//					String year =jianLiA01.getJobLevelTime().substring(0,4);		//2019
//					String month =jianLiA01.getJobLevelTime().substring(5,7);
//					createCell6.setCellValue(year+"."+month);//A0173  任现职务层次时间
//				}else if(jianLiA01.getJobLevelTime()!=null&&jianLiA01.getJobLevelTime().length()>8){
//					String year =jianLiA01.getJobLevelTime().substring(0,4);		//2019
//					String month =jianLiA01.getJobLevelTime().substring(5,7);
//					String day =jianLiA01.getJobLevelTime().substring(8);//09
//					String  jobLevelTime= year+"."+month+"."+day+".";
//					createCell6.setCellValue(jobLevelTime);//A0173  任现职务层次时间
//				}
				
				if(jianLiA01.getJobLevelTime()!=null&&jianLiA01.getJobLevelTime()!=" "&& !jianLiA01.getJobLevelTime().equals("")&&jianLiA01.getJobLevelTime().length()!=0){
				    if(jianLiA01.getJobLevelTime().length()==4){
						String year =jianLiA01.getWorkingTime().substring(0,4);		//2019
						createCell6.setCellValue(year);//A0173  任现职务层次时间
					}else if(jianLiA01.getJobLevelTime().length()>4&&jianLiA01.getJobLevelTime().length()<7){
						String year =jianLiA01.getJobLevelTime().substring(0,4);		//2019
						String month =jianLiA01.getJobLevelTime().substring(4);
						createCell6.setCellValue(year+"."+month);//A0173  任现职务层次时间
					}else if(jianLiA01.getJobLevelTime().length()==8){
						String year =jianLiA01.getJobLevelTime().substring(0,4);		//2019
						String month =jianLiA01.getJobLevelTime().substring(4,6);
						String day =jianLiA01.getJobLevelTime().substring(6);//09
						String  BirthDay= year+"."+month+"."+day+".";
						createCell6.setCellValue(BirthDay);//A0173  任现职务层次时间
					}
				}else{
					createCell6.setCellValue("");//A0173  任现职务层次时间
				}
				
				Cell createCell7 = row.createCell(6);
				createCell7.setCellStyle(utils.Style6(workbook));
				createCell7.setCellValue(jianLiA01.getGrade()); //A0192d  现职级
				
				Cell createCell8 = row.createCell(7);
				createCell8.setCellStyle(utils.Style6(workbook));
//				if(jianLiA01.getGradeTime()!=null&&jianLiA01.getGradeTime().length()==0){
//					createCell8.setCellValue(""); //A0192c  任现职级时间
//			    }else if(jianLiA01.getGradeTime()!=null&&jianLiA01.getGradeTime().length()>4){
//					String year =jianLiA01.getGradeTime().substring(0,4);		//2019
//					createCell8.setCellValue(year); //A0192c  任现职级时间
//				}else if(jianLiA01.getGradeTime()!=null&&jianLiA01.getGradeTime().length()>7){
//					String year =jianLiA01.getGradeTime().substring(0,4);		//2019
//					String month =jianLiA01.getGradeTime().substring(5,7);
//					createCell8.setCellValue(year+"."+month); //A0192c  任现职级时间
//				}else if(jianLiA01.getGradeTime()!=null&&jianLiA01.getGradeTime().length()>8){
//					String year =jianLiA01.getGradeTime().substring(0,4);		//2019
//					String month =jianLiA01.getGradeTime().substring(5,7);
//					String day =jianLiA01.getGradeTime().substring(8);//09
//					String  gradeTime= year+"."+month+"."+day+".";
//					createCell8.setCellValue(gradeTime); //A0192c  任现职级时间
//				}
				if(jianLiA01.getGradeTime()!=null&&jianLiA01.getGradeTime()!=" "&& !jianLiA01.getGradeTime().equals("")&&jianLiA01.getGradeTime().length()!=0){
				    if(jianLiA01.getGradeTime().length()==4){
						String year =jianLiA01.getGradeTime().substring(0,4);		//2019
						createCell8.setCellValue(year);//A0192c  任现职级时间
					}else if(jianLiA01.getGradeTime().length()>4&&jianLiA01.getGradeTime().length()<7){
						String year =jianLiA01.getGradeTime().substring(0,4);		//2019
						String month =jianLiA01.getGradeTime().substring(4);
						createCell8.setCellValue(year+"."+month);//A0192c  任现职级时间
					}else if(jianLiA01.getGradeTime().length()==8){
						String year =jianLiA01.getGradeTime().substring(0,4);		//2019
						String month =jianLiA01.getGradeTime().substring(4,6);
						String day =jianLiA01.getGradeTime().substring(6);//09
						String  BirthDay= year+"."+month+"."+day+".";
						createCell8.setCellValue(BirthDay);//A0192c  任现职级时间
					}
				}else{
					createCell8.setCellValue("");//A0192c  任现职级时间
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
        BufferedImage bufferImg = null;  
		try {
			
			//输入模板文件
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream("upload/base/简历表（模板3）.xlsx"));
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
			if(jianLiA01.getBirthDay()!=null&&jianLiA01.getBirthDay()!=" "&& !jianLiA01.getBirthDay().equals("")&&jianLiA01.getBirthDay().length()!=0){
			    if(jianLiA01.getBirthDay().length()==4){
					String year =jianLiA01.getBirthDay().substring(0,4);		//2019
					cell3.setCellValue(year);//A0107     出生年月1
				}else if(jianLiA01.getBirthDay().length()>4&&jianLiA01.getBirthDay().length()<7){
					String year =jianLiA01.getBirthDay().substring(0,4);		//2019
					String month =jianLiA01.getBirthDay().substring(4);
					cell3.setCellValue(year+"."+month);//A0107     出生年月1
				}else if(jianLiA01.getBirthDay().length()==8){
					String year =jianLiA01.getBirthDay().substring(0,4);		//2019
					String month =jianLiA01.getBirthDay().substring(4,6);
					String day =jianLiA01.getBirthDay().substring(6);//09
					String  BirthDay= year+"."+month+"."+day+".";
					cell3.setCellValue(BirthDay);//A0107     出生年月1
				}
			}else{
				cell3.setCellValue("");//A0107     出生年月1
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
			if(jianLiA39.getJoinTime()!=null&&jianLiA39.getJoinTime()!=" "&& !jianLiA39.getJoinTime().equals("")&&jianLiA39.getJoinTime().length()!=0){
			    if(jianLiA39.getJoinTime().length()==4){
					String year =jianLiA39.getJoinTime().substring(0,4);		//2019
					cell7.setCellValue(year);//A0144  入党时间
				}else if(jianLiA39.getJoinTime().length()>4&&jianLiA39.getJoinTime().length()<7){
					String year =jianLiA39.getJoinTime().substring(0,4);		//2019
					String month =jianLiA39.getJoinTime().substring(4);
					cell7.setCellValue(year+"."+month);//A0144  入党时间
				}else if(jianLiA39.getJoinTime().length()==8){
					String year =jianLiA39.getJoinTime().substring(0,4);		//2019
					String month =jianLiA39.getJoinTime().substring(4,6);
					String day =jianLiA39.getJoinTime().substring(6);//09
					String  BirthDay= year+"."+month+"."+day+".";
					cell7.setCellValue(BirthDay);//A0144  入党时间
				}
			}else{
				cell7.setCellValue("");//A0144  入党时间
			}
//			cell7.setCellValue(jianLiA39.getJoinTime());//A0144  入党时间
			
			Cell cell010=row4.createCell(2);
			cell010.setCellStyle(utils.Style9(workbook));
			cell010.setCellValue("参加工作时间");
			
			Cell cell8=row4.createCell(3);
			cell8.setCellStyle(utils.Style6(workbook));
			if(jianLiA01.getWorkingTime()!=null&&jianLiA01.getWorkingTime()!=" "&& !jianLiA01.getWorkingTime().equals("")&&jianLiA01.getWorkingTime().length()!=0){
			    if(jianLiA01.getWorkingTime().length()==4){
					String year =jianLiA01.getWorkingTime().substring(0,4);		//2019
					cell8.setCellValue(year);//A0144  入党时间
				}else if(jianLiA01.getWorkingTime().length()>4&&jianLiA01.getWorkingTime().length()<7){
					String year =jianLiA01.getWorkingTime().substring(0,4);		//2019
					String month =jianLiA01.getWorkingTime().substring(4);
					cell8.setCellValue(year+"."+month);//A0144  入党时间
				}else if(jianLiA01.getWorkingTime().length()==8){
					String year =jianLiA01.getWorkingTime().substring(0,4);		//2019
					String month =jianLiA01.getWorkingTime().substring(4,6);
					String day =jianLiA01.getWorkingTime().substring(6);//09
					String  BirthDay= year+"."+month+"."+day+".";
					cell8.setCellValue(BirthDay);//A0144  入党时间
				}
			}else{
				cell8.setCellValue("");//A0144  入党时间
			}
//			cell8.setCellValue(jianLiA01.getWorkingTime());//A0134     参加工作时间
	        
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
	        Cell cell0132=row4.createCell(6);
			cell0132.setCellStyle(utils.Style6(workbook));
	        cell0132.setCellValue("");
	        Cell cell0133=row3.createCell(6);
			cell0133.setCellStyle(utils.Style6(workbook));
	        cell0133.setCellValue("");
	        Cell cell0134=row2.createCell(6);
			cell0134.setCellStyle(utils.Style6(workbook));
	        cell0134.setCellValue("");
	        Cell cell0135=row1.createCell(6);
			cell0135.setCellStyle(utils.Style6(workbook));
	        cell0135.setCellValue("");
	        
	        Cell cell012=row5.createCell(0);
			cell012.setCellStyle(utils.Style9(workbook));
	        cell012.setCellValue("专业技术职务");
	        
	        CellRangeAddress region5 = new CellRangeAddress(5, (short) 5, 1, (short) 2);
			Cell cell10=row5.createCell(1);
			utils.setRegionStyle(sh, region5, utils.Style9(workbook));
			sh.addMergedRegion(region5);
			if(jianLiA01.getSpecialityPost()!=null&&jianLiA01.getSpecialityPost()!=" "&& !jianLiA01.getSpecialityPost().equals("")&&jianLiA01.getSpecialityPost().length()!=0){
				cell10.setCellValue(jianLiA01.getSpecialityPost());//A0196  专业技术职务
			}else{
				cell10.setCellValue("");//A0196  专业技术职务
			}
	        Cell cell013=row5.createCell(3);
			cell013.setCellStyle(utils.Style9(workbook));
	        cell013.setCellValue("熟悉专业有何专长");
	        
	        CellRangeAddress region6 = new CellRangeAddress(5, (short) 5, 4, (short) 5);
			Cell cell11=row5.createCell(4);
			utils.setRegionStyle(sh, region6, utils.Style9(workbook));
			sh.addMergedRegion(region6);
			if(jianLiA01.getSpeciality()!=null&&jianLiA01.getSpeciality()!=" "&& !jianLiA01.getSpeciality().equals("")&&jianLiA01.getSpeciality().length()!=0){
				cell11.setCellValue(jianLiA01.getSpeciality());//A0187A   熟悉专业有何专长
			}else{
				cell11.setCellValue("");//A0187A   熟悉专业有何专长;//A0196  专业技术职务
			}
	        
	        JianLiA01 jianliA01=new JianLiA01();
	        jianliA01.setAid(id);
	        JianLiA01 imageurl = jianLiA01Dao.selectImgUrl(jianliA01);
	        if(imageurl!=null&& !imageurl.equals("")){
	        	String imgurl="D:\\zzbhr\\apache-tomcat-6.0.26\\webapps\\zzbhr\\"+imageurl.getImgUrl();
				ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
	            bufferImg = ImageIO.read(new File(imgurl));     
	            ImageIO.write(bufferImg, "jpg", byteArrayOut); 
	           //sheet只能获取一个
	            XSSFDrawing patriarch =(XSSFDrawing) sh.createDrawingPatriarch();
	            int inx = xssfWorkbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG);
	            // 图片位置
	            Drawing drawing = sh.createDrawingPatriarch();  //创建绘图对象
	            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1500, 320, (short) 6, 1, (short) 6, 5);
	            anchor.setAnchorType(3);
	            Picture picture = drawing.createPicture(anchor, inx);
	            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	            picture.resize();
			}else{
				CellRangeAddress region7 = new CellRangeAddress(1, (short) 5, 6, (short) 6);
				Cell cell12=row1.createCell(6);
				utils.setRegionStyle(sh, region7, utils.Style6(workbook));
				sh.addMergedRegion(region7);
		        cell12.setCellValue("（无）");//照片图片
			}
//	        String imgurl="D:\\zzbhr\\apache-tomcat-6.0.26\\webapps\\zzbhr\\"+imageurl.getImgUrl();
//			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();     
//            bufferImg = ImageIO.read(new File(imgurl));     
//            ImageIO.write(bufferImg, "jpg", byteArrayOut); 
//           //sheet只能获取一个
//            XSSFDrawing patriarch =(XSSFDrawing) sh.createDrawingPatriarch();
//            int inx = xssfWorkbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG);
//            // 图片位置
//            Drawing drawing = sh.createDrawingPatriarch();  //创建绘图对象
//            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1500, 320, (short) 6, 1, (short) 6, 5);
//            anchor.setAnchorType(3);
//            Picture picture = drawing.createPicture(anchor, inx);
//            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
//            picture.resize();
//            patriarch.createPicture(anchor, xssfWorkbook.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));

//            https://blog.csdn.net/qingdatiankong/article/details/81566787
	        
	        Row row6 = sh.createRow(6);
	        row6.setHeightInPoints(16);
	        
	        Cell cell015=row6.createCell(1);
			cell015.setCellStyle(utils.Style7(workbook));
	        cell015.setCellValue("全日制");
	        List<JianLiA08> jianLiA08QRZ= jianLiA08Dao.selectQRZ(id);
	        System.out.println(jianLiA08QRZ);
	        
	        CellRangeAddress region8 = new CellRangeAddress(6, (short) 6, 2, (short) 3);
			Cell cell13=row6.createCell(2);
			utils.setRegionStyle(sh, region8, utils.Style7(workbook));
			sh.addMergedRegion(region8);
			
			Row row7 = sh.createRow(7);
	        row7.setHeightInPoints(16);
	        
	        CellRangeAddress region9 = new CellRangeAddress(7, (short) 7, 2, (short) 3);
			Cell cell14=row7.createCell(2);
			utils.setRegionStyle(sh, region9, utils.Style8(workbook));
			sh.addMergedRegion(region9);
			
	        List<JianLiA08> aa = new ArrayList<>();
	        int ii=0;
			if(jianLiA08QRZ!=null&&jianLiA08QRZ.size()>0) {
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
				if(aa.get(0).getEducational()!=null&&aa.get(0).getEducational()!=" "&& !aa.get(0).getEducational().equals("")){
					cell13.setCellValue(aa.get(0).getEducational());//A0801A  在职学历
				}else{
					cell13.setCellValue("");//A0801A  在职学历
				}
				if(aa.get(0).getDegree()!=null&&aa.get(0).getDegree()!=" "&& !aa.get(0).getDegree().equals("")){
					cell14.setCellValue(aa.get(0).getDegree());//A0901A  在职学位
				}else{
					cell14.setCellValue("");//A0901A  在职学位
				}
			}else{
				cell13.setCellValue("");//A0801A  全日制学历  
				cell14.setCellValue("");//A0901A  全日制学位  
			}
			
//	        CellRangeAddress region8 = new CellRangeAddress(6, (short) 6, 2, (short) 3);
//			Cell cell13=row6.createCell(2);
//			utils.setRegionStyle(sh, region8, utils.Style7(workbook));
//			sh.addMergedRegion(region8);
//			if(aa.get(0).getEducational()!=null&&aa.get(0).getEducational()!=" "&& !aa.get(0).getEducational().equals("")){
//				cell13.setCellValue(aa.get(0).getEducational());//A0801A  全日制学历  
//			}else{
//				cell13.setCellValue("");//A0801A  全日制学历  
//				cell14.setCellValue("");//A0901A  全日制学位
//			}
	        
//	        Row row7 = sh.createRow(7);
//	        row7.setHeightInPoints(16);
	        
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
	        
//	        CellRangeAddress region9 = new CellRangeAddress(7, (short) 7, 2, (short) 3);
//			Cell cell14=row7.createCell(2);
//			utils.setRegionStyle(sh, region9, utils.Style8(workbook));
//			sh.addMergedRegion(region9);
			if(aa.get(0).getDegree()!=null&&aa.get(0).getDegree()!=" "&& !aa.get(0).getDegree().equals("")){
				cell14.setCellValue(aa.get(0).getDegree());//A0901A  全日制学位
			}else{
				cell14.setCellValue("");//A0901A  全日制学位
			}
			
			List<JianLiA08> jianLiA08QRZYX = jianLiA08Dao.selectQRZYX(id);
//			System.out.println(jianLiA08QRZYX);
			CellRangeAddress region10 = new CellRangeAddress(6, (short) 7, 5, (short) 6);
			Cell cell15=row6.createCell(5);
			utils.setRegionStyle(sh, region10, utils.Style9(workbook));
			sh.addMergedRegion(region10);
			
			List<JianLiA08> bb = new ArrayList<>();
			int jj=0;
			if(jianLiA08QRZYX!=null&&jianLiA08QRZYX.size()>0) {
				for(int w=0;w<jianLiA08QRZYX.size();w++) {
					if(jianLiA08QRZYX.size()==1 && jj<1) {			//只查询出一条全日制学院
						bb.add(jianLiA08QRZYX.get(w));
						jj++;
					}else if(jianLiA08QRZYX.size()>1 && jj<1) {								//查询出多条全日制
						if(jianLiA08QRZYX.get(w).getMajor()!=null && jianLiA08QRZYX.get(w).getMajor()!="" && !jianLiA08QRZYX.get(w).getMajor().equals("") ) {		
							bb.add(jianLiA08QRZYX.get(w));
							jj++;
						}else if(w==(jianLiA08QRZYX.size()-1)) {
							bb.add(jianLiA08QRZYX.get(w));
							jj++;
						}
					}
				}
				if(bb.get(0).getCollege()!=null&&bb.get(0).getCollege()!=" "&& !bb.get(0).getCollege().equals("")){
					if(bb.get(0).getMajor()!=null&&bb.get(0).getMajor()!=" "&& !bb.get(0).getMajor().equals("")){
						cell15.setCellValue(bb.get(0).getCollege()+bb.get(0).getMajor()+"专业");//ZZXLXX  毕业院校系及专业上 
					}else{
						cell15.setCellValue(bb.get(0).getCollege());//ZZXLXX  毕业院校系及专业上
					}
				}else{
					cell15.setCellValue("");//ZZXLXX  毕业院校系及专业上 
				}
			}else{
				 cell15.setCellValue("");//ZZXLXX  毕业院校系及专业上 
			}
//	        CellRangeAddress region10 = new CellRangeAddress(6, (short) 7, 5, (short) 6);
//			Cell cell15=row6.createCell(5);
//			utils.setRegionStyle(sh, region10, utils.Style9(workbook));
//			sh.addMergedRegion(region10);
	        
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
	        Cell cell019=row9.createCell(1);
			cell019.setCellStyle(utils.Style8(workbook));
	        cell019.setCellValue("教育");
	        
	        CellRangeAddress region11 = new CellRangeAddress(8, (short) 8, 2, (short) 3);
			Cell cell16=row8.createCell(2);
			utils.setRegionStyle(sh, region11, utils.Style7(workbook));
			sh.addMergedRegion(region11);
			
			CellRangeAddress region12 = new CellRangeAddress(9, (short) 9, 2, (short) 3);
			Cell cell17=row9.createCell(2);
			utils.setRegionStyle(sh, region12, utils.Style8(workbook));
			sh.addMergedRegion(region12);
			
			CellRangeAddress region121 = new CellRangeAddress(8, (short) 9, 0, (short) 0);
			Cell cell0171=row8.createCell(0);
			utils.setRegionStyle(sh, region121, utils.Style6(workbook));
			sh.addMergedRegion(region121);
		    cell0171.setCellValue("学 位");  
		        
	        List<JianLiA08> jianLiA08ZZ= jianLiA08Dao.selectZZ(id);
	        System.out.println(jianLiA08ZZ);
	        List<JianLiA08> aa1 = new ArrayList<>();
	        int ii1=0;
	        if(jianLiA08ZZ!=null&&jianLiA08ZZ.size()>0) {
	        	System.out.println("进来了");
				for(int w=0;w<jianLiA08ZZ.size();w++) {
					if(jianLiA08ZZ.size()==1 && ii1<1) {			//只查询出一条在职教育
						aa1.add(jianLiA08ZZ.get(w));
						ii1++;
					}else if(jianLiA08ZZ.size()>1 && ii1<1) {								//查询出多条在职教育
						if(jianLiA08ZZ.get(w).getDegree()!=null && jianLiA08ZZ.get(w).getDegree()!="" && !jianLiA08ZZ.get(w).getDegree().equals("") ) {		
							aa1.add(jianLiA08ZZ.get(w));
							ii1++;
						}else if(w==(jianLiA08ZZ.size()-1)) {
							aa1.add(jianLiA08ZZ.get(w));
							ii1++;
						}
					}
				}
				if(aa1.get(0).getEducational()!=null&&aa1.get(0).getEducational()!=" "&& !aa1.get(0).getEducational().equals("")){
					cell16.setCellValue(aa1.get(0).getEducational());//A0801A  在职学历
				}else{
					cell16.setCellValue("");//A0801A  在职学历
				}
				if(aa1.get(0).getDegree()!=null&&aa1.get(0).getDegree()!=" "&& !aa1.get(0).getDegree().equals("")){
					cell17.setCellValue(aa1.get(0).getDegree());//A0901A  在职学位
				}else{
					cell17.setCellValue("");//A0901A  在职学位
				}
			}else{
				System.out.println("进来了去");
				cell16.setCellValue("");//A0801A  在职学历
				cell17.setCellValue("");//A0901A  在职学位
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
//	        CellRangeAddress region11 = new CellRangeAddress(8, (short) 8, 2, (short) 3);
//			Cell cell16=row8.createCell(2);
//			utils.setRegionStyle(sh, region11, utils.Style7(workbook));
//			sh.addMergedRegion(region11);
//			if(aa1.get(0).getEducational()!=null&&aa1.get(0).getEducational()!=" "&& !aa1.get(0).getEducational().equals("")){
//				cell16.setCellValue(aa1.get(0).getEducational());//A0801A  在职学历
//			}else{
//				cell16.setCellValue("");//A0801A  在职学历
//			}
				
//			CellRangeAddress region12 = new CellRangeAddress(9, (short) 9, 2, (short) 3);
//			Cell cell17=row9.createCell(2);
//			utils.setRegionStyle(sh, region12, utils.Style8(workbook));
//			sh.addMergedRegion(region12);
//			if(aa1.get(0).getDegree()!=null&&aa1.get(0).getDegree()!=" "&& !aa1.get(0).getDegree().equals("")){
//				cell17.setCellValue(aa1.get(0).getDegree());//A0901A  在职学位
//			}else{
//				cell17.setCellValue("");//A0901A  在职学位
//			}
	        List<JianLiA08> jianLiA08ZZYX = jianLiA08Dao.selectZZYX(id);
	        CellRangeAddress region101 = new CellRangeAddress(8, (short) 9, 5, (short) 6);
			Cell cell151=row8.createCell(5);
			utils.setRegionStyle(sh, region101, utils.Style9(workbook));
			sh.addMergedRegion(region101);
	        List<JianLiA08> bb1 = new ArrayList<>();
			int jj1=0;
			if(jianLiA08ZZYX!=null&&jianLiA08ZZYX.size()>0) {
				for(int w=0;w<jianLiA08ZZYX.size();w++) {
					if(jianLiA08ZZYX.size()==1 && jj1<1) {			//只查询出一条在职学院
						bb1.add(jianLiA08ZZYX.get(w));
						jj1++;
					}else if(jianLiA08ZZYX.size()>1 && jj1<1) {								//查询出多条在职
						if(jianLiA08ZZYX.get(w).getMajor()!=null && jianLiA08ZZYX.get(w).getMajor()!="" && !jianLiA08ZZYX.get(w).getMajor().equals("") ) {		
							bb1.add(jianLiA08ZZYX.get(w));
							jj1++;
						}else if(w==(jianLiA08ZZYX.size()-1)) {
							bb1.add(jianLiA08ZZYX.get(w));
							jj1++;
						}
					}
				}
				if(bb1.get(0).getCollege()!=null&&bb1.get(0).getCollege()!=" "&& !bb1.get(0).getCollege().equals("")){
					if(bb1.get(0).getMajor()!=null&&bb1.get(0).getMajor()!=" "&& !bb1.get(0).getMajor().equals("")){
						cell151.setCellValue(bb1.get(0).getCollege()+bb1.get(0).getMajor()+"专业");//ZZXLXX  毕业院校系及专业上 
					}else{
						cell151.setCellValue(bb1.get(0).getCollege());//ZZXLXX  毕业院校系及专业下
					}
				}else{
					cell151.setCellValue("");//ZZXLXX  毕业院校系及专业上下
				}
			}else{
				 cell151.setCellValue("");//ZZXLXX  毕业院校系及专业上下
			}

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
			if(jianLiA01.getJobNameL()!=null&&jianLiA01.getJobNameL()!=" "&& !jianLiA01.getJobNameL().equals("")){
				cell19.setCellValue(jianLiA01.getJobNameL());//A0192A  工作单位及职务全称(现在职务)
			}else{
				cell19.setCellValue("");//A0192A  工作单位及职务全称(现在职务)
			}
	        
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
	        row13.setHeightInPoints(12);
//	        Row row14 = sh.createRow(14);
//	        row14.setHeightInPoints(12);
//	        Row row15 = sh.createRow(15);
//	        row15.setHeightInPoints(12);
//	        Row row16 = sh.createRow(16);
//	        row16.setHeightInPoints(12);
//	        Row row17 = sh.createRow(17);
//	        row17.setHeightInPoints(12);
//	        Row row18 = sh.createRow(18);
//	        row18.setHeightInPoints(12);
//	        Row row19 = sh.createRow(19);
//	        row19.setHeightInPoints(12);
//	        Row row20 = sh.createRow(20);
//	        row20.setHeightInPoints(12);
//	        Row row21 = sh.createRow(21);
//	        row21.setHeightInPoints(12);
//	        Row row22 = sh.createRow(22);
//	        row22.setHeightInPoints(12);
//	        Row row23 = sh.createRow(23);
//	        row23.setHeightInPoints(12);
//	        Row row24 = sh.createRow(24);
//	        row24.setHeightInPoints(12);
//	        Row row25 = sh.createRow(25);
//	        row25.setHeightInPoints(12);
//	        Row row26 = sh.createRow(26);
//	        row26.setHeightInPoints(12);
//	        Row row27 = sh.createRow(27);
//	        row27.setHeightInPoints(12);
//	        Row row28 = sh.createRow(28);
//	        row28.setHeightInPoints(12);
//	        Row row29 = sh.createRow(29);
//	        row29.setHeightInPoints(12);
//	        Row row30 = sh.createRow(30);
//	        row30.setHeightInPoints(12);
//	        Row row31 = sh.createRow(31);
//	        row31.setHeightInPoints(12);
//	        Row row32 = sh.createRow(32);
//	        row32.setHeightInPoints(12);
//	        Row row33 = sh.createRow(33);
//	        row33.setHeightInPoints(12);
//	        Row row34 = sh.createRow(34);
//	        row34.setHeightInPoints(12);
//	        Row row35 = sh.createRow(35);
//	        row35.setHeightInPoints(12);
//	        Row row36 = sh.createRow(36);
//	        row36.setHeightInPoints(12);
//	        Row row37 = sh.createRow(37);
//	        row37.setHeightInPoints(12);
//	        Row row38 = sh.createRow(38);
//	        row38.setHeightInPoints(12);
//	        Row row39 = sh.createRow(39);
//	        row39.setHeightInPoints(12);
//	        Row row40 = sh.createRow(40);
//	        row40.setHeightInPoints(12);
//	        Row row41 = sh.createRow(41);
//	        row41.setHeightInPoints(12);
//	        Row row42 = sh.createRow(42);
//	        row42.setHeightInPoints(12);
	        
	        
//	        CellRangeAddress region1701 = new CellRangeAddress(13, (short) 42, 0, (short) 0);
//			Cell cell024=row13.createCell(0);
//			utils.setRegionStyle(sh, region1701, utils.Style9(workbook));
//			sh.addMergedRegion(region1701);
//	        cell024.setCellValue("简历");
	        
			String jianli =jianLiA01.getResume();
			String[] jianli1 = jianli.split("\r");
//			String jianli1 = jianli.replaceAll("\r\n", "WWW");
			for(int j=0; j<jianli1.length; j++) {
//				cell22.setCellValue(jianli1[j]+"\r\n");//A1701  简历
				Row row = sh.createRow(j+13);
				row.setHeightInPoints(12);
				
		        CellRangeAddress region17 = new CellRangeAddress(j+13, (short) j+13, 1, (short) 6);
				Cell cell22=row.createCell(1);
				utils.setRegionStyle(sh, region17, utils.Style11(workbook));
				sh.addMergedRegion(region17);
				cell22.setCellValue(jianli1[j]);
//			CellRangeAddress region17 = new CellRangeAddress(13, (short) 13, 1, (short) 6);
//			Cell cell22=row13.createCell(1);
//			utils.setRegionStyle(sh, region17, utils.Style11(workbook));
//			sh.addMergedRegion(region17);
//			cell22.setCellValue(jianli1);
//			System.out.println(jianli1);
			}
	        CellRangeAddress region1703 = new CellRangeAddress(13, (short) 42, 0, (short) 0);
			Cell cell0242=row13.createCell(0);
			utils.setRegionStyle(sh, region1703, utils.Style6(workbook));
			sh.addMergedRegion(region1703);
	        cell0242.setCellValue("简历");
	        
//			Row row22 = sh.createRow(22);
//			row22.setHeightInPoints(12);
//			Row row32 = sh.createRow(32);
//			row32.setHeightInPoints(12);
//			
//			CellRangeAddress region1701 = new CellRangeAddress(22, (short) 23, 0, (short) 0);
//			Cell cell024=row22.createCell(0);
//			utils.setRegionStyle(sh, region1701, utils.Style12(workbook));
//			sh.addMergedRegion(region1701);
//	        cell024.setCellValue("简");
//	        
//	        CellRangeAddress region1702 = new CellRangeAddress(32, (short) 33, 0, (short) 0);
//			Cell cell0241=row32.createCell(0);
//			utils.setRegionStyle(sh, region1702, utils.Style12(workbook));
//			sh.addMergedRegion(region1702);
//	        cell0241.setCellValue("历");
	        
	      //对应Excel文件中的sheet，0代表第一个
			Sheet sh1 = xssfWorkbook.getSheetAt(1); 
			
			Row row001 = sh1.createRow(1);
			row001.setHeightInPoints(66);
			
			Cell cell025=row001.createCell(0);
			cell025.setCellStyle(utils.Style9(workbook));
	        cell025.setCellValue("奖惩情况");
			
	        CellRangeAddress region18 = new CellRangeAddress(1, (short) 1, 1, (short) 6);
			Cell cell23=row001.createCell(1);
			utils.setRegionStyle1(sh1, region18, utils.Style81(workbook));
			sh1.addMergedRegion(region18);
			if(jianLiA01.getAwardAndPenalty()!=null&&jianLiA01.getAwardAndPenalty()!=" "&& !jianLiA01.getAwardAndPenalty().equals("")){
				cell23.setCellValue(jianLiA01.getAwardAndPenalty());//A14Z101  奖罚情况
			}else{
				cell23.setCellValue("");//A14Z101  奖罚情况
			}
	        
	        Row row002 = sh1.createRow(2);
	        row002.setHeightInPoints(53);
	        
	        Cell cell026=row002.createCell(0);
			cell026.setCellStyle(utils.Style9(workbook));
	        cell026.setCellValue("年度考核结果");
	        
	        CellRangeAddress region19 = new CellRangeAddress(2, (short) 2, 1, (short) 6);
			Cell cell24=row002.createCell(1);
			utils.setRegionStyle1(sh1, region19, utils.Style81(workbook));
			sh1.addMergedRegion(region19);
			if(jianLiA01.getYearResult()!=null&&jianLiA01.getYearResult()!=" "&& !jianLiA01.getYearResult().equals("")){
				cell24.setCellValue(jianLiA01.getYearResult());//A15Z101  年度考核结果
			}else{
				cell24.setCellValue("");//A15Z101  年度考核结果
			}
	        
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
				if(jianLiA36.get(j).getJRcall()!=null&&jianLiA36.get(j).getJRcall()!=" "&& !jianLiA36.get(j).getJRcall().equals("")){
					cell32.setCellValue(jianLiA36.get(j).getJRcall());//A3604A 家人称谓
				}else{
					cell32.setCellValue("");//A3604A 家人称谓
				}
		        
		        Cell cell33=row.createCell(2);
				cell33.setCellStyle(utils.Style6(workbook));
				if(jianLiA36.get(j).getJRname()!=null&&jianLiA36.get(j).getJRname()!=" "&& !jianLiA36.get(j).getJRname().equals("")){
					cell33.setCellValue(jianLiA36.get(j).getJRname());//A3601   家人姓名
				}else{
					cell33.setCellValue("");//A3601   家人姓名
				}
				
				Cell cell34=row.createCell(3);
				cell34.setCellStyle(utils.Style6(workbook));
				if(jianLiA36.get(j).getJRbirthDay()!=null&&jianLiA36.get(j).getJRbirthDay().length()!=0&&jianLiA36.get(j).getJRbirthDay()!=" "&& !jianLiA36.get(j).getJRbirthDay().equals("")){
					if(jianLiA36.get(j).getJRbirthDay()!=null&&jianLiA36.get(j).getJRbirthDay().length()>4){
				    	String JRbirthDay=jianLiA36.get(j).getJRbirthDay();		//2019
				    	String JRyear =JRbirthDay.substring(0,4);
				    	int i = Integer.parseInt(JRyear);
						Calendar date = Calendar.getInstance();
						String year = String.valueOf(date.get(Calendar.YEAR));
						int k = Integer.parseInt(year);
						int age =k-i;
						cell34.setCellValue(age);//A3607 家人出生年月  算出年龄
					}
				}else{
					cell34.setCellValue(" ");//A0107     出生年月1
					}
				
				
				Cell cell35=row.createCell(4);
				cell35.setCellStyle(utils.Style6(workbook));
				if(jianLiA36.get(j).getJRpolitics()!=null&&jianLiA36.get(j).getJRpolitics()!=" "&& !jianLiA36.get(j).getJRpolitics().equals("")){
					cell35.setCellValue(jianLiA36.get(j).getJRpolitics());//A3627 家人政治面貌
				}else{
					cell35.setCellValue("");//A3627 家人政治面貌
				}
				
				CellRangeAddress region23 = new CellRangeAddress(j+5, (short) j+5, 5, (short)6);
				Cell cell36=row.createCell(5);
				utils.setRegionStyle1(sh1, region23, utils.Style81(workbook));
				sh1.addMergedRegion(region23);
				if(jianLiA36.get(j).getJRjobPlace()!=null&&jianLiA36.get(j).getJRjobPlace()!=" "&& !jianLiA36.get(j).getJRjobPlace().equals("")){
					cell36.setCellValue(jianLiA36.get(j).getJRjobPlace());//A3611 家人工作单位及职位
				}else{
					cell36.setCellValue("");//A3611 家人工作单位及职位
				}
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
				if(selectA01.get(i).getBirthDay().length()<7 && selectA01.get(i).getBirthDay().length()>4 ) {
					selectA01.get(i).setBirthDay(selectA01.get(i).getBirthDay().substring(0, 4)+"."+selectA01.get(i).getBirthDay().substring(4));
				}else if(selectA01.get(i).getBirthDay().length()>7) {
					selectA01.get(i).setBirthDay(selectA01.get(i).getBirthDay().substring(0, 4)+"."+selectA01.get(i).getBirthDay().substring(4,6)+"."+selectA01.get(i).getBirthDay().substring(6));
				}
			}
			
			if(selectA01.get(i).getJobLevelTime()!=null && selectA01.get(i).getJobLevelTime()!=" " && !selectA01.get(i).getJobLevelTime().equals("")) {
				if(selectA01.get(i).getJobLevelTime().length()<7 && selectA01.get(i).getJobLevelTime().length()>4) {
					selectA01.get(i).setJobLevelTime(selectA01.get(i).getJobLevelTime().substring(0, 4)+"."+selectA01.get(i).getJobLevelTime().substring(4));
				}else if(selectA01.get(i).getJobLevelTime().length()>7) {
					selectA01.get(i).setJobLevelTime(selectA01.get(i).getJobLevelTime().substring(0, 4)+"."+selectA01.get(i).getJobLevelTime().substring(4,6)+"."+selectA01.get(i).getJobLevelTime().substring(6));
				}
			}
			
			if(selectA01.get(i).getWorkingTime()!=null && selectA01.get(i).getWorkingTime()!=" " &&  !selectA01.get(i).getWorkingTime().equals("")) {
				if(selectA01.get(i).getWorkingTime().length()<7 && selectA01.get(i).getWorkingTime().length()>4) {
					selectA01.get(i).setWorkingTime(selectA01.get(i).getWorkingTime().substring(0, 4)+"."+selectA01.get(i).getWorkingTime().substring(4));
				}else if(selectA01.get(i).getWorkingTime().length()>7) {
					selectA01.get(i).setWorkingTime(selectA01.get(i).getWorkingTime().substring(0, 4)+"."+selectA01.get(i).getWorkingTime().substring(4,6)+"."+selectA01.get(i).getWorkingTime().substring(6));
				}
			}
			
			if(selectA01.get(i).getEmployTime()!=null && selectA01.get(i).getEmployTime()!=" " &&  !selectA01.get(i).getEmployTime().equals("")) {
				if(selectA01.get(i).getEmployTime().length()<7 && selectA01.get(i).getEmployTime().length()>4) {
					selectA01.get(i).setEmployTime(selectA01.get(i).getEmployTime().substring(0, 4)+"."+selectA01.get(i).getEmployTime().substring(4));
				}else if(selectA01.get(i).getEmployTime().length()>7) {
					selectA01.get(i).setEmployTime(selectA01.get(i).getEmployTime().substring(0, 4)+"."+selectA01.get(i).getEmployTime().substring(4,6)+"."+selectA01.get(i).getEmployTime().substring(6));
				}
			}
			
			if(selectA01.get(i).getTransferringTime()!=null && selectA01.get(i).getTransferringTime()!=" " &&  !selectA01.get(i).getTransferringTime().equals("")) {
				if(selectA01.get(i).getTransferringTime().length()<7 && selectA01.get(i).getTransferringTime().length()>4) {
					selectA01.get(i).setTransferringTime(selectA01.get(i).getTransferringTime().substring(0, 4)+"."+selectA01.get(i).getTransferringTime().substring(4));
				}else if(selectA01.get(i).getTransferringTime().length()>7) {
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
					if(selectA36.get(p).getJRbirthDay().length()<7 && selectA36.get(p).getJRbirthDay().length()>4) {
						selectA36.get(p).setJRbirthDay(selectA36.get(p).getJRbirthDay().substring(0, 4)+"."+selectA36.get(p).getJRbirthDay().substring(4));
					}else if(selectA36.get(p).getJRbirthDay().length()>7) {
						selectA36.get(p).setJRbirthDay(selectA36.get(p).getJRbirthDay().substring(0, 4)+"."+selectA36.get(p).getJRbirthDay().substring(4,6)+"."+selectA36.get(p).getJRbirthDay().substring(6));
					}
				}
			}
		}
		map2.put("A36", selectA36);
		
		
		JianLiA39 selectA39 = jianLiA39Dao.selectA39(selectA01.get(0).getAid());
		if(selectA39!=null) {
			if(selectA39.getJoinTime()!=null && selectA39.getJoinTime()!=" " && !selectA39.getJoinTime().equals("")) {
				if(selectA39.getJoinTime().length()<7 && selectA39.getJoinTime().length()>4) {
					selectA39.setJoinTime(selectA39.getJoinTime().substring(0, 4)+"."+selectA39.getJoinTime().substring(4));
				}else if(selectA39.getJoinTime().length()>7) {
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
		if(url!=null) {
			String imgurl="D:\\zzbhr\\apache-tomcat-6.0.26\\webapps\\zzbhr\\"+url.getImgUrl();
			String base64Str = Base64Transformation.imageToBase64Str(imgurl).replaceAll("\r\n","");
			map2.put("url", base64Str);
		}

		String id=jianli.getAid();
		List<JianLiA08> selectQRZYX = jianLiA08Dao.selectQRZYX(id);
		List<JianLiA08> bb = new ArrayList<>();
		int ii=0;
		if(selectQRZYX!=null) {
			for(int w=0;w<selectQRZYX.size();w++) {
				if(selectQRZYX.size()==1 && ii<1) {			//只查询出一条全日制学院
					bb.add(selectQRZYX.get(w));
					ii++;
				}else if(selectQRZYX.size()>1 && ii<1) {								//查询出多条全日制
					if(selectQRZYX.get(w).getMajor()!=null && selectQRZYX.get(w).getMajor()!="" && !selectQRZYX.get(w).getMajor().equals("") ) {		
						bb.add(selectQRZYX.get(w));
						ii++;
					}else if(w==(selectQRZYX.size()-1)) {
						bb.add(selectQRZYX.get(w));
						ii++;
					}
				}
			}
		}

		ii=0;
		List<JianLiA08> selectZZYX = jianLiA08Dao.selectZZYX(id);
		if(selectZZYX!=null) {
			for(int w=0;w<selectZZYX.size();w++) {
				if(selectZZYX.size()==1 && ii<1) {			//只查询出一条在职教育学院
					bb.add(selectZZYX.get(w));
					ii++;
				}else if(selectZZYX.size()>1 && ii<1) {								//查询出多条在职教育学院
					if(selectZZYX.get(w).getMajor()!=null && selectZZYX.get(w).getMajor()!="" && !selectZZYX.get(w).getMajor().equals("") ) {		
						bb.add(selectZZYX.get(w));
						ii++;
					}else if(w==(selectZZYX.size()-1)) {
						bb.add(selectZZYX.get(w));
						ii++;
					}
				}
			}
		}
		
		//循环上面添加进去的bb 然后拼接字符串返回前端
		for(int e= 0;e<bb.size();e++) {
			if(bb.get(e).getMajor()!=null && bb.get(e).getMajor()!=" " && !bb.get(e).getMajor().equals("") ) {
				bb.get(e).setCollege(bb.get(e).getCollege().concat(bb.get(e).getMajor()).concat("专业"))	;		//拼接学院和专业
			}else if(bb.get(e).getMajor()==null || bb.get(e).getMajor()==" " || bb.get(e).getMajor().equals("")) {
				bb.get(e).setCollege(bb.get(e).getCollege());	
			}
			
		}
		map2.put("college", bb);  
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
			if(list.get(i).getAid()==null || list.get(i).getAid().equals("")) {
				list.get(i).setAid("-");
			}
			if(list.get(i).getName()==null || list.get(i).getName().equals("")) {
				list.get(i).setName("-");
			}
			if(list.get(i).getIdCard()==null || list.get(i).getIdCard().equals("")) {
				list.get(i).setIdCard("-");
			}
			if(list.get(i).getJobNameL()==null ||list.get(i).getJobNameL().equals("")) {
				list.get(i).setJobNameL("-");
			}
			if(list.get(i).getGradeTime()==null || list.get(i).getGradeTime().equals("") || list.get(i).getGradeTime().length()==0) {
				list.get(i).setGradeTime("-");
			}else {
				if(list.get(i).getGradeTime().length()<7 && list.get(i).getGradeTime().length()>4) {
					list.get(i).setGradeTime(list.get(i).getGradeTime().substring(0, 4)+"."+list.get(i).getGradeTime().substring(4));
				}else if(list.get(i).getGradeTime().length()>7) {
					list.get(i).setGradeTime(list.get(i).getGradeTime().substring(0, 4)+"."+list.get(i).getGradeTime().substring(4,6)+"."+list.get(i).getGradeTime().substring(6));
				}
			}
			if(list.get(i).getJobLevel()==null ||list.get(i).getJobLevel().equals("")) {
				list.get(i).setJobLevel("-");
			}
			if(list.get(i).getGrade()==null || list.get(i).getGrade().equals("")) {
				list.get(i).setGrade("-");
			}
			if(list.get(i).getJobLevelTime()==null || list.get(i).getJobLevelTime().equals("") || list.get(i).getJobLevelTime().length()==0) {
				list.get(i).setJobLevelTime("-");
			}else {
				if(list.get(i).getJobLevelTime().length()<7 && list.get(i).getJobLevelTime().length()>4) {
					list.get(i).setJobLevelTime(list.get(i).getJobLevelTime().substring(0, 4)+"."+list.get(i).getJobLevelTime().substring(4));
				}else if(list.get(i).getJobLevelTime().length()>7) {
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
			if(list.get(i).getAid()==null || list.get(i).getAid().equals("")) {
				list.get(i).setAid("-");
			}
			if(list.get(i).getName()==null || list.get(i).getName().equals("")) {
				list.get(i).setName("-");
			}
			if(list.get(i).getIdCard()==null || list.get(i).getIdCard().equals("")) {
				list.get(i).setIdCard("-");
			}
			if(list.get(i).getJobNameL()==null ||list.get(i).getJobNameL().equals("")) {
				list.get(i).setJobNameL("-");
			}
			if(list.get(i).getGradeTime()==null || list.get(i).getGradeTime().equals("") || list.get(i).getGradeTime().length()==0) {
				list.get(i).setGradeTime("-");
			}else {
				if(list.get(i).getGradeTime().length()<7 && list.get(i).getGradeTime().length()>4) {
					list.get(i).setGradeTime(list.get(i).getGradeTime().substring(0, 4)+"."+list.get(i).getGradeTime().substring(4));
				}else if(list.get(i).getGradeTime().length()>7) {
					list.get(i).setGradeTime(list.get(i).getGradeTime().substring(0, 4)+"."+list.get(i).getGradeTime().substring(4,6)+"."+list.get(i).getGradeTime().substring(6));
				}
			}
			if(list.get(i).getJobLevel()==null ||list.get(i).getJobLevel().equals("")) {
				list.get(i).setJobLevel("-");
			}
			if(list.get(i).getGrade()==null || list.get(i).getGrade().equals("")) {
				list.get(i).setGrade("-");
			}
			if(list.get(i).getJobLevelTime()==null || list.get(i).getJobLevelTime().equals("") || list.get(i).getJobLevelTime().length()==0) {
				list.get(i).setJobLevelTime("-");
			}else {
				if(list.get(i).getJobLevelTime().length()<7 && list.get(i).getJobLevelTime().length()>4) {
					list.get(i).setJobLevelTime(list.get(i).getJobLevelTime().substring(0, 4)+"."+list.get(i).getJobLevelTime().substring(4));
				}else if(list.get(i).getJobLevelTime().length()>7) {
					list.get(i).setJobLevelTime(list.get(i).getJobLevelTime().substring(0, 4)+"."+list.get(i).getJobLevelTime().substring(4,6)+"."+list.get(i).getJobLevelTime().substring(6));
				}
			}
		}
		return list ;
	}


	@Override
	public List<JianLiA01> screenZFWList(ScreeningDTO sc) {
		// TODO Auto-generated method stuby
		Map<String,String> rsUnmkMap = new HashMap<>();
		//rsUnmkMap.put("gaj", "");		//公安局
		rsUnmkMap.put("jcy", "A49.F09.291");		//检察院
		rsUnmkMap.put("sfj", "A49.F09.435");		//司法局
		rsUnmkMap.put("fy", "A49.F09.281");			//法院
		List<RsUnmk> rsUnmkList = rsUnmkDaoImpl.findZFWCode(rsUnmkMap);		//1.查询出全政法委的全部部门id
		RsUnmk r = new RsUnmk();
		r.setUID("0");
		r.setName("中国共产党太原市委员会政法委员会");
		r.setCode("A49.F09.329");
		r.setSID("-1");
		rsUnmkList.add(r);
		Set<String> set = new HashSet<>();
		for(int i=0;i<rsUnmkList.size();i++) {
			List<JianLiA01> rsA02UserId = jianLiA01Dao.findZFWCodeID(rsUnmkList.get(i).getUID());
			if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
				for(int j=0;j<rsA02UserId.size();j++) {
					set.add(rsA02UserId.get(j).getAid());
				}
			}
		}
		if(sc.getSexValue().equals("") && 
				sc.getAge_highest().equals("") && 
				sc.getAge_lowest().equals("") && 
				sc.getNationValue().equals("") && 
				sc.getPlace().equals("") && 
				sc.getEducationBg_one().equals("") && 
				sc.getEducationBg_two().equals("") && 
				sc.getDepartment().equals("") && 
				sc.getDuty().equals("") && 
				sc.getCurrentLevel().equals("") && 
				sc.getJoblevel().equals("") && 
				sc.getPersonnel().equals("") && 
				sc.getManage().equals("") && 
				sc.getCompile().equals("") && 
				sc.getNewJoblevel().equals("") && 
				sc.getEmployTime().equals("") && 
				sc.getJoinTime().equals("") && 
				sc.getRewardPunishment().equals("") && 
				sc.getYearResult().equals("") && 
				sc.getTransferring().equals("")) {
			List<JianLiA01> selectAllList = new ArrayList<>();
			for(String all:set) {
				JianLiA01 jl = new JianLiA01();
				jl.setAid(all);
				JianLiA01 jlList = jianLiA01Dao.selectIdList(jl);
				selectAllList.add(jlList);
			}
			return selectAllList;
		}
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
		map1.put("rewardPunishment", sc.getRewardPunishment());
		map1.put("yearResult", sc.getYearResult());
		map1.put("transferring", sc.getTransferring());
		List<JianLiA01> screenA01 = jianLiA01Dao.screenA01(map1);
		List<JianLiA01> list =  new ArrayList<>();		//存放查询完A02的  结果ID
		for(int x=0 ;x<screenA01.size();x++) {
			list.add(screenA01.get(x));
		}
		String [] xl = {"小学","初中","高中","中专","大专","大学","研究生","博士"};
		if(sc.getEducationBg_one()!=null && sc.getEducationBg_one()!="" && sc.getEducationBg_one()!=" " && !sc.getEducationBg_one().equals("")) {
			List<JianLiA01> list2 = new ArrayList<>();
			int s =0;
			for(int a =0;a<list.size();a++) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(a).getAid());
				for(int i=0;i<xl.length;i++) {
					if(xl[i].equals(sc.getEducationBg_one())) {
						s=i;
					}
				}
				for(int i=s;i<xl.length;i++) {
					jl.setSex(xl[i]);			//传入sql中的sex其实是第一学历
					List<JianLiA01> screenA02 = jianLiA01Dao.screenEducationBgOne(jl);
					if(screenA02!=null && screenA02.size()>0) {
						list2.add(screenA02.get(0));
					}
				}
			}
			if(list2!=null && list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}
			
		}
		
		if(sc.getEducationBg_two()!=null && sc.getEducationBg_two()!="" && sc.getEducationBg_two()!=" " && !sc.getEducationBg_two().equals("") && list!=null) {
			List<JianLiA01> list2 = new ArrayList<>();
			int s =0;
			for(int b=0;b<list.size();b++) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(b).getAid());
				for(int i=0;i<xl.length;i++) {
					if(xl[i].equals(sc.getEducationBg_two())) {
						s=i;
					}
				}
				for(int i=s;i<xl.length;i++) {
					jl.setSex(xl[i]);
					List<JianLiA01> screenA03 = jianLiA01Dao.screenEducationBgTwo(jl);
					if(screenA03!=null && screenA03.size()>0) {
						list2.add(screenA03.get(0));
					}
				}
				
				
			}
			if(list2!=null && list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}
		}
		if(sc.getDepartment()!=null && sc.getDepartment()!="" && sc.getDepartment()!=" " && !sc.getDepartment().equals("") && list!=null) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int c=0;c<list.size();c++ ) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(c).getAid());
				jl.setSex(sc.getDepartment());
				List<JianLiA01> screenA04 = jianLiA01Dao.screenDepartment(jl);
				if(screenA04 !=null && screenA04.size()>0) {
					list2.add(screenA04.get(0));
				}
			}
			if(list2!=null && list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}

		}
		if(sc.getDuty()!=null && sc.getDuty()!="" && sc.getDuty()!=" " && !sc.getDuty().equals("") && list!=null) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int c=0;c<list.size();c++ ) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(c).getAid());
				jl.setSex(sc.getDuty());
				List<JianLiA01> screenA05 = jianLiA01Dao.screenDuty(jl);
				if(screenA05!=null && screenA05.size()>0) {
					list2.add(screenA05.get(0));
				}
			}
			if(list2!=null || list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}

		}
		if(sc.getJoinTime()!=null && sc.getJoinTime()!="" && !sc.getJoinTime().equals("") && list.size()>0) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int c=0;c<list.size();c++ ) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(c).getAid());
				jl.setSex(sc.getJoinTime());
				List<JianLiA01> screenA06 = jianLiA01Dao.screenJoinTime(jl);
				if(screenA06!=null && screenA06.size()>0) {
					list2.add(screenA06.get(0));
				}
			}
			if(list2!=null && list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}
		}
		List<JianLiA01> iDList = new ArrayList<>();
		if(list!=null) {
			for(int i = 0;i<list.size();i++) {
				JianLiA01 jl = new JianLiA01();
				jl.setAid(list.get(i).getAid());
				JianLiA01 jlList = jianLiA01Dao.selectIdList(jl);
				iDList.add(jlList);
			}
			
		}

		List<JianLiA01> selectList = new ArrayList<>();
		for(int i=0;i<iDList.size();i++) {
			for(String id:set) {
				if(iDList.get(i).getAid()==id || iDList.get(i).getAid().equals(id)) {
					selectList.add(iDList.get(i));
				}
			}
		}
		return selectList;
	}
	
	
	@Override
	public List<JianLiA01> screenQTList(ScreeningDTO sc) {
		// TODO Auto-generated method stub
		String ss=null;
		/*if(sc.getCard()=="gaj" || sc.getCard().equals("gaj")) {
		ss="公安局的法人编号";
		}*/
		if(sc.getCard()=="jcy" || sc.getCard().equals("jcy")) {
			ss="A49.F09.291";
		}
		if(sc.getCard()=="fy" || sc.getCard().equals("fy")) {
			ss="A49.F09.281";
		}
		if(sc.getCard()=="sfj" || sc.getCard().equals("sfj")) {
			ss="A49.F09.435";
		}
		RsUnmk rsUnmkList = rsUnmkDaoImpl.findQTCode(ss);		//1.查询出全政法委的全部部门id
		Set<String> set = new HashSet<>();
		List<JianLiA01> rsA02UserId = jianLiA01Dao.findQTCodeID(rsUnmkList.getUID());
		if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
			for(int j=0;j<rsA02UserId.size();j++) {
				set.add(rsA02UserId.get(j).getAid());
			}
		}
		if(sc.getSexValue().equals("") && 
				sc.getAge_highest().equals("") && 
				sc.getAge_lowest().equals("") && 
				sc.getNationValue().equals("") && 
				sc.getPlace().equals("") && 
				sc.getEducationBg_one().equals("") && 
				sc.getEducationBg_two().equals("") && 
				sc.getDepartment().equals("") && 
				sc.getDuty().equals("") && 
				sc.getCurrentLevel().equals("") && 
				sc.getJoblevel().equals("") && 
				sc.getPersonnel().equals("") && 
				sc.getManage().equals("") && 
				sc.getCompile().equals("") && 
				sc.getNewJoblevel().equals("") && 
				sc.getEmployTime().equals("") && 
				sc.getJoinTime().equals("") && 
				sc.getRewardPunishment().equals("") && 
				sc.getYearResult().equals("") && 
				sc.getTransferring().equals("")) {
			List<JianLiA01> selectAllList = new ArrayList<>();
			for(String all:set) {
				JianLiA01 jl = new JianLiA01();
				jl.setAid(all);
				JianLiA01 jlList = jianLiA01Dao.selectIdList(jl);
				selectAllList.add(jlList);
			}
			return selectAllList;
		}
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
		map1.put("rewardPunishment", sc.getRewardPunishment());
		map1.put("yearResult", sc.getYearResult());
		map1.put("transferring", sc.getTransferring());
		List<JianLiA01> screenA01 = jianLiA01Dao.screenA01(map1);
		List<JianLiA01> list =  new ArrayList<>();		//存放查询完A02的  结果ID
		for(int x=0 ;x<screenA01.size();x++) {
			list.add(screenA01.get(x));
		}
		String [] xl = {"小学","初中","高中","中专","大专","大学","研究生","博士"};
		if(sc.getEducationBg_one()!=null && sc.getEducationBg_one()!="" && sc.getEducationBg_one()!=" " && !sc.getEducationBg_one().equals("")) {
			List<JianLiA01> list2 = new ArrayList<>();
			int s =0;
			for(int a =0;a<list.size();a++) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(a).getAid());
				for(int i=0;i<xl.length;i++) {
					if(xl[i].equals(sc.getEducationBg_one())) {
						s=i;
					}
				}
				for(int i=s;i<xl.length;i++) {
					jl.setSex(xl[i]);			//传入sql中的sex其实是第一学历
					List<JianLiA01> screenA02 = jianLiA01Dao.screenEducationBgOne(jl);
					if(screenA02!=null && screenA02.size()>0) {
						list2.add(screenA02.get(0));
					}
				}
			}
			if(list2!=null && list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}
		}
		
		if(sc.getEducationBg_two()!=null && sc.getEducationBg_two()!="" && sc.getEducationBg_two()!=" " && !sc.getEducationBg_two().equals("") && list!=null) {
			List<JianLiA01> list2 = new ArrayList<>();
			int s =0;
			for(int b=0;b<list.size();b++) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(b).getAid());
				for(int i=0;i<xl.length;i++) {
					if(xl[i].equals(sc.getEducationBg_two())) {
						s=i;
					}
				}
				for(int i=s;i<xl.length;i++) {
					jl.setSex(xl[i]);
					List<JianLiA01> screenA03 = jianLiA01Dao.screenEducationBgTwo(jl);
					if(screenA03!=null && screenA03.size()>0) {
						list2.add(screenA03.get(0));
					}
				}
				
				
			}
			if(list2!=null && list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}
		}
		if(sc.getDepartment()!=null && sc.getDepartment()!="" && sc.getDepartment()!=" " && !sc.getDepartment().equals("") && list!=null) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int c=0;c<list.size();c++ ) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(c).getAid());
				jl.setSex(sc.getDepartment());
				List<JianLiA01> screenA04 = jianLiA01Dao.screenDepartment(jl);
				if(screenA04 !=null && screenA04.size()>0) {
					list2.add(screenA04.get(0));
				}
			}
			if(list2!=null && list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}

		}
		if(sc.getDuty()!=null && sc.getDuty()!="" && sc.getDuty()!=" " && !sc.getDuty().equals("") && list!=null) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int c=0;c<list.size();c++ ) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(c).getAid());
				jl.setSex(sc.getDuty());
				List<JianLiA01> screenA05 = jianLiA01Dao.screenDuty(jl);
				if(screenA05!=null && screenA05.size()>0) {
					list2.add(screenA05.get(0));
				}
			}
			if(list2!=null || list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}

		}
		if(sc.getJoinTime()!=null && sc.getJoinTime()!="" && !sc.getJoinTime().equals("") && list.size()>0) {
			List<JianLiA01> list2 = new ArrayList<>();
			for(int c=0;c<list.size();c++ ) {
				JianLiA01 jl= new JianLiA01();
				jl.setAid(list.get(c).getAid());
				jl.setSex(sc.getJoinTime());
				List<JianLiA01> screenA06 = jianLiA01Dao.screenJoinTime(jl);
				if(screenA06!=null && screenA06.size()>0) {
					list2.add(screenA06.get(0));
				}
			}
			if(list2!=null && list2.size()>0) {
				list.clear();
				for(int b=0;b<list2.size();b++) {
					list.add(list2.get(b));
				}
			}
			if(list2.size()==0){
				List<JianLiA01> selectAllList = new ArrayList<>();
				return selectAllList;
			}
		}
		List<JianLiA01> iDList = new ArrayList<>();
		if(list!=null) {
			for(int i = 0;i<list.size();i++) {
				JianLiA01 jl = new JianLiA01();
				jl.setAid(list.get(i).getAid());
				JianLiA01 jlList = jianLiA01Dao.selectIdList(jl);
				iDList.add(jlList);
			}
			
		}
		
		List<JianLiA01> selectList = new ArrayList<>();
		for(int i=0;i<iDList.size();i++) {
			for(String id:set) {
				if(iDList.get(i).getAid()==id || iDList.get(i).getAid().equals(id)) {
					selectList.add(iDList.get(i));
				}
			}
		}
		return selectList;
	}


	@Override
	public Map<String, Object> ZFWdataAnalysis() {
		// TODO Auto-generated method stub
		Map<String,String> rsUnmkMap = new HashMap<>();
		//rsUnmkMap.put("gaj", "");		//公安局
		rsUnmkMap.put("jcy", "A49.F09.291");		//检察院
		rsUnmkMap.put("sfj", "A49.F09.435");		//司法局
		rsUnmkMap.put("fy", "A49.F09.281");			//法院
		List<RsUnmk> rsUnmkList = rsUnmkDaoImpl.findZFWCode(rsUnmkMap);		//1.查询出全政法委的全部部门id
		RsUnmk r = new RsUnmk();
		r.setUID("0");
		r.setName("中国共产党太原市委员会政法委员会");
		r.setCode("A49.F09.329");
		r.setSID("-1");
		rsUnmkList.add(r);
		Set<String> set2 = new HashSet<>();			//存放唯一政法委的用户id
		for(int i=0;i<rsUnmkList.size();i++) {
			List<JianLiA01> rsA02UserId = jianLiA01Dao.findZFWCodeID(rsUnmkList.get(i).getUID());		//根据部门id查询全部的人员id
			if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
				for(int j=0;j<rsA02UserId.size();j++) {
					set2.add(rsA02UserId.get(j).getAid());
				}
			}
		}
		List<String> zfwidList = new ArrayList<>();
		for(String ss:set2) {
			zfwidList.add(ss);
		}
		Map<String,Object> map = new HashMap<>();
		List<JianLiA01> list = jianLiA01Dao.selectSexRatio(zfwidList);
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getSex().equals("1")) {		//是男
				int a=zfwidList.size();
				int b=Integer.parseInt(list.get(i).getBirthDay());
				DecimalFormat df = new DecimalFormat("0.00");
				String num = df.format((float) b/a);
				double d = Double.valueOf(num);
				int cc =(int)(d*100);
				map.put("sexRatio1", cc+"%");
			}else if(list.get(i).getSex().equals("2")) {		//是女
				int a=zfwidList.size();
				int b=Integer.parseInt(list.get(i).getBirthDay());
				DecimalFormat df = new DecimalFormat("0.00");
				String num = df.format((float) b/a);
				double d = Double.valueOf(num);
				int cc =(int)(d*100);
				map.put("sexRatio2", cc+"%");
			}
		}
		
		List<JianLiA01> age = jianLiA01Dao.selectAGE(zfwidList);				
		int a=0;
		int b=0;
		int c=0;
		int d=0;
		int e=0;
		for(int i=0;i<age.size();i++) {
			if(age.get(i)!=null && !age.get(i).equals("")) {
				int intage = Integer.parseInt(age.get(i).getBirthDay());
				if(intage<=30) {
					a=a+1;
				}else if(intage>=31 && intage<=40 ) {
					b=b+1;
				}else if(intage>=41 && intage<=50 ) {
					c=c+1;
				}else if(intage>=51 && intage<=59 ) {
					d=d+1;
				}else if(intage>=60) {
					e=e+1;
				}
			}
			
		}
		map.put("30", a);
		map.put("40", b);
		map.put("50", c);
		map.put("59", d);
		map.put("60", e);
		
		List<JianLiA01> selectGradeTime = jianLiA01Dao.selectGradeTime(zfwidList);			
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("YYYY");
		int year = Integer.parseInt(sf.format(date));
		int u=0;
		int f=0;
		int g=0;
		int h=0;
		for(int i =0;i<selectGradeTime.size();i++) {
			if(selectGradeTime.get(i).getBirthDay()!=null && selectGradeTime.get(i).getBirthDay()!=" " && !selectGradeTime.get(i).getBirthDay().equals("")) {
				int gradeTime =Integer.parseInt(selectGradeTime.get(i).getBirthDay().substring(0, 4));
				if((year-gradeTime)<2) {
					u=u+1;
				}else if((year-gradeTime)>=2 && (year-gradeTime)<5 ) {
					f=f+1;
				} else if((year-gradeTime)>=5 && (year-gradeTime)<10 ) {
					g=g+1;
				}else if((year-gradeTime)>10) {
					h=h+1;
				}
				
			}
		}
		map.put("dutyLevelTwo", u);
		map.put("dutyLevelFive", f);
		map.put("dutyLevelNine", g);
		map.put("dutyLevelTen", h);
		List<JianLiA01> jl=new ArrayList<>();
		JianLiA01 jl21= new JianLiA01();
		jl21.setJobLevel("1A21");
		jl.add(jl21);
		JianLiA01 jl22= new JianLiA01();
		jl22.setJobLevel("1A22");
		jl.add(jl22);
		
		JianLiA01 jl31= new JianLiA01();
		jl31.setJobLevel("1A31");
		jl.add(jl31);
		JianLiA01 jl32= new JianLiA01();
		jl32.setJobLevel("1A32");
		jl.add(jl32);
		
		JianLiA01 jl41= new JianLiA01();
		jl41.setJobLevel("1A41");
		jl.add(jl41);
		JianLiA01 jl42= new JianLiA01();
		jl42.setJobLevel("1A42");
		jl.add(jl42);
		
		int o=0;
		int l=0;
		for(int i =0;i<jl.size();i++) {
			List<JianLiA01> alist = jianLiA01Dao.selectJobLevel(jl.get(i));  				
			List<JianLiA01> idList= new ArrayList<>(); 
			if(alist !=null && alist.size()>0) {
				for(int ii=0;ii<zfwidList.size();ii++) {
					for(int iii=0;iii<alist.size();iii++) {
						if(zfwidList.get(ii)==alist.get(iii).getAid() || zfwidList.get(ii).equals(alist.get(iii).getAid())) {
							idList.add(alist.get(iii));
						}
					}
				}
				for(int q=0;q<idList.size();q++) {
					Map<String,String> map2 = new HashMap<>();;
					map2.put("ID",idList.get(q).getAid());
					JianLiA01 selectAll = jianLiA01Dao.selectAll(map2);					//循环id查询集合用户性别
					if(selectAll.getSex().equals("1")) {		//是男
						o=o+1;
					}else if(selectAll.getSex().equals("2")) {		//是女
						l=l+1;
					}
					
				}
				map.put(jl.get(i).getJobLevel()+"-1", o);
				map.put(jl.get(i).getJobLevel()+"-2", l);
				
			}else {
				map.put(jl.get(i).getJobLevel()+"-1", "0");
				map.put(jl.get(i).getJobLevel()+"-2", "0");
			}
			o=0;
			l=0;
		}
		jl.clear();

		JianLiA01 jl50= new JianLiA01();
		jl50.setJobLevel("1A50");
		jl.add(jl50);
		JianLiA01 jl99= new JianLiA01();
		jl99.setJobLevel("1A99");
		jl.add(jl99);
		Set<String> set3 = new HashSet<>();
		Set<String> set = new HashSet<>();
		for(int i =0;i<jl.size();i++) {
			List<JianLiA01> idList = jianLiA01Dao.selectJobLevel(jl.get(i));  					
			for(int q=0;q<idList.size();q++) {
				set3.add(idList.get(q).getAid());
			}
		}
		List<JianLiA01> jobLevelIsNull = jianLiA01Dao.selectJobLevelIsNull();					
		for(int x=0;x<jobLevelIsNull.size();x++) {
			set3.add(jobLevelIsNull.get(x).getAid());
		}
		for(int i=0;i<zfwidList.size();i++) {
			for(String ss:set3) {
				if(zfwidList.get(i)==ss || zfwidList.get(i).equals(ss)) {
					set.add(ss);
				}
			}
		}
		int m=0;
		int n=0;
		for(String str : set) {
			Map<String,String> map2 = new HashMap<>();;
			map2.put("ID",str);
			JianLiA01 selectAll = jianLiA01Dao.selectAll(map2);							//循环id查询集合用户性别
			if(selectAll.getSex().equals("1")) {		//是男
				m=m+1;
			}else if(selectAll.getSex().equals("2")) {		//是女
				n=n+1;
			}
		}
		map.put("KY1", m);
		map.put("KY2", n);
		
		//List<JianLiA01> list2 = jianLiA01Dao.selectAllList();
		map.put("total", zfwidList.size());
		int aa=0;
		int bb=0;
		for(int k=0;k<zfwidList.size();k++) {
			JianLiA01 jianli = new JianLiA01();
			jianli.setAid(zfwidList.get(k));
			jianli.setSex("大学");
			List<JianLiA01> screenEducationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);			
			if(screenEducationBgOne!=null) {
				for(int kk=0;kk<screenEducationBgOne.size();kk++) {
					aa=aa+1;
				}
			}
			
			jianli.setSex("研究生");
			List<JianLiA01> educationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);					
			if(educationBgOne!=null) {
				for(int kk=0;kk<educationBgOne.size();kk++) {
					bb=bb+1;
				}
			}
		}
		map.put("University", aa);
		map.put("postgraduate", bb);
		return map;
	}

	
	@Override
	public Map<String, Object> QTdataAnalysis(String card) {
		// TODO Auto-generated method stub
		String s=null;
		/*if(card=="gaj" || card.equals("gaj")) {
			s="公安局的法人编号";
		}*/
		if(card=="jcy" || card.equals("jcy")) {
			s="A49.F09.291";
		}
		if(card=="fy" || card.equals("fy")) {
			s="A49.F09.281";
		}
		if(card=="sfj" || card.equals("sfj")) {
			s="A49.F09.435";
		}
		RsUnmk rsUnmkList = rsUnmkDaoImpl.findQTCode(s);		//1.查询出全政法委的全部部门id
		Set<String> set2 = new HashSet<>();			//存放唯一政法委的用户id
		List<JianLiA01> rsA02UserId = jianLiA01Dao.findQTCodeID(rsUnmkList.getUID());		//根据部门id查询全部的人员id
		if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
			for(int j=0;j<rsA02UserId.size();j++) {
				set2.add(rsA02UserId.get(j).getAid());
			}
		}
		List<String> zfwidList = new ArrayList<>();
		for(String ss:set2) {
			zfwidList.add(ss);
		}
		
		Map<String,Object> map = new HashMap<>();
		List<JianLiA01> list = jianLiA01Dao.selectSexRatio(zfwidList);
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getSex().equals("1")) {		//是男
				int a=zfwidList.size();
				int b=Integer.parseInt(list.get(i).getBirthDay());
				DecimalFormat df = new DecimalFormat("0.00");
				String num = df.format((float) b/a);
				double d = Double.valueOf(num);
				int cc =(int)(d*100);
				map.put("sexRatio1", cc+"%");
			}else if(list.get(i).getSex().equals("2")) {		//是女
				int a=zfwidList.size();
				int b=Integer.parseInt(list.get(i).getBirthDay());
				DecimalFormat df = new DecimalFormat("0.00");
				String num = df.format((float) b/a);
				double d = Double.valueOf(num);
				int cc =(int)(d*100);
				map.put("sexRatio2", cc+"%");
			}
		}
		
		List<JianLiA01> age = jianLiA01Dao.selectAGE(zfwidList);				
		int a=0;
		int b=0;
		int c=0;
		int d=0;
		int e=0;
		for(int i=0;i<age.size();i++) {
			if(age.get(i)!=null && !age.get(i).equals("")) {
				int intage = Integer.parseInt(age.get(i).getBirthDay());
				if(intage<=30) {
					a=a+1;
				}else if(intage>=31 && intage<=40 ) {
					b=b+1;
				}else if(intage>=41 && intage<=50 ) {
					c=c+1;
				}else if(intage>=51 && intage<=59 ) {
					d=d+1;
				}else if(intage>=60) {
					e=e+1;
				}
			}
			
		}
		map.put("30", a);
		map.put("40", b);
		map.put("50", c);
		map.put("59", d);
		map.put("60", e);
		
		List<JianLiA01> selectGradeTime = jianLiA01Dao.selectGradeTime(zfwidList);			
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("YYYY");
		int year = Integer.parseInt(sf.format(date));
		int u=0;
		int f=0;
		int g=0;
		int h=0;
		for(int i =0;i<selectGradeTime.size();i++) {
			if(selectGradeTime.get(i).getBirthDay()!=null && selectGradeTime.get(i).getBirthDay()!=" " && !selectGradeTime.get(i).getBirthDay().equals("")) {
				int gradeTime =Integer.parseInt(selectGradeTime.get(i).getBirthDay().substring(0, 4));
				if((year-gradeTime)<2) {
					u=u+1;
				}else if((year-gradeTime)>=2 && (year-gradeTime)<5 ) {
					f=f+1;
				} else if((year-gradeTime)>=5 && (year-gradeTime)<10 ) {
					g=g+1;
				}else if((year-gradeTime)>10) {
					h=h+1;
				}
				
			}
		}
		map.put("dutyLevelTwo", u);
		map.put("dutyLevelFive", f);
		map.put("dutyLevelNine", g);
		map.put("dutyLevelTen", h);
		List<JianLiA01> jl=new ArrayList<>();
		JianLiA01 jl21= new JianLiA01();
		jl21.setJobLevel("1A21");
		jl.add(jl21);
		JianLiA01 jl22= new JianLiA01();
		jl22.setJobLevel("1A22");
		jl.add(jl22);
		
		JianLiA01 jl31= new JianLiA01();
		jl31.setJobLevel("1A31");
		jl.add(jl31);
		JianLiA01 jl32= new JianLiA01();
		jl32.setJobLevel("1A32");
		jl.add(jl32);
		
		JianLiA01 jl41= new JianLiA01();
		jl41.setJobLevel("1A41");
		jl.add(jl41);
		JianLiA01 jl42= new JianLiA01();
		jl42.setJobLevel("1A42");
		jl.add(jl42);
		
		int o=0;
		int l=0;
		for(int i =0;i<jl.size();i++) {
			List<JianLiA01> alist = jianLiA01Dao.selectJobLevel(jl.get(i));  				
			List<JianLiA01> idList= new ArrayList<>(); 
			if(alist !=null && alist.size()>0) {
				for(int ii=0;ii<zfwidList.size();ii++) {
					for(int iii=0;iii<alist.size();iii++) {
						if(zfwidList.get(ii)==alist.get(iii).getAid() || zfwidList.get(ii).equals(alist.get(iii).getAid())) {
							idList.add(alist.get(iii));
						}
					}
				}
				for(int q=0;q<idList.size();q++) {
					Map<String,String> map2 = new HashMap<>();;
					map2.put("ID",idList.get(q).getAid());
					JianLiA01 selectAll = jianLiA01Dao.selectAll(map2);					//循环id查询集合用户性别
					if(selectAll.getSex().equals("1")) {		//是男
						o=o+1;
					}else if(selectAll.getSex().equals("2")) {		//是女
						l=l+1;
					}
					
				}
				map.put(jl.get(i).getJobLevel()+"-1", o);
				map.put(jl.get(i).getJobLevel()+"-2", l);
				
			}else {
				map.put(jl.get(i).getJobLevel()+"-1", "0");
				map.put(jl.get(i).getJobLevel()+"-2", "0");
			}
			o=0;
			l=0;
		}
		jl.clear();

		JianLiA01 jl50= new JianLiA01();
		jl50.setJobLevel("1A50");
		jl.add(jl50);
		JianLiA01 jl99= new JianLiA01();
		jl99.setJobLevel("1A99");
		jl.add(jl99);
		Set<String> set3 = new HashSet<>();
		Set<String> set = new HashSet<>();
		for(int i =0;i<jl.size();i++) {
			List<JianLiA01> idList = jianLiA01Dao.selectJobLevel(jl.get(i));  					
			for(int q=0;q<idList.size();q++) {
				set3.add(idList.get(q).getAid());
			}
		}
		List<JianLiA01> jobLevelIsNull = jianLiA01Dao.selectJobLevelIsNull();					
		for(int x=0;x<jobLevelIsNull.size();x++) {
			set3.add(jobLevelIsNull.get(x).getAid());
		}
		for(int i=0;i<zfwidList.size();i++) {
			for(String ss:set3) {
				if(zfwidList.get(i)==ss || zfwidList.get(i).equals(ss)) {
					set.add(ss);
				}
			}
		}
		int m=0;
		int n=0;
		for(String str : set) {
			Map<String,String> map2 = new HashMap<>();;
			map2.put("ID",str);
			JianLiA01 selectAll = jianLiA01Dao.selectAll(map2);							//循环id查询集合用户性别
			if(selectAll.getSex().equals("1")) {		//是男
				m=m+1;
			}else if(selectAll.getSex().equals("2")) {		//是女
				n=n+1;
			}
		}
		map.put("KY1", m);
		map.put("KY2", n);
		
		//List<JianLiA01> list2 = jianLiA01Dao.selectAllList();
		map.put("total", zfwidList.size());
		int aa=0;
		int bb=0;
		for(int k=0;k<zfwidList.size();k++) {
			JianLiA01 jianli = new JianLiA01();
			jianli.setAid(zfwidList.get(k));
			jianli.setSex("大学");
			List<JianLiA01> screenEducationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);			
			if(screenEducationBgOne!=null) {
				for(int kk=0;kk<screenEducationBgOne.size();kk++) {					//这里的代码可优化
					aa=aa+1;
				}
			}
			
			jianli.setSex("研究生");
			List<JianLiA01> educationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);					
			if(educationBgOne!=null) {
				for(int kk=0;kk<educationBgOne.size();kk++) {					//这里的代码可优化
					bb=bb+1;
				}
			}
		}
		map.put("University", aa);
		map.put("postgraduate", bb);
		return map;
	}

	
	
	@Override
	public List<JianLiA01> findZFWCodeID(String userID) {
		// TODO Auto-generated method stub
		return jianLiA01Dao.findZFWCodeID(userID);
	}

	
	@Override
	public List<JianLiA01> findQTCodeID(String userID) {
		// TODO Auto-generated method stub
		return jianLiA01Dao.findQTCodeID(userID);
	}


	@Override
	public JianLiA01 selectIdList(JianLiA01 jl) {
		// TODO Auto-generated method stub
		return jianLiA01Dao.selectIdList(jl);
	}


	@Override
	public List<JianLiA01> findUidAllList(String UID) {
		// TODO Auto-generated method stub
		List<JianLiA01> allList= new ArrayList<>();
		List<JianLiA01> list = jianLiA01Dao.findUidAllList(UID);
		if(list!=null && list.size()>0) {
			for(int i =0;i<list.size();i++) {
				JianLiA01 aidList = jianLiA01Dao.selectIdList(list.get(i));
				allList.add(aidList);
			}
			return allList;
		}else {
			return null;
		}
	}


	@Override
	public Map<String, Object> findIndexData() {
		// TODO Auto-generated method stub
		Map<String,Object> bossMap = new HashMap<>();
		Map<String,String> rsUnmkMap = new HashMap<>();
		//rsUnmkMap.put("gaj", "");		//公安局
		rsUnmkMap.put("jcy", "A49.F09.291");		//检察院
		rsUnmkMap.put("sfj", "A49.F09.435");		//司法局
		rsUnmkMap.put("fy", "A49.F09.281");			//法院
		List<RsUnmk> rsUnmkList = rsUnmkDaoImpl.findZFWCode(rsUnmkMap);		//1.查询出全政法委的全部部门id
		RsUnmk r = new RsUnmk();
		r.setUID("0");
		r.setName("中国共产党太原市委员会政法委员会");
		r.setCode("A49.F09.329");
		r.setSID("-1");
		rsUnmkList.add(r);
		Set<String> set = new HashSet<>();			//存放唯一政法委的用户id
		for(int i=0;i<rsUnmkList.size();i++) {
			List<JianLiA01> rsA02UserId = jianLiA01Dao.findZFWCodeID(rsUnmkList.get(i).getUID());		//根据部门id查询全部的人员id
			if(rsA02UserId!=null && rsA02UserId.size()>0 ) {
				for(int j=0;j<rsA02UserId.size();j++) {
					set.add(rsA02UserId.get(j).getAid());
				}
			}
		}
		List<JianLiA01> ZFWAllList = new ArrayList<>();
		List<String> zfwidList= new ArrayList<>();
		for(String aid:set) {
			zfwidList.add(aid);
			Map<String,String> map = new HashMap<>();
			map.put("ID",aid);	
			List<JianLiA01> selectA01 = jianLiA01Dao.selectA01(map);
			if(selectA01!=null && selectA01.size()>0) {
				for(int i=0;i<selectA01.size();i++) {
					ZFWAllList.add(selectA01.get(i));
				}
			}
			
		}
		//===========================查询出政法委的全部人员的全部字段=================================
		/*List<JianLiA01> GAJAllList = new ArrayList<>();
		Set<String> setA = new HashSet<>();
		String a="";				//公安局的法人编号
		RsUnmk aa = rsUnmkDaoImpl.findQTCode(a);
		List<JianLiA01> GAJList = jianLiA01Dao.findQTCodeID(aa.getUID());		//根据部门id查询全部的人员id
		if(GAJList!=null && GAJList.size()>0 ) {
			for(int j=0;j<GAJList.size();j++) {
				setA.add(GAJList.get(j).getAid());
			}
		}
		List<String> gajidList= new ArrayList<>();
		for(String aid:setA) {
			gajidList.add(aid);
			Map<String,String> map = new HashMap<>();
			map.put("ID",aid);	
			List<JianLiA01> selectA01 = jianLiA01Dao.selectA01(map);
			if(selectA01!=null && selectA01.size()>0) {
				for(int i=0;i<selectA01.size();i++) {
					GAJAllList.add(selectA01.get(i));
				}
			}
			
		}
		*/
		
		
		
		
		
		List<JianLiA01> JCYAllList = new ArrayList<>();
		Set<String> setB = new HashSet<>();
		String b="A49.F09.291";		//检察院的法人编号
		RsUnmk bb = rsUnmkDaoImpl.findQTCode(b);
		List<JianLiA01> JCYList = jianLiA01Dao.findQTCodeID(bb.getUID());		//根据部门id查询全部的人员id
		if(JCYList!=null && JCYList.size()>0 ) {
			for(int j=0;j<JCYList.size();j++) {
				setB.add(JCYList.get(j).getAid());
			}
		}
		List<String> jcyidList= new ArrayList<>();
		for(String aid:setB) {
			jcyidList.add(aid);
			Map<String,String> map = new HashMap<>();
			map.put("ID",aid);	
			List<JianLiA01> selectA01 = jianLiA01Dao.selectA01(map);
			if(selectA01!=null && selectA01.size()>0) {
				for(int i=0;i<selectA01.size();i++) {
					JCYAllList.add(selectA01.get(i));
				}
			}
			
		}
		
		
		
		List<JianLiA01> FYAllList = new ArrayList<>();
		Set<String> setC = new HashSet<>();
		String c="A49.F09.281";		//法院的法人编号
		RsUnmk cc = rsUnmkDaoImpl.findQTCode(c);
		List<JianLiA01> FYList = jianLiA01Dao.findQTCodeID(cc.getUID());		//根据部门id查询全部的人员id
		if(FYList!=null && FYList.size()>0 ) {
			for(int j=0;j<FYList.size();j++) {
				setC.add(FYList.get(j).getAid());
			}
		}
		List<String> fyidList= new ArrayList<>();
		for(String aid:setC) {
			fyidList.add(aid);
			Map<String,String> map = new HashMap<>();
			map.put("ID",aid);	
			List<JianLiA01> selectA01 = jianLiA01Dao.selectA01(map);
			if(selectA01!=null && selectA01.size()>0) {
				for(int i=0;i<selectA01.size();i++) {
					FYAllList.add(selectA01.get(i));
				}
			}
			
		}
		
		
		
		
		List<JianLiA01> SFJAllList = new ArrayList<>();
		Set<String> setD = new HashSet<>();
		String d="A49.F09.435";		//司法局的法人编号
		RsUnmk dd = rsUnmkDaoImpl.findQTCode(d);
		List<JianLiA01> SFJList = jianLiA01Dao.findQTCodeID(dd.getUID());		//根据部门id查询全部的人员id
		if(SFJList!=null && SFJList.size()>0 ) {
			for(int j=0;j<SFJList.size();j++) {
				setD.add(SFJList.get(j).getAid());
			}
		}
		List<String> sfjidList= new ArrayList<>();
		for(String aid:setD) {
			sfjidList.add(aid);
			Map<String,String> map = new HashMap<>();
			map.put("ID",aid);	
			List<JianLiA01> selectA01 = jianLiA01Dao.selectA01(map);
			if(selectA01!=null && selectA01.size()>0) {
				for(int i=0;i<selectA01.size();i++) {
					SFJAllList.add(selectA01.get(i));
				}
			}
			
		}
		//===========================查询出其他组织的全部人员的全部字段=================================
		int zfwSexNum=0;			//政法委年龄总和
		int gajSexNum=0;			//公安局年龄总和
		int jcySexNum=0;			//检察院年龄总和
		int fySexNum=0;				//法院年龄总和
		int sfjSexNum=0;			//司法局年龄总和
		
		//int zfwEthnicMinorityNum = 0;				//政法委少数名族总和
		//int gajEthnicMinorityNum = 0;				//公安局少数名族总和
		//int jcyEthnicMinorityNum = 0;				//检察院少数名族总和
		//int fyEthnicMinorityNum = 0;				//法院少数名族总和
		//int sfjEthnicMinorityNum = 0;				//司法局少数名族总和
		
		int zfwManNum=0;							//政法委男女人数
		int zfwGirlNum=0;
		
		int gajManNum=0;							//公安局男女人数
		int gajGirlNum=0;
		
		int jcyManNum=0;							//检察院男女人数
		int jcyGirlNum=0;
		
		int fyManNum=0;								//法院男女人数
		int fyGirlNum=0;
		
		int sfjManNum=0;							//司法局男女人数
		int sfjGirlNum=0;
		
		int QB30=0;
		int QB40=0;
		int QB50=0;								
		int QB59=0;
		int QB60=0;
		
		int QB2=0;
		int QB5=0;
		int QB10=0;								
		int QB11=0;
		
		int zfwbk=0;
		int zfwyjs=0;
		
		int gajbk=0;
		int gajyjs=0;
		
		int jcybk=0;
		int jcyyjs=0;
		
		int fybk=0;
		int fyyjs=0;
		
		int sfjbk=0;
		int sfjyjs=0;
		

		Map<String,Object> ageDistribution = new HashMap<>();				//年龄分布
		Map<String,Object> dutyLevel = new HashMap<>();					//职务层次
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("YYYY");
		int year = Integer.parseInt(sf.format(date));
		for(int i=0;i<ZFWAllList.size();i++) {
			zfwSexNum+=Integer.parseInt(ZFWAllList.get(i).getRemarks());
			if(ZFWAllList.get(i).getNation()!="汉族" || !ZFWAllList.get(i).getNation().equals("汉族")) {
				//zfwEthnicMinorityNum=zfwEthnicMinorityNum+1;
			}
			if(ZFWAllList.get(i).getSex()=="男" ||  ZFWAllList.get(i).getSex().equals("男")) {
				zfwManNum=zfwManNum+1;
			}
			if(ZFWAllList.get(i).getSex()=="女" ||  ZFWAllList.get(i).getSex().equals("女")) {
				zfwGirlNum=zfwGirlNum+1;
			}
			if(ZFWAllList.get(i).getJobLevelTime()!=null && ZFWAllList.get(i).getJobLevelTime()!=" " && !ZFWAllList.get(i).getJobLevelTime().equals("")) {
				int gradeTime =Integer.parseInt(ZFWAllList.get(i).getJobLevelTime().substring(0, 4));
				if((year-gradeTime)<2) {
					QB2=QB2+1;
				}else if((year-gradeTime)>=2 && (year-gradeTime)<5 ) {
					QB5=QB5+1;
				} else if((year-gradeTime)>=5 && (year-gradeTime)<10 ) {
					QB10=QB10+1;
				}else if((year-gradeTime)>10) {
					QB11=QB11+1;
				}
			}
			
		}
		Map<String,String> map2zfw = new HashMap<>();
		map2zfw.put("dutyLevelTwo", QB2+"");
		map2zfw.put("dutyLevelFive", QB5+"");
		map2zfw.put("dutyLevelNine", QB10+"");
		map2zfw.put("dutyLevelTen", QB11+"");
		dutyLevel.put("ZFWDutyLevel", map2zfw);
		QB2=0;
		QB5=0;
		QB10=0;								
		QB11=0;
		
		
		Map<String,String> map1zfw = new HashMap<>();
		List<JianLiA01> age = jianLiA01Dao.selectAGE(zfwidList);
		for(int i=0;i<age.size();i++) {
			if(age.get(i)!=null && !age.get(i).equals("")) {
				int intage = Integer.parseInt(age.get(i).getBirthDay());
				if(intage<=30) {
					QB30=QB30+1;
				}else if(intage>=31 && intage<=40 ) {
					QB40=QB40+1;
				}else if(intage>=41 && intage<=50 ) {
					QB50=QB50+1;
				}else if(intage>=51 && intage<=59 ) {
					QB59=QB59+1;
				}else if(intage>=60) {
					QB60=QB60+1;
				}
			}
			
		}
		map1zfw.put("30", QB30+"");
		map1zfw.put("40", QB40+"");
		map1zfw.put("50", QB50+"");
		map1zfw.put("59", QB59+"");
		map1zfw.put("60", QB60+"");
		ageDistribution.put("ZFWAgeDistribution", map1zfw);
		QB30=0;
		QB40=0;
		QB50=0;								
		QB59=0;
		QB60=0;
		for(int i=0;i<zfwidList.size();i++) {
			JianLiA01 jianli = new JianLiA01();
			jianli.setAid(zfwidList.get(i));
			jianli.setSex("大学");
			List<JianLiA01> screenEducationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);
			if(screenEducationBgOne!=null) {
				zfwbk=zfwbk+screenEducationBgOne.size();	
			}
			jianli.setSex("研究生");
			
			List<JianLiA01> educationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);	
			if(educationBgOne!=null) {
				zfwyjs=zfwyjs+educationBgOne.size();
			}	
		}
		
	
		
		

		/*for(int i=0;i<GAJAllList.size();i++) {
			gajSexNum+=Integer.parseInt(GAJAllList.get(i).getRemarks());
			if(GAJAllList.get(i).getNation()!="汉族" && !GAJAllList.get(i).getNation().equals("汉族")) {
				gajEthnicMinorityNum=gajEthnicMinorityNum+1;
			}
			if(GAJAllList.get(i).getSex()=="男" ||  GAJAllList.get(i).getSex().equals("男")) {
				gajManNum=gajManNum+1;
			}
			if(GAJAllList.get(i).getSex()=="女" ||  GAJAllList.get(i).getSex().equals("女")) {
				gajGirlNum=gajGirlNum+1;
			}
			if(GAJAllList.get(i).getJobLevelTime()!=null && GAJAllList.get(i).getJobLevelTime()!=" " && !GAJAllList.get(i).getJobLevelTime().equals("")) {
				int gradeTime =Integer.parseInt(GAJAllList.get(i).getJobLevelTime().substring(0, 4));
				if((year-gradeTime)<2) {
					QB2=QB2+1;
				}else if((year-gradeTime)>=2 && (year-gradeTime)<5 ) {
					QB5=QB5+1;
				} else if((year-gradeTime)>=5 && (year-gradeTime)<10 ) {
					QB10=QB10+1;
				}else if((year-gradeTime)>10) {	
					QB11=QB11+1;
				}
			}

		}
		Map<String,String> map2gaj = new HashMap<>();
		map2gaj.put("dutyLevelTwo", QB2+"");
		map2gaj.put("dutyLevelFive", QB5+"");
		map2gaj.put("dutyLevelNine", QB10+"");
		map2gaj.put("dutyLevelTen", QB11+"");
		dutyLevel.put("GAJDutyLevel", map2gaj);
		QB2=0;
		QB5=0;
		QB10=0;								
		QB11=0;
		
		Map<String,String> map1gaj = new HashMap<>();
		List<JianLiA01> age2 = jianLiA01Dao.selectAGE(gajidList);
		for(int i=0;i<age2.size();i++) {
			if(age2.get(i)!=null && !age2.get(i).equals("")) {
				int intage = Integer.parseInt(age2.get(i).getBirthDay());
				if(intage<=30) {
					QB30=QB30+1;
				}else if(intage>=31 && intage<=40 ) {
					QB40=QB40+1;
				}else if(intage>=41 && intage<=50 ) {
					QB50=QB50+1;
				}else if(intage>=51 && intage<=59 ) {
					QB59=QB59+1;
				}else if(intage>=60) {
					QB60=QB60+1;
				}
			}
			
		}
		map1gaj.put("30", QB30+"");
		map1gaj.put("40", QB40+"");
		map1gaj.put("50", QB50+"");
		map1gaj.put("59", QB59+"");
		map1gaj.put("60", QB60+"");
		ageDistribution.put("GAJAgeDistribution", map1gaj);
		QB30=0;
		QB40=0;
		QB50=0;								
		QB59=0;
		QB60=0;
		for(int i=0;i<gajidList.size();i++) {
			JianLiA01 jianli = new JianLiA01();
			jianli.setAid(gajidList.get(i));
			jianli.setSex("大学");
			List<JianLiA01> screenEducationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);
			if(screenEducationBgOne!=null) {
				gajwbk=gajbk+screenEducationBgOne.size();	
			}
			jianli.setSex("研究生");
			
			List<JianLiA01> educationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);	
			if(educationBgOne!=null) {
				gajyjs=gajyjs+educationBgOne.size();
			}	
		}
		*/
		
		
		for(int i=0;i<FYAllList.size();i++) {
			fySexNum+=Integer.parseInt(FYAllList.get(i).getRemarks());
			if(FYAllList.get(i).getNation()!="汉族" ||  !FYAllList.get(i).getNation().equals("汉族")) {
				//fyEthnicMinorityNum=fyEthnicMinorityNum+1;
			}
			if(FYAllList.get(i).getSex()=="男" ||  FYAllList.get(i).getSex().equals("男")) {
				fyManNum=fyManNum+1;
			}
			if(FYAllList.get(i).getSex()=="女" ||  FYAllList.get(i).getSex().equals("女")) {
				fyGirlNum=fyGirlNum+1;
			}
			if(FYAllList.get(i).getJobLevelTime()!=null && FYAllList.get(i).getJobLevelTime()!=" " && !FYAllList.get(i).getJobLevelTime().equals("")) {
				int gradeTime =Integer.parseInt(FYAllList.get(i).getJobLevelTime().substring(0, 4));
				if((year-gradeTime)<2) {
					QB2=QB2+1;
				}else if((year-gradeTime)>=2 && (year-gradeTime)<5 ) {
					QB5=QB5+1;
				} else if((year-gradeTime)>=5 && (year-gradeTime)<10 ) {
					QB10=QB10+1;
				}else if((year-gradeTime)>10) {
					QB11=QB11+1;
				}
			}
		}
		Map<String,String> map2fy = new HashMap<>();
		map2fy.put("dutyLevelTwo", QB2+"");
		map2fy.put("dutyLevelFive", QB5+"");
		map2fy.put("dutyLevelNine", QB10+"");
		map2fy.put("dutyLevelTen", QB11+"");
		dutyLevel.put("FYDutyLevel", map2fy);
		QB2=0;
		QB5=0;
		QB10=0;								
		QB11=0;

		Map<String,String> map1fy = new HashMap<>();
		List<JianLiA01> age3 = jianLiA01Dao.selectAGE(fyidList);
		for(int i=0;i<age3.size();i++) {
			if(age3.get(i)!=null && !age3.get(i).equals("")) {
				int intage = Integer.parseInt(age3.get(i).getBirthDay());
				if(intage<=30) {
					QB30=QB30+1;
				}else if(intage>=31 && intage<=40 ) {
					QB40=QB40+1;
				}else if(intage>=41 && intage<=50 ) {
					QB50=QB50+1;
				}else if(intage>=51 && intage<=59 ) {
					QB59=QB59+1;
				}else if(intage>=60) {
					QB60=QB60+1;
				}
			}
			
		}
		map1fy.put("30", QB30+"");
		map1fy.put("40", QB40+"");
		map1fy.put("50", QB50+"");
		map1fy.put("59", QB59+"");
		map1fy.put("60", QB60+"");
		ageDistribution.put("FYAgeDistribution", map1fy);
		QB30=0;
		QB40=0;
		QB50=0;								
		QB59=0;
		QB60=0;
		for(int i=0;i<fyidList.size();i++) {
			JianLiA01 jianli = new JianLiA01();
			jianli.setAid(fyidList.get(i));
			jianli.setSex("大学");
			List<JianLiA01> screenEducationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);
			if(screenEducationBgOne!=null) {
				fybk=fybk+screenEducationBgOne.size();	
			}
			jianli.setSex("研究生");
			
			List<JianLiA01> educationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);	
			if(educationBgOne!=null) {
				fyyjs=fyyjs+educationBgOne.size();
			}	
		}
		
		
		for(int i=0;i<JCYAllList.size();i++) {  
			jcySexNum+=Integer.parseInt(JCYAllList.get(i).getRemarks());
			if(JCYAllList.get(i).getNation()!="汉族" ||  !JCYAllList.get(i).getNation().equals("汉族")) {
				//jcyEthnicMinorityNum=jcyEthnicMinorityNum+1;
			}
			if(JCYAllList.get(i).getSex()=="男" ||  JCYAllList.get(i).getSex().equals("男")) {
				jcyManNum=jcyManNum+1;
			}
			if(JCYAllList.get(i).getSex()=="女" ||  JCYAllList.get(i).getSex().equals("女")) {
				jcyGirlNum=jcyGirlNum+1;
			}
			if(JCYAllList.get(i).getJobLevelTime()!=null && JCYAllList.get(i).getJobLevelTime()!=" " && !JCYAllList.get(i).getJobLevelTime().equals("")) {
				int gradeTime =Integer.parseInt(JCYAllList.get(i).getJobLevelTime().substring(0, 4));
				if((year-gradeTime)<2) {
					QB2=QB2+1;
				}else if((year-gradeTime)>=2 && (year-gradeTime)<5 ) {
					QB5=QB5+1;
				} else if((year-gradeTime)>=5 && (year-gradeTime)<10 ) {
					QB10=QB10+1;
				}else if((year-gradeTime)>10) {
					QB11=QB11+1;
				}
			}
		}
		Map<String,String> map2jcy = new HashMap<>();
		map2jcy.put("dutyLevelTwo", QB2+"");
		map2jcy.put("dutyLevelFive", QB5+"");
		map2jcy.put("dutyLevelNine", QB10+"");
		map2jcy.put("dutyLevelTen", QB11+"");
		dutyLevel.put("JCYDutyLevel", map2jcy);
		QB2=0;
		QB5=0;
		QB10=0;								
		QB11=0;

		Map<String,String> map1jcy = new HashMap<>();
		List<JianLiA01> age4 = jianLiA01Dao.selectAGE(jcyidList);
		for(int i=0;i<age4.size();i++) {
			if(age4.get(i)!=null && !age4.get(i).equals("")) {
				int intage = Integer.parseInt(age4.get(i).getBirthDay());
				if(intage<=30) {
					QB30=QB30+1;
				}else if(intage>=31 && intage<=40 ) {
					QB40=QB40+1;
				}else if(intage>=41 && intage<=50 ) {
					QB50=QB50+1;
				}else if(intage>=51 && intage<=59 ) {
					QB59=QB59+1;
				}else if(intage>=60) {
					QB60=QB60+1;
				}
			}
			
		}
		map1jcy.put("30", QB30+"");
		map1jcy.put("40", QB40+"");
		map1jcy.put("50", QB50+"");
		map1jcy.put("59", QB59+"");
		map1jcy.put("60", QB60+"");
		ageDistribution.put("JCYAgeDistribution", map1jcy);
		QB30=0;
		QB40=0;
		QB50=0;								
		QB59=0;
		QB60=0;
		for(int i=0;i<jcyidList.size();i++) {
			JianLiA01 jianli = new JianLiA01();
			jianli.setAid(jcyidList.get(i));
			jianli.setSex("大学");
			List<JianLiA01> screenEducationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);
			if(screenEducationBgOne!=null) {
				jcybk=jcybk+screenEducationBgOne.size();	
			}
			jianli.setSex("研究生");
			
			List<JianLiA01> educationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);	
			if(educationBgOne!=null) {
				jcyyjs=jcyyjs+educationBgOne.size();
			}	
		}

		
		for(int i=0;i<SFJAllList.size();i++) {
			sfjSexNum+=Integer.parseInt(SFJAllList.get(i).getRemarks());
			if(SFJAllList.get(i).getNation()!="汉族" ||  !SFJAllList.get(i).getNation().equals("汉族")) {
				//sfjEthnicMinorityNum=sfjEthnicMinorityNum+1;
			}
			if(SFJAllList.get(i).getSex()=="男" ||  SFJAllList.get(i).getSex().equals("男")) {
				sfjManNum=sfjManNum+1;
			}
			if(SFJAllList.get(i).getSex()=="女" ||  SFJAllList.get(i).getSex().equals("女")) {
				sfjGirlNum=sfjGirlNum+1;
			}
			if(SFJAllList.get(i).getJobLevelTime()!=null && SFJAllList.get(i).getJobLevelTime()!=" " && !SFJAllList.get(i).getJobLevelTime().equals("")) {
				int gradeTime =Integer.parseInt(SFJAllList.get(i).getJobLevelTime().substring(0, 4));
				if((year-gradeTime)<2) {
					QB2=QB2+1;
				}else if((year-gradeTime)>=2 && (year-gradeTime)<5 ) {
					QB5=QB5+1;
				} else if((year-gradeTime)>=5 && (year-gradeTime)<10 ) {
					QB10=QB10+1;
				}else if((year-gradeTime)>10) {
					QB11=QB11+1;
				}
			}
		}
		Map<String,String> map2sfj = new HashMap<>();
		map2sfj.put("dutyLevelTwo", QB2+"");
		map2sfj.put("dutyLevelFive", QB5+"");
		map2sfj.put("dutyLevelNine", QB10+"");
		map2sfj.put("dutyLevelTen", QB11+"");
		dutyLevel.put("SFJDutyLevel", map2sfj);

		Map<String,String> map1sfj = new HashMap<>();
		List<JianLiA01> age5 = jianLiA01Dao.selectAGE(sfjidList);
		for(int i=0;i<age5.size();i++) {
			if(age5.get(i)!=null && !age5.get(i).equals("")) {
				int intage = Integer.parseInt(age5.get(i).getBirthDay());
				if(intage<=30) {
					QB30=QB30+1;
				}else if(intage>=31 && intage<=40 ) {
					QB40=QB40+1;
				}else if(intage>=41 && intage<=50 ) {
					QB50=QB50+1;
				}else if(intage>=51 && intage<=59 ) {
					QB59=QB59+1;
				}else if(intage>=60) {
					QB60=QB60+1;
				}
			}
			
		}
		map1sfj.put("30", QB30+"");
		map1sfj.put("40", QB40+"");
		map1sfj.put("50", QB50+"");
		map1sfj.put("59", QB59+"");
		map1sfj.put("60", QB60+"");
		ageDistribution.put("SFJAgeDistribution", map1sfj);

		for(int i=0;i<sfjidList.size();i++) {
			JianLiA01 jianli = new JianLiA01();
			jianli.setAid(sfjidList.get(i));
			jianli.setSex("大学");
			List<JianLiA01> screenEducationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);
			if(screenEducationBgOne!=null) {
				sfjbk=sfjbk+screenEducationBgOne.size();	
			}
			jianli.setSex("研究生");
			
			List<JianLiA01> educationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);	
			if(educationBgOne!=null) {
				sfjyjs=sfjyjs+educationBgOne.size();
			}	
		}
		
		bossMap.put("ageDistribution", ageDistribution);			//年龄分布
		bossMap.put("dutyLevel", dutyLevel);						//职务层次
		
		Map<String,Integer> ageRatio = new HashMap<>();				//年龄比
		ageRatio.put("zfwSex", zfwSexNum/zfwidList.size());
		//ageRatio.put("gajSex", gajSexNum/gajidList.size());
		ageRatio.put("jcySex", jcySexNum/jcyidList.size());
		ageRatio.put("fySex", fySexNum/fyidList.size());
		ageRatio.put("sfjSex", sfjSexNum/sfjidList.size());
		bossMap.put("ageRatio", ageRatio);
		
		Map<String,Integer> ethnicMinorityRatio = new HashMap<>();			//少数名族占比
		//ethnicMinorityRatio.put("zfwEthnicMinority", zfwEthnicMinorityNum/zfwidList.size());
		//ethnicMinorityRatio.put("gajEthnicMinority", gajEthnicMinorityNum/gajidList.size());
		//ethnicMinorityRatio.put("jcyEthnicMinority", jcyEthnicMinorityNum/jcyidList.size());
		//ethnicMinorityRatio.put("fyEthnicMinority", fyEthnicMinorityNum/fyidList.size());
		//ethnicMinorityRatio.put("sfjEthnicMinority", sfjEthnicMinorityNum/sfjidList.size());
		//bossMap.put("ethnicMinorityRatio", ethnicMinorityRatio);
		bossMap.put("ethnicMinorityRatio", ethnicMinorityRatio);
	
		Map<String,Integer> sexRatio = new HashMap<>();			//男女
		
		sexRatio.put("zfwMan", zfwManNum*100/zfwidList.size());
		sexRatio.put("zfwGirl", zfwGirlNum*100/zfwidList.size());
		
		//sexRatio.put("gajMan", gajManNum*100/gajidList.size());
		//sexRatio.put("gajGirl", gajGirlNum*100/gajidList.size());
		
		sexRatio.put("jcyMan", jcyManNum*100/jcyidList.size());
		sexRatio.put("jcyGirl", jcyGirlNum*100/jcyidList.size());
		
		sexRatio.put("fyMan", fyManNum*100/fyidList.size());
		sexRatio.put("fyGirl", fyGirlNum*100/fyidList.size());
		
		sexRatio.put("sfjMan", sfjManNum*100/sfjidList.size());
		sexRatio.put("sfjGirl", sfjGirlNum*100/sfjidList.size());
		bossMap.put("sexRatio", sexRatio);
		
		Map<String,Integer> education = new HashMap<>();			
		education.put("zfwbk", zfwbk*100/zfwidList.size());
		education.put("zfwyjs", zfwyjs*100/zfwidList.size());
		
		//education.put("gajbk", gajbk*100/gajidList.size());
		//education.put("gajyjs", gajyjs*100/gajidList.size());
		
		education.put("jcybk", jcybk*100/jcyidList.size());
		education.put("jcyyjs", jcyyjs*100/jcyidList.size());
		
		education.put("fybk", fybk*100/fyidList.size());
		education.put("fyyjs", fyyjs*100/fyidList.size());
		
		education.put("sfjbk", sfjbk*100/sfjidList.size());
		education.put("sfjyjs", sfjyjs*100/sfjidList.size());
		bossMap.put("education", education);
		
		Map<String,String> aaa= new HashMap<>();
		aaa.put("ZFWTotal", ZFWAllList.size()+"");
		aaa.put("SFJTotal", SFJAllList.size()+"");
		aaa.put("JCYTotal", JCYAllList.size()+"");
		aaa.put("FYTotal", FYAllList.size()+"");
		//aaa.put("GAJTotal", GAJAllList.size()+"");
		bossMap.put("Total", aaa);
		return bossMap;
	}


	@Override
	public Map<String, Object> screenDataAnalysis(String UID) {
		// TODO Auto-generated method stub
		List<JianLiA01> zfwList = jianLiA01Dao.findUidAllList(UID);
		System.out.println(zfwList+"---------------");
		if(zfwList.size()<=0 ) {
			Map<String,Object> map = new HashMap<>();
			map.put("sexRatio1", 0+"%");
			map.put("sexRatio2", 0+"%");
			map.put("30", 0);
			map.put("40", 0);
			map.put("50", 0);
			map.put("59", 0);
			map.put("60", 0);
			map.put("dutyLevelTwo", 0);
			map.put("dutyLevelFive", 0);
			map.put("dutyLevelNine", 0);
			map.put("dutyLevelTen", 0);
			map.put("1A41-1", 0);
			map.put("1A41-2", 0);
			map.put("1A21-1", 0);
			map.put("1A21-2", 0);
			map.put("1A22-1", 0);
			map.put("1A22-2", 0);
			map.put("1A31-1", 0);
			map.put("1A31-2", 0);
			map.put("1A32-1", 0);
			map.put("1A32-2", 0);
			map.put("1A42-1", 0);
			map.put("1A42-2", 0);
			map.put("KY1", 0);
			map.put("KY2", 0);
			map.put("total", 0);
			map.put("University", 0);
			map.put("postgraduate", 0);
			
			return map;
		}
		List<String> zfwidList = new ArrayList<>(); 
		for(int i=0;i<zfwList.size();i++) {
			zfwidList.add(zfwList.get(i).getAid());
		}
		
		Map<String,Object> map = new HashMap<>();
		List<JianLiA01> list = jianLiA01Dao.selectSexRatio(zfwidList);
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getSex().equals("1")) {		//是男
				int a=zfwidList.size();
				int b=Integer.parseInt(list.get(i).getBirthDay());
				DecimalFormat df = new DecimalFormat("0.00");
				String num = df.format((float) b/a);
				double d = Double.valueOf(num);
				int cc =(int)(d*100);
				map.put("sexRatio1", cc+"%");
			}else if(list.get(i).getSex().equals("2")) {		//是女
				int a=zfwidList.size();
				int b=Integer.parseInt(list.get(i).getBirthDay());
				DecimalFormat df = new DecimalFormat("0.00");
				String num = df.format((float) b/a);
				double d = Double.valueOf(num);
				int cc =(int)(d*100);
				map.put("sexRatio2", cc+"%");
			}
		}
		
		List<JianLiA01> age = jianLiA01Dao.selectAGE(zfwidList);				
		int a=0;
		int b=0;
		int c=0;
		int d=0;
		int e=0;
		for(int i=0;i<age.size();i++) {
			if(age.get(i)!=null && !age.get(i).equals("")) {
				int intage = Integer.parseInt(age.get(i).getBirthDay());
				if(intage<=30) {
					a=a+1;
				}else if(intage>=31 && intage<=40 ) {
					b=b+1;
				}else if(intage>=41 && intage<=50 ) {
					c=c+1;
				}else if(intage>=51 && intage<=59 ) {
					d=d+1;
				}else if(intage>=60) {
					e=e+1;
				}
			}
			
		}
		map.put("30", a);
		map.put("40", b);
		map.put("50", c);
		map.put("59", d);
		map.put("60", e);
		
		List<JianLiA01> selectGradeTime = jianLiA01Dao.selectGradeTime(zfwidList);			
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("YYYY");
		int year = Integer.parseInt(sf.format(date));
		int u=0;
		int f=0;
		int g=0;
		int h=0;
		for(int i =0;i<selectGradeTime.size();i++) {
			if(selectGradeTime.get(i).getBirthDay()!=null && selectGradeTime.get(i).getBirthDay()!=" " && !selectGradeTime.get(i).getBirthDay().equals("")) {
				int gradeTime =Integer.parseInt(selectGradeTime.get(i).getBirthDay().substring(0, 4));
				if((year-gradeTime)<2) {
					u=u+1;
				}else if((year-gradeTime)>=2 && (year-gradeTime)<5 ) {
					f=f+1;
				} else if((year-gradeTime)>=5 && (year-gradeTime)<10 ) {
					g=g+1;
				}else if((year-gradeTime)>10) {
					h=h+1;
				}
				
			}
		}
		map.put("dutyLevelTwo", u);
		map.put("dutyLevelFive", f);
		map.put("dutyLevelNine", g);
		map.put("dutyLevelTen", h);
		List<JianLiA01> jl=new ArrayList<>();
		JianLiA01 jl21= new JianLiA01();
		jl21.setJobLevel("1A21");
		jl.add(jl21);
		JianLiA01 jl22= new JianLiA01();
		jl22.setJobLevel("1A22");
		jl.add(jl22);
		
		JianLiA01 jl31= new JianLiA01();
		jl31.setJobLevel("1A31");
		jl.add(jl31);
		JianLiA01 jl32= new JianLiA01();
		jl32.setJobLevel("1A32");
		jl.add(jl32);
		
		JianLiA01 jl41= new JianLiA01();
		jl41.setJobLevel("1A41");
		jl.add(jl41);
		JianLiA01 jl42= new JianLiA01();
		jl42.setJobLevel("1A42");
		jl.add(jl42);
		
		int o=0;
		int l=0;
		for(int i =0;i<jl.size();i++) {
			List<JianLiA01> alist = jianLiA01Dao.selectJobLevel(jl.get(i));  				
			List<JianLiA01> idList= new ArrayList<>(); 
			if(alist !=null && alist.size()>0) {
				for(int ii=0;ii<zfwidList.size();ii++) {
					for(int iii=0;iii<alist.size();iii++) {
						if(zfwidList.get(ii)==alist.get(iii).getAid() || zfwidList.get(ii).equals(alist.get(iii).getAid())) {
							idList.add(alist.get(iii));
						}
					}
				}
				for(int q=0;q<idList.size();q++) {
					Map<String,String> map2 = new HashMap<>();;
					map2.put("ID",idList.get(q).getAid());
					JianLiA01 selectAll = jianLiA01Dao.selectAll(map2);					//循环id查询集合用户性别
					if(selectAll.getSex().equals("1")) {		//是男
						o=o+1;
					}else if(selectAll.getSex().equals("2")) {		//是女
						l=l+1;
					}
					
				}
				map.put(jl.get(i).getJobLevel()+"-1", o);
				map.put(jl.get(i).getJobLevel()+"-2", l);
				
			}else {
				map.put(jl.get(i).getJobLevel()+"-1", "0");
				map.put(jl.get(i).getJobLevel()+"-2", "0");
			}
			o=0;
			l=0;
		}
		jl.clear();

		JianLiA01 jl50= new JianLiA01();
		jl50.setJobLevel("1A50");
		jl.add(jl50);
		JianLiA01 jl99= new JianLiA01();
		jl99.setJobLevel("1A99");
		jl.add(jl99);
		Set<String> set3 = new HashSet<>();
		Set<String> set = new HashSet<>();
		for(int i =0;i<jl.size();i++) {
			List<JianLiA01> idList = jianLiA01Dao.selectJobLevel(jl.get(i));  					
			for(int q=0;q<idList.size();q++) {
				set3.add(idList.get(q).getAid());
			}
		}
		List<JianLiA01> jobLevelIsNull = jianLiA01Dao.selectJobLevelIsNull();					
		for(int x=0;x<jobLevelIsNull.size();x++) {
			set3.add(jobLevelIsNull.get(x).getAid());
		}
		for(int i=0;i<zfwidList.size();i++) {
			for(String ss:set3) {
				if(zfwidList.get(i)==ss || zfwidList.get(i).equals(ss)) {
					set.add(ss);
				}
			}
		}
		int m=0;
		int n=0;
		for(String str : set) {
			Map<String,String> map2 = new HashMap<>();;
			map2.put("ID",str);
			JianLiA01 selectAll = jianLiA01Dao.selectAll(map2);							//循环id查询集合用户性别
			if(selectAll.getSex().equals("1")) {		//是男
				m=m+1;
			}else if(selectAll.getSex().equals("2")) {		//是女
				n=n+1;
			}
		}
		map.put("KY1", m);
		map.put("KY2", n);
		
		//List<JianLiA01> list2 = jianLiA01Dao.selectAllList();
		map.put("total", zfwidList.size());
		int aa=0;
		int bb=0;
		for(int k=0;k<zfwidList.size();k++) {
			JianLiA01 jianli = new JianLiA01();
			jianli.setAid(zfwidList.get(k));
			jianli.setSex("大学");
			List<JianLiA01> screenEducationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);			
			if(screenEducationBgOne!=null) {
				for(int kk=0;kk<screenEducationBgOne.size();kk++) {
					aa=aa+1;
				}
			}
			
			jianli.setSex("研究生");
			List<JianLiA01> educationBgOne = jianLiA01Dao.screenEducationBgOne(jianli);					
			if(educationBgOne!=null) {
				for(int kk=0;kk<educationBgOne.size();kk++) {
					bb=bb+1;
				}
			}
		}
		map.put("University", aa);
		map.put("postgraduate", bb);
		return map;

	}



}
