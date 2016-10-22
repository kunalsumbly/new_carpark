package com.au.sofico.dto;

public class AddressDTO {
	private OfficeAddressDTO officeAddress;
	private HomeAddressDTO homeAddress;
	public OfficeAddressDTO getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(OfficeAddressDTO officeAddress) {
		this.officeAddress = officeAddress;
	}
	public HomeAddressDTO getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(HomeAddressDTO homeAddress) {
		this.homeAddress = homeAddress;
	}

}
