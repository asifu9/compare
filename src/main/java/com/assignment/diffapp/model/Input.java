package com.assignment.diffapp.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * Class Input to hold the left and right data
 * @author usman
 *
 */
@EqualsAndHashCode(of = "id")
@Builder
@Data
@ToString
public class Input {

	/** The id value to identify uniquely */
	int id;
	
	/** The left value for comparison */
	String left;
	
	/** The right value for comparison */
	String right;
	
}
