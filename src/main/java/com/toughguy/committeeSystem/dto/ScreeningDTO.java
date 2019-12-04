package com.toughguy.committeeSystem.dto;

public class ScreeningDTO {
	private String sexValue;		//性别
	private String age_highest;		//最高年龄
	private String age_lowest;		//最低年龄
	private String nationValue;		//民族
	private String place;			//籍贯
	private String educationBg_one;	//第一学历
	private String educationBg_two;	//第二学历
	private String department;		//所属部门
	private String duty;			//职务
	private String CurrentLevel;	//现职级
	private String Joblevel;		//职务层次
	private String personnel;		//人员类别
	private String manage;			//管理类别
	private String Compile;			//编制类型
	private String NewJoblevel;		//任现职层次时间
	private String employTime;		//录用时间
	private String joinTime;		//入党时间
	private String transferring;	//是否为选调生
	private String NowPage;			//当前页
	private String Nums;			//每页显示条数
	private String rewardPunishment;	//奖惩情况
	private String yearResult;		//年度考核结果
	private String card;			//法人编号
	
	public ScreeningDTO() {
		super();
	}

	public ScreeningDTO(String sexValue, String age_highest, String age_lowest, String nationValue, String place,
			String educationBg_one, String educationBg_two, String department, String duty, String currentLevel,
			String joblevel, String personnel, String manage, String compile, String newJoblevel, String employTime,
			String joinTime, String transferring, String nowPage, String nums, String rewardPunishment,
			String yearResult, String card) {
		super();
		this.sexValue = sexValue;
		this.age_highest = age_highest;
		this.age_lowest = age_lowest;
		this.nationValue = nationValue;
		this.place = place;
		this.educationBg_one = educationBg_one;
		this.educationBg_two = educationBg_two;
		this.department = department;
		this.duty = duty;
		CurrentLevel = currentLevel;
		Joblevel = joblevel;
		this.personnel = personnel;
		this.manage = manage;
		Compile = compile;
		NewJoblevel = newJoblevel;
		this.employTime = employTime;
		this.joinTime = joinTime;
		this.transferring = transferring;
		NowPage = nowPage;
		Nums = nums;
		this.rewardPunishment = rewardPunishment;
		this.yearResult = yearResult;
		this.card = card;
	}

	public String getSexValue() {
		return sexValue;
	}

	public void setSexValue(String sexValue) {
		this.sexValue = sexValue;
	}

	public String getAge_highest() {
		return age_highest;
	}

	public void setAge_highest(String age_highest) {
		this.age_highest = age_highest;
	}

	public String getAge_lowest() {
		return age_lowest;
	}

	public void setAge_lowest(String age_lowest) {
		this.age_lowest = age_lowest;
	}

	public String getNationValue() {
		return nationValue;
	}

	public void setNationValue(String nationValue) {
		this.nationValue = nationValue;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getEducationBg_one() {
		return educationBg_one;
	}

	public void setEducationBg_one(String educationBg_one) {
		this.educationBg_one = educationBg_one;
	}

	public String getEducationBg_two() {
		return educationBg_two;
	}

	public void setEducationBg_two(String educationBg_two) {
		this.educationBg_two = educationBg_two;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getCurrentLevel() {
		return CurrentLevel;
	}

	public void setCurrentLevel(String currentLevel) {
		CurrentLevel = currentLevel;
	}

	public String getJoblevel() {
		return Joblevel;
	}

	public void setJoblevel(String joblevel) {
		Joblevel = joblevel;
	}

	public String getPersonnel() {
		return personnel;
	}

	public void setPersonnel(String personnel) {
		this.personnel = personnel;
	}

	public String getManage() {
		return manage;
	}

	public void setManage(String manage) {
		this.manage = manage;
	}

	public String getCompile() {
		return Compile;
	}

	public void setCompile(String compile) {
		Compile = compile;
	}

	public String getNewJoblevel() {
		return NewJoblevel;
	}

	public void setNewJoblevel(String newJoblevel) {
		NewJoblevel = newJoblevel;
	}

	public String getEmployTime() {
		return employTime;
	}

	public void setEmployTime(String employTime) {
		this.employTime = employTime;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public String getTransferring() {
		return transferring;
	}

	public void setTransferring(String transferring) {
		this.transferring = transferring;
	}

	
	public String getNowPage() {
		return NowPage;
	}

	public void setNowPage(String nowPage) {
		NowPage = nowPage;
	}

	public String getNums() {
		return Nums;
	}

	public void setNums(String nums) {
		Nums = nums;
	}

	public String getRewardPunishment() {
		return rewardPunishment;
	}

	public void setRewardPunishment(String rewardPunishment) {
		this.rewardPunishment = rewardPunishment;
	}

	public String getYearResult() {
		return yearResult;
	}

	public void setYearResult(String yearResult) {
		this.yearResult = yearResult;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	@Override
	public String toString() {
		return "ScreeningDTO [sexValue=" + sexValue + ", age_highest=" + age_highest + ", age_lowest=" + age_lowest
				+ ", nationValue=" + nationValue + ", place=" + place + ", educationBg_one=" + educationBg_one
				+ ", educationBg_two=" + educationBg_two + ", department=" + department + ", duty=" + duty
				+ ", CurrentLevel=" + CurrentLevel + ", Joblevel=" + Joblevel + ", personnel=" + personnel + ", manage="
				+ manage + ", Compile=" + Compile + ", NewJoblevel=" + NewJoblevel + ", employTime=" + employTime
				+ ", joinTime=" + joinTime + ", transferring=" + transferring + ", NowPage=" + NowPage + ", Nums="
				+ Nums + ", rewardPunishment=" + rewardPunishment + ", yearResult=" + yearResult + ", card=" + card
				+ "]";
	}
	
}
