package ua.nure.norkin.SummaryTask4.repository;

import ua.nure.norkin.SummaryTask4.entity.User;

public interface UserRepository extends Repository<User> {

	/**
	 * Finds User in database by specified email, it can be done because email
	 * should be unique.
	 *
	 * @param email
	 *            - user email
	 * @return User instance with such email
	 */
	public User find(String email);

	/**
	 * Finds User in database by specified login and password. Mainly used to
	 * login User in system.
	 *
	 * @param email
	 *            - user email
	 * @param password
	 *            - user password
	 * @return User with such fields
	 */
	public User find(String email, String password);
}
