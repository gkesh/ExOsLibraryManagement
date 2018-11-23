/**
 * Methods for accessing and manipulating the books data in the database
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

import com.synergy.exos.dao.BookDBMethods;
import com.synergy.exos.entity.Author;
import com.synergy.exos.entity.Books;
import com.synergy.exos.entity.Publisher;

/**
 * @author G-Kesh
 *
 */
public class BooksDB implements BookDBMethods {

	/**
	 * @param book
	 * @param authors
	 * @param pb
	 */
	public void save(Books book, List<Author> authors, Publisher pb) {
		/*
		 * To enable batch procession in java which will avoid the OutOfSpaceException
		 * You need to use session.flush() to flush the data and session.clear() to
		 * clean the session Use an iteration and count the number of records entered
		 * and at 50, flush and clean the cache This will be used while saving or
		 * updating the data Example: if(count%50==0) {session.flush();
		 * session.clear();}
		 */
		Session session = SessionManufacturer.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (pb != null)
				session.save(pb);
			session.save(book);
			if (authors != null) {
				authors.forEach((author) -> {
					session.save(author);
				});
			}
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Error..(Save)..." + ex);
		} finally {
			session.close();
		}
	}

	/**
	 * @return the list of books based on the given identifier and method
	 * @param fetchMethod
	 * @param identifier
	 */
	public List<Books> fetch(int fetchMethod, String identifier) {
		Session session = SessionManufacturer.getSession();
		Transaction tx = null;
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Books> criteriaQuery = cb.createQuery(Books.class);
		Root<Books> root = criteriaQuery.from(Books.class);
		try {
			switch (fetchMethod) {
			case 2001:
				tx = session.beginTransaction();
				ParameterExpression<String> paraExIsbn = cb.parameter(String.class);
				criteriaQuery.select(root).where(cb.equal(root.get("isbn"), paraExIsbn));
				List<Books> allBooksByIsbn = session.createQuery(criteriaQuery).setParameter(paraExIsbn, identifier)
						.setCacheable(true).setCacheRegion("Books.cache").getResultList();
				tx.commit();
				session.close();
				return allBooksByIsbn;
			case 2002:
				tx = session.beginTransaction();
				ParameterExpression<String> paraExTitle = cb.parameter(String.class);
				criteriaQuery.select(root).where(cb.like(root.<String>get("title"), paraExTitle));
				List<Books> allBooksByTitle = session.createQuery(criteriaQuery)
						.setParameter(paraExTitle, "%" + identifier + "%").setCacheable(true)
						.setCacheRegion("Books.cache").getResultList();
				tx.commit();
				session.close();
				return allBooksByTitle;
			case 2003:
				tx = session.beginTransaction();
				ParameterExpression<Integer> paraExAuthor = cb.parameter(Integer.class);
				CriteriaQuery<Object> acriteriaQuery = cb.createQuery(Object.class);
				Root<Author> aroot = acriteriaQuery.from(Author.class);
				acriteriaQuery.select(aroot.get("books")).where(cb.equal(aroot.get("id"), paraExAuthor));
				@SuppressWarnings("unchecked")
				List<Books> allBooksByAuthor = (List<Books>) (List<?>) session.createQuery(acriteriaQuery)
						.setParameter(paraExAuthor, Integer.parseInt(identifier)).setCacheable(true)
						.setCacheRegion("Author.cache").getResultList();
				tx.commit();
				session.close();
				return allBooksByAuthor;
			case 2004:
				tx = session.beginTransaction();
				ParameterExpression<Integer> paraExPublisher = cb.parameter(Integer.class);
				CriteriaQuery<Object> pcriteriaQuery = cb.createQuery(Object.class);
				Root<Publisher> proot = pcriteriaQuery.from(Publisher.class);
				pcriteriaQuery.select(proot.get("books")).where(cb.equal(proot.get("pid"), paraExPublisher));
				@SuppressWarnings("unchecked")
				List<Books> allBooksByPublisher = (List<Books>) (List<?>) session.createQuery(pcriteriaQuery)
						.setParameter(paraExPublisher, Integer.parseInt(identifier)).setCacheable(true).getResultList();
				tx.commit();
				session.close();
				return allBooksByPublisher;
			case 2005:
				tx = session.beginTransaction();
				criteriaQuery.select(root);
				List<Books> allBooks = session.createQuery(criteriaQuery).setCacheable(true)
						.setCacheRegion("Books.cache").getResultList();
				tx.commit();
				session.close();
				return allBooks;
			}
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Error...(Fetch)..." + ex);
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * @param book
	 * @param authors
	 * @param pb
	 */
	public void update(Books book, List<Author> authors, Publisher pb) {
		Session session = SessionManufacturer.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (pb != null)
				session.save(pb);
			session.update(book);
			if (authors != null) {
				authors.forEach((author) -> {
					session.save(author);
				});
			}
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Failed...(Update)..." + ex);
		} finally {
			session.close();
		}
	}

	/**
	 * @param book
	 */
	public void terminate(Books book) {
		Session session = SessionManufacturer.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(book);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Failed...(Delete)..." + ex);
		} finally {
			session.close();
		}
	}

}
