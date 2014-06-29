package com.javademo.test.derby.model;

import java.io.Serializable;
import java.util.Date;

/**
 * A person in the system.
 * 
 */
public class Person implements Serializable {

    /** Unique serialization ID. */
    private static final long serialVersionUID = 200536397636273910L;

    /** Person's id in the back-end system. */
    private long id;

    /** Name of the person. */
    private String name;

    /** First name of the person. */
    private String firstName;

    /** Date of birth of the person. */
    private Date birthDate;

    /**
     * Getter of id
     * 
     * @return Value of id
     */
    public long getId() {
	return id;
    }

    /**
     * Getter of name
     * 
     * @return Value of name
     */
    public String getName() {
	return name;
    }

    /**
     * Getter of firstName
     * 
     * @return Value of firstName
     */
    public String getFirstName() {
	return firstName;
    }

    /**
     * Getter of birthDate
     * 
     * @return Value of birthDate
     */
    public Date getBirthDate() {
	return birthDate;
    }

    /**
     * Setter of id.
     * 
     * @param pId
     *                New value for id
     */
    public void setId(long pId) {
	id = pId;
    }

    /**
     * Setter of name.
     * 
     * @param pName
     *                New value for name
     */
    public void setName(String pName) {
	name = pName;
    }

    /**
     * Setter of firstName.
     * 
     * @param pFirstName
     *                New value for firstName
     */
    public void setFirstName(String pFirstName) {
	firstName = pFirstName;
    }

    /**
     * Setter of birthDate.
     * 
     * @param pBirthDate
     *                New value for birthDate
     */
    public void setBirthDate(Date pBirthDate) {
	birthDate = pBirthDate;
    }
}
