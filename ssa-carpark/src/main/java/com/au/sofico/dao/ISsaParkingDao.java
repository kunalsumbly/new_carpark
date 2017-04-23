package com.au.sofico.dao;

import java.util.List;

import com.au.sofico.dao.entity.SsaEmployeeParkingMapping;
import com.au.sofico.dao.entity.SsaEmployees;
import com.au.sofico.dao.entity.SsaParkingSpots;

public interface ISsaParkingDao {
	public List<SsaEmployees> loadAllEmployees() throws Exception;
	
	public List<SsaParkingSpots> loadAllParkingSpots() throws Exception;
	
	
	public void allocateParkingSpots() throws Exception; 
	
	 public SsaEmployeeParkingMapping fetchParkingDetailForEmployeeByName(String employeeName) throws Exception;
	 
	 public SsaEmployees fetchEmployeeByEmployeeShortName(String shortName);
	 
	 public SsaParkingSpots fetchParkingSpotByNumber(String parkingSportNumber);
	

}
