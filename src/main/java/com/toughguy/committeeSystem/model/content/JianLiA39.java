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
public class JianLiA39 extends AbstractModel {
	
	private String SID;            //关联id
	private String joinTime;	  //A0144  入党时间
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	public String getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}
	@Override
	public String toString() {
		return "JianLiA39 [SID=" + SID + ", joinTime=" + joinTime + "]";
	}
	
	

	
	

	
}
