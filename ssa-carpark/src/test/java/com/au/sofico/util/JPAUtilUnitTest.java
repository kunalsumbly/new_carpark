package com.au.sofico.util;

import java.util.Calendar;

import javax.persistence.EntityManager;

import com.au.sofico.dao.SsaEmployeesEntity;
import com.au.sofico.dao.SsaParkingSpotsEntity;

import junit.framework.TestCase;

public class JPAUtilUnitTest extends TestCase {
	
	private JPAUtil jpaUtil = new JPAUtil(); 
	
	public void testCreateDBRecord() throws Exception {
		EntityManager em =  jpaUtil.getEntityManager();
		jpaUtil.beginTransaction();
		
		
		SsaEmployeesEntity employee = new SsaEmployeesEntity();
		
		employee.setAbsentFromDate(Calendar.getInstance().getTime());
		employee.setAbsentToDate(Calendar.getInstance().getTime());
		employee.setDateOfJoining(Calendar.getInstance().getTime());
		employee.setEmployeeEmail("kunal.sumbly@gmail.com");
		employee.setGroupEmail("kunal.sumbly@gmail.com");
		employee.setSsaEmployeeName("KUSU");
		SsaParkingSpotsEntity parkingSpot = new SsaParkingSpotsEntity();
		parkingSpot.setIsOriginalOwner(1);
		parkingSpot.setIsSpecialParkingSpot(0);
		parkingSpot.setSsaParkingNumber("172");
		parkingSpot.setSsaEmployeesId(employee.getSsaEmployeesId());
		parkingSpot.getSsaEmployees().add(employee); // this means parking spot entity is the parent of the relation (relation goes from parking spot entity to Employee entity{parking spot -----> employee}) 
		employee.getSsaParkingSpotses().add(parkingSpot); // this means employee is the parent of the relation (relation goes from employee entity to parking spot entity{employee ---> parking spot})
		em.persist(parkingSpot);
		em.persist(employee);
		jpaUtil.commitTransaction();
		
		
		
		
	}

}
