package com.au.sofico.dto;

public class EmployeeParserRequestDTO extends AbstractParserRequestDTO {
	
	private int employeeId;
	
	private String employeeName;
	
	private AddressDTO address;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}


}
