package com.assignment.diffapp.cache;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.diffapp.exception.CacheException;
import com.assignment.diffapp.model.Input;
import com.assignment.diffapp.service.IDataService;

/**
 * The Class CacheDataService.
 * This service will be used to create comparable data
 * and find the the data.
 */
@Service
public class CacheDataService implements IDataService<Input>{

	/** The cache service. */
	@Autowired
	private CacheService<Integer,Input> cacheService;
	
	/**
	 * Method to create or update the entry in cache
	 *
	 * @param input the Input object
	 * @throws CacheException throws exception
	 */
	@Override
	public void createOrUpdate(Input input) throws CacheException {
		cacheService.put(input.getId(),input);
	}

	/**
	 * Method to find Input object for given ID.
	 *
	 * @param id the id
	 * @return the Optional object as result
	 * @throws CacheException throws exception
	 */
	@Override
	public Optional<Input> findById(int id) throws CacheException {
		return cacheService.get(id);
	}

}
