package ua.nure.norkin.SummaryTask4.repository;

import java.util.Collection;

public interface Repository<T> {

	public void create(T entity);

	public void update(T entity);

	public void delete(T entity);

	public T find(int entityPK);

	public Collection<T> findAll();
}
