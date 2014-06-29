package com.javademo.test.derby.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;

/**
 * A DAO interface with common methods.
 * 
 * @param <T>
 *                Object type of the DAO
 * @param <K>
 *                Object type of the unique key
 */
public interface IDao<T extends Serializable, K> {

    /**
     * Returns the object denoted by this unique identifier in the back-end
     * system.
     * 
     * @param pId
     *                Unique identifier
     * @return Object
     * @throws SQLException
     *                 If an error occurs
     */
    T findById(K pId) throws SQLException;

    /**
     * Removes the object denoted by this unique identifier from the back-end
     * system.
     * 
     * @param pId
     *                Unique identifier
     * @throws SQLException
     *                 If an error occurs
     */
    void removeById(K pId) throws SQLException;

    /**
     * Returns all the object in the back-end system.
     * 
     * @return Collection of objects
     * @throws SQLException
     *                 If an error occurs
     */
    Collection<T> getAll() throws SQLException;
}
