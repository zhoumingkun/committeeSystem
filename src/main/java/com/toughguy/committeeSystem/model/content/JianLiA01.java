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
public class JianLiA01 extends AbstractModel {
	
	private String ID;            //关联id
	private String sex;           //A0104    性别
	private String birthDay;      //A0107     出生年月
	private String nation;        //A0117     民族
	private String workingTime;   //A0134     参加工作时间
	private String personType;	  //A0160    人员类别
	private String idCard;	      //A0184     身份证号
	private String name;	      //A0101      姓名
	private String homeTown;	  //A0111A 籍贯
	private String birthPlace;	  //A0114A  出生地
	private String healthState;	  //A0128   健康状况
	private String manageType;	  //A0165   管理类别代码
	private String speciality;	   //A0187A   熟悉专业有何专长
	private String resume;	      //A1701  简历
	private String jobNameL;	  //A0192A  工作单位及职务全称
	private String jobNameS;	  //A0192  工作单位及职务简称
	private String yearResult;	  //A15Z101  年度考核结果
	private String specialityPost;	  //A0196  专业技术职务
	private String gradeTime;	  //A0192c  任现职级时间
	private String jobLevel;	  //A0172  职务层次
	private String grade;	      //A0192d  现职级
	private String graduateSchool1;	  //ZZXLXX  毕业院校系及专业上
	private String graduateSchool2;	  //QRZXLXX 毕业院校系及专业下
	private String jobLevelTime;	  //A1073  任现职务层次时间
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getWorkingTime() {
		return workingTime;
	}
	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHomeTown() {
		return homeTown;
	}
	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public String getHealthState() {
		return healthState;
	}
	public void setHealthState(String healthState) {
		this.healthState = healthState;
	}
	public String getManageType() {
		return manageType;
	}
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getJobNameL() {
		return jobNameL;
	}
	public void setJobNameL(String jobNameL) {
		this.jobNameL = jobNameL;
	}
	public String getJobNameS() {
		return jobNameS;
	}
	public void setJobNameS(String jobNameS) {
		this.jobNameS = jobNameS;
	}
	public String getYearResult() {
		return yearResult;
	}
	public void setYearResult(String yearResult) {
		this.yearResult = yearResult;
	}
	public String getSpecialityPost() {
		return specialityPost;
	}
	public void setSpecialityPost(String specialityPost) {
		this.specialityPost = specialityPost;
	}
	public String getGradeTime() {
		return gradeTime;
	}
	public void setGradeTime(String gradeTime) {
		this.gradeTime = gradeTime;
	}
	public String getJobLevel() {
		return jobLevel;
	}
	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGraduateSchool1() {
		return graduateSchool1;
	}
	public void setGraduateSchool1(String graduateSchool1) {
		this.graduateSchool1 = graduateSchool1;
	}
	public String getGraduateSchool2() {
		return graduateSchool2;
	}
	public void setGraduateSchool2(String graduateSchool2) {
		this.graduateSchool2 = graduateSchool2;
	}
	public String getJobLevelTime() {
		return jobLevelTime;
	}
	public void setJobLevelTime(String jobLevelTime) {
		this.jobLevelTime = jobLevelTime;
	}
	@Override
	public String toString() {
		return "JianLiA01 [ID=" + ID + ", sex=" + sex + ", birthDay=" + birthDay + ", nation=" + nation
				+ ", workingTime=" + workingTime + ", personType=" + personType + ", idCard=" + idCard + ", name="
				+ name + ", homeTown=" + homeTown + ", birthPlace=" + birthPlace + ", healthState=" + healthState
				+ ", manageType=" + manageType + ", speciality=" + speciality + ", resume=" + resume + ", jobNameL="
				+ jobNameL + ", jobNameS=" + jobNameS + ", yearResult=" + yearResult + ", specialityPost="
				+ specialityPost + ", gradeTime=" + gradeTime + ", jobLevel=" + jobLevel + ", grade=" + grade
				+ ", graduateSchool1=" + graduateSchool1 + ", graduateSchool2=" + graduateSchool2 + ", jobLevelTime="
				+ jobLevelTime + "]";
	}
	
	

	
	

	
}
