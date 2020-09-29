package com.assignment.diffapp.configuration;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Instantiates a new error details.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorDetails {

	/** The timestamp. */
	private Date timestamp;
	  
  	/** The message  */
  	private String message;
	  
  	/** The details. */
  	private String details;
}
