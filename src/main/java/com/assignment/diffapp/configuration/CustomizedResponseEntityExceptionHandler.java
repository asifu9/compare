package com.assignment.diffapp.configuration;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assignment.diffapp.exception.IDNotFoundException;
import com.assignment.diffapp.exception.InputDataMissingException;

/**
 * The Class CustomizedResponseEntityExceptionHandler.
 * To handle all the exceptions of the application
 */
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	 /**
  	 * Method to handle user not found exception.
  	 *
  	 * @param ex the IDNotFoundException exception
  	 * @param request the WebRequest request object
  	 * @return the response entity
  	 */
  	@ExceptionHandler(IDNotFoundException.class)
	  public final ResponseEntity<Object> handlerNoDataFoundException(IDNotFoundException ex, WebRequest request) {
  		
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getLocalizedMessage(),
	        request.getDescription(false));
        
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
  	
  	@ExceptionHandler(InputDataMissingException.class)
	  public final ResponseEntity<Object> handleMissingDataException(InputDataMissingException ex, WebRequest request) {
  		
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getLocalizedMessage(),
	        request.getDescription(false));
        
	    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  
}
