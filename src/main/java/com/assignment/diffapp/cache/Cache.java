package com.assignment.diffapp.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assignment.diffapp.exception.CacheException;
import com.assignment.diffapp.service.CompareService;

/**
 * The Class Cache. Class to build the cache with method to put and get for
 * given key.
 *
 */
public final class Cache<K, V> {

	/** The capacity. */
	private final int capacity;

	/** The map. */
	private Map<K, V> map;

	/** The read write lock. */
	private final ReadWriteLock readWriteLock;

	/** The read lock. */
	private final Lock readLock;

	/** The write lock. */
	private final Lock writeLock;
	
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompareService.class);


	/**
	 * Constructor to Instantiates a new cache.
	 *
	 * @param capacity the capacity
	 */
	public Cache(int capacity) {
		this.capacity = capacity;
		this.map = new LinkedHashMap<K, V>(this.capacity, 0.75F, true) {
			private static final long serialVersionUID = 7911913901138518801L;
			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > capacity;
			}
		};
		this.readWriteLock = new ReentrantReadWriteLock(true);
		this.readLock = readWriteLock.readLock();
		this.writeLock = readWriteLock.writeLock();
	}

	/**
	 * Method to put the data in cache.
	 *
	 * @param key   the key
	 * @param value the value
	 * @throws CacheException throw exception
	 */
	public void put(K key, V value) throws CacheException {
		writeLock.lock();
		try {
			this.map.put(key, value);

		} catch(Exception ex) {
			LOGGER.error("put method failed: error details {}",ex);
			throw new CacheException(ex.getMessage());
		} finally {
			writeLock.unlock();
		}
	}

	/**
	 * Method to get the data from cache.
	 *
	 * @param key the key
	 * @return the Optional returns the optional value of value
	 * @throws CacheException throw exception
	 */
	public Optional<V> get(K key) throws CacheException {
		readLock.lock();
		try {
			return this.map.get(key)==null? Optional.empty():Optional.of(this.map.get(key));

		} catch(Exception ex) {
			LOGGER.error("get method failed: error details {}",ex);
			throw new CacheException(ex.getMessage());
		} finally {
			readLock.unlock();
		}
	}
}
