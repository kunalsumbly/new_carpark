package com.au.sofico.dao.entity;
// Generated 07/02/2017 8:43:12 PM by Hibernate Tools 3.5.0.Final

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;


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
	
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	  @JoinTable(
	      name="ssa_employee_parking_mapping",
	      joinColumns=
	        @JoinColumn(name="ssa_employee_id", referencedColumnName="ssa_employees_id"),
	      inverseJoinColumns=
	        @JoinColumn(name="ssa_parking_spot_id", referencedColumnName="ssa_parking_spots_id"))
	  private SsaParkingSpots ssaParkingSpots;

	public SsaParkingSpots getSsaParkingSpots() {
		return ssaParkingSpots;
	}

	public void setSsaParkingSpots(SsaParkingSpots ssaParkingSpots) {
		this.ssaParkingSpots = ssaParkingSpots;
	}

	public SsaEmployees() {
	}

	public SsaEmployees(Date absentFromDate, Date absentToDate, Date dateOfJoining, String employeeEmail,
			String groupEmail, String ssaEmployeeName,SsaParkingSpots ssaParkingSpots) {
		this.absentFromDate = absentFromDate;
		this.absentToDate = absentToDate;
		this.dateOfJoining = dateOfJoining;
		this.employeeEmail = employeeEmail;
		this.groupEmail = groupEmail;
		this.ssaEmployeeName = ssaEmployeeName;
		this.ssaParkingSpots =ssaParkingSpots;
	}

	public Integer getSsaEmployeesId() {
		return this.ssaEmployeesId;
	}

	public void setSsaEmployeesId(Integer ssaEmployeesId) {
		this.ssaEmployeesId = ssaEmployeesId;
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

	public Date getDateOfJoining() {
		return this.dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
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

	public String getSsaEmployeeName() {
		return this.ssaEmployeeName;
	}

	public void setSsaEmployeeName(String ssaEmployeeName) {
		this.ssaEmployeeName = ssaEmployeeName;
	}

	

}
