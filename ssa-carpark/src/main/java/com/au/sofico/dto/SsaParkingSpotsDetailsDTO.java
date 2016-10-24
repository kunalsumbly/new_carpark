package com.au.sofico.dto;

public class SsaParkingSpotsDetailsDTO {
	private String parkingNumber;
	private boolean isSpecialParkingSpot;
	private String originalOwner;
	public String getParkingNumber() {
		return parkingNumber;
	}
	public void setParkingNumber(String parkingNumber) {
		this.parkingNumber = parkingNumber;
	}
	public boolean isSpecialParkingSpot() {
		return isSpecialParkingSpot;
	}

	public void setSpecialParkingSpot(boolean isSpecialParkingSpot) {
		this.isSpecialParkingSpot = isSpecialParkingSpot;
	}
	public String getOriginalOwner() {
		return originalOwner;
	}
	public void setOriginalOwner(String originalOwner) {
		this.originalOwner = originalOwner;
	}

}
