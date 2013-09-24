package org.ag.timeline.business.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Manages Hibernate integration.
 * 
 * @author Abhishek Gaurav
 */
public final class HibernateUtil {

	/**
	 * SessionFactory instance.
	 */
	private static SessionFactory sessionFactory;

	/**
	 * Private constructor.
	 */
	private HibernateUtil() {
		// no calls from external world
	}

	/**
	 * Returns singleton instance for {@link SessionFactory} based on default
	 * hibernate config file name.
	 * 
	 * @return {@link SessionFactory} instance.
	 */
	public static SessionFactory getSessionFactory() {
		return getSessionFactory(null);
	}

	/**
	 * Returns singleton instance for {@link SessionFactory} based on provided
	 * config file name.
	 * 
	 * @param configFileName Name of config file in classpath, to be used for
	 *            configuring {@link SessionFactory}.
	 * @return {@link SessionFactory} instance.
	 */
	public static SessionFactory getSessionFactory(String configFileName) {
		if (sessionFactory == null) {

			try {

				Configuration configuration = new Configuration();

				// check if config file is to be used
				if (configFileName != null) {
					configuration.configure(configFileName);
				} else {
					configuration.configure();
				}

				ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
				ServiceRegistry registry = builder.applySettings(configuration.getProperties()).buildServiceRegistry();

				sessionFactory = configuration.buildSessionFactory(registry);

			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}

		return sessionFactory;
	}
}