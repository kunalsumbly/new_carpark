package com.au.sofico.dto;

import java.util.Date;

/**
 * This class represents each row in the employee row
 * in the parking details excel
 * @author kusu
 *
 */
public class SsaEmployeeDetailsDTO {

	private String employeeId;
	private String employeeName;
	private Date dateOfJoining;
	private Date absentFromDate;
	private Date absentToDate;
	private String employeeEmailAddress;
	private String employeeGroupEmailAddress;
	private boolean needsParking;


	

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getAbsentFromDate() {
		return absentFromDate;
	}

	public void setAbsentFromDate(Date absentFromDate) {
		this.absentFromDate = absentFromDate;
	}

	public Date getAbsentToDate() {
		return absentToDate;
	}

	public void setAbsentToDate(Date absentToDate) {
		this.absentToDate = absentToDate;
	}

	public String getEmployeeEmailAddress() {
		return employeeEmailAddress;
	}

	public void setEmployeeEmailAddress(String employeeEmailAddress) {
		this.employeeEmailAddress = employeeEmailAddress;
	}

	public String getEmployeeGroupEmailAddress() {
		return employeeGroupEmailAddress;
	}

	public void setEmployeeGroupEmailAddress(String employeeGroupEmailAddress) {
		this.employeeGroupEmailAddress = employeeGroupEmailAddress;
	}

	public boolean getNeedsParking() {
		return needsParking;
	}

	public void setNeedsParking(boolean needsParking) {
		this.needsParking = needsParking;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	

}
