package com.javademo.test.derby.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.javademo.test.derby.model.Person;


/**
 * DAO for accessing the datas of a person.
 * 
 */
public class PersonDao extends AbstractDao<Person, Integer> {

    /**
     * @throws SQLException
     * @see com.javademo.test.derby.dao.IDao#findById(java.lang.Object)
     */
    public Person findById(Integer pId) throws SQLException {

	Connection lConnection = null;

	try {

	    lConnection = createConnection();

	    PreparedStatement lStatement = lConnection
		    .prepareStatement("SELECT * FROM PERSON WHERE ID = ?");

	    lStatement.setInt(1, pId);

	    ResultSet lRs = lStatement.executeQuery();

	    if (lRs.next()) {

		Person lPerson = new Person();

		lPerson.setId(lRs.getLong("ID"));
		lPerson.setName(lRs.getString("NAME"));
		lPerson.setFirstName(lRs.getString("FIRST_NAME"));
		lPerson.setBirthDate(lRs.getDate("BIRTH_DATE"));

		return lPerson;
	    }

	} finally {

	    if (lConnection == null) {

		lConnection.close();
	    }
	}

	return null;
    }

    /**
     * @see com.javademo.test.derby.dao.IDao#getAll()
     */
    public Collection<Person> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    /**
     * @see com.javademo.test.derby.dao.IDao#removeById(java.lang.Object)
     */
    public void removeById(Integer pId) {
	// TODO Auto-generated method stub

    }
}
