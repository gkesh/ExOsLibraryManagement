/**
 * 
 */
package com.synergy.exos.hdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.synergy.exos.entity.Author;
import com.synergy.exos.entity.Books;
import com.synergy.exos.entity.Publisher;
import com.synergy.exos.entity.UserBooks;
import com.synergy.exos.entity.Users;

/**
 * @author G-Kesh
 *
 */
public final class SessionManufacturer {
	private static Configuration cfg;
	private static ServiceRegistry sr;
	private static SessionFactory sf;

	public SessionManufacturer() {
		try {
			cfg = new Configuration().configure().addAnnotatedClass(Users.class).addAnnotatedClass(Books.class)
					.addAnnotatedClass(Author.class).addAnnotatedClass(Publisher.class)
					.addAnnotatedClass(UserBooks.class);
		} catch (Throwable ex) {
			System.err.println("Failed to initiate SessionManufacturer..." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		sr = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		sf = cfg.buildSessionFactory(sr);
	}

	public static Session getSession() {
		return sf.openSession();
	}
}
