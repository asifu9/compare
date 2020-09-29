package com.assignment.diffapp.model;

import java.util.List;

import com.assignment.diffapp.enums.OutputType;

import lombok.Value;

/**
 * Class Output to hold the result data
 *
 */
@Value
public class Output {

	/** The status of the output */
	OutputType status;
	
	/** The difference if there are any offsets */
	List<Difference> difference;
	
}
