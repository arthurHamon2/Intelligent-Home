package server.database.dao;

import java.util.List;

import server.database.dal.ConnectionException;
import server.database.dal.Dal;

public abstract class AbstractDao<T,ID> {

	/*
	 * The data access link that initialize the connection with the database
	 */
	protected Dal dal = null;
	
	/**
	 * The constructor which initialize the Data Access Link
	 */
	public AbstractDao()
	{
		try {
			this.dal = Dal.instance();
		} catch (ConnectionException e) {
			System.out.println("Connection problem occurs with the database, please retry.");
			e.printStackTrace();
		}
	}
    
    /**
     * Getting the right object with its id
     * @param id of the object
     * @return
     */
    public abstract T find(ID id);
    
    /**
     * Getting all objects
     * @return
     */
    public abstract List<T> findAll();
	
	/**
	 * Persist the given object in parameters
	 * The object that you want to persist in the database
	 * @param obj that you want to create
	 */
	public abstract void create(T obj);
	
	/**
	 * Update the object in the database by the given object.
	 * The updated object must be available in the database. 
	 * @param obj that you want to update
	 */
	public abstract void update(T obj);
	
	/**
	 * Delete the given object in the database.
	 * The object must be available in the database. 
	 * @param obj that you want to delete
	 */
	public abstract void delete(T obj);

}
