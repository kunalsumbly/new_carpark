package com.au.sofico.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.au.sofico.dao.entity.SsaEmployeeParkingMapping;
import com.au.sofico.dao.entity.SsaEmployees;
import com.au.sofico.dao.entity.SsaParkingSpots;
import com.au.sofico.util.JPAUtil;

public class SsaParkingDao implements ISsaParkingDao {
	
	private EntityManager em = JPAUtil.getEntityManager();

	@Override
	public List<SsaEmployees> loadAllEmployees() throws Exception {
		// TODO Auto-generated method stub
		return em.createQuery("select e from SsaEmployees e").getResultList();
	}

	@Override
	public List<SsaParkingSpots> loadAllParkingSpots() throws Exception {
		// TODO Auto-generated method stub
		return em.createQuery("select p from SsaParkingSpots p").getResultList();
	}

	@Override
	public void allocateParkingSpots() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public SsaEmployeeParkingMapping fetchParkingDetailForEmployeeByName(String employeeName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SsaEmployees fetchEmployeeByEmployeeShortName(String shortName) {
		List returnList  =  em.createQuery("select e from SsaEmployees e  where e.ssaEmployeeName= :employeeShortName").setParameter("employeeShortName", shortName).getResultList();
		if(returnList.size() <1){
			System.out.println("Error while finding the SsaEmployee with the name="+shortName);
			return null;
		}else{
			return (SsaEmployees)returnList.get(0);
		}
		
		
	}

	@Override
	public SsaParkingSpots fetchParkingSpotByNumber(String parkingSportNumber) {
		List returnList  =  em.createQuery("select p from SsaParkingSpots p  where p.ssaParkingNumber= :parkingSportNumber").setParameter("parkingSportNumber", parkingSportNumber).getResultList();
		if(returnList.size() <1){
			System.out.println("Error while finding the Ssaparkingsppot with the number="+parkingSportNumber);
			return null;
		}else{
			return (SsaParkingSpots)returnList.get(0);
		}
	}

}
