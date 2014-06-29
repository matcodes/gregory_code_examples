package com.javademo.test.derby.dao;

import java.sql.Statement;

/**
 * Run once in order to create the tables.
 * 
 */
public class TableCreator extends AbstractDaoTest {

	/**
	 * Initializes the DB. Must be run once and only once to create structure.
	 * 
	 * @throws Exception
	 */
	public void initialize() throws Exception {

		Statement lStatement = getConnection().getConnection()
				.createStatement();

		lStatement
				.executeUpdate("CREATE TABLE PERSON (ID INT, NAME VARCHAR(60), FIRST_NAME VARCHAR(60), BIRTH_DATE DATE)");
	}

	/**
	 * Calls the {@link #initialize()} method on a new instance.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		new TableCreator().initialize();
	}
}
