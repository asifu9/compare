package com.assignment.diffapp.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.assignment.diffapp.enums.OutputType;
import com.assignment.diffapp.model.Difference;
import com.assignment.diffapp.model.Output;
import com.assignment.diffapp.service.IDifferenceService;


/**
 * The Class CompareServiceTest.
 */
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class DifferenceServiceTest {

	/** The service. */
	@Autowired
	IDifferenceService<Output, String, String> service;

	/**
	 * Method to compare with empty data.
	 */
	@Test
	void differenceWithOffsetTest() {
		Output output=service.compare("sdfsfdf==", "fdsfsdf==");
		assertThat(output.getStatus()).isEqualTo(OutputType.OFFSET_DIFFERENCE);
		assertThat(output.getDifference().size()).isEqualTo(2);
		assertThat(output.getDifference().get(0)).isEqualTo(new Difference(0, 1));
		assertThat(output.getDifference().get(1)).isEqualTo(new Difference(2, 3));
	}
	
	/**
	 * Method to compare with empty data.
	 */
	@Test
	void differenceWithOffsetTest2() {
		Output output=service.compare("aa22cc==", "aa12cc==");
		assertNotNull(output.getDifference());
		assertThat(output.getStatus()).isEqualTo(OutputType.OFFSET_DIFFERENCE);
		assertThat(output.getDifference().size()).isEqualTo(1);
		assertThat(output.getDifference().get(0)).isEqualTo(new Difference(2, 1));
	}

	@Test
	void shouldBeEqualsTest() {
		Output output=service.compare("sdfsfdf==", "sdfsfdf==");
		assertThat(output.getStatus()).isEqualTo(OutputType.EQUALS);
		assertNull(output.getDifference());
	}

	@Test
	void differnceInSizeTest() {
		Output output=service.compare("sdfsf1df==", "sdfsfdf==");
		assertThat(output.getStatus()).isEqualTo(OutputType.SIZE_DIFFERENCE);
		assertNull(output.getDifference());
	}

}
