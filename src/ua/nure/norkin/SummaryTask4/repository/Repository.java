package ua.nure.norkin.SummaryTask4.repository;

import java.util.Collection;

/**
 * Main interface for all DAO objects with operations of creating, updating,
 * deleting and finding some specific object. Also all subclasses should
 * implement findAll() method, which finds all saved objects of type
 * <code>T</code> in the database, xml files etc.
 *
 * @author Mark Norkin
 *
 * @param <T>
 *            type of the object
 */
public interface Repository<T> {

	/**
	 * Saves specified object in repository.
	 *
	 * @param entity
	 *            - object to be saved
	 */
	public void create(T entity);

	/**
	 * Updates specified object in repository
	 *
	 * @param entity
	 *            - object to be updated
	 */
	public void update(T entity);

	/**
	 * Deletes specified object from repository
	 *
	 * @param entity
	 *            - object to be deleted
	 */
	public void delete(T entity);

	/**
	 * Finds object in repository by it's unique primary key.
	 *
	 * @param entityPK
	 *            - primary key of the object that should be found.
	 * @return object of type <code>T</code>, which unique id is equal to
	 *         specified.
	 */
	public T find(int entityPK);

	/**
	 * Finds all objects of type <code>T</code> in repository.
	 *
	 * @return all objects that repository has at this point of time.
	 */
	public Collection<T> findAll();
}
