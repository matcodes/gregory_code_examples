package com.javademo.test.derby.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.dataset.NoSuchTableException;
import com.javademo.test.derby.model.Person;


/**
 * A test case for {@link PersonDao}.
 * 
 */
public class PersonDaoTest extends AbstractDaoTest {

	/** The DAO to be tested. */
	private PersonDao personDao;

	/**
	 * @see org.dbunit.DatabaseTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {

		super.setUp();

		// Creates the DAO to be tested
		personDao = new PersonDao() {

			/**
			 * @see com.javademo.test.derby.dao.AbstractDao#createConnection()
			 */
			@Override
			protected Connection createConnection() throws SQLException {

				try {

					return getConnection().getConnection();

				} catch (Exception e) {

					throw new SQLException(e);
				}
			}
		};

		InputStream lStream = PersonDaoTest.class.getClassLoader()
				.getResourceAsStream("com/javademo/test/derby/dao/person.xls");

		// Creates the initialization data
		IDataSet lDataSet = new XlsDataSet(lStream);

    ITable tbl = lDataSet.getTable("PERSON");
    try {
		  DatabaseOperation.CLEAN_INSERT.execute(getConnection(), lDataSet);
		} catch (NoSuchTableException ex) {
		  new TableCreator().initialize();
		  DatabaseOperation.CLEAN_INSERT.execute(getConnection(), lDataSet);
		}
	}

	/**
	 * Test method for
	 * {@link com.javademo.test.derby.dao.PersonDao#findById(java.lang.Integer)}.
	 * 
	 * @throws SQLException
	 */
	public void testFindById() throws SQLException {

		Person lPerson = personDao.findById(1);

		assertNotNull(lPerson);
	}
}
