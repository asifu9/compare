package com.assignment.diffapp.service;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.diffapp.enums.InputType;
import com.assignment.diffapp.exception.CacheException;
import com.assignment.diffapp.exception.CompareException;
import com.assignment.diffapp.exception.IDNotFoundException;
import com.assignment.diffapp.exception.InputDataMissingException;
import com.assignment.diffapp.model.Input;
import com.assignment.diffapp.model.Output;

// TODO: Auto-generated Javadoc
/**
 * The Class CompareService. This service class will hold methods to
 * create/update input data and also the compare method.
 */
@Service
public class CompareService {

	/**  The id not found message. */
	private static String ID_NOT_FOUND = " No Data found for ID %s ";
	
	/**  The left missing message. */
	private static String LEFT_MISSING = " Left data missing for ID %s ";
	
	/**  The right missing message. */
	private static String RIGHT_MISSING = " Right data missing for ID %s ";

	/** The data service. */
	@Autowired
	private IDataService<Input> dataService;

	/** The difference service. */
	@Autowired
	private IDifferenceService<Output,String,String> differenceService;
	
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompareService.class);

	/**
	 * Method to build and store the Input object.
	 *
	 * @param id        the id
	 * @param data      the data
	 * @param inputType the input type
	 * @throws CacheException the cache exception
	 */
	public void createInput(int id, String data, InputType inputType) throws CacheException {
		
		LOGGER.debug("CreateInput: ID = {}, data = {} and InputType = {} ", id, data, inputType);
		Optional<Input> inputOptional = dataService.findById(id);
		Input input=null;

		if (inputOptional.isPresent()) {
			input = inputOptional.get();
		}else {
			input = Input.builder().id(id).build();
		}
		
		if (inputType == InputType.LEFT) {
			input.setLeft(data);
		} else {
			input.setRight(data);
		}
		dataService.createOrUpdate(input);
	}

	/**
	 * Method to compare the left and right data from Input object.
	 *
	 * @param id the id
	 * @return the Output object
	 * @throws CompareException the compare exception
	 */
	public Output compare(int id) throws CompareException {
		
		LOGGER.debug("comparing for : ID = {} ", id);

		Optional<Input> input = dataService.findById(id);
		validateInput(id, input);
		return differenceService.compare(input.get().getLeft(),input.get().getRight());
		
	}

	/**
	 * Validate input data.
	 *
	 * @param id    the id
	 * @param input the input
	 * @throws CompareException the compare exception
	 */
	void validateInput(int id, Optional<Input> input) throws CompareException {
		LOGGER.debug("validating input for : ID = {} and input = {}", id, input);

		if (!input.isPresent()) {
			throw new IDNotFoundException(String.format(ID_NOT_FOUND, id));
		}
		if (input.get().getLeft() == null) {
			throw new InputDataMissingException(String.format(LEFT_MISSING, id));
		}
		if (input.get().getRight() == null) {
			throw new InputDataMissingException(String.format(RIGHT_MISSING, id));
		}
	}

}
