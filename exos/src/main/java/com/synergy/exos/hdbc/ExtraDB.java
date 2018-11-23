/**
 * @author G-Kesh
 * Author and Publisher data Fetching
 */

package com.synergy.exos.hdbc;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.synergy.exos.dao.ExtraDBMethods;
import com.synergy.exos.entity.Author;
import com.synergy.exos.entity.Publisher;

public class ExtraDB implements ExtraDBMethods{
	/**
	 * @param fetchMethod
	 * @param identifier
	 * @return
	 */
	public List<Author> fetchAuthor(int fetchMethod, String identifier) {
		Session session = SessionManufacturer.getSession();
		Transaction tx = null;
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Author> criteriaQuery = cb.createQuery(Author.class);
		Root<Author> root = criteriaQuery.from(Author.class);
		try {
			switch (fetchMethod) {
			case 3001:
				tx = session.beginTransaction();
				ParameterExpression<Integer> paraExId = cb.parameter(Integer.class);
				criteriaQuery.select(root).where(cb.equal(root.get("id"), paraExId));
				List<Author> allAuthorsById = session.createQuery(criteriaQuery)
						.setParameter(paraExId, Integer.parseInt(identifier)).setCacheable(true)
						.setCacheRegion("Author.cache").getResultList();
				tx.commit();
				session.close();
				return allAuthorsById;
			case 3002:
				String[] name = identifier.split("\\s");
				tx = session.beginTransaction();
				ParameterExpression<String> paraExFName = cb.parameter(String.class),
						paraExLName = cb.parameter(String.class);
				criteriaQuery.select(root).where(cb.and(cb.like(root.<String>get("first_name"), paraExFName),
						cb.like(root.<String>get("last_name"), paraExLName)));
				List<Author> allAuthorsByName = session.createQuery(criteriaQuery)
						.setParameter(paraExFName, "%" + name[0] + "%").setParameter(paraExLName, "%" + name[1] + "%")
						.setCacheable(true).setCacheRegion("Author.cache").getResultList();
				tx.commit();
				session.close();
				return allAuthorsByName;
			case 3003:
				tx = session.beginTransaction();
				criteriaQuery.select(root);
				List<Author> allAuthors = session.createQuery(criteriaQuery).setCacheable(true)
						.setCacheRegion("Author.class").getResultList();
				tx.commit();
				session.close();
				return allAuthors;
			}
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Failed...(Fetch At Author).." + ex);
		} finally {
			session.close();
		}
		return null;
	}
	
	/**
	 * @param fetchMethod
	 * @param identifier
	 * @return 
	 */
	public List<Publisher> fetchPublisher(int fetchMethod, String identifier) {
		Session session = SessionManufacturer.getSession();
		Transaction tx = null;
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Publisher> criteriaQuery = cb.createQuery(Publisher.class);
		Root<Publisher> root = criteriaQuery.from(Publisher.class);
		try {
			switch (fetchMethod) {
			case 3001:
				tx = session.beginTransaction();
				ParameterExpression<Integer> paraExId = cb.parameter(Integer.class);
				criteriaQuery.select(root).where(cb.equal(root.get("pid"), paraExId));
				List<Publisher> allPublishersById = session.createQuery(criteriaQuery)
						.setParameter(paraExId, Integer.parseInt(identifier)).setCacheable(true)
						.setCacheRegion("Publisher.cache").getResultList();
				tx.commit();
				session.close();
				return allPublishersById;
			case 3002:
				tx = session.beginTransaction();
				ParameterExpression<String> paraExName = cb.parameter(String.class);
				criteriaQuery.select(root).where(cb.like(root.<String>get("name"), paraExName));
				List<Publisher> allPublishersByName = session.createQuery(criteriaQuery)
						.setParameter(paraExName, "%" + identifier + "%").setCacheable(true)
						.setCacheRegion("Publisher.cache").getResultList();
				tx.commit();
				session.close();
				return allPublishersByName;
			case 3003:
				tx = session.beginTransaction();
				criteriaQuery.select(root);
				List<Publisher> allPublishers = session.createQuery(criteriaQuery).setCacheable(true)
						.setCacheRegion("Publisher.class").getResultList();
				tx.commit();
				session.close();
				return allPublishers;
			}
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Failed...(Fetch At Publisher).." + ex);
		} finally {
			session.close();
		}
		return null;
	}
}
