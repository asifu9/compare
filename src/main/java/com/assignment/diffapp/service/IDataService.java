package com.assignment.diffapp.service;

import java.util.Optional;

import com.assignment.diffapp.exception.CacheException;

/**
 * The Interface IDataService.
 *
 * This interface defines methods for the data service.
 * Currently its been implemented by CacheDataService.
 * 
 *
 * @param <T> the generic type
 */
public interface IDataService<T> {

	/**
	 * Method to create or update the data.
	 *
	 * @param input the input
	 * @throws CacheException the cache exception
	 */
	public void createOrUpdate(T input) throws CacheException;
	
	/**
	 * Method to find the data for given id.
	 *
	 * @param id the id
	 * @return the t
	 * @throws CacheException the cache exception
	 */
	public Optional<T> findById(int id) throws CacheException;
}
