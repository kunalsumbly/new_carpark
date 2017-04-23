package com.au.sofico.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.au.sofico.dao.ISsaParkingDao;
import com.au.sofico.dao.SsaParkingDao;
import com.au.sofico.dao.entity.SsaEmployeeParkingMapping;
import com.au.sofico.dao.entity.SsaEmployeeParkingMappingId;
import com.au.sofico.dao.entity.SsaEmployees;
import com.au.sofico.dao.entity.SsaParkingSpots;
import com.au.sofico.dto.AbstractParserRequestDTO;
import com.au.sofico.dto.AbstractParserResponseDTO;
import com.au.sofico.dto.SsaEmployeeDetailsDTO;
import com.au.sofico.dto.SsaParkingParserRequestDTO;
import com.au.sofico.dto.SsaParkingParserResponseDTO;
import com.au.sofico.dto.SsaParkingSpotsDetailsDTO;
import com.au.sofico.parser.ExcelParser;
import com.au.sofico.util.JPAUtil;


public class SsaBookParkingService implements IssaBookParkingService{
	
	
	
	private ExcelParser excelParser = new ExcelParser();
	
	private ISsaParkingDao ssaParkingDao = new SsaParkingDao();
	
	private JPAUtil jpaUtil = new JPAUtil(); 

	@Override
	public AbstractParserResponseDTO importParkingDataInWorkspaceInMemory() throws Exception{

		AbstractParserRequestDTO requestDTO = new SsaParkingParserRequestDTO() ;
		requestDTO.setFilePath("C:/Users/kusu/git/ssa-carpark/ssa-carpark/src/main/resources/employee_parking_details.xlsx");
		requestDTO.setFileTrfmXmlPath("C:/Users/kusu/git/ssa-carpark/ssa-carpark/src/main/resources/employee_parking_mapping.xml");
		requestDTO.setParserType("excel");
		List<AbstractParserResponseDTO>respDTO = excelParser.parse(requestDTO);
		
		List<SsaEmployeeDetailsDTO>ssaEmployeeDetailsDTOList= ((SsaParkingParserResponseDTO)respDTO.get(0)).getSsaEmployeeDetails();
		List<SsaParkingSpotsDetailsDTO>ssaParkingDetailsDTOList= ((SsaParkingParserResponseDTO)respDTO.get(0)).getSsaParkingSpotsDetails();
		
		
		return respDTO.get(0);
		
	}
	@Override
	public void importParkingDataInWorkspace() throws Exception{
		AbstractParserRequestDTO requestDTO = new SsaParkingParserRequestDTO() ;
		requestDTO.setFilePath("C:/Users/kusu/git/ssa-carpark/ssa-carpark/src/main/resources/employee_parking_details.xlsx");
		requestDTO.setFileTrfmXmlPath("C:/Users/kusu/git/ssa-carpark/ssa-carpark/src/main/resources/employee_parking_mapping.xml");
		requestDTO.setParserType("excel");
		List<AbstractParserResponseDTO>respDTO = excelParser.parse(requestDTO);
		
		List<SsaEmployeeDetailsDTO>ssaEmployeeDetailsDTOList= ((SsaParkingParserResponseDTO)respDTO.get(0)).getSsaEmployeeDetails();
		List<SsaParkingSpotsDetailsDTO>ssaParkingDetailsDTOList= ((SsaParkingParserResponseDTO)respDTO.get(0)).getSsaParkingSpotsDetails();
		
		
		saveEntities(ssaEmployeeDetailsDTOList, ssaParkingDetailsDTOList);
	}




	private void saveEntities(List<SsaEmployeeDetailsDTO> ssaEmployeeDetailsDTOList,
			List<SsaParkingSpotsDetailsDTO> ssaParkingDetailsDTOList) {
		EntityManager em = jpaUtil.getEntityManager();
		jpaUtil.beginTransaction();

		// save employee entity to database
		for (SsaEmployeeDetailsDTO ssaEmployeeDetailsDTO : ssaEmployeeDetailsDTOList) {
			SsaEmployees employeeEntity = new SsaEmployees();
			employeeEntity.setAbsentFromDate(ssaEmployeeDetailsDTO.getAbsentFromDate());
			employeeEntity.setAbsentToDate(ssaEmployeeDetailsDTO.getAbsentToDate());
			employeeEntity.setDateOfJoining(ssaEmployeeDetailsDTO.getDateOfJoining());
			employeeEntity.setEmployeeEmail(ssaEmployeeDetailsDTO.getEmployeeEmailAddress());
			employeeEntity.setGroupEmail(ssaEmployeeDetailsDTO.getEmployeeGroupEmailAddress());
			employeeEntity.setSsaEmployeeName(ssaEmployeeDetailsDTO.getEmployeeId());
			employeeEntity.setSsaEmployeeFullName(ssaEmployeeDetailsDTO.getEmployeeName());
			employeeEntity.setNeedsParking(ssaEmployeeDetailsDTO.getNeedsParking()?1:0);
			em.persist(employeeEntity);

		}

		// save parking spots entity to database
		for (SsaParkingSpotsDetailsDTO ssaParkingSpotsDetailsDTO : ssaParkingDetailsDTOList) {
			SsaParkingSpots parkingSpotEntity = new SsaParkingSpots();
			parkingSpotEntity.setIsVisitorParking(ssaParkingSpotsDetailsDTO.getVisitorParking() ? 1 : 0);
			parkingSpotEntity.setOriginalParkingOwnerName(ssaParkingSpotsDetailsDTO.getOriginalOwner());
			parkingSpotEntity.setSsaParkingNumber(ssaParkingSpotsDetailsDTO.getParkingNumber());
			parkingSpotEntity.setParkingLabel(ssaParkingSpotsDetailsDTO.getComments());
			em.persist(parkingSpotEntity);
		}

		jpaUtil.commitTransaction();
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
	public void allocateDefaultParkingToEmployeesInMemory() throws Exception {
		SsaParkingParserResponseDTO ssaParkingParserResponseDTO = (SsaParkingParserResponseDTO) importParkingDataInWorkspaceInMemory();
		List<SsaEmployeeDetailsDTO>ssaEmployeeDetailsDTOList= ssaParkingParserResponseDTO.getSsaEmployeeDetails();
		List<SsaParkingSpotsDetailsDTO>ssaParkingDetailsDTOList= ssaParkingParserResponseDTO.getSsaParkingSpotsDetails();
		
		
		Map<String,Boolean> employeePresentMap = new HashMap<String,Boolean>();
		Map<String,SsaEmployeeDetailsDTO> employeeDetailsMap = new HashMap<String,SsaEmployeeDetailsDTO>();
		for(SsaEmployeeDetailsDTO ssaEmployeeDetailsDTO:ssaEmployeeDetailsDTOList){
			boolean isAbsent = isTheEmployeeAbsentToday(ssaEmployeeDetailsDTO.getAbsentFromDate(),ssaEmployeeDetailsDTO.getAbsentToDate());
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
		
		Map<String,String> employeeIdNameMap = new HashMap<String,String>();
		for(SsaEmployeeDetailsDTO ssaEmployeeDetailsDTO:notUsualSuspectsEmployeeDTOList){
			employeeIdNameMap.put(ssaEmployeeDetailsDTO.getEmployeeName(),ssaEmployeeDetailsDTO.getEmployeeId());
		}
				
		Map<String,String> finalEmployeeParkingSpotMap = new HashMap<String,String>();
		
		int i=0;
		for(SsaParkingSpotsDetailsDTO ssaParkingSpotsDetailsDTO:ssaParkingDetailsDTOList){
			//if(!ssaParkingSpotsDetailsDTO.getVisitorParking()){// if not a visitor parking , then do the normal stuff
				if("NULL".equals(ssaParkingSpotsDetailsDTO.getOriginalOwner()) || !employeePresentMap.get(ssaParkingSpotsDetailsDTO.getOriginalOwner())){// usual suspects need parking
					if(!ssaParkingSpotsDetailsDTO.getVisitorParking()){
						finalEmployeeParkingSpotMap.put(notUsualSuspectsEmployeeDTOList.get(i).getEmployeeId(), ssaParkingSpotsDetailsDTO.getParkingNumber());
						i++;
					}else{
						if(employeeIdNameMap.get(ssaParkingSpotsDetailsDTO.getComments()) != null){
							finalEmployeeParkingSpotMap.put(employeeIdNameMap.get(ssaParkingSpotsDetailsDTO.getComments()), ssaParkingSpotsDetailsDTO.getParkingNumber());
						}else{
							System.out.println("The visitor employee name does not match the string in the parking comments for that visitor parkingID="+ssaParkingSpotsDetailsDTO.getParkingNumber());
						}
					}
					
				}else{// usual suspect is present
					finalEmployeeParkingSpotMap.put(ssaParkingSpotsDetailsDTO.getOriginalOwner(), ssaParkingSpotsDetailsDTO.getParkingNumber());
				}
		}
		
		System.out.println(finalEmployeeParkingSpotMap.size());
		for(Map.Entry<String,String> itr:finalEmployeeParkingSpotMap.entrySet()){
			System.out.println("Employee Name =="+itr.getKey() + "Assigned parking =="+itr.getValue());
			
			
		}
	}

	private boolean isTheEmployeeAbsentToday(Date absentFromDate, Date absentToDate) {
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
	public List<SsaEmployees> loadAllEmployeeDataFromWorkspace() throws Exception {
		return ssaParkingDao.loadAllEmployees();
	}




	@Override
	public List<SsaParkingSpots> loadAllParkingSpotDataFromWorkspace() throws Exception {
		// TODO Auto-generated method stub
		return ssaParkingDao.loadAllParkingSpots();
	}




	@Override
	public void allocatedParkingSpotInWorkspace() throws Exception {
		EntityManager em = jpaUtil.getEntityManager();
		jpaUtil.beginTransaction();
		List<SsaEmployees> ssaEmployeeEntityList = loadAllEmployeeDataFromWorkspace();
		List<SsaParkingSpots> ssaParkingSpotsEntityList = loadAllParkingSpotDataFromWorkspace();

		Map<String, Boolean> employeePresentMap = new HashMap<String, Boolean>();
		Map<String, SsaEmployees> employeeEntityMap = new HashMap<String, SsaEmployees>();
		for (SsaEmployees ssaEmployeesEntity : ssaEmployeeEntityList) {
			boolean isAbsent = isTheEmployeeAbsentToday(ssaEmployeesEntity.getAbsentFromDate(),
					ssaEmployeesEntity.getAbsentToDate());
			if (isAbsent) {
				employeePresentMap.put(ssaEmployeesEntity.getSsaEmployeeName(), false);
			} else {
				employeePresentMap.put(ssaEmployeesEntity.getSsaEmployeeName(),
						ssaEmployeesEntity.getNeedsParking() == 1 ? true : false);
			}
			// if present and needs parking
			employeeEntityMap.put(ssaEmployeesEntity.getSsaEmployeeName(), ssaEmployeesEntity);
		}

		// this list will have all the employees which don't have permanent
		// parking spots
		// Set<String> usualSuspectsEmployeeDTOSet = new HashSet<String>();
		// Collections.copy(notUsualSuspectsEmployeeDTOList,
		// ssaEmployeeDetailsDTOList);

		for (SsaParkingSpots ssaParkingSpotsEntity : ssaParkingSpotsEntityList) {
			for (SsaEmployees ssaEmployeesEntity : ssaEmployeeEntityList) {
				if (ssaParkingSpotsEntity.getOriginalParkingOwnerName()
						.equals(ssaEmployeesEntity.getSsaEmployeeName())) {
					employeeEntityMap.remove(ssaEmployeesEntity.getSsaEmployeeName());
				}
			}
		}

		List<SsaEmployees> notUsualSuspectsEmployeeDTOList = new ArrayList<SsaEmployees>(employeeEntityMap.values());

		// sorting the list based on date of joining, if the same date, compare
		// of Name
		Collections.sort(notUsualSuspectsEmployeeDTOList, new Comparator<SsaEmployees>() {

			@Override
			public int compare(SsaEmployees o1, SsaEmployees o2) {
				int compare = o1.getDateOfJoining().compareTo(o2.getDateOfJoining());
				if (compare != 0) {
					return compare;
				} else {
					return o1.getSsaEmployeeFullName().compareTo(o2.getSsaEmployeeFullName());
				}

			}
		});

		Map<String, String> employeeNameFullNameMap = new HashMap<String, String>();
		for (SsaEmployees ssaEmployeesEntity : notUsualSuspectsEmployeeDTOList) {
			employeeNameFullNameMap.put(ssaEmployeesEntity.getSsaEmployeeFullName(),
					ssaEmployeesEntity.getSsaEmployeeName());
		}

		Map<String, String> finalEmployeeParkingSpotMap = new HashMap<String, String>();

		int i = 0;
		for (SsaParkingSpots ssaParkingSpotsEntity : ssaParkingSpotsEntityList) {
			// if(!ssaParkingSpotsDetailsDTO.getVisitorParking()){// if not a
			// visitor parking , then do the normal stuff
			if ("NULL".equals(ssaParkingSpotsEntity.getOriginalParkingOwnerName())
					|| !employeePresentMap.get(ssaParkingSpotsEntity.getOriginalParkingOwnerName())) {// usual
																										// suspects
																										// need
																										// parking
				if (!(ssaParkingSpotsEntity.getIsVisitorParking() == 1 ? true : false)) {
					finalEmployeeParkingSpotMap.put(notUsualSuspectsEmployeeDTOList.get(i).getSsaEmployeeName(),
							ssaParkingSpotsEntity.getSsaParkingNumber());
					i++;
				} else {// in case of visitor parking, parking label should
						// match employee full name
					if (employeeNameFullNameMap.get(ssaParkingSpotsEntity.getParkingLabel()) != null) {
						finalEmployeeParkingSpotMap.put(
								employeeNameFullNameMap.get(ssaParkingSpotsEntity.getParkingLabel()),
								ssaParkingSpotsEntity.getSsaParkingNumber());
					} else {
						System.out.println(
								"The visitor employee name does not match the string in the parking comments for that visitor parkingID="
										+ ssaParkingSpotsEntity.getSsaParkingNumber());
					}
				}

			} else {// usual suspect is present
				finalEmployeeParkingSpotMap.put(ssaParkingSpotsEntity.getOriginalParkingOwnerName(),
						ssaParkingSpotsEntity.getSsaParkingNumber());
			}
		}

		System.out.println(finalEmployeeParkingSpotMap.size());
		for (Map.Entry<String, String> itr : finalEmployeeParkingSpotMap.entrySet()) {
			SsaEmployees employeeEntity = ssaParkingDao.fetchEmployeeByEmployeeShortName(itr.getKey());
			SsaParkingSpots parkingSpotEntity = ssaParkingDao.fetchParkingSpotByNumber(itr.getValue());

			SsaEmployeeParkingMappingId mapID = new SsaEmployeeParkingMappingId();
			mapID.setSsaEmployeeId(employeeEntity.getSsaEmployeesId());
			mapID.setSsaParkingSpotId(parkingSpotEntity.getSsaParkingSpotsId());

			SsaEmployeeParkingMapping mappingEntity = new SsaEmployeeParkingMapping();
			mappingEntity.setId(mapID);
			em.persist(mappingEntity);
			
			System.out.println("Employee Name ==" + itr.getKey() + "Assigned parking ==" + itr.getValue());

		}
		jpaUtil.commitTransaction();

	}




	@Override
	public SsaEmployeeParkingMapping fetchParkingDetailsForEmployeeByIdAndParkingSpotId(Integer employeeID,
			Integer parkingSpotID) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public SsaEmployeeParkingMapping fetchParkingDetailForEmployeeByName(String employeeName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void allocateParkingSpotInWorkspace(SsaEmployees employeeEntity, SsaParkingSpots parkingEntity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	/*public static void main(String[] args) {
		boolean bool1 = true;
		boolean bool2 = true;
		System.out.println(bool1==bool2);
	}*/

}
