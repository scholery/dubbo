package com.scholery.solr.demo.model;

import java.util.Date;

public class Worktime implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	int empId;
	String empCode;
	String empName;
	int prjId;
	String prjName;
	int actId;
	String actName;
	float workHours;
	float overWorkHours;
	String workDateStr;
	Date workDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getPrjId() {
		return prjId;
	}

	public void setPrjId(int prjId) {
		this.prjId = prjId;
	}

	public String getPrjName() {
		return prjName;
	}

	public void setPrjName(String prjName) {
		this.prjName = prjName;
	}

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public float getWorkHours() {
		return workHours;
	}

	public void setWorkHours(float workHours) {
		this.workHours = workHours;
	}

	public float getOverWorkHours() {
		return overWorkHours;
	}

	public void setOverWorkHours(float overWorkHours) {
		this.overWorkHours = overWorkHours;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getWorkDateStr() {
		return workDateStr;
	}

	public void setWorkDateStr(String workDateStr) {
		this.workDateStr = workDateStr;
	}

}