package com.toughguy.committeeSystem.model.content;

import java.util.List;

public class RsUnmk {
	private String UID;			//id
	private String SID;			//父id
	private String code;		//B0121  法人编码
	private String name;		//名称
	//private List<RsUnmk> sonList;	//子数据列表
	
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getSID() {
		return SID;
	}
	public void setSID(String sID) {
		SID = sID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
/*	public List<RsUnmk> getSonList() {
		return sonList;
	}
	public void setSonList(List<RsUnmk> sonList) {
		this.sonList = sonList;
	}*/
/*	@Override
	public String toString() {
		return "RsUnmk [UID=" + UID + ", SID=" + SID + ", code=" + code + ", name=" + name + ", sonList=" + sonList
				+ "]";
	}*/
	@Override
	public String toString() {
		return "RsUnmk [UID=" + UID + ", SID=" + SID + ", code=" + code + ", name=" + name + "]";
	}
	
}
