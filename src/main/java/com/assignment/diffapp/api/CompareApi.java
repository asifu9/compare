package com.assignment.diffapp.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.diffapp.enums.InputType;
import com.assignment.diffapp.exception.CacheException;
import com.assignment.diffapp.exception.CompareException;
import com.assignment.diffapp.model.Output;
import com.assignment.diffapp.service.CompareService;

/**
 * The Class CompareApi represent rest controller to create API.
 * 
 */
@RestController
@RequestMapping("/api/")
public class CompareApi {

	/** The compare service. */
	@Autowired
	CompareService compareService;
	
	/**
	 * Rest API to create left data to compare.
	 *
	 * @param id the id
	 * @param entity the entity
	 * @return the response entity
	 * @throws CacheException the cache exception
	 */
	@RequestMapping(value="v1/diff/{id}/left", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> createLeft (@PathVariable int id,InputStream entity) throws CacheException {
		
		compareService.createInput(id, getStringFromBinary(entity), InputType.LEFT);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	private String getStringFromBinary(InputStream stream){
		String text = new BufferedReader(
			      new InputStreamReader(stream, StandardCharsets.UTF_8))
			        .lines()
			        .collect(Collectors.joining("\n"));
		return new String(Base64.getDecoder().decode(text));
	}
	
	/**
	 * Rest API to create right data to compare.
	 *
	 * @param id the id
	 * @param entity the entity
	 * @return the response entity
	 * @throws CacheException the cache exception
	 */
	@RequestMapping(value="v1/diff/{id}/right", consumes =MediaType.APPLICATION_OCTET_STREAM_VALUE, method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> createRight (@PathVariable int id,InputStream entity  ) throws CacheException {

		compareService.createInput(id, getStringFromBinary(entity), InputType.RIGHT);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	/**
	 * Rest API to compare and get the result for given id
	 *
	 * @param id the id
	 * @return the difference result
	 * @throws CompareException the compare exception
	 */
	@GetMapping("v1/diff/{id}")
	public ResponseEntity<Output> getDiffResult (@PathVariable int id ) throws CompareException {
		
		return ResponseEntity.ok(compareService.compare(id));
	}
		
}
