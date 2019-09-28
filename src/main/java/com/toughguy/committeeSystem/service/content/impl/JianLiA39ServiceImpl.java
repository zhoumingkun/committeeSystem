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
import com.toughguy.committeeSystem.model.content.JianLiA39;
import com.toughguy.committeeSystem.persist.content.prototype.IJianLiA39Dao;
import com.toughguy.committeeSystem.service.content.prototype.IJianLiA39Service;
import com.toughguy.committeeSystem.service.impl.GenericServiceImpl;


/**
 * 简历表rs_a39Service实现类
 * @author zmk
 *
 */
@Service
public class JianLiA39ServiceImpl extends GenericServiceImpl<JianLiA39, Integer> implements IJianLiA39Service{
	
	
	@Autowired
	IJianLiA39Dao  jianLiA39Dao;

}
