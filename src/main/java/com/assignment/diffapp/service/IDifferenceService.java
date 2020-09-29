package com.assignment.diffapp.service;
/**
 * The Interface IDifferenceService.
 * 
 *
 * @param <T> the generic type
 * @param <E> the element type
 */
public interface IDifferenceService<T, E, S> {

	/**
	 * Method to Compare.
	 *
	 * @param e the e
	 * @param s the s
	 * @return the t
	 */
	public T compare(E e,S s);
}
