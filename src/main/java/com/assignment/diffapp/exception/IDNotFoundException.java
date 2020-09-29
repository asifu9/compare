package com.assignment.diffapp.exception;
/**
 * The Class NoDataFoundException.
 * Used to show exception when there is no data for given ID
 */
public class IDNotFoundException extends CompareException {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3020485665386075766L;
	
	/**
	 * Constructor
	 *
	 * @param message the message
	 */
	public IDNotFoundException(String message){
		super(message);
	}

}
