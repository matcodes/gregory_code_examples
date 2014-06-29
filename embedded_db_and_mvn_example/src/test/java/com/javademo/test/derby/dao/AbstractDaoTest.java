package com.javademo.test.derby.dao;

import org.dbunit.JdbcBasedDBTestCase;
import org.dbunit.dataset.IDataSet;

/**
 * An abstract Jdbc test case that factorizes the common methods.
 * 
 */
public abstract class AbstractDaoTest extends JdbcBasedDBTestCase {

	/**
	 * @see org.dbunit.JdbcBasedDBTestCase#getConnectionUrl()
	 */
	@Override
	protected String getConnectionUrl() {
		return "jdbc:derby:myDB;create=true";
	}

	/**
	 * @see org.dbunit.JdbcBasedDBTestCase#getDriverClass()
	 */
	@Override
	protected String getDriverClass() {
		return "org.apache.derby.jdbc.EmbeddedDriver";
	}

	/**
	 * @see org.dbunit.DatabaseTestCase#getDataSet()
	 */
	@Override
	protected IDataSet getDataSet() throws Exception {
		return getConnection().createDataSet();
	}
}
