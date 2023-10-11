package com.bed.repositary;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.bed.model.Account;
import com.bed.model.Customer;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
class CustomerRepoTest {

	@Autowired
	CustomerRepo customerRepo;
	
	@Autowired
	TestEntityManager entitymanager;

	
	@Test
	void findAllByCustomerNameIgnoreCaseTest() {
	
	
	
		Customer c= new Customer("Raj", 1234567890);
		Customer c2= new Customer("raj", 1234567890);
		Customer c3= new Customer("Raj2", 1234567890);
		c.setAccounts(Stream.of
				(
				new Account("Savings", 1000),
				new Account("JOINT", 1000000)
				).collect(Collectors.toSet()));
	
		c2.setAccounts(Stream.of
				(
				new Account("Savings", 1000),
				new Account("JOINT", 1000000)
				).collect(Collectors.toSet()));
	
		c3.setAccounts(Stream.of
				(
				new Account("Savings", 1000),
				new Account("JOINT", 1000000)
				).collect(Collectors.toSet()));
	
		entitymanager.persist(c);
		entitymanager.persist(c2);
		entitymanager.persist(c3);
		
		List<Customer> returnedList= customerRepo.findAllByCustomerNameIgnoreCase("Raj");
		
		
		assertEquals(2, returnedList.size());
	}
	
	
}









