package com.au.sofico.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * This class handles the creation of EntityManagerFactory 
 * and the entityManager for the whole application
 * @author kusu
 *
 */
public class JPAUtil {
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	static{
		emf = Persistence.createEntityManagerFactory("ssaCarPark");
		em = emf.createEntityManager();
		
	}

	public static EntityManager getEntityManager() {
		
		return em;

	}

	public static boolean beginTransaction() {
		EntityTransaction txn = em.getTransaction();
		try {
			txn.begin();
		} catch (Exception ex) {
			if (txn != null) {
				txn.rollback();
				return false;
			}
		} finally {
			if (em != null) {
				//em.close();
			}
		}
		return true;
	}

	public static boolean commitTransaction() {
		EntityTransaction txn = em.getTransaction();
		try {
			txn.commit();
		} catch (Exception ex) {
			if (txn != null) {
				txn.rollback();
				return false;
			}

		} finally {
			if (em != null) {
				em.close();
			}
		}
		return true;
	}

}
