package com.au.sofico.service;

import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.dto.SsaParkingDetailsDTO;

public interface IssaBookParkingService {
	
	// this will be used to load data from the temp db or excel sheet
	public AbstractParserResponseDTO loadParkingData() throws Exception;
	
	// this will be used to do the real allocation
	public boolean allocateParking() throws Exception;
	
	public SsaParkingDetailsDTO fetchParkingDetailsForEmployee(String employeeID) throws Exception;
	
	//public 

}
