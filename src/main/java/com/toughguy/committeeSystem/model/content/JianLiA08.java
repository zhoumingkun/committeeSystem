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
public class JianLiA08 extends AbstractModel {
	
	private String SID;            //关联id
	private String educationType;	  //A0837  教育类型  1全日制教育  2在职教育
	private String educational;	      //A0801A  学历  
	private String degree;	          //A0901A  学位
	private String college;	  		  //A0814       毕业院校
	private String major;			  //A0824       专业
	
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	public String getEducationType() {
		return educationType;
	}
	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}
	public String getEducational() {
		return educational;
	}
	public void setEducational(String educational) {
		this.educational = educational;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	@Override
	public String toString() {
		return "JianLiA08 [SID=" + SID + ", educationType=" + educationType + ", educational=" + educational
				+ ", degree=" + degree + ", college=" + college + ", major=" + major + "]";
	}

	
	
	

	
	

	
}
