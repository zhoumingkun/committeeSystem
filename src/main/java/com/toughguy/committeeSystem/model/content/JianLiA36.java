package com.toughguy.committeeSystem.model.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.toughguy.committeeSystem.model.AbstractModel;
import com.toughguy.committeeSystem.util.JsonUtil;

/**
 *报警情况实体类
 * @author ZMK
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL) //为空字段不返回
public class JianLiA36 extends AbstractModel {
	
	private String SID;            //关联id  家庭成员类
	private String JRname;	  //A3601   家人姓名
	private String JRcall;	  //A3604A 家人称谓
	private String JRbirthDay;	  //A3607 家人出生年月
	private String JRpolitics;	  //A3627 家人政治面貌
	private String JRjobPlace;	  //A3611 家人工作单位及职位
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	
	public String getJRname() {
		return JRname;
	}
	public void setJRname(String jRname) {
		JRname = jRname;
	}
	public String getJRcall() {
		return JRcall;
	}
	public void setJRcall(String jRcall) {
		JRcall = jRcall;
	}
	public String getJRbirthDay() {
		return JRbirthDay;
	}
	public void setJRbirthDay(String jRbirthDay) {
		JRbirthDay = jRbirthDay;
	}
	public String getJRpolitics() {
		return JRpolitics;
	}
	public void setJRpolitics(String jRpolitics) {
		JRpolitics = jRpolitics;
	}
	public String getJRjobPlace() {
		return JRjobPlace;
	}
	public void setJRjobPlace(String jRjobPlace) {
		JRjobPlace = jRjobPlace;
	}
	@Override
	public String toString() {
		return "JianLiA36 [SID=" + SID + ", JRname=" + JRname + ", JRcall=" + JRcall + ", JRbirthDay=" + JRbirthDay
				+ ", JRpolitics=" + JRpolitics + ", JRjobPlace=" + JRjobPlace + "]";
	}
	
	
	

	
	

	
}
