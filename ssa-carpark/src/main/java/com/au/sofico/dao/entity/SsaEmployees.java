package com.au.sofico.dao.entity;
// Generated 20/11/2016 9:24:01 AM by Hibernate Tools 3.5.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * SsaEmployees generated by hbm2java
 */
@Entity
@Table(name = "ssa_employees", catalog = "ssaparking")
public class SsaEmployees implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ssa_employees_id", unique = true, nullable = false)
	private Integer ssaEmployeesId;
	@Column(name = "ssa_employee_name")
	private String ssaEmployeeName;
	@Column(name = "date_of_joining")
	private Date dateOfJoining;
	@Column(name = "absent_from_date")
	private Date absentFromDate;
	@Column(name = "absent_to_date")
	private Date absentToDate;
	@Column(name = "employee_email")
	private String employeeEmail;
	@Column(name = "group_email")
	private String groupEmail;
	//private Set<SsaEmployeeParkingMapping> ssaEmployeeParkingMappings = new HashSet<SsaEmployeeParkingMapping>(0);

	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name = "ssa_employee_parking_mapping", 
	joinColumns = @JoinColumn(name = "ssa_employees_id"), 
	inverseJoinColumns = @JoinColumn(name = "ssa_parking_spots_id"))
	private SsaParkingSpots ssaParkingSpotses = new SsaParkingSpots();
	
	

	public SsaEmployees() {
	}

	public SsaEmployees(String ssaEmployeeName, Date dateOfJoining, Date absentFromDate, Date absentToDate,
			String employeeEmail, String groupEmail 
			//,Set<SsaEmployeeParkingMapping> ssaEmployeeParkingMappings
			) {
		this.ssaEmployeeName = ssaEmployeeName;
		this.dateOfJoining = dateOfJoining;
		this.absentFromDate = absentFromDate;
		this.absentToDate = absentToDate;
		this.employeeEmail = employeeEmail;
		this.groupEmail = groupEmail;
		//this.ssaEmployeeParkingMappings = ssaEmployeeParkingMappings;
	}

	public Integer getSsaEmployeesId() {
		return this.ssaEmployeesId;
	}

	public SsaParkingSpots getSsaParkingSpotses() {
		return ssaParkingSpotses;
	}

	public void setSsaParkingSpotses(SsaParkingSpots ssaParkingSpotses) {
		this.ssaParkingSpotses = ssaParkingSpotses;
	}

	public void setSsaEmployeesId(Integer ssaEmployeesId) {
		this.ssaEmployeesId = ssaEmployeesId;
	}

	public String getSsaEmployeeName() {
		return this.ssaEmployeeName;
	}

	public void setSsaEmployeeName(String ssaEmployeeName) {
		this.ssaEmployeeName = ssaEmployeeName;
	}

	public Date getDateOfJoining() {
		return this.dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getAbsentFromDate() {
		return this.absentFromDate;
	}

	public void setAbsentFromDate(Date absentFromDate) {
		this.absentFromDate = absentFromDate;
	}

	public Date getAbsentToDate() {
		return this.absentToDate;
	}

	public void setAbsentToDate(Date absentToDate) {
		this.absentToDate = absentToDate;
	}

	public String getEmployeeEmail() {
		return this.employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getGroupEmail() {
		return this.groupEmail;
	}

	public void setGroupEmail(String groupEmail) {
		this.groupEmail = groupEmail;
	}

	/*public Set<SsaEmployeeParkingMapping> getSsaEmployeeParkingMappings() {
		return this.ssaEmployeeParkingMappings;
	}

	public void setSsaEmployeeParkingMappings(Set<SsaEmployeeParkingMapping> ssaEmployeeParkingMappings) {
		this.ssaEmployeeParkingMappings = ssaEmployeeParkingMappings;
	}*/

}
