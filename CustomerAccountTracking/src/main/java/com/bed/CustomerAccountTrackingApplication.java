package com.bed;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bed.Exceptions.InvalidCredintialsException;
import com.bed.Exceptions.SameAccountException;
import com.bed.model.Account;
import com.bed.model.Customer;
import com.bed.repositary.AccountRepo;
import com.bed.repositary.CustomerRepo;
import com.bed.service.AccountService;
import com.bed.service.CustomerService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;

@SpringBootApplication
public class CustomerAccountTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerAccountTrackingApplication.class, args);
	}

//	@Autowired
//	CustomerService customRepo;
//	
//	@Autowired
//	AccountService accountRepo;
//	
//	@PostConstruct
//	public void testData() throws Exception {
////		Set<Account> a_list= new HashSet<Account>();
////		a_list.add(new Account(11,"Savings",1200));
////		a_list.add(new Account(13,"JOINT",1000));
//		Customer c= new Customer(1,"SatyaTestData", 1234567890);
//		
//		c.setAccounts(Stream.of
//				(
//				new Account(1,"Savings",1200),
//				new Account(2,"JOINT", 1000)
//				).collect(Collectors.toSet()));
//	
//		customRepo.addCustomer(c);
//		
//	}
}
