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
		employee.setAbsentFromDate(Calendar.getInstance().getTime());
		employee.setAbsentToDate(Calendar.getInstance().getTime());
		employee.setDateOfJoining(Calendar.getInstance().getTime());
		employee.setEmployeeEmail("kunal.sumbly@gmail.com");
		employee.setGroupEmail("kunal.sumbly@gmail.com");
		employee.setSsaEmployeeName("KUSU");
		
			
		SsaParkingSpots parkingSpot = new SsaParkingSpots();
		//mapping.setSsaParkingSpots(parkingSpot);
		parkingSpot.setIsVisitorParking(0);
		parkingSpot.setSsaParkingNumber("172");
		parkingSpot.setOriginalParkingOwnerName("MACE");
		
		employee.setSsaParkingSpots(parkingSpot);
		
		
		//em.persist(parkingSpot); // save the child before parent
		
		em.persist(employee);
		
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
	
	
	public void testUpdateParkingLiknage() throws Exception {
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
	
	public void testSaveParkingCommentsLiknage() throws Exception {
		EntityManager em =  jpaUtil.getEntityManager();
		jpaUtil.beginTransaction();
		// 
		SsaEmployeeParkingMappingId mapId = new SsaEmployeeParkingMappingId();
		mapId.setSsaEmployeeId(24);
		mapId.setSsaParkingSpotId(24);
		
		SsaEmployeeParkingMapping mapEntity = em.find(SsaEmployeeParkingMapping.class, mapId);
		//mapEntity.setComments("This is a comment for 24");
		jpaUtil.commitTransaction();
		
	}
	
	
	

}
