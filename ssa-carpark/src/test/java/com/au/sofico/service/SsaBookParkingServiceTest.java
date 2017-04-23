package com.au.sofico.service;

import junit.framework.TestCase;

public class SsaBookParkingServiceTest extends TestCase {
	
	private SsaBookParkingService ssaBookParkingService = new SsaBookParkingService();

	public void testAllocateParking() throws Exception {		
		
		ssaBookParkingService.allocateDefaultParkingToEmployeesInMemory();
	}
	
	public void testLoadingParkingData() throws Exception {		
		
		ssaBookParkingService.importParkingDataInWorkspace();
	}

	
	public void testAllocateDataInWorkSpace() throws Exception{
		
		ssaBookParkingService.allocatedParkingSpotInWorkspace();
	}
}
