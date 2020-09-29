package com.assignment.diffapp.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.assignment.diffapp.enums.InputType;
import com.assignment.diffapp.enums.OutputType;
import com.assignment.diffapp.exception.CacheException;
import com.assignment.diffapp.exception.CompareException;
import com.assignment.diffapp.exception.IDNotFoundException;
import com.assignment.diffapp.exception.InputDataMissingException;
import com.assignment.diffapp.model.Difference;
import com.assignment.diffapp.model.Output;
import com.assignment.diffapp.service.CompareService;


/**
 * The Class CompareServiceTest.
 */
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations="classpath:application-test.properties")
class CompareServiceTest {

	/** The service. */
	@Autowired
	CompareService service;

	/**
	 * Method to compare with empty data.
	 */
	@Test
	@Order(value = 1)
	void compareWithEmptyDataTest() {
		final Throwable thrown = catchThrowable(() -> service.compare(1));
        assertThat(thrown).isInstanceOf(IDNotFoundException.class);
	}

	/**
	 * Method to compare with left data and should throw error 
	 * @throws CacheException 
	 */
	@Test
	@Order(value = 2)
	void compareWithLeftDataTest() throws CacheException {

		service.createInput(1, "fe2ft3f==", InputType.LEFT);
		final Throwable thrown = catchThrowable(() -> service.compare(1));
        assertThat(thrown).isInstanceOf(InputDataMissingException.class);
	}

	/**
	 * Method to compare with right data should be equal.
	 *
	 * @throws CompareException the compare exception
	 */
	@Test
	@Order(value = 3)
	void compareWithEqualDataTest() throws CompareException {
		service.createInput(1, "fe2ft3f==", InputType.RIGHT);
		assertThat(service.compare(1).getStatus()).isEqualTo(OutputType.EQUALS);
	}

	/**
	 * Method to compare with difference offset data.
	 *
	 * @throws CompareException the compare exception
	 */
	@Test
	@Order(value = 4)
	void compareWithDifferenceOffsetDataTest() throws CompareException {
		service.createInput(1, "fe2ft32==", InputType.LEFT);
		final Output output =  service.compare(1);
        assertThat(output.getStatus()).isEqualTo(OutputType.OFFSET_DIFFERENCE);
        assertThat(output.getDifference().get(0)).isEqualTo(new Difference(6, 1));
	}

	/**
	 * Method to compare with  difference size data.
	 *
	 * @throws CompareException the compare exception
	 */
	@Test
	@Order(value = 5)
	void compareWithSizeDifferenceDataTest() throws CompareException {

		service.createInput(1, "fe2ft321==", InputType.LEFT);
		final Output output =  service.compare(1);
        assertThat(output.getStatus()).isEqualTo(OutputType.SIZE_DIFFERENCE);
	}
}
