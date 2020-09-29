package com.assignment.diffapp.exception;
/**
 * The Class CacheException.
 * Used to show exception when there is any error in Caching
 */
public class CacheException extends CompareException {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3020485665386075766L;
	
	/**
	 * Constructor
	 *
	 * @param message the message
	 */
	public CacheException(String message){
		super(message);
	}
}
