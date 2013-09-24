package org.ag.timeline.business.util;

import junit.framework.Assert;

import org.hibernate.SessionFactory;

import org.junit.Test;

/**
 * Test case for {@link HibernateUtil}.
 * 
 * @author Abhishek Gaurav
 */
public class HibernateUtilTest {

	/**
	 * Test config file name.
	 */
	private final String testConfigName = "hibernate-test.cfg.xml";

	/**
	 * Creation test.
	 */
	@Test
	public void sessionFactoryCreateTest() {
		SessionFactory factory = HibernateUtil.getSessionFactory(testConfigName);
		Assert.assertNotNull("Could not instantiate SessionFactory.", factory);
		factory.close();
		factory = null;
	}
}