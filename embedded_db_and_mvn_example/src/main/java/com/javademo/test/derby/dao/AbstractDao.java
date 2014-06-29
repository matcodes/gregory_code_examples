package com.javademo.test.derby.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * An abstract DAO that knows how to get a connection to the db.
 * 
 * @param <T>
 *            Object type of the DAO
 * @param <K>
 *            Object type of the unique key
 */
public abstract class AbstractDao<T extends Serializable, K> implements
		IDao<T, K> {

	/** Connection URL . */
	private static final String CONNECTION_URL;

	/** Connection login to the DB. */
	private static final String CONNECTION_LOGIN;

	/** Connection password to the DB. */
	private static final String CONNECTION_PWD;

	static {
		CONNECTION_URL = System.getProperty("com.javademo.url");
		CONNECTION_LOGIN = System.getProperty("com.javademo.login");
		CONNECTION_PWD = System.getProperty("com.javademo.pwd");
	}

	/**
	 * Gets a connection to the database.
	 * 
	 * 
	 * @return Connection
	 * @throws SQLException
	 *             If the DB can't be connected to
	 */
	protected Connection createConnection() throws SQLException {

		return DriverManager.getConnection(CONNECTION_URL, CONNECTION_LOGIN,
				CONNECTION_PWD);
	}
}
