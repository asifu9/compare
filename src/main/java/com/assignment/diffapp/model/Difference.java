package com.assignment.diffapp.model;
import lombok.AllArgsConstructor;
import lombok.Value;




/**
 * Difference to hold the difference from offset with length
 *
 */
@AllArgsConstructor
@Value
public class Difference {

	  /** The offset. */
  	private int offset;
	  
	  /** The length. */
  	private int length;
	  
}
