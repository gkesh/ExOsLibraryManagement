/**
 * Methods for accessing and manipulating the users data in the database
 */
package com.synergy.exos.hdbc;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.scheduling.annotation.Async;

import com.synergy.exos.dao.UserDBMethods;
import com.synergy.exos.entity.Users;
import com.synergy.exos.hdbc.extrafetch.ExtraFetch;
import com.synergy.exos.hdbc.extrafetch.UsersFetch;

/**
 * @author G-Kesh
 *
 */
public class UsersDB implements UserDBMethods {
	/**
	 * @param usr
	 */
	public void save(Users usr) {
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
			session.save(usr);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Failed..." + ex);
		} finally {
			session.close();
		}
	}

	/**
	 * @return the list of users based on the given criteria and instructed method
	 * @param fetchMethod - defines what to fetch with
	 * @param identifier - the item that will be used to fetch the info
	 */
	public List<Users> fetch(int fetchMethod, String identifier) {
		Session session = SessionManufacturer.getSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Users> criteriaQuery = cb.createQuery(Users.class);
		Root<Users> root = criteriaQuery.from(Users.class);
		Transaction tx = null;
		try {
			switch (fetchMethod) {
			case 1001:
				tx = session.beginTransaction();
				ParameterExpression<Integer> paraExId = cb.parameter(Integer.class);
				criteriaQuery.select(root).where(cb.equal(root.get("id"), paraExId));
				List<Users> allUsersById = session.createQuery(criteriaQuery)
						.setParameter(paraExId, Integer.parseInt(identifier)).setCacheable(true)
						.setCacheRegion("Users.cache").getResultList();
				tx.commit();
				session.close();
				return allUsersById;
			case 1002:
				tx = session.beginTransaction();
				ParameterExpression<String> paraExUsername = cb.parameter(String.class);
				criteriaQuery.select(root).where(cb.equal(root.<String>get("username"), paraExUsername));
				List<Users> allUsersByUsername = session.createQuery(criteriaQuery)
						.setParameter(paraExUsername, identifier).setCacheable(true).setCacheRegion("Users.cache")
						.getResultList();
				tx.commit();
				session.close();
				return allUsersByUsername;
			case 1003:
				String[] fullName = identifier.split("\\s");
				tx = session.beginTransaction();
				ParameterExpression<String> paraExFN = cb.parameter(String.class),
						paraExLN = cb.parameter(String.class);
				criteriaQuery.select(root).where(cb.and(cb.like(root.<String>get("first_name"), paraExFN),
						cb.like(root.<String>get("last_name"), paraExLN)));
				List<Users> allUsersByFullName = session.createQuery(criteriaQuery)
						.setParameter(paraExFN, "%" + fullName[0] + "%").setParameter(paraExLN, "%" + fullName[1] + "%")
						.setCacheable(true).setCacheRegion("Users.cache").getResultList();
				tx.commit();
				session.close();
				return allUsersByFullName;
			case 1004:
				tx = session.beginTransaction();
				criteriaQuery.select(root);
				List<Users> allUsers = session.createQuery(criteriaQuery).setCacheable(true)
						.setCacheRegion("Users.cache").getResultList();
				tx.commit();
				session.close();
				return allUsers;
			case 1005:
				tx = session.beginTransaction();
				ParameterExpression<String> paraExEmail = cb.parameter(String.class);
				criteriaQuery.select(root).where(cb.equal(root.<String>get("email_add"), paraExEmail));
				List<Users> allUsersByEmail = session.createQuery(criteriaQuery).setParameter(paraExEmail, identifier)
						.setCacheable(true).setCacheRegion("Users.cache").getResultList();
				tx.commit();
				session.close();
				return allUsersByEmail;
			}
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Failed.." + ex);
		} finally {
			session.close();
		}
		return null;
	}

	/**
	 * @return the updated information
	 * @param usr
	 */
	public void update(Users usr) {
		Session session = SessionManufacturer.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(usr);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Failed..." + ex);
		} finally {
			session.close();
		}
	}

	/**
	 * @param usr
	 * @return the deleted data
	 */
	public void terminate(Users usr) {
		Session session = SessionManufacturer.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(usr);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.err.println("Transaction Processing Failed..." + ex);
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Users login(String username, String password) {
		Users usr = fetch(UsersFetch.FETCH_BY_USERNAME, username).get(0);
		if (usr.getPassword().equals(password))
			return usr;
		else
			return null;
	}

	/**
	 * 
	 * @param identifier
	 * @param parameter
	 * @return
	 */
	public boolean userExists(String identifier, int parameter) {
		boolean exists = false;
		switch (parameter) {
		case 1002:
			if (!fetch(UsersFetch.FETCH_BY_USERNAME, identifier).isEmpty())
				exists = true;
			break;
		case 1005:
			if (!fetch(UsersFetch.FETCH_BY_EMAIL, identifier).isEmpty())
				exists = true;
			break;
		}
		return exists;
	}

	/**
	 * notify users that the book needs to be returned
	 */
	public static void notifyReturnDate() {
		new UsersDB().fetch(UsersFetch.FETCH_ALL, null).parallelStream().forEach((user) -> {
			if (!user.getBook().isEmpty()) {
				user.getBook().parallelStream().forEach((withdrawl) -> {
					Timestamp withdrawlTime = withdrawl.getIssuedDate();
					if (Days.daysBetween(new DateTime(withdrawlTime.getTime()), new DateTime()).getDays() > 30)
						try {
							sendMail(user, user.getEmail_add(), "exOs - Delay in Book Return", "Hi, "
									+ user.getFirst_name() + ", \n\nYour withdrawl of the book \""
									+ withdrawl.getBook().getTitle()
									+ "\" has exceeded its normal time frame of 30 days. Please reissue or return the book in time or fines will be charged.\n\nKindly,\nexOs Management Team.");
						} catch (UnsupportedEncodingException | MessagingException e) {
							e.printStackTrace();
							new com.synergy.exos.uxinterface.NotificationDialog()
									.createNotification("Error occured while sending Late Notification Email");
						}
				});
			}
		});
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param tempUser
	 * @param tempEmail
	 * @param subject
	 * @param text
	 */
	@Async
	public static void sendMail(@NotNull final Users tempUser, @NotBlank @Email final String tempEmail,
			@NotBlank final String subject, @NotBlank final String text)
			throws MessagingException, UnsupportedEncodingException {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ExtraFetch.USER_MAIL, ExtraFetch.USER_PASSWORD);
			}
		};

		javax.mail.Session session = javax.mail.Session.getInstance(properties, auth);

		final MimeMessage message = new MimeMessage(session);
		message.addHeader("Content-type", "text/HTML; charset=UTF-8");
		message.addHeader("format", "flowed");
		message.addHeader("Content-Transfer-Encoding", "8bit");
		message.setFrom(new InternetAddress("no_reply@exos.com", "NoReply-exOs"));
		message.setReplyTo(InternetAddress.parse("no_reply@exos.com", false));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(tempEmail));
		message.setSubject(subject, "UTF-8");
		message.setText(text, "UTF-8");
		message.setSentDate(new Date());
		Transport.send(message);

	}

}
