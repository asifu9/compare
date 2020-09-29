package com.assignment.diffapp.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.assignment.diffapp.cache.CacheDataService;
import com.assignment.diffapp.exception.CacheException;
import com.assignment.diffapp.model.Input;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations="classpath:application-test.properties")
class CacheDataServiceTest {

	@Autowired
	CacheDataService service;

	Input INPUT1 = Input.builder().id(1).left("abc").build();
	Input INPUT2 = Input.builder().id(2).left("def").build();
	Input INPUT3 = Input.builder().id(3).left("ghi").build();
	Input INPUT4 = Input.builder().id(4).left("jkl").build();
	Input INPUT5 = Input.builder().id(5).left("mno").build();
	Input INPUT6 = Input.builder().id(6).left("pqr").build();

	@Test
	@Order(value = 1)
	void createFirstTest() throws CacheException {
		service.createOrUpdate(INPUT1);
		assertThat(service.findById(1).get()).isEqualTo(INPUT1);
	}

	@Test
	@Order(value = 2)
	void createAllOtherTest() throws CacheException {

		service.createOrUpdate(INPUT2);
		service.createOrUpdate(INPUT3);
		service.createOrUpdate(INPUT4);
		service.createOrUpdate(INPUT5);
		assertThat(service.findById(3).get()).isEqualTo(INPUT3);
		assertThat(service.findById(2).get()).isEqualTo(INPUT2);
		assertThat(service.findById(4).get()).isEqualTo(INPUT4);
		assertThat(service.findById(5).get()).isEqualTo(INPUT5);
	}

	@Test
	@Order(value = 3)
	void testLRUonNewInsert() throws CacheException {
		// the lease recently used should be evicted from cache
		// in this case while inserting input 6, input 1 should be removed
		service.createOrUpdate(INPUT6);
		assertNull(service.findById(1).orElse(null));
	}

	@Test
	@Order(value = 4)
	void testLRUonFetching() throws CacheException {
		// fetch all the elements except input 5
		service.findById(2);
		service.findById(3);
		service.findById(4);
		service.findById(6);
		// when adding new, input 5 should be evicted
		service.createOrUpdate(INPUT1);

		assertNull(service.findById(5).orElse(null));
	}

	@Test
	@Order(value = 5)
	void tsetUpdateAnInput() throws CacheException {

		Optional<Input> inputOptional = service.findById(2);
		Input input = inputOptional.orElse(null);
		input.setRight("xyz");
		service.createOrUpdate(input);
		assertThat(service.findById(2).get()).isEqualTo(input);
		assertThat(service.findById(2).get().getRight()).isEqualTo("xyz");

	}
}
