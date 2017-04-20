package com.au.sofico.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.au.sofico.dto.AbstractParserRequestDTO;
import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.dto.SsaEmployeeDetailsDTO;
import com.au.sofico.dto.SsaParkingDetailsDTO;
import com.au.sofico.dto.SsaParkingParserRequestDTO;
import com.au.sofico.dto.SsaParkingParserResponseDTO;
import com.au.sofico.dto.SsaParkingSpotsDetailsDTO;
import com.au.sofico.parser.ExcelParser;
import com.au.sofico.util.JPAUtil;


public class SsaBookParkingService implements IssaBookParkingService{
	
	private static final String VISITOR_ID = "99";
	
	private ExcelParser excelParser = new ExcelParser();

	@Override
	public AbstractParserResponseDTO loadParkingData() throws Exception{
		AbstractParserRequestDTO requestDTO = new SsaParkingParserRequestDTO() ;
		requestDTO.setFilePath("C:/Users/kusu/git/ssa-carpark/ssa-carpark/src/main/resources/employee_parking_details.xlsx");
		requestDTO.setFileTrfmXmlPath("C:/Users/kusu/git/ssa-carpark/ssa-carpark/src/main/resources/employee_parking_mapping.xml");
		requestDTO.setParserType("excel");
		List<AbstractParserResponseDTO>respDTO = excelParser.parse(requestDTO);
		return respDTO.get(0);
	}


	
	
	/**
	 * 1) check for the special parking first, if the original owners are present
	 * 2) if they are , assign the parking to them
	 * 3) else , based on the ranking and availability of the next person, assign the spot to the that person.
	 * 4) special parking over
	 * 5) Move to non special parking , get the refreshed list of the people (- the ones which have been already assigned 
	 * the special parking, )
	 * 6) Visitor Parking is also a special parking, with no actual owner is (99 which is a visitor and based on visitor's
	 * availability , this parking is assigned to him, other wise goes to the ranking order)
	 */
	@Override
	public boolean allocateParking() throws Exception {
		SsaParkingParserResponseDTO ssaParkingParserResponseDTO = (SsaParkingParserResponseDTO) loadParkingData();
		List<SsaEmployeeDetailsDTO>ssaEmployeeDetailsDTOList= ssaParkingParserResponseDTO.getSsaEmployeeDetails();
		List<SsaParkingSpotsDetailsDTO>ssaParkingDetailsDTOList= ssaParkingParserResponseDTO.getSsaParkingSpotsDetails();
		
		
		Map<String,Boolean> employeePresentMap = new HashMap<String,Boolean>();
		Map<String,SsaEmployeeDetailsDTO> employeeDetailsMap = new HashMap<String,SsaEmployeeDetailsDTO>();
		for(SsaEmployeeDetailsDTO ssaEmployeeDetailsDTO:ssaEmployeeDetailsDTOList){
			boolean isAbsent = isTheEmployeeAbsentToday(ssaEmployeeDetailsDTO.getAbsentFromDate(),ssaEmployeeDetailsDTO.getAbsentToDate(),ssaEmployeeDetailsDTO.getEmployeeName());
			if(isAbsent){
				employeePresentMap.put(ssaEmployeeDetailsDTO.getEmployeeId(), false);
			}else{
				employeePresentMap.put(ssaEmployeeDetailsDTO.getEmployeeId(), ssaEmployeeDetailsDTO.getNeedsParking());
			}
			 // if present and needs parking 
			employeeDetailsMap.put(ssaEmployeeDetailsDTO.getEmployeeId(), ssaEmployeeDetailsDTO);
		}
		
		// this list will have all the employees which don't have permanent parking spots
		//Set<String> usualSuspectsEmployeeDTOSet = new HashSet<String>(); 
		//Collections.copy(notUsualSuspectsEmployeeDTOList, ssaEmployeeDetailsDTOList);
		
		for(SsaParkingSpotsDetailsDTO ssaParkingSpotsDetailsDTO:ssaParkingDetailsDTOList){
			for(SsaEmployeeDetailsDTO ssaEmployeeDetailsDTO:ssaEmployeeDetailsDTOList){
				if(ssaParkingSpotsDetailsDTO.getOriginalOwner().equals(ssaEmployeeDetailsDTO.getEmployeeId())){
					//ssaEmployeeDetailsDTO = null;
					//usualSuspectsEmployeeDTOSet.add(ssaEmployeeDetailsDTO.getEmployeeName());
					employeeDetailsMap.remove(ssaEmployeeDetailsDTO.getEmployeeId());
					
				}
			}
		}
		
		
		
		List<SsaEmployeeDetailsDTO> notUsualSuspectsEmployeeDTOList = new ArrayList<SsaEmployeeDetailsDTO>(employeeDetailsMap.values());
		
		
		
		// sorting the list based on date of joining, if the same date, compare of Name
		Collections.sort(notUsualSuspectsEmployeeDTOList, new Comparator<SsaEmployeeDetailsDTO>() {

			@Override
			public int compare(SsaEmployeeDetailsDTO o1, SsaEmployeeDetailsDTO o2) {
				int compare = o1.getDateOfJoining().compareTo(o2.getDateOfJoining());
				if(compare != 0){
					return compare;
				}else{
					return o1.getEmployeeName().compareTo(o2.getEmployeeName()); 
				}
				
			}
		});
				
		Map<String,String> finalEmployeeParkingSpotMap = new HashMap<String,String>();
		
		int i=0,j=i+1;
		for(SsaParkingSpotsDetailsDTO ssaParkingSpotsDetailsDTO:ssaParkingDetailsDTOList){
			if(!ssaParkingSpotsDetailsDTO.getVisitorParking()){// if not a visitor parking , then do the normal stuff
				if("NULL".equals(ssaParkingSpotsDetailsDTO.getOriginalOwner()) || !employeePresentMap.get(ssaParkingSpotsDetailsDTO.getOriginalOwner())){// usual suspects need parking
					finalEmployeeParkingSpotMap.put(notUsualSuspectsEmployeeDTOList.get(i).getEmployeeName(), ssaParkingSpotsDetailsDTO.getParkingNumber());
					i++;
				}else{// usual suspect is present
					finalEmployeeParkingSpotMap.put(ssaParkingSpotsDetailsDTO.getOriginalOwner(), ssaParkingSpotsDetailsDTO.getParkingNumber());
				}
				
			}else{// visitor parking, cannot be assigned to any employee
				finalEmployeeParkingSpotMap.put(VISITOR_ID+j, ssaParkingSpotsDetailsDTO.getParkingNumber());
				j++;
			}
			
		}
		
		System.out.println(finalEmployeeParkingSpotMap.size());
		for(Map.Entry<String,String> itr:finalEmployeeParkingSpotMap.entrySet()){
			System.out.println("Employee Name =="+itr.getKey() + "Assigned parking =="+itr.getValue());
		}
		
		// need to save the map in database using jpa and hibernate
		
		JPAUtil.beginTransaction();
		
		
		
		
		JPAUtil.commitTransaction();
		
		return false;
	}

	private boolean isTheEmployeeAbsentToday(Date absentFromDate, Date absentToDate, String employeeName) {
		// TODO Auto-generated method stub
		Date currentDate = Calendar.getInstance().getTime();
		if(absentFromDate != null && absentToDate != null){
		if(absentFromDate.before(currentDate) && absentToDate.after(currentDate)){
			return true;
		}else if(absentFromDate.equals(currentDate) && absentToDate.equals(currentDate)){
			return true;
		}else if (absentFromDate.equals(currentDate) && absentToDate.after(currentDate)){
			return true;
		}else if(absentFromDate.before(currentDate) && absentToDate.equals(currentDate)){
			return true;
		}}
	
		return false;
	}

	@Override
	public SsaParkingDetailsDTO fetchParkingDetailsForEmployee(String employeeID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*public static void main(String[] args) {
		boolean bool1 = true;
		boolean bool2 = true;
		System.out.println(bool1==bool2);
	}*/

}
