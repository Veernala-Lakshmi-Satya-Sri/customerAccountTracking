package com.bed.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bed.CustomerAccountTrackingApplication;
import com.bed.model.Account;
import com.bed.model.Customer;



//@RunWith(SpringRunner.class)
//@SpringBootTest(classes =CustomerAccountTrackingApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringBootTest
class BankControllerIntegrationTest {
	
//	@LocalServerPort
//	private int port;
//	
//	@Autowired
//	private TestRestTemplate restTemplate;
//	
	@Test
	void contextLoads() {
		assertEquals(6, 2+4);
	}
//
//	@Test
//	public void testAddCustomer() {
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		Set<Account> a_list= new HashSet<Account>();
//		a_list.add(new Account(12,"Savings",1200));
//		Customer c= new Customer(1,"Satya", 1234567890,a_list);
//		HttpEntity<Customer> requestEntity = new HttpEntity<>(c, headers);
//		
//		ResponseEntity<Customer> responseEntity = restTemplate.postForEntity("http://localhost:" +port+"/customer", requestEntity, Customer.class);
//
//		System.out.println("Status Code: " + responseEntity.getStatusCode());	
//		System.out.println("Id: " + responseEntity.getBody().getCustomerName());		
//		System.out.println("Location: " + responseEntity.getHeaders().getLocation());
//		
//		assertEquals("hello", responseEntity);
//
//	}
	
//	@Test
//	void getCustomer() {
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		Set<Account> a_list= new HashSet<Account>();
//		a_list.add(new Account(12,"Savings",1200));
//		Customer c= new Customer(1,"Satya", 1234567890,a_list);
//		HttpEntity<Customer> requestEntity = new HttpEntity<>(c, headers);
//		
//		ResponseEntity<Customer> responseEntity = restTemplate.postForEntity("http://localhost:" +port+"/customer", requestEntity, Customer.class);
//
//		System.out.println("Status Code: " + responseEntity.getStatusCode());	
//		System.out.println("Id: " + responseEntity.getBody().getCustomerName());		
//		System.out.println("Location: " + responseEntity.getHeaders().getLocation());
//
//	}

	
	
	
	

}
