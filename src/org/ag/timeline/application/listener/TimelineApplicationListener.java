package org.ag.timeline.application.listener;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hsqldb.Server;

import org.ag.timeline.application.context.RequestContext;
import org.ag.timeline.application.context.TimelineContext;
import org.ag.timeline.application.exception.TimelineException;
import org.ag.timeline.business.service.iface.TimelineIface;
import org.ag.timeline.business.service.impl.TimelineImpl;
import org.ag.timeline.business.util.HibernateUtil;
import org.ag.timeline.common.TimelineConstants;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Application context listener (manager database server).
 * 
 * @author Abhishek Gaurav
 */
public class TimelineApplicationListener implements ServletContextListener {

	/**
	 * Database server instance.
	 */
	private Server server = null;

	/**
	 * Shuts down the database.
	 */
	private void shutdownDatabase() {
		System.out.print("Shutting down database...");

		try {
			if (server != null) {

				Class.forName(TimelineConstants.DATABASE_DRIVER);
				Connection connection = DriverManager.getConnection(TimelineConstants.DATABASE_WEB_URL);
				Statement statement = connection.createStatement();
				statement.execute("SHUTDOWN COMPACT");
				statement.close();
				connection.close();

				server.stop();
				server.shutdown();
				server.signalCloseAllServerConnections();
				server = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done.");
	}

	/**
	 * Starts the database.
	 */
	private void startDatabase(String applicationPath) {
		System.out.println("Starting database...");

		try {

			server = new Server();
			server.setLogWriter(null);
			server.setSilent(true);

			server.setDatabaseName(0, TimelineConstants.DATABASE_NAME);
			server.setDatabasePath(0, applicationPath + TimelineConstants.DATABASE_PATH);

			// Start the database!
			server.start();

			// test if DB is working fine
			test();

			// update weeks
			updateWeeks();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done.");
	}

	private void updateWeeks() {

		try {

			TimelineContext context = new TimelineContext(-1);
			RequestContext.setTimelineContext(context);
			TimelineIface impl = new TimelineImpl();
			impl.systemManagement();

		} catch (TimelineException e) {
			e.printStackTrace();
		} finally {
			RequestContext.destroy();
		}
	}

	private void test() {

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			long users = 0;

			Query query = session.createQuery("SELECT COUNT(*) from User");
			List<?> l = query.list();

			Iterator<?> iterator = l.iterator();

			if (iterator.hasNext()) {
				users = (Long) iterator.next();
			}

			// commit transaction
			transaction.commit();

			if (users > 0) {
				System.out.println("-----------------------\nDatabase working fine !\n-----------------------");
			} else {
				System.out.println("-----------------------\nProblem with Database !\n-----------------------");
			}

		} catch (HibernateException hibernateException) {
			if (transaction != null) {
				transaction.rollback();
			}
			hibernateException.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {

		shutdownDatabase();

		// Deregister Driver
		Enumeration<Driver> drivers = DriverManager.getDrivers();

		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		String applicationPath = event.getServletContext().getRealPath("/").replace('\\', '/');
		startDatabase(applicationPath);
	}

	/**
	 * Tests the listener as a stand alone class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TimelineApplicationListener listener = new TimelineApplicationListener();

		listener.contextInitialized(null);
		listener.test();
		listener.contextDestroyed(null);
	}

}
