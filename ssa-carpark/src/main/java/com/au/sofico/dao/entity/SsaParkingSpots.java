package com.au.sofico.dao.entity;
// Generated 07/02/2017 8:43:12 PM by Hibernate Tools 3.5.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ssa_parking_spots", catalog = "ssaparking")
public class SsaParkingSpots implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ssa_parking_spots_id", unique = true, nullable = false)
	private Integer ssaParkingSpotsId;
	@Column(name = "ssa_parking_number")
	private String ssaParkingNumber;
	
	@Column(name = "original_parking_owner_name")
	private String originalParkingOwnerName;
	@Column(name = "is_visitor_parking")
	private Integer isVisitorParking;
	
	@Column(name = "parking_label")
	private String parkingLabel;
	
	
	
	public String getParkingLabel() {
		return parkingLabel;
	}


	public void setParkingLabel(String parkingLabel) {
		this.parkingLabel = parkingLabel;
	}


	public void setIsVisitorParking(Integer isVisitorParking) {
		this.isVisitorParking = isVisitorParking;
	}

	
	public Integer getIsVisitorParking() {
		return this.isVisitorParking;
	}

	public SsaParkingSpots() {
	}

	public SsaParkingSpots(Integer isVisitorParking, String originalParkingOwnerName,
			String ssaParkingNumber) {
		this.isVisitorParking = isVisitorParking;
		this.originalParkingOwnerName = originalParkingOwnerName;
		this.ssaParkingNumber = ssaParkingNumber;
	}

	public Integer getSsaParkingSpotsId() {
		return this.ssaParkingSpotsId;
	}

	public void setSsaParkingSpotsId(Integer ssaParkingSpotsId) {
		this.ssaParkingSpotsId = ssaParkingSpotsId;
	}

	

	
	public String getOriginalParkingOwnerName() {
		return this.originalParkingOwnerName;
	}

	public void setOriginalParkingOwnerName(String originalParkingOwnerName) {
		this.originalParkingOwnerName = originalParkingOwnerName;
	}

	public String getSsaParkingNumber() {
		return this.ssaParkingNumber;
	}

	public void setSsaParkingNumber(String ssaParkingNumber) {
		this.ssaParkingNumber = ssaParkingNumber;
	}
}
