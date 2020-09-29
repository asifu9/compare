package com.assignment.diffapp.integrationTest;


import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.diffapp.DiffAppApplication;
import com.assignment.diffapp.enums.OutputType;


/**
 * The Class CompareApiTest.
 * Integration Test for CompareApi
 */
//@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = DiffAppApplication.class)
@AutoConfigureMockMvc 
class CompareApiTest {

	/** The mvc. */
	@Autowired
    private MockMvc mvc;
	
	
	/**
	 * Test method to fetch as not data exists.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Order(1)
	void fetchAsNotDataExistsTest() throws Exception {
		
		 mvc.perform(get("/api/v1/diff/3").contentType(MediaType.APPLICATION_JSON))
         .andDo(print())
         .andExpect(status().isNotFound())
         .andExpect(jsonPath("$.message", is(" No Data found for ID 3 ")));
	}
	
	/**
	 * Test method to create the left data.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Order(2)
	void createLeftDataTest() throws Exception {
		//{
		//  "data":"data"
		//}
		byte[] bytes = "ewogICJkYXRhIjoiZGF0YSIKfQ==".getBytes(Charset.forName("UTF-8"));
		for(int i=0;i<bytes.length;i++) {
			System.out.println(bytes[i]);
		}
		 mvc.perform(post("/api/v1/diff/1/left").contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE).content(bytes))
         .andDo(print())
         .andExpect(status().isCreated());
	}
	    
	/**
	 * Test method to F=fetch incomplete comparison test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Order(3)
	void fetchIncompleteComparisonTest() throws Exception {
		
		 mvc.perform(get("/api/v1/diff/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message", is(" Right data missing for ID 1 ")));
	}
	
	/**
	 * Test method to create the right data.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Order(4)
	void createRightDataTest() throws Exception {
		
		byte[] bytes = "ewogICJkYXRhIjoiZGF0YSIKfQ==".getBytes(Charset.forName("UTF-8"));
		mvc.perform(post("/api/v1/diff/1/right").contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE).content(bytes))
         .andDo(print())
         .andExpect(status().isCreated());
	}
	
	/**
	 * Test method to fetch comparison result must be equal.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Order(5)
	void fetchComparisonResultMustEqualTest() throws Exception {
		
		 mvc.perform(get("/api/v1/diff/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is(OutputType.EQUALS.toString())));
	}
	
	/**
	 * 	Test method to update the right data.
	 *
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Order(6)
	void updateRightDataTest() throws Exception {

		byte[] bytes = "ewogICJkYXQxIjoiZGF0MSIKfQ==".getBytes(Charset.forName("UTF-8"));
		 
		 
		 mvc.perform(post("/api/v1/diff/1/right").contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE).content(bytes))
         .andDo(print())
         .andExpect(status().isCreated());
	}
	
	/**
	 * Test method to fetch difference with offset result.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Order(7)
	void fetchDifferenceResultTest() throws Exception {
		
		 mvc.perform(get("/api/v1/diff/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is(OutputType.OFFSET_DIFFERENCE.toString())))
        .andExpect(jsonPath("$.difference[0].offset", is(8)))
		.andExpect(jsonPath("$.difference[0].length", is(1)));
	}
	
	/**
	 * Test method to update the right data with different size data
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Order(8)
	void updateRightWithDifferentSizeDataTest() throws Exception {
		byte[] bytes = "ewogICJkYXRhMSI6ImRhdGEyIgp9".getBytes(Charset.forName("UTF-8"));
		 mvc.perform(post("/api/v1/diff/1/right").contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE).content(bytes))
         .andDo(print())
         .andExpect(status().isCreated());
	}
	
	/**
	 * Fetch difference size result test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@Order(9)
	void fetchDifferenceSizeResultTest() throws Exception {
		
		 mvc.perform(get("/api/v1/diff/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is(OutputType.SIZE_DIFFERENCE.toString())));
	}
	
	@Test
	@Order(10)
	void leftDataMissingExceptionTest() throws Exception {
		//{
		//  "data":"data"
		//}
		byte[] bytes = "ewogICJkYXRhIjoiZGF0YSIKfQ==".getBytes(Charset.forName("UTF-8"));
		for(int i=0;i<bytes.length;i++) {
			System.out.println(bytes[i]);
		}
		 mvc.perform(post("/api/v1/diff/2/right").contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE).content(bytes))
         .andDo(print())
         .andExpect(status().isCreated());
		 mvc.perform(get("/api/v1/diff/2").contentType(MediaType.APPLICATION_JSON))
	        .andDo(print())
	        .andExpect(status().isInternalServerError())
	        .andExpect(jsonPath("$.message", is(" Left data missing for ID 2 ")));
	}
}
