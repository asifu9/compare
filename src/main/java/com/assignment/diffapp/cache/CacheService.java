package com.assignment.diffapp.cache;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.assignment.diffapp.cache.Cache;
import com.assignment.diffapp.exception.CacheException;

/**
 * The Class CacheService.
 * To represent a in memory cache.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
@Service
public class CacheService<K,V> {

	/** The cache size. */
	@Value("${compare.cache.size}")
	private String cacheSize;
	
	/** The cache. */
	Cache<K, V> cache=null;
	
	/**
	 * Inits the.
	 */
	@PostConstruct
    public void init() {
		cache=new Cache<>(Integer.parseInt(cacheSize));
    }
	
	/**
	 * Instantiates a new cache service.
	 */
	public CacheService() {
	}

	/**
	 * Method to put the data into cache
	 * If cache is full then last recently used
	 * entry will be removed from the cache and
	 * new record will be entered.
	 *
	 * @param key the key
	 * @param value the value
	 * @throws CacheException  throws exception
	 */
	public void put(K key, V value) throws CacheException {
		cache.put(key, value);
		
	}

	/**
	 * Method to get the data for given key.
	 *
	 * @param key the key
	 * @return the optional value from cache
	 * @throws CacheException throws exception
	 */
	public Optional<V> get(K key) throws CacheException {
		return cache.get(key);
	}
	
	
}
