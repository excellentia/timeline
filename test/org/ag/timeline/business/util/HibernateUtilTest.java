package org.ag.timeline.business.util;

import junit.framework.Assert;

import org.ag.timeline.business.model.User;
import org.hibernate.Session;
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
		Session session =  factory.openSession();
		Assert.assertNotNull("Could not instantiate Session.", session);
		User user = (User)session.get(User.class, new Long(0));
		Assert.assertNotNull("Could not load User.", user);
		factory.close();
		factory = null;
	}
}