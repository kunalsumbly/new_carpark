package com.au.sofico.util;

import java.util.Calendar;

import javax.persistence.EntityManager;

import com.au.sofico.dao.entity.SsaEmployeeParkingMapping;
import com.au.sofico.dao.entity.SsaEmployeeParkingMappingId;
import com.au.sofico.dao.entity.SsaEmployees;
import com.au.sofico.dao.entity.SsaParkingSpots;

import junit.framework.TestCase;

public class JPAUtilUnitTest extends TestCase {
	
	private JPAUtil jpaUtil = new JPAUtil(); 
	
	public void testCreateDBRecord() throws Exception {
		EntityManager em =  jpaUtil.getEntityManager();
		jpaUtil.beginTransaction();
		
		
		SsaEmployees employee = new SsaEmployees();
		/*SsaEmployeeParkingMapping mapping = new SsaEmployeeParkingMapping();*/
		
		employee.setAbsentFromDate(Calendar.getInstance().getTime());
		employee.setAbsentToDate(Calendar.getInstance().getTime());
		employee.setDateOfJoining(Calendar.getInstance().getTime());
		employee.setEmployeeEmail("kunal.sumbly@gmail.com");
		employee.setGroupEmail("kunal.sumbly@gmail.com");
		employee.setSsaEmployeeName("KUSU");
		
		//mapping.setSsaEmployees(employee);;
		//employee.setSsaEmployeeParkingMappings(mapping);
		
		/*SsaEmployeeParkingMapping mapping = new SsaEmployeeParkingMapping();
		mapping.setSsaEmployees(employee);
	
		employee.getSsaEmployeeParkingMappings().add(mapping);*/
		
		SsaParkingSpots parkingSpot = new SsaParkingSpots();
		//mapping.setSsaParkingSpots(parkingSpot);
		parkingSpot.setIsOriginalOwner(1);
		parkingSpot.setIsSpecialParkingSpot(0);
		parkingSpot.setSsaParkingNumber("172");
		parkingSpot.setOriginalParkingOwnerName("MACE");
		//mapping.setSsaParkingSpots(parkingSpot);
		//parkingSpot.setSsaEmployeeParkingMappings(mapping);
		//em.persist(parkingSpot);
		employee.setSsaParkingSpots(parkingSpot);
		
		//em.persist(parkingSpot); // save the child before parent
		em.persist(employee);
		
		//mapping.getSsaParkingSpots(
		//parkingSpot.setSsaEmployeesId(employee.getSsaEmployeeName());
		//parkingSpot.setSsaEmployees(employee); // this means parking spot entity is the parent of the relation (relation goes from parking spot entity to Employee entity{parking spot -----> employee}) 
		//employee.setSsaParkingSpotses(parkingSpot); // this means employee is the parent of the relation (relation goes from employee entity to parking spot entity{employee ---> parking spot})
		//em.persist(parkingSpot);
		//em.persist(mapping);
		jpaUtil.commitTransaction();
		
		
		
		
	}
	
	public void testfetchParkingLiknage() throws Exception {
		EntityManager em =  jpaUtil.getEntityManager();
		//jpaUtil.beginTransaction();		
		SsaEmployeeParkingMappingId linkID = new SsaEmployeeParkingMappingId();
		/*SsaEmployees emp = em.find(SsaEmployees.class, 7);
		SsaParkingSpots spot = em.find(SsaParkingSpots.class, 7);*/
		linkID.setSsaEmployeeId(7);
		linkID.setSsaParkingSpotId(7);
		SsaEmployeeParkingMapping mappingEntity =  em.find( SsaEmployeeParkingMapping.class,linkID);  
		System.out.println("entity found "+mappingEntity.getId().getSsaParkingSpotId());
		//em.flush();
		//jpaUtil.commitTransaction();
		
		
	}
	
	public void testDeleteParkingLiknage() throws Exception {
		EntityManager em =  jpaUtil.getEntityManager();
		jpaUtil.beginTransaction();		
		SsaEmployeeParkingMappingId linkID = new SsaEmployeeParkingMappingId();
		/*SsaEmployees emp = em.find(SsaEmployees.class, 7);
		SsaParkingSpots spot = em.find(SsaParkingSpots.class, 7);*/
		linkID.setSsaEmployeeId(7);
		linkID.setSsaParkingSpotId(7);
		SsaEmployeeParkingMapping mappingEntity =  em.find( SsaEmployeeParkingMapping.class,linkID); 
		 em.remove(mappingEntity);
		  mappingEntity =  em.find( SsaEmployeeParkingMapping.class,linkID);
		System.out.println("entity found "+mappingEntity);
		//em.flush();
		jpaUtil.commitTransaction();
		
		
	}

}
