package com.au.sofico.dto;

public class SsaParkingSpotsDetailsDTO {
	private String parkingNumber;
	private boolean isVisitorParking;
	private String originalOwner;
	public String getParkingNumber() {
		return parkingNumber;
	}
	public void setParkingNumber(String parkingNumber) {
		this.parkingNumber = parkingNumber;
	}
	
	public String getOriginalOwner() {
		return originalOwner;
	}
	public void setOriginalOwner(String originalOwner) {
		this.originalOwner = originalOwner;
	}
	public boolean getVisitorParking() {
		return isVisitorParking;
	}
	public void setVisitorParking(boolean isVisitorParking) {
		this.isVisitorParking = isVisitorParking;
	}

}
