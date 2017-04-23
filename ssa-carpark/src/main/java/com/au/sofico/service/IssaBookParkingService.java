package com.au.sofico.service;

import java.util.List;

import com.au.sofico.dao.entity.SsaEmployeeParkingMapping;
import com.au.sofico.dao.entity.SsaEmployees;
import com.au.sofico.dao.entity.SsaParkingSpots;
import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.dto.SsaEmployeeDetailsDTO;
import com.au.sofico.dto.SsaParkingDetailsDTO;
import com.au.sofico.dto.SsaParkingSpotsDetailsDTO;

public interface IssaBookParkingService {
	
	// this will be used to load data from the temp db or excel sheet
	public void importParkingDataInWorkspace() throws Exception;
	
	// this will be used to do the real allocation , this is a direct method only to be used 
	// if we want to re-calculate eveyrthing based on default settings
	/**
	 * 1) check for the special parking first, if the original owners are present
	 * 2) if they are , assign the parking to them
	 * 3) else , based on the ranking and availability of the next person, assign the spot to the that person.
	 * 4) special parking over
	 * 5) Move to non special parking , get the refreshed list of the people (- the ones which have been already assigned 
	 * the special parking, )
	 * 6) Visitor Parking is also a special parking, with no actual owner is (99 which is a visitor and based on visitor's
	 * availability , this parking is assigned to him, other wise goes to the ranking order)*/
	public void allocateDefaultParkingToEmployeesInMemory() throws Exception;
	
	
	/**
	 * This method will load all the employee data from database 
	 * @return
	 * @throws Exception
	 */
	public List<SsaEmployees> loadAllEmployeeDataFromWorkspace() throws Exception;
	
	
	public List<SsaParkingSpots> loadAllParkingSpotDataFromWorkspace() throws Exception;
	
	
	
	public void allocatedParkingSpotInWorkspace() throws Exception;
	
	public SsaEmployeeParkingMapping fetchParkingDetailsForEmployeeByIdAndParkingSpotId(Integer employeeID,Integer parkingSpotID) throws Exception;
	
	public SsaEmployeeParkingMapping fetchParkingDetailForEmployeeByName(String employeeName) throws Exception;
	
	/**
	 * This method allocates the parking for all the employees in the workspace
	 * @throws Exception
	 */
	public void allocateParkingSpotInWorkspace(SsaEmployees employeeEntity, SsaParkingSpots parkingEntity) throws Exception;
	
	// this will be used to load data from the temp db or excel sheet
		public AbstractParserResponseDTO importParkingDataInWorkspaceInMemory() throws Exception;

}
